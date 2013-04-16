-- migration needs to be performed when event is closed

DROP INDEX developer_id ON votes;

ALTER TABLE votes ADD COLUMN current TINYINT(1) DEFAULT 0;