package uz.ollobergan.apicollector;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import uz.ollobergan.apicollector.config.RabbitMqConfig;
import uz.ollobergan.apicollector.constants.RabbitConstants;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(RabbitMqConfig.class)
@ActiveProfiles("test")
@Disabled
public class RabbitConfigTest {
    @Autowired
    private DirectExchange exchange;

    @Autowired
    private DirectExchange exchangeError;

    @Autowired
    private Queue queue;

    @Autowired
    private Queue queueError;

    @Autowired
    private Binding binding;

    @Autowired
    private Binding bindingError;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    public void testRabbitMQConfig() {
        assertNotNull(exchange);
        assertNotNull(exchangeError);

        assertNotNull(queue);
        assertNotNull(queueError);

        assertNotNull(binding);
        assertNotNull(bindingError);

        assertTrue(rabbitAdmin.getQueueProperties(RabbitConstants.RABBIT_MAIN_QUEUE).size() > 0);
        assertTrue(rabbitAdmin.getQueueProperties(RabbitConstants.RABBIT_MAIN_QUEUE_ERROR).size() > 0);

        assertTrue(binding.getRoutingKey() == RabbitConstants.RABBIT_MAIN_ROUTING_KEY);
        assertTrue(bindingError.getRoutingKey() == RabbitConstants.RABBIT_MAIN_ROUTING_KEY_ERROR);

        assertTrue(exchange.isDurable());
        assertTrue(exchangeError.isDurable());

        assertTrue(!queue.isAutoDelete());
        assertTrue(!queueError.isAutoDelete());
    }
}
