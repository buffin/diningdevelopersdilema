ALTER TABLE DEVELOPERS DROP COLUMN participating;
ALTER TABLE DEVELOPERS CHANGE passowrd password varchar(255) not null;