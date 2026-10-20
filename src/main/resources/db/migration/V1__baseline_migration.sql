CREATE SEQUENCE IF NOT EXISTS revinfo_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE revchanges
(
    rev        BIGINT NOT NULL,
    entityname VARCHAR(255)
);

CREATE TABLE revinfo
(
    rev      BIGINT NOT NULL,
    revtstmp BIGINT,
    CONSTRAINT pk_revinfo PRIMARY KEY (rev)
);

CREATE TABLE users
(
    id           UUID    NOT NULL,
    username     VARCHAR(255),
    email        VARCHAR(255),
    password     VARCHAR(255),
    role         VARCHAR(255),
    enabled      BOOLEAN NOT NULL,
    default_user BOOLEAN NOT NULL,
    created_at   TIMESTAMP(6) WITHOUT TIME ZONE,
    updated_at   TIMESTAMP(6) WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uk_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uk_users_username UNIQUE (username);

ALTER TABLE revchanges
    ADD CONSTRAINT fk_revchanges_on_default_tracking_modified_entities_changelog FOREIGN KEY (rev) REFERENCES revinfo (rev);