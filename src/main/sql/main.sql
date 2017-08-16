-- 不采用外键的原因是防止以后分库分表或清洗数据时造成不必要的麻烦
-- create_time update_time 查业务问题
-- 创建用户表
create table user(
  id int(11) not NULL AUTO_INCREMENT COMMENT '用户表id',
  username VARCHAR(50) not NULL COMMENT '用户名',
  password VARCHAR(50) NOT NULL COMMENT '密码，md5加密',
  email VARCHAR(50) NOT NULL COMMENT '邮箱',
  phone VARCHAR(20) NOT NULL COMMENT '手机',
  question VARCHAR(50) NOT NULL COMMENT '找回密码问题',
  answer VARCHAR(50) NOT NULL COMMENT '找回密码问题答案',
  role INT(4) not NULL  COMMENT '角色：0-管理员，1-普通用户',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  update_time DATETIME NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `username_unique` (username) using BTREE

)ENGINE =InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET =utf8;

-- 分类表
CREATE TABLE category(
  id INT(11) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  parent_id INT DEFAULT NULL COMMENT '当父类别id是0是说明是根节点',
  name VARCHAR(50) DEFAULT  NULL COMMENT '类别名称',
  status TINYINT(1) DEFAULT '1'COMMENT '类别状态 1-正常，2-已废弃',
  sort_order int(4) DEFAULT NULL COMMENT '排序编号，同类展示顺序，数值相等则自然排序',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL  COMMENT '更新时间',
  PRIMARY KEY(id)
)ENGINE = InnoDB AUTO_INCREMENT=1000032 DEFAULT CHARSET =utf8;

-- 商品表
CREATE TABLE product (
  id INT NOT NULL AUTO_INCREMENT COMMENT '商品id',
  category_id INT NOT NULL COMMENT '分类id，对应category主键',
  name VARCHAR(100) NOT NULL COMMENT '商品名称',
  subtitle VARCHAR(200) NOT NULL COMMENT '商品副标题',
  main_image VARCHAR(500) DEFAULT NULL COMMENT '主图，存放为url相对地址',
  -- 主图为子图第一张图片地址，主要是因为在没有缓存情况下为了提高性能可以增加一点冗余性，这样在前台频繁获取时只需获取主图即可，而不用将所有子图查出后再取第一张图片
  sub_images TEXT COMMENT '子图图片地址，json格式，扩展用',
  detail TEXT COMMENT '商品详情',
  price DECIMAL(20,2) NOT NULL COMMENT '价格，单位为元，保留两位小数,18位整数',
  -- java会利用bigdecimal处理这个字段
  stock INT NOT NULL  NULL COMMENT '库存数据',
  status INT(6) DEFAULT '1' COMMENT '商品状态，1-在售，2-下架，3-删除',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL  COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE = InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET =utf8;

-- 购物车表
CREATE TABLE cart(
  id INT NOT NULL AUTO_INCREMENT COMMENT '购物车表id',
  user_id INT NOT NULL COMMENT '用户id',
  product_id int DEFAULT NULL COMMENT '商品id',
  quantity int DEFAULT NULL COMMENT '商品数量',
  checked int DEFAULT NULL COMMENT '是否选择，1=选择，0=未选',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL  COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY user_id_index (user_id) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET =utf8;

-- 支付信息表
CREATE TABLE pay_info(
  id INT NOT NULL AUTO_INCREMENT,
  user_id int DEFAULT NULL COMMENT '用户id',
  order_no BIGINT DEFAULT NULL COMMENT '订单号',
  pay_platform INT DEFAULT NULL COMMENT '支付平台：1-支付宝 2-微信',
  platform_number VARCHAR(200) DEFAULT NULL COMMENT '支付流水号',
  platform_status VARCHAR(20) DEFAULT NULL COMMENT '支付状态',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL  COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE = InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET =utf8;

-- 订单表
CREATE TABLE order(
  id INT NOT NULL AUTO_INCREMENT,
  user_id int DEFAULT NULL COMMENT '用户id',
  order_no BIGINT(20) DEFAULT NULL COMMENT '订单号',
  shipping_id INT DEFAULT NULL ,
  payment DECIMAL(20,2) DEFAULT  NULL COMMENT '实际付款金额',
  payment_type INT DEFAULT NULL  COMMENT '支付类型：1-在线支付',
  postage INT DEFAULT NULL COMMENT '运费，单位元',
  status INT DEFAULT NULL COMMENT '订单状态：0-已取消 10-未付款 20-已付款 40-已发货 50-交易成功 60-交易关闭',
  payment_time DATETIME DEFAULT  NULL COMMENT '支付时间',
  send_time DATETIME DEFAULT NULL COMMENT '发货时间',
  close_time DATETIME DEFAULT NULL COMMENT '交易关闭时间',
  end_time DATETIME DEFAULT NULL COMMENT '交易完成时间',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (id),
  UNIQUE KEY order_no_index (order_no) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET =utf8;

-- 订单明细表
CREATE TABLE order_item (
  id INT NOT NULL AUTO_INCREMENT,
  user_id int DEFAULT NULL COMMENT '用户id',
  order_no BIGINT(20) DEFAULT NULL COMMENT '订单号',
  product_id int DEFAULT NULL COMMENT '商品id',
  product_name VARCHAR(100) DEFAULT NULL COMMENT '商品名称',
  product_image VARCHAR(500) DEFAULT NULL COMMENT '商品图片地址',
  current_unit_price decimal(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元',
  quantity INT DEFAULT  NULL COMMENT '商品数量',
  total_price DECIMAL(20,2) DEFAULT NULL COMMENT '商品总价',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id),
  key order_no_index (order_no) USING BTREE ,
  key order_no_user_id_index (user_id,order_no) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET =utf8;
-- 收获地址表
CREATE TABLE shipping(
  id INT NOT NULL AUTO_INCREMENT,
  user_id int DEFAULT NULL COMMENT '用户id',
  receiver_name VARCHAR(20) DEFAULT NULL COMMENT '收货姓名',
  receiver_phone VARCHAR(20) DEFAULT NULL COMMENT '收货固定电话',
  receiver_mobile VARCHAR(20) DEFAULT NULL COMMENT '收货移动电话',
  receiver_province VARCHAR(20) DEFAULT NULL COMMENT '省份',
  receiver_city VARCHAR(20) DEFAULT NULL COMMENT '城市',
  receiver_district VARCHAR(20) DEFAULT NULL COMMENT '区/县',
  receiver_address VARCHAR(200) DEFAULT NULL COMMENT '详细地址',
  receiver_zip VARCHAR(6) DEFAULT NULL COMMENT '邮编',
  create_time DATETIME DEFAULT NULL COMMENT '创建时间',
  update_time DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE = InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET =utf8;