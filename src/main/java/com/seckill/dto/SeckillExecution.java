package com.seckill.dto;

import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnums;

/**
 * 封装执行秒杀后的结果
 * Created by Hexun on 2017/8/8 0008.
 */
public class SeckillExecution {
    private long seckillId;
    //秒杀状态
    private int state;
    //秒杀状态信息
    private String stateInfo;
    //秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, SeckillStateEnums stateEnums, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = stateEnums.getState();
        this.stateInfo = stateEnums.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckillId, SeckillStateEnums stateEnums) {
        this.seckillId = seckillId;
        this.state = stateEnums.getState();
        this.stateInfo = stateEnums.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
