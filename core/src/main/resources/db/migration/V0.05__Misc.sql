alter table developers add column participating bit;

ALTER TABLE votes ADD UNIQUE (developer_id, location_id);

INSERT INTO version (version) VALUES (5);
