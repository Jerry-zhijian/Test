-- ==========消息表==========
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户ID',
  `application_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '应用ID',
  `type` TINYINT(4) UNSIGNED NOT NULL DEFAULT '0' COMMENT '消息类型',
  `subject` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '消息标题',
  `content` VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '消息内容',
  `status` TINYINT(4) UNSIGNED NOT NULL DEFAULT '0' COMMENT '消息状态 0:未读 1:已读',
  `readtime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '已读时间',
  `inserttime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isactive` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_application_id` (`application_id`),
  KEY `idx_inserttime` (`inserttime`),
  KEY `idx_updatetime` (`updatetime`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='智牛SaaS平台消息表, owner: guiqingqing, manager: zhengkeshuang';