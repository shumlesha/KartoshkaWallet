CREATE TABLE sessions
(
    id            UUID                        NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_at   TIMESTAMP WITHOUT TIME ZONE,
    access_token  VARCHAR(400)                NOT NULL,
    refresh_token VARCHAR(400)                NOT NULL,
    user_id       UUID                        NOT NULL,
    enabled       BOOLEAN                     NOT NULL,
    CONSTRAINT pk_sessions PRIMARY KEY (id)
);

CREATE TABLE users
(
    id           UUID                        NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_at  TIMESTAMP WITHOUT TIME ZONE,
    first_name   VARCHAR(255)                NOT NULL,
    last_name    VARCHAR(255)                NOT NULL,
    patronymic   VARCHAR(255),
    phone_number VARCHAR(255)                NOT NULL,
    email        VARCHAR(255)                NOT NULL,
    birth_date   date                        NOT NULL,
    password     VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE wallets
(
    id          UUID                        NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITHOUT TIME ZONE,
    owner_id    UUID                        NOT NULL,
    balance     BIGINT                      NOT NULL,
    CONSTRAINT pk_wallets PRIMARY KEY (id)
);

ALTER TABLE sessions
    ADD CONSTRAINT uc_sessions_accesstoken UNIQUE (access_token);

ALTER TABLE sessions
    ADD CONSTRAINT uc_sessions_refreshtoken UNIQUE (refresh_token);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_phonenumber UNIQUE (phone_number);

ALTER TABLE wallets
    ADD CONSTRAINT uc_wallets_owner UNIQUE (owner_id);

ALTER TABLE sessions
    ADD CONSTRAINT FK_SESSIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE wallets
    ADD CONSTRAINT FK_WALLETS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id);