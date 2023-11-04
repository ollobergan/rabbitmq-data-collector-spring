package uz.ollobergan.apicollector.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class RawMessageDto {
    private String messageType;
    private byte[] object;
}