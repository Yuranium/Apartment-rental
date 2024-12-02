import matplotlib.pyplot as plt
from io import BytesIO
from datetime import datetime


def build_graph(data: dict) -> BytesIO:
    dates = [datetime.strptime(date, "%Y-%m-%d") for date in data.keys()]
    values = list(data.values())

    plt.figure(figsize=(10, 6))
    plt.plot(dates, values, marker='o', linestyle='-', color='b')
    plt.title('График данных по дням')
    plt.xlabel('Дата')
    plt.ylabel('Количество арендованных апартаментов')
    plt.grid(True)
    plt.gcf().autofmt_xdate()

    buffer = BytesIO()
    plt.savefig(buffer, format='png')
    buffer.seek(0)
    plt.close()
    return buffer
