/*creating current president elections*/
INSERT INTO users (name, surname, patronymic, birthday, email, passport_series, passport_number, password_hash, timezone, role)
VALUES ('deny','agent',NULL,'1980-01-08','deny_agent@mail.ru','0000','000000','7110eda4d09e62aa5e4a390b0a572acd2c220',3,'AGENT');
INSERT INTO users (name, surname, patronymic, birthday, email, passport_series, passport_number, password_hash, timezone, role)
VALUES ('john','agent',NULL,'1980-01-08','john_agent@mail.ru','0000','000000','7110eda4d09e62aa5e4a390b0a572acd2c220',3,'AGENT');

INSERT INTO candidates (party, name, info, achievements, election_program, image_src, agent_id)
VALUES (NULL,'Deyneris Targarien','Mother of dragons','Breaker of Chains',
        'Stop slavery!','img/candidates/deny.jpg',(SELECT id FROM users WHERE email='deny_agent@mail.ru'));
INSERT INTO candidates (party, name, info, achievements, election_program, image_src, agent_id)
VALUES (NULL,'John Snow','King of North!','Son of Starks and Targariens',
        'Winter is coming!','img/candidates/john.jpg',(SELECT id FROM users WHERE email='john_agent@mail.ru'));

INSERT INTO candidates_lists(candidate_id, election_id) VALUES ((SELECT id FROM candidates WHERE name = 'Пустой бюллетень'), 1);
INSERT INTO candidates_lists(candidate_id, election_id) VALUES ((SELECT id FROM candidates WHERE name = 'Испортить бюллетень'), 1);
INSERT INTO candidates_lists(candidate_id, election_id) VALUES ((SELECT id FROM candidates WHERE name = 'John Snow'), 1);
INSERT INTO candidates_lists(candidate_id, election_id) VALUES ((SELECT id FROM candidates WHERE name = 'Deyneris Targarien'), 1);

/*creating old parliament elections*/
INSERT INTO parties (id, seats_in_parliament) VALUES (4,0);
INSERT INTO parties (id, seats_in_parliament) VALUES (5,0);
INSERT INTO parties (id, seats_in_parliament) VALUES (6,0);
INSERT INTO users (name, surname, patronymic, birthday, email, passport_series, passport_number, password_hash, timezone, role)
VALUES ('ninja','agent',NULL,'1980-01-08','ninja_agent@mail.ru','0000','000000','7110eda4d09e62aa5e4a390b0a572acd2c220',3,'AGENT');
INSERT INTO users (name, surname, patronymic, birthday, email, passport_series, passport_number, password_hash, timezone, role)
VALUES ('mstit','agent',NULL,'1980-01-08','mstit_agent@mail.ru','0000','000000','7110eda4d09e62aa5e4a390b0a572acd2c220',3,'AGENT');
INSERT INTO users (name, surname, patronymic, birthday, email, passport_series, passport_number, password_hash, timezone, role)
VALUES ('hran','agent',NULL,'1980-01-08','hran_agent@mail.ru','0000','000000','7110eda4d09e62aa5e4a390b0a572acd2c220',3,'AGENT');