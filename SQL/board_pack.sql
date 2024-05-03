--------------------------------------------------------
--  파일이 생성됨 - 금요일-5월-03-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package BOARD_PACK
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "USER24"."BOARD_PACK" 
is
    procedure create_boardgame 
        (p_title varchar2, p_description varchar2, p_min_peoeple number, p_max_people number,
         p_min_play_time number, p_max_play_time number, p_rental_fee number, p_stock number);
    procedure view_boardgames;
    procedure edit_boardgame 
        (p_board_id number, p_title varchar2, p_description varchar2, p_min_peoeple number,
         p_max_people number, p_min_play_time number, p_max_play_time number,
         p_rental_fee number, p_copy number);
    procedure delete_boardgame (p_board_id number);
    
    procedure board_game_statement (statement_cursor OUT SYS_REFCURSOR);
    procedure edit_board_copy (p_board_id number, p_copy number);
end;

/
--------------------------------------------------------
--  DDL for Package Body BOARD_PACK
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "USER24"."BOARD_PACK" 
is
    procedure create_boardgame 
    (p_title in varchar2,     p_description  in varchar2,
     p_min_peoeple in number, p_max_people in number,
     p_min_play_time  in number, p_max_play_time  in number,
     p_rental_fee in number, p_stock in number)
    is
        v_board_id number;
    begin
        insert into board_game (game_title, description, min_people, max_people, min_play_time, max_play_time, rental_fee) 
        values (p_title, p_description, p_min_peoeple, p_max_people, p_min_play_time, p_max_play_time, p_rental_fee)
        returning board_id into v_board_id;

        for i in 1..p_stock loop
            insert into board_game_copy (board_game_board_id) values (v_board_id);
        end loop;
    end;

    procedure view_boardgames 
    is
    begin
        null;
    end;

    procedure edit_boardgame
        (p_board_id in number, p_title in varchar2, p_description  in varchar2,
         p_min_peoeple in number, p_max_people in number, p_min_play_time in number, 
         p_max_play_time in number, p_rental_fee in number, p_copy number)
    is
    begin
        update board_game
        set game_title = p_title, description = p_description,
        min_people = p_min_peoeple, max_people = p_max_people,
        min_play_time = p_min_play_time, max_play_time = p_max_play_time,
        rental_fee = p_rental_fee
        where board_id = p_board_id;
        
        edit_board_copy(p_board_id, p_copy);
    end;
    
    procedure delete_boardgame (p_board_id number)
    is
    begin
        null;
    end;
    
    procedure board_game_statement (statement_cursor OUT SYS_REFCURSOR)
    -- 보드게임 + 장르 + //구현해야함 개수 + 남은개수
    is
    begin
        open statement_cursor for
        select b.game_title, b.description, count(bcopy.board_game_board_id) 개수,
               gen.장르,
               b.min_people as 최소인원, b.max_people as 최대인원, b.min_play_time "최소 플레이 시간",
               b.max_play_time "최대 플레이 시간", b.rental_fee as 시간당_가격
               
        from board_game b
        join board_game_copy bcopy on b.board_id = bcopy.board_game_board_id
        join 
            (
                select distinct
                    bg.board_game_board_id, 
                    listagg(gen.genre, ', ') within group (order by gen.genre) as 장르
                from board_genre bg
                join genre gen on bg.genre_genre_id = gen.genre_id
                group by bg.board_game_board_id
            ) gen ON b.board_id = gen.board_game_board_id
            
        group by b.game_title,  b.description, gen.장르,
        b.min_people, b.max_people, b.min_play_time, b.max_play_time, b.rental_fee
        order by b.game_title;
    end;
    
    procedure edit_board_copy (p_board_id in number, p_copy in number)
    is
        v_cur number;
    begin
    
        select count(*) into v_cur from board_game_copy
        where board_game_board_id = p_board_id;
        
        if p_copy > v_cur then
            for i in 1..(p_copy - v_cur) loop
                insert into board_game_copy (board_game_board_id)
                values (p_board_id);
            end loop;
        elsif p_copy < v_cur then
            delete from board_game_copy
            where board_game_board_id = p_board_id and rownum <= (v_cur - p_copy);
        end if;    
    end;
end;

/
