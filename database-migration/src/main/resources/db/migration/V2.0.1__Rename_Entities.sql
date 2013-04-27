
RENAME TABLE voting TO events;
RENAME TABLE developers TO users;

alter table votes add column event_id bigint;
alter table votes add index FK6B30AC914051D11 (event_id), add constraint FK6B30AC914051D11 foreign key (event_id) references events (id);
alter table votes add index FK6B30AC957BF104 (developer_id), add constraint FK6B30AC957BF104 foreign key (developer_id) references users (id);

update votes set event_id = (select max(id) from events);
ALTER TABLE votes CHANGE event_id event_id BIGINT( 20 ) NOT NULL;
 