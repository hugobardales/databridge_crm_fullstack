CREATE TABLE  app_status (
  id SERIAL PRIMARY KEY,
  status varchar(50) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO app_status (status) VALUES ('INITIALIZED')