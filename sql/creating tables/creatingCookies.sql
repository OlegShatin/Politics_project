CREATE TABLE cookies(
  user_id INT UNIQUE NOT NULL REFERENCES users(id),
  value VARCHAR(40)
)