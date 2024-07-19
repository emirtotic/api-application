CREATE TABLE `covid-tracker`.covid_record
(
    id          bigint      NOT NULL AUTO_INCREMENT,
    code        VARCHAR(6) NOT NULL,
    country        VARCHAR(25) NOT NULL,
    confirmed   bigint NOT NULL,
    recovered   bigint NOT NULL,
    critical   bigint NOT NULL,
    deaths   bigint NOT NULL,
    last_change   datetime NOT NULL,
    last_update   datetime NOT NULL,

    CONSTRAINT covid_record_id_pk PRIMARY KEY (id),
    UNIQUE (id)
);