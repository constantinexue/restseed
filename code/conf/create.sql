CREATE TABLE user (
    id              CHAR(4)        NOT NULL,
    name            VARCHAR(50)     NOT NULL,
    PRIMARY KEY (id),
    UNIQUE  KEY (name)
);
CREATE TABLE message (
    id              CHAR(4)        NOT NULL,
    text            VARCHAR(100)    NOT NULL,
    created         DATETIME        NOT NULL
    PRIMARY KEY (id)
);