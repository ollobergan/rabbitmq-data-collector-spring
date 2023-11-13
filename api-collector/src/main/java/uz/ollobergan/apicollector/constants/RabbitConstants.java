package uz.ollobergan.apicollector.constants;

public class RabbitConstants {
    //------------------------------------------------------------------------------------------------------------------
    // QUEUES
    //------------------------------------------------------------------------------------------------------------------
    public static final String RABBIT_MAIN_QUEUE = "collector_queue";
    public static final String RABBIT_MAIN_QUEUE_ERROR = "collector_queue_error";

    //------------------------------------------------------------------------------------------------------------------
    // EXCHANGES
    //------------------------------------------------------------------------------------------------------------------
    public static final String RABBIT_MAIN_EXCHANGE = "collector_exchange";
    public static final String RABBIT_MAIN_EXCHANGE_ERROR = "collector_exchange_error";

    //------------------------------------------------------------------------------------------------------------------
    // ROUTING KEYS
    //------------------------------------------------------------------------------------------------------------------
    public static final String RABBIT_MAIN_ROUTING_KEY = "collector_routing_key";
    public static final String RABBIT_MAIN_ROUTING_KEY_ERROR = "collector_routing_key_error";

}
