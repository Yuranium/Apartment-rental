package ru.yuriy.propertyrental.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig
{
    @Bean
    public Queue imageQueue()
    {
        return new Queue("image_queue", false);
    }

    @Bean
    public Queue dataQueue()
    {
        return new Queue("data_queue", false);
    }
}