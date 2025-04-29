CREATE SEQUENCE IF NOT EXISTS monro.global_seq AS INTEGER START WITH 100 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS monro.text_template
(
    id         INTEGER DEFAULT nextval('monro.global_seq') PRIMARY KEY,
    key_value  VARCHAR(50)  NOT NULL,
    text_value VARCHAR(500) NOT NULL,
    CONSTRAINT key_unique_idx UNIQUE (key_value)
);

INSERT INTO monro.text_template (key_value, text_value)
VALUES ('GREETING', 'Привет, я умный бот!'),
       ('GREETING_FOR_RETURNED', 'Привет, хорошо, что ты вернулся!');

CREATE TABLE IF NOT EXISTS monro.scale
(
    size        INTEGER       NOT NULL,
    volume      INTEGER       NOT NULL,
    foot_length DECIMAL(3, 1) NOT NULL,
    foot_girth  DECIMAL(3, 1) NOT NULL,
    PRIMARY KEY (size, volume)
);

INSERT INTO monro.scale (size, volume, foot_length, foot_girth)
VALUES (35, 3 - 1, NULL, 19.7),
       (35, 4 - 1, NULL, 20.2),
       (35, 5 - 1, NULL, 20.7),
       (35, 6 - 1, NULL, 21.2),
       (35, 7 - 1, NULL, 21.7),
       (35, 8 - 1, NULL, 22.2),
       (35, 9 - 1, NULL, 22.7),
       (36, 3 - 1, NULL, 20.1),
       (36, 4 - 1, NULL, 20.6),
       (36, 5 - 1, NULL, 21.1),
       (36, 6 - 1, NULL, 21.6),
       (36, 7 - 1, NULL, 22.1),
       (36, 8 - 1, NULL, 22.6),
       (36, 9 - 1, NULL, 23.1),
       (37, 3 - 1, NULL, 20.5),
       (37, 4 - 1, NULL, 21.0),
       (37, 5 - 1, NULL, 21.5),
       (37, 6 - 1, NULL, 22.0),
       (37, 7 - 1, NULL, 22.5),
       (37, 8 - 1, NULL, 23.0),
       (37, 9 - 1, NULL, 23.5),
       (38, 3 - 1, NULL, 20.9),
       (38, 4 - 1, NULL, 21.4),
       (38, 5 - 1, NULL, 21.9),
       (38, 6 - 1, NULL, 22.4),
       (38, 7 - 1, NULL, 22.9),
       (38, 8 - 1, NULL, 23.4),
       (38, 9 - 1, NULL, 23.9),
       (39, 3 - 1, NULL, 21.3),
       (39, 4 - 1, NULL, 21.8),
       (39, 5 - 1, NULL, 22.3),
       (39, 6 - 1, NULL, 22.8),
       (39, 7 - 1, NULL, 23.3),
       (39, 8 - 1, NULL, 23.8),
       (39, 9 - 1, NULL, 24.3),
       (40, 3 - 1, NULL, 21.7),
       (40, 4 - 1, NULL, 22.2),
       (40, 5 - 1, NULL, 22.7),
       (40, 6 - 1, NULL, 23.2),
       (40, 7 - 1, NULL, 23.7),
       (40, 8 - 1, NULL, 24.2),
       (40, 9 - 1, NULL, 24.7),
       (41, 3 - 1, NULL, 22.1),
       (41, 4 - 1, NULL, 22.6),
       (41, 5 - 1, NULL, 23.1),
       (41, 6 - 1, NULL, 23.6),
       (41, 7 - 1, NULL, 24.1),
       (41, 8 - 1, NULL, 24.6),
       (41, 9 - 1, NULL, 25.1),
       (42, 3 - 1, NULL, 22.5),
       (42, 4 - 1, NULL, 23.0),
       (42, 5 - 1, NULL, 23.5),
       (42, 6 - 1, NULL, 24.0),
       (42, 7 - 1, NULL, 24.5),
       (42, 8 - 1, NULL, 25.0),
       (42, 9 - 1, NULL, 25.5);

CREATE TABLE IF NOT EXISTS monro.customer
(
    chat_member_id BIGINT PRIMARY KEY,
    user_name      VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS monro.customer_scale
(
    customer_id BIGINT  NOT NULL,
    size        INTEGER NOT NULL,
    volume      INTEGER NOT NULL,
    PRIMARY KEY (customer_id, size, volume),
    CONSTRAINT fk__customer_scale__customer__customer_id__chat_member_id
        FOREIGN KEY (customer_id) REFERENCES customer (chat_member_id),
    CONSTRAINT fk__customer_scale__scale__size__volume__size__volume
        FOREIGN KEY (size, volume) REFERENCES scale (size, volume)
);

CREATE TABLE IF NOT EXISTS monro.season
(
    name_val       VARCHAR(50) PRIMARY KEY,
    localized_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS monro.shoes_model
(
    name_val       VARCHAR(50) PRIMARY KEY,
    localized_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS monro.shoes
(
    vendor_code VARCHAR(50) PRIMARY KEY,
    url         VARCHAR(200) NOT NULL,
    name        VARCHAR(50)  NOT NULL,
    description VARCHAR(500) NOT NULL,
    image_url   VARCHAR(200) NULL,
    season_name VARCHAR(50)  NOT NULL,
    model_name  VARCHAR(50)  NOT NULL,
    CONSTRAINT fk__shoes__season__season_name__name
        FOREIGN KEY (season_name) REFERENCES season (name_val),
    CONSTRAINT fk__shoes__shoes_model__model_name__name
        FOREIGN KEY (model_name) REFERENCES shoes_model (name_val)
);

CREATE TABLE IF NOT EXISTS monro.shoes_scales
(
    shoes_vendor_code VARCHAR(50) NOT NULL,
    size              INTEGER     NOT NULL,
    volume            INTEGER     NOT NULL,
    PRIMARY KEY (shoes_vendor_code, size, volume),
    CONSTRAINT fk__shoes_scales__shoes_vendor_code__vendor_code
        FOREIGN KEY (shoes_vendor_code) REFERENCES shoes (vendor_code),
    CONSTRAINT fk__shoes_scales__scale__size__volume__size__volume
        FOREIGN KEY (size, volume) REFERENCES scale (size, volume)
);

CREATE TABLE IF NOT EXISTS monro.customer_shoes_wish
(
    customer_id BIGINT      NOT NULL,
    vendor_code VARCHAR(50) NOT NULL,
    PRIMARY KEY (customer_id, vendor_code),
    CONSTRAINT fk__customer_shoes_wish__customer__customer_id__id
        FOREIGN KEY (customer_id) REFERENCES customer (chat_member_id),
    CONSTRAINT fk__customer_shoes_wish__shoes__vendor_code__vendor_code
        FOREIGN KEY (vendor_code) REFERENCES shoes (vendor_code)
);

CREATE TABLE IF NOT EXISTS monro.users
(
    name_val VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NULL
);
