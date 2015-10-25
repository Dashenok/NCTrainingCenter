-- 1.1. Выполнить запрос, который получает фамилии сотрудников и их E-mail адреса в
--полном формате: значение атрибута E-mail + "@Netcracker.com"
SELECT 
	last_name, 
	email||'@Netcracker.com' AS email
FROM employees;
--King	SKING@Netcracker.com
--Kochhar	NKOCHHAR@Netcracker.com
--De Haan	LDEHAAN@Netcracker.com
--Hunold	AHUNOLD@Netcracker.com
--Ernst	BERNST@Netcracker.com
--Austin	DAUSTIN@Netcracker.com
--Pataballa	VPATABAL@Netcracker.com
--Lorentz	DLORENTZ@Netcracker.com
--Greenberg	NGREENBE@Netcracker.com
--Faviet	DFAVIET@Netcracker.com

-- 1.2.Выполнить запрос, который:
-- получает фамилию сотрудников и их зарплату;
-- зарплата превышает 15000$.
SELECT 
	last_name, 
	salary 
FROM employees 
WHERE salary >= 15000;
--King	    24000
--Kochhar	17000
--De Haan	17000
	
-- 1.3. Выполнить запрос, который получает фамилии сотрудников, зарплату, комиссионные,
-- их зарплату за год с учетом комиссионных.
SELECT 
	last_name, 
	salary, 
	NVL(commission_pct, 0),
	salary*(12 + NVL(commission_pct, 0)) AS AnnualSalary
FROM employees;
--Matos	    2600	0	31200
--Vargas	2500	0	30000
--Russell	14000	0.4	173600
--Partners	13500	0.3	166050
--Errazuriz	12000	0.3	147600
--Cambrault	11000	0.3	135300
--Zlotkey	10500	0.2	128100
--Tucker	10000	0.3	123000

--2.1 Выполнить запрос, который:
-- получает для каждого сотрудника cтроку в формате
--'Dear '+A+ ' ' + B + ’! ' + ‘ Your salary = ‘ + C,
-- где A = {‘Mr.’,’Mrs.’} – сокращенный вариант обращения к мужчине или женщине
--(предположить, что женщиной являются все сотрудницы, имя которых заканчивается на букву
--‘a’ или ‘e’)
--B – фамилия сотрудника;
--C – годовая зарплата с учетом комиссионных сотрудника
SELECT 
	'Dear Mrs. '||last_name||'! Your salary = '||salary*(12 + NVL(commission_pct, 0)) AS SALARYINFO
FROM employees 
WHERE first_name LIKE '%a' OR first_name LIKE '%e'
UNION
SELECT 
	'Dear Mr. '||last_name||'! Your salary = '||salary*(12 + NVL(commission_pct, 0)) 
FROM employees 
WHERE first_name NOT LIKE '%a' AND first_name NOT LIKE '%e';
--Dear Mr. Urman! Your salary = 93600
--Dear Mr. Vargas! Your salary = 30000
--Dear Mr. Weiss! Your salary = 96000
--Dear Mr. Whalen! Your salary = 52800
--Dear Mr. Zlotkey! Your salary = 128100
--Dear Mrs. Atkinson! Your salary = 33600
--Dear Mrs. Bissot! Your salary = 39600
--Dear Mrs. Cambrault! Your salary = 91500

-- 3.1 Выполнить запрос, который:
-- получает названия подразделений;
-- подразделения расположены в городе Seattle.
SELECT 
	DEPARTMENTS.DEPARTMENT_NAME 
FROM LOCATIONS JOIN DEPARTMENTS ON (LOCATIONS.LOCATION_ID = DEPARTMENTS.LOCATION_ID) 
WHERE city = 'Seattle';
--Administration
--Purchasing
--Executive
--Finance
--Accounting
--Treasury
--Corporate Tax
--Control And Credit
--Shareholder Services

--3.2 Выполнить запрос, который:
-- получает фамилию, должность, номер подразделения сотрудников
-- сотрудники работают в городе Toronto.
SELECT 
	E.LAST_NAME AS SURNAME,
	J.JOB_TITLE AS JOB, 
	D.DEPARTMENT_ID AS DEPTNO
FROM DEPARTMENTS D JOIN LOCATIONS L ON (L.LOCATION_ID = D.LOCATION_ID) 
JOIN EMPLOYEES E ON (E.DEPARTMENT_ID = D.DEPARTMENT_ID)
JOIN JOBS J ON (E.JOB_ID = J.JOB_ID)
WHERE L.CITY = 'Toronto';
--Hartstein	Marketing Manager	        20
--Fay	    Marketing Representative	20

-- 3.3 Выполнить запрос, который:
-- получает номер и фамилию сотрудника, номер и фамилию его менеджера
-- для сотрудников без менеджеров выводить фамилию менеджера в виде «No manager».
SELECT 
	E.EMPLOYEE_ID AS EMPNO,
	E.LAST_NAME AS EMPSURNAME,
	M.EMPLOYEE_ID AS MANNO,
	NVL(M.LAST_NAME, 'NO MANAGER') AS MANSURNAME
FROM EMPLOYEES E LEFT JOIN EMPLOYEES M ON (E.MANAGER_ID = M.EMPLOYEE_ID);
--102	De Haan	100	King
--101	Kochhar	100	King
--205	Higgins	101	Kochhar
--204	Baer	101	Kochhar
--203	Mavris	101	Kochhar
--200	Whalen	101	Kochhar
--108	Greenberg	101	Kochhar
--103	Hunold	102	De Haan
--107	Lorentz	103	Hunold
--106	Pataballa	103	Hunold

--3.4 Выполнить запрос, который:
-- получает номер и название подразделений;
-- подразделения расположены в стране UNITED STATES OF AMERICA
-- в подразделениях не должно быть сотрудников.
SELECT
  D.DEPARTMENT_ID,
  D.DEPARTMENT_NAME
FROM DEPARTMENTS D JOIN LOCATIONS L ON (D.LOCATION_ID = L.LOCATION_ID)
JOIN COUNTRIES C ON (C.COUNTRY_ID = L.COUNTRY_ID)
LEFT JOIN EMPLOYEES E ON (D.DEPARTMENT_ID = E.DEPARTMENT_ID)
WHERE C.COUNTRY_NAME = 'United States of America' AND E.EMPLOYEE_ID IS NULL;
--220	NOC
--170	Manufacturing
--240	Government Sales
--260	Recruiting
--200	Operations
--210	IT Support
--160	Benefits
--120	Treasury
--270	Payroll
--130	Corporate Tax

-- 4.1 Выполнить запрос, который:
-- получает кол-во сотрудников в каждом подразделении;
-- кол-во сотрудников не должно быть меньше 2;
SELECT 
  COUNT(E.EMPLOYEE_ID) AS EMPNUMBER,
  D.DEPARTMENT_NAME AS DEPARTMENT
FROM EMPLOYEES E JOIN DEPARTMENTS D ON (E.DEPARTMENT_ID = D.DEPARTMENT_ID)
GROUP BY D.DEPARTMENT_NAME
HAVING COUNT(E.EMPLOYEE_ID) >= 2;
--2	Accounting
--3	Executive
--5	IT
--6	Purchasing
--45Shipping
--6	Finance
--34Sales
--2	Marketing

--4.2 Выполнить запрос, который:
-- получает названия должностей и среднюю зарплату по должности;
-- должность должна быть связана с управлением, т.е. содержать слово Manager;
-- средняя зарплата не должна быть менее 10 тысяч.
SELECT 
AVG(EMPLOYEES.SALARY),
JOBS.JOB_TITLE
FROM EMPLOYEES JOIN JOBS ON (EMPLOYEES.JOB_ID = JOBS.JOB_ID)
WHERE JOBS.JOB_TITLE LIKE '%Manager%'
GROUP BY JOBS.JOB_TITLE
HAVING AVG(EMPLOYEES.SALARY) > 10000;
--12000	Accounting Manager
--12000	Finance Manager
--11000	Purchasing Manager
--12200	Sales Manager
--13000	Marketing Manager

--4.3. Выполнить запрос, который:
-- получает кол-во сотрудников в каждом подразделении;
-- последней строкой ответа на запрос должно быть общее кол-во сотрудников.
SELECT 
  COUNT(E.EMPLOYEE_ID) AS EMPNUMBER,
  D.DEPARTMENT_NAME
FROM EMPLOYEES E LEFT JOIN DEPARTMENTS D ON (E.DEPARTMENT_ID = D.DEPARTMENT_ID)
GROUP BY ROLLUP(D.DEPARTMENT_NAME);
--2	Accounting
--1	Administration
--3	Executive
--6	Finance
--1	Human Resources
--5	IT
--2	Marketing
--1	Public Relations
--6	Purchasing
--34Sales
--45Shipping
--1	NULL
--107 NULL	


