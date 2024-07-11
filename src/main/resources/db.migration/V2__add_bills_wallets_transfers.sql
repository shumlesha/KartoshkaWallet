CREATE TABLE bills
(
    id           UUID                        NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_at  TIMESTAMP WITHOUT TIME ZONE,
    cost         BIGINT                      NOT NULL,
    sender_id    UUID                        NOT NULL,
    recipient_id UUID                        NOT NULL,
    comment      VARCHAR(250),
    bill_status  VARCHAR(255),
    CONSTRAINT pk_bills PRIMARY KEY (id)
);

CREATE TABLE transfers
(
    id              UUID                        NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_at     TIMESTAMP WITHOUT TIME ZONE,
    cost            BIGINT                      NOT NULL,
    sender_id       UUID                        NOT NULL,
    recipient_id    UUID                        NOT NULL,
    comment         VARCHAR(250),
    transfer_status VARCHAR(255),
    CONSTRAINT pk_transfers PRIMARY KEY (id)
);

ALTER TABLE bills
    ADD CONSTRAINT FK_BILLS_ON_RECIPIENT FOREIGN KEY (recipient_id) REFERENCES users (id);

ALTER TABLE bills
    ADD CONSTRAINT FK_BILLS_ON_SENDER FOREIGN KEY (sender_id) REFERENCES users (id);

ALTER TABLE transfers
    ADD CONSTRAINT FK_TRANSFERS_ON_RECIPIENT FOREIGN KEY (recipient_id) REFERENCES users (id);

ALTER TABLE transfers
    ADD CONSTRAINT FK_TRANSFERS_ON_SENDER FOREIGN KEY (sender_id) REFERENCES users (id);