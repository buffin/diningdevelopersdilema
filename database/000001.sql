-- Init. DB Setup

create table developers (
	id bigint not null auto_increment, 
	email varchar(255), 
	name varchar(255) not null, 
	passowrd varchar(255) not null, 
	username varchar(255) not null unique, 
	primary key (id)
) ENGINE=InnoDB;

create table locations (
	id bigint not null auto_increment, 
	name varchar(255) not null, 
	primary key (id)
) ENGINE=InnoDB;

create table votes (
	id bigint not null auto_increment, 
	vote integer not null,
	developer_id bigint not null, 
	location_id bigint not null, 
	primary key (id), 
	unique (developer_id, location_id)
) ENGINE=InnoDB;

alter table votes add index FK6B30AC9268074A3 (location_id), 
	add constraint FK6B30AC9268074A3 
	foreign key (location_id) references locations (id);
	
alter table votes 
	add index FK6B30AC94FBE2B91 (developer_id), 
	add constraint FK6B30AC94FBE2B91 
	foreign key (developer_id) references developers (id);
	
INSERT INTO  developers (name, username, email, passowrd) VALUES ('Nils', 'neckert', 'mail@nils-eckert.de', SHA1( 'neckert'));
INSERT INTO  developers (name, username, email, passowrd) VALUES ('Benjamin', 'basbach', 'mail@nils-eckert.de', SHA1( 'basbach'));
INSERT INTO  developers (name, username, email, passowrd) VALUES ('Markus', 'mfischer', 'mail@nils-eckert.de', SHA1( 'mfischer'));
INSERT INTO  developers (name, username, email, passowrd) VALUES ('Michel', 'mzedler', 'mail@nils-eckert.de', SHA1( 'mzedler'));

	