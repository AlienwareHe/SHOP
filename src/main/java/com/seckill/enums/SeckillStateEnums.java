package com.seckill.enums;

/**
 * Created by Hexun on 2017/8/8 0008.
 */
public enum SeckillStateEnums {
    SUCCESS(1,"秒杀成功"),
    REPEAT(0,"重复秒杀"),
    REWRITE(-1,"数据篡改"),
    END(-2,"秒杀结束"),
    INNER_ERROR(-3,"系统异常");

    private int state;
    private String stateInfo;

    private SeckillStateEnums(int state, String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStateEnums getState(int index){
        for(SeckillStateEnums s:values()){
            if(s.getState()==index){
                return s;
            }
        }
        return null;
    }
}
