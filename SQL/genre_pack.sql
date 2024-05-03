--------------------------------------------------------
--  ������ ������ - �ݿ���-5��-03-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package GENRE_PACK
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "USER24"."GENRE_PACK" 
is
    procedure create_genre (p_gname varchar2);
    procedure add_genre (p_gname varchar2, p_board_id number);
    procedure delete_genre (p_gname varchar2);

end;

/
--------------------------------------------------------
--  DDL for Package Body GENRE_PACK
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "USER24"."GENRE_PACK" 
is
    procedure create_genre (p_gname  in varchar2)
    is
    begin
        insert into genre (genre) values (p_gname);
    end;

    procedure add_genre (p_gname in varchar2, p_board_id in number)
    is
        v_genre_id number;
    begin
        select genre_id into v_genre_id from genre where genre = p_gname;
        insert into board_genre (genre_genre_id, board_game_board_id)
        values (v_genre_id, p_board_id);

        exception when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, '�ش��ϴ� �帣�� �����ϴ�.');
    end;

    procedure delete_genre (p_gname varchar2)
    is
        v_genre_id number;
    begin
        select genre_id into v_genre_id from genre where genre = p_gname;
        delete genre where genre_id = v_genre_id;

        exception when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, '�ش��ϴ� �帣�� �����ϴ�.');
    end;
end;

/
