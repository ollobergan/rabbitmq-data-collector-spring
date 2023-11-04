package uz.ollobergan.apicollector.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ollobergan.apicollector.dto.RawMessageDto;
import uz.ollobergan.apicollector.service.RabbitMqProducerService;

@Service
public class RabbitMqProducerServiceImpl implements RabbitMqProducerService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void pushMessage(RawMessageDto rawMessageDto){
        rabbitTemplate.convertAndSend(rawMessageDto);
    }
}
