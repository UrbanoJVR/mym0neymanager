CREATE SCHEMA IF NOT EXISTS "mym0neymanager";

DROP TABLE IF EXISTS "mym0neymanager".transactions;

CREATE TABLE IF NOT EXISTS "mym0neymanager".transactions
(
    id BIGINT      NOT NULL AUTO_INCREMENT,
    concept    varchar(100) NOT NULL,
    amount    DOUBLE      NOT NULL,
    `date` DATE NOT NULL,
    PRIMARY KEY (id)
);
