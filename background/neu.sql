-- MySQL dump 10.13  Distrib 8.0.41, for macos15 (arm64)
--
-- Host: localhost    Database: neu_fund_advisory
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `capital_flow`
--

DROP TABLE IF EXISTS `capital_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `capital_flow` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `flow_no` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '流水编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `flow_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '流水类型：DEPOSIT-充值，WITHDRAW-提现，BUY-买入，SELL-卖出，FEE-手续费，DIVIDEND-分红',
  `amount` decimal(15,2) NOT NULL COMMENT '流水金额',
  `balance_before` decimal(15,2) DEFAULT NULL COMMENT '流水前余额',
  `balance_after` decimal(15,2) DEFAULT NULL COMMENT '流水后余额',
  `order_id` bigint DEFAULT NULL COMMENT '关联订单ID',
  `product_id` bigint DEFAULT NULL COMMENT '关联产品ID',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '流水状态：SUCCESS-成功，FAILED-失败，PENDING-处理中',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流水备注',
  `flow_time` timestamp NOT NULL COMMENT '流水时间',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `flow_no` (`flow_no`),
  KEY `fk_flow_order` (`order_id`),
  KEY `fk_flow_product` (`product_id`),
  KEY `idx_flow_user` (`user_id`),
  KEY `idx_flow_type` (`flow_type`),
  KEY `idx_flow_time` (`flow_time`),
  KEY `idx_flow_user_time` (`user_id`,`flow_time`),
  CONSTRAINT `fk_flow_order` FOREIGN KEY (`order_id`) REFERENCES `trade_order` (`id`),
  CONSTRAINT `fk_flow_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资金流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `capital_flow`
--

LOCK TABLES `capital_flow` WRITE;
/*!40000 ALTER TABLE `capital_flow` DISABLE KEYS */;
INSERT INTO `capital_flow` VALUES (1,'FLOW202401150001',1001,'BUY',-100000.00,500000.00,400000.00,1,1,'SUCCESS','购买价值投资产品','2024-01-15 01:35:00','2025-06-28 13:00:39'),(2,'FLOW202401150002',1002,'BUY',-150000.00,800000.00,650000.00,2,2,'SUCCESS','购买成长投资产品','2024-01-15 02:20:00','2025-06-28 13:00:39'),(3,'FLOW202401150003',1003,'BUY',-50000.00,200000.00,150000.00,3,3,'SUCCESS','购买FOF稳健产品','2024-01-15 03:05:00','2025-06-28 13:00:39'),(4,'FLOW202401150004',1001,'BUY',-50000.00,400000.00,350000.00,4,1,'SUCCESS','追加购买价值投资产品','2024-01-15 06:35:00','2025-06-28 13:00:39'),(5,'FLOW202401150005',1002,'SELL',50000.00,650000.00,700000.00,5,2,'SUCCESS','赎回成长投资产品','2024-01-15 07:50:00','2025-06-28 13:00:39'),(6,'FLOW202401150006',1001,'FEE',-1000.00,350000.00,349000.00,1,1,'SUCCESS','购买手续费','2024-01-15 01:35:00','2025-06-28 13:00:39'),(7,'FLOW202401150007',1002,'FEE',-1500.00,700000.00,698500.00,2,2,'SUCCESS','购买手续费','2024-01-15 02:20:00','2025-06-28 13:00:39'),(8,'FLOW202401150008',1003,'FEE',-400.00,150000.00,149600.00,3,3,'SUCCESS','购买手续费','2024-01-15 03:05:00','2025-06-28 13:00:39'),(9,'FLOW202401150009',1001,'FEE',-500.00,349000.00,348500.00,4,1,'SUCCESS','购买手续费','2024-01-15 06:35:00','2025-06-28 13:00:39'),(10,'FLOW202401150010',1002,'FEE',-250.00,698500.00,698250.00,5,2,'SUCCESS','赎回手续费','2024-01-15 07:50:00','2025-06-28 13:00:39');
/*!40000 ALTER TABLE `capital_flow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_order`
--

DROP TABLE IF EXISTS `delivery_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(15,2) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `fee` decimal(10,2) DEFAULT NULL,
  `order_id` bigint NOT NULL,
  `price` decimal(10,4) DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `shares` decimal(15,4) DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trade_time` datetime(6) NOT NULL,
  `trade_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_order`
--

LOCK TABLES `delivery_order` WRITE;
/*!40000 ALTER TABLE `delivery_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor`
--

DROP TABLE IF EXISTS `factor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factor` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `factor_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `factor_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `factor_category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `factor_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `calculation_formula` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `data_source` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_frequency` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `factor_code` (`factor_code`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='因子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor`
--

LOCK TABLES `factor` WRITE;
/*!40000 ALTER TABLE `factor` DISABLE KEYS */;
INSERT INTO `factor` VALUES (1,'F001','成立日期','基本面','日期','基金成立日期',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(2,'F002','发行机构','基本面','字符串','基金发行机构',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(3,'F003','阶段涨跌幅','业绩','数值','近一阶段涨跌幅',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(4,'F004','基金规模','规模','数值','基金当前规模',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(5,'F005','夏普比率','风险','数值','夏普比率',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(6,'F006','最大回撤','风险','数值','历史最大回撤',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(7,'F007','波动率','风险','数值','年化波动率',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(8,'F008','成长风格','风格','数值','成长风格因子',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(9,'F009','价值风格','风格','数值','价值风格因子',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(10,'F010','大盘风格','风格','数值','大盘风格因子',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(11,'COMPOSITE_1751298530787','衍生1','复合因子','加权','由多个基础因子加权生成复合因子',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL,NULL,NULL),(12,'COMPOSITE_1751698316551','test1','复合因子','加权','由多个基础因子加权生成复合因子',NULL,NULL,NULL,'active',NULL,NULL,NULL,NULL,NULL,NULL),(13,'COMPOSITE_1751698508736','衍生测试1','复合因子','加权','由多个基础因子加权生成复合因子',NULL,NULL,NULL,'active',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `factor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_data`
--

DROP TABLE IF EXISTS `factor_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factor_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `factor_id` bigint NOT NULL COMMENT '因子ID',
  `fund_id` bigint NOT NULL COMMENT '基金ID',
  `factor_value` double DEFAULT NULL,
  `data_date` date NOT NULL COMMENT '数据日期',
  `data_source` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `data_quality` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据质量',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `value` double DEFAULT NULL,
  PRIMARY KEY (`id`,`data_date`),
  UNIQUE KEY `uk_factor_fund_date` (`factor_id`,`fund_id`,`data_date`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='因子数据表'
/*!50100 PARTITION BY RANGE (((year(`data_date`) * 100) + month(`data_date`)))
(PARTITION p202401 VALUES LESS THAN (202402) ENGINE = InnoDB,
 PARTITION p202402 VALUES LESS THAN (202403) ENGINE = InnoDB,
 PARTITION p202403 VALUES LESS THAN (202404) ENGINE = InnoDB,
 PARTITION p202404 VALUES LESS THAN (202405) ENGINE = InnoDB,
 PARTITION p202405 VALUES LESS THAN (202406) ENGINE = InnoDB,
 PARTITION p202406 VALUES LESS THAN (202407) ENGINE = InnoDB,
 PARTITION p202407 VALUES LESS THAN (202408) ENGINE = InnoDB,
 PARTITION p202408 VALUES LESS THAN (202409) ENGINE = InnoDB,
 PARTITION p202409 VALUES LESS THAN (202410) ENGINE = InnoDB,
 PARTITION p202410 VALUES LESS THAN (202411) ENGINE = InnoDB,
 PARTITION p202411 VALUES LESS THAN (202412) ENGINE = InnoDB,
 PARTITION p202412 VALUES LESS THAN (202501) ENGINE = InnoDB,
 PARTITION p_future VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_data`
--

LOCK TABLES `factor_data` WRITE;
/*!40000 ALTER TABLE `factor_data` DISABLE KEYS */;
INSERT INTO `factor_data` VALUES (1,1,1,15.23,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(2,2,1,2.45,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(3,3,1,0.185,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(4,7,1,1.12,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(5,8,1,0.245,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(6,1,2,12.67,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(7,2,2,1.89,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(8,3,2,0.156,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(9,7,2,0.98,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(10,8,2,0.198,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(11,1,3,18.45,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(12,2,3,3.12,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(13,3,3,0.203,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(14,7,3,1.25,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(15,8,3,0.267,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(16,1,4,14.78,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(17,2,4,2.23,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(18,3,4,0.167,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(19,7,4,1.08,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(20,8,4,0.223,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(21,1,5,13.92,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(22,2,5,2.67,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(23,3,5,0.178,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(24,7,5,1.15,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL),(25,8,5,0.234,'2024-01-15','Wind','HIGH','ACTIVE','2025-06-28 12:58:40','2025-06-28 12:58:40',NULL);
/*!40000 ALTER TABLE `factor_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_tree`
--

DROP TABLE IF EXISTS `factor_tree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factor_tree` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tree_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tree_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tree_description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `root_node_id` bigint DEFAULT NULL COMMENT '根节点ID',
  `tree_structure` text COLLATE utf8mb4_unicode_ci COMMENT '树结构JSON',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tree_code` (`tree_code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='因子树表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_tree`
--

LOCK TABLES `factor_tree` WRITE;
/*!40000 ALTER TABLE `factor_tree` DISABLE KEYS */;
INSERT INTO `factor_tree` VALUES (11,'测试','1',NULL,NULL,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL,NULL),(12,'tree1',NULL,'test1',NULL,NULL,'active',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `factor_tree` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_tree_node`
--

DROP TABLE IF EXISTS `factor_tree_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factor_tree_node` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tree_id` bigint NOT NULL COMMENT '因子树ID',
  `node_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `node_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `factor_id` bigint DEFAULT NULL COMMENT '关联因子ID',
  `operator` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '运算符：AND, OR, NOT',
  `parent_id` bigint DEFAULT NULL COMMENT '父节点ID',
  `node_order` int DEFAULT '0' COMMENT '节点顺序',
  `weight` double DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_node_tree` (`tree_id`),
  KEY `fk_node_factor` (`factor_id`),
  KEY `fk_node_parent` (`parent_id`),
  CONSTRAINT `fk_node_factor` FOREIGN KEY (`factor_id`) REFERENCES `factor` (`id`),
  CONSTRAINT `fk_node_parent` FOREIGN KEY (`parent_id`) REFERENCES `factor_tree_node` (`id`),
  CONSTRAINT `fk_node_tree` FOREIGN KEY (`tree_id`) REFERENCES `factor_tree` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='因子树节点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_tree_node`
--

LOCK TABLES `factor_tree_node` WRITE;
/*!40000 ALTER TABLE `factor_tree_node` DISABLE KEYS */;
INSERT INTO `factor_tree_node` VALUES (108,11,'成立日期','因子',1,NULL,NULL,0,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL),(109,11,'发行机构','因子',2,NULL,NULL,0,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL),(110,11,'阶段涨跌幅','因子',3,NULL,NULL,0,NULL,'active','2025-07-01 07:52:30','2025-07-01 07:52:30',NULL),(111,12,'成立日期','因子',1,NULL,NULL,NULL,NULL,'active',NULL,NULL,NULL),(112,12,'发行机构','因子',2,NULL,NULL,NULL,NULL,'active',NULL,NULL,NULL);
/*!40000 ALTER TABLE `factor_tree_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fof_portfolio`
--

DROP TABLE IF EXISTS `fof_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fof_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `portfolio_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组合名称',
  `portfolio_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组合代码',
  `portfolio_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组合类型',
  `risk_level` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '风险等级',
  `target_return` decimal(8,4) DEFAULT NULL COMMENT '目标收益率',
  `max_drawdown` decimal(8,4) DEFAULT NULL COMMENT '最大回撤',
  `investment_horizon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投资期限',
  `min_investment` decimal(10,2) DEFAULT NULL COMMENT '最小投资金额',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-正常，INACTIVE-停用',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `portfolio_code` (`portfolio_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='FOF组合表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fof_portfolio`
--

LOCK TABLES `fof_portfolio` WRITE;
/*!40000 ALTER TABLE `fof_portfolio` DISABLE KEYS */;
INSERT INTO `fof_portfolio` VALUES (1,'稳健FOF组合','FOF_STABLE001','STABLE','LOW',0.0800,0.0800,'3-5年',50000.00,'稳健型FOF组合，适合保守型投资者','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(2,'平衡FOF组合','FOF_BALANCED001','BALANCED','MEDIUM',0.1200,0.1200,'5-8年',50000.00,'平衡型FOF组合，适合平衡型投资者','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(3,'进取FOF组合','FOF_AGGRESSIVE001','AGGRESSIVE','HIGH',0.1600,0.2000,'8-10年',50000.00,'进取型FOF组合，适合激进型投资者','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL);
/*!40000 ALTER TABLE `fof_portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund`
--

DROP TABLE IF EXISTS `fund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fund_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '基金代码',
  `fund_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '基金名称',
  `company_id` bigint DEFAULT NULL COMMENT '基金公司ID',
  `manager_id` bigint DEFAULT NULL COMMENT '基金经理ID',
  `inception_date` date DEFAULT NULL COMMENT '成立日期',
  `fund_size` decimal(38,2) DEFAULT NULL,
  `nav` decimal(38,2) DEFAULT NULL,
  `nav_date` date DEFAULT NULL COMMENT '净值日期',
  `risk_level` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `investment_strategy` text COLLATE utf8mb4_unicode_ci COMMENT '投资策略',
  `benchmark` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '业绩基准',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fund_code` (`fund_code`),
  KEY `idx_fund_code` (`fund_code`),
  KEY `idx_fund_company` (`company_id`),
  KEY `idx_fund_manager` (`manager_id`),
  KEY `idx_fund_status` (`status`),
  KEY `idx_fund_nav_date` (`nav_date`),
  CONSTRAINT `fk_fund_company` FOREIGN KEY (`company_id`) REFERENCES `fund_company` (`id`),
  CONSTRAINT `fk_fund_manager` FOREIGN KEY (`manager_id`) REFERENCES `fund_manager` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基金信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund`
--

LOCK TABLES `fund` WRITE;
/*!40000 ALTER TABLE `fund` DISABLE KEYS */;
INSERT INTO `fund` VALUES (1,'110001','华夏成长混合',1,1,'2004-09-01',120.50,2.35,'2024-05-01','HIGH','成长+价值均衡配置','沪深300','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','110001','华夏成长混合','HYBRID'),(2,'110002','华夏大盘精选',1,2,'2005-03-15',98.30,3.12,'2024-05-01','HIGH','精选大盘蓝筹','上证50','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','110002','华夏大盘精选','STOCK'),(3,'161005','易方达消费行业',2,3,'2007-07-20',80.00,1.99,'2024-05-01','MEDIUM','消费行业精选','中证消费','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','161005','易方达消费行业','STOCK'),(4,'000001','易方达上证50',2,4,'2010-01-10',150.00,1.23,'2024-05-01','MEDIUM','指数增强策略','上证50','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','000001','易方达上证50','INDEX'),(5,'202301','南方成长先锋',3,5,'2012-05-18',60.00,2.88,'2024-05-01','HIGH','成长股精选','创业板指','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','202301','南方成长先锋','STOCK'),(6,'202302','南方价值精选',3,5,'2013-08-22',70.00,2.46,'2024-05-01','MEDIUM','价值蓝筹配置','沪深300','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','202302','南方价值精选','STOCK'),(7,'008888','华夏中小盘混合',1,2,'2015-11-11',40.00,1.77,'2024-05-01','MEDIUM','中小盘成长','中证500','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','008888','华夏中小盘混合','HYBRID'),(8,'009999','易方达科技创新',2,4,'2018-06-30',55.00,2.12,'2024-05-01','HIGH','科技创新主题','科创50','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','009999','易方达科技创新','STOCK'),(9,'100038','华夏蓝筹核心',1,1,'2016-02-28',110.00,2.65,'2024-05-01','LOW','蓝筹核心配置','沪深300','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','100038','华夏蓝筹核心','STOCK'),(10,'501018','南方中证500ETF',3,5,'2019-09-09',200.00,1.54,'2024-05-01','LOW','指数增强','中证500','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','501018','南方中证500ETF','INDEX');
/*!40000 ALTER TABLE `fund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_company`
--

DROP TABLE IF EXISTS `fund_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_company` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `company_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公司代码',
  `company_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公司名称',
  `company_short_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司简称',
  `establishment_date` date DEFAULT NULL COMMENT '成立日期',
  `registered_capital` decimal(38,2) DEFAULT NULL,
  `legal_representative` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '法定代表人',
  `contact_phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系邮箱',
  `website` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公司网站',
  `address` text COLLATE utf8mb4_unicode_ci COMMENT '公司地址',
  `business_license` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '营业执照号',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-正常，INACTIVE-停用',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `admin_user_id` bigint DEFAULT NULL COMMENT '公司管理员用户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_code` (`company_code`),
  KEY `fk_company_admin` (`admin_user_id`),
  CONSTRAINT `fk_company_admin` FOREIGN KEY (`admin_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基金公司表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_company`
--

LOCK TABLES `fund_company` WRITE;
/*!40000 ALTER TABLE `fund_company` DISABLE KEYS */;
INSERT INTO `fund_company` VALUES (1,'C001','华夏基金','华夏','2001-04-01',50000.00,'王总','010-88888888','contact@huaxia.com','https://www.huaxia.com','北京市朝阳区建国路88号','BL123456','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','国内领先的基金公司','华夏基金管理有限公司',1),(2,'C002','易方达基金','易方达','2002-06-15',60000.00,'李总','020-99999999','service@efunds.com','https://www.efunds.com','广州市天河区珠江新城1号','BL654321','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','专注指数与主动管理','易方达基金管理有限公司',1),(3,'C003','南方基金','南方','2003-09-20',55000.00,'张总','0755-12345678','info@southernfund.com','https://www.southernfund.com','深圳市福田区深南大道100号','BL789012','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','创新驱动成长','南方基金管理有限公司',1);
/*!40000 ALTER TABLE `fund_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_manager`
--

DROP TABLE IF EXISTS `fund_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_manager` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `manager_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '经理代码',
  `manager_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '经理姓名',
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `birth_date` date DEFAULT NULL COMMENT '出生日期',
  `education` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学历',
  `experience_years` int DEFAULT NULL COMMENT '从业年限',
  `company_id` bigint DEFAULT NULL COMMENT '所属公司ID',
  `introduction` text COLLATE utf8mb4_unicode_ci COMMENT '个人简介',
  `investment_philosophy` text COLLATE utf8mb4_unicode_ci COMMENT '投资理念',
  `awards` text COLLATE utf8mb4_unicode_ci COMMENT '获奖情况',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-正常，INACTIVE-停用',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `profile` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` bigint DEFAULT NULL COMMENT '关联用户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `manager_code` (`manager_code`),
  KEY `fk_manager_company` (`company_id`),
  KEY `fk_manager_user` (`user_id`),
  CONSTRAINT `fk_manager_company` FOREIGN KEY (`company_id`) REFERENCES `fund_company` (`id`),
  CONSTRAINT `fk_manager_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基金经理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_manager`
--

LOCK TABLES `fund_manager` WRITE;
/*!40000 ALTER TABLE `fund_manager` DISABLE KEYS */;
INSERT INTO `fund_manager` VALUES (1,'M001','张三','男','1980-01-01','硕士',15,1,'20年证券从业经验，擅长成长股投资。','价值成长并重','金牛奖','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','张三','明星基金经理',1),(2,'M002','李四','女','1982-05-12','博士',12,1,'专注大盘蓝筹，风格稳健。','稳健为主','三年优秀基金经理','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','李四','大盘蓝筹专家',1),(3,'M003','王五','男','1978-09-23','本科',18,2,'指数增强策略专家。','量化投资','指数之星','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','王五','指数增强达人',1),(4,'M004','赵六','女','1985-11-30','硕士',10,2,'科技主题投资新锐。','科技创新','新锐基金经理','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','赵六','科技主题新星',1),(5,'M005','钱七','男','1975-03-15','硕士',20,3,'消费与成长领域资深专家。','消费成长','十年金牛奖','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','钱七','消费成长专家',1);
/*!40000 ALTER TABLE `fund_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_portfolio`
--

DROP TABLE IF EXISTS `fund_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `portfolio_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组合名称',
  `portfolio_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组合代码',
  `portfolio_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组合类型',
  `risk_level` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '风险等级',
  `target_return` decimal(38,2) DEFAULT NULL,
  `max_drawdown` decimal(38,2) DEFAULT NULL,
  `investment_horizon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投资期限',
  `min_investment` decimal(38,2) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-正常，INACTIVE-停用',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `portfolio_code` (`portfolio_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基金组合表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_portfolio`
--

LOCK TABLES `fund_portfolio` WRITE;
/*!40000 ALTER TABLE `fund_portfolio` DISABLE KEYS */;
INSERT INTO `fund_portfolio` VALUES (1,'进取型组合','P001','激进型','HIGH',0.20,0.15,'1年',10000.00,'追求高收益，波动较大','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','进取型组合',1),(2,'稳健型组合','P002','平衡型','MEDIUM',0.12,0.08,'2年',5000.00,'风险适中，收益稳定','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','稳健型组合',1),(3,'指数增强组合','P003','指数型','LOW',0.08,0.05,'3年',2000.00,'以指数增强为主','ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','指数增强组合',1);
/*!40000 ALTER TABLE `fund_portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_portfolio_relation`
--

DROP TABLE IF EXISTS `fund_portfolio_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_portfolio_relation` (
  `portfolio_id` bigint NOT NULL,
  `fund_id` bigint NOT NULL,
  KEY `FKgrt2av3qcwfrhy411fqu2vkfo` (`fund_id`),
  KEY `FK30q189fhdq6b1ntiav75avsue` (`portfolio_id`),
  CONSTRAINT `FK30q189fhdq6b1ntiav75avsue` FOREIGN KEY (`portfolio_id`) REFERENCES `fund_portfolio` (`id`),
  CONSTRAINT `FKgrt2av3qcwfrhy411fqu2vkfo` FOREIGN KEY (`fund_id`) REFERENCES `fund` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_portfolio_relation`
--

LOCK TABLES `fund_portfolio_relation` WRITE;
/*!40000 ALTER TABLE `fund_portfolio_relation` DISABLE KEYS */;
INSERT INTO `fund_portfolio_relation` VALUES (1,1),(1,5),(1,8),(2,2),(2,3),(2,9),(3,4),(3,10);
/*!40000 ALTER TABLE `fund_portfolio_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_tag`
--

DROP TABLE IF EXISTS `fund_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tag_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tag_category` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签分类',
  `tag_description` text COLLATE utf8mb4_unicode_ci COMMENT '标签描述',
  `tag_color` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签颜色',
  `sort_order` int DEFAULT '0' COMMENT '排序',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-正常，INACTIVE-停用',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基金标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_tag`
--

LOCK TABLES `fund_tag` WRITE;
/*!40000 ALTER TABLE `fund_tag` DISABLE KEYS */;
INSERT INTO `fund_tag` VALUES (1,'价值投资','STYLE','价值风格','#1890FF',1,'ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','偏重低估值蓝筹','价值投资'),(2,'成长投资','STYLE','成长风格','#52C41A',2,'ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','关注高成长行业','成长投资'),(3,'消费主题','THEME','消费行业','#FA8C16',3,'ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','消费板块相关','消费主题'),(4,'科技主题','THEME','科技行业','#722ED1',4,'ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','科技创新相关','科技主题'),(5,'大盘蓝筹','SIZE','大盘蓝筹','#FAAD14',5,'ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','大市值蓝筹','大盘蓝筹'),(6,'中小盘','SIZE','中小盘','#A0D911',6,'ACTIVE','2025-07-04 08:49:03','2025-07-04 08:49:03','中小市值公司','中小盘');
/*!40000 ALTER TABLE `fund_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_tag_relation`
--

DROP TABLE IF EXISTS `fund_tag_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_tag_relation` (
  `fund_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  KEY `FK5xidseiga21mr17erimbm2alx` (`tag_id`),
  KEY `FKhu9uieyy5gckldvgycycjlx19` (`fund_id`),
  CONSTRAINT `FK5xidseiga21mr17erimbm2alx` FOREIGN KEY (`tag_id`) REFERENCES `fund_tag` (`id`),
  CONSTRAINT `FKhu9uieyy5gckldvgycycjlx19` FOREIGN KEY (`fund_id`) REFERENCES `fund` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_tag_relation`
--

LOCK TABLES `fund_tag_relation` WRITE;
/*!40000 ALTER TABLE `fund_tag_relation` DISABLE KEYS */;
INSERT INTO `fund_tag_relation` VALUES (1,2),(2,1),(2,5),(3,3),(3,2),(4,5),(5,2),(6,1),(7,6),(8,4),(9,1),(9,5),(10,5);
/*!40000 ALTER TABLE `fund_tag_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `index_portfolio`
--

DROP TABLE IF EXISTS `index_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `index_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `portfolio_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组合名称',
  `portfolio_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组合代码',
  `index_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '指数代码',
  `index_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '指数名称',
  `portfolio_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组合类型',
  `risk_level` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '风险等级',
  `target_return` decimal(8,4) DEFAULT NULL COMMENT '目标收益率',
  `max_drawdown` decimal(8,4) DEFAULT NULL COMMENT '最大回撤',
  `investment_horizon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投资期限',
  `min_investment` decimal(10,2) DEFAULT NULL COMMENT '最小投资金额',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-正常，INACTIVE-停用',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `portfolio_code` (`portfolio_code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基金指数组合表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `index_portfolio`
--

LOCK TABLES `index_portfolio` WRITE;
/*!40000 ALTER TABLE `index_portfolio` DISABLE KEYS */;
INSERT INTO `index_portfolio` VALUES (1,'沪深300指数组合','INDEX_HS300001','000300','沪深300指数','INDEX','MEDIUM',0.1000,0.1500,'5-10年',10000.00,'跟踪沪深300指数的组合','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(2,'中证500指数组合','INDEX_ZZ500001','000905','中证500指数','INDEX','HIGH',0.1200,0.2000,'5-10年',10000.00,'跟踪中证500指数的组合','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(3,'创业板指数组合','INDEX_CYB001','399006','创业板指数','INDEX','HIGH',0.1500,0.2500,'5-10年',10000.00,'跟踪创业板指数的组合','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(4,'消费指数组合','INDEX_CONSUMER001','000932','中证消费指数','THEME','MEDIUM',0.1300,0.1800,'3-5年',10000.00,'跟踪消费指数的组合','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(5,'科技指数组合','INDEX_TECH001','000986','中证科技指数','THEME','HIGH',0.1600,0.2500,'5-8年',10000.00,'跟踪科技指数的组合','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL);
/*!40000 ALTER TABLE `index_portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品代码',
  `product_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产品名称',
  `product_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '产品类型',
  `strategy_id` bigint DEFAULT NULL COMMENT '关联策略ID',
  `portfolio_id` bigint DEFAULT NULL COMMENT '关联组合ID',
  `risk_level` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `target_return` decimal(38,2) DEFAULT NULL,
  `max_drawdown` decimal(38,2) DEFAULT NULL,
  `investment_horizon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投资期限',
  `min_investment` decimal(38,2) DEFAULT NULL,
  `max_investment` decimal(38,2) DEFAULT NULL,
  `management_fee` decimal(38,2) DEFAULT NULL,
  `performance_fee` decimal(38,2) DEFAULT NULL,
  `subscription_fee` decimal(38,2) DEFAULT NULL,
  `redemption_fee` decimal(38,2) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prospectus` text COLLATE utf8mb4_unicode_ci COMMENT '产品说明书',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_code` (`product_code`),
  KEY `fk_product_strategy` (`strategy_id`),
  KEY `fk_product_portfolio` (`portfolio_id`),
  CONSTRAINT `fk_product_portfolio` FOREIGN KEY (`portfolio_id`) REFERENCES `fund_portfolio` (`id`),
  CONSTRAINT `fk_product_strategy` FOREIGN KEY (`strategy_id`) REFERENCES `strategy` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='组合产品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'PROD001','价值投资产品A','STRATEGY',1,NULL,'MEDIUM',0.12,0.15,'3-5年',100000.00,10000000.00,0.02,0.20,0.01,0.01,'基于价值投资策略的产品，适合稳健型投资者','产品说明书详细内容...','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(2,'PROD002','成长投资产品B','STRATEGY',2,NULL,'HIGH',0.15,0.20,'5-10年',100000.00,10000000.00,0.02,0.20,0.01,0.01,'基于成长投资策略的产品，适合激进型投资者','产品说明书详细内容...','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(3,'PROD003','FOF稳健产品C','FOF',NULL,1,'LOW',0.08,0.08,'3-5年',50000.00,5000000.00,0.01,0.15,0.01,0.00,'稳健型FOF产品，适合保守型投资者','产品说明书详细内容...','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(4,'PROD004','指数增强产品D','INDEX',NULL,1,'MEDIUM',0.10,0.15,'5-10年',100000.00,10000000.00,0.02,0.20,0.01,0.01,'沪深300指数增强产品','产品说明书详细内容...','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(5,'PROD005','择时产品E','TIMING',5,1,'MEDIUM',0.12,0.15,'3-5年',200000.00,20000000.00,0.03,0.20,0.02,0.01,'基于择时策略的产品','产品说明书详细内容...','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_performance`
--

DROP TABLE IF EXISTS `product_performance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_performance` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `performance_date` date NOT NULL COMMENT '收益日期',
  `nav` decimal(10,4) DEFAULT NULL COMMENT '单位净值',
  `cumulative_return` decimal(8,4) DEFAULT NULL COMMENT '累计收益率',
  `daily_return` decimal(8,4) DEFAULT NULL COMMENT '日收益率',
  `weekly_return` decimal(8,4) DEFAULT NULL COMMENT '周收益率',
  `monthly_return` decimal(8,4) DEFAULT NULL COMMENT '月收益率',
  `quarterly_return` decimal(8,4) DEFAULT NULL COMMENT '季度收益率',
  `yearly_return` decimal(8,4) DEFAULT NULL COMMENT '年收益率',
  `max_drawdown` decimal(8,4) DEFAULT NULL COMMENT '最大回撤',
  `sharpe_ratio` decimal(8,4) DEFAULT NULL COMMENT '夏普比率',
  `volatility` decimal(8,4) DEFAULT NULL COMMENT '波动率',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '状态',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `date` date DEFAULT NULL,
  `return_rate` double DEFAULT NULL,
  PRIMARY KEY (`id`,`performance_date`),
  UNIQUE KEY `uk_product_date` (`product_id`,`performance_date`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品收益表'
/*!50100 PARTITION BY RANGE (((year(`performance_date`) * 100) + month(`performance_date`)))
(PARTITION p202401 VALUES LESS THAN (202402) ENGINE = InnoDB,
 PARTITION p202402 VALUES LESS THAN (202403) ENGINE = InnoDB,
 PARTITION p202403 VALUES LESS THAN (202404) ENGINE = InnoDB,
 PARTITION p202404 VALUES LESS THAN (202405) ENGINE = InnoDB,
 PARTITION p202405 VALUES LESS THAN (202406) ENGINE = InnoDB,
 PARTITION p202406 VALUES LESS THAN (202407) ENGINE = InnoDB,
 PARTITION p202407 VALUES LESS THAN (202408) ENGINE = InnoDB,
 PARTITION p202408 VALUES LESS THAN (202409) ENGINE = InnoDB,
 PARTITION p202409 VALUES LESS THAN (202410) ENGINE = InnoDB,
 PARTITION p202410 VALUES LESS THAN (202411) ENGINE = InnoDB,
 PARTITION p202411 VALUES LESS THAN (202412) ENGINE = InnoDB,
 PARTITION p202412 VALUES LESS THAN (202501) ENGINE = InnoDB,
 PARTITION p_future VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_performance`
--

LOCK TABLES `product_performance` WRITE;
/*!40000 ALTER TABLE `product_performance` DISABLE KEYS */;
INSERT INTO `product_performance` VALUES (1,1,'2024-01-01',1.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(2,1,'2024-01-02',1.0020,0.0020,0.0020,0.0020,0.0020,0.0020,0.0020,0.0000,0.0000,0.0020,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(3,1,'2024-01-03',1.0050,0.0050,0.0030,0.0050,0.0050,0.0050,0.0050,0.0000,0.0000,0.0025,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(4,1,'2024-01-04',1.0030,0.0030,-0.0020,0.0030,0.0030,0.0030,0.0030,0.0020,0.0000,0.0020,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(5,1,'2024-01-05',1.0080,0.0080,0.0050,0.0080,0.0080,0.0080,0.0080,0.0020,0.0000,0.0025,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(6,2,'2024-01-01',1.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(7,2,'2024-01-02',1.0030,0.0030,0.0030,0.0030,0.0030,0.0030,0.0030,0.0000,0.0000,0.0030,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(8,2,'2024-01-03',1.0080,0.0080,0.0050,0.0080,0.0080,0.0080,0.0080,0.0000,0.0000,0.0040,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(9,2,'2024-01-04',1.0050,0.0050,-0.0030,0.0050,0.0050,0.0050,0.0050,0.0030,0.0000,0.0035,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(10,2,'2024-01-05',1.0120,0.0120,0.0070,0.0120,0.0120,0.0120,0.0120,0.0030,0.0000,0.0040,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(11,3,'2024-01-01',1.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(12,3,'2024-01-02',1.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0000,0.0000,0.0010,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(13,3,'2024-01-03',1.0030,0.0030,0.0020,0.0030,0.0030,0.0030,0.0030,0.0000,0.0000,0.0015,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(14,3,'2024-01-04',1.0020,0.0020,-0.0010,0.0020,0.0020,0.0020,0.0020,0.0010,0.0000,0.0012,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL),(15,3,'2024-01-05',1.0040,0.0040,0.0020,0.0040,0.0040,0.0040,0.0040,0.0010,0.0000,0.0014,'ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL,NULL);
/*!40000 ALTER TABLE `product_performance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_review`
--

DROP TABLE IF EXISTS `product_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_review` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `reviewer_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `review_type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核类型：INITIAL-初审，FINAL-终审',
  `review_status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核状态：PENDING-待审核，APPROVED-通过，REJECTED-拒绝',
  `review_comment` text COLLATE utf8mb4_unicode_ci COMMENT '审核意见',
  `review_date` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_review_product` (`product_id`),
  CONSTRAINT `fk_review_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品审核表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_review`
--

LOCK TABLES `product_review` WRITE;
/*!40000 ALTER TABLE `product_review` DISABLE KEYS */;
INSERT INTO `product_review` VALUES (1,1,1001,'INITIAL','APPROVED','初审通过，产品设计合理','2024-01-10 02:00:00','2025-06-28 13:00:29','2025-06-28 13:00:29'),(2,1,1002,'FINAL','APPROVED','终审通过，可以上线','2024-01-12 06:30:00','2025-06-28 13:00:29','2025-06-28 13:00:29'),(3,2,1001,'INITIAL','APPROVED','初审通过，风险收益匹配','2024-01-11 01:15:00','2025-06-28 13:00:29','2025-06-28 13:00:29'),(4,2,1002,'FINAL','APPROVED','终审通过，可以上线','2024-01-13 08:45:00','2025-06-28 13:00:29','2025-06-28 13:00:29'),(5,3,1001,'INITIAL','APPROVED','初审通过，适合保守投资者','2024-01-12 03:20:00','2025-06-28 13:00:29','2025-06-28 13:00:29'),(6,3,1002,'FINAL','APPROVED','终审通过，可以上线','2024-01-14 07:10:00','2025-06-28 13:00:29','2025-06-28 13:00:29'),(7,4,1001,'INITIAL','PENDING','初审中，需要补充材料','2024-01-13 05:45:00','2025-06-28 13:00:29','2025-06-28 13:00:29'),(8,5,1001,'INITIAL','REJECTED','初审不通过，风险过高','2024-01-14 02:30:00','2025-06-28 13:00:29','2025-06-28 13:00:29');
/*!40000 ALTER TABLE `product_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'管理员','平台管理员'),(2,'普通用户','普通注册用户');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_change_request`
--

DROP TABLE IF EXISTS `role_change_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_change_request` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `review_time` datetime(6) DEFAULT NULL,
  `reviewer_id` bigint DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `target_role_id` bigint NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_change_request`
--

LOCK TABLES `role_change_request` WRITE;
/*!40000 ALTER TABLE `role_change_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_change_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strategy`
--

DROP TABLE IF EXISTS `strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `strategy` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `strategy_code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '策略代码',
  `strategy_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '策略名称',
  `strategy_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '策略类型',
  `risk_level` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `target_return` double DEFAULT NULL,
  `max_drawdown` double DEFAULT NULL,
  `investment_horizon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `factor_tree_id` bigint DEFAULT NULL COMMENT '关联因子树ID',
  `parameters` tinytext COLLATE utf8mb4_unicode_ci,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `strategy_code` (`strategy_code`),
  KEY `fk_strategy_factor_tree` (`factor_tree_id`),
  CONSTRAINT `fk_strategy_factor_tree` FOREIGN KEY (`factor_tree_id`) REFERENCES `factor_tree` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='策略表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strategy`
--

LOCK TABLES `strategy` WRITE;
/*!40000 ALTER TABLE `strategy` DISABLE KEYS */;
INSERT INTO `strategy` VALUES (1,'VALUE_STRATEGY','价值投资策略','VALUE','MEDIUM',0.12,0.15,'3-5年','基于价值因子的投资策略，选择低估值优质股票',1,'{\"pe_threshold\": 15, \"pb_threshold\": 2.5, \"roe_threshold\": 0.15}','ACTIVE','2025-06-28 12:58:49','2025-06-28 12:58:49',NULL,NULL,NULL),(2,'GROWTH_STRATEGY','成长投资策略','GROWTH','HIGH',0.15,0.2,'5-10年','基于成长因子的投资策略，选择高成长性股票',2,'{\"growth_threshold\": 0.20, \"roe_threshold\": 0.12}','ACTIVE','2025-06-28 12:58:49','2025-06-28 12:58:49',NULL,NULL,NULL),(3,'RISK_CONTROL_STRATEGY','风险控制策略','RISK','LOW',0.08,0.1,'1-3年','基于风险因子的投资策略，控制投资风险',3,'{\"beta_threshold\": 1.0, \"volatility_threshold\": 0.20}','ACTIVE','2025-06-28 12:58:49','2025-06-28 12:58:49',NULL,NULL,NULL),(4,'BALANCED_STRATEGY','均衡配置策略','BALANCED','MEDIUM',0.11,0.12,'3-5年','均衡配置价值、成长和风险因子',NULL,'{\"value_weight\": 0.4, \"growth_weight\": 0.4, \"risk_weight\": 0.2}','ACTIVE','2025-06-28 12:58:49','2025-06-28 12:58:49',NULL,NULL,NULL),(5,'MOMENTUM_STRATEGY','动量策略','MOMENTUM','HIGH',0.14,0.18,'1-2年','基于动量因子的短期投资策略',NULL,'{\"momentum_period\": 60, \"momentum_threshold\": 0.05}','ACTIVE','2025-06-28 12:58:49','2025-06-28 12:58:49',NULL,NULL,NULL);
/*!40000 ALTER TABLE `strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strategy_backtest`
--

DROP TABLE IF EXISTS `strategy_backtest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `strategy_backtest` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `strategy_id` bigint NOT NULL COMMENT '策略ID',
  `backtest_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '回测名称',
  `start_date` date NOT NULL COMMENT '回测开始日期',
  `end_date` date NOT NULL COMMENT '回测结束日期',
  `initial_capital` double DEFAULT NULL,
  `final_value` decimal(15,2) DEFAULT NULL COMMENT '最终价值',
  `total_return` double DEFAULT NULL,
  `annual_return` double DEFAULT NULL,
  `max_drawdown` double DEFAULT NULL,
  `sharpe_ratio` double DEFAULT NULL,
  `volatility` decimal(8,4) DEFAULT NULL COMMENT '波动率',
  `win_rate` decimal(8,4) DEFAULT NULL COMMENT '胜率',
  `backtest_result` text COLLATE utf8mb4_unicode_ci COMMENT '回测结果JSON',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `backtest_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `results` tinytext COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `idx_backtest_strategy` (`strategy_id`),
  KEY `idx_backtest_date` (`start_date`,`end_date`),
  CONSTRAINT `fk_backtest_strategy` FOREIGN KEY (`strategy_id`) REFERENCES `strategy` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='策略回测表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strategy_backtest`
--

LOCK TABLES `strategy_backtest` WRITE;
/*!40000 ALTER TABLE `strategy_backtest` DISABLE KEYS */;
INSERT INTO `strategy_backtest` VALUES (1,1,'价值策略回测2023','2023-01-01','2023-12-31',1000000,1120000.00,0.12,0.12,0.085,1.45,0.1560,0.6500,'{\"monthly_returns\": [0.02, 0.03, -0.01, 0.04, 0.02, 0.01, 0.03, -0.02, 0.02, 0.01, 0.02, 0.01]}','ACTIVE','2025-06-28 12:58:49','2025-06-28 12:58:49',NULL,NULL),(2,2,'成长策略回测2023','2023-01-01','2023-12-31',1000000,1150000.00,0.15,0.15,0.12,1.25,0.1850,0.6000,'{\"monthly_returns\": [0.03, 0.04, -0.02, 0.05, 0.03, 0.02, 0.04, -0.03, 0.03, 0.02, 0.03, 0.02]}','ACTIVE','2025-06-28 12:58:49','2025-06-28 12:58:49',NULL,NULL),(3,3,'风险控制策略回测2023','2023-01-01','2023-12-31',1000000,1080000.00,0.08,0.08,0.05,1.6,0.0980,0.7000,'{\"monthly_returns\": [0.01, 0.02, 0.01, 0.02, 0.01, 0.01, 0.02, 0.01, 0.01, 0.01, 0.02, 0.01]}','ACTIVE','2025-06-28 12:58:49','2025-06-28 12:58:49',NULL,NULL),(4,4,'均衡策略回测2023','2023-01-01','2023-12-31',1000000,1110000.00,0.11,0.11,0.08,1.35,0.1340,0.6800,'{\"monthly_returns\": [0.02, 0.02, 0.01, 0.03, 0.02, 0.01, 0.02, -0.01, 0.02, 0.01, 0.02, 0.01]}','ACTIVE','2025-06-28 12:58:49','2025-06-28 12:58:49',NULL,NULL),(5,5,'动量策略回测2023','2023-01-01','2023-12-31',1000000,1140000.00,0.14,0.14,0.1,1.4,0.1670,0.6200,'{\"monthly_returns\": [0.02, 0.03, 0.01, 0.04, 0.02, 0.02, 0.03, -0.01, 0.03, 0.02, 0.02, 0.01]}','ACTIVE','2025-06-28 12:58:49','2025-06-28 12:58:49',NULL,NULL);
/*!40000 ALTER TABLE `strategy_backtest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strategy_portfolio`
--

DROP TABLE IF EXISTS `strategy_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `strategy_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `strategy_id` bigint NOT NULL COMMENT '策略ID',
  `portfolio_id` bigint NOT NULL COMMENT '组合ID',
  `weight` decimal(38,2) DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-正常，INACTIVE-停用',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_strategy_portfolio` (`strategy_id`,`portfolio_id`),
  KEY `fk_strategy_portfolio_portfolio` (`portfolio_id`),
  CONSTRAINT `fk_strategy_portfolio_portfolio` FOREIGN KEY (`portfolio_id`) REFERENCES `fund_portfolio` (`id`),
  CONSTRAINT `fk_strategy_portfolio_strategy` FOREIGN KEY (`strategy_id`) REFERENCES `strategy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='策略组合关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strategy_portfolio`
--

LOCK TABLES `strategy_portfolio` WRITE;
/*!40000 ALTER TABLE `strategy_portfolio` DISABLE KEYS */;
/*!40000 ALTER TABLE `strategy_portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timing_portfolio`
--

DROP TABLE IF EXISTS `timing_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `timing_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `portfolio_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组合名称',
  `portfolio_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组合代码',
  `timing_strategy` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '择时策略',
  `portfolio_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组合类型',
  `risk_level` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '风险等级',
  `target_return` decimal(8,4) DEFAULT NULL COMMENT '目标收益率',
  `max_drawdown` decimal(8,4) DEFAULT NULL COMMENT '最大回撤',
  `investment_horizon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投资期限',
  `min_investment` decimal(10,2) DEFAULT NULL COMMENT '最小投资金额',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-正常，INACTIVE-停用',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `portfolio_code` (`portfolio_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='择时组合表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timing_portfolio`
--

LOCK TABLES `timing_portfolio` WRITE;
/*!40000 ALTER TABLE `timing_portfolio` DISABLE KEYS */;
INSERT INTO `timing_portfolio` VALUES (1,'趋势择时组合','TIMING_TREND001','趋势跟踪','TREND','MEDIUM',0.1200,0.1500,'3-5年',20000.00,'基于趋势跟踪的择时组合','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(2,'均值回归择时组合','TIMING_MEAN001','均值回归','MEAN_REVERSION','MEDIUM',0.1000,0.1200,'2-3年',20000.00,'基于均值回归的择时组合','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL),(3,'波动率择时组合','TIMING_VOL001','波动率择时','VOLATILITY','HIGH',0.1400,0.1800,'1-2年',20000.00,'基于波动率的择时组合','ACTIVE','2025-06-28 13:00:29','2025-06-28 13:00:29',NULL);
/*!40000 ALTER TABLE `timing_portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade_error`
--

DROP TABLE IF EXISTS `trade_error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade_error` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delivery_order_id` bigint NOT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade_error`
--

LOCK TABLES `trade_error` WRITE;
/*!40000 ALTER TABLE `trade_error` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_error` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade_order`
--

DROP TABLE IF EXISTS `trade_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `trade_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易类型：BUY-买入，SELL-卖出',
  `amount` decimal(15,2) NOT NULL COMMENT '交易金额',
  `shares` decimal(15,4) DEFAULT NULL COMMENT '交易份额',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PENDING' COMMENT '订单状态：PENDING-待处理，PROCESSING-处理中，SUCCESS-成功，FAILED-失败，CANCELLED-已取消',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单备注',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `processed_at` timestamp NULL DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no` (`order_no`),
  KEY `idx_order_user` (`user_id`),
  KEY `idx_order_product` (`product_id`),
  KEY `idx_order_status` (`status`),
  KEY `idx_order_created` (`created_at`),
  KEY `idx_order_user_status` (`user_id`,`status`),
  CONSTRAINT `fk_order_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade_order`
--

LOCK TABLES `trade_order` WRITE;
/*!40000 ALTER TABLE `trade_order` DISABLE KEYS */;
INSERT INTO `trade_order` VALUES (1,'ORD202401150001',1001,1,'BUY',100000.00,100000.0000,'REJECTED','首次购买价值投资产品','2024-01-15 01:30:00','2024-01-15 01:35:00','2024-01-15 01:35:00'),(2,'ORD202401150002',1002,2,'BUY',150000.00,150000.0000,'PROCESSING','购买成长投资产品','2024-01-15 02:15:00','2024-01-15 02:20:00','2024-01-15 02:20:00'),(3,'ORD202401150003',1003,3,'BUY',50000.00,50000.0000,'SUCCESS','购买FOF稳健产品','2024-01-15 03:00:00','2024-01-15 03:05:00','2024-01-15 03:05:00'),(4,'ORD202401150004',1001,1,'BUY',50000.00,50000.0000,'SUCCESS','追加购买价值投资产品','2024-01-15 06:30:00','2024-01-15 06:35:00','2024-01-15 06:35:00'),(5,'ORD202401150005',1002,2,'SELL',50000.00,50000.0000,'SUCCESS','部分赎回成长投资产品','2024-01-15 07:45:00','2024-01-15 07:50:00','2024-01-15 07:50:00'),(6,'ORD202401160001',1004,4,'BUY',100000.00,100000.0000,'PENDING','购买指数增强产品','2024-01-16 01:00:00','2024-01-16 01:00:00',NULL),(7,'ORD202401160002',1005,5,'BUY',200000.00,200000.0000,'PROCESSING','购买择时产品','2024-01-16 02:30:00','2024-01-16 02:30:00',NULL),(8,'ORD202401160003',1001,1,'SELL',20000.00,20000.0000,'CANCELLED','用户取消赎回','2024-01-16 03:15:00','2024-01-16 03:20:00',NULL);
/*!40000 ALTER TABLE `trade_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade_record`
--

DROP TABLE IF EXISTS `trade_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `record_no` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易记录编号',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `trade_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易类型：BUY-买入，SELL-卖出',
  `amount` decimal(15,2) NOT NULL COMMENT '交易金额',
  `shares` decimal(15,4) DEFAULT NULL COMMENT '交易份额',
  `price` decimal(10,4) DEFAULT NULL COMMENT '交易价格',
  `fee` decimal(10,2) DEFAULT NULL COMMENT '手续费',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易状态：SUCCESS-成功，FAILED-失败',
  `trade_time` timestamp NOT NULL COMMENT '交易时间',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `record_no` (`record_no`),
  KEY `fk_record_product` (`product_id`),
  KEY `idx_record_user` (`user_id`),
  KEY `idx_record_order` (`order_id`),
  KEY `idx_record_time` (`trade_time`),
  KEY `idx_record_user_time` (`user_id`,`trade_time`),
  CONSTRAINT `fk_record_order` FOREIGN KEY (`order_id`) REFERENCES `trade_order` (`id`),
  CONSTRAINT `fk_record_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade_record`
--

LOCK TABLES `trade_record` WRITE;
/*!40000 ALTER TABLE `trade_record` DISABLE KEYS */;
INSERT INTO `trade_record` VALUES (1,'REC202401150001',1,1001,1,'BUY',100000.00,100000.0000,1.0000,1000.00,'SUCCESS','2024-01-15 01:35:00','2025-06-28 13:00:39'),(2,'REC202401150002',2,1002,2,'BUY',150000.00,150000.0000,1.0000,1500.00,'SUCCESS','2024-01-15 02:20:00','2025-06-28 13:00:39'),(3,'REC202401150003',3,1003,3,'BUY',50000.00,50000.0000,1.0000,400.00,'SUCCESS','2024-01-15 03:05:00','2025-06-28 13:00:39'),(4,'REC202401150004',4,1001,1,'BUY',50000.00,50000.0000,1.0020,500.00,'SUCCESS','2024-01-15 06:35:00','2025-06-28 13:00:39'),(5,'REC202401150005',5,1002,2,'SELL',50000.00,50000.0000,1.0120,250.00,'SUCCESS','2024-01-15 07:50:00','2025-06-28 13:00:39');
/*!40000 ALTER TABLE `trade_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名/登录名',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码（加密存储）',
  `real_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `company_id` bigint DEFAULT NULL COMMENT '所属公司ID（可选）',
  `manager_id` bigint DEFAULT NULL COMMENT '关联基金经理ID（可选）',
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'ACTIVE' COMMENT '状态：ACTIVE-正常，INACTIVE-停用',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_user_role` (`role_id`),
  KEY `fk_user_company` (`company_id`),
  KEY `fk_user_manager` (`manager_id`),
  CONSTRAINT `fk_user_company` FOREIGN KEY (`company_id`) REFERENCES `fund_company` (`id`),
  CONSTRAINT `fk_user_manager` FOREIGN KEY (`manager_id`) REFERENCES `fund_manager` (`id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$12$rqKhvVxEcKx0CeXRe2WdE.irM3hh7VlX0VR1oDfVOeYR46SOnHEO6','管理员','admin@example.com','13800000000',1,NULL,NULL,'ACTIVE','2025-07-02 18:35:51','2025-07-02 18:36:01'),(4,'天南第一深情','$2a$10$aMom8CNI5zhIJ5ONi.C3p.qfLKUiAbok7ax9VwQSgCKkhG4csQuSG','zzy','1111111@163.com','111111111111',2,NULL,NULL,'ACTIVE','2025-07-02 18:51:01','2025-07-02 19:13:25');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_position`
--

DROP TABLE IF EXISTS `user_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_position` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `shares` decimal(15,4) NOT NULL COMMENT '持仓份额',
  `cost` decimal(15,2) NOT NULL COMMENT '持仓成本',
  `avg_cost_price` decimal(10,4) DEFAULT NULL COMMENT '平均成本价',
  `market_value` decimal(15,2) DEFAULT NULL COMMENT '当前市值',
  `profit_loss` decimal(15,2) DEFAULT NULL COMMENT '浮动盈亏',
  `profit_loss_rate` decimal(8,4) DEFAULT NULL COMMENT '浮动盈亏率',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`,`product_id`),
  KEY `idx_position_user` (`user_id`),
  KEY `idx_position_product` (`product_id`),
  CONSTRAINT `fk_position_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户持仓表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_position`
--

LOCK TABLES `user_position` WRITE;
/*!40000 ALTER TABLE `user_position` DISABLE KEYS */;
INSERT INTO `user_position` VALUES (1,1001,1,150000.0000,150000.00,1.0000,150600.00,600.00,0.0040,'2025-06-28 13:00:39','2025-06-28 13:00:39'),(2,1002,2,100000.0000,100000.00,1.0000,101200.00,1200.00,0.0120,'2025-06-28 13:00:39','2025-06-28 13:00:39'),(3,1003,3,50000.0000,50000.00,1.0000,50200.00,200.00,0.0040,'2025-06-28 13:00:39','2025-06-28 13:00:39');
/*!40000 ALTER TABLE `user_position` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-05 15:55:06
