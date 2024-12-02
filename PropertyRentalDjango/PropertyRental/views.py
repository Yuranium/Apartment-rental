import json
import logging
from io import BytesIO

from django.http import JsonResponse
from pika import BlockingConnection, ConnectionParameters

from .create_graph import build_graph
from .settings import JAVA_TO_PYTHON_QUEUE_NAME, PYTHON_TO_JAVA_QUEUE_NAME

connection_params = ConnectionParameters(
    port=5672,
    host='localhost'
)

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s [%(levelname)s] %(message)s',
    datefmt='%Y-%m-%d %H:%M:%S',
)


def consume_to_queue(request):
    try:
        with BlockingConnection(connection_params) as connection:
            with connection.channel() as channel:
                channel.queue_declare(queue=JAVA_TO_PYTHON_QUEUE_NAME)
                logging.info(f'Объявление очереди: {JAVA_TO_PYTHON_QUEUE_NAME}')

                method_frame, header_frame, body = channel.basic_get(queue=JAVA_TO_PYTHON_QUEUE_NAME)

                if method_frame:
                    channel.basic_ack(delivery_tag=method_frame.delivery_tag)
                    message = body.decode('utf-8')

                    try:
                        data = json.loads(message)
                        logging.info(f'Прочитано сообщение {data} из очереди {JAVA_TO_PYTHON_QUEUE_NAME}')
                    except json.JSONDecodeError:
                        logging.error(f'Ошибка парсинга JSON из очереди {JAVA_TO_PYTHON_QUEUE_NAME}')
                        return JsonResponse({'status': 'error', 'message': 'Invalid JSON format'})

                    send_to_queue(PYTHON_TO_JAVA_QUEUE_NAME, build_graph(json.loads(message)))
                    logging.info(f'Прочитано сообщение "{message}" из очереди "{JAVA_TO_PYTHON_QUEUE_NAME}" '
                                 f'и отправлено в очередь "{PYTHON_TO_JAVA_QUEUE_NAME}"')

                    return JsonResponse({'status': 'Успешно', 'message': message})
                else:
                    return JsonResponse({'status': 'Предупреждение',
                                         'message': 'Очередь пуста'
                                         })
    except Exception as e:
        logging.error(f'Ошибка: {str(e)}')
        return JsonResponse({'status': 'error', 'message': str(e)})


def send_to_queue(queue_name, data):
    with BlockingConnection(connection_params) as connection:
        with connection.channel() as channel:
            channel.queue_declare(queue=queue_name)
            logging.info(f'Объявление очереди: {queue_name}')
            if isinstance(data, BytesIO):
                data = data.getvalue()

            channel.basic_publish(exchange='',
                                  routing_key=queue_name,
                                  body=data)
            logging.info(f'Отправлено сообщение в очередь {queue_name}. Размер сообщения: {len(data)} байт')
