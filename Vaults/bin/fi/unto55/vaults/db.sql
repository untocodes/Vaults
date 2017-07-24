-- phpMyAdmin SQL Dump
-- version 3.1.3.1
-- http://www.phpmyadmin.net
--
-- Palvelin: localhost
-- Luontiaika: 05.02.2017 klo 18:38
-- Palvelimen versio: 5.1.33
-- PHP:n versio: 5.2.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Tietokanta: `vaults`
--

-- --------------------------------------------------------

--
-- Rakenne taululle `inventories`
--

CREATE TABLE IF NOT EXISTS `inventories` (
  `vaultid` varchar(999) NOT NULL,
  `base` longtext NOT NULL,
  PRIMARY KEY (`vaultid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;