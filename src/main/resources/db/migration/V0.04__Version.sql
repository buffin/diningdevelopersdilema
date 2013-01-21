CREATE TABLE `version` (
  `version` bigint(20) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `version` (`version`,`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO version (version) VALUES (1), (2), (3), (4);
