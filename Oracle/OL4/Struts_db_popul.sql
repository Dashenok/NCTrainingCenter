--1.1.1 Создать представление, которое:
-- получает фамилию и имя сотрудников;
-- получает зарплату, начисленную каждому сотруднику за весь период его работы в компании с
-- учетом комиссионных;
-- получает для каждого сотрудника количество лет работы в компании;
-- имя сотрудников представить как: первая буква в верхнем регистре, остальные - в нижнем;
-- количество месяцев округлить до ближайшего целого;
-- отсортировать сотрудников в порядке возрастания размера начисленной зарплаты.
-- Выполните запрос к созданному представлению.
DROP VIEW EMP_SAL;
CREATE VIEW EMP_SAL AS 
SELECT E.LAST_NAME AS SURNAME,
INITCAP(E.FIRST_NAME) AS NAME,
TRUNC(MONTHS_BETWEEN (J.END_DATE, J.START_DATE), 0) * E.SALARY * (1 + NVL(E.COMMISSION_PCT, 0)) AS SALARYSUM,
ROUND((J.END_DATE - J.START_DATE)/365, 0) AS YEARS
FROM EMPLOYEES E JOIN JOB_HISTORY J ON (E.EMPLOYEE_ID = J.EMPLOYEE_ID)
ORDER BY SALARYSUM;
--Kaufling	Payam	    86900	1
--Taylor	Jonathon	92880	1
--Taylor	Jonathon	113520	1
--Raphaely	Den	        231000	2
--Whalen	Jennifer	233200	5
--Whalen	Jennifer	281600	5
--Hartstein	Michael	    598000	4
--Kochhar	Neena	    680000	3
--Kochhar	Neena	    833000	4
--De Haan	Lex	        1122000	6

--1.1.2. Создать представление, которое:
-- получает фамилии, имена сотрудников;
-- получает для сотрудников надбавку к зарплате "Tax", которая определяется как 4% за каждый
-- год работы для Programmer, 3% за каждый год работы для Accountant, 2% за каждый год работы для
-- Sales Manager и 0.1% за каждый год работы для Administration Assistant.
-- Выполните запрос к созданному представлению.
DROP VIEW EMP_SAL;
Create VIEW tax AS SELECT E.LAST_NAME AS SURNAME,
E.FIRST_NAME AS NAME,
JOBS.JOB_TITLE AS JOBTITLE,
DECODE(JOBS.JOB_TITLE, 'Programmer', ROUND((J.END_DATE - J.START_DATE)/365, 0)*4,
                              'Accountant', ROUND((J.END_DATE - J.START_DATE)/365, 0)*3,
                              'Sales Manager',ROUND((J.END_DATE - J.START_DATE)/365, 0)*2,
                              'Administration Assistant',ROUND((J.END_DATE - J.START_DATE)/365, 0)*0,1,
                              0) AS TAX
FROM EMPLOYEES E JOIN JOB_HISTORY J ON (E.EMPLOYEE_ID = J.EMPLOYEE_ID)
LEFT JOIN JOBS ON (J.JOB_ID = JOBS.JOB_ID);
--Kochhar	Neena	Public Accountant	
--Kochhar	Neena	Accounting Manager	
--De Haan	Lex	Programmer	                       24
--Raphaely	Den	Stock Clerk	
--Kaufling	Payam	Stock Clerk	
--Taylor	Jonathon	Sales Representative	
--Taylor	Jonathon	Sales Manager	            2
--Whalen	Jennifer	Public Accountant	
--Whalen	Jennifer	Administration Assistant	0
--Hartstein	Michael	Marketing Representative	

--1.1.3. Создать представление, которое:
-- получает фамилии сотрудников
-- получает количество выходных дней (суббота, воскресенье) с момента их зачисления на
--работу, например, если сотрудник был зачислен в прошлую пятницу, а сегодня понедельник, то у него
--уже было 2 выходных дня, хотя всего прошло 3 дня с момента его зачисления.
-- сотрудники зачислены в июле 1998 года;
-- отсортировать сотрудников в порядке убывания количествв выходных дней.
--Выполните запрос к созданному представлению.
DROP VIEW WEEKENDS;


-- 2.0. Загрузить структуру новой БД с учетом созданного пользователя под именем new_db.
-- 2.1. Для всех таблиц новой БД создать генераторы последовательности, обеспечивающие
-- автоматическое создание новых значений колонок, входящих в первичный ключ.
CREATE SEQUENCE Country_sec INCREMENT BY 1 START WITH 1 MAXVALUE 251 NOCACHE NOCYCLE;
CREATE SEQUENCE City_sec INCREMENT BY 1 START WITH 1 MAXVALUE 9999 NOCACHE NOCYCLE;
CREATE SEQUENCE Location_sec INCREMENT BY 1 START WITH 1000 MAXVALUE 99999 NOCACHE NOCYCLE;
CREATE SEQUENCE Job_sec INCREMENT BY 1 START WITH 100 MAXVALUE 999 NOCACHE NOCYCLE;
CREATE SEQUENCE Department_sec INCREMENT BY 1 START WITH 10 MAXVALUE 99 NOCACHE NOCYCLE;
CREATE SEQUENCE Employee_sec INCREMENT BY 1 START WITH 1000 MAXVALUE 9999 NOCACHE NOCYCLE;
CREATE SEQUENCE Manager_sec INCREMENT BY 1 START WITH 10 MAXVALUE 99 NOCACHE NOCYCLE;
CREATE SEQUENCE Product_sec INCREMENT BY 1 START WITH 1000 MAXVALUE 9999 NOCACHE NOCYCLE;
CREATE SEQUENCE Counterparty_sec INCREMENT BY 1 START WITH 100 MAXVALUE 999 NOCACHE NOCYCLE;
CREATE SEQUENCE PurchaseOrder_sec INCREMENT BY 2 START WITH 1 MAXVALUE 99999999 NOCACHE NOCYCLE;
CREATE SEQUENCE SalesOrder_sec INCREMENT BY 3 START WITH 1 MAXVALUE 99999999 NOCACHE NOCYCLE;
CREATE SEQUENCE OrderProducts_sec INCREMENT BY 1 START WITH 1000000000 MAXVALUE 9999999999 NOCACHE NOCYCLE;
-- Sequence created.

--2.2. Для каждой таблицы новой БД создать 2 команды на внесение данных (внести две строки).
INSERT INTO Country VALUES (1, 'JAPAN');
INSERT INTO Country VALUES (2, 'USA');

INSERT INTO CITY VALUES (1000, 'TOKYO', 1);
INSERT INTO CITY VALUES (2000, 'NEW YORK', 2);

INSERT INTO LOCATION VALUES (1500, 'TAKESITA', 65789, '34', '54A', 1000);
INSERT INTO LOCATION VALUES (2222, 'Third Avenue', 87654, '45', '65', 2000);

INSERT INTO JOB VALUES (111, 'SALES MANAGER');
INSERT INTO JOB VALUES (222, 'PROGRAMMER');

INSERT INTO DEPARTMENT VALUES (22, 'TOKYO-1', 'SALES', 1500);
INSERT INTO DEPARTMENT VALUES (33, 'NEY YORK', 'SALES', 2222);

INSERT INTO Employee VALUES (3333, 'GALT', 'JOHN', TO_DATE('05-02-2001', 'dd-mm-yyyy'), 300000, 2, 222, 22);
INSERT INTO Employee VALUES (4444, 'REARDEN', 'KHENK', TO_DATE('25-04-2000', 'dd-mm-yyyy'), 40000, 2, 111, 33);

INSERT INTO Manager VALUES (Manager_SEC.NEXTVAL, 3333);
INSERT INTO Manager VALUES (Manager_SEC.NEXTVAL, 4444);

INSERT INTO Product VALUES (5555, 'STEAL', NULL);
INSERT INTO Product VALUES (6666, 'ORA', NULL);

INSERT INTO Counterparty VALUES (123, 'SMB', '09029390238908', 1500);
INSERT INTO Counterparty VALUES (234, 'SMT', '89034895083490', 2222);

INSERT INTO PurchaseOrder VALUES (11111111, SYSDATE, 123, 3333);
INSERT INTO PurchaseOrder VALUES (22222222, SYSDATE + 1,123, 3333);

INSERT INTO SalesOrder VALUES (33333333, SYSDATE, 234, 4444);
INSERT INTO SalesOrder VALUES (44444444, SYSDATE + 1, 234, 4444);

INSERT INTO SalePrice VALUES (SYSDATE, 6666, 234, 40);
INSERT INTO SalePrice VALUES (SYSDATE + 1, 6666, 123, 42);

INSERT INTO PurchasePrice VALUES (SYSDATE, 5555, 123, 20);
INSERT INTO PurchasePrice VALUES (SYSDATE + 1, 5555, 234, 21);

INSERT INTO OrderProducts VALUES (OrderProducts_SEC.NEXTVAL, 11111111, 6666, 5,  50);
INSERT INTO OrderProducts VALUES (OrderProducts_SEC.NEXTVAL, 22222222, 6666, 5,  20);

-- 2.3. Выполнить команду по фиксации всех изменений в БД.
COMMIT;
--Commit complete.

--2.4. Для одной из таблиц, содержащей ограничение целостности внешнего ключа, выполнить
--команду по изменению значения колонки внешнего ключа на значение, отсутствующее в колонке
--первичного ключа соответствующей таблицы. Проверить реакцию СУБД на подобное изменение.
UPDATE DEPARTMENT SET LOCATION_ID = 3333 WHERE DEPARTMENT_ID = 22;
--ORA-02291: integrity constraint (NEW_DB.SYS_C008356) violated - parent key not found

--2.5. Для одной из таблиц, содержащей ограничение целостности первичного ключа, выполнить
--команду по изменению значения колонки первичного ключа на значение, отсутствующее в колонке
--внешнего ключа соответствующей таблицы. Проверить реакцию СУБД на подобное изменение.
UPDATE DEPARTMENT SET DEPARTMENT_ID = 12 WHERE DEPARTMENT_NAME = 'TOKYO-1';
--ORA-02292: integrity constraint (NEW_DB.SYS_C008367) violated - child record found

--2.6. Для одной из таблиц, содержащей ограничение целостности первичного ключа, выполнить
--одну команду по удалению строки со значением колонки первичного ключа, присутствующее в
--колонке внешнего ключа соответствующей таблицы. Проверить реакцию СУБД на изменение.
DELETE DEPARTMENT WHERE DEPARTMENT_NAME = 'TOKYO-1';
-- ORA-02292: integrity constraint (NEW_DB.SYS_C008367) violated - child record found

--2.7. Для одной из таблиц изменить ограничение целостности внешнего ключа,
--обеспечивающее каскадное удаление. Повторить задание 6 для измененной таблицы.
ALTER TABLE EMPLOYEE DROP CONSTRAINT SYS_C008367;
DELETE DEPARTMENT WHERE DEPARTMENT_NAME = 'TOKYO-1';
--Table EMPLOYEE altered.
--1 row deleted

--2.8. Выполнить команду по отмене (откату) всех операций изменений из пунктов 4-7.
ROLLBACK;



--3.1. Увеличить комиссионные на n% всем сотрудникам, которые находятся на должности
--«Administration Assistant», где n – количество лет, которые проработали сотрудники.
UPDATE EMPLOYEES SET COMMISSION_PCT = NVL(COMMISSION_PCT, 0) + ROUND((SYSDATE - HIRE_DATE)/365, 0)/100
WHERE JOB_ID IN (SELECT JOB_ID FROM JOBS WHERE JOB_TITLE LIKE 'Administration Assistant');
--1 row updated.

--3.2. Уволить всех сотрудников (удалить из таблицы), которые проработали более 20 лет на
--должности Shipping Clerk. Перед удалением сохранить информацию об увольняемых сотрудниках в
--отдельную таблицу employee_drop, которая содержит такую же структуру, как и таблица employee.
CREATE TABLE employee_drop AS SELECT EMPLOYEE_ID, 
FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, 
JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID 
FROM EMPLOYEES 
WHERE (SYSDATE - HIRE_DATE)/365 > 20 
AND JOB_ID IN (SELECT JOB_ID FROM JOBS WHERE JOB_TITLE LIKE 'Shipping Clerk');
DELETE EMPLOYEES WHERE (SYSDATE - HIRE_DATE)/365 > 20 AND JOB_ID IN (SELECT JOB_ID FROM JOBS WHERE JOB_TITLE LIKE 'Shipping Clerk');
COMMIT;


--Для того, чтобы сохранить данные, накопленные в тарой БД, необходимо выполнить перенос
--данных из таблиц старой БД в таблицы новой БД.
--Если при переносе в столбах новых таблиц окажутся неопределенные значения, то необходимо
--заменить эти значения на значения-константы с учетом семантики столбцов.
--Использовать следующий вариант запросов по переносу:
--INSERT INTO NEW_DB.таблица_новой_бд (колонки новой БД)
--SELECT …. FROM OLD_DB.таблица_старой_бд …;
--Необходимо учесть установку прав доступа к таблицам старой БД, используя команду:
--GRANT SELECT ON OLD_DB.таблица_старой_бд TO NEW_DB;
--Все операции оформить в виде одной транзакции.

GRANT SELECT ON OLD_DB.COUNTRIES TO NEW_DB;
GRANT SELECT ON OLD_DB.DEPARTMENTS TO NEW_DB;
GRANT SELECT ON OLD_DB.EMPLOYEES TO NEW_DB;
GRANT SELECT ON OLD_DB.JOBS TO NEW_DB;
GRANT SELECT ON OLD_DB.LOCATIONS TO NEW_DB;
GRANT SELECT ON OLD_DB.REGIONS TO NEW_DB;
INSERT INTO NEW_DB.JOB (JOB_ID, JOB_NAME)
SELECT NEW_DB.job_sec.NEXTVAL, JOB_TITLE  FROM OLD_DB.JOBS;
INSERT INTO NEW_DB.LOCATION (LOCATION_ID, STREET, POSTAL_CODE, CITY_ID, BLD, APT)
SELECT NEW_DB.location_sec.NEXTVAL, STREET_ADDRESS, 00000, 1000, 1, 1 FROM OLD_DB.LOCATIONS;
INSERT INTO NEW_DB.DEPARTMENT (DEPARTMENT_ID, DEPARTMENT_NAME, DEPARTMENT_TYPE, LOCATION_ID)
SELECT NEW_DB.department_sec.NEXTVAL, SUBSTR(DEPARTMENT_NAME,1, 15), 'sales', 1500  FROM OLD_DB.DEPARTMENTS;
INSERT INTO NEW_DB.EMPLOYEE (EMPLOYEE_ID, EMPLOYEE_FIRST_NAME, EMPLOYEE_LAST_NAME, HIRE_DATE, SALARY, COMM, JOB_ID, DEPT_ID)
SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, HIRE_DATE, 1000, 0, 111, 22    FROM OLD_DB.EMPLOYEES;
COMMIT;
-- 19 rows inserted.
-- 23 rows inserted.
-- 27 rows inserted.
-- 107 rows inserted.

