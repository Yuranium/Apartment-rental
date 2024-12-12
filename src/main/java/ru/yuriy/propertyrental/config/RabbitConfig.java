package ru.yuriy.propertyrental.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig
{
    @Bean
    public Queue imageQueue(@Value("${queue.python-to-java.name}") String queue)
    {
        return new Queue(queue, false);
    }

    @Bean
    public Queue dataQueue(@Value("${queue.java-to-python.name}") String queue)
    {
        return new Queue(queue, false);
    }
}