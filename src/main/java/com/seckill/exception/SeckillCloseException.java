package com.seckill.exception;

/**
 * 秒杀系统关闭异常
 * Created by Hexun on 2017/8/8 0008.
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
