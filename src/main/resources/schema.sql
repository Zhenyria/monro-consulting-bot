DROP TABLE IF EXISTS chat_member;
DROP TABLE IF EXISTS text_template;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq;
CREATE TABLE text_template
(
    id         INT DEFAULT global_seq.nextval PRIMARY KEY,
    key_value  VARCHAR(50)  NOT NULL,
    text_value VARCHAR(500) NOT NULL,
    CONSTRAINT key_unique_idx UNIQUE (key_value)
);

CREATE TABLE chat_member
(
    id             INT DEFAULT global_seq.nextval PRIMARY KEY,
    chat_member_id BIGINT      NOT NULL,
    user_name      VARCHAR(50) NOT NULL,
    CONSTRAINT chat_member_id_unique_idx UNIQUE (chat_member_id)
);
