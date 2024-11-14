SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE DATABASE  IF NOT EXISTS javafxTest;
USE javafxTest;


CREATE TABLE IF NOT EXISTS `age_groups` (
  `AgeGroupId` int NOT NULL AUTO_INCREMENT,
  `AgeGroupTile` varchar(3) NOT NULL,
  PRIMARY KEY (`AgeGroupId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	INSERT INTO `age_groups` VALUES (1,'0+'),(2,'6+'),(3,'12+'),(4,'16+'),(5,'18+');

CREATE TABLE IF NOT EXISTS `genres` (
  `GenreId` int NOT NULL AUTO_INCREMENT,
  `GenreTitle` varchar(45) NOT NULL,
  PRIMARY KEY (`GenreId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	INSERT INTO `genres` VALUES (1,'Комедия'),(2,'Драма'),(3,'Биография'),(4,'Фантастика'),(5,'Боевик'),(6,'Анимация'),(7,'Семейный'),(8,'Приключения'),(9,'Аниме'),(10,'Детектив'),(11,'Триллер'),(12,'Ужасы'),(13,'Фэнтези'),(14,'Мистика'),(15,'Научный');

CREATE TABLE IF NOT EXISTS `films` (
  `FilmId` int NOT NULL AUTO_INCREMENT,
  `FilmTitle` varchar(45) NOT NULL,
  `AgeGroupId` int NOT NULL,
  `FilmLenght` time NOT NULL,
  `FilmDescription` text NOT NULL,
  `FilmTrailer` varchar(45) NOT NULL,
  `FilmImage` varchar(45) NOT NULL,
  `FilmCountry` varchar(45) NOT NULL,
  `FilmYear` varchar(45) NOT NULL,
  PRIMARY KEY (`FilmId`),
  KEY `fk_films_age_groups1_idx` (`AgeGroupId`),
  CONSTRAINT `fk_films_age_groups1` FOREIGN KEY (`AgeGroupId`) REFERENCES `age_groups` (`AgeGroupId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	INSERT INTO `films` VALUES (1,'Мстители: Финал',4,'03:03:00','Мстители и Стражи Галактики вступают в последнюю стадию войны с Таносом, владеющим всемогущей Перчаткой Бесконечности. Грядёт финальная битва между силами героев и Безумного Титана, которая раз и навсегда определит дальнейшую судьбу не только Земли, но и всей вселенной.','QPRtU7EqQy0','avengers.jpg','США','2019'),(2,'1+1',5,'01:52:00','Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, — молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.','StrAz2lWjR8','1+1.jpeg','Франция','2011'),(3,'Как приручить дракона 3',3,'01:44:00','Когда-то викинги жили в гармонии с драконами. В те времена они делили радость, горе… и последние штаны. Казалось, что так будет всегда, но появление загадочной Дневной Фурии изменило жизнь острова. В заключительной части головокружительной франшизы Иккинг и Беззубик столкнутся с безжалостным охотником на драконов, жаждущим уничтожить все, что им дорого.','al-XKf5IBXg','dragons.jpg','США','2018');

CREATE TABLE IF NOT EXISTS `halls_seats` (
  `HallId` int NOT NULL,
  `SeatId` int NOT NULL,
  PRIMARY KEY (`HallId`,`SeatId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	INSERT INTO `halls_seats` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),(1,40),(1,41),(1,42),(1,43),(1,44),(1,45),(1,46),(1,47),(1,48),(1,49),(1,50),(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),(2,11),(2,12),(2,13),(2,14),(2,15),(2,16),(2,17),(2,18),(2,19),(2,20),(2,21),(2,22),(2,23),(2,24),(2,25),(2,26),(2,27),(2,28),(2,29),(2,30),(2,31),(2,32),(2,33),(2,34),(2,35),(2,36),(2,37),(2,38),(2,39),(2,40),(2,41),(2,42),(2,43),(2,44),(2,45),(2,46),(2,47),(2,48),(2,49),(2,50),(3,1),(3,2),(3,3),(3,4),(3,5),(3,6),(3,7),(3,8),(3,9),(3,10),(3,11),(3,12),(3,13),(3,14),(3,15),(3,16),(3,17),(3,18),(3,19),(3,20),(3,21),(3,22),(3,23),(3,24),(3,25),(3,26),(3,27),(3,28),(3,29),(3,30),(3,31),(3,32),(3,33),(3,34),(3,35),(3,36),(3,37),(3,38),(3,39),(3,40),(3,41),(3,42),(3,43),(3,44),(3,45),(3,46),(3,47),(3,48),(3,49),(3,50),(8,1),(8,2),(8,3),(8,4),(8,5),(8,6),(8,7),(8,8),(8,9),(8,10),(8,11),(8,12),(8,13),(8,14),(8,15),(8,16),(8,17),(8,18),(8,19),(8,20),(8,21),(8,22),(8,23),(8,24),(8,25),(8,26),(8,27),(8,28),(8,29),(8,30),(8,31),(8,32),(8,33),(8,34),(8,35),(8,36),(8,37),(8,38),(8,39),(8,40),(8,41),(8,42),(8,43),(8,44),(8,45),(8,46),(8,47),(8,48),(8,49),(8,50),(9,1),(9,2),(9,3),(9,4),(9,5),(9,6),(9,7),(9,8),(9,9),(9,10),(9,11),(9,12),(9,13),(9,14),(9,15),(9,16),(9,17),(9,18),(9,19),(9,20),(9,21),(9,22),(9,23),(9,24),(9,25),(9,26),(9,27),(9,28),(9,29),(9,30),(9,31),(9,32),(9,33),(9,34),(9,35),(9,36),(9,37),(9,38),(9,39),(9,40),(9,41),(9,42),(9,43),(9,44),(9,45),(9,46),(9,47),(9,48),(9,49),(9,50),(10,1),(10,2),(10,3),(10,4),(10,5),(10,6),(10,7),(10,8),(10,9),(10,10),(10,11),(10,12),(10,13),(10,14),(10,15),(10,16),(10,17),(10,18),(10,19),(10,20),(10,21),(10,22),(10,23),(10,24),(10,25),(10,26),(10,27),(10,28),(10,29),(10,30),(10,31),(10,32),(10,33),(10,34),(10,35),(10,36),(10,37),(10,38),(10,39),(10,40),(10,41),(10,42),(10,43),(10,44),(10,45),(10,46),(10,47),(10,48),(10,49),(10,50);

CREATE TABLE IF NOT EXISTS `sessions`  (
  `SessionId` int NOT NULL AUTO_INCREMENT,
  `FilmId` int NOT NULL,
  `SessionDatetime` datetime NOT NULL,
  `SessionPrice` varchar(45) NOT NULL,
  `HallId` int NOT NULL,
  PRIMARY KEY (`SessionId`),
  KEY `fk_sessions_films_idx` (`FilmId`),
  KEY `fk_sessions_halls_seats_idx` (`HallId`),
  CONSTRAINT `fk_sessions_films` FOREIGN KEY (`FilmId`) REFERENCES `films` (`FilmId`),
  CONSTRAINT `fk_sessions_halls_seats` FOREIGN KEY (`HallId`) REFERENCES `halls_seats` (`HallId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	INSERT INTO `sessions` VALUES (15,1,'2023-12-05 23:00:00','400.0',1),(16,2,'2023-12-05 23:55:00','350.0',3),(17,1,'2023-12-06 10:00:00','300.0',8);

CREATE TABLE IF NOT EXISTS `sessions_has_halls_seats` (
  `SessionId` int NOT NULL,
  `HallId` int NOT NULL,
  `SeatId` int NOT NULL,
  `SeatStatus` varchar(45) NOT NULL DEFAULT 'Свободно',
  PRIMARY KEY (`SessionId`,`HallId`,`SeatId`),
  KEY `fk_sessions_has_halls_seats_halls_seats1_idx` (`HallId`,`SeatId`),
  KEY `fk_sessions_has_halls_seats_sessions1_idx` (`SessionId`),
  CONSTRAINT `fk_sessions_has_halls_seats_halls_seats1` FOREIGN KEY (`HallId`, `SeatId`) REFERENCES `halls_seats` (`HallId`, `SeatId`),
  CONSTRAINT `fk_sessions_has_halls_seats_sessions1` FOREIGN KEY (`SessionId`) REFERENCES `sessions` (`SessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	INSERT INTO `sessions_has_halls_seats` VALUES (15,1,1,'Свободно'),(15,1,2,'Свободно'),(15,1,3,'Свободно'),(15,1,4,'Свободно'),(15,1,5,'Свободно'),(15,1,6,'Свободно'),(15,1,7,'Свободно'),(15,1,8,'Свободно'),(15,1,9,'Свободно'),(15,1,10,'Свободно'),(15,1,11,'Свободно'),(15,1,12,'Свободно'),(15,1,13,'Свободно'),(15,1,14,'Свободно'),(15,1,15,'Свободно'),(15,1,16,'Свободно'),(15,1,17,'Свободно'),(15,1,18,'Свободно'),(15,1,19,'Свободно'),(15,1,20,'Свободно'),(15,1,21,'Свободно'),(15,1,22,'Свободно'),(15,1,23,'Свободно'),(15,1,24,'Свободно'),(15,1,25,'Свободно'),(15,1,26,'Свободно'),(15,1,27,'Свободно'),(15,1,28,'Свободно'),(15,1,29,'Свободно'),(15,1,30,'Свободно'),(15,1,31,'Свободно'),(15,1,32,'Свободно'),(15,1,33,'Свободно'),(15,1,34,'Свободно'),(15,1,35,'Свободно'),(15,1,36,'Свободно'),(15,1,37,'Свободно'),(15,1,38,'Свободно'),(15,1,39,'Свободно'),(15,1,40,'Свободно'),(15,1,41,'Свободно'),(15,1,42,'Свободно'),(15,1,43,'Свободно'),(15,1,44,'Свободно'),(15,1,45,'Свободно'),(15,1,46,'Свободно'),(15,1,47,'Свободно'),(15,1,48,'Свободно'),(15,1,49,'Свободно'),(15,1,50,'Свободно'),(16,3,1,'Свободно'),(16,3,2,'Свободно'),(16,3,3,'Свободно'),(16,3,4,'Свободно'),(16,3,5,'Свободно'),(16,3,6,'Свободно'),(16,3,7,'Свободно'),(16,3,8,'Свободно'),(16,3,9,'Свободно'),(16,3,10,'Свободно'),(16,3,11,'Свободно'),(16,3,12,'Свободно'),(16,3,13,'Свободно'),(16,3,14,'Свободно'),(16,3,15,'Свободно'),(16,3,16,'Свободно'),(16,3,17,'Свободно'),(16,3,18,'Свободно'),(16,3,19,'Свободно'),(16,3,20,'Свободно'),(16,3,21,'Свободно'),(16,3,22,'Свободно'),(16,3,23,'Свободно'),(16,3,24,'Свободно'),(16,3,25,'Свободно'),(16,3,26,'Свободно'),(16,3,27,'Свободно'),(16,3,28,'Свободно'),(16,3,29,'Свободно'),(16,3,30,'Свободно'),(16,3,31,'Свободно'),(16,3,32,'Свободно'),(16,3,33,'Свободно'),(16,3,34,'Свободно'),(16,3,35,'Свободно'),(16,3,36,'Свободно'),(16,3,37,'Свободно'),(16,3,38,'Свободно'),(16,3,39,'Свободно'),(16,3,40,'Свободно'),(16,3,41,'Свободно'),(16,3,42,'Свободно'),(16,3,43,'Свободно'),(16,3,44,'Свободно'),(16,3,45,'Свободно'),(16,3,46,'Свободно'),(16,3,47,'Свободно'),(16,3,48,'Свободно'),(16,3,49,'Свободно'),(16,3,50,'Свободно'),(17,8,1,'Занято'),(17,8,2,'Свободно'),(17,8,3,'Свободно'),(17,8,4,'Свободно'),(17,8,5,'Занято'),(17,8,6,'Свободно'),(17,8,7,'Свободно'),(17,8,8,'Свободно'),(17,8,9,'Свободно'),(17,8,10,'Свободно'),(17,8,11,'Свободно'),(17,8,12,'Свободно'),(17,8,13,'Занято'),(17,8,14,'Свободно'),(17,8,15,'Свободно'),(17,8,16,'Свободно'),(17,8,17,'Свободно'),(17,8,18,'Свободно'),(17,8,19,'Свободно'),(17,8,20,'Свободно'),(17,8,21,'Занято'),(17,8,22,'Свободно'),(17,8,23,'Свободно'),(17,8,24,'Свободно'),(17,8,25,'Свободно'),(17,8,26,'Свободно'),(17,8,27,'Свободно'),(17,8,28,'Свободно'),(17,8,29,'Свободно'),(17,8,30,'Свободно'),(17,8,31,'Свободно'),(17,8,32,'Свободно'),(17,8,33,'Свободно'),(17,8,34,'Свободно'),(17,8,35,'Свободно'),(17,8,36,'Свободно'),(17,8,37,'Свободно'),(17,8,38,'Свободно'),(17,8,39,'Свободно'),(17,8,40,'Свободно'),(17,8,41,'Свободно'),(17,8,42,'Свободно'),(17,8,43,'Свободно'),(17,8,44,'Свободно'),(17,8,45,'Свободно'),(17,8,46,'Свободно'),(17,8,47,'Свободно'),(17,8,48,'Свободно'),(17,8,49,'Свободно'),(17,8,50,'Свободно');

CREATE TABLE IF NOT EXISTS `roles` (
  `RoleId` int NOT NULL DEFAULT '1',
  `RoleName` varchar(45) NOT NULL DEFAULT 'Пользователь',
  PRIMARY KEY (`RoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	INSERT INTO `roles` VALUES (1,'Администратор'),(2,'Пользователь');

CREATE TABLE IF NOT EXISTS `users` (
  `UserNumber` varchar(11) NOT NULL,
  `UserFio` varchar(45) NOT NULL,
  `UserDateOfBirth` date NOT NULL,
  `RoleId` int NOT NULL DEFAULT '2',
  `UserPassword` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`UserNumber`),
  KEY `RoleId_idx` (`RoleId`),
  CONSTRAINT `RoleId` FOREIGN KEY (`RoleId`) REFERENCES `roles` (`RoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	INSERT INTO `users` VALUES ('89012001392','test','2023-10-30',2,'test'),('89040632692','Коверигин Иван Сергеевич','2005-07-10',2,NULL),('89200269882','Дубкова Оксана Валерьевна','1981-02-10',2,NULL),('89200401660','Дубков Матвей Алексеевич','2005-02-03',1,'admin'),('89307033698','Сычев Владислав Владимирович','2005-03-16',2,'1');

CREATE TABLE IF NOT EXISTS `films_has_genres` (
  `films_FilmId` int NOT NULL,
  `genres_GenreId` int NOT NULL,
  PRIMARY KEY (`films_FilmId`,`genres_GenreId`),
  KEY `fk_films_has_genres_genres1_idx` (`genres_GenreId`),
  KEY `fk_films_has_genres_films1_idx` (`films_FilmId`),
  CONSTRAINT `fk_films_has_genres_films1` FOREIGN KEY (`films_FilmId`) REFERENCES `films` (`FilmId`),
  CONSTRAINT `fk_films_has_genres_genres1` FOREIGN KEY (`genres_GenreId`) REFERENCES `genres` (`GenreId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	INSERT INTO `films_has_genres` VALUES (2,1),(3,1),(2,2),(2,3),(1,4),(1,5),(3,6),(3,7),(3,8);

CREATE TABLE IF NOT EXISTS `tickets`  (
  `TicketId` int NOT NULL AUTO_INCREMENT,
  `SessionId` int NOT NULL,
  `UserNumber` varchar(11) NOT NULL,
  `HallId` int NOT NULL,
  `SeatId` int NOT NULL,
  PRIMARY KEY (`TicketId`,`SeatId`),
  KEY `fk_tickets_sessions1_idx` (`SessionId`),
  KEY `fk_tickets_users1_idx` (`UserNumber`),
  KEY `fk_tickets_halls_seats_idx` (`HallId`,`SeatId`),
  CONSTRAINT `fk_tickets_halls_seats` FOREIGN KEY (`HallId`, `SeatId`) REFERENCES `halls_seats` (`HallId`, `SeatId`),
  CONSTRAINT `fk_tickets_sessions1` FOREIGN KEY (`SessionId`) REFERENCES `sessions` (`SessionId`),
  CONSTRAINT `fk_tickets_users1` FOREIGN KEY (`UserNumber`) REFERENCES `users` (`UserNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

	INSERT INTO `tickets` VALUES (1,17,'89040632692',8,1),(1,17,'89040632692',8,5),(1,17,'89040632692',8,13),(1,17,'89040632692',8,21);

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` TRIGGER `films_BEFORE_DELETE` BEFORE DELETE ON `films` FOR EACH ROW BEGIN
	DELETE FROM sessions WHERE FilmId = OLD.FilmId;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` TRIGGER `before_sessions_insert` BEFORE INSERT ON `sessions` FOR EACH ROW BEGIN
DECLARE i int;
SET i = 1;
IF NEW.HallId NOT IN(SELECT DISTINCT HallId FROM halls_seats) THEN
			WHILE i < 51 DO
				INSERT INTO halls_seats(HallId, SeatId) VALUES(NEW.HallId, i);
                SET i = i + 1;
            END WHILE;
        END IF;
	SET i = 1;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` TRIGGER `after_sessions_insert` AFTER INSERT ON `sessions` FOR EACH ROW BEGIN
    DECLARE i int;
    SET i = 1;
    WHILE i < 51 DO
        INSERT INTO sessions_has_halls_seats(SessionId, HallId, SeatId) VALUES (NEW.SessionId, NEW.HallId, i);
        SET i = i + 1;
	END WHILE;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` TRIGGER `sessions_BEFORE_DELETE` BEFORE DELETE ON `sessions` FOR EACH ROW BEGIN
	DELETE FROM tickets WHERE SessionId = OLD.SessionId;
	DELETE FROM sessions_has_halls_seats WHERE SessionId = OLD.SessionId;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`root`@`localhost` TRIGGER `tickets_AFTER_INSERT` AFTER INSERT ON `tickets` FOR EACH ROW BEGIN
	UPDATE sessions_has_halls_seats SET SeatStatus = 'Занято' WHERE SessionId = NEW.SessionId AND HallId = NEW.HallId AND SeatId = NEW.SeatId;
END ;;
DELIMITER ;

DROP PROCEDURE IF EXISTS `deleteNonActualSessions`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteNonActualSessions`()
BEGIN
	DELETE FROM sessions WHERE DATE(SessionDateTime) < DATE(now());
END ;;
DELIMITER ;
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;