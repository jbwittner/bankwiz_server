-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : mysql
-- Généré le : ven. 16 fév. 2024 à 21:18
-- Version du serveur : 8.0.33
-- Version de PHP : 8.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bankwiz_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `BANK_ACCOUNT`
--

DROP TABLE IF EXISTS `BANK_ACCOUNT`;
CREATE TABLE `BANK_ACCOUNT` (
  `BASE_AMOUNT` int NOT NULL,
  `GROUP_ID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `BANK_ACCOUNT_NAME` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CURRENCY` enum('EUR','USD') COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `GROUP_RIGHT`
--

DROP TABLE IF EXISTS `GROUP_RIGHT`;
CREATE TABLE `GROUP_RIGHT` (
  `GROUP_ID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `USER_ID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `GROUP_RIGHT` enum('READ','WRITE','ADMIN') COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `TRANSACTION`
--

DROP TABLE IF EXISTS `TRANSACTION`;
CREATE TABLE `TRANSACTION` (
  `DECIMAL_AMOUNT` int NOT NULL,
  `BANK_ACCOUNT_ID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `COMMENT` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `USER_ACCOUNT`
--

DROP TABLE IF EXISTS `USER_ACCOUNT`;
CREATE TABLE `USER_ACCOUNT` (
  `ID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `AUTH_ID` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `EMAIL` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `USER_GROUP`
--

DROP TABLE IF EXISTS `USER_GROUP`;
CREATE TABLE `USER_GROUP` (
  `GROUP_ID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `GROUP_NAME` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `BANK_ACCOUNT`
--
ALTER TABLE `BANK_ACCOUNT`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_GROUPRELATEDENTITY_GROUP` (`GROUP_ID`);

--
-- Index pour la table `GROUP_RIGHT`
--
ALTER TABLE `GROUP_RIGHT`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_GROUPRIGHT_GROUP` (`GROUP_ID`),
  ADD KEY `FK_GROUPRIGHT_USER` (`USER_ID`);

--
-- Index pour la table `TRANSACTION`
--
ALTER TABLE `TRANSACTION`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_TRANSACTION_BANKACCOUNT` (`BANK_ACCOUNT_ID`);

--
-- Index pour la table `USER_ACCOUNT`
--
ALTER TABLE `USER_ACCOUNT`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `USER_GROUP`
--
ALTER TABLE `USER_GROUP`
  ADD PRIMARY KEY (`GROUP_ID`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `BANK_ACCOUNT`
--
ALTER TABLE `BANK_ACCOUNT`
  ADD CONSTRAINT `FK_GROUPRELATEDENTITY_GROUP` FOREIGN KEY (`GROUP_ID`) REFERENCES `USER_GROUP` (`GROUP_ID`);

--
-- Contraintes pour la table `GROUP_RIGHT`
--
ALTER TABLE `GROUP_RIGHT`
  ADD CONSTRAINT `FK_GROUPRIGHT_GROUP` FOREIGN KEY (`GROUP_ID`) REFERENCES `USER_GROUP` (`GROUP_ID`),
  ADD CONSTRAINT `FK_GROUPRIGHT_USER` FOREIGN KEY (`USER_ID`) REFERENCES `USER_ACCOUNT` (`ID`);

--
-- Contraintes pour la table `TRANSACTION`
--
ALTER TABLE `TRANSACTION`
  ADD CONSTRAINT `FK_TRANSACTION_BANKACCOUNT` FOREIGN KEY (`BANK_ACCOUNT_ID`) REFERENCES `BANK_ACCOUNT` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
