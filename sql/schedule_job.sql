CREATE TABLE `schedule_job` (
  `id` bigint(20) NOT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `cron_expression` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `job_group` varchar(255) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `pause` bit(1) DEFAULT NULL,
  `trigger_group` varchar(255) DEFAULT NULL,
  `trigger_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;