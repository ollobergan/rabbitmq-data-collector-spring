package uz.ollobergan.apicollector.service;

import uz.ollobergan.apicollector.dto.RawMessageDto;

public interface RabbitMqProducerService {

    public void pushMessage(RawMessageDto rawMessageDto);

}
