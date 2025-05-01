ALTER TABLE IF EXISTS monro.customer
    ADD COLUMN IF NOT EXISTS first_name   VARCHAR(50) NULL,
    ADD COLUMN IF NOT EXISTS last_name    VARCHAR(50) NULL,
    ADD COLUMN IF NOT EXISTS phone_number VARCHAR(50) NULL;

CREATE TABLE IF NOT EXISTS monro.consultation_request
(
    id              INTEGER   DEFAULT nextval('monro.global_seq') PRIMARY KEY,
    customer_id     BIGINT                  NOT NULL,
    create_datetime TIMESTAMP DEFAULT now() NOT NULL,
    CONSTRAINT fk__consultation_request__customer__customer_id__chat_member_id
        FOREIGN KEY (customer_id) REFERENCES monro.customer (chat_member_id)
);

CREATE TABLE IF NOT EXISTS monro.consultation_requests_shoes
(
    consultation_request_id INTEGER NOT NULL,
    shoes_id                INTEGER NOT NULL,
    PRIMARY KEY (consultation_request_id, shoes_id),
    CONSTRAINT fk__consultation_requests_shoes__cnslt_request__cnslt_rq_id__id
        FOREIGN KEY (consultation_request_id) REFERENCES monro.consultation_request (id),
    CONSTRAINT fk__consultation_requests_shoes__shoes__shoes_id__id
        FOREIGN KEY (shoes_id) REFERENCES monro.shoes (id)
);
