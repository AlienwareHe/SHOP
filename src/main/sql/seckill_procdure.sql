-- 将插入明细和更新库存放入存储过程中
--
  DELIMITER $$ -- ; console 将;分号转换，因为存储过程中用到了;
-- 定义存储过程
-- in为输入参数 out为输出参数
-- row_count 返回上一条修改类型语句影响行数

CREATE PROCEDURE `seckill`.`execute_seckill`
  (in v_seckill_id BIGINT,in v_phone BIGINT,in v_kill_time TIMESTAMP,out r_result INT)
  BEGIN
    DECLARE count int DEFAULT 0;
    START TRANSACTION ;
    INSERT IGNORE INTO success_killed (seckill_id,user_phone,create_time)
      VALUES (v_seckill_id,v_phone,v_kill_time);
    SELECT row_count() INTO count;
    IF (count=0) THEN
      ROLLBACK;
      set r_result=0;
    ELSEIF (count<1) THEN
      ROLLBACK ;
      SET r_result=-3;
    ELSE
      update seckill
        set number=number-1 where seckill_id=v_seckill_id and end_time>v_kill_time  and start_time<v_kill_time and number>0;
      select row_count() into count;
      IF (count=0) THEN
        ROLLBACK;
        set r_result=0;
      ELSEIF (count<1) THEN
        ROLLBACK ;
        set r_result=-3;
      ELSE
        COMMIT ;
        set r_result=1;
      END IF;
    END IF;
  END;
$$
-- 存储过程结束
-- 1.存储过程优化了行级锁持有时间，此处可以提供一个秒杀单6000的qps。
-- 2.不要过度的依赖存储过程。