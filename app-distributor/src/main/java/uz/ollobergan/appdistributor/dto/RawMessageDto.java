package uz.ollobergan.appdistributor.dto;


import lombok.Data;

@Data
public class RawMessageDto  {
    private String messageType;
    private byte[] object;
}