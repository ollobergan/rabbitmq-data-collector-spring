package uz.ollobergan.appsubscriber.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import uz.ollobergan.appsubscriber.constants.RabbitMqConstants;
import uz.ollobergan.appsubscriber.dto.DemoBigDto;
import uz.ollobergan.appsubscriber.dto.RawMessageDto;
import uz.ollobergan.appsubscriber.helper.CompressHelper;
import uz.ollobergan.appsubscriber.mapper.ReportMapper;

@Service
public class ReportDataHandler {

    @RabbitListener(queues = RabbitMqConstants.RABBIT_WEFO_REPORT_QUEUE, containerFactory = "rabbitListenerContainerFactoryWefoReport")
    public void listenerReportData(Message message) throws Exception {
        //Get rabbit message body
        String messageBody = new String(message.getBody(),"UTF-8");
        //Decode message to DTO
        RawMessageDto rawMessageDto = CompressHelper.DecodeToDto(messageBody,RawMessageDto.class);
        //Convert byte to json
        String json = CompressHelper.Decompress(rawMessageDto.getObject());
        DemoBigDto demoBigDto = ReportMapper.jsonToDto(json);

        System.out.println(demoBigDto.toString());
    }
}
