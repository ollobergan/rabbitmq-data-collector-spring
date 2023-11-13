package uz.ollobergan.appsubscriber.dto;


import lombok.Data;

@Data
public class RawMessageDto  {
    private String messageType;
    private byte[] object;
}