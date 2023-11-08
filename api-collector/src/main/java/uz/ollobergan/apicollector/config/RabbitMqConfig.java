package uz.ollobergan.apicollector.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for RabbitMQ
 */
@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.queue.name}")
    private String queueName;
    @Value("${rabbitmq.queue.name_error}")
    private String queueNameError;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.exchange.name_error}")
    private String exchangeError;

    @Value("${rabbitmq.routing.key.name}")
    private String routingkey;

    @Value("${rabbitmq.routing.key.name_error}")
    private String routingkeyError;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.virtualhost}")
    private String virtualHost;

    @Value("${rabbitmq.reply.timeout}")
    private Integer replyTimeout;

    /**
     * Create queue
     */
    @Bean
    public Queue queue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", exchangeError); //error exch
        args.put("x-dead-letter-routing-key", routingkeyError);
        return new Queue(queueName, false, false, false, args);
    }
    @Bean
    public Queue queueError() {
        return new Queue(queueNameError, false, false, false);
    }

    /**
     * Create exchange
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }
    @Bean
    public DirectExchange exchangeError() {
        return new DirectExchange(exchangeError);
    }

    /**
     * Binding queue to exchange
     */
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }

    @Bean
    public Binding bindingError() {
        return BindingBuilder.bind(queueError()).to(exchangeError()).with(routingkeyError);
    }

    /**
     * Message convert configuration
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * RabbitMQ credentials setup
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    /**
     * Default configuration setup
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setDefaultReceiveQueue(queueName);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setReplyAddress(queue().getName());
        rabbitTemplate.setReplyTimeout(replyTimeout);
        rabbitTemplate.setUseDirectReplyToContainer(false);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(routingkey);
        return rabbitTemplate;
    }

    /**
     * RabbitMQ admin configuration
     */
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
}
