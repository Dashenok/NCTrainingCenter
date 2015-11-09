--1.1. Выполнить запрос, который:
-- получает названия должностей;
-- на указанных должностях должны работать сотрудники.
SELECT j.JOB_TITLE
	FROM jobs j
	WHERE EXISTS (
			SELECT e.JOB_ID 
			FROM EMPLOYEES e 
			WHERE e.JOB_ID = j.JOB_ID);
      
-- JOB_TITLE                         
-----------------------------------
--President                          
--Administration Vice President      
--Programmer                         
--Finance Manager                    
--Accountant                         
--Purchasing Manager                 
--Purchasing Clerk                   
--Stock Manager                      
--Stock Clerk                        
--Sales Manager                      
--Sales Representative 


--1.2. Выполнить запрос, который:
-- получает фамилию сотрудников и их зарплату;
-- размер зарплаты сотрудников должен быть больше средней зарплаты сотрудников,
--работающих в Европе.
SELECT e.LAST_NAME, e.SALARY
FROM employees e
WHERE e.SALARY > (
SELECT AVG(e.salary) "Average"
FROM employees e
WHERE e.DEPARTMENT_ID IN (SELECT d.DEPARTMENT_ID FROM DEPARTMENTS d
WHERE d.LOCATION_ID IN (SELECT l.LOCATION_ID FROM LOCATIONS l
WHERE l.COUNTRY_ID IN (SELECT c.COUNTRY_ID FROM COUNTRIES c
WHERE c.REGION_ID = (SELECT r.REGION_ID FROM REGIONS r
WHERE r.REGION_NAME = 'Europe'))))); 

--LAST_NAME                     SALARY
------------------------- ----------
--King                           24000
--Kochhar                        17000
--De Haan                        17000
--Hunold                          9000
--Greenberg                      12000
--Faviet                          9000
--Raphaely                       11000
--Russell                        14000
--Partners                       13500
--Errazuriz                      12000
--Cambrault                      11000

--1.3. Выполнить запрос, который:
-- получает название подразделений;
-- в указанных подразделениях средняя зарплата сотрудников должна быть больше средней
--зарплаты сотрудников в других подразделениях.
SELECT d.DEPARTMENT_NAME
FROM employees e, DEPARTMENTS d
WHERE e.DEPARTMENT_ID = d.DEPARTMENT_ID
GROUP BY d.DEPARTMENT_NAME
HAVING AVG(e.salary) = (SELECT MAX(AVG(salary)) FROM employees GROUP BY DEPARTMENT_ID);
--DEPARTMENT_NAME              
------------------------------
--Executive   

--1.4. Выполнить запрос, который получает название страны с минимальным количеством
--сотрудников по сравнению с другими странами.
SELECT Cout.Country_Name
FROM Employees Emp JOIN Departments Dept ON (Emp.Department_ID = Dept.Department_ID)
      JOIN Locations Loc ON (Dept.Location_ID = Loc.Location_ID)
      JOIN Countries Cout ON (Cout.Country_ID = Loc.Country_ID)
GROUP BY Cout.Country_Name
HAVING COUNT(Emp.Employee_ID) = (SELECT MIN(COUNT(Emp.Employee_ID))
FROM Employees Emp JOIN Departments Dept ON (Emp.Department_ID = Dept.Department_ID)
      JOIN Locations Loc ON (Dept.Location_ID = Loc.Location_ID)
GROUP BY Loc.Country_ID);

--COUNTRY_NAME                           
----------------------------------------
--Germany  

-- 1.5. Выполнить запрос, который получает фамилию сотрудника с самым большим доходом
-- за все время работы в организации.
SELECT Emp.Last_Name
FROM Employees Emp
WHERE (Sysdate - Emp.Hire_Date)/30*Emp.Salary = (SELECT MAX((Sysdate - Emp.Hire_Date)/30*Emp.Salary)
                                                    FROM Employees Emp);
--LAST_NAME               
-------------------------
--King 

-- 1.6. Выполнить запрос, который получает список стран и подразделений, в которых не
-- работают сотрудники.

SELECT * FROM COUNTRIES C
WHERE NOT EXISTS ( SELECT l.COUNTRY_ID
 			    FROM LOCATIONS l, DEPARTMENTS d, EMPLOYEES e
 			    where l.COUNTRY_ID = C.COUNTRY_ID
          AND d.LOCATION_ID = l.LOCATION_ID
          AND e.DEPARTMENT_ID = d.DEPARTMENT_ID);
          
          
-- 1.7. Выполнить запрос, который получает:
-- название подразделения
-- сумму окладов сотрудников подразделения;
-- процент, который сумма окладов сотрудников подразделения составляет от суммы
-- окладов всех сотрудников компании;
-- если в подразделении нет сотрудников, то считать, что сумма их окладов равна нулю.

WITH 
all_salary AS
      (SELECT Sum(EmpAll.Salary) Salary
       FROM Employees EmpAll)
SELECT Dept.Department_Name Department, SUM(NVL(Emp.Salary, 0)) Sal, ROUND(SUM(NVL(Emp.Salary, 0))/all_salary.Salary, 4)*100 PRS
FROM Departments Dept LEFT JOIN Employees Emp ON (Emp.Department_ID = Dept.Department_ID), all_salary
GROUP BY Dept.Department_Name, all_salary.Salary;

--Sales	          304500	44.04
--Marketing	      19000	   2.75
--Administration	4400	   0.64
--Human Resources	6500	  0.94
--Executive	      58000	  8.39
--Purchasing	    24900	  3.6
--Shipping	      156400	22.62
--IT	            28800	4.17
--Finance	        51600	7.46
--Public Relations	10000	1.45
--Accounting	    20300	2.94

-- 1.8. Выполнить запрос, который:
-- получает фамилии сотрудников;
-- зарплата сотрудников должна быть больше средней зарплаты сотрудников, работающих
-- в других подразделениях.
WITH
dept_avg_sal AS(
SELECT DISTINCT Emp.Department_ID Dep, AVG(EmpDept.Sal) AvgSal
FROM Employees Emp, (SELECT Emp1.Department_ID Dept, AVG(Emp1.Salary) Sal 
                    FROM Employees Emp1
                    GROUP BY Emp1.Department_ID) EmpDept
WHERE Emp.Department_ID != EmpDept.Dept
GROUP BY Emp.Department_ID
)
SELECT Employees.Last_Name
FROM Employees, dept_avg_sal
WHERE Employees.Department_ID = dept_avg_sal.Dep
AND Employees.Salary > dept_avg_sal.AvgSal;

--LAST_NAME               
-------------------------
--King                     
--Kochhar                  
--De Haan                  
--Hunold                   
--Greenberg                
--Faviet                   
--Raphaely                 
-- 31 rows selected        

-- 1.9. Выполнить запрос, который:
--- получает названия подразделений;
--- в указанных подразделениях средняя зарплата сотрудников должна быть больше средней
--зарплаты сотрудников, работающих в других подразделениях.
WITH
dept_avg_sal AS(
SELECT DISTINCT Emp.Department_ID Dep, AVG(EmpDept.Sal) AvgSal
FROM Employees Emp, (SELECT Emp1.Department_ID Dept, AVG(Emp1.Salary) Sal 
                    FROM Employees Emp1
                    GROUP BY Emp1.Department_ID) EmpDept
WHERE Emp.Department_ID != EmpDept.Dept
GROUP BY Emp.Department_ID
)
SELECT Departments.Department_Name
FROM Employees, dept_avg_sal, Departments
WHERE Employees.Department_ID = dept_avg_sal.Dep
AND Employees.Department_ID  = Departments.Department_ID
GROUP BY Departments.Department_Name, dept_avg_sal.AvgSal
HAVING AVG(Employees.Salary) > dept_avg_sal.AvgSal;

--Sales
--Marketing
--Public Relations
--Accounting
--Executive
--Finance

--2.1. Используя одну INSERT-команду, зарегистрировать нового сотрудника с Вашей
--фамилией и предпочитаемой Вами зарплатой, который будет работать:
-- на должности Software Developer;
-- в стране Ukraine;
-- в городе Odessa;
-- в подразделении NC Office.
--Остальные необходимые для внесения данные выбрать самостоятельно.

CREATE SEQUENCE LOCATIONSS;
CREATE SEQUENCE DEPARTMENTSS;
CREATE SEQUENCE EMPLOYEESS;
INSERT ALL 
INTO COUNTRIES VALUES ('UA', 'Ukraine', 1)
INTO LOCATIONS VALUES (LOCATIONSS.NEXTVAL, 'Mechikova', '09539', 'Odessa', '222', 'UA')
INTO DEPARTMENTS VALUES (DEPARTMENTSS.NEXTVAL, 'NC Office', null, LOCATIONSS.CURRVAL)
INTO JOBS VALUES ('SWD', 'Software Developer', 300,3000)
INTO EMPLOYEES VALUES (EMPLOYEESS.NEXTVAL, 'Daria', 'Struts', 'ds@gmail.com', '284920', sysdate, 'SWD', 100000, null, null,DEPARTMENTSS.CURRVAL) 
SELECT * FROM DUAL;
--5 rows inserted.


--2.2. Ликвидировать страны, в которых не работают сотрудники.
DELETE FROM LOCATIONS l
WHERE NOT EXISTS ( SELECT d.DEPARTMENT_ID 
 			    FROM DEPARTMENTS d, EMPLOYEES e
 			    where d.LOCATION_ID = l.LOCATION_ID
          AND e.DEPARTMENT_ID = d.DEPARTMENT_ID);
--16 rows deleted.
DELETE FROM COUNTRIES C
WHERE NOT EXISTS ( SELECT l.COUNTRY_ID
 			    FROM LOCATIONS l, DEPARTMENTS d, EMPLOYEES e
 			    where l.COUNTRY_ID = C.COUNTRY_ID
          AND d.LOCATION_ID = l.LOCATION_ID
          AND e.DEPARTMENT_ID = d.DEPARTMENT_ID);
--21 rows deleted.

--2.3. Сотруднику, который дольше всех работает в подразделении с самой низкой средней
--зарплатой, увеличить комиссионные на 10%                                                              
UPDATE Employees SET Employees.Commission_PCT = NVL(Employees.Commission_PCT, 0) + 0.1
WHERE Employees.Hire_Date = (SELECT MIN(EmpHD.Hire_Date)
                      FROM Employees EmpHD
                      WHERE EmpHD.Department_ID = (SELECT  EmpDept.Department_ID
                            FROM  Employees EmpDept
                            GROUP BY EmpDept.Department_ID
                            HAVING AVG(EmpDept.Salary) = (SELECT MIN(AVG(Employees.Salary))
                                                          FROM Employees
                                                          GROUP BY Employees.Department_ID)))
 AND Employees.Department_ID =  (SELECT  EmpDept.Department_ID
                            FROM  Employees EmpDept
                            GROUP BY EmpDept.Department_ID
                            HAVING AVG(EmpDept.Salary) = (SELECT MIN(AVG(Employees.Salary))
                                                          FROM Employees
                                                          GROUP BY Employees.Department_ID));
--1 row updated.

-- 2.4. Перевести всех сотрудников из подразделения с самым низким количеством
-- сотрудников в подразделение с самой высокой средней зарплатой.
UPDATE Employees SET Department_ID = (SELECT Department_ID
                                      FROM Employees
                                      GROUP BY Department_ID
                                      HAVING AVG(Salary) = (SELECT MAX(AVG(Salary)) FROM Employees GROUP BY Department_ID))
WHERE Department_ID IN (SELECT Department_ID
                        FROM Employees
                        GROUP BY Department_ID
                        HAVING COUNT(Employee_ID) = (SELECT MIN(COUNT(Employee_ID)) FROM Employees GROUP BY Department_ID));
                        
-- 3 rows updated.
--3.1. Выполнить запрос на получение названий подразделений, фамилий с учетом иерархии
-- подчинения, начиная с руководителей.
SELECT e.LAST_NAME, d.DEPARTMENT_NAME
FROM EMPLOYEES e, DEPARTMENTS d
WHERE e.DEPARTMENT_ID = d.DEPARTMENT_ID
START WITH e.MANAGER_ID is null
CONNECT BY prior e.EMPLOYEE_ID = e.MANAGER_ID 
ORDER BY level;

--LAST_NAME                 MANAGER_ID DEPARTMENT_NAME                     LEVEL
------------------------- ---------- ------------------------------ ----------
--King                                 Executive                               1
--Struts                               NC Office                               1
--Kochhar                          100 Executive                               2
--De Haan                          100 Executive                               2
--Raphaely                         100 Purchasing                              2
--Weiss                            100 Shipping                                2
--Fripp                            100 Shipping                                2
--Kaufling                         100 Shipping                                2
--Vollman                          100 Shipping                                2
--Mourgos                          100 Shipping                                2
--Russell                          100 Sales                                   2
  
-- 3.2. Выполнить запрос на получение названий подразделений, фамилий с учетом иерархии
-- подчинения, начиная с подчиненных.
SELECT e.LAST_NAME, d.DEPARTMENT_NAME, level
FROM EMPLOYEES e, DEPARTMENTS d
WHERE e.DEPARTMENT_ID = d.DEPARTMENT_ID
START WITH e.LAST_NAME = 'Lorentz'
CONNECT BY prior e.MANAGER_ID = e.Employee_ID
ORDER BY level DESC;
--LAST_NAME                 DEPARTMENT_NAME                     LEVEL
------------------------- ------------------------------ ----------
--King                      Executive                               4
--De Haan                   Executive                               3
--Hunold                    IT                                      2
--Lorentz                   IT                                      1

-- 3.3. Выполнить запрос на получение фамилии сотрудника, номера и названия
-- подразделения, где он работает, номер узла иерархии и имен всех его менеджеров через /.
-- Внутри одного уровня иерархии сотрудники должны быть отсортированы по названиям
-- подразделения.

SELECT Emp.Last_Name Empl, 
      Dept.Department_ID DeptNo, 
      Dept.Department_Name DeptName, 
      Level,  
      SYS_CONNECT_BY_PATH(Emp.MANAGER_ID, '/') as Mng
FROM Employees Emp, Departments Dept
WHERE Emp.Department_ID = Dept.Department_ID
START WITH Emp.MANAGER_ID is null
CONNECT BY prior Emp.EMPLOYEE_ID = Emp.MANAGER_ID 
ORDER BY level, Dept.Department_ID;

-- 3.4. Выполнить запрос на получение:
-- календаря на предыдущий, текущий и следующий месяц текущего года
-- формат вывода: номер дня в месяце (две цифры), полное название месяца,
-- по каждому месяцу количество возвращаемых строк должно точно соответствовать
-- количеству дней в месяце.
SELECT TO_CHAR(ADD_MONTHS(TRUNC(sysdate, 'month'), -1)+rownum-1
             , 'DD Month'
             , 'NLS_DATE_LANGUAGE=AMERICAN') AS d
FROM dual
CONNECT BY ROWNUM <= TO_CHAR(LAST_DAY(ADD_MONTHS(TRUNC(sysdate, 'month'), 1)) - ADD_MONTHS(TRUNC(sysdate, 'month'), -1)+1)
;