CREATE TABLE candidates_lists (
  id SERIAL PRIMARY KEY ,
  election_id INT NOT NULL REFERENCES elections(id),
  candidate_id INT NOT NULL REFERENCES candidates(id),
  votes int DEFAULT 0 NOT NULL CHECK (votes >= 0)
)