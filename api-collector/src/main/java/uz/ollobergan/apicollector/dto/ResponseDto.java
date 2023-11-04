package uz.ollobergan.apicollector.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto<T>{
    private boolean success;
    private String message;
    private T data;

    public static ResponseDto success(String message, Object object){
        ResponseDto responseDto = new ResponseDto<>();
        responseDto.setSuccess(true);
        responseDto.setMessage(message);
        responseDto.setData(object);
        return responseDto;
    }

    public static ResponseDto error(String message, Object object){
        ResponseDto responseDto = new ResponseDto<>();
        responseDto.setSuccess(false);
        responseDto.setMessage(message);
        responseDto.setData(object);
        return responseDto;
    }
}
