package ru.yuriy.propertyrental.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.yuriy.propertyrental.models.entity.User;
import ru.yuriy.propertyrental.repositories.PaymentRepository;
import ru.yuriy.propertyrental.util.exceptions.ImageNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Setter
@RequiredArgsConstructor
public class RabbitService
{
    private final AmqpTemplate template;

    private final PaymentRepository paymentRepository;

    private final RestTemplate restTemplate;

    @Value("${queue.java-to-python.name}")
    private String javaPythonQueue;

    @Value("${queue.python-to-java.name}")
    private String pythonJavaQueue;

    @Value("${python.django.url}")
    private String pythonUrl;

    @SneakyThrows
    @Transactional(readOnly = true)
    public byte[] compilingDataGraph(User user)
    {
        List<Object[]> results = paymentRepository.findAllByUserGrouping(user);
        Map<String, Integer> groupedPayments = results.stream()
                .collect(Collectors.toMap(
                        result -> result[0].toString(),
                        result -> ((Number) result[1]).intValue()
                ));
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(groupedPayments);
        template.convertAndSend(javaPythonQueue, jsonMessage);
        restTemplate.getForObject(pythonUrl, String.class);

        Message response = template.receive(pythonJavaQueue, 5000);
        if (response != null && response.getBody().length != 0)
            return response.getBody();
        else throw new ImageNotFoundException("Не удалось получить график платежей");
    }
}