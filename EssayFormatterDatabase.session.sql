CREATE TABLE USERS(
userid      VARCHAR(30),
name        VARCHAR(30),
email       VARCHAR(30),
PRIMARY KEY (userid),
FOREIGN KEY (userid) REFERENCES ESSAYS(userid)
);
CREATE TABLE ESSAYS(
userid      VARCHAR(30),
fileName   VARCHAR(75),
PRIMARY KEY(userid)
);