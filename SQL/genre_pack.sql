--------------------------------------------------------
--  파일이 생성됨 - 수요일-5월-08-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package GENRE_PACK
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "USER24"."GENRE_PACK" 
is
    procedure create_genre (p_gname varchar2);
    procedure view_genre (view_cursor out sys_refcursor);
    procedure search_genre (search_cursor out sys_refcursor, v_genre varchar2);
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
    
    procedure view_genre 
    (view_cursor out sys_refcursor)
    is
    begin
        open view_cursor for
        select genre from genre;
        
    end;
    
    procedure search_genre
    (search_cursor out sys_refcursor, v_genre in varchar2)
    is
    begin
        open search_cursor for
        select genre from genre where genre like '%' || v_genre || '%'; 
    end;
    
    procedure add_genre (p_gname in varchar2, p_board_id in number)
    is
        v_genre_id number;
    begin
        select genre_id into v_genre_id from genre where genre = p_gname;
        insert into board_genre (genre_genre_id, board_game_board_id)
        values (v_genre_id, p_board_id);

        exception when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, '해당하는 장르가 없습니다.');
    end;

    procedure delete_genre (p_gname varchar2)
    is
        v_genre_id number;
    begin
        select genre_id into v_genre_id from genre where genre = p_gname;
        delete genre where genre_id = v_genre_id;

        exception when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, '해당하는 장르가 없습니다.');
    end;
end;

/
