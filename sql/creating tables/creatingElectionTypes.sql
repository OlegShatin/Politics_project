CREATE TABLE election_types(
  type VARCHAR(16)  NOT NULL PRIMARY KEY
);
INSERT INTO election_types (type) VALUES ('PRESIDENT');
INSERT INTO election_types (type) VALUES ('PARLIAMENT');