CREATE TABLE supporters(
  id SERIAL PRIMARY KEY ,
  name VARCHAR(20) NOT NULL ,
  surname VARCHAR(30),
  image_src VARCHAR(64),
  party_id INT NOT NULL REFERENCES parties(id)
)