
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ip`
--

DROP TABLE IF EXISTS `ip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `referer` varchar(80) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL,
  `protocol` varchar(10) DEFAULT NULL,
  `requestUrl` varchar(30) DEFAULT NULL,
  `realPath` varchar(20) DEFAULT NULL,
  `remoteAddr` varchar(63) DEFAULT NULL,
  `remoteHost` varchar(40) DEFAULT NULL,
  `serverName` varchar(30) DEFAULT NULL,
  `serverPath` varchar(30) DEFAULT NULL,
  `serverPort` varchar(6) DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  `characterEncoding` varchar(10) DEFAULT NULL,
  `queryString` varchar(50) DEFAULT NULL,
  `pathInfo` varchar(60) DEFAULT NULL,
  `remoteUser` varchar(50) DEFAULT NULL,
  `acceptLanguage` varchar(30) DEFAULT NULL,
  `acceptEncoding` varchar(40) DEFAULT NULL,
  `lastAccessed` varchar(15) DEFAULT NULL,
  `userAgent` varchar(230) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1778 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tb_article`
--

DROP TABLE IF EXISTS `tb_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_typeID` smallint(6) DEFAULT NULL,
  `article_title` varchar(30) DEFAULT NULL,
  `article_content` varchar(3000) DEFAULT NULL,
  `article_sdTime` varchar(30) DEFAULT NULL,
  `article_create` varchar(10) DEFAULT NULL,
  `article_info` varchar(50) DEFAULT NULL,
  `article_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_article_typeID` (`article_typeID`),
  CONSTRAINT `fk_article_typeID` FOREIGN KEY (`article_typeID`) REFERENCES `tb_articletype` (`articleType_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tb_articletype`
--

DROP TABLE IF EXISTS `tb_articletype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_articletype` (
  `articleType_id` smallint(6) NOT NULL AUTO_INCREMENT,
  `articleType_name` varchar(10) DEFAULT NULL,
  `articleType_info` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`articleType_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tb_friend`
--

DROP TABLE IF EXISTS `tb_friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `friend_blog` varchar(30) DEFAULT NULL,
  `friend_link` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tb_master`
--

DROP TABLE IF EXISTS `tb_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_master` (
  `master_name` varchar(20) DEFAULT NULL,
  `master_password` varchar(30) DEFAULT NULL,
  `master_sex` varchar(6) DEFAULT NULL,
  `master_oicq` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tb_review`
--

DROP TABLE IF EXISTS `tb_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `review_articleID` int(11) DEFAULT NULL,
  `review_author` varchar(20) DEFAULT NULL,
  `review_content` varchar(500) DEFAULT NULL,
  `review_sdTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tb_word`
--

DROP TABLE IF EXISTS `tb_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word_title` varchar(30) DEFAULT NULL,
  `word_content` varchar(500) DEFAULT NULL,
  `word_sdTime` varchar(20) DEFAULT NULL,
  `word_author` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=gb2312;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-07-23 18:15:49
