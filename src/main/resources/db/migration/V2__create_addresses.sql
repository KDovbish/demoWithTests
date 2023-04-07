CREATE SEQUENCE addresses_id_seq;

CREATE TABLE addresses
(
    id bigint PRIMARY KEY DEFAULT nextval('addresses_id_seq'::regclass),
    address_has_active boolean,
    city varchar(255),
    country varchar(255),
    street varchar(255),
    employee_id integer,
    FOREIGN KEY (employee_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);