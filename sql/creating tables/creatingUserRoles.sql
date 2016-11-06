CREATE TABLE user_roles(
  role VARCHAR(16)  NOT NULL PRIMARY KEY
);
INSERT INTO user_roles VALUES ('guest');
INSERT INTO user_roles VALUES ('user');
INSERT INTO user_roles VALUES ('agent');
INSERT INTO user_roles VALUES ('admin');
