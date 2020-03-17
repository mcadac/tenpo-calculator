-- Creation of the schema
create schema if not exists cl authorization tenpo;

-- Create the tables
create table cl.web_user (

    user_id varchar primary key,
    username varchar,
    password varchar,
    creation_date timestamp,
    UNIQUE (username)

);

create table cl.operation (

    operation_id varchar primary key,
    user_id varchar references cl.web_user(user_id),
    creation_date timestamp,
    request varchar,
    response varchar

);

alter table cl.web_user owner to tenpo;
alter table cl.operation owner to tenpo;

-- Create the admin webUser
insert into cl.web_user values ('fadfsa34kfd', 'milo-admin', 'tenpoTest123', now());


