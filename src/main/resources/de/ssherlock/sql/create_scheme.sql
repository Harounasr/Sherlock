DO $$
BEGIN
        -- Check if course_role enum type exists
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'course_role') THEN
            EXECUTE 'CREATE TYPE course_role AS ENUM (''TEACHER'', ''TUTOR'', ''MEMBER'')';
END IF;

        -- Check if system_role enum type exists
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'system_role') THEN
            EXECUTE 'CREATE TYPE system_role AS ENUM (''NOT_REGISTERED'', ''REGISTERED'', ''TEACHER'', ''ADMINISTRATOR'')';
END IF;

        -- Check if grade enum type exists
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'grade') THEN
            EXECUTE 'CREATE TYPE grade AS ENUM (''1'', ''2'', ''3'', ''4'', ''5'', ''6'')';
END IF;

        -- Check if checker_type enum type exists
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'checker_type') THEN
            EXECUTE 'CREATE TYPE checker_type AS ENUM (''COMPILATION'',''IDENTITY'',''LINE_WIDTH'',''USER_DEFINED'')';
END IF;
END $$;

CREATE TABLE IF NOT EXISTS faculty
(
    id SERIAL PRIMARY KEY NOT NULL,
    name    VARCHAR(50) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS "user"
(
    id            SERIAL PRIMARY KEY NOT NULL,
    username      VARCHAR(50) UNIQUE      NOT NULL,
    email         VARCHAR(50) UNIQUE      NOT NULL,
    firstname     VARCHAR(50)             NOT NULL,
    lastname      VARCHAR(50)             NOT NULL,
    faculty       VARCHAR(50)             DEFAULT 'NONE',
    password_hash VARCHAR(75)             NOT NULL,
    password_salt VARCHAR(50)             NOT NULL,
    user_role     SYSTEM_ROLE             NOT NULL,
    failed_login_attempts INTEGER         DEFAULT 0,
    token         VARCHAR(75)             NOT NULL,
    expiry_date   TIMESTAMP               WITH TIME ZONE NOT NULL,

                                              FOREIGN KEY (faculty) REFERENCES faculty (name) ON DELETE SET DEFAULT
    );

CREATE TABLE IF NOT EXISTS course
(
    id SERIAL PRIMARY KEY NOT NULL,
    course_name VARCHAR(50) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS exercise
(
    id                   SERIAL PRIMARY KEY NOT NULL,
    name                 VARCHAR(200) UNIQUE NOT NULL,
    course_name          VARCHAR(50)        NOT NULL,
    publish_date         TIMESTAMP          WITH TIME ZONE NOT NULL,
    recommended_deadline TIMESTAMP          WITH TIME ZONE NOT NULL,
    obligatory_deadline  TIMESTAMP          WITH TIME ZONE NOT NULL,
                                                description          TEXT,

                                                FOREIGN KEY (course_name) REFERENCES course (course_name) ON DELETE CASCADE,
    CONSTRAINT date_constraints CHECK (publish_date < recommended_deadline AND recommended_deadline < obligatory_deadline)
    );

CREATE TABLE IF NOT EXISTS exercise_image
(
    uuid           UUID PRIMARY KEY NOT NULL,
    exercise_id    INTEGER          NOT NULL,
    exercise_image BYTEA,

    FOREIGN KEY (exercise_id) REFERENCES exercise (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS checker
(
    id          SERIAL      NOT NULL,
    exercise_id INTEGER     NOT NULL,
    is_visible  BOOLEAN     NOT NULL DEFAULT FALSE,
    is_required BOOLEAN     NOT NULL DEFAULT FALSE,
    parameter_1 VARCHAR(255),
    parameter_2 VARCHAR(255),
    type         CHECKER_TYPE NOT NULL,

    PRIMARY KEY (id, exercise_id),
    FOREIGN KEY (exercise_id) REFERENCES exercise (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS submission
(
    id                   SERIAL PRIMARY KEY NOT NULL,
    timestamp_submission TIMESTAMP WITH TIME ZONE NOT NULL,
    student_username     VARCHAR(50)        REFERENCES "user"(username) ON DELETE CASCADE,
    tutor_username       VARCHAR(50)        REFERENCES "user" (username) ON DELETE SET NULL,
    exercise_id          INTEGER REFERENCES exercise (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS checker_result
(
    exercise_id        INTEGER NOT NULL,
    checker_id         INTEGER NOT NULL,
    submission_id      INTEGER NOT NULL,
    has_passed         BOOLEAN,
    result_description TEXT,

    PRIMARY KEY (exercise_id, checker_id, submission_id),
    FOREIGN KEY (checker_id, exercise_id) REFERENCES checker (id, exercise_id) ON DELETE CASCADE,
    FOREIGN KEY (submission_id) REFERENCES submission (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS submission_file
(
    id            SERIAL PRIMARY KEY NOT NULL,
    submission_id INTEGER NOT NULL,
    file_name     VARCHAR(50) NOT NULL,
    file          BYTEA,

    FOREIGN KEY (submission_id) REFERENCES submission(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS testate_comment
(
    comment_id    SERIAL  NOT NULL,
    submission_id INTEGER NOT NULL,
    file_id       INTEGER NOT NULL,
    is_visible    BOOLEAN NOT NULL,
    line_number   INTEGER NOT NULL,
    comment       TEXT    NOT NULL,

    PRIMARY KEY (submission_id, file_id, comment_id),
    FOREIGN KEY (file_id) REFERENCES submission_file (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS testate
(
    id SERIAL PRIMARY KEY NOT NULL,
    submission_id       INTEGER NOT NULL,
    tutor_id            INTEGER NOT NULL,
    layout_grade        GRADE   NOT NULL,
    structure_grade     GRADE   NOT NULL,
    readability_grade   GRADE   NOT NULL,
    functionality_grade GRADE   NOT NULL,

    FOREIGN KEY (submission_id) REFERENCES submission (id) ON DELETE CASCADE,
    FOREIGN KEY (tutor_id) REFERENCES "user" (id) ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS system_settings
(
    email_regex         VARCHAR(255),
    primary_color_hex   VARCHAR(6),
    secondary_color_hex VARCHAR(6),
    system_name         VARCHAR(255),
    system_logo         BYTEA,
    imprint             TEXT,
    contact_information TEXT
    );

CREATE TABLE IF NOT EXISTS participates
(
    user_id    INTEGER     NOT NULL,
    course_id  INTEGER     NOT NULL,
    user_role  COURSE_ROLE NOT NULL,

    PRIMARY KEY (user_id, course_id),
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES course (id) ON DELETE CASCADE
    );
