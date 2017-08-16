package com.seckill.exception;

/**
 * 重复秒杀异常
 * Spring 声名式事务只会对运行时异常进行处理
 * Created by Hexun on 2017/8/8 0008.
 */
public class RepeatKillException extends SeckillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
