package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Hexun on 2017/8/9 0009.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    @Autowired
    private SeckillService seckillService;
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list=seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getSeckillById() throws Exception {
        Seckill seckill=seckillService.getSeckillById(5L);
        logger.info("sec:{}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        Exposer exposer=seckillService.exportSeckillUrl(1L);
        logger.info("exposer:{}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long id=1L;
        long userphone=12345678;
        String md5="f96758275b19372a94da8ad09948";
        SeckillExecution seckillExecution=seckillService.executeSeckill(id,userphone,md5);
        logger.info("seckill:{}",seckillExecution);
    }

    @Test
    public void executeSeckillTestProcedure(){
        long seckillId=1L;
        long phone=15478945632L;
        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5=exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.excuteSeckillProcedure(seckillId, phone, md5);
            System.out.println(seckillExecution.getStateInfo());
        }
    }

}