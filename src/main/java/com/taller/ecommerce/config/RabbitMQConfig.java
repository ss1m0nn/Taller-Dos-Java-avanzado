package com.taller.ecommerce.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RabbitMQConfig {
    public static final String ORDEN_EXCHANGE     = "orden.exchange";
    public static final String PAGO_EXCHANGE      = "pago.exchange";
    public static final String ORDEN_CREADA_QUEUE   = "orden.creada.queue";
    public static final String PAGO_APROBADO_QUEUE  = "pago.aprobado.queue";
    public static final String PAGO_RECHAZADO_QUEUE = "pago.rechazado.queue";
    public static final String ORDEN_CREADA_KEY   = "orden.creada";
    public static final String PAGO_APROBADO_KEY  = "pago.aprobado";
    public static final String PAGO_RECHAZADO_KEY = "pago.rechazado";

    @Bean public TopicExchange ordenExchange() { return new TopicExchange(ORDEN_EXCHANGE); }
    @Bean public TopicExchange pagoExchange()  { return new TopicExchange(PAGO_EXCHANGE); }

    @Bean public Queue ordenCreadaQueue()   { return new Queue(ORDEN_CREADA_QUEUE,   true); }
    @Bean public Queue pagoAprobadoQueue()  { return new Queue(PAGO_APROBADO_QUEUE,  true); }
    @Bean public Queue pagoRechazadoQueue() { return new Queue(PAGO_RECHAZADO_QUEUE, true); }

    @Bean
    public Binding bindOrdenCreada() {
        return BindingBuilder.bind(ordenCreadaQueue()).to(ordenExchange()).with(ORDEN_CREADA_KEY);
    }
    @Bean
    public Binding bindPagoAprobado() {
        return BindingBuilder.bind(pagoAprobadoQueue()).to(pagoExchange()).with(PAGO_APROBADO_KEY);
    }
    @Bean
    public Binding bindPagoRechazado() {
        return BindingBuilder.bind(pagoRechazadoQueue()).to(pagoExchange()).with(PAGO_RECHAZADO_KEY);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
