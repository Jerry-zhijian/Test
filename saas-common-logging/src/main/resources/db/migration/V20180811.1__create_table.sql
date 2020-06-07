-- ==========登录日志表==========
DROP TABLE IF EXISTS `cas_login_log`;
CREATE TABLE `cas_login_log` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户ID',
  `user_name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `ip` VARCHAR(32) NOT NULL DEFAULT '' COMMENT 'IP地址',
  `type` TINYINT(4) UNSIGNED NOT NULL DEFAULT '0' COMMENT '登陆日志类型 1:登入 2:登出',
  `inserttime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isactive` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_inserttime` (`inserttime`),
  KEY `idx_updatetime` (`updatetime`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='智牛SaaS平台登录日志表, owner: guiqingqing, manager: zhengkeshuang';

-- ==========操作日志表==========
DROP TABLE IF EXISTS `cas_operation_log`;
CREATE TABLE `cas_operation_log` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户ID',
  `user_name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `application_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '应用ID',
  `ip` VARCHAR(32) NOT NULL DEFAULT '' COMMENT 'IP地址',
  `url` VARCHAR(512) NOT NULL DEFAULT '' COMMENT 'URL',
  `operation_code` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '操作码',
  `operation_value` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '操作值',
  `inserttime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isactive` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_inserttime` (`inserttime`),
  KEY `idx_updatetime` (`updatetime`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='智牛SaaS平台操作日志表, owner: guiqingqing, manager: zhengkeshuang';