--liquibase formatted sql

--changeset lbibera:00000_initial_db
--comment: initial database with user storage
CREATE TABLE IF NOT EXISTS users
(
    user_id VARCHAR(255) NOT NULL
        CONSTRAINT users_pkey
            PRIMARY KEY ,
    display_name VARCHAR(255),
    username VARCHAR(255),
    encrypted_password VARCHAR(255)
);

--changeset lbibera:00001_create_stored_proc_create_user
--comment: create a stored procedure to create a new user with a unique username (NOTE, liquibase has a bug with the $$ thing)
CREATE PROCEDURE user_create(
    IN username varchar,
    IN display_name varchar,
    INOUT user_id varchar)
LANGUAGE plpgsql
AS '
BEGIN
    user_id := ''1223'';

    INSERT INTO users(user_id, username, display_name)
    VALUES(user_id, username, display_name);
END;
';

--changeset lbibera:00002_create_stored_proc_find_users_by_username
--comment: create a stored procedure to retrieve a user by its username (NOTE, liquibase has a bug with the $$ thing)
CREATE FUNCTION user_find_by_username(username_in varchar)
    RETURNS SETOF users AS '
DECLARE
    is_found boolean := false;
BEGIN
    RAISE NOTICE ''Looking for User: %'', username_in;  -- Prints 30
    RETURN QUERY SELECT * FROM users WHERE users.username = username_in;
END;' LANGUAGE plpgsql;