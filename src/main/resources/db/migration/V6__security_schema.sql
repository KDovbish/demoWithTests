create table secusers(
                         username varchar(50) not null primary key,
                         password varchar(500) not null,
                         enabled boolean not null);

create table secauthorities (
                                username varchar(50) not null,
                                authority varchar(50) not null,
                                constraint fk_authorities_users foreign key(username) references secusers(username));

create unique index ix_auth_username on secauthorities (username,authority);