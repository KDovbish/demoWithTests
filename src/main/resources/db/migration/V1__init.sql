CREATE SEQUENCE users_id_seq;
CREATE SEQUENCE passports_id_seq;

CREATE TABLE passports
(
    id integer PRIMARY KEY DEFAULT nextval('users_id_seq'::regclass),
    date_of_birthday timestamp,
    expire_date timestamp,
    first_name varchar(255),
    second_name varchar(255),
    serial_number varchar(255),
    deleted boolean,
    state integer,
    next_id integer,
    prev_id integer,
    FOREIGN KEY (next_id) REFERENCES passports (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (prev_id) REFERENCES passports (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE users
(
    id integer PRIMARY KEY DEFAULT nextval('users_id_seq'::regclass),
    name varchar(255),
    email varchar(255),
    country varchar(255),
    gender varchar(255),
    is_deleted boolean,
    deny_users varchar(255),
    visible boolean,
    passport_id integer,
    FOREIGN KEY (passport_id) REFERENCES passports (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);
