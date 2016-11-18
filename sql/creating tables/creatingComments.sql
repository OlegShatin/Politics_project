CREATE TABLE comments(
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL REFERENCES users(id),
  article_id INT NOT NULL REFERENCES news(id),
  /* todo: add constraint on article_id = parent_comment.article_id*/
  parent_comment_id INT REFERENCES comments(id),
  comment_text TEXT NOT NULL,
  publication_date TIMESTAMPTZ NOT NULL,
  rating INT NOT NULL DEFAULT(0)
)