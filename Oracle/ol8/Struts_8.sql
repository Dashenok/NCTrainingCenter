-- 1.1 Повторить выполнение задания 1.1 из лабораторной работы 7, включив
-- анонимный PL/SQL-блок в хранимую процедуру, учитывая, что:
-- − название процедуры– employee_generate;
-- − входным параметром процедуры является количество вносимых строк;
-- − для идентификаторов сотрудника использовать значение генератора sequence;
-- − имя, фамилия сотрудника определяется как Ваше имя, фамилия + значение
-- генератора;
-- − E-mail сотрудника определяется как Ваше имя + значение генератора;
-- − дата зачисления определяется как ‘01.01.2000’ + значение генератора;
-- − должность = «Finance Manager»;
-- − остальные значения колонок оставить NULL.
CREATE OR REPLACE PROCEDURE employee_generate
	(emp_number IN NUMBER:=1)
	IS
BEGIN
		FOR i IN 1..emp_number LOOP
			INSERT INTO EMPLOYEES (employee_id, first_name,  last_name,  email, phone_number, hire_date,
														 job_id, salary, commission_pct, manager_id, department_id)
			VALUES (emp_id.nextval,  'Daria', 'Struts'||i, 'daria'||i||'@mail.com', null,
															 TO_DATE('01.01.2000')+i, 'FI_MGR', null, null, null, null );
		END LOOP;
END;

-- 1.2 Создать функцию employee_generate, выполняющую те же действия, что и
-- процедура employee_generate, но возвращающая значение в виде времени выполнения
-- функции в миллисекундах.
CREATE OR REPLACE FUNCTION employee_generate (emp_number IN NUMBER:=1)
	RETURN NUMBER
	IS
	t1 TIMESTAMP; -- момент времени начала выполнения запроса
	t2 TIMESTAMP; -- момент времени завершения выполнения запроса
	delta NUMBER; -- время выполнения запроса
BEGIN
	-- получение момента времени начала процесса
	t1 := SYSTIMESTAMP;
	-- выполнение команд
	employee_generate(3);
	-- получение момента времени завершения процесса
	t2 := SYSTIMESTAMP;
	-- определение разницы через преобразование форматов дата->строка->целое
	delta := TO_NUMBER(TO_CHAR(t2, 'HHMISS.FF3'),'999999.999')-
					 TO_NUMBER(TO_CHAR(t1, 'HHMISS.FF3'),'999999.999');
	RETURN delta;
END;

-- 1.3 Внести изменения в функцию employee_generate с учетом следующих действий:
-- − добавить еще один параметр функции oper_type – тип операции заполнения;
-- − при значении параметра oper_type = ‘loop’ использовать циклический способ
-- внесения, который был ранее в коде;
-- − при значении параметра oper_type = ‘forall’ использовать при внесении пакетную
-- операцию внесения FORALL.
-- Выполнить функцию для двух вариантов значения параметра oper_type и сравнить
-- времена выполнения.
CREATE OR REPLACE PROCEDURE employee_generate
	(emp_number IN NUMBER:=1,
	 oper_type IN VARCHAR:= 'loop')
IS
	TYPE Employee IS TABLE OF EMPLOYEES%ROWTYPE;
	emp_list Employee:=Employee();
	BEGIN
		IF oper_type = 'loop' THEN
			FOR i IN 1..emp_number LOOP
					INSERT INTO EMPLOYEES (employee_id, first_name,  last_name,  email, phone_number, hire_date,
																 job_id, salary, commission_pct, manager_id, department_id)
					VALUES (emp_id.nextval,  'Daria', 'Struts'||i, 'daria'||i||'@mail.com', null,
																	 TO_DATE('01.01.2000')+i, 'FI_MGR', null, null, null, null );
			END LOOP;
		ELSIF oper_type = 'forall' THEN
			emp_list.EXTEND(1000);
			FOR i IN 1..emp_number LOOP
				emp_list(i).employee_id:=emp_id.nextval;
				emp_list(i).first_name:= 'Daria';
				emp_list(i).last_name:= 'Struts';
				emp_list(i).email:= 'daria'||i||'@mail.com';
				emp_list(i).hire_date:=TO_DATE('01.01.2000')+i;
				emp_list(i).job_id := 'FI_MGR';
			END LOOP;

			FORALL i IN 1..emp_number
			INSERT INTO EMPLOYEES VALUES(emp_list(i).employee_id, emp_list(i).first_name, emp_list(i).last_name, emp_list(i).email, emp_list(i).phone_number, emp_list(i).hire_date,
																														emp_list(i).job_id, emp_list(i).salary, emp_list(i).commission_pct, emp_list(i).manager_id, emp_list(i).department_id);
		END IF;
	END;
CREATE OR REPLACE FUNCTION employee_generate (emp_number IN NUMBER:=1)
	RETURN NUMBER
IS
	t1 TIMESTAMP; -- момент времени начала выполнения запроса
	t2 TIMESTAMP; -- момент времени завершения выполнения запроса
	delta NUMBER; -- время выполнения запроса
	BEGIN
		-- получение момента времени начала процесса
		t1 := SYSTIMESTAMP;
		-- выполнение команд
		employee_generate(3, 'forall');
		-- получение момента времени завершения процесса
		t2 := SYSTIMESTAMP;
		-- определение разницы через преобразование форматов дата->строка->целое
		delta := TO_NUMBER(TO_CHAR(t2, 'HHMISS.FF3'),'999999.999')-
						 TO_NUMBER(TO_CHAR(t1, 'HHMISS.FF3'),'999999.999');
		RETURN delta;
	END;

-- 1.4 Внести изменения в функцию employee_generate с учетом следующих действий:
-- − добавить еще один параметр функции random_const – способ внесения значений в
-- таблицу;
-- − если random_const = ‘const’, использовать старый вариант внесения данных по
-- колонкам.
-- − если random_const = ‘random’, для некоторых колонок использовать случайно
-- выбранные значения на основе функций пакета DBMS_RANDOM:
-- a) дата зачисления определяется как ‘01.01.2000’ + случайное число от 1 до
-- 1000;
-- b) идентификатор подразделения определяется случайным числом в
-- допустимом диапазоне идентификаторов подразделений;
-- c) должность определяется случайным числом в допустимом диапазоне
-- идентификаторов, используя порядковый номер получаемой должности из
-- списка должностей
CREATE OR REPLACE FUNCTION job_generate(rownumber IN NUMBER)
	return VARCHAR2
IS
	job_value varchar2(10);
	BEGIN
		SELECT JOB_ID INTO job_value FROM JOBS WHERE ROWNUM = rownumber;
		return job_Value;
	END;

CREATE OR REPLACE PROCEDURE employee_generate
	(emp_number IN NUMBER:=1,
	 oper_type IN VARCHAR:= 'loop',
	 random_const IN VARCHAR:='const')
IS
	TYPE Employee IS TABLE OF EMPLOYEES%ROWTYPE;
	emp_list Employee:=Employee();
	BEGIN
		IF oper_type = 'loop' THEN
			FOR i IN 1..emp_number LOOP
				IF random_const = 'const' THEN
					INSERT INTO EMPLOYEES (employee_id, first_name,  last_name,  email, phone_number, hire_date,
																 job_id, salary, commission_pct, manager_id, department_id)
					VALUES (emp_id.nextval,  'Daria', 'Struts'||i, 'daria'||i||'@mail.com', null,
																	 TO_DATE('01.01.2000')+i, 'FI_MGR', null, null, null, null );
				ELSIF random_const = 'random' THEN
					INSERT INTO EMPLOYEES VALUES (emp_id.nextval,  'Daria', 'Struts'||i, 'daria'||i||'@mail.com', null,
																			TO_DATE('01.01.2000')+DBMS_RANDOM.value(1,1000),job_generate(DBMS_RANDOM.value(1,19)), null, null, null, DBMS_RANDOM.value(1,27)*10 );

				END IF;
			END LOOP;
		ELSIF oper_type = 'forall' THEN
			emp_list.EXTEND(1000);
			FOR i IN 1..emp_number LOOP
				emp_list(i).employee_id:=emp_id.nextval;
				emp_list(i).first_name:= 'Daria';
				emp_list(i).last_name:= 'Struts';
				emp_list(i).email:= 'daria'||i||'@mail.com';
				IF random_const = 'const' THEN
					emp_list(i).hire_date:=TO_DATE('01.01.2000')+i;
					emp_list(i).job_id := 'FI_MGR';
					emp_list(i).department_id := 10;
				ELSIF random_const = 'random' THEN
					emp_list(i).hire_date:=TO_DATE('01.01.2000')+DBMS_RANDOM.value(1,1000);
					emp_list(i).job_id := job_generate(DBMS_RANDOM.value(1,19));
					emp_list(i).department_id := DBMS_RANDOM.value(1,27)*10;
				END IF;
			END LOOP;

			FORALL i IN 1..emp_number
			INSERT INTO EMPLOYEES VALUES(emp_list(i).employee_id, emp_list(i).first_name, emp_list(i).last_name, emp_list(i).email, emp_list(i).phone_number, emp_list(i).hire_date,
																														emp_list(i).job_id, emp_list(i).salary, emp_list(i).commission_pct, emp_list(i).manager_id, emp_list(i).department_id);
		END IF;
	END;
CREATE OR REPLACE FUNCTION employee_generate (emp_number IN NUMBER:=1)
	RETURN NUMBER
IS
	t1 TIMESTAMP; -- момент времени начала выполнения запроса
	t2 TIMESTAMP; -- момент времени завершения выполнения запроса
	delta NUMBER; -- время выполнения запроса
	BEGIN
		-- получение момента времени начала процесса
		t1 := SYSTIMESTAMP;
		-- выполнение команд
		employee_generate(3, 'forall', 'const');
		-- получение момента времени завершения процесса
		t2 := SYSTIMESTAMP;
		-- определение разницы через преобразование форматов дата->строка->целое
		delta := TO_NUMBER(TO_CHAR(t2, 'HHMISS.FF3'),'999999.999')-
						 TO_NUMBER(TO_CHAR(t1, 'HHMISS.FF3'),'999999.999');
		RETURN delta;
	END;
--Создать пакет pkg_location по управлению таблицей подразделений, включающий:
--2.1 функцию удаления заданного города, учитывая, что:
--− Название функции – drop_city;
--− входным параметром является название города;
--− возвращаемое значение – код удаленного города;
--− если города с заданным названием не оказалось, возвращать значение = -1
--− если по заданному городу есть подразделения, вывести на экран список названий
--подразделений и вернуть значение = 0, иначе вернуть код города.
	CREATE OR REPLACE PACKAGE pkg_location
		IS
		TYPE DepList IS TABLE OF DEPARTMENTS%ROWTYPE;
		FUNCTION drop_city(city_name IN VARCHAR2)
			RETURN NUMBER;

	END pkg_location;

CREATE OR REPLACE PACKAGE BODY  pkg_location
	IS
		FUNCTION drop_city(city_name IN VARCHAR2)
			RETURN number
			IS
			TYPE LocNumber IS TABLE OF LOCATIONS.LOCATION_ID%TYPE;
			locnum LocNumber:= LocNumber();
			TYPE dep_rec_name IS RECORD (dep DEPARTMENTS.DEPARTMENT_NAME%TYPE);
			depnames dep_rec_name;
			BEGIN
				--IF depnames.COUNT > 0 THEN
				--	FOR deprt IN depnames LOOP
				--		DBMS_OUTPUT.PUT_LINE(deprt.DEPARTMENT_NAME);
				--	END LOOP;
				--	RETURN 0;
				--ELSE
					DELETE FROM LOCATIONS WHERE CITY = city_name;
					--RETURNING LOCATION_ID INTO locnum;
					--RETURN locnum;
				--END IF;
				RETURN -1;
			END;
	END pkg_location;




CREATE OR REPLACE PACKAGE BODY  pkg_location
IS
	FUNCTION drop_city(city_name IN VARCHAR2)
		RETURN number
	IS
		CURSOR deptcount(city_name VARCHAR2) IS
			SELECT Count(*) FROM DEPARTMENTS D join LOCATIONS L ON (D.LOCATION_ID = L.LOCATION_ID)
			WHERE L.CITY = city_name;
		CURSOR depnames(city_name VARCHAR2) IS
			SELECT D.DEPARTMENT_NAME FROM DEPARTMENTS D join LOCATIONS L ON (D.LOCATION_ID = L.LOCATION_ID)
			WHERE L.CITY = city_name;
	locnum NUMBER;
	depCount NUMBER;
	depname VARCHAR2(100);
	BEGIN
		OPEN deptcount(city_name);
		FETCH deptcount INTO depCount;
		IF depCount > 0 THEN
			FOR dname IN depnames(city_name) LOOP
				DBMS_OUTPUT.PUT_LINE(dname.DEPARTMENT_NAME);
			END LOOP;
			RETURN 0;
		ELSE
			DELETE FROM LOCATIONS WHERE CITY = city_name
			RETURNING LOCATION_ID INTO locnum;
			RETURN locnum;
		END IF;
			RETURN -1;
		END;
END pkg_location;

--2.2 функцию создания города, учитывая, что:
--− название функции – create_city;
--− входными параметрами являются: название города, название страны;
--− возвращаемое значение - новый код созданного города;
--− если название страны отсутствует в таблице БД, должна быть сформирована
--операция внесения этого значения в таблицу по любому из регионов;
--− если название города уже есть в таблице, выдавать сообщение об ошибке «City
--already exists» с указанием уникального кода ошибки.
CREATE OR REPLACE PACKAGE BODY  pkg_location
IS
	FUNCTION drop_city(city_name IN VARCHAR2)
		RETURN number
	IS
		CURSOR deptcount(city_name VARCHAR2) IS
			SELECT Count(*) FROM DEPARTMENTS D join LOCATIONS L ON (D.LOCATION_ID = L.LOCATION_ID)
			WHERE L.CITY = city_name;
		CURSOR depnames(city_name VARCHAR2) IS
			SELECT D.DEPARTMENT_NAME FROM DEPARTMENTS D join LOCATIONS L ON (D.LOCATION_ID = L.LOCATION_ID)
			WHERE L.CITY = city_name;
		locnum NUMBER;
		depCount NUMBER;
		depname VARCHAR2(100);
		BEGIN
			OPEN deptcount(city_name);
			FETCH deptcount INTO depCount;
			IF depCount > 0 THEN
				FOR dname IN depnames(city_name) LOOP
					DBMS_OUTPUT.PUT_LINE(dname.DEPARTMENT_NAME);
				END LOOP;
				RETURN 0;
			ELSE
				DELETE FROM LOCATIONS WHERE CITY = city_name
				RETURNING LOCATION_ID INTO locnum;
				RETURN locnum;
			END IF;
			RETURN -1;
		END;

	FUNCTION create_city(city_name_in VARCHAR2, country_name_in VARCHAR2)
		RETURN NUMBER
	IS
		citycount NUMBER;
		countryID VARCHAR2(3);
		cityID NUMBER;
	BEGIN
		BEGIN
			SELECT COUNT(*) INTO citycount FROM LOCATIONS
			WHERE CITY = city_name_in;
				DBMS_OUTPUT.PUT_LINE('City already exists');
				RETURN -1;
			EXCEPTION
			WHEN NO_DATA_FOUND THEN
				BEGIN
				SELECT COUNTRY_ID INTO countryID FROM COUNTRIES
					WHERE COUNTRY_NAME = country_name_in;
				EXCEPTION WHEN NO_DATA_FOUND THEN
					INSERT INTO COUNTRIES VALUES (SUBSTR(country_name_in,1,2), country_name_in, 4);
					countryID := SUBSTR(country_name_in,1,2);
				END;
				cityID := DBMS_RANDOM.value(3300,9900)*10;
				INSERT INTO LOCATIONS(LOCATION_ID, CITY, COUNTRY_ID)
				VALUES (cityID, city_name_in, countryID);
			RETURN cityID;
		END;
	END;
END pkg_location;


--2.3 функцию удаления всех подразделений заданного города, учитывая, что:
--− название функции – drop_departments;
--− входным параметром является название города;
--− если указанного города не существует, выдать соответствующее сообщение об
--ошибке с указанием уникального кода ошибки;
--− возвращаемое значение - список удаленных подразделения в формате:
--идентификатор подразделения, название подразделения.
CREATE OR REPLACE PACKAGE pkg_location
IS
	TYPE DepList IS TABLE OF DEPARTMENTS%ROWTYPE;
	FUNCTION drop_city(city_name IN VARCHAR2)
		RETURN NUMBER;

END pkg_location;
CREATE OR REPLACE PACKAGE BODY  pkg_location
IS
	FUNCTION drop_city(city_name IN VARCHAR2)
		RETURN number
	IS
		CURSOR deptcount(city_name VARCHAR2) IS
			SELECT Count(*) FROM DEPARTMENTS D join LOCATIONS L ON (D.LOCATION_ID = L.LOCATION_ID)
			WHERE L.CITY = city_name;
		CURSOR depnames(city_name VARCHAR2) IS
			SELECT D.DEPARTMENT_NAME FROM DEPARTMENTS D join LOCATIONS L ON (D.LOCATION_ID = L.LOCATION_ID)
			WHERE L.CITY = city_name;
		locnum NUMBER;
		depCount NUMBER;
		depname VARCHAR2(100);
		BEGIN
			OPEN deptcount(city_name);
			FETCH deptcount INTO depCount;
			IF depCount > 0 THEN
				FOR dname IN depnames(city_name) LOOP
					DBMS_OUTPUT.PUT_LINE(dname.DEPARTMENT_NAME);
				END LOOP;
				RETURN 0;
			ELSE
				DELETE FROM LOCATIONS WHERE CITY = city_name
				RETURNING LOCATION_ID INTO locnum;
				RETURN locnum;
			END IF;
			RETURN -1;
		END;

	FUNCTION create_city(city_name_in VARCHAR2, country_name_in VARCHAR2)
		RETURN NUMBER
	IS
		citycount NUMBER;
		countryID VARCHAR2(3);
		cityID NUMBER;
		BEGIN
			BEGIN
				SELECT COUNT(*) INTO citycount FROM LOCATIONS
				WHERE CITY = city_name_in;
				DBMS_OUTPUT.PUT_LINE('City already exists');
				RETURN -1;
				EXCEPTION
				WHEN NO_DATA_FOUND THEN
				BEGIN
					SELECT COUNTRY_ID INTO countryID FROM COUNTRIES
					WHERE COUNTRY_NAME = country_name_in;
					EXCEPTION WHEN NO_DATA_FOUND THEN
					INSERT INTO COUNTRIES VALUES (SUBSTR(country_name_in,1,2), country_name_in, 4);
					countryID := SUBSTR(country_name_in,1,2);
				END;
				cityID := DBMS_RANDOM.value(3300,9900)*10;
				INSERT INTO LOCATIONS(LOCATION_ID, CITY, COUNTRY_ID)
				VALUES (cityID, city_name_in, countryID);
				RETURN cityID;
			END;
		END;

	FUNCTION drop_departments(city_name_in IN VARCHAR)
		return DepList
	IS
		DepartmentList DepList;
		locID VARCHAR2(30);
		BEGIN
			DepartmentList.EXTEND(50);
			SELECT LOCATION_ID INTO locID FROM LOCATIONS WHERE CITY = city_name_in;
			DELETE FROM DEPARTMENTS WHERE LOCATION_ID = locID
			RETURNING DEPARTMENT_ID,DEPARTMENT_NAME, MANAGER_ID, LOCATION_ID INTO DepartmentList;
			RETURN DepartmentList;
			EXCEPTION WHEN NO_DATA_FOUND THEN
			RAISE_APPLICATION_ERROR(-20555,
															'City not found');
		END;
END pkg_location;
