CREATE TABLE icons
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    url  VARCHAR(255) NOT NULL,
    type INT          NOT NULL
);
