-- Init. DB Setup

alter table votes add column vote_date datetime;
update votes set vote_date = NOW();

ALTER TABLE  `votes` CHANGE  `vote_date`  `vote_date` DATETIME NOT NULL;
