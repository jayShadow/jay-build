/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50719
Source Host           : 127.0.0.1:3306
Source Database       : sv

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-01-16 00:08:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_client_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_client_detail`;
CREATE TABLE `t_client_detail` (
  `id` int(11) NOT NULL,
  `client_id` varchar(36) NOT NULL,
  `resource_ids` varchar(255) NOT NULL,
  `secret_required` tinyint(4) NOT NULL DEFAULT '0',
  `client_secret` varchar(255) NOT NULL,
  `scoped` tinyint(4) NOT NULL DEFAULT '0',
  `authorized_grant_types` varchar(255) NOT NULL,
  `registered_redirect_uri` varchar(255) NOT NULL,
  `authorities` varchar(255) NOT NULL,
  `access_token_validity_seconds` int(11) NOT NULL DEFAULT '1800',
  `refresh_token_validity_seconds` int(11) NOT NULL DEFAULT '144000',
  `auto_approve_scope` varchar(255) NOT NULL,
  `additional_information` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL,
  `created_by` varchar(36) NOT NULL,
  `modified_by` varchar(36) NOT NULL,
  `version` int(11) NOT NULL DEFAULT '0',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
