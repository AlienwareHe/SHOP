package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SuccessKilledDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExecution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.SeckillStateEnums;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hexun on 2017/8/8 0008.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired //@Resource @inject
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    //md5盐值，用于混淆md5
    private final String salt="safwef2341";
    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime=seckill.getStarttime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userphone, String md5) throws  RepeatKillException, SeckillCloseException ,SeckillException{
        if(md5==null ||! md5.equals(getMD5(seckillId))){
            throw new SeckillException("md5 rewrite");
        }

        Date nowTime=new Date();
        try {
            //添加购买明细
            int insertCount=successKilledDao.insertSuccessKill(seckillId,userphone,new Date());
            if(insertCount<=0){//若添加失败则说明添加重复，因为表中采用seckillId和userphone作为联合主键
                throw new RepeatKillException("repeat");
            }else{
                //秒杀成功
                int insertNum=seckillDao.reduceNum(seckillId,nowTime);
                if(insertNum<=0){
                    throw new SeckillCloseException("finished");
                }
                //减库存
                SuccessKilled successKilled=successKilledDao.queryByIdWithSecKill(seckillId,userphone);
                return new SeckillExecution(seckillId, SeckillStateEnums.SUCCESS,successKilled);
            }
        } catch (SeckillCloseException e) {
            throw e;
        } catch (RepeatKillException e) {
            throw e;
        }catch (Exception e){
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }
    }

    @Override
    public SeckillExecution excuteSeckillProcedure(long seckillId, long userphone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5==null ||! md5.equals(getMD5(seckillId))){
            throw new SeckillException("md5 rewrite");
        }

        Date nowTime=new Date();
        Map<String,Object> map=new HashMap<>();
        map.put("seckillId",seckillId);
        map.put("phone",userphone);
        map.put("killTime",nowTime);
        map.put("result",null);
        try {
            seckillDao.killByProcedure(map);
            int result=MapUtils.getInteger(map,"result",-2);
            if(result==1){
                SuccessKilled successKilled=successKilledDao.queryByIdWithSecKill(seckillId,userphone);
                return new SeckillExecution(seckillId, SeckillStateEnums.SUCCESS,successKilled);
            }else {
                return new SeckillExecution(seckillId,SeckillStateEnums.getState(result));
            }
        } catch (Exception e) {
            return new SeckillExecution(seckillId, SeckillStateEnums.INNER_ERROR);
        }

    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+salt;
        //采用spring工具包中的md5加密
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return  md5;
    }
}
