CREATE TABLE news (
  id       SERIAL PRIMARY KEY,
  headline VARCHAR(50),
  content  TEXT,
  datetime TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp
)