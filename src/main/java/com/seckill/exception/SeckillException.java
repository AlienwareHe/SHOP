package com.seckill.exception;

/**
 * 秒杀相关业务异常
 * Created by Hexun on 2017/8/8 0008.
 */
public class SeckillException extends RuntimeException{


    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
