CREATE SEQUENCE IF NOT EXISTS global_seq AS INTEGER START WITH 100 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS text_template
(
    id         INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    key_value  VARCHAR(50)  NOT NULL,
    text_value VARCHAR(500) NOT NULL,
    CONSTRAINT key_unique_idx UNIQUE (key_value)
);

CREATE TABLE IF NOT EXISTS scale
(
    size        INTEGER       NOT NULL,
    volume      INTEGER       NOT NULL,
    foot_length DECIMAL(3, 1) NOT NULL,
    foot_girth  DECIMAL(3, 1) NOT NULL,
    PRIMARY KEY (size, volume)
);

CREATE TABLE IF NOT EXISTS customer
(
    chat_member_id BIGINT PRIMARY KEY,
    user_name      VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS customer_scale
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

CREATE TABLE IF NOT EXISTS season
(
    name_val       VARCHAR(50) PRIMARY KEY,
    localized_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS shoes_model
(
    name_val       VARCHAR(50) PRIMARY KEY,
    localized_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS shoes
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

CREATE TABLE IF NOT EXISTS shoes_scales
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

CREATE TABLE IF NOT EXISTS customer_shoes_wish
(
    customer_id BIGINT      NOT NULL,
    vendor_code VARCHAR(50) NOT NULL,
    PRIMARY KEY (customer_id, vendor_code),
    CONSTRAINT fk__customer_shoes_wish__customer__customer_id__id
        FOREIGN KEY (customer_id) REFERENCES customer (chat_member_id),
    CONSTRAINT fk__customer_shoes_wish__shoes__vendor_code__vendor_code
        FOREIGN KEY (vendor_code) REFERENCES shoes (vendor_code)
);

CREATE TABLE IF NOT EXISTS users
(
    name_val VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NULL
);
