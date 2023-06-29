CREATE DATABASE  IF NOT EXISTS `flights_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `flights_db`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: flights_db
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `airport`
--

DROP TABLE IF EXISTS `airport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `airport` (
  `AIRPORT_NUMBER` int NOT NULL AUTO_INCREMENT,
  `AIRPORT_NAME` varchar(100) NOT NULL,
  `CITY` varchar(100) NOT NULL,
  `COUNTRY` varchar(100) NOT NULL,
  `TIME_ZONE` time DEFAULT NULL,
  PRIMARY KEY (`AIRPORT_NUMBER`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airport`
--

LOCK TABLES `airport` WRITE;
/*!40000 ALTER TABLE `airport` DISABLE KEYS */;
INSERT INTO `airport` VALUES (1,'Samos Aristarchos Samios Airport','Samos','Greece','01:46:00'),(2,'Athens Airport Eleftherios Venizelos','Athens','Greece','01:46:00'),(3,'Mykonos International Airport','Mykonos','Greece','01:46:00'),(4,'Milos Airport','Milos','Greece','01:46:00'),(5,'Airport of Thessaloniki Macedonia','Thessaloniki','Greece','01:46:00');
/*!40000 ALTER TABLE `airport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `BOOKING_NUMBER` int NOT NULL AUTO_INCREMENT,
  `BOOKING_DATE` date NOT NULL,
  `PASSENGER_PASSPORT_NUMBER` int NOT NULL,
  `FLIGHT_NUMBER` int NOT NULL,
  `DEPARTURE_DATE` date NOT NULL,
  `DEPARTURE_TIME` time NOT NULL,
  `ARRIVAL_TIME` time NOT NULL,
  PRIMARY KEY (`BOOKING_NUMBER`),
  KEY `PASSENGER_PASSPORT_NUMBER` (`PASSENGER_PASSPORT_NUMBER`),
  KEY `FLIGHT_NUMBER` (`FLIGHT_NUMBER`),
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`PASSENGER_PASSPORT_NUMBER`) REFERENCES `passenger` (`PASSENGER_PASSPORT_NUMBER`),
  CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`FLIGHT_NUMBER`) REFERENCES `flight` (`FLIGHT_NUMBER`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (7,'2023-05-09',123456,5,'2023-06-20','14:30:00','15:15:00'),(8,'2023-05-09',59595959,2,'2023-06-05','14:30:00','15:15:00'),(9,'2023-05-09',456456456,2,'2023-06-05','14:30:00','15:15:00'),(10,'2023-05-09',75757575,2,'2023-06-05','14:30:00','15:15:00'),(11,'2023-05-09',542542542,2,'2023-06-05','14:30:00','15:15:00'),(12,'2023-05-09',896325,2,'2023-06-05','14:30:00','15:15:00'),(13,'2023-05-09',13579,5,'2023-06-20','14:30:00','15:15:00'),(14,'2023-05-09',365412,1,'2023-06-01','08:00:00','09:30:00'),(15,'2023-05-09',36541299,1,'2023-06-01','08:00:00','09:30:00'),(16,'2023-05-09',789789,5,'2023-06-20','14:30:00','15:15:00'),(17,'2023-05-09',58545652,6,'2023-06-25','11:00:00','12:15:00'),(18,'2023-05-09',135794,7,'2023-06-05','18:00:00','18:45:00'),(19,'2023-05-09',1357944,7,'2023-06-05','18:00:00','18:45:00'),(20,'2023-05-09',13579,4,'2023-06-15','08:00:00','09:30:00'),(21,'2023-05-12',753258,3,'2023-06-08','11:00:00','12:15:00'),(22,'2023-05-14',7412365,5,'2023-06-20','14:30:00','15:15:00'),(23,'2023-05-16',123,7,'2023-06-05','18:00:00','18:45:00'),(24,'2023-05-16',365,7,'2023-06-05','18:00:00','18:45:00'),(25,'2023-05-17',89652332,7,'2023-06-05','18:00:00','18:45:00');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS `flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight` (
  `FLIGHT_NUMBER` int NOT NULL AUTO_INCREMENT,
  `AIRLINE_NAME` varchar(100) NOT NULL,
  `DEPARTURE_AIRPORT` varchar(100) NOT NULL,
  `ARRIVAL_AIRPORT` varchar(100) NOT NULL,
  `DEPARTURE_DATE` date NOT NULL,
  `DEPARTURE_TIME` time NOT NULL,
  `ARRIVAL_TIME` time NOT NULL,
  `FLIGHT_DURATION` int NOT NULL,
  `PRICE` float NOT NULL,
  PRIMARY KEY (`FLIGHT_NUMBER`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight`
--

LOCK TABLES `flight` WRITE;
/*!40000 ALTER TABLE `flight` DISABLE KEYS */;
INSERT INTO `flight` VALUES (1,'Olympic Air','Athens Airport Eleftherios Venizelos','Samos Aristarchos Samios Airport','2023-06-01','08:00:00','09:30:00',90,120.5),(2,'Aegean Airlines','Mykonos International Airport','Milos Airport','2023-06-05','14:30:00','15:15:00',45,75),(3,'Ellinair','Airport of Thessaloniki Macedonia','Athens Airport Eleftherios Venizelos','2023-06-08','11:00:00','12:15:00',75,90),(4,'Olympic Air','Samos Aristarchos Samios Airport','Athens Airport Eleftherios Venizelos','2023-06-15','08:00:00','09:30:00',90,120.5),(5,'Aegean Airlines','Milos Airport','Mykonos International Airport','2023-06-20','14:30:00','15:15:00',45,75),(6,'Ellinair','Athens Airport Eleftherios Venizelos','Airport of Thessaloniki Macedonia','2023-06-25','11:00:00','12:15:00',75,90),(7,'Olympic Air','Mykonos International Airport','Milos Airport','2023-06-05','18:00:00','18:45:00',90,135.5),(8,'Olympic Air','Milos Airport','Mykonos International Airport','2023-06-20','18:00:00','18:45:00',90,135.5);
/*!40000 ALTER TABLE `flight` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passenger` (
  `PASSENGER_PASSPORT_NUMBER` int NOT NULL,
  `NAMEp` varchar(100) NOT NULL,
  `SURNAMEp` varchar(100) NOT NULL,
  `CITY` varchar(100) NOT NULL,
  `ADDRESS` varchar(150) NOT NULL,
  `PHONE` varchar(50) NOT NULL,
  `EMAIL` varchar(150) NOT NULL,
  PRIMARY KEY (`PASSENGER_PASSPORT_NUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger`
--

LOCK TABLES `passenger` WRITE;
/*!40000 ALTER TABLE `passenger` DISABLE KEYS */;
INSERT INTO `passenger` VALUES (123,'ClientTestName1','ClientTestSurName1','ClientTestCity1','ClientTestAddress1','ClientTestPhone1','ClientTestEmail1'),(365,'testClientName2','testClientSurName2','testClientCity2','testClientAddress2','testClientPhone2','testClientEmail2'),(13579,'Kalisperis','Kalisperakis','Vari','Plousiou 21','695833256','kalisp@gmail.com'),(123456,'Pasxalis','Terzis','Pallini','Grigoriou 44','694855691','terz@aegean.gr'),(135794,'Meplomeni','Melpo','Methana','Mathaniou 55','6948566335','melpo@aegean.gr'),(365412,'Xrisoula','Xrisoulaki','Athens','Monastiraki 234','6953654478','xrisou@aegean.gr'),(753258,'testUserName','testUserSurName','testUserCity','testUserAddress','testUserPhone','testUserEmail'),(789789,'Anna','Zaxarea','Kamatero','Xrisou 37','695632458','zaxxa@gmail.com'),(896325,'Pinelopi','Pinelopaki','Xolargos','Papagou 5','693345521','pine@gmail.com'),(1357944,'Mikelina','Mikel','Pallini','Stanatiadou 8','695833525','mikel@aegean.gr'),(7412365,'Chris','Christakis','Pallini','Ploutarxou 34','6945632546','leva@aegean.gr'),(36541299,'AllosUser','Allounou','Athens','Monastiraki 99','6953654479','userallou@aegean.gr'),(58545652,'Elena','Elenitsa','Marousi','Amarousiou 34','6985662478','elenaki@gmail.com'),(59595959,'Dimitris','Dimitriou','Spata','Aerodromiou 33','695233489','dim@aegean.gr'),(75757575,'Maria','Maraki','Karlobasi','Limperi 56','685447892','maria@aegean.gr'),(89652332,'telikoTestUserName','telikoTestSurUserName','telikoTestCityName','telikoTestAddressName','telikoTestPhoneName','telikoTestEmailName'),(456456456,'Kostas','Kostakis','Pallini','Miltiadou 45','695883365','kok@gmail.gr'),(542542542,'Alex','Alexakis','Xalandri','Ipokratous 12','694856632','alex@aegean.gr');
/*!40000 ALTER TABLE `passenger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat`
--

DROP TABLE IF EXISTS `seat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat` (
  `SEAT_ID` int NOT NULL AUTO_INCREMENT,
  `SEAT_NUMBER` int NOT NULL,
  `FLIGHT_NUMBER` int NOT NULL,
  `IS_AVAILABLE` tinyint(1) NOT NULL,
  `CLASS` varchar(50) NOT NULL,
  `PRICE` float NOT NULL,
  `PASSENGER_PASSPORT_NUMBER` int DEFAULT NULL,
  PRIMARY KEY (`SEAT_ID`),
  UNIQUE KEY `SEAT_NUMBER` (`SEAT_NUMBER`,`FLIGHT_NUMBER`),
  KEY `FLIGHT_NUMBER` (`FLIGHT_NUMBER`),
  CONSTRAINT `seat_ibfk_1` FOREIGN KEY (`FLIGHT_NUMBER`) REFERENCES `flight` (`FLIGHT_NUMBER`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat`
--

LOCK TABLES `seat` WRITE;
/*!40000 ALTER TABLE `seat` DISABLE KEYS */;
INSERT INTO `seat` VALUES (1,1,1,1,'business',1000,NULL),(2,2,1,0,'business',1000,365412),(3,3,1,0,'business',1000,365412),(4,4,1,0,'business',1000,36541299),(5,5,1,0,'business',1000,36541299),(6,6,1,1,'business',500,NULL),(7,7,1,1,'business',500,NULL),(8,8,1,1,'business',500,NULL),(9,9,1,1,'business',500,NULL),(10,10,1,1,'business',500,NULL),(11,11,1,1,'economy',200,NULL),(12,12,1,1,'economy',200,NULL),(13,13,1,1,'economy',200,NULL),(14,14,1,1,'economy',200,NULL),(15,15,1,1,'economy',200,NULL),(16,16,1,1,'economy',100,NULL),(17,17,1,1,'economy',100,NULL),(18,18,1,1,'economy',100,NULL),(19,19,1,1,'economy',100,NULL),(20,20,1,1,'economy',100,NULL),(21,1,2,0,'business',1000,542542542),(22,2,2,0,'business',1000,542542542),(23,3,2,0,'business',1000,542542542),(24,4,2,0,'business',1000,542542542),(25,5,2,0,'business',1000,542542542),(26,6,2,0,'business',500,542542542),(27,7,2,0,'business',500,542542542),(28,8,2,0,'business',500,542542542),(29,9,2,0,'business',500,542542542),(30,10,2,0,'business',500,542542542),(31,11,2,0,'economy',200,59595959),(32,12,2,0,'economy',200,456456456),(33,13,2,0,'economy',200,456456456),(34,14,2,0,'economy',200,456456456),(35,15,2,0,'economy',200,456456456),(36,16,2,0,'economy',100,456456456),(37,17,2,0,'economy',100,75757575),(38,18,2,0,'economy',100,75757575),(39,19,2,0,'economy',100,75757575),(40,20,2,0,'economy',100,75757575),(41,1,3,0,'business',1000,753258),(42,2,3,0,'business',1000,753258),(43,3,3,0,'business',1000,753258),(44,4,3,1,'business',1000,NULL),(45,5,3,1,'business',1000,NULL),(46,6,3,1,'business',500,NULL),(47,7,3,1,'business',500,NULL),(48,8,3,1,'business',500,NULL),(49,9,3,1,'business',500,NULL),(50,10,3,1,'business',500,NULL),(51,11,3,1,'economy',200,NULL),(52,12,3,1,'economy',200,NULL),(53,13,3,1,'economy',200,NULL),(54,14,3,1,'economy',200,NULL),(55,15,3,1,'economy',200,NULL),(56,16,3,1,'economy',100,NULL),(57,17,3,1,'economy',100,NULL),(58,18,3,1,'economy',100,NULL),(59,19,3,1,'economy',100,NULL),(60,20,3,1,'economy',100,NULL),(61,1,4,1,'business',1000,NULL),(62,2,4,1,'business',1000,NULL),(63,3,4,1,'business',1000,NULL),(64,4,4,1,'business',1000,NULL),(65,5,4,1,'business',1000,NULL),(66,6,4,1,'business',500,NULL),(67,7,4,1,'business',500,NULL),(68,8,4,1,'business',500,NULL),(69,9,4,1,'business',500,NULL),(70,10,4,1,'business',500,NULL),(71,11,4,0,'economy',200,13579),(72,12,4,0,'economy',200,13579),(73,13,4,1,'economy',200,NULL),(74,14,4,1,'economy',200,NULL),(75,15,4,1,'economy',200,NULL),(76,16,4,1,'economy',100,NULL),(77,17,4,1,'economy',100,NULL),(78,18,4,1,'economy',100,NULL),(79,19,4,1,'economy',100,NULL),(80,20,4,1,'economy',100,NULL),(81,1,5,0,'business',1000,123456),(82,2,5,0,'business',1000,13579),(83,3,5,0,'business',1000,13579),(84,4,5,0,'business',1000,7412365),(85,5,5,1,'business',1000,NULL),(86,6,5,1,'business',500,NULL),(87,7,5,1,'business',500,NULL),(88,8,5,1,'business',500,NULL),(89,9,5,1,'business',500,NULL),(90,10,5,1,'business',500,NULL),(91,11,5,0,'economy',200,789789),(92,12,5,0,'economy',200,789789),(93,13,5,1,'economy',200,NULL),(94,14,5,1,'economy',200,NULL),(95,15,5,1,'economy',200,NULL),(96,16,5,1,'economy',100,NULL),(97,17,5,1,'economy',100,NULL),(98,18,5,1,'economy',100,NULL),(99,19,5,1,'economy',100,NULL),(100,20,5,1,'economy',100,NULL),(101,1,6,0,'business',1000,58545652),(102,2,6,0,'business',1000,58545652),(103,3,6,0,'business',1000,58545652),(104,4,6,1,'business',1000,NULL),(105,5,6,1,'business',1000,NULL),(106,6,6,1,'business',500,NULL),(107,7,6,1,'business',500,NULL),(108,8,6,1,'business',500,NULL),(109,9,6,1,'business',500,NULL),(110,10,6,1,'business',500,NULL),(111,11,6,1,'economy',200,NULL),(112,12,6,1,'economy',200,NULL),(113,13,6,1,'economy',200,NULL),(114,14,6,1,'economy',200,NULL),(115,15,6,1,'economy',200,NULL),(116,16,6,1,'economy',100,NULL),(117,17,6,1,'economy',100,NULL),(118,18,6,1,'economy',100,NULL),(119,19,6,1,'economy',100,NULL),(120,20,6,1,'economy',100,NULL),(121,1,7,0,'business',1000,135794),(122,2,7,0,'business',1000,1357944),(123,3,7,0,'business',1000,123),(124,4,7,0,'business',1000,365),(125,5,7,0,'business',1000,365),(126,6,7,0,'business',500,89652332),(127,7,7,0,'business',500,89652332),(128,8,7,1,'business',500,NULL),(129,9,7,1,'business',500,NULL),(130,10,7,1,'business',500,NULL),(131,11,7,1,'economy',200,NULL),(132,12,7,1,'economy',200,NULL),(133,13,7,1,'economy',200,NULL),(134,14,7,1,'economy',200,NULL),(135,15,7,1,'economy',200,NULL),(136,16,7,1,'economy',100,NULL),(137,17,7,1,'economy',100,NULL),(138,18,7,1,'economy',100,NULL),(139,19,7,1,'economy',100,NULL),(140,20,7,1,'economy',100,NULL),(141,1,8,1,'business',1000,NULL),(142,2,8,1,'business',1000,NULL),(143,3,8,1,'business',1000,NULL),(144,4,8,1,'business',1000,NULL),(145,5,8,1,'business',1000,NULL),(146,6,8,1,'business',500,NULL),(147,7,8,1,'business',500,NULL),(148,8,8,1,'business',500,NULL),(149,9,8,1,'business',500,NULL),(150,10,8,1,'business',500,NULL),(151,11,8,1,'economy',200,NULL),(152,12,8,1,'economy',200,NULL),(153,13,8,1,'economy',200,NULL),(154,14,8,1,'economy',200,NULL),(155,15,8,1,'economy',200,NULL),(156,16,8,1,'economy',100,NULL),(157,17,8,1,'economy',100,NULL),(158,18,8,1,'economy',100,NULL),(159,19,8,1,'economy',100,NULL),(160,20,8,1,'economy',100,NULL);
/*!40000 ALTER TABLE `seat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-17 22:35:57
