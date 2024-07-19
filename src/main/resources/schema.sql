DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS books;

CREATE TABLE authors
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    age  INT  NOT NULL
);

CREATE TABLE books
(
    isbn      TEXT PRIMARY KEY NOT NULL,
    title     TEXT             NOT NULL,
    author_id BIGINT           NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors (id)
);