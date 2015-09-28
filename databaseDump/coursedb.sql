-- MySQL dump 10.13  Distrib 5.5.23, for Win64 (x86)
--
-- Host: localhost    Database: coursedb
-- ------------------------------------------------------
-- Server version	5.5.23

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
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cars` (
  `carID` int(11) NOT NULL AUTO_INCREMENT,
  `clientID` int(11) DEFAULT NULL,
  `make` varchar(45) NOT NULL,
  `model` varchar(45) NOT NULL,
  `year` varchar(45) NOT NULL,
  `vin` varchar(45) NOT NULL,
  PRIMARY KEY (`carID`),
  UNIQUE KEY `carID_UNIQUE` (`carID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES (1,1,'Citroen','C4','2006','14132421412'),(2,1,'Toyota','RAV4','2012','14124214'),(3,2,'Lada','Samara','1995','13412421'),(4,2,'Pagani','Zonda','2010','123123'),(5,3,'Ford','F150','2007','45135132'),(6,14,'Lada','Granta','1965','1313413'),(8,5,'Peugeot','405','2001','32523423'),(10,4,'Reno','Laguna','2005','242423'),(11,5,'Lamborgini','Aventador','2015','2245353'),(12,15,'Ford','Mondeo','1995','4252453'),(13,3,'Ford','Mustang','2002','1343143'),(14,6,'Peugeot','307','2003','131232'),(15,7,'Ferrari','F40','2013','24653564'),(16,8,'Mercedes','C200','2008','1413431'),(17,6,'Lada','2105','1980','133542'),(18,4,'Peugeot','205','1999','4252454'),(19,4,'Mercedes','W124','2005','6536544'),(20,9,'Volvo','V60','2001','634534'),(21,9,'Volvo','V40','2003','324252'),(22,11,'Volvo','S60','2010','453533'),(23,11,'Volvo','S80','2012','134212');
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `orderID` int(11) NOT NULL,
  `carID` int(11) NOT NULL,
  `date` date NOT NULL,
  `orderCost` float DEFAULT NULL,
  `orderStatus` char(13) DEFAULT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`orderID`),
  UNIQUE KEY `orderID_UNIQUE` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,2,'2015-09-18',142,'Completed','new tires'),(3,15,'2015-09-27',8000,'In progress','new door'),(4,16,'2015-09-27',245,'In progress','wf'),(7,5,'2015-09-27',800,'Completed','something'),(8,20,'2015-09-27',900,'In progress','none'),(9,20,'2015-09-27',250,'In progress','wfw'),(10,22,'2015-09-27',234,'In progress','ggff'),(11,17,'2015-09-28',123,'In progress','sgsg'),(12,19,'2015-09-28',450,'In progress','eger'),(13,13,'2015-09-28',123,'In progress','wfw'),(14,11,'2015-09-28',123,'In progress','ffe'),(15,1,'2015-09-28',542,'In progress','afrfe'),(16,18,'2015-09-28',562,'In progress','wfwe'),(17,1,'2015-09-28',200,'Completed','314'),(18,3,'2015-09-28',600,'In progress','thr');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `clientID` int(11) NOT NULL DEFAULT '0',
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(13) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`clientID`),
  UNIQUE KEY `userID_UNIQUE` (`clientID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Vasily','Apanovich','1996-08-11','Minsk','+375336310652','apanovich96@gmail.com'),(2,'Vladislav','Tsvirko','2015-09-08','Minsk','+375334563245','vlad95@gmail.com'),(3,'Elena','Khmaruk','1995-12-18','Minsk','+375255403242','elenakhmaruk1@gmail.com'),(4,'Jack','Jones','1992-11-11','Brest','+375463103212','jj@gmail.com'),(5,'Anton','Nekrasov','1989-12-14','Minsk','+375449990651','anton@gmail.com'),(6,'Alexey','Ivanov','1993-09-09','Gomel','+375291235462','ivanov@gmail.com'),(7,'Jordan','Clarkson','1988-12-26','London','+375336330655','clarkson@gmail.com'),(8,'Kate','Jones','1992-10-13','Minsk','+375338420982','kate@gmail.com'),(9,'Anton','Apanovich','1999-06-27','Nesvizh','+375295679302','apa99@mail.ru'),(10,'Olga','Petrova','1972-01-13','Minsk','+375446541317','petrova@tut.by'),(11,'Yulia','Demkiv','1976-11-26','Lvov','+375444314756','demkiv@tut.by'),(12,'Gena','Bukin','1994-09-08','Moscow','+375547453212','bukin@gmail.com'),(13,'Vika','Baranova','1995-09-12','Minsk','+375256667898','vika@tut.by'),(14,'Viktor','Astapenko','1979-03-17','Minsk','+375448904323','astap@mail.ru'),(15,'Andrei','Noname','1991-02-17','Minsk','+375441233214','non@mail.ru'),(17,'Nikita','Baranov','1994-08-12','Minsk','+375256664312','baranov@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-09-28 15:58:18
