--------------------------------------------------------
--  파일이 생성됨 - 월요일-5월-13-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package PKG_MEMBER
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE "USER24"."PKG_MEMBER" 
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
--------------------------------------------------------
--  DDL for Package Body PKG_MEMBER
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE PACKAGE BODY "USER24"."PKG_MEMBER" 
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
