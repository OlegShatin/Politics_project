CREATE TABLE votes(
    user_id INT NOT NULL REFERENCES users(id),
    election_id INT NOT NULL REFERENCES elections(id),
    CONSTRAINT votes_pk PRIMARY KEY (user_id, election_id)
);