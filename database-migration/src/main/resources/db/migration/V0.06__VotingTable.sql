create table voting (
	id bigint not null auto_increment, 
	votingdate datetime not null,
	closed bit not null,
	primary key (id)
) ENGINE=InnoDB;