CREATE SCHEMA IF NOT EXISTS `mym0neymanager`;

CREATE TABLE IF NOT EXISTS `mym0neymanager`.transactions
(
    id BIGINT      NOT NULL AUTO_INCREMENT,
    concept    varchar(100) NOT NULL,
    amount    DOUBLE(15,2)      NOT NULL,
    `date` DATE NOT NULL,
    PRIMARY KEY (idTransaction)
);

INSERT INTO mym0neymanager.transactions (id,concept,amount, `date`) VALUES (1,'Concepto de ejemplo 1', 50.35, DATE '2015-12-17');
INSERT INTO mym0neymanager.transactions (id,concept,amount, `date`) VALUES (2,'Concepto de ejemplo 2', 200.01, DATE '2015-12-17');

SELECT * FROM mym0neymanager.transactions;

DROP TABLE mym0neymanager.transactions;

COMMIT;
