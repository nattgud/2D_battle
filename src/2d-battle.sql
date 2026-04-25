-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: battle_game
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

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
-- Table structure for table `game_units`
--

DROP TABLE IF EXISTS `game_units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_units` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `game` int(3) NOT NULL,
  `monster` int(3) NOT NULL,
  `team` tinyint(4) NOT NULL DEFAULT 0,
  `x` int(3) NOT NULL,
  `y` int(3) NOT NULL,
  `health` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_units`
--

LOCK TABLES `game_units` WRITE;
/*!40000 ALTER TABLE `game_units` DISABLE KEYS */;
/*!40000 ALTER TABLE `game_units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `games` (
  `id` int(11) NOT NULL,
  `turn` int(11) NOT NULL,
  `playername` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monsters`
--

DROP TABLE IF EXISTS `monsters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monsters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `distance` int(2) NOT NULL,
  `health` int(2) NOT NULL,
  `damage` int(2) NOT NULL,
  `race` int(11) NOT NULL,
  `ranged` tinyint(1) NOT NULL,
  `flying` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monsters`
--

LOCK TABLES `monsters` WRITE;
/*!40000 ALTER TABLE `monsters` DISABLE KEYS */;
INSERT INTO `monsters` VALUES (1,'basilisk',5,220,28,2,0,0),(2,'centaur',8,160,32,2,1,0),(3,'harpy',14,110,26,2,0,1),(4,'lizard',3,140,20,2,1,0),(5,'raven',40,90,34,2,0,1),(6,'scorpion',3,180,30,2,0,0),(7,'snake',4,120,27,2,0,0),(8,'troll',7,300,22,2,0,0),(9,'wolf',18,130,29,2,0,0),(10,'angel',25,180,35,5,0,1),(11,'centaur_paladin',15,240,30,5,0,0),(12,'daeva',11,140,34,5,0,1),(13,'ereshkigal',3,400,60,5,0,0),(14,'holy_dragon',19,320,33,5,0,1),(15,'paladin',6,260,26,5,0,0),(16,'priest',4,80,65,5,1,0),(17,'titan',5,400,25,5,1,0),(18,'draconic',9,260,34,4,0,0),(19,'dragon',40,350,36,4,0,1),(20,'drake',9,220,32,4,0,0),(21,'fire_dragon',8,300,42,4,1,1),(22,'hydra',2,350,79,4,0,0),(23,'ice_dragon',18,310,25,4,1,1),(24,'shadow_dragon',12,270,38,4,0,1),(25,'wyvern',10,200,32,4,0,1),(26,'archer',2,110,54,1,1,0),(27,'death_knight',16,250,29,1,0,0),(28,'giant',3,650,20,1,1,0),(29,'griffon',40,210,35,1,0,1),(30,'hippogriff',21,190,33,1,0,1),(31,'human',7,150,28,1,0,0),(32,'juggernaut',5,450,26,1,0,0),(33,'slave',45,90,18,1,0,0),(34,'air_elemental',40,140,37,3,1,0),(35,'eye',4,100,35,3,1,0),(36,'fire_elemental',15,180,38,3,1,0),(37,'frost_giant',6,380,30,3,0,0),(38,'jelly',7,450,20,3,0,0),(39,'phoenix',20,170,42,3,0,1),(40,'shadow_weaver',5,130,49,3,0,0),(41,'shapeshifter',8,300,32,3,0,0),(42,'treant',4,400,24,3,0,0),(43,'balrog',10,150,71,6,0,0),(44,'bone_dragon',40,300,35,6,0,1),(45,'efreet',12,210,38,6,1,1),(46,'ghost',3,480,16,6,0,0),(47,'hellwing',23,190,64,6,0,1),(48,'imp',18,110,39,6,0,0),(49,'lich',2,150,55,6,1,0),(50,'reaper',8,170,43,6,0,0),(51,'skeletal_warrior',8,320,29,6,0,0);
/*!40000 ALTER TABLE `monsters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monsters_strengths`
--

DROP TABLE IF EXISTS `monsters_strengths`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monsters_strengths` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `strength` int(11) NOT NULL,
  `monster` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monsters_strengths`
--

LOCK TABLES `monsters_strengths` WRITE;
/*!40000 ALTER TABLE `monsters_strengths` DISABLE KEYS */;
INSERT INTO `monsters_strengths` VALUES (3,12,2),(5,12,4),(9,11,8),(10,11,9),(12,11,11),(14,11,13),(15,11,14),(16,11,15),(17,12,16),(18,12,17),(19,11,18),(21,11,20),(22,12,21),(24,12,23),(25,13,24),(27,12,26),(28,11,27),(29,12,28),(31,11,30),(33,11,32),(35,12,34),(36,13,35),(37,12,36),(38,11,37),(39,13,38),(41,13,40),(42,13,41),(43,11,42),(46,12,45),(47,13,46),(50,12,49),(51,13,50),(52,11,51),(53,1,23),(54,1,37),(55,2,21),(56,2,36),(57,2,43),(58,1,22),(59,3,28),(60,3,38),(61,3,42),(62,4,14),(63,4,19),(64,4,25),(65,4,34),(66,4,47),(67,8,10),(68,8,12),(69,8,13),(70,8,14),(71,8,16),(72,9,24),(73,9,27),(74,9,40),(75,9,45),(76,9,47),(77,9,48),(78,9,49),(79,9,50),(80,9,51),(81,2,39),(82,1,1),(83,14,3),(84,14,5),(85,14,9),(86,14,14),(87,14,19),(88,14,21),(89,14,23),(90,14,24),(91,14,25),(92,14,29),(93,14,30),(94,14,34),(95,14,36),(96,14,39),(97,14,44),(98,14,45),(99,14,46),(100,14,47),(101,15,4),(102,15,6),(103,15,11),(104,15,12),(105,15,13),(106,15,14),(107,15,15),(108,15,18),(109,15,19),(110,15,20),(111,15,21),(112,15,23),(113,15,24),(114,15,27),(115,15,30),(116,15,32),(117,15,37),(118,15,43),(119,15,44),(120,15,51),(121,16,14),(122,16,17),(123,16,19),(124,16,21),(125,16,22),(126,16,23),(127,16,24),(128,16,28),(129,16,35),(130,16,37),(131,16,42),(132,16,43),(133,16,44),(134,14,48),(135,10,2),(136,10,3),(137,10,11),(138,10,15),(139,10,16),(140,10,17),(141,10,18),(142,10,26),(143,10,27),(144,10,28),(145,10,31),(146,10,32),(147,10,33),(148,10,37),(149,10,40),(150,10,41),(151,10,0);
/*!40000 ALTER TABLE `monsters_strengths` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `races`
--

DROP TABLE IF EXISTS `races`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `races` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `strong` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `races`
--

LOCK TABLES `races` WRITE;
/*!40000 ALTER TABLE `races` DISABLE KEYS */;
INSERT INTO `races` VALUES (1,'mortal','beast'),(2,'beast','magic'),(3,'magic','dragon'),(4,'dragon','divine'),(5,'divine','unholy'),(6,'unholy','mortal');
/*!40000 ALTER TABLE `races` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strengths`
--

DROP TABLE IF EXISTS `strengths`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `strengths` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `against` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strengths`
--

LOCK TABLES `strengths` WRITE;
/*!40000 ALTER TABLE `strengths` DISABLE KEYS */;
INSERT INTO `strengths` VALUES (1,'water',2),(2,'fire',3),(3,'earth',4),(4,'wind',1),(8,'holy',9),(9,'demonic',10),(10,'human',0),(11,'warrior',12),(12,'caster',13),(13,'psychic',11),(14,'fast',16),(15,'armored',14),(16,'giant',15),(17,'cunning',15);
/*!40000 ALTER TABLE `strengths` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-25 14:08:50
