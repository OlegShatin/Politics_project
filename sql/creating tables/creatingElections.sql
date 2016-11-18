
CREATE TABLE elections (
  id SERIAL PRIMARY KEY ,
  type VARCHAR(16) NOT NULL REFERENCES election_types(type),
  start_time TIMESTAMPTZ NOT NULL,
  finish_time TIMESTAMPTZ
);

/*CREATE FUNCTION add_day_in_finish_time () RETURNS trigger AS $add_day_in_finish_time$
BEGIN
  IF NEW.finish_time IS NULL THEN
  NEW.finish_time = NEW.start_time + INTERVAL '24 hour';
  END IF;
  RETURN NEW;
END; $add_day_in_finish_time$
LANGUAGE  plpgsql;

CREATE FUNCTION add_default_candidates () RETURNS trigger AS $add_default_candidates$
BEGIN
  INSERT INTO candidates_lists(candidate_id, election_id) VALUES (1, NEW.id);
  INSERT INTO candidates_lists(candidate_id, election_id) VALUES (2, NEW.id);
END; $add_default_candidates$
LANGUAGE  plpgsql;

CREATE TRIGGER default_election_finish_time_trigger
BEFORE INSERT OR UPDATE ON elections FOR EACH ROW
  EXECUTE PROCEDURE add_day_in_finish_time();

CREATE TRIGGER election_add_default_candidates_trigger
AFTER INSERT ON elections FOR EACH ROW
EXECUTE PROCEDURE add_default_candidates();*/
