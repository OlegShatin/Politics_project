CREATE TABLE users (
  id              SERIAL PRIMARY KEY,
  name            VARCHAR(20),
  surname         VARCHAR(25),
  patronymic      VARCHAR(20),
  birthday        DATE
    CHECK (date_part('year', birthday + TIME '00:00') > 1900
           AND date_part('year', birthday + TIME '00:00') <= date_part('year', current_timestamp)),
  email           VARCHAR    NOT NULL
    CONSTRAINT proper_email CHECK (email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'),
  passport_series VARCHAR(4) NOT NULL
    CHECK (passport_series ~* '^[0-9]{4}$'),
  passport_number VARCHAR(4) NOT NULL
    CHECK (passport_series ~* '^[0-9]{4}$'),
  password_hash   VARCHAR(40)
)
	