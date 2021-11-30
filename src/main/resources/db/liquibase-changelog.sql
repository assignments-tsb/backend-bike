--liquibase formatted sql

--changeset lbibera:00000_initial_db
--comment: initial database with user storage
CREATE EXTENSION pgcrypto;
CREATE TABLE IF NOT EXISTS users
(
    user_id VARCHAR(255) NOT NULL
        CONSTRAINT users_pkey
            PRIMARY KEY ,
    display_name VARCHAR(255),
    username VARCHAR(255),
    encrypted_password VARCHAR(255)
);
INSERT INTO users(user_id, display_name, username, encrypted_password)
VALUES (gen_random_uuid(), 'Administrator', 'admin', 'admin123'),
       (gen_random_uuid(), 'Staff 1', 'staff1', 'staff123');

--changeset lbibera:00001_create_stored_proc_create_user
--comment: create a stored procedure to create a new user with a unique username (NOTE, liquibase has a bug with the $$ thing)
CREATE PROCEDURE user_create(
    IN username varchar,
    IN display_name varchar,
    INOUT user_id varchar)
LANGUAGE plpgsql
AS '
BEGIN
    user_id := gen_random_uuid();

    INSERT INTO users(user_id, username, display_name)
    VALUES(user_id, username, display_name);
END;
';

--changeset lbibera:00002_create_stored_proc_find_users_by_username
--comment: create a stored procedure to retrieve a user by its username (NOTE, liquibase has a bug with the $$ thing)
CREATE FUNCTION user_find_by_username(username_in varchar)
RETURNS SETOF users AS '
    SELECT user_id, display_name, username, encrypted_password FROM users WHERE users.username = username_in;
' LANGUAGE sql;

--changeset lbibera:00003_bike_table
--comment: table to store all the bikes
CREATE TABLE IF NOT EXISTS bikes
(
    bike_id VARCHAR(255) NOT NULL
        CONSTRAINT bikes_pkey
            PRIMARY KEY ,
    label VARCHAR(255)
);

--changeset lbibera:00004_user_roles
--comment: table to store roles and its join table
CREATE TABLE IF NOT EXISTS roles
(
    role_id VARCHAR(255) NOT NULL CONSTRAINT roles_pk PRIMARY KEY,
    label VARCHAR(255) NOT NULL
);
INSERT INTO roles(role_id, label)
VALUES
       ('ADMIN', 'Administrator'),
       ('STAFF', 'Staff');
CREATE TABLE IF NOT EXISTS user_roles(
    role_id VARCHAR(255) REFERENCES roles(role_id) NOT NULL,
    user_id VARCHAR(255) REFERENCES users(user_id) NOT NULL,
    PRIMARY KEY (role_id, user_id)
);
INSERT INTO user_roles(role_id, user_id)
VALUES ('ADMIN', (SELECT user_id FROM users WHERE username = 'admin' LIMIT 1)),
       ('STAFF', (SELECT user_id FROM users WHERE username = 'staff1' LIMIT 1))

