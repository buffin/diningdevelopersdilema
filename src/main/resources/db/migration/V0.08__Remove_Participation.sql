ALTER TABLE developers DROP COLUMN participating;
ALTER TABLE developers CHANGE passowrd password varchar(255) not null;