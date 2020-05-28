-- MySQL dump 10.13  Distrib 8.0.20, for Linux (x86_64)
--
-- Host: localhost    Database: easytoolz
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO easytools.address (id, address, district, lat, lng, postalcode, fk_city) VALUES (1, 'Route de Valmont 12', 'Vaud', 46.43081, 6.92634, '1823', 1);
INSERT INTO easytools.address (id, address, district, lat, lng, postalcode, fk_city) VALUES (2, 'Rue des Laurelles 5', 'Vaud', 46.61298, 6.50762, '1304', 5);
INSERT INTO easytools.address (id, address, district, lat, lng, postalcode, fk_city) VALUES (3, 'Rue de la Maison Rouge', 'Vaud', 46.77881, 6.64282, '1400', 4);
INSERT INTO easytools.address (id, address, district, lat, lng, postalcode, fk_city) VALUES (4, 'Place de la Cathédrale', 'Vaud', 46.52240, 6.63513, '1005', 2);
INSERT INTO easytools.address (id, address, district, lat, lng, postalcode, fk_city) VALUES (5, 'Avenue de la Gare 5', 'Valais', 46.10221, 7.07382, '1920', 3);
INSERT INTO easytools.address (id, address, district, lat, lng, postalcode, fk_city) VALUES (6, 'Allée de la Petite Prairie 14', 'Vaud', 46.39053, 6.22264, '1260', 6);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Glion',1),(2,'Lausanne',1),(3,'Martigny',1),(4,'Yverdon-Les-Bains',1),(5,'Cossonay',1),(6,'Nyon',1);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `conversation`
--

LOCK TABLES `conversation` WRITE;
/*!40000 ALTER TABLE `conversation` DISABLE KEYS */;
/*!40000 ALTER TABLE `conversation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'Suisse');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ezobject`
--

LOCK TABLES `ezobject` WRITE;
/*!40000 ALTER TABLE `ezobject` DISABLE KEYS */;
INSERT INTO `ezobject` VALUES (1,'Une pelle de jardinage en bon état. N\'étant pas un très grand jardinier, je préfère la prêter à des personnes plus capables que moi.',1,'Pelle','fukuchimiste'),(2,'Une perceuse BOSCH en bon état, vert, fonctionnelle. Utilisée de temps en temps lorsque Madame doit accrocher la nouvelle peinture qu\'elle a réalisée.',1,'Perceuse BOSCH','double-i'),(3,'vidaXL Arc à poulies avec accessoires et flèches en fibre de verre. Je tire uniquement pour le plaisir ;) Par conséquent, je l\'utilise assez peu',1,'vidaXL Arc à poulies','vitorvaz'),(4,'Un skateboard de bonne qualité acheté en pensant que je serais peut-être pas si mauvais et pourtant. Par conséquent, autant que certaines personnes l\'utilisent, je ne peux pas le vendre.',1,'Skateboard','heymanuel'),(5,'Elle est bonne état. Elle n\'a pas été souvent utilisée car son propriétaire est beaucoup trop mauvais au ping-pong.',1,'raquette de ping pong','fukuchimiste'),(6,'D130 DREADNOUGHT GUITARE ACOUSTIQUE JM FOREST JMFD130. Ne convient plus à ma taille, mais je veux la garder, du coup je préfère la prêter.',1,'Guitare classique','mauricel'),(7,'De bonne jumelles vous permettant d\'apprécier les bouquetins de nos montagnes ;) Avec un p\'tit plus que je l\'utilise uniquement pendant le mois d\'été',1,'Jumelles','robinr'),(8,'Sac à dos de randonnée, permettant de contenir une bonne quantité de nourriture et de boissons. Sous-utilisé car pas sportif',1,'Sac à dos','heymanuel'),(9,'Une scie classique bien qu\'utilisée, ne m\'est plus réellement utile. Je préfère du coup en faire profiter à d\'autres personnes.',1,'Scie classique','double-i'),(10,'Pour vos dîners en montagne avec les coupains ;) Vachement pratique, je vous l\'assure :D',1,'TTM Raclette','robinr'),(11,'Montrez votre fierté et votre nationalisme pour notre belle patrie qu\'est la Suisse ! Utilisé seulement au premier août et à quelques manifestations ;)',1,'Drapeau suisse','fukuchimiste'),(12,'Besoin de musique pour vos soirées ? J\'ai un p\'tit haut-parleur JBL bluetooth que j\'utilise que de temps en temps. Prêté volontiers, mais à rendre !',1,'Haut-Parleur JBL','heymanuel'),(13,'Une agrafeuse de bureau classique. Rien de particulier à vous dire. Hormis, profitez ;)',1,'Agrafeuse','mauricel');
/*!40000 ALTER TABLE `ezobject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ezobject_image`
--

LOCK TABLES `ezobject_image` WRITE;
/*!40000 ALTER TABLE `ezobject_image` DISABLE KEYS */;
INSERT INTO `ezobject_image` VALUES (1,_binary '','1fukuchimistepelle.jpg',1),(2,_binary '','5fukuchimisteraquettepingpong.jpg',5),(3,_binary '','11fukuchimistesuissedrapeau.jpg',11),(4,_binary '','2double-iperceuseBosch.jpg',2),(5,_binary '','9double-iscie.jpg',9),(6,_binary '','4heymanuelskateboard.jpeg',4),(7,_binary '','8heymanuelsac_a_dos.jpg',8),(8,_binary '','12heymanuelhautparleurjbl.jpg',12),(9,_binary '\0','7robinrjumelle.jpg',7),(10,_binary '','7robinrjumelle.jpg',7),(11,_binary '','10robinrttmraclette.jpg',10),(12,_binary '','3vitorvazarc.jpg',3),(13,_binary '','6mauricelguitare.jpg',6),(14,_binary '','13mauricelagrapheuse.jpg',13);
/*!40000 ALTER TABLE `ezobject_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ezobject_tag`
--

LOCK TABLES `ezobject_tag` WRITE;
/*!40000 ALTER TABLE `ezobject_tag` DISABLE KEYS */;
INSERT INTO `ezobject_tag` VALUES (7,'autre'),(11,'autre'),(1,'bricolage'),(2,'bricolage'),(9,'bricolage'),(13,'bureau'),(1,'ete'),(7,'ete'),(8,'ete'),(7,'hiver'),(10,'hiver'),(1,'jardin'),(3,'jeu'),(5,'jeu'),(6,'musique'),(12,'musique'),(4,'skateboard'),(3,'sport'),(4,'sport');
/*!40000 ALTER TABLE `ezobject_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `period`
--

LOCK TABLES `period` WRITE;
/*!40000 ALTER TABLE `period` DISABLE KEYS */;
/*!40000 ALTER TABLE `period` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES ('autre'),('bois'),('bricolage'),('bureau'),('dessin'),('electricite'),('ete'),('hiver'),('jardin'),('jeu'),('musique'),('neige'),('skateboard'),('sport');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('double-i','Ilias.Goujgali@heig-vd.ch','Ilias',_binary '','Goujgali','$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya',3),('fukuchimiste','bastien.potet@heig-vd.ch','bastien',_binary '\0','potet','$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya',5),('heymanuel','emmanuel.janssens@heig-vd.ch','emmanuel',_binary '\0','janssens','$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya',1),('mauricel','Maurice.Lehman@heig-vd.ch','Maurice',_binary '\0','Lehman','$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya',4),('robinr','Robin.Reuteler@heig-vd.ch','Robin',_binary '\0','Reuteler','$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya',6),('vitorvaz','Vitor.vazafonzo@heig-vd.ch','Vitor',_binary '\0','Vaz Afomzo','$2a$10$8sFTFnSd.COg/GFQG9BIruaI5SJYtcmewjFunqo4CbtFtTpMW2/Ya',2);
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

-- Dump completed on 2020-05-26 13:34:54
