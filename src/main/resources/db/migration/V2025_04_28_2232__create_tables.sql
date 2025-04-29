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
VALUES (35, 3, 22.5, 20.2),
       (35, 4, 22.5, 20.7),
       (35, 5, 22.5, 21.2),
       (35, 6, 22.5, 21.7),
       (35, 7, 22.5, 22.2),
       (35, 8, 22.5, 22.7),
       (35, 9, 22.5, 23.2),
       (36, 3, 23.0, 20.6),
       (36, 4, 23.0, 21.1),
       (36, 5, 23.0, 21.6),
       (36, 6, 23.0, 22.1),
       (36, 7, 23.0, 22.6),
       (36, 8, 23.0, 23.1),
       (36, 9, 23.0, 23.6),
       (37, 3, 24.0, 21.0),
       (37, 4, 24.0, 21.5),
       (37, 5, 24.0, 22.0),
       (37, 6, 24.0, 22.5),
       (37, 7, 24.0, 23.0),
       (37, 8, 24.0, 23.5),
       (37, 9, 24.0, 24.0),
       (38, 3, 24.5, 21.4),
       (38, 4, 24.5, 21.9),
       (38, 5, 24.5, 22.4),
       (38, 6, 24.5, 22.9),
       (38, 7, 24.5, 23.4),
       (38, 8, 24.5, 23.9),
       (38, 9, 24.5, 24.4),
       (39, 3, 25.0, 21.8),
       (39, 4, 25.0, 22.3),
       (39, 5, 25.0, 22.8),
       (39, 6, 25.0, 23.3),
       (39, 7, 25.0, 23.8),
       (39, 8, 25.0, 24.3),
       (39, 9, 25.0, 24.8),
       (40, 3, 26.0, 22.2),
       (40, 4, 26.0, 22.7),
       (40, 5, 26.0, 23.2),
       (40, 6, 26.0, 23.7),
       (40, 7, 26.0, 24.2),
       (40, 8, 26.0, 24.7),
       (40, 9, 26.0, 25.2),
       (41, 3, 26.5, 22.6),
       (41, 4, 26.5, 23.1),
       (41, 5, 26.5, 23.6),
       (41, 6, 26.5, 24.1),
       (41, 7, 26.5, 24.6),
       (41, 8, 26.5, 25.1),
       (41, 9, 26.5, 25.6),
       (42, 3, 27.0, 23.0),
       (42, 4, 27.0, 23.5),
       (42, 5, 27.0, 24.0),
       (42, 6, 27.0, 24.5),
       (42, 7, 27.0, 25.0),
       (42, 8, 27.0, 25.5),
       (42, 9, 27.0, 26.0);

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

INSERT INTO monro.season (name_val, localized_name)
VALUES ('summer', 'Лето'),
       ('winter', 'Зима'),
       ('demi_season', 'Весна-осень');

CREATE TABLE IF NOT EXISTS monro.shoes_model
(
    name_val       VARCHAR(50) PRIMARY KEY,
    localized_name VARCHAR(50) NOT NULL
);

INSERT INTO monro.shoes_model (name_val, localized_name)
VALUES ('high_boots', 'Сапоги'),
       ('shoes', 'Туфли'),
       ('boots', 'Ботинки'),
       ('slippers', 'Сланцы'),
       ('sneakers', 'Кроссовки'),
       ('ankle_boots', 'Полусапоги');

CREATE TABLE IF NOT EXISTS monro.shoes
(
    id          INTEGER DEFAULT nextval('monro.global_seq') PRIMARY KEY,
    vendor_code VARCHAR(50)  NOT NULL,
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
    shoes_id INTEGER NOT NULL,
    size     INTEGER NOT NULL,
    volume   INTEGER NOT NULL,
    PRIMARY KEY (shoes_id, size, volume),
    CONSTRAINT fk__shoes_scales__shoes__shoes_id__id
        FOREIGN KEY (shoes_id) REFERENCES monro.shoes (id),
    CONSTRAINT fk__shoes_scales__scale__size__volume__size__volume
        FOREIGN KEY (size, volume) REFERENCES monro.scale (size, volume)
);

CREATE TABLE IF NOT EXISTS monro.customer_shoes_wish
(
    customer_id BIGINT  NOT NULL,
    shoes_id    INTEGER NOT NULL,
    PRIMARY KEY (customer_id, shoes_id),
    CONSTRAINT fk__customer_shoes_wish__customer__customer_id__id
        FOREIGN KEY (customer_id) REFERENCES monro.customer (chat_member_id),
    CONSTRAINT fk__customer_shoes_wish__shoes__shoes_id__id
        FOREIGN KEY (shoes_id) REFERENCES monro.shoes (id)
);

CREATE TABLE IF NOT EXISTS monro.users
(
    name_val VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NULL
);
