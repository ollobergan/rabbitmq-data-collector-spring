package uz.ollobergan.apicollector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.ollobergan.apicollector.dto.RawMessageDto;
import uz.ollobergan.apicollector.dto.ResponseDto;
import uz.ollobergan.apicollector.helper.CompressHelper;
import uz.ollobergan.apicollector.service.RabbitMqProducerService;


@RestController
public class ExternalDataController {

    @Autowired
    RabbitMqProducerService rabbitMqProducerService;

    @PostMapping("/send/{message_type}")
    public ResponseDto sendMessage(@RequestBody Object object, @PathVariable("message_type") String messageType) throws Exception {
        RawMessageDto message = CompressHelper.CompressRawMessageDto(object, messageType);
        rabbitMqProducerService.pushMessage(message);
        return ResponseDto.success("Success",null);
    }

}
