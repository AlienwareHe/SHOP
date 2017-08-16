package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Hexun on 2017/8/5 0005.
 */
public interface SeckillDao {
    /**
    *@Author:HeXun
    *@Description: 减库存
    *@Date: 2017/8/5 0005
    */
    public int reduceNum(@Param("secKillId") long secKillId,@Param("killTime") Date killTime);

    /**
    *@Author:HeXun
    *@Description: 根据id查询秒杀商品
    *@Date: 2017/8/5 0005
    */
    public Seckill queryById( long secKillId);

    /**
    *@Author:HeXun
    *@Description: 根据偏移量查询商品列表
    *@Date: 2017/8/5 0005
    */
    //通过param注解可告诉myabtis xml文件传入多个参数时如何通过#{name}获得参数，否则只能通过#{index}参数顺序绑定参数
    public List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    public void killByProcedure(Map<String,Object> paramMap);
}
