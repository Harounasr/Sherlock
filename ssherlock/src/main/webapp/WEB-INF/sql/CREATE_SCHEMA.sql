CREATE TYPE course_role AS ENUM ('TEACHER', 'TUTOR', 'MEMBER');

CREATE TYPE system_role AS ENUM ('REGISTERED', 'TEACHER', 'ADMINISTRATOR');

CREATE TYPE grade AS ENUM ('1', '2', '3', '4', '5', '6');

CREATE TYPE checker_type AS ENUM ('COMPILATION','IDENTITY','LINE_WIDTH','USER_DEFINED');

CREATE TABLE user
(
    username      VARCHAR(50) PRIMARY KEY NOT NULL,
    email         VARCHAR(50) UNIQUE      NOT NULL,
    firstname     VARCHAR(50)             NOT NULL,
    lastname      VARCHAR(50)             NOT NULL,
    faculty       VARCHAR(50)             DEFAULT 'NONE',
    password_hash VARCHAR(75)             NOT NULL,
    password_salt VARCHAR(50)             NOT NULL,
    user_role     system_role             NOT NULL,
    failed_login_attempts INTEGER         DEFAULT 0

    FOREIGN KEY (faculty) REFERENCES faculty (name) ON DELETE SET DEFAULT
);

CREATE TABLE not_verified_user
(
    username  VARCHAR(50) PRIMARY KEY NOT NULL,
    email     VARCHAR(50) UNIQUE      NOT NULL,
    token     VARCHAR(75)             NOT NULL,
    firstname VARCHAR(50)             NOT NULL,
    lastname  VARCHAR(50)             NOT NULL,
    faculty   VARCHAR(50)             DEFAULT 'NONE',
    expiry_date TIMESTAMP             NOT NULL

    FOREIGN KEY (faculty) REFERENCES faculty (name) ON DELETE SET DEFAULT
);

CREATE TABLE course
(
    course_name VARCHAR(50) PRIMARY KEY NOT NULL,
);

CREATE TABLE exercise
(
    id                   SERIAL PRIMARY KEY NOT NULL,
    course_name          VARCHAR(50)        NOT NULL,
    publish_date         TIMESTAMP          NOT NULL,
    recommended_deadline TIMESTAMP          NOT NULL,
    obligatory_deadline  TIMESTAMP          NOT NULL,
    description          text,

    FOREIGN KEY (course_name) REFERENCES course (course_name) ON DELETE CASCADE,
    CONSTRAINT date_constraints CHECK (
                publish_date < recommended_deadline AND
                recommended_deadline < obligatory_deadline
        )
);

CREATE TABLE exercise_image
(
    uuid           uuid PRIMARY KEY NOT NULL,
    exercise_id    INTEGER          NOT NULL,
    exercise_image BYTEA,

    FOREIGN KEY (exercise_id) REFERENCES exercise (id) ON DELETE CASCADE
);

CREATE TABLE checker
(
    id          SERIAL      NOT NULL,
    exercise_id INTEGER     NOT NULL,
    is_visible  BOOLEAN     NOT NULL DEFAULT FALSE,
    is_required BOOLEAN     NOT NULL DEFAULT FALSE,
    parameter_1 VARCHAR(255),
    parameter_2 VARCHAR(255),
    type         checker_type NOT NULL,

    PRIMARY KEY (id, exercise_id),
    FOREIGN KEY (exercise_id) REFERENCES exercise (id) ON DELETE CASCADE
);

CREATE TABLE checker_result
(
    exercise_id        INTEGER NOT NULL,
    checker_id         INTEGER NOT NULL,
    submission_id      INTEGER NOT NULL,
    has_passed         BOOLEAN,
    result_description TEXT,

    PRIMARY key (Exercise_id, checker_id, submission_id),
    FOREIGN KEY (checker_id, Exercise_id) REFERENCES checker (id, exercise_id) ON DELETE CASCADE,
    FOREIGN KEY (submission_id) REFERENCES submission (id) ON DELETE CASCADE
);

CREATE TABLE submission
(
    id                   SERIAL PRIMARY KEY NOT NULL,
    timestamp_submission TIMESTAMP,
    student_username     VARCHAR(50)        REFERENCES user(username) ON DELETE CASCADE,
    tutor_username       VARCHAR(50)        REFERENCES user (username) ON DELETE SET NULL,
    exercise_id          INTEGER REFERENCES exercise (id) ON DELETE CASCADE,

);

CREATE TABLE submission_file
(
    id            SERIAL INTEGER NOT NULL,
    submission_id INTEGER NOT NULL,
    file_name     VARCHAR(50) NOT NULL,
    file          BYTEA,

    PRIMARY KEY (id, submission_id)
        FOREIGN KEY (submission_id) REFERENCES submission(id) ON DELETE CASCADE
);

CREATE TABLE testate_comment
(
    comment_id    SERIAL  NOT NULL,
    file_id       INTEGER NOT NULL,
    is_visible    BOOLEAN NOT NULL,
    line_number   INTEGER NOT NULL,
    comment       TEXT    NOT NULL,

    PRIMARY KEY (submission_id, file_id, comment_id),
    FOREIGN KEY (file_id) REFERENCES submission_file (submission_id, id) ON DELETE CASCADE,
);

CREATE TABLE testate
(
    submission_id       INTEGER NOT NULL,
    tutor_id            INTEGER NOT NULL,
    layout_grade        grade   NOT NULL,
    structure_grade     grade   NOT NULL,
    readability_grade   grade   NOT NULL,
    functionality_grade grade   NOT NULL,

    PRIMARY key (submission_id),
    FOREIGN KEY (submission_id) REFERENCES submission (id) ON DELETE CASCADE,
    FOREIGN KEY (tutor_id) REFERENCES user (username) ON DELETE SET NULL
);

CREATE TABLE system_settings
(
    email_regex         VARCHAR(255),
    primary_color_hex   VARCHAR(20),
    secondary_color_hex VARCHAR(20),
    system_name         VARCHAR(255),
    system_logo         BYTEA,
    imprint             TEXT,
    contact_information TEXT
);

CREATE TABLE faculty
(
    name    VARCHAR(50)   PRIMARY KEY NOT NULL,
);

CREATE TABLE participates
(
    username    INTEGER     NOT NULL,
    course_name VARCHAR     NOT NULL,
    user_role   course_role NOT NULL,

    PRIMARY KEY (username, course_name),
    FOREIGN KEY (username) REFERENCES username ON DELETE CASCADE,
    FOREIGN KEY (course_name) REFERENCES course (course_name) ON DELETE CASCADE
);


