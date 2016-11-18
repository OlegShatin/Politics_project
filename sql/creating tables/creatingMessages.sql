CREATE TABLE messages (
  id SERIAL PRIMARY KEY,
  /*todo: add constraint to check that sender and recipient are user and agent */
  sender_id INT NOT NULL REFERENCES users(id),
  recipient_id INT NOT NULL REFERENCES users(id),
  message_text TEXT NOT NULL ,
  sending_time TIMESTAMPTZ NOT NULL
)