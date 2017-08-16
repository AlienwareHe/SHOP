package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

import java.util.List;

/**
 *
 * 业务层接口：站在使用者设计角度上
 * 三个方面：方法定义粒度，参数(简练直接)，返回类型(return 类型/异常)
 * Created by Hexun on 2017/8/8 0008.
 */
public interface SeckillService {
    //查询所有秒杀记录
    List<Seckill> getSeckillList();
    //查询单个秒杀记录
    Seckill getSeckillById(long seckillId);
    //秒杀开启时输出秒杀接口的地址，否则输出系统秒杀时间和地址.
    //防止用户恶意猜出秒杀网页的地址，利用插件进行秒杀
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作,此处应该抛出自定义异常，例如重复秒杀
     * @param seckillId
     * @param userphone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userphone, String md5) throws SeckillException,RepeatKillException,SeckillCloseException;

    /**
     * 通过存储过程执行秒杀过程
     * @param seckillId
     * @param userphone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    SeckillExecution excuteSeckillProcedure(long seckillId, long userphone, String md5) throws SeckillException,RepeatKillException,SeckillCloseException;
}
