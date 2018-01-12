-- MySQL dump 10.16  Distrib 10.2.10-MariaDB, for osx10.13 (x86_64)
--
-- Host: localhost    Database: LMS
-- ------------------------------------------------------
-- Server version	10.2.10-MariaDB

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
-- Table structure for table `Authors`
--

DROP TABLE IF EXISTS `Authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Authors` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `last_name` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Authors`
--

LOCK TABLES `Authors` WRITE;
/*!40000 ALTER TABLE `Authors` DISABLE KEYS */;
/*!40000 ALTER TABLE `Authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Book_and_author`
--

DROP TABLE IF EXISTS `Book_and_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Book_and_author` (
  `book_ID` int(11) NOT NULL,
  `author_ID` int(11) NOT NULL,
  PRIMARY KEY (`book_ID`,`author_ID`),
  KEY `author_ID` (`author_ID`),
  CONSTRAINT `book_and_author_ibfk_1` FOREIGN KEY (`book_ID`) REFERENCES `Books_info` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_and_author_ibfk_2` FOREIGN KEY (`author_ID`) REFERENCES `Authors` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Book_and_author`
--

LOCK TABLES `Book_and_author` WRITE;
/*!40000 ALTER TABLE `Book_and_author` DISABLE KEYS */;
/*!40000 ALTER TABLE `Book_and_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Books`
--

DROP TABLE IF EXISTS `Books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Books` (
  `code` int(11) NOT NULL AUTO_INCREMENT,
  `ISBN` char(13) COLLATE utf8_polish_ci NOT NULL,
  `was_borrowed` enum('true','false') COLLATE utf8_polish_ci NOT NULL DEFAULT 'false',
  PRIMARY KEY (`code`),
  KEY `ISBN` (`ISBN`),
  CONSTRAINT `books_ibfk_1` FOREIGN KEY (`ISBN`) REFERENCES `Books_edition` (`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Books`
--

LOCK TABLES `Books` WRITE;
/*!40000 ALTER TABLE `Books` DISABLE KEYS */;
/*!40000 ALTER TABLE `Books` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER updateNumberOfCopiesInsert
  BEFORE INSERT ON Books
  FOR EACH ROW
  BEGIN
    UPDATE Books_edition SET copies = copies + 1 WHERE NEW.ISBN = Books_edition.ISBN;
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER updateNumberOfCopiesDelete
  BEFORE DELETE ON Books
  FOR EACH ROW
  BEGIN
    IF OLD.was_borrowed = 'true' THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The book was borrowed!';
    END IF;
    UPDATE Books_edition SET copies = copies - 1 WHERE OLD.ISBN = Books_edition.ISBN;
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Books_edition`
--

DROP TABLE IF EXISTS `Books_edition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Books_edition` (
  `ISBN` char(13) COLLATE utf8_polish_ci NOT NULL,
  `book_ID` int(11) NOT NULL,
  `year` char(4) COLLATE utf8_polish_ci DEFAULT NULL,
  `publisher` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `copies` int(11) NOT NULL DEFAULT 0,
  `borrowed` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ISBN`),
  KEY `book_ID` (`book_ID`),
  CONSTRAINT `books_edition_ibfk_1` FOREIGN KEY (`book_ID`) REFERENCES `Books_info` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CONSTRAINT_1` CHECK (`ISBN` regexp '^[0-9]{10}$' or `ISBN` regexp '^[0-9]{13}$'),
  CONSTRAINT `CONSTRAINT_2` CHECK (`year` regexp '^[0-9]{4}$')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Books_edition`
--

LOCK TABLES `Books_edition` WRITE;
/*!40000 ALTER TABLE `Books_edition` DISABLE KEYS */;
/*!40000 ALTER TABLE `Books_edition` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER checkBeforeDeleteEdition
  BEFORE DELETE ON Books_edition
  FOR EACH ROW
  BEGIN
    IF OLD.copies > 0 THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'There are copies!';
    END IF;
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Books_info`
--

DROP TABLE IF EXISTS `Books_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Books_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Books_info`
--

LOCK TABLES `Books_info` WRITE;
/*!40000 ALTER TABLE `Books_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `Books_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER checkBeforeDeleteBooksInfo
  BEFORE DELETE ON Books_info
  FOR EACH ROW
  BEGIN
    DECLARE sum INT;

    SET sum = (SELECT COUNT(*) FROM Books_edition WHERE book_ID = OLD.ID AND copies > 0);

    IF sum > 0 THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'There are editions!';
    END IF;
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Clients`
--

DROP TABLE IF EXISTS `Clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Clients` (
  `indx` int(11) NOT NULL,
  `type` enum('student','employee') COLLATE utf8_polish_ci NOT NULL,
  `first_name` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `last_name` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_polish_ci NOT NULL,
  `number_of_loans` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`indx`),
  CONSTRAINT `CONSTRAINT_1` CHECK (`email` like '%_@_%._%')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Clients`
--

LOCK TABLES `Clients` WRITE;
/*!40000 ALTER TABLE `Clients` DISABLE KEYS */;
/*!40000 ALTER TABLE `Clients` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER checkBeforeDeleteClient
  BEFORE DELETE ON Clients
  FOR EACH ROW
  BEGIN
    IF OLD.number_of_loans > 0 THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'There are borrowed books!';
    END IF;
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `History`
--

DROP TABLE IF EXISTS `History`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `History` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `borrow_date` date NOT NULL,
  `return_date` date NOT NULL,
  `client_indx` int(11) NOT NULL,
  `loan_ID` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `book_code` int(11) NOT NULL,
  `fine` float NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  KEY `client_indx` (`client_indx`),
  KEY `book_code` (`book_code`),
  CONSTRAINT `history_ibfk_1` FOREIGN KEY (`client_indx`) REFERENCES `Clients` (`indx`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `history_ibfk_2` FOREIGN KEY (`book_code`) REFERENCES `Books` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `History`
--

LOCK TABLES `History` WRITE;
/*!40000 ALTER TABLE `History` DISABLE KEYS */;
/*!40000 ALTER TABLE `History` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `On_loan`
--

DROP TABLE IF EXISTS `On_loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `On_loan` (
  `ID` varchar(10) COLLATE utf8_polish_ci NOT NULL,
  `borrow_date` date NOT NULL,
  `due_date` date DEFAULT NULL,
  `book_code` int(11) NOT NULL,
  `client_indx` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `book_code` (`book_code`),
  KEY `client_indx` (`client_indx`),
  CONSTRAINT `on_loan_ibfk_1` FOREIGN KEY (`book_code`) REFERENCES `Books` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `on_loan_ibfk_2` FOREIGN KEY (`client_indx`) REFERENCES `Clients` (`indx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `On_loan`
--

LOCK TABLES `On_loan` WRITE;
/*!40000 ALTER TABLE `On_loan` DISABLE KEYS */;
/*!40000 ALTER TABLE `On_loan` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER beforeInsertToOnLoan
  BEFORE INSERT ON On_loan
  FOR EACH ROW
  BEGIN
    DECLARE book_status ENUM('true', 'false');
    DECLARE currType ENUM('student', 'employee');
    DECLARE numberOfLoans INT;
    DECLARE limitOfLoans INT;
    DECLARE days INT;
    DECLARE due DATE;

    SET book_status = (SELECT was_borrowed FROM Books WHERE code = NEW.book_code);
    SET currType = (SELECT Clients.type FROM Clients
                      WHERE NEW.client_indx = Clients.indx);
    SET limitOfLoans = (SELECT books_limit FROM Rules WHERE type = currType);
    SET numberOfLoans = (SELECT number_of_loans FROM Clients WHERE indx = NEW.client_indx);

    IF book_status = 'true' THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The book was borrowed!';
    ELSEIF numberOfLoans >= limitOfLoans THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The client has too many books!';
    END IF;

    UPDATE Books SET was_borrowed = 'true' WHERE code = NEW.book_code;
    UPDATE Clients SET number_of_loans = number_of_loans + 1 WHERE indx = NEW.client_indx;

    SET days = (SELECT max_period FROM Rules
                  WHERE Rules.type = currType);
    SET due = ADDDATE(NEW.borrow_date, days);
    SET NEW.due_date = due;

    UPDATE Books_edition Be
      JOIN Books B ON Be.ISBN = B.ISBN
      SET borrowed = borrowed + 1
      WHERE NEW.book_code = B.code;
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER updateNumberOfBorrowedDelete
  BEFORE DELETE ON On_loan
  FOR EACH ROW
  BEGIN
    UPDATE Books_edition Be
      JOIN Books B ON Be.ISBN = B.ISBN
      SET borrowed = borrowed - 1
      WHERE OLD.book_code = B.code;

    UPDATE Books SET was_borrowed = 'false' WHERE code = OLD.book_code;
    UPDATE Clients SET number_of_loans = number_of_loans - 1 WHERE indx = OLD.client_indx;
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER addToHistory
  BEFORE DELETE ON On_loan
  FOR EACH ROW
  BEGIN
    DECLARE fine FLOAT;
    DECLARE fine_to_pay FLOAT;
    DECLARE days INT;
    DECLARE currType ENUM('student', 'employee');

    SET currType = (SELECT Clients.type FROM Clients
                      WHERE OLD.client_indx = Clients.indx);

    SET fine = (SELECT fine_per_day FROM Rules
                  WHERE Rules.type = currType);

    SET days = DATEDIFF(CURDATE(), OLD.due_date);

    IF days > 0 THEN
      SET fine_to_pay = days * fine;
    ELSE
      SET fine_to_pay = 0;
    END IF;

    INSERT INTO History (borrow_date, return_date, client_indx, loan_ID, book_code, fine) VALUES
      (OLD.borrow_date, CURDATE(), OLD.client_indx, OLD.ID, OLD.book_code, fine_to_pay);
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Rules`
--

DROP TABLE IF EXISTS `Rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Rules` (
  `type` enum('student','employee') COLLATE utf8_polish_ci NOT NULL,
  `books_limit` int(11) NOT NULL,
  `max_period` int(11) NOT NULL,
  `fine_per_day` float NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Rules`
--

LOCK TABLES `Rules` WRITE;
/*!40000 ALTER TABLE `Rules` DISABLE KEYS */;
INSERT INTO `Rules` VALUES ('student',10,180,0.5),('employee',10,180,0.5);
/*!40000 ALTER TABLE `Rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `login` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `type` enum('admin','librarian','service') COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('admin','admin');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-12 19:59:02
