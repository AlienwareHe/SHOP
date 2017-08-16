package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Hexun on 2017/8/7 0007.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKill() throws Exception {
        int x=successKilledDao.insertSuccessKill(7L,123456789L,new Date());
        System.out.println(x);
    }

    @Test
    public void queryByIdWithSecKill() throws Exception {
        SuccessKilled s=successKilledDao.queryByIdWithSecKill(5L,123456789L);
        System.out.println(s.getSeckill().getName());
    }

}