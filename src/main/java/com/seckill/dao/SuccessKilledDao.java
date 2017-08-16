package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Hexun on 2017/8/5 0005.
 */
public interface SuccessKilledDao {
    /**
    *@Author:HeXun
    *@Description: 保存秒杀明细，过滤重复秒杀
    *@Date: 2017/8/5 0005
    */
    public int insertSuccessKill(@Param("secKillId") long secKillId,@Param("userPhone") long userPhone,@Param("createTime") Date nowTime);

    /**
    *@Author:HeXun
    *@Description: 根据id查询秒杀明细并携带秒杀产品对象
    *@Date: 2017/8/5 0005
    */
    public SuccessKilled queryByIdWithSecKill(@Param("secKillId") long secKillId,@Param("userPhone") long userPhone);
}
