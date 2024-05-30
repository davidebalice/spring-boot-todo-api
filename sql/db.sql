

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";




CREATE TABLE `api_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `id_category` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



INSERT INTO `api_category` (`id`, `name`, `description`, `id_category`) VALUES
(1, 'Tv', 'lorem ipsum', 0),
(2, 'Smartphone', 'lorem ipsum', 0),
(3, 'Game', 'lorem ipsum', 0),
(4, 'Book', 'lorem ipsum', 0),
(5, 'Ebook', 'Lorem ipsum', 0);


CREATE TABLE `api_customer` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `surname` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `tel` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



INSERT INTO `api_customer` (`id`, `name`, `surname`, `code`, `email`, `tel`, `username`, `password`) VALUES
(1, 'Mario', 'Rossi', 'MREFRWGREGGERG', 'mario@rossi.it', NULL, 'mariorossi', '12345678'),
(2, 'Laura', 'Ambrosini', 'LGGBHIGERGE23432', 'luigi@bianchi.it', NULL, NULL, NULL),
(3, 'Mario', 'Rossi', 'MREFRWGREGGERG', 'mario@rossi.it', NULL, 'mariorossi', '12345678'),
(6, 'Angelo', 'Bianchi', 'ANGBNC46346FWVD', 'bianchi@angelo.it', NULL, NULL, NULL);



CREATE TABLE `api_product` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `id_category` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `price` double(8,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;



INSERT INTO `api_product` (`id`, `name`, `id_category`, `description`, `price`) VALUES
(2, 'Tv 65 oled', 1, NULL, 2000.00),
(4, 'Tv 45 led', 1, NULL, 2000.00),
(5, 'iPhone', 2, NULL, 11.00),
(6, 'Xiaomi', 2, NULL, 22.00),
(8, 'Tv 85 oled', 1, 'lorem ipsum', 2800.00);



CREATE TABLE `api_user` (
  `id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `surname` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `role` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



INSERT INTO `api_user` (`id`, `name`, `surname`, `email`, `password`, `username`, `role`) VALUES
(1, 'Mario', 'Rossi', 'mario@rossi.it', '$2a$10$LqjcSXTUekCnmlmrWaCb.elGv/VFm2FGQCfUEO.KcOHEbFQN5ZLEC', 'mariorossi', 'admin');



ALTER TABLE `api_category`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `api_customer`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `api_product`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `api_user`
  ADD PRIMARY KEY (`id`);


ALTER TABLE `api_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;


ALTER TABLE `api_customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;


ALTER TABLE `api_product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;


ALTER TABLE `api_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;


