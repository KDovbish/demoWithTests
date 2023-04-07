CREATE SEQUENCE cabinets_id_seq;

CREATE TABLE cabinets
(
    id integer PRIMARY KEY DEFAULT nextval('cabinets_id_seq'::regclass),
    capacity integer,
    deleted boolean,
    name varchar(255)
    );