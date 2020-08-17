DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
	user_id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	user_type varchar(30),
	username VARCHAR(30),
	pass VARCHAR(30),
	first_name VARCHAR(30),
	last_name VARCHAR(30)
);

CREATE TABLE accounts (
	account_id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	account_type varchar(30),
	balance numeric(7,2),
	status varchar(30),
	user_id_fk int REFERENCES users(user_id) NOT NULL
);

CREATE OR REPLACE FUNCTION trigger_set_time() RETURNS TRIGGER 
AS $$
BEGIN
	NEW.update_at = NOW();
	RETURN NEW; 
END;
$$ LANGUAGE plpgsql; 


ALTER TABLE users ADD COLUMN update_at TIMESTAMP;

CREATE TRIGGER set_time BEFORE UPDATE ON users FOR EACH ROW
EXECUTE PROCEDURE trigger_set_time();

ALTER TABLE accounts ADD COLUMN update_at TIMESTAMP;

CREATE TRIGGER set_time BEFORE UPDATE ON accounts FOR EACH ROW
EXECUTE PROCEDURE trigger_set_time();

INSERT INTO users (user_type, username, pass, first_name, last_name) 
VALUES ('admin', 'levkim', '1234pass', 'Lev', 'Kim');

INSERT INTO accounts (account_type, balance, status, user_id_fk) VALUES ('checking', 90.00, 'open', 1);

SELECT * FROM users WHERE username = 'levvy' AND pass = '1234pass';
