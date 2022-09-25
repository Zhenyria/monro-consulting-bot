DROP TABLE IF EXISTS text_templates;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq;
CREATE TABLE text_templates
(
    id    BIGINT DEFAULT global_seq.nextval PRIMARY KEY,
    key   VARCHAR(50)  NOT NULL,
    value VARCHAR(500) NOT NULL,
    CONSTRAINT key_unique_idx UNIQUE (key)
);
