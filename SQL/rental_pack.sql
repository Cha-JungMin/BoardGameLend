--------------------------------------------------------
--  ������ ������ - �ݿ���-5��-10-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package RENTAL_PACK
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "USER24"."RENTAL_PACK" 
-- ��Żid, �������id, ī��id, ��������, ������, �ݳ���, ���, ����, ����
-- ����id�� ������ ī�Ǿ��̵� �� ��Ż ���̺��� ����-�ݳ� ���� ��¥�� ���°� ã�� �ֱ�?
-- �����̸�, ����(�뿩 ����, �뿩��, �뿩 �Ϸ�)

-- �� ���̵� ��
-- �뿩 �Ϸ� ���¶�� ���� + �ı� �ۼ� ���ν��� ����
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
-- ��Żid, �������id, ī��id, ��������, ������, �ݳ���, ���, ����, ����
-- �����̸�, ����(�뿩����, �뿩��, �뿩�Ϸ�)

-- �뿩 �Ϸ� ���¶�� ���� + �ı� �ۼ� ���ν��� ����
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
            v_statement := '�뿩����';
            if p_start_date < to_char(sysdate, 'YY/MM/DD') then
                raise_application_error(-20003, '�뿩 �������� ���� ��¥���� �����ϴ�.');
            elsif p_end_date < p_start_date then
                raise_application_error(-20004, '�뿩 �������� �뿩 �����Ϻ��� Ů�ϴ�.');
            elsif p_start_date = to_char(sysdate, 'YY/MM/DD') then
                v_statement := '�뿩��';
            end if;

            select copy_id into v_copy_id
            from board_game_copy
            where board_game_board_id = p_board_id   -- copy ���̺� �������id 
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
            raise_application_error(-20002, '�뿩 ������ ���纻�� ã�� �� �����ϴ�. �ش� �Ⱓ �� ��� ��������� �뿩���Դϴ�.');
    end;
        
    procedure load_rental_statement
    is
        v_start_date date;
        v_end_date date;
    begin

        update rental_detail
        set statement = '��ü -' || trunc(sysdate - to_date(end_date))
        where (statement = '�뿩��' or statement like '��ü%') 
            and end_date < trunc(sysdate);
        commit;
        update rental_detail
        set statement = '�뿩��'
        where statement = '�뿩����' and start_date >= trunc(sysdate);
        commit;
    end;
    
    procedure get_rental_statement (p_start_date in date, p_end_date in date,
                                    statement_cursor out sys_refcursor)
    is
    begin
        open statement_cursor for
        -- board_id, �����̸�, �뿩��, �� ���� , ��밡���� ����
        select b.board_id,
               b.game_title,
               b.description,
               count(bcopy.board_game_board_id) as "�� ����",
               NVL(gen.�帣, '�帣����') as �帣,
               b.min_people as �ּ��ο�,
               b.max_people as �ִ��ο�, 
               b.min_play_time "�ּ� �÷��� �ð�",
               b.max_play_time "�ִ� �÷��� �ð�", 
               b.rental_fee as "�뿩��(��)",
               count(bcopy.board_game_board_id) - NVL(rental_counts."�뿩����", 0) as "���� ���� ����"
        from board_game b
        left join board_game_copy bcopy on b.board_id = bcopy.board_game_board_id
        left join
            (   
                select boardgame_board_id,
                       count(*) as "�뿩����"
                from rental_detail
                where (start_date <= p_end_date) and (end_date >= p_start_date)
                group by boardgame_board_id
            ) rental_counts 
            on b.board_id = rental_counts.boardgame_board_id
        left join
            (
                select bg.board_game_board_id,
                    listagg(gen.genre, ', ') within group (order by gen.genre) as �帣
                from board_genre bg
                join genre gen
                on bg.genre_genre_id = gen.genre_id
                group by bg.board_game_board_id
            ) gen on b.board_id = gen.board_game_board_id
        group by
            b.board_id, b.game_title, b.description, NVL(gen.�帣, '�帣����'),
            b.min_people, b.max_people, b.min_play_time, b.max_play_time, 
            b.rental_fee, NVL(rental_counts.�뿩����, 0)
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
            select game_title as "���� �̸�", sum(total_fee) as ����, count(*) as �Ǹŷ�,
            count (rental_comment) as "��� ��", count (grade) as "���� ��",
            avg(grade) as "��� ����"
            from rental_detail
            where (p_start_date <= start_date and end_date <= p_end_date)
            group by game_title;
    end;
    
    procedure get_rental_statistic_by_member (p_start_date in date, p_end_date in date,
                                             statistic_cursor out sys_refcursor)
    is 
    begin
        open statistic_cursor for
            select m.name as �̸�, sum(rd.total_fee) as ����, count(*) as �Ǹŷ�,
            count (rental_comment) as "��� ��", count (rd.grade) as "���� ��",
            avg(rd.grade) as "��� ����"
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
                where (rd.statement = '�뿩��' or rd.statement = '�뿩����')
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
    
        -- �־��� board_game_copy_id�κ��� board_id�� ã���ϴ�.
        select bg.board_id, bg.game_title into v_board_id, v_board_title
        from board_game_copy bgc
        join board_game bg on bgc.board_game_board_id = bg.board_id
        where bgc.copy_id = p_board_game_copy_id;
        
        -- ��¥ ��� �� �� �뿩�� ���� ����մϴ�.
        v_days := trunc(to_date(p_end_date) - to_date(p_start_date)) + 1;
        select (bg.rental_fee * v_days) into v_total_fee
        from board_game bg
        where bg.board_id = v_board_id;
    
        -- �뿩 ������ �߰��մϴ�.
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
            '�뿩��',
            v_total_fee
        );
        
        -- ���������� �߰��Ǿ����� Ȯ���մϴ�.
        if sql%rowcount = 1 then
            p_success := 1; -- ���������� �߰��� ���
        else
            p_success := 0; -- �߰��� ������ ���
        end if;
        
        commit; -- Ʈ������� �Ϸ��մϴ�.
            
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
        set statement = '�뿩�Ϸ�'
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
