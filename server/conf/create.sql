CREATE TABLE user (
    id              CHAR(32)       NOT NULL,
    username        VARCHAR(50)    NOT NULL,
    password        VARCHAR(50)    NOT NULL,
    created         DATETIME       NOT NULL,
    PRIMARY KEY (id),
    UNIQUE  KEY (username)
);
CREATE TABLE message (
    id              CHAR(32)       NOT NULL,
    text            VARCHAR(100)   NOT NULL,
    created         DATETIME       NOT NULL,
    author          CHAR(32)       NOT NULL,
    PRIMARY KEY (id)
);