CREATE TABLE candidates (
  id SERIAL PRIMARY KEY ,
  party INT REFERENCES parties(id),
  name VARCHAR(64) NOT NULL ,
  info TEXT,
  achievements TEXT,
  election_program TEXT,
  image_src VARCHAR(64),
  /*todo: add constrain only agents users can be agent_id referenced*/
  agent_id INT NOT NULL REFERENCES users(id)
);

CREATE FUNCTION check_is_user_admin_or_agent () RETURNS trigger AS $check_is_user_admin_or_agent$
BEGIN
  IF (SELECT users.role FROM users WHERE users.id = NEW.agent_id) IN ('ADMIN', 'AGENT')
  THEN RETURN NEW;
  END IF;
END; $check_is_user_admin_or_agent$
LANGUAGE  plpgsql;

CREATE TRIGGER check_is_user_admin_or_agent_trigger
BEFORE INSERT ON candidates FOR EACH ROW
EXECUTE PROCEDURE check_is_user_admin_or_agent();

INSERT INTO parties (id, seats_in_parliament) VALUES (1,0);
INSERT INTO candidates (id, party, name, info, achievements, election_program, image_src, agent_id) VALUES (1, 1, 'Пустой бюллетень','','','','',(SELECT id FROM users WHERE role='ADMIN'));
INSERT INTO candidates (id, party, name, info, achievements, election_program, image_src, agent_id) VALUES (2, 1, 'Испортить бюллетень','','','','',(SELECT id FROM users WHERE role='ADMIN'));