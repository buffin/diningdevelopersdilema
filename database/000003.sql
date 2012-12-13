-- Init. DB Setup

create table audit (
	id bigint not null auto_increment, 
	auditdate datetime not null, 
	username varchar(255) not null, 
	message varchar(255) not null, 
	primary key (id)
) ENGINE=InnoDB;

