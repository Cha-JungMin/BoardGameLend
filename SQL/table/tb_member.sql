drop table member purge;

create table member (
    member_id    number                 generated always as identity,
    id           varchar2(50 char)      not null,
    password     varchar2(1000 char)    not null,
    name         varchar2(50 char)      not null,
    phone_number varchar2(13 char)      not null,
    birth_date   date                   not null,
    join_date    date                   not null,
    grade        number(1, 0)           not null
);

alter table member add constraint member_pk primary key ( member_id );

comment on column member.member_id is '회원번호';
comment on column member.id is '아이디';
comment on column member.password is '비밀번호';
comment on column member.name is '이름';
comment on column member.phone_number is '전화번호';
comment on column member.birth_date is '생년월일';
comment on column member.join_date is '가입일자';
comment on column member.grade is '회원등급';

insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('admin', '9999', 'Administrator', '999-9999-9999', '1990-01-01', '1990-01-01', 1);
insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('test1', '1111', 'John Smith', '123-456-7890', '1990-05-15', '2020-01-01', 0);
insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('test2', '2222', 'Alice Johnson', '987-654-3210', '1985-08-20', '2019-07-10', 0);
insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('test3', '3333', 'David Lee', '555-123-4567', '1978-12-10', '2015-03-25', 0);
insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('test4', '4444', 'Emily Brown', '111-222-3333', '1995-11-30', '2022-02-28', 0);
insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('test5', '5555', 'Michael Davis', '999-888-7777', '2000-03-05', '2023-09-15', 0);
insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('test6', '6666', 'Sarah Wilson', '333-444-5555', '1982-07-18', '2018-11-20', 0);
insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('test7', '7777', 'Daniel Taylor', '777-666-5555', '1998-04-12', '2021-06-30', 0);
insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('test8', '8888', 'Olivia Martinez', '444-555-6666', '1989-09-25', '2017-02-15', 0);
insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('test9', '9999', 'Lucas Thompson', '222-333-4444', '1993-01-08', '2016-08-05', 0);
insert into member (id, password, name, phone_number, birth_date, join_date, grade)
values ('test10', '1010', 'Sophia Clark', '888-999-0000', '2005-06-20', '2024-04-01', 0);

create or replace package pkg_member
is
	function check_user(
		p_id in varchar2,
		p_pwd in varchar2
	) return number;
	
	function get_user_id(
        p_member_id in number
    ) return varchar2;
	
	function get_user_grade(
        p_member_id in number
    ) return number;
	
	
	
	procedure get_login_member_no(
		p_id in varchar2,
		p_pwd in varchar2,
		p_member_id out number
	);
	procedure get_member_info(
		p_member_id in number,
		p_user_info out sys_refcursor
	);
	procedure add_member_info(
        p_id in varchar2,
        p_pwd in varchar2,
        p_name in varchar2,
        p_phone in varchar2,
        p_birth in varchar2,
        p_result out number
    );
	procedure update_member_info(
        p_member_id in number,
        p_pwd in varchar2,
        p_new_pwd in varchar2,
        p_result out number
    );
	procedure delete_member_info(
		p_member_id in number,
		p_pwd in varchar2,
		p_result out number
	);
    
end;
/

create or replace package body pkg_member
is
	function check_user(
		p_id in varchar2,
		p_pwd in varchar2
	) return number
	is
		v_count number;
	begin
		select count(*) into v_count from member where id = p_id and password = p_pwd;
        if v_count > 0 then
            return 1;
        else
            return 0;
        end if;
	end;
	
	function get_user_id(
        p_member_id in number
    ) return varchar2
	is
        v_user_id varchar2(50 char);
    begin
        select id into v_user_id from member where member_id = p_member_id;
        return v_user_id;
    end;
	
	function get_user_grade(p_member_id in number)
    return number
    is
        result_value number;
    begin
        select grade into result_value from member where member_id = p_member_id;
        return result_value;
    end;
	
	
	
	procedure get_login_member_no(
		p_id in varchar2,
		p_pwd in varchar2,
		p_member_id out number
	)
	is
	begin
		if check_user(p_id, p_pwd) = 1 then
            select member_id into p_member_id from member where id = p_id and password = p_pwd;
        else
            p_member_id := -1;
        end if;
	end;
	
	procedure get_member_info(
		p_member_id in number,
		p_user_info out sys_refcursor
	)
	is
	begin
		open p_user_info for
        select * from member where member_id = p_member_id;
	end;
	
	procedure add_member_info(
        p_id in varchar2,
        p_pwd in varchar2,
        p_name in varchar2,
        p_phone in varchar2,
        p_birth in varchar2,
        p_result out number
    )
    is
    begin
        insert into member(id, password, name, phone_number, birth_date, join_date, grade)
        values (p_id, p_pwd, p_name, p_phone, to_date(p_birth, 'YYYY-MM-DD'), sysdate, 0);
        p_result := 1;
    exception
        when others then
        p_result := 0;
        raise_application_error(-20100, 'An error occurred: ' || sqlerrm);
    end;
	
	procedure update_member_info(
        p_member_id in number,
        p_pwd in varchar2,
        p_new_pwd in varchar2,
        p_result out number
    )
	is
    begin
        if check_user(get_user_id(p_member_id), p_pwd) = 1 then
            update member set password = p_new_pwd
            where member_id = p_member_id;
            p_result := 1;
        else
            p_result := -1;
        end if;
    end;
	
	procedure delete_member_info(
		p_member_id in number,
		p_pwd in varchar2,
		p_result out number
	)
	is
	begin
		if check_user(get_user_id(p_member_id), p_pwd) = 1 then
            delete from member where member_id = p_member_id;
			p_result := 1;
        else
            p_result := -1;
        end if;
	exception
		when others then
		p_result := 0;
		raise_application_error(-20101, 'An error occurred: ' || sqlerrm);
	end;
    
end;
/



