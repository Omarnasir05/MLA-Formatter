CREATE TABLE user (
    user_id INT,
    name VARCHAR (20),
    email VARCHAR (20),
    PRIMARY KEY(user_id)
);

DESCRIBE user;

SELECT * FROM user;

INSERT INTO user VALUES( 1,name);