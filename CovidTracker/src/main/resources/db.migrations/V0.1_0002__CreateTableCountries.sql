CREATE TABLE `covid-tracker`.country
(
    id         bigint      NOT NULL AUTO_INCREMENT,
    name       VARCHAR(50) NOT NULL,
    alpha2code VARCHAR(2)  NOT NULL,
    alpha3code VARCHAR(5)  NOT NULL,
    CONSTRAINT countries_id_pk PRIMARY KEY (id),
    UNIQUE (id)
);