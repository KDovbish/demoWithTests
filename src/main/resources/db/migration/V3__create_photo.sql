CREATE SEQUENCE photo_id_seq;

CREATE TABLE photo
(
    id integer PRIMARY KEY DEFAULT nextval('photo_id_seq'::regclass),
    add_date timestamp,
    camera_type varchar(255),
    description varchar(255),
    photo_url varchar(255),
    employee_id integer,
    image bytea,
    FOREIGN KEY (employee_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION
    );