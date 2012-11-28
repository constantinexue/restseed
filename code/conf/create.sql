CREATE TABLE user (
    id              CHAR(32)        NOT NULL,
    name            VARCHAR(50)     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE  KEY (name)
);