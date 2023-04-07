CREATE TABLE users_cabinets
(
    cabinet_id integer NOT NULL,
    user_id integer NOT NULL,
    active boolean,
    PRIMARY KEY (cabinet_id, user_id),
    FOREIGN KEY (cabinet_id) REFERENCES cabinets (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);