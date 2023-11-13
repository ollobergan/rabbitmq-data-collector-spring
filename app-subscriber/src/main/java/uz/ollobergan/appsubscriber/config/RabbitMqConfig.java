package uz.ollobergan.appsubscriber.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import uz.ollobergan.appsubscriber.constants.RabbitMqConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for RabbitMQ
 */
@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.virtualhost}")
    private String virtualHost;

    private final Integer replyTimeout = 60000;
    /**
     * Create queue
     */
    @Bean
    public Queue queue() {
        return new Queue(RabbitMqConstants.RABBIT_MAIN_QUEUE, false, false, false);
    }
    @Bean
    public Queue queueError() {
        return new Queue(RabbitMqConstants.RABBIT_MAIN_QUEUE_ERROR, false, false, false);
    }

    @Bean
    public Queue wefoReportQueue() {
        return new Queue(RabbitMqConstants.RABBIT_WEFO_REPORT_QUEUE, false, false, false);
    }
    @Bean
    public Queue wefoReportQueueError() {
        return new Queue(RabbitMqConstants.RABBIT_WEFO_REPORT_QUEUE_ERROR, false, false, false);
    }

    /**
     * Create exchange
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(RabbitMqConstants.RABBIT_MAIN_EXCHANGE);
    }
    @Bean
    public DirectExchange exchangeError() {
        return new DirectExchange(RabbitMqConstants.RABBIT_MAIN_EXCHANGE_ERROR);
    }

    @Bean
    public DirectExchange wefoReportExchange() {
        return new DirectExchange(RabbitMqConstants.RABBIT_WEFO_REPORT_EXCHANGE);
    }

    @Bean
    public DirectExchange wefoReportExchangeError() {
        return new DirectExchange(RabbitMqConstants.RABBIT_WEFO_REPORT_EXCHANGE_ERROR);
    }
    /**
     * Binding queue to exchange
     */
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMqConstants.RABBIT_MAIN_ROUTING_KEY);
    }

    @Bean
    public Binding bindingError() {
        return BindingBuilder.bind(queueError()).to(exchangeError()).with(RabbitMqConstants.RABBIT_MAIN_ROUTING_KEY_ERROR);
    }

    @Bean
    public Binding bindingWefoReport() {
        return BindingBuilder.bind(wefoReportQueue()).to(wefoReportExchange()).with(RabbitMqConstants.RABBIT_WEFO_REPORT_ROUTING_KEY);
    }

    @Bean
    public Binding bindingWefoReportError() {
        return BindingBuilder.bind(wefoReportQueueError()).to(wefoReportExchangeError()).with(RabbitMqConstants.RABBIT_WEFO_REPORT_ROUTING_KEY_ERROR);
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
        rabbitTemplate.setDefaultReceiveQueue(RabbitMqConstants.RABBIT_MAIN_QUEUE);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setReplyAddress(queue().getName());
        rabbitTemplate.setReplyTimeout(replyTimeout);
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }

    /**
     * RabbitMQ admin configuration
     */
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(3);
        factory.setAdviceChain(retryInterceptor());
        return factory;
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor() {
        RepublishMessageRecoverer recoverer = new RepublishMessageRecoverer(new RabbitTemplate(connectionFactory()), RabbitMqConstants.RABBIT_MAIN_EXCHANGE_ERROR, RabbitMqConstants.RABBIT_MAIN_ROUTING_KEY_ERROR);
        return RetryInterceptorBuilder.stateless().maxAttempts(1).recoverer(recoverer).build();
    }
}
