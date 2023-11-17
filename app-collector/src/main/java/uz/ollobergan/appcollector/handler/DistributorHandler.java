package uz.ollobergan.appcollector.handler;

import jakarta.annotation.Resource;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import uz.ollobergan.appcollector.constants.RawMessageTypes;
import uz.ollobergan.appcollector.constants.RabbitMqConstants;
import uz.ollobergan.appcollector.dto.RawMessageDto;
import uz.ollobergan.appcollector.helper.CompressHelper;

@Service
public class DistributorHandler {

    @Resource
    RabbitTemplate rabbitTemplate;

    /**
     * Main listener for data collector.
     * This method distribute messages to queues.
     *
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues = RabbitMqConstants.RABBIT_MAIN_QUEUE, containerFactory = "rabbitListenerContainerFactory")
    public void listenMessages(Message message) throws Exception {
        //Get rabbit message body
        String messageBody = new String(message.getBody(),"UTF-8");
        //Decode message to DTO
        RawMessageDto rawMessageDto = CompressHelper.DecodeToDto(messageBody,RawMessageDto.class);

        seporateMessages(rawMessageDto);
    }

    private void seporateMessages(RawMessageDto rawMessageDto) throws Exception{
        switch (rawMessageDto.getMessageType()){
            case RawMessageTypes.MESSAGE_WEFO_REPORT: sendWefoReportToQueue(rawMessageDto); break;
            default: throw new Exception("Wrong message type!");
        }
    }

    private void sendWefoReportToQueue(RawMessageDto rawMessageDto){
        rabbitTemplate.convertAndSend(RabbitMqConstants.RABBIT_WEFO_REPORT_EXCHANGE,RabbitMqConstants.RABBIT_WEFO_REPORT_ROUTING_KEY,rawMessageDto);
    }
}
