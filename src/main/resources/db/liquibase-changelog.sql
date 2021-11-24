--liquibase formatted sql

--changeset lbibera:00000_initial_db
--comment: initial database with user storage
create table if not exists users
(
    user_id varchar(255) not null
        constraint users_pkey
            primary key,
    display_name varchar(255),
    username varchar(255),
    encrypted_password varchar(255)
);