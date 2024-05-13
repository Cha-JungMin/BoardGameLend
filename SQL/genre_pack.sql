--------------------------------------------------------
--  파일이 생성됨 - 월요일-5월-13-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package GENRE_PACK
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "USER24"."GENRE_PACK" 
is
    procedure create_genre (p_gname varchar2);
    procedure view_genre (view_cursor out sys_refcursor);
    procedure search_genre (search_cursor out sys_refcursor, p_genre varchar2);
    procedure add_genre (p_gname varchar2, p_board_id number);
    procedure delete_genre (p_gname varchar2);
    
    procedure get_genre_by_game (p_result_cursor out sys_refcursor, p_board_id in number);
    procedure delete_genre_by_game (p_board_id in number, p_genre in varchar2);
    
    procedure get_genre_info (p_result_cursor out sys_refcursor);
    procedure get_serch_genre_boardgame_info (
        p_board_genre_id 	in	number,
        p_result_cursor		out	sys_refcursor
    );
    procedure get_board_game_genres (
        p_board_id 		in number,
        p_result_cursor out sys_refcursor
    );
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
    (search_cursor out sys_refcursor, p_genre in varchar2)
    is
    begin
        open search_cursor for
        select genre from genre where genre like '%' || p_genre || '%'; 
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
    
    procedure get_genre_by_game (p_result_cursor out sys_refcursor, p_board_id in number)
    is
    begin
        open p_result_cursor for
        select gen.genre
        from board_genre bg
        join genre gen on bg.genre_genre_id = gen.genre_id
        where bg.board_game_board_id = p_board_id;
        
    end;
    
    procedure delete_genre_by_game (p_board_id in number, p_genre in varchar2) 
    is
    begin
         delete board_genre
         where board_game_board_id = p_board_id and 
         genre_genre_id = (select genre_id from genre where genre = p_genre);
    end;
    
    procedure get_genre_info (
        p_result_cursor		out	sys_refcursor
    )
    is
    begin
        open p_result_cursor for
            select *
            from genre;
    end;
    
    procedure get_serch_genre_boardgame_info (
        p_board_genre_id 	in	number,
        p_result_cursor		out	sys_refcursor
    )
    is
    begin
        open p_result_cursor for
            select bg.board_id,
                   bg.game_title,
                   bg.min_people,
                   bg.max_people,
                   bg.min_play_time,
                   bg.max_play_time,
                   bg.rental_fee
                from board_game bg
                inner join board_genre bgg on bg.board_id = bgg.board_game_board_id
            where bgg.genre_genre_id = p_board_genre_id
            order by bg.game_title asc;
    end;
    
    procedure get_board_game_genres (
        p_board_id 		in number,
        p_result_cursor out sys_refcursor
    )
    is
    begin
        open p_result_cursor for
            select g.genre
            from genre g
            join board_genre bg on g.genre_id = bg.genre_genre_id
            where bg.board_game_board_id = p_board_id
            order by g.genre asc;
    
        exception
            when no_data_found then
                null; -- 해당하는 장르를 찾을 수 없는 경우 커서를 NULL로 설정합니다.
    end;
    
end;

/
