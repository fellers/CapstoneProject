USE client_schedule;


CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `salesmen` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `salesman_id` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `salesman_id_UNIQUE` (`salesman_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `project_managers` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `project_manager_id` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `project_manager_id_UNIQUE` (`project_manager_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `clients` (
  `client_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `salesman_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`client_id`),
  KEY `salesman_id_idx` (`salesman_id`),
  CONSTRAINT `salesman_id` FOREIGN KEY (`salesman_id`) REFERENCES `salesmen` (`salesman_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `jobs` (
  `job_id` int NOT NULL AUTO_INCREMENT,
  `job_name` varchar(45) NOT NULL,
  `progress` varchar(45) DEFAULT NULL,
  `job_notes` varchar(8000) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `last_edit` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `last_edit_by` varchar(45) DEFAULT NULL,
  `final_install_date` date DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `zip` int DEFAULT NULL,
  `client_id` int NOT NULL,
  `salesman_id` varchar(45) NOT NULL,
  `project_manager_id` varchar(45) NOT NULL,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `job_name_UNIQUE` (`job_name`),
  KEY `client_id_idx` (`client_id`),
  KEY `salesman_id_idx` (`salesman_id`),
  KEY `project_manager_id_idx` (`project_manager_id`),
  CONSTRAINT `client_id` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`),
  CONSTRAINT `project_manager_id` FOREIGN KEY (`project_manager_id`) REFERENCES `project_managers` (`project_manager_id`),
  CONSTRAINT `salesmanid` FOREIGN KEY (`salesman_id`) REFERENCES `salesmen` (`salesman_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `model_number` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT '0.00',
  `category` varchar(45) DEFAULT 'Not Set',
  `brand` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `model_number_UNIQUE` (`model_number`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `job_invoice` (
  `invoice_id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  `model_number` varchar(45) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `jobFK_idx` (`job_id`),
  KEY `productFK_idx` (`product_id`),
  CONSTRAINT `jobFK` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`job_id`),
  CONSTRAINT `productFK` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

insert into admin (name, user_name, password, email, phone) values ('admin', 'admin', 'password', 'email', '2122');
insert into salesmen (name, user_name, password, email, phone, salesman_id) VALUES ('Austin Fellows', 'afellows', 'password', 'email', '1222211', 'afellows');
insert into project_managers (name, user_name, password, email, phone, project_manager_id) VALUES ('John Doe', 'jdoe', 'password', 'email', '1222211', 'jdoe');