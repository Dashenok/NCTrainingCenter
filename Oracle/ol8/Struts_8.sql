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
DECLARE
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

	CREATE OR REPLACE PACKAGE pkg_location
		IS
		FUNCTION drop_city(city_name IN VARCHAR2)
			RETURN NUMBER;
	END pkg_location;

	CREATE OR REPLACE PACKAGE BODY  pkg_location
	IS
		FUNCTION drop_city(city_name IN VARCHAR2)
			IS
			TYPE LocNumber IS TABLE OF LOCATIONS.LOCATION_ID%TYPE;
			locnum LocNumber:= LocNumber();
			TYPE DeptName IS TABLE OF DEPARTMENTS.DEPARTMENT_NAME%TYPE;
			depnames DeptName:= DeptName();
			BEGIN
				SELECT DEPARTMENT_NAME INTO depnames FROM DEPARTMENTS WHERE (SELECT COUNT(LOCATION_ID)FROM LOCATIONS WHERE CITY = city_name);
				IF depnames.COUNT > 0 THEN
					FOR i IN 1..depnames.COUNT LOOP
						DBMS_OUTPUT.PUT_LINE(depnames(i));
					END LOOP;
					RETURN 0;
				END IF;
				RETURN -1;
			END;
	END pkg_location;