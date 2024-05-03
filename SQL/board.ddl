-- ������ Oracle SQL Developer Data Modeler 23.1.0.087.0806
--   ��ġ:        2024-05-03 15:51:34 KST
--   ����Ʈ:      Oracle Database 11g
--   ����:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE board_game (
    board_id      NUMBER NOT NULL,
    game_title    VARCHAR2(50 CHAR) NOT NULL,
    description   VARCHAR2(1000 CHAR),
    min_people    NUMBER(2) NOT NULL,
    max_people    NUMBER(2) NOT NULL,
    play_time     NUMBER(5) NOT NULL,
    rental_fee    NUMBER(10) NOT NULL,
    max_play_time NUMBER(5)
);

COMMENT ON COLUMN board_game.board_id IS
    '�������id';

COMMENT ON COLUMN board_game.game_title IS
    '���� �̸�';

COMMENT ON COLUMN board_game.description IS
    '���� �Ұ�';

COMMENT ON COLUMN board_game.min_people IS
    '�ּ� �ο�';

COMMENT ON COLUMN board_game.max_people IS
    '�ִ� �ο�';

COMMENT ON COLUMN board_game.play_time IS
    '���� �÷��� Ÿ��(��)';

COMMENT ON COLUMN board_game.rental_fee IS
    '�뿩�� (��)';

ALTER TABLE board_game ADD CONSTRAINT board_game_pk PRIMARY KEY ( board_id );

CREATE TABLE board_game_copy (
    board_game_board_id NUMBER NOT NULL,
    copy_id             NUMBER NOT NULL
);

COMMENT ON COLUMN board_game_copy.copy_id IS
    '���id';

ALTER TABLE board_game_copy ADD CONSTRAINT board_game_copy_pk PRIMARY KEY ( copy_id,
                                                                            board_game_board_id );

CREATE TABLE board_genre (
    board_genre_id      NUMBER NOT NULL,
    genre_genre_id      NUMBER NOT NULL,
    board_game_board_id NUMBER NOT NULL
);

COMMENT ON COLUMN board_genre.board_genre_id IS
    '������� �帣';

ALTER TABLE board_genre ADD CONSTRAINT board_genre_pk PRIMARY KEY ( board_genre_id );

CREATE TABLE genre (
    genre_id NUMBER NOT NULL,
    genre    VARCHAR2(50 CHAR) NOT NULL
);

ALTER TABLE genre ADD CONSTRAINT genre_pk PRIMARY KEY ( genre_id );

CREATE TABLE member (
    member_id    NUMBER NOT NULL,
    id           VARCHAR2(50 CHAR) NOT NULL,
    password     VARCHAR2(1000 CHAR) NOT NULL,
    name         VARCHAR2(50 CHAR) NOT NULL,
    phone_number NUMBER(20) NOT NULL,
    birth_date   DATE NOT NULL,
    reg_date     DATE NOT NULL,
    grade        NUMBER(1)
);

COMMENT ON COLUMN member.member_id IS
    'ȸ����ȣ';

COMMENT ON COLUMN member.id IS
    '���̵�';

COMMENT ON COLUMN member.password IS
    '��й�ȣ';

COMMENT ON COLUMN member.name IS
    '�̸�';

COMMENT ON COLUMN member.phone_number IS
    '��ȭ��ȣ';

COMMENT ON COLUMN member.birth_date IS
    '�������';

COMMENT ON COLUMN member.reg_date IS
    '��������';

ALTER TABLE member ADD CONSTRAINT member_pk PRIMARY KEY ( member_id );

CREATE TABLE rental_detail (
    rantal_detail_id                   NUMBER NOT NULL,
    start_date                         DATE NOT NULL,
    end_date                           DATE NOT NULL,
    board_game_copy_copy_id            NUMBER NOT NULL,
    "comment"                          VARCHAR2(1000 CHAR),
    grade                              NUMBER(1) NOT NULL,
    statement                          VARCHAR2(10 CHAR) NOT NULL,
    waiting                            NUMBER(2), 
--  ERROR: Column name length exceeds maximum allowed length(30) 
    board_game_copy_boardgame_board_id NUMBER NOT NULL,
    member_member_id                   NUMBER NOT NULL
);

ALTER TABLE rental_detail ADD CONSTRAINT rental_detail_pk PRIMARY KEY ( rantal_detail_id,
                                                                        board_game_copy_boardgame_board_id );

ALTER TABLE board_game_copy
    ADD CONSTRAINT board_game_copy_board_game_fk FOREIGN KEY ( board_game_board_id )
        REFERENCES board_game ( board_id );

ALTER TABLE board_genre
    ADD CONSTRAINT board_genre_board_game_fk FOREIGN KEY ( board_game_board_id )
        REFERENCES board_game ( board_id );

ALTER TABLE board_genre
    ADD CONSTRAINT board_genre_genre_fk FOREIGN KEY ( genre_genre_id )
        REFERENCES genre ( genre_id );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE rental_detail
    ADD CONSTRAINT rental_detail_board_game_copy_fk FOREIGN KEY ( board_game_copy_copy_id,
                                                                  board_game_copy_boardgame_board_id )
        REFERENCES board_game_copy ( copy_id,
                                     board_game_board_id );

ALTER TABLE rental_detail
    ADD CONSTRAINT rental_detail_member_fk FOREIGN KEY ( member_member_id )
        REFERENCES member ( member_id );



-- Oracle SQL Developer Data Modeler ��� ����: 
-- 
-- CREATE TABLE                             6
-- CREATE INDEX                             0
-- ALTER TABLE                             11
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   2
-- WARNINGS                                 0
