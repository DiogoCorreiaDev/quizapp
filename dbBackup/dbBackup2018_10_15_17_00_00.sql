-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: quizz
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `quizz`
--

DROP TABLE IF EXISTS `quizz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `quizz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `beginDate` timestamp NULL DEFAULT NULL,
  `endDate` timestamp NULL DEFAULT NULL,
  `available` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizz`
--

LOCK TABLES `quizz` WRITE;
/*!40000 ALTER TABLE `quizz` DISABLE KEYS */;
INSERT INTO `quizz` VALUES (1,'Questionário de Saúde','2018-11-08 22:53:11','2018-11-11 00:00:00',NULL),(2,'Hábitos Alimentares','2018-08-08 21:53:11','2018-08-09 23:00:00',NULL),(5,'NovoQuest','2018-10-08 11:14:24','2018-10-10 11:14:24',NULL),(6,'Cinema','2018-10-10 11:07:41','2018-10-12 11:07:41',NULL),(8,'NBA','2018-10-15 08:34:26','2018-11-17 09:34:26',NULL);
/*!40000 ALTER TABLE `quizz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizz_question`
--

DROP TABLE IF EXISTS `quizz_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `quizz_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quizz_id` int(11) NOT NULL,
  `text` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `quizz_id_idx` (`quizz_id`),
  CONSTRAINT `quizz_id` FOREIGN KEY (`quizz_id`) REFERENCES `quizz` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizz_question`
--

LOCK TABLES `quizz_question` WRITE;
/*!40000 ALTER TABLE `quizz_question` DISABLE KEYS */;
INSERT INTO `quizz_question` VALUES (1,1,'Tem algum problema de Saúde?'),(2,1,'É praticante habitual de desporto?'),(3,1,'Tem histórico familiar de doenças?'),(4,1,'Considera o seu dia-a-dia stressante?'),(11,5,'pergunta1'),(12,5,'pergunta3'),(13,6,'Qual é o seu género favorito?'),(14,6,'Qual é o seu ator favorito?'),(15,6,'Qual é o seu realizador favorito?'),(18,8,'Qual é o seu jogador favorito?'),(19,8,'Qual é a sua equipa favorita?'),(20,8,'Quem é que vai ganhar o campeonato em 2019?');
/*!40000 ALTER TABLE `quizz_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizz_question_option`
--

DROP TABLE IF EXISTS `quizz_question_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `quizz_question_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quizz_question_id` int(11) NOT NULL,
  `text` varchar(45) DEFAULT NULL,
  `is_correct` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `quizz_question_id_idx` (`quizz_question_id`),
  CONSTRAINT `quizz_question_id` FOREIGN KEY (`quizz_question_id`) REFERENCES `quizz_question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizz_question_option`
--

LOCK TABLES `quizz_question_option` WRITE;
/*!40000 ALTER TABLE `quizz_question_option` DISABLE KEYS */;
INSERT INTO `quizz_question_option` VALUES (1,1,'sim',NULL),(2,1,'não',NULL),(3,2,'Sim',NULL),(4,2,'Não',NULL),(5,3,'SIm',NULL),(6,3,'NÃo',NULL),(7,4,'SIM',NULL),(8,4,'NÃO',NULL),(24,11,'respota2',NULL),(25,11,'respota3',NULL),(26,12,'respot23',NULL),(27,12,'preoasdo23',NULL),(28,13,'Thriller',NULL),(29,13,'Drama',NULL),(30,13,'Comédia',NULL),(31,14,'Daniel Day Lewis',NULL),(32,14,'Justin Bieber',NULL),(33,14,'Futre',NULL),(34,15,'PTA',NULL),(35,15,'Paul T. Anderson',NULL),(36,15,'Paul Thomas Anderson',NULL),(43,18,'Michael Jordan',NULL),(44,18,'Lebron James',NULL),(45,18,'Kobe Bryant',NULL),(46,19,'Los Angeles Lakers',NULL),(47,19,'Chicago Bulls',NULL),(48,19,'Boston Celtics',NULL),(49,20,'Warriors',NULL),(50,20,'Rockets',NULL),(51,20,'Celtics',NULL);
/*!40000 ALTER TABLE `quizz_question_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizz_user_answer`
--

DROP TABLE IF EXISTS `quizz_user_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `quizz_user_answer` (
  `id_user` int(11) NOT NULL,
  `quizz_question_id` int(11) NOT NULL,
  `quizz_question_option_id` int(11) NOT NULL,
  `answerTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_user`,`quizz_question_id`,`quizz_question_option_id`),
  KEY `quizz_question_id_idx` (`quizz_question_id`),
  KEY `optionID_idx` (`quizz_question_option_id`),
  CONSTRAINT `iduser` FOREIGN KEY (`id_user`) REFERENCES `user` (`iduser`),
  CONSTRAINT `optionID` FOREIGN KEY (`quizz_question_option_id`) REFERENCES `quizz_question_option` (`id`),
  CONSTRAINT `questionID` FOREIGN KEY (`quizz_question_id`) REFERENCES `quizz_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizz_user_answer`
--

LOCK TABLES `quizz_user_answer` WRITE;
/*!40000 ALTER TABLE `quizz_user_answer` DISABLE KEYS */;
INSERT INTO `quizz_user_answer` VALUES (1,1,2,'2018-10-15 08:36:22'),(1,2,4,'2018-10-15 08:36:22'),(1,3,6,'2018-10-15 08:36:22'),(1,4,8,'2018-10-15 08:36:22'),(1,11,25,'2018-10-10 10:23:30'),(1,12,27,'2018-10-10 10:23:31'),(1,13,29,'2018-10-10 11:07:52'),(1,14,31,'2018-10-10 11:07:53'),(1,15,36,'2018-10-10 11:07:53'),(1,18,45,'2018-10-15 08:36:24'),(1,19,48,'2018-10-15 08:36:24'),(1,20,49,'2018-10-15 08:36:24'),(2,1,2,'2018-10-15 08:35:24'),(2,2,4,'2018-10-15 08:35:24'),(2,3,6,'2018-10-15 08:35:24'),(2,4,8,'2018-10-15 08:35:24'),(2,11,25,'2018-10-10 10:24:33'),(2,12,27,'2018-10-10 10:24:34'),(2,18,43,'2018-10-15 08:35:24'),(2,19,48,'2018-10-15 08:35:24'),(2,20,51,'2018-10-15 08:35:25'),(3,1,2,'2018-10-15 08:35:55'),(3,2,4,'2018-10-15 08:35:55'),(3,3,6,'2018-10-15 08:35:55'),(3,4,8,'2018-10-15 08:35:56'),(3,11,25,'2018-10-08 13:43:03'),(3,12,26,'2018-10-08 13:43:04'),(3,18,45,'2018-10-15 08:35:56'),(3,19,47,'2018-10-15 08:35:56'),(3,20,51,'2018-10-15 08:35:57'),(4,1,2,'2018-10-15 08:37:20'),(4,2,4,'2018-10-15 08:37:20'),(4,3,6,'2018-10-15 08:37:20'),(4,4,8,'2018-10-15 08:37:20'),(4,18,45,'2018-10-15 08:37:22'),(4,19,47,'2018-10-15 08:37:22'),(4,20,49,'2018-10-15 08:37:22'),(5,1,2,'2018-10-15 08:38:18'),(5,2,4,'2018-10-15 08:38:18'),(5,3,6,'2018-10-15 08:38:18'),(5,4,8,'2018-10-15 08:38:18'),(5,18,44,'2018-10-15 08:38:20'),(5,19,47,'2018-10-15 08:38:20'),(5,20,50,'2018-10-15 08:38:20'),(6,1,2,'2018-10-15 08:39:35'),(6,2,4,'2018-10-15 08:39:35'),(6,3,6,'2018-10-15 08:39:35'),(6,4,8,'2018-10-15 08:39:35'),(6,18,43,'2018-10-15 08:39:37'),(6,19,46,'2018-10-15 08:39:38'),(6,20,51,'2018-10-15 08:39:38'),(7,1,2,'2018-10-15 08:42:04'),(7,2,4,'2018-10-15 08:42:04'),(7,3,6,'2018-10-15 08:42:05'),(7,4,8,'2018-10-15 08:42:05'),(7,18,44,'2018-10-15 08:42:07'),(7,19,47,'2018-10-15 08:42:07'),(7,20,50,'2018-10-15 08:42:07');
/*!40000 ALTER TABLE `quizz_user_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` char(76) NOT NULL,
  `role` enum('admin','creator','user') NOT NULL,
  PRIMARY KEY (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Braz','Rui','ruipbras@gmail.com','abcdef','admin'),(2,'José','António','aj@gmail.com','abcdef','creator'),(3,'Antonieta','Maria','ma@gmail.com','abcdef','creator'),(4,'Correia','Diogo','diogomiguelc@hotmail.com','password','user'),(5,'Ronaldo','Cristiano','cronaldo@gmail.com','ronaldo','user'),(6,'Pereira','Tiago','tpereira@gmail.com','abcdef','user'),(7,'Santos','Gustavo','gsantos@hotmail.com','abcdef','user');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-15 17:00:00
