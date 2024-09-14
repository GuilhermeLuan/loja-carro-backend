CREATE TABLE TB_VEHICLES
(
    id    SERIAL PRIMARY KEY,
    type  VARCHAR(50)    NOT NULL,
    model TEXT   NOT NULL,
    color VARCHAR(50)    NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    year  INT            NOT NULL
);
