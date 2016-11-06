CREATE TABLE candidates (
  id SERIAL PRIMARY KEY ,
  party INT REFERENCES parties(id),
  name VARCHAR(64) NOT NULL ,
  info TEXT,
  achievments TEXT,
  election_program TEXT,
  image_src VARCHAR(64),
  /*todo: add constrain only agents users can be agent_id referenced*/
  agent_id INT NOT NULL UNIQUE REFERENCES users(id)
)