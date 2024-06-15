-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 07, 2024 alle 19:23
-- Versione del server: 10.4.24-MariaDB
-- Versione PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jdbc1`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `todo_category`
--

CREATE TABLE `todo_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `id_category` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `todo_category`
--

INSERT INTO `todo_category` (`id`, `name`, `description`, `id_category`, `created_at`, `updated_at`) VALUES
(1, 'Work', 'lorem ipsum', 0, NULL, NULL),
(2, 'Personal', 'lorem ipsum', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `todo_todo`
--

CREATE TABLE `todo_todo` (
  `id` int(11) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `id_category` int(11) DEFAULT NULL,
  `completed` int(1) NOT NULL DEFAULT 0,
  `description` text DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

--
-- Dump dei dati per la tabella `todo_todo`
--

INSERT INTO `todo_todo` (`id`, `title`, `id_category`, `completed`, `description`, `created_at`, `updated_at`) VALUES
(17, 'lorem ipsum', 1, 0, 'lorem ipsum dolor ipsum dolor lorem', '2024-05-29 18:12:16', '2024-05-29 19:14:16'),
(18, 'Nnnnnnnnnnnn', 1, 0, 'bbbbbbbbbbbbbbbbbbbbbbbbbb', '2024-05-30 17:01:27', NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `todo_user`
--

CREATE TABLE `todo_user` (
  `id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `surname` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `role` varchar(100) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `birthdate` date DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `profile_image_url` varchar(200) DEFAULT NULL,
  `last_access` date DEFAULT NULL,
  `authentication_token` varchar(200) DEFAULT NULL,
  `password_reset_token` int(200) DEFAULT NULL,
  `login_attempts` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `todo_user`
--

INSERT INTO `todo_user` (`id`, `name`, `surname`, `email`, `password`, `username`, `role`, `created_at`, `updated_at`, `active`, `birthdate`, `address`, `phone_number`, `profile_image_url`, `last_access`, `authentication_token`, `password_reset_token`, `login_attempts`) VALUES
(1, 'Mario', 'Rossi', 'mario@rossi.it', '$2a$10$LqjcSXTUekCnmlmrWaCb.elGv/VFm2FGQCfUEO.KcOHEbFQN5ZLEC', 'mariorossi', 'admin', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '', 0, 0),
(2, 'Luigi', 'Bianchi', 'luigi@bianchi.it', '$2a$10$q7yZy7zta7ZyzQrLwUlvoOhTjZkcjtBZ0e4qtKKYeZzYrs76IwcYy', 'luiginetto13', 'admin', NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, '', 0, 0),
(3, 'Ciccio', 'Neri', 'ciccio@neri.it', '$2a$10$YuKwf4hjc8Wa5y/uaYPYz.V/NMwxTD7YC4OSd9ODO1XI3AqgJIOVi', 'ciccio', 'admin', '2024-05-13 18:28:06', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `todo_category`
--
ALTER TABLE `todo_category`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `todo_todo`
--
ALTER TABLE `todo_todo`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `todo_user`
--
ALTER TABLE `todo_user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `todo_category`
--
ALTER TABLE `todo_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `todo_todo`
--
ALTER TABLE `todo_todo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT per la tabella `todo_user`
--
ALTER TABLE `todo_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
