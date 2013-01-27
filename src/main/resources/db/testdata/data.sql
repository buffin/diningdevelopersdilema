INSERT INTO  developers (name, username, email, password) VALUES ('Nils', 'neckert', 'mail@nils-eckert.de', SHA1( 'neckert'));
INSERT INTO  developers (name, username, email, password) VALUES ('Benjamin', 'basbach', 'mail@nils-eckert.de', SHA1( 'basbach'));
INSERT INTO  developers (name, username, email, password) VALUES ('Markus', 'mfischer', 'mail@nils-eckert.de', SHA1( 'mfischer'));
INSERT INTO  developers (name, username, email, password) VALUES ('Michel', 'mzedler', 'mail@nils-eckert.de', SHA1( 'mzedler'));
INSERT INTO  developers (name, username, email, password) VALUES ('Jens', 'jmertz', 'mail@nils-eckert.de', SHA1( 'jmertz'));
INSERT INTO  developers (name, username, email, password) VALUES ('Phil', 'pmeier', 'mail@nils-eckert.de', SHA1( 'pmeier'));
INSERT INTO  developers (name, username, email, password) VALUES ('Timo', 'tguenzel', 'mail@nils-eckert.de', SHA1( 'tguenzel'));

INSERT INTO locations (name, description, url, coordinates) VALUES ('Illuminati', 'Kontrovers! Polarisierend! Illuminati!', 'http://www.illuminati-stuttgart.de', '48.8131,9.17688');
INSERT INTO locations (name, description, url, coordinates) VALUES ('Theaterhaus', 'Auch im Theaterhaus auf dem Pragsattel verwöhnen wir Sie nach allen Regeln der Kunst: In unserem Restaurant mit 180 Plätzen bieten wir Ihnen alles, was die kulinarische Landkarte so bietet. Ob schwäbisch oder international, für den großen oder kleinen Hunger, eine kühle Erfrischung oder ein stilvolles Glas Rotwein - Sie werden sich bei uns wohl fühlen. Erleben Sie also in aller Ruhe die Höhepunkte des Stuttgarter Kulturlebens, wir sorgen dafür, dass das Drumherum stimmt.', 'http://www.plankenhorn-stengel.de/inhalt_02.html', '48.810586,9.179186');
INSERT INTO locations (name, description, url, coordinates) VALUES ('Kantine', 'Immerhin schnell...immerhin!', null, '48.809994,9.180928');

INSERT INTO version (version) VALUES (6);
