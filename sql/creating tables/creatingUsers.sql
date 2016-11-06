CREATE TABLE users (
  id              SERIAL PRIMARY KEY,
  name            VARCHAR(20) NOT NULL ,
  surname         VARCHAR(25) NOT NULL ,
  patronymic      VARCHAR(20),
  birthday        DATE
    CHECK (date_part('year', birthday + TIME '00:00') > 1900
           AND date_part('year', birthday + TIME '00:00') <= date_part('year', current_timestamp)),
  email           VARCHAR    NOT NULL
    CONSTRAINT proper_email CHECK (email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),
  passport_series VARCHAR(4) NOT NULL
    CHECK (passport_series ~* '^[0-9]{4}$'),
  passport_number VARCHAR(6) NOT NULL
    CHECK (passport_number ~* '^[0-9]{6}$'),
  password_hash   VARCHAR(40) NOT NULL,
  timezone INT NOT NULL DEFAULT (0),
  role VARCHAR(16) NOT NULL REFERENCES user_roles(role) DEFAULT ('user')
)
	