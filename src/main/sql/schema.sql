-- 数据库初始化脚本

-- 库存表 scekill
CREATE TABLE seckill (
  seckillId  BIGINT      NOT NULL,
  name        VARCHAR(20) NOT NULL
  COMMENT '商品名字',
  number      INT         NOT NULL
  COMMENT '库存数量',
  create_time TIMESTAMP   NOT NULL DEFAULT current_timestamp
  COMMENT '创建时间',
  start_time  TIMESTAMP   NOT NULL
  COMMENT '秒杀开始时间',
  end_time    TIMESTAMP   NOT NULL
  COMMENT '秒杀结束时间',
  PRIMARY KEY (seckillId),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)
  AUTO_INCREMENT = 1000
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '秒杀库存表';

-- 初始化数据
INSERT INTO seckill (name, number, start_time, end_time)
VALUES ('1000元秒杀ipad', 100, '2017-08-05 00:00:00', '2017-08-06 00:00:00'),
  ('2000元秒杀MBP', 200, '2017-08-05 00:00:00', '2017-08-06 00:00:00'),
  ('500元秒杀ipod', 300, '2017-08-05 00:00:00', '2017-08-06 00:00:00'),
  ('1500元秒杀iphone', 400, '2017-08-05 00:00:00', '2017-08-06 00:00:00');

-- 秒杀成功明细表
CREATE TABLE success_killed (
  seckillId  BIGINT  NOT NULL
  COMMENT '秒杀商品id',
  user_phone  BIGINT  NOT NULL
  COMMENT '秒杀用户',
  state       TINYINT NOT NULL DEFAULT -1
  COMMENT '-1：无效，0：成功，1：付款',
  create_time TIMESTAMP        DEFAULT current_timestamp,
  PRIMARY KEY (seckillId, user_phone),
  KEY idex_create_time(create_time)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '秒杀成功明细表';