alter table voting add column state varchar(255);

update voting set state = 'Open' where closed = 0;
update voting set state = 'Closed' where closed = 1;

ALTER TABLE voting DROP closed;