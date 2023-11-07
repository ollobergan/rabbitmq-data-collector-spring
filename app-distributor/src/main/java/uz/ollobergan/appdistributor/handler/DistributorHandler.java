package uz.ollobergan.appdistributor.handler;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import uz.ollobergan.appdistributor.constants.RabbitMqConstants;
import uz.ollobergan.appdistributor.dto.RawMessageDto;
import uz.ollobergan.appdistributor.helper.CompressHelper;

@Service
public class DistributorHandler {

    /**
     * Main listener for data collectot.
     * This method distribute messages to queues.
     *
     * @param message
     * @throws Exception
     */
    @RabbitListener(queues = RabbitMqConstants.MAIN_QUEUE)
    public void listenMessages(Message message) throws Exception {
        //Get rabbit message body
        String messageBody = new String(message.getBody(),"UTF-8");
        //Decode message to DTO
        RawMessageDto rawMessageDto = CompressHelper.DecodeToDto(messageBody,RawMessageDto.class);
        //Decompress object
        String messageJson = CompressHelper.Decompress(rawMessageDto.getObject());
        //Show json
        System.out.println(messageJson);
    }
}
