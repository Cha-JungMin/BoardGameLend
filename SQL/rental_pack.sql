--------------------------------------------------------
--  파일이 생성됨 - 금요일-5월-10-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package RENTAL_PACK
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "USER24"."RENTAL_PACK" 
-- 렌탈id, 보드게임id, 카피id, 게임제목, 시작일, 반납일, 댓글, 평점, 상태
-- 보드id를 넣으면 카피아이디 중 랜탈 테이블에서 시작-반납 범위 날짜에 없는거 찾고 넣기?
-- 게임이름, 상태(대여 예정, 대여중, 대여 완료)

-- 내 아이디 중
-- 대여 완료 상태라면 평점 + 후기 작성 프로시저 가능
is
    procedure create_rental (p_board_id in  number, p_member_id in number,
                             p_start_date in date, p_end_date in date);
    procedure load_rental_statement;
    procedure get_rental_statement (p_start_date in date, p_end_date in date,
                                    statement_cursor out sys_refcursor);
    procedure get_rental_statistic (p_member_id in number, p_start_date in date, p_end_date in date,
                                   statistic_cursor out sys_refcursor);
    procedure get_rental_statistic_by_game (p_start_date in date, p_end_date in date,
                                            statistic_cursor out sys_refcursor);
    procedure get_rental_statistic_by_member (p_start_date in date, p_end_date in date,
                                              statistic_cursor out sys_refcursor);  
    procedure update_comment (p_rental_id in number, p_member_id in number, p_grade in number, p_comment in varchar2);
    
    procedure get_available_games(
        p_start_date	in	date,
        p_end_date		in	date,
        p_result_cursor out sys_refcursor
    );
    
    procedure get_rental_history (statement_cursor out sys_refcursor,
              p_start_date in date, p_end_date in date, p_username in varchar2, p_title in varchar2);

    procedure add_rental_detail (
        p_board_game_copy_id 	in 	number,
        p_start_date 			in 	date,
        p_end_date 				in 	date,
        p_member_id 			in 	number,
        p_success 				out number
    );
    
    procedure update_rental_statement(p_rental_id in number);
    
    
    procedure get_member_rental_detail (
        p_member_id				in 	number,
        p_start_date 			in 	varchar2,
        p_end_date 				in 	varchar2,
        p_result_cursor 		out sys_refcursor
    );
end;

/
--------------------------------------------------------
--  DDL for Package Body RENTAL_PACK
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "USER24"."RENTAL_PACK" 
-- 렌탈id, 보드게임id, 카피id, 게임제목, 시작일, 반납일, 댓글, 평점, 상태
-- 게임이름, 상태(대여예정, 대여중, 대여완료)

-- 대여 완료 상태라면 평점 + 후기 작성 프로시저 가능
---  update_rental_statement
is
    procedure create_rental
    (p_board_id in number, p_member_id in number, p_start_date in date, p_end_date in date)
        is
            v_copy_id board_game_copy.copy_id%type;
            v_statement rental_detail.statement%type;
            v_game_title rental_detail.game_title%type;
            v_total_fee rental_detail.total_fee%type;
            v_days number;
        begin
            v_days := trunc(to_date(p_end_date) - to_date(p_start_date)) + 1;
            v_statement := '대여예정';
            if p_start_date < to_char(sysdate, 'YY/MM/DD') then
                raise_application_error(-20003, '대여 시작일이 현재 날짜보다 적습니다.');
            elsif p_end_date < p_start_date then
                raise_application_error(-20004, '대여 종료일이 대여 시작일보다 큽니다.');
            elsif p_start_date = to_char(sysdate, 'YY/MM/DD') then
                v_statement := '대여중';
            end if;

            select copy_id into v_copy_id
            from board_game_copy
            where board_game_board_id = p_board_id   -- copy 테이블 보드게임id 
            and copy_id not in (select board_game_copy_copy_id from rental_detail
                                where board_game_copy_copy_id is not null and (start_date <= p_end_date and end_date >= p_start_date))
            and rownum = 1;
        
            select game_title, (rental_fee * v_days) into v_game_title, v_total_fee
            from board_game
            where board_id = p_board_id;
            
            
            insert into rental_detail (boardgame_board_id, member_member_id, board_game_copy_copy_id,
                                       game_title, start_date, end_date, statement, total_fee)
                         values (p_board_id, p_member_id, v_copy_id, v_game_title,
                                 p_start_date, p_end_date, v_statement, v_total_fee);
           exception
           when no_data_found then
            raise_application_error(-20002, '대여 가능한 복사본을 찾을 수 없습니다. 해당 기간 내 모든 보드게임이 대여중입니다.');
    end;
        
    procedure load_rental_statement
    is
        v_start_date date;
        v_end_date date;
    begin

        update rental_detail
        set statement = '연체 -' || trunc(sysdate - to_date(end_date))
        where (statement = '대여중' or statement like '연체%') 
            and end_date < trunc(sysdate);
        commit;
        update rental_detail
        set statement = '대여중'
        where statement = '대여예정' and start_date >= trunc(sysdate);
        commit;
    end;
    
    procedure get_rental_statement (p_start_date in date, p_end_date in date,
                                    statement_cursor out sys_refcursor)
    is
    begin
        open statement_cursor for
        -- board_id, 게임이름, 대여료, 총 개수 , 사용가능한 개수
        select b.board_id,
               b.game_title,
               b.description,
               count(bcopy.board_game_board_id) as "총 개수",
               NVL(gen.장르, '장르없음') as 장르,
               b.min_people as 최소인원,
               b.max_people as 최대인원, 
               b.min_play_time "최소 플레이 시간",
               b.max_play_time "최대 플레이 시간", 
               b.rental_fee as "대여료(일)",
               count(bcopy.board_game_board_id) - NVL(rental_counts."대여개수", 0) as "실제 보유 개수"
        from board_game b
        left join board_game_copy bcopy on b.board_id = bcopy.board_game_board_id
        left join
            (   
                select boardgame_board_id,
                       count(*) as "대여개수"
                from rental_detail
                where (start_date <= p_end_date) and (end_date >= p_start_date)
                group by boardgame_board_id
            ) rental_counts 
            on b.board_id = rental_counts.boardgame_board_id
        left join
            (
                select bg.board_game_board_id,
                    listagg(gen.genre, ', ') within group (order by gen.genre) as 장르
                from board_genre bg
                join genre gen
                on bg.genre_genre_id = gen.genre_id
                group by bg.board_game_board_id
            ) gen on b.board_id = gen.board_game_board_id
        group by
            b.board_id, b.game_title, b.description, NVL(gen.장르, '장르없음'),
            b.min_people, b.max_people, b.min_play_time, b.max_play_time, 
            b.rental_fee, NVL(rental_counts.대여개수, 0)
        order by 
            b.game_title;
    end;
    
    procedure get_rental_statistic (p_member_id in number, p_start_date in date, p_end_date in date,
                                   statistic_cursor out sys_refcursor)
    is
        v_permission member.grade%type;
    begin
        select grade into v_permission
        from member
        where member_id = p_member_id;
        
        if v_permission = 1 then
            open statistic_cursor for
            select * from rental_detail
            where (p_start_date <= start_date and end_date <= p_end_date);
        else
            open statistic_cursor for
            select * from rental_detail
            where (member_member_id = p_member_id
            and p_start_date <= start_date and end_date <= p_end_date);
        end if;
    end;
    
    procedure get_rental_statistic_by_game (p_start_date in date, p_end_date in date,
                                            statistic_cursor out sys_refcursor)
    is 
    begin
        open statistic_cursor for
            select game_title as "게임 이름", sum(total_fee) as 매출, count(*) as 판매량,
            count (rental_comment) as "댓글 수", count (grade) as "평점 수",
            avg(grade) as "평균 평점"
            from rental_detail
            where (p_start_date <= start_date and end_date <= p_end_date)
            group by game_title;
    end;
    
    procedure get_rental_statistic_by_member (p_start_date in date, p_end_date in date,
                                             statistic_cursor out sys_refcursor)
    is 
    begin
        open statistic_cursor for
            select m.name as 이름, sum(rd.total_fee) as 매출, count(*) as 판매량,
            count (rental_comment) as "댓글 수", count (rd.grade) as "평점 수",
            avg(rd.grade) as "평균 평점"
            from rental_detail rd
            join member m on rd.member_member_id = m.member_id
            where (p_start_date <= start_date and end_date <= p_end_date)
            group by m.name; 
    end;
    
    procedure update_comment (p_rental_id in number, p_member_id in number, p_grade in number, p_comment in varchar2)
    is
    begin
        update rental_detail
        set grade = p_grade, rental_comment = p_comment
        where rental_detail_id = p_rental_id and member_member_id = p_member_id; 
        
    end;
    
    procedure get_available_games(
        p_start_date	in	date,
        p_end_date		in	date,
        p_result_cursor out sys_refcursor
    )
    is
    begin
        open p_result_cursor for
            select bc.copy_id, bg.game_title, bg.rental_fee
            from board_game_copy bc
            join board_game bg on bc.board_game_board_id = bg.board_id
            where bc.copy_id not in (
                select rd.board_game_copy_copy_id
                from rental_detail rd
                where (rd.statement = '대여중' or rd.statement = '대여예정')
                  and (rd.start_date <= p_start_date
                  and rd.end_date >= p_end_date)
            );
    end;
    
    procedure get_rental_history (statement_cursor out sys_refcursor,
              p_start_date in date, p_end_date in date, p_username in varchar2, p_title in varchar2)
    is
    begin
        open statement_cursor for
        select rental_detail_id, board_game_copy_copy_id, game_title, mem.name, start_date,
               end_date, total_fee, statement, ren.grade, rental_comment
        from rental_detail ren
        left join member mem on mem.member_id = ren.member_member_id
        where (p_start_date <= start_date and end_date <= p_end_date)
            and (p_username is null or lower(mem.name) like lower('%' || p_username || '%'))
            and (p_title is null or game_title like '%' || p_title  || '%')
        order by rental_detail_id desc;
    end;
    
    
    
    
    procedure add_rental_detail (
        p_board_game_copy_id 	in 	number,
        p_start_date 			in 	date,
        p_end_date 				in 	date,
        p_member_id 			in 	number,
        p_success 				out number
    )
    is
        v_board_id 		board_game.board_id%type;
        v_board_title	board_game.game_title%type;
        v_total_fee		number;
        v_days			number;
    begin
    
        -- 주어진 board_game_copy_id로부터 board_id를 찾습니다.
        select bg.board_id, bg.game_title into v_board_id, v_board_title
        from board_game_copy bgc
        join board_game bg on bgc.board_game_board_id = bg.board_id
        where bgc.copy_id = p_board_game_copy_id;
        
        -- 날짜 계산 후 총 대여로 값을 계산합니다.
        v_days := trunc(to_date(p_end_date) - to_date(p_start_date)) + 1;
        select (bg.rental_fee * v_days) into v_total_fee
        from board_game bg
        where bg.board_id = v_board_id;
    
        -- 대여 정보를 추가합니다.
        insert into rental_detail (
            boardgame_board_id,
            member_member_id,
            board_game_copy_copy_id,
            game_title,
            start_date,
            end_date,
            rental_comment,
            grade,
            statement,
            total_fee
        )
        values (
            v_board_id,
            p_member_id,
            p_board_game_copy_id,
            v_board_title,
            p_start_date,
            p_end_date,
            null,
            null,
            '대여중',
            v_total_fee
        );
        
        -- 성공적으로 추가되었는지 확인합니다.
        if sql%rowcount = 1 then
            p_success := 1; -- 성공적으로 추가된 경우
        else
            p_success := 0; -- 추가에 실패한 경우
        end if;
        
        commit; -- 트랜잭션을 완료합니다.
            
    exception
        when others then
            p_success := 0;
            rollback;
            raise_application_error(-20104, 'An error occurred: ' || sqlerrm);
    end;
    
    procedure update_rental_statement(p_rental_id in number)
    is
    begin
        update rental_detail
        set statement = '대여완료'
        where rental_detail_id = p_rental_id;
    end;
    
    
    
    procedure get_member_rental_detail (
        p_member_id				in 	number,
        p_start_date 			in 	varchar2,
        p_end_date 				in 	varchar2,
        p_result_cursor 		out sys_refcursor
    )
    is
    begin
        open p_result_cursor for
            select
                rd.rental_detail_id,
                bgc.copy_id,
                bg.game_title,
                to_char(rd.start_date,'YYYY-MM-DD'),
                to_char(rd.end_date,'YYYY-MM-DD'),
                rd.end_date - rd.start_date + 1 as cnt_date,
                rd.statement,
                rd.total_fee
            from
                rental_detail rd
            join
                board_game_copy bgc on rd.board_game_copy_copy_id = bgc.copy_id
            join
                board_game bg on bgc.board_game_board_id = bg.board_id
            where
                rd.member_member_id = p_member_id
                and greatest(
                        to_date(p_start_date), rd.start_date
                    ) <= least(
                        to_date(p_end_date), rd.end_date
                        );
    end;
end;

/
