package com.codeplayer.dto;

import com.codeplayer.exception.CustomizeErrorCode;
import com.codeplayer.exception.CustomizeException;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Data
@Component
public class ResultDTO<T> implements Serializable{
    private static final long serialVersionUID = 5180649989439680003L;
    private int code;
    private String message;
    private String url;
    private T data;

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage("请求成功");
        resultDTO.setCode(200);
        return resultDTO;
    }
    public static ResultDTO error() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage("【失败】用户名或密码错误!!");
        resultDTO.setCode(100);
        return resultDTO;
    }

    public static ResultDTO errorOf(Integer code,String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO okOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static <T> ResultDTO<List<CommentDTO>> okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }

}
