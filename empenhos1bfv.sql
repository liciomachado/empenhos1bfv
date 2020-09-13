-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: empenhos1bfv
-- ------------------------------------------------------
-- Server version	5.7.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `empenho`
--

DROP TABLE IF EXISTS `empenho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `empenho` (
  `id_empenho` int(11) NOT NULL AUTO_INCREMENT,
  `numero_empenho` varchar(255) DEFAULT NULL,
  `empresa_id_empresa` int(11) DEFAULT NULL,
  `destino` varchar(255) DEFAULT NULL,
  `valor_total` double NOT NULL,
  `empenho_digitalizado` longblob,
  `data_empenho` datetime DEFAULT NULL,
  `etapa` int(11) NOT NULL,
  `usuario_id_usuario` int(11) DEFAULT NULL,
  `saldo` double DEFAULT NULL,
  `saldo_utilizado` double DEFAULT NULL,
  PRIMARY KEY (`id_empenho`)
) ENGINE=InnoDB AUTO_INCREMENT=866 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `empresa` (
  `id_empresa` int(11) NOT NULL AUTO_INCREMENT,
  `cnpj` varchar(255) DEFAULT NULL,
  `contato` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=339 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notafiscal`
--

DROP TABLE IF EXISTS `notafiscal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `notafiscal` (
  `id_nota_fiscal` int(11) NOT NULL AUTO_INCREMENT,
  `chave_acesso` varchar(255) DEFAULT NULL,
  `data_emissao` date DEFAULT NULL,
  `data_protocolado` date DEFAULT NULL,
  `data_recebido` date DEFAULT NULL,
  `num_nota` int(11) NOT NULL,
  `valor_total` double NOT NULL,
  `empenho_id_empenho` int(11) DEFAULT NULL,
  `secao_id_secao` int(11) DEFAULT NULL,
  `usuario_id_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_nota_fiscal`),
  KEY `FKi00odqwklgvfyxmm105nsuqrl` (`secao_id_secao`),
  CONSTRAINT `FKi00odqwklgvfyxmm105nsuqrl` FOREIGN KEY (`secao_id_secao`) REFERENCES `secao` (`id_secao`)
) ENGINE=InnoDB AUTO_INCREMENT=956 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `observacoes`
--

DROP TABLE IF EXISTS `observacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `observacoes` (
  `id_obs` int(11) NOT NULL,
  `data_obs` date DEFAULT NULL,
  `observacao` longtext,
  `empenho_id_empenho` int(11) DEFAULT NULL,
  `usuario_id_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_obs`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `observacoes_empresa`
--

DROP TABLE IF EXISTS `observacoes_empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `observacoes_empresa` (
  `id_obs` int(11) NOT NULL AUTO_INCREMENT,
  `data_obs` date DEFAULT NULL,
  `observacao` longtext,
  `empresa_id_empresa` int(11) DEFAULT NULL,
  `usuario_id_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_obs`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `protocolo`
--

DROP TABLE IF EXISTS `protocolo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `protocolo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `etapa_protocolo` int(11) NOT NULL,
  `nota_fiscal_id_nota_fiscal` int(11) NOT NULL,
  `secao_id_secao` int(11) NOT NULL,
  `usuario_id_usuario` int(11) NOT NULL,
  `usuario_recebedor_id_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkmy7c1w57v0y9nhrbd99r3xw2` (`secao_id_secao`),
  CONSTRAINT `FKkmy7c1w57v0y9nhrbd99r3xw2` FOREIGN KEY (`secao_id_secao`) REFERENCES `secao` (`id_secao`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `secao`
--

DROP TABLE IF EXISTS `secao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `secao` (
  `id_secao` int(11) NOT NULL,
  `nome_secao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_secao`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `graduacao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `senha_gmail` varchar(255) DEFAULT NULL,
  `tipo_user` varchar(255) DEFAULT NULL,
  `secao_id_secao` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  KEY `FKokvr4cv5jepwil92jaby1ilck` (`secao_id_secao`),
  CONSTRAINT `FKokvr4cv5jepwil92jaby1ilck` FOREIGN KEY (`secao_id_secao`) REFERENCES `secao` (`id_secao`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'empenhos1bfv'
--

--
-- Dumping routines for database 'empenhos1bfv'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-13 19:10:10
