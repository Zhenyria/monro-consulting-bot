DROP TABLE IF EXISTS text_template;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq;
CREATE TABLE text_template (
    id         INT DEFAULT global_seq.nextval PRIMARY KEY,
    key_value  VARCHAR(50)  NOT NULL,
    text_value VARCHAR(500) NOT NULL,
    CONSTRAINT key_unique_idx UNIQUE (key_value)
);
