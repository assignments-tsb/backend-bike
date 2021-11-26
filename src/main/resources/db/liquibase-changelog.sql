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

--changeset lbibera:00001_create_stored_proc_find_users_by_username
--comment: create a stored procedure to retrieve a user by its username
CREATE OR REPLACE FUNCTION user_find_by_username(
    IN username_in VARCHAR
)
RETURNS refcursor AS '
DECLARE
    mycurs refcursor;
BEGIN
    OPEN mycurs FOR SELECT username, display_name, username, encrypted_password FROM users WHERE username = username_in;
    RETURN mycurs;
END;'
LANGUAGE plpgsql;
