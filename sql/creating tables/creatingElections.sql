
CREATE TABLE elections (
  id SERIAL PRIMARY KEY ,
  type VARCHAR(16) NOT NULL REFERENCES election_types(type),
  start_time TIMESTAMPTZ NOT NULL,
  finish_time TIMESTAMPTZ
);

/*CREATE FUNCTION add_day_in_finish_time () RETURNS trigger AS $add_day_in_finish_time$
BEGIN
  NEW.finish_time = NEW.start_time + INTERVAL '24 hour';
END; $add_day_in_finish_time$
LANGUAGE  plpgsql;*/

CREATE TRIGGER default_election_finish_time_trigger
AFTER INSERT OR UPDATE ON elections FOR EACH ROW
  EXECUTE PROCEDURE add_day_in_finish_time();


