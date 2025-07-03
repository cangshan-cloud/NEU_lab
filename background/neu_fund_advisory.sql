-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
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
-- Current Database: `neu_fund_advisory`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `neu_fund_advisory` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `neu_fund_advisory`;

--
-- Table structure for table `capital_flow`
--

DROP TABLE IF EXISTS `capital_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `capital_flow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(15,2) NOT NULL,
  `balance_after` decimal(15,2) DEFAULT NULL,
  `balance_before` decimal(15,2) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `flow_no` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `flow_time` datetime(6) NOT NULL,
  `flow_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKn3tc9clsx15tgg71abys35wme` (`flow_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `capital_flow`
--

LOCK TABLES `capital_flow` WRITE;
/*!40000 ALTER TABLE `capital_flow` DISABLE KEYS */;
/*!40000 ALTER TABLE `capital_flow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor`
--

DROP TABLE IF EXISTS `factor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `calculation_formula` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `data_source` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_frequency` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `factor_category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `factor_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `factor_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `factor_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor`
--

LOCK TABLES `factor` WRITE;
/*!40000 ALTER TABLE `factor` DISABLE KEYS */;
INSERT INTO `factor` VALUES (1,NULL,'基金成立日期',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'基本面','F001','成立日期','日期'),(2,NULL,'基金发行机构',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'基本面','F002','发行机构','字符串'),(3,NULL,'近一阶段涨跌幅',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'业绩','F003','阶段涨跌幅','数值'),(4,NULL,'基金当前规模',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'规模','F004','基金规模','数值'),(5,NULL,'夏普比率',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'风险','F005','夏普比率','数值'),(6,NULL,'历史最大回撤',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'风险','F006','最大回撤','数值'),(7,NULL,'年化波动率',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'风险','F007','波动率','数值'),(8,NULL,'成长风格因子',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'风格','F008','成长风格','数值'),(9,NULL,'价值风格因子',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'风格','F009','价值风格','数值'),(10,NULL,'大盘风格因子',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'风格','F010','大盘风格','数值'),(11,NULL,'由多个基础因子加权生成复合因子',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'active',NULL,'复合因子','COMPOSITE_1751298530787','衍生1','加权');
/*!40000 ALTER TABLE `factor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_data`
--

DROP TABLE IF EXISTS `factor_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factor_data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `factor_id` bigint DEFAULT NULL,
  `fund_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `data_date` date DEFAULT NULL,
  `data_source` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `factor_value` double DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_data`
--

LOCK TABLES `factor_data` WRITE;
/*!40000 ALTER TABLE `factor_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `factor_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_tree`
--

DROP TABLE IF EXISTS `factor_tree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factor_tree` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tree_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tree_description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tree_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_tree`
--

LOCK TABLES `factor_tree` WRITE;
/*!40000 ALTER TABLE `factor_tree` DISABLE KEYS */;
INSERT INTO `factor_tree` VALUES (11,NULL,NULL,NULL,NULL,'active',NULL,'1','测试');
/*!40000 ALTER TABLE `factor_tree` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factor_tree_node`
--

DROP TABLE IF EXISTS `factor_tree_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factor_tree_node` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `factor_id` bigint DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `tree_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `node_order` int DEFAULT NULL,
  `node_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `node_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `weight` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factor_tree_node`
--

LOCK TABLES `factor_tree_node` WRITE;
/*!40000 ALTER TABLE `factor_tree_node` DISABLE KEYS */;
INSERT INTO `factor_tree_node` VALUES (108,NULL,1,NULL,NULL,11,NULL,NULL,'因子','成立日期','active',NULL),(109,NULL,2,NULL,NULL,11,NULL,NULL,'因子','发行机构','active',NULL),(110,NULL,3,NULL,NULL,11,NULL,NULL,'因子','阶段涨跌幅','active',NULL);
/*!40000 ALTER TABLE `factor_tree_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fof_portfolio`
--

DROP TABLE IF EXISTS `fof_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fof_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fof_portfolio`
--

LOCK TABLES `fof_portfolio` WRITE;
/*!40000 ALTER TABLE `fof_portfolio` DISABLE KEYS */;
/*!40000 ALTER TABLE `fof_portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund`
--

DROP TABLE IF EXISTS `fund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `manager_id` bigint DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `risk_level` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund`
--

LOCK TABLES `fund` WRITE;
/*!40000 ALTER TABLE `fund` DISABLE KEYS */;
INSERT INTO `fund` VALUES (8,'44',1,NULL,1,'测试基金4',NULL,'STOCK',NULL,'HIGH'),(9,'11',5,NULL,2,'测试基金1',NULL,'MONEY',NULL,'MEDIUM'),(10,'22',3,NULL,2,'测试基金2',NULL,'MONEY',NULL,'LOW'),(11,'55',3,NULL,2,'测试基金5',NULL,'HYBRID',NULL,'MEDIUM');
/*!40000 ALTER TABLE `fund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_company`
--

DROP TABLE IF EXISTS `fund_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_company` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_company`
--

LOCK TABLES `fund_company` WRITE;
/*!40000 ALTER TABLE `fund_company` DISABLE KEYS */;
INSERT INTO `fund_company` VALUES (1,'2025-06-29 21:01:55.000000',NULL,'华夏基金','2025-06-29 21:01:55.000000'),(2,'2025-06-29 21:01:55.000000',NULL,'易方达基金','2025-06-29 21:01:55.000000'),(3,'2025-06-29 23:23:35.000000',NULL,'华夏基金','2025-06-29 23:23:35.000000'),(4,'2025-06-29 23:23:35.000000',NULL,'易方达基金','2025-06-29 23:23:35.000000'),(5,'2025-06-29 23:23:35.000000',NULL,'南方基金','2025-06-29 23:23:35.000000');
/*!40000 ALTER TABLE `fund_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_manager`
--

DROP TABLE IF EXISTS `fund_manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_manager` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `profile` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_manager`
--

LOCK TABLES `fund_manager` WRITE;
/*!40000 ALTER TABLE `fund_manager` DISABLE KEYS */;
INSERT INTO `fund_manager` VALUES (1,'2025-06-29 23:23:35.000000','张三',NULL,'2025-06-29 23:23:35.000000',1),(2,'2025-06-29 23:23:35.000000','李四',NULL,'2025-06-29 23:23:35.000000',2),(3,'2025-06-29 23:23:35.000000','王五',NULL,'2025-06-29 23:23:35.000000',3);
/*!40000 ALTER TABLE `fund_manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_portfolio`
--

DROP TABLE IF EXISTS `fund_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_portfolio`
--

LOCK TABLES `fund_portfolio` WRITE;
/*!40000 ALTER TABLE `fund_portfolio` DISABLE KEYS */;
INSERT INTO `fund_portfolio` VALUES (3,'2025-06-30 17:02:37.950406',NULL,'基金组合25','2025-06-30 17:02:37.950406',NULL),(4,'2025-06-30 17:05:23.950617',NULL,'基金组合125','2025-06-30 17:05:23.950617',NULL),(5,'2025-06-30 22:52:30.239133',NULL,'基金组合1245','2025-06-30 22:52:30.239133',NULL);
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
  PRIMARY KEY (`portfolio_id`,`fund_id`),
  KEY `FKgrt2av3qcwfrhy411fqu2vkfo` (`fund_id`),
  CONSTRAINT `FK30q189fhdq6b1ntiav75avsue` FOREIGN KEY (`portfolio_id`) REFERENCES `fund_portfolio` (`id`),
  CONSTRAINT `FKgrt2av3qcwfrhy411fqu2vkfo` FOREIGN KEY (`fund_id`) REFERENCES `fund` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_portfolio_relation`
--

LOCK TABLES `fund_portfolio_relation` WRITE;
/*!40000 ALTER TABLE `fund_portfolio_relation` DISABLE KEYS */;
INSERT INTO `fund_portfolio_relation` VALUES (5,8),(4,9),(5,9),(3,10),(4,10),(5,10),(3,11),(4,11),(5,11);
/*!40000 ALTER TABLE `fund_portfolio_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund_tag`
--

DROP TABLE IF EXISTS `fund_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `tag_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_tag`
--

LOCK TABLES `fund_tag` WRITE;
/*!40000 ALTER TABLE `fund_tag` DISABLE KEYS */;
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
  PRIMARY KEY (`fund_id`,`tag_id`),
  KEY `FK5xidseiga21mr17erimbm2alx` (`tag_id`),
  CONSTRAINT `FK5xidseiga21mr17erimbm2alx` FOREIGN KEY (`tag_id`) REFERENCES `fund_tag` (`id`),
  CONSTRAINT `FKhu9uieyy5gckldvgycycjlx19` FOREIGN KEY (`fund_id`) REFERENCES `fund` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund_tag_relation`
--

LOCK TABLES `fund_tag_relation` WRITE;
/*!40000 ALTER TABLE `fund_tag_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `fund_tag_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `index_portfolio`
--

DROP TABLE IF EXISTS `index_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `index_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `index_portfolio`
--

LOCK TABLES `index_portfolio` WRITE;
/*!40000 ALTER TABLE `index_portfolio` DISABLE KEYS */;
/*!40000 ALTER TABLE `index_portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `risk_level` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `strategy_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_performance`
--

DROP TABLE IF EXISTS `product_performance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_performance` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `return_rate` double DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_performance`
--

LOCK TABLES `product_performance` WRITE;
/*!40000 ALTER TABLE `product_performance` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_performance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_review`
--

DROP TABLE IF EXISTS `product_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `product_id` bigint NOT NULL,
  `review_comment` text COLLATE utf8mb4_unicode_ci,
  `review_date` datetime(6) DEFAULT NULL,
  `review_status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `review_type` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reviewer_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_review`
--

LOCK TABLES `product_review` WRITE;
/*!40000 ALTER TABLE `product_review` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strategy`
--

DROP TABLE IF EXISTS `strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `strategy` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `investment_horizon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `max_drawdown` double DEFAULT NULL,
  `parameters` longtext COLLATE utf8mb4_unicode_ci,
  `risk_level` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `target_return` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strategy`
--

LOCK TABLES `strategy` WRITE;
/*!40000 ALTER TABLE `strategy` DISABLE KEYS */;
/*!40000 ALTER TABLE `strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strategy_backtest`
--

DROP TABLE IF EXISTS `strategy_backtest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `strategy_backtest` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `result` longtext COLLATE utf8mb4_unicode_ci,
  `start_date` date DEFAULT NULL,
  `strategy_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `annual_return` double DEFAULT NULL,
  `backtest_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `initial_capital` double DEFAULT NULL,
  `max_drawdown` double DEFAULT NULL,
  `results` longtext COLLATE utf8mb4_unicode_ci,
  `sharpe_ratio` double DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_return` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strategy_backtest`
--

LOCK TABLES `strategy_backtest` WRITE;
/*!40000 ALTER TABLE `strategy_backtest` DISABLE KEYS */;
/*!40000 ALTER TABLE `strategy_backtest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strategy_portfolio`
--

DROP TABLE IF EXISTS `strategy_portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `strategy_portfolio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `portfolio_id` bigint NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `strategy_id` bigint NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `weight` decimal(5,4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
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
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timing_portfolio`
--

LOCK TABLES `timing_portfolio` WRITE;
/*!40000 ALTER TABLE `timing_portfolio` DISABLE KEYS */;
/*!40000 ALTER TABLE `timing_portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade_order`
--

DROP TABLE IF EXISTS `trade_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(15,2) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `order_no` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `processed_at` datetime(6) DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shares` decimal(15,4) DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trade_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6ykuik9mbj62fjqj3c33gqek9` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade_order`
--

LOCK TABLES `trade_order` WRITE;
/*!40000 ALTER TABLE `trade_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade_record`
--

DROP TABLE IF EXISTS `trade_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(15,2) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `fee` decimal(10,2) DEFAULT NULL,
  `order_id` bigint NOT NULL,
  `price` decimal(10,4) DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `record_no` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shares` decimal(15,4) DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `trade_time` datetime(6) NOT NULL,
  `trade_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKgl0gsanmldnff6c5trqjw9p5n` (`record_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade_record`
--

LOCK TABLES `trade_record` WRITE;
/*!40000 ALTER TABLE `trade_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `trade_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_position`
--

DROP TABLE IF EXISTS `user_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_position` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avg_cost_price` decimal(10,4) DEFAULT NULL,
  `cost` decimal(15,2) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `market_value` decimal(15,2) DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `profit_loss` decimal(15,2) DEFAULT NULL,
  `profit_loss_rate` decimal(8,4) DEFAULT NULL,
  `shares` decimal(15,4) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_position`
--

LOCK TABLES `user_position` WRITE;
/*!40000 ALTER TABLE `user_position` DISABLE KEYS */;
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

-- Dump completed on 2025-07-01 15:03:19
