package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 *Created by Hexun on 2017/8/7 0007.
 *
 * 配置spring和junit整合，junit启动时加载spring ioc容器
 * spring test，junit*/
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() throws Exception {
        long id=1L;
        Seckill seckill=seckillDao.queryById(id);
        System.out.println(seckill);
    }
    @Test
    public void queryAll() throws Exception {

        List<Seckill> l=seckillDao.queryAll(0,2);
        for(Seckill s:l){
            System.out.println(s.getName());
        }
    }
    @Test
    public void reduceNum() throws Exception {
        long seckillid=1;
        Date d=new Date();
        int updateTimes=seckillDao.reduceNum(1L,d);
        System.out.println(updateTimes);
    }


}