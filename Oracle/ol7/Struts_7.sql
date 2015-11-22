--1.1 В предыдущей лабораторной работе при рассмотрении иерархических запросов
--был указан способ их использования для массовой (пакетной) генерации значений атрибутов
--таблиц.
--Создать запрос типа INSERT ALL по автоматической регистрации в БД 10000
--сотрудников, учитывая следующее:
--− для идентификаторов сотрудника использовать значение, сгенерированное
--иерархическим запросом ( значение генератора);
--− имя, фамилия сотрудника определяется как Ваше имя, фамилия + значение
--генератора;
--− E-mail сотрудника определяется как Ваше имя + значение генератора;
--− дата зачисления определяется как ‘01.01.2000’ + значение генератора;
--− должность = «Finance Manager»;
--− остальные значения колонок оставить NULL.
ALTER SESSION SET NLS_DATE_FORMAT = 'dd.mm.yyyy';
CREATE SEQUENCE emp_id START With 207;
INSERT ALL
	INTO EMPLOYEES (employee_id, first_name,  last_name,  email, phone_number, hire_date, job_id)
    VALUES (emp_id.nextval,  'Daria', 'Struts'||cnt, 'daria'||cnt||'@mail.com', null,
		TO_DATE('01.01.2000')+cnt, 'FI_MGR')
SELECT rownum as cnt FROM dual
CONNECT BY level <= 10000;

-- 1.2 Предыдущее решение позволяет создавать простые генераторы.
-- Отменить операцию внесения данных предыдущего задания.
-- Создать анонимный PL/SQL-блок, автоматически регистрирующий в БД 10000
-- сотрудников, учитывая условия из задания 1.1.

ROLLBACK;
BEGIN
	FOR i IN 1..10000 LOOP
		INSERT INTO EMPLOYEES (employee_id, first_name,  last_name,  email, phone_number, hire_date, job_id)
		VALUES (emp_id.nextval,  'Daria', 'Struts'||i, 'daria'||i||'@mail.com', null,
														 TO_DATE('01.01.2000')+i, 'FI_MGR');
	END LOOP;
END;
--2. В решение задания 1.2 добавить контроль ограничений целостности:
-- - внесения дубликатов по E-mail сотрудников, нарушающих ограничение целостности
-- UNIQUE, с выводом ошибки, например, “ E-mail Ivanov already exists!”;
-- - внесения несуществующей должности с выводом ошибки, например, “Job = Student
-- is incorrect job! ”
-- Для проверки срабатывания созданных исключений последовательно выполнить
-- PL/SQL-блок, вносящий:
-- 1) те же E-mail, что и при выполнении задания 1.2;
-- 2) любую несуществующую должность.
ALTER TABLE EMPLOYEES ADD CONSTRAINT email_un UNIQUE (EMAIL);

DECLARE
	v_email EMPLOYEES.EMAIL%TYPE;
	v_count_emails NUMBER;
BEGIN
	FOR i IN 1..10000 LOOP
		v_email:= 'daria'||i||'@mail.com';
		SELECT count(*)
		INTO v_count_emails
		FROM EMPLOYEES
		WHERE EMAIL = v_email;
		INSERT INTO EMPLOYEES (employee_id, first_name,  last_name,  email, phone_number, hire_date,
													 job_id)
		VALUES (emp_id.nextval,  'Daria', 'Struts'||i, v_email, null,
														 TO_DATE('01.01.2000')+i, 'FI_MGR');
	END LOOP;
	EXCEPTION
	WHEN DUP_VAL_ON_INDEX  THEN
	RAISE_APPLICATION_ERROR(-20555,
													'E-mail '||v_email||' already exists!');
END;

DECLARE
	v_job EMPLOYEES.JOB_ID%TYPE;
BEGIN
	FOR i IN 1..10000 LOOP
		SELECT JOBS.JOB_ID INTO v_job FROM JOBS WHERE JOBS.JOB_ID = 'BIG_BOSS';
		INSERT INTO EMPLOYEES (employee_id, first_name,  last_name,  email, phone_number, hire_date, job_id)
		VALUES (emp_id.nextval,  'Daria', 'Struts'||i, 'struts'||i||'@mail.com', null,
														 TO_DATE('01.01.2000')+i, v_job);

	END LOOP;
	EXCEPTION
	WHEN NO_DATA_FOUND  THEN
	RAISE_APPLICATION_ERROR(-20555,
													'Job = '||v_job||' is incorrect job!');
END;

-- Задание 3 Работа с курсорами
-- Создать анонимный PL/SQL-блок, выводящий на экран описание структуры таблицы
-- EMPLOYEES

DECLARE
	CURSOR con_list IS
    SELECT
      USER_TAB_COLUMNS.COLUMN_NAME,
      USER_TAB_COLUMNS.DATA_TYPE,
      USER_CONSTRAINTS.SEARCH_CONDITION,
      USER_CONSTRAINTS.CONSTRAINT_TYPE,
      R.TABLE_NAME
    FROM USER_TAB_COLUMNS, USER_CONS_COLUMNS, USER_CONSTRAINTS LEFT JOIN USER_CONSTRAINTS R ON (R.CONSTRAINT_NAME = USER_CONSTRAINTS.R_CONSTRAINT_NAME)
    WHERE USER_TAB_COLUMNS.TABLE_NAME = 'EMPLOYEES' AND USER_CONS_COLUMNS.TABLE_NAME = 'EMPLOYEES'
          AND USER_CONS_COLUMNS.COLUMN_NAME = USER_TAB_COLUMNS.COLUMN_NAME
          AND USER_CONSTRAINTS.TABLE_NAME = 'EMPLOYEES' AND USER_CONSTRAINTS.CONSTRAINT_NAME = USER_CONS_COLUMNS.CONSTRAINT_NAME
  ;
	con_value con_list%ROWTYPE;
  search_cond VARCHAR2(40);
	BEGIN
	OPEN con_list;
	DBMS_OUTPUT.PUT_LINE('Column Name    Type      Constraints');
	FETCH con_list INTO con_value;
	WHILE con_list%FOUND LOOP
    IF UPPER(con_value.DATA_TYPE) = 'P' THEN
      search_cond:= 'Primary key';
    ELSIF UPPER(con_value.DATA_TYPE) = 'U' THEN
      search_cond:= 'Unique';
    ELSIF UPPER(con_value.DATA_TYPE) = 'R' THEN
     search_cond:= 'Foreign key :'||con_value.TABLE_NAME;
    ELSE
      search_cond:= con_value.SEARCH_CONDITION;
      END IF;
		DBMS_OUTPUT.PUT_LINE(RPAD(con_value.COLUMN_NAME, 15)|| RPAD(con_value.DATA_TYPE, 10)||search_cond);
		FETCH con_list INTO con_value;
	END LOOP;
	CLOSE con_list;
	END;

--4.1 Создать анонимный PL/SQL-блок, автоматизирующий этот процесс на основе
--шагов:
--− определение максимального значения идентификатора подразделения в таблице
--Departments и идентификатора сотрудника в таблице Employees;
--− проверка наличия генератора в БД с учетом заранее известных названий для таблиц
--Departments, Employees, используя запрос по шаблону select sequence_name from
--user_sequences where sequence_name = 'название_в_верхнем_регистре';
--− если генераторы уже существуют, выполнение команды удаления генераторов;
--− создание генераторов с учетом смещений начального значения, превышающего на 1
--полученные максимальные значения.

DECLARE
	s_name varchar2(30);
	max_emp EMPLOYEES.EMPLOYEE_ID%TYPE;
	max_dept DEPARTMENTS.DEPARTMENT_ID%TYPE;
BEGIN
	BEGIN
			SELECT MAX(EMPLOYEES.EMPLOYEE_ID)
      INTO max_emp
      FROM EMPLOYEES;
      SELECT MAX(DEPARTMENTS.DEPARTMENT_ID)
      INTO max_dept
      FROM DEPARTMENTS;
  EXCEPTION
      WHEN OTHERS THEN
					RAISE_APPLICATION_ERROR(-20558,
								'Some Error');

	END;
  BEGIN
	SELECT sequence_name
		INTO s_name
		FROM user_sequences
		WHERE sequence_name = UPPER('emp_id');
		execute immediate 'drop sequence emp_id';
		execute immediate 'create sequence emp_id start with '||(max_emp+1);
 EXCEPTION
		WHEN NO_DATA_FOUND THEN
		execute immediate 'create sequence emp_id start with '||(max_emp+1);
 END;
 BEGIN
	SELECT sequence_name
		INTO s_name
		FROM user_sequences
		WHERE sequence_name = UPPER('dept_id');
		execute immediate 'drop sequence dept_id';
		execute immediate 'create sequence dept_id start with '||(max_dept+1);
 EXCEPTION
		WHEN NO_DATA_FOUND THEN
		execute immediate 'create sequence dept_id start with '||(max_dept+1);
 END;
END;

--4.2 В решение задания 4.1 изменить PL/SQL-код так, чтобы не было необходимости
--проверять наличие генераторов в БД через создание заглушки на исключение, возникающее
--из-за ошибки создания генератора с уже существующим названием.

DECLARE
	max_emp EMPLOYEES.EMPLOYEE_ID%TYPE;
	max_dept DEPARTMENTS.DEPARTMENT_ID%TYPE;
BEGIN
	BEGIN
	SELECT MAX(EMPLOYEES.EMPLOYEE_ID)
      INTO max_emp
      FROM EMPLOYEES;
      SELECT MAX(DEPARTMENTS.DEPARTMENT_ID)
      INTO max_dept
      FROM DEPARTMENTS;
  EXCEPTION
      WHEN OTHERS THEN
					RAISE_APPLICATION_ERROR(-20558,
								'Some Error');

	END;
  BEGIN
	BEGIN
		execute immediate 'drop sequence emp_id';
	EXCEPTION
       WHEN OTHERS THEN
        NULL;
    END;
  execute immediate 'create sequence emp_id start with '||(max_emp+1);
	BEGIN
        execute immediate 'drop sequence dept_id';
	EXCEPTION
       WHEN OTHERS THEN
        NULL;
    END;
  execute immediate 'create sequence dept_id start with '||(max_dept+1);
  EXCEPTION
		WHEN OTHERS THEN
					RAISE_APPLICATION_ERROR(-20559,
								'Some Error with sequences');
  END;
END;

--Задание 5 Динамические запросы
--Отменить ранее выполненные операции внесения записей по сотрудникам.
--Создать анонимный PL/SQL-блок, который автоматически зарегистрирует
--сотрудников с фамилией (last_name), начинающейся на букву C или D, как пользователей
--Oracle с учетом условий:
--− имена пользователей (логины) совпадают с Last_name сотрудников из таблицы
--employees;
--− пароль генерируется как любая константа;
--− пользователю-сотруднику после регистрации предоставляется право входа в
--систему, т.е. автоматически выполняется команда GRANT CONNECT TO пользователь;
--− если пользователь-сотрудник с указанным логином уже существует, создать этого
--пользователя с логином = First_name + Last_name, например AnthonyCabrio;
--Необходимо учесть, что пользователь, запускающий созданный скрипт, должен иметь
--полномочия на выполнение команд: CREATE USER, GRANT CONNECT.

DECLARE
  CURSOR emp_list IS
    SELECT replace(emp.LAST_NAME, ' ', '') ln, emp.FIRST_NAME FIRST_NAME
    FROM EMPLOYEES emp
    WHERE emp.LAST_NAME LIKE 'C%'
          OR emp.LAST_NAME LIKE 'D%';
BEGIN
  FOR emp_rec IN emp_list LOOP
    BEGIN
      EXECUTE IMMEDIATE 'Create user '||emp_rec.ln||' identified by '||DBMS_RANDOM.STRING('A', 3);
      EXECUTE IMMEDIATE 'Grant connect to '||emp_rec.ln;
      EXCEPTION
      WHEN OTHERS THEN
        EXECUTE IMMEDIATE 'Create user '||emp_rec.ln||emp_rec.FIRST_NAME||' identified by '||DBMS_RANDOM.STRING('A', 3);
        EXECUTE IMMEDIATE 'Grant connect to '||emp_rec.ln||emp_rec.FIRST_NAME;
    END;
  END LOOP;
END;
-- Задание 6 Пакетная работа с данными
-- Повторить выполнение задания 1.2, используя пакетную операцию внесения FORALL.
-- Предварительно отменить все ранее внесенные операции по предыдущим заданиям.
-- Сравнить времена выполнения PL/SQL-блоков этого задания и второго задания.
ROLLBACK;



DECLARE
  TYPE Employee IS TABLE OF EMPLOYEES%ROWTYPE;
  emp_list Employee:=Employee();
BEGIN
  emp_list.EXTEND(100);
  FOR i IN 1..100 LOOP
    emp_list(i).employee_id:=emp_id.nextval;
    emp_list(i).first_name:= 'Daria';
    emp_list(i).last_name:= 'Struts';
    emp_list(i).email:= 'daria'||i||'@mail.com';
    emp_list(i).hire_date:=TO_DATE('01.01.2000')+i;
    emp_list(i).job_id := 'FI_MGR';
  END LOOP;
FORALL i IN 1..100
  INSERT INTO EMPLOYEES  (employee_id, first_name,  last_name,  email,
                          hire_date, job_id)
  VALUES(emp_list(i).employee_id, emp_list(i).first_name, emp_list(i).last_name, emp_list(i).email,
         emp_list(i).hire_date, emp_list(i).job_id);
END;
