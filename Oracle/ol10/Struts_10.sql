--1.1. Для каждого сотрудника сгенерировать новый электронный адрес по шаблону A@B.C.D,
--где A – значение колонки email, B,C – части значения колонки job_id, находящиеся перед и после
--символа _, D – значение колонки country_id. При создании элементов B и C использовать
--функцию regexp_substr.
MERGE INTO EMPLOYEES e
USING (
  SELECT e.EMPLOYEE_ID,  e.email,  e.email||'@'||regexp_substr(j.JOB_ID,'^[A-Z]+', 1, 1)
        ||'.'||regexp_substr(j.JOB_ID,'[A-Z]+$', 1, 1)||'.'||l.COUNTRY_ID AS EMAILNEW
  FROM EMPLOYEES e JOIN JOBS j ON (e.JOB_ID = j.JOB_ID)
  join DEPARTMENTS d  ON (e.DEPARTMENT_ID = d.DEPARTMENT_ID)
  join LOCATIONS l ON (d.LOCATION_ID = l.LOCATION_ID)
) em ON (e.EMPLOYEE_ID = em.EMPLOYEE_ID)
WHEN MATCHED THEN
UPDATE SET e.email = em.EMAILNEW
--1.2. Для строковых значений колонки street_address, содержащих цифровой код в начале
--строки, выполнить перенос кода в конец строки, поставив перед ним запятую. Если в процессе
--обновления будет выдана ошибка о превышении длины обновляемой строки, выполнить
--расширение длины колонки street_address ( операция alter table … modify )

SELECT regexp_replace(STREET_ADDRESS, '(^\d+\s)(.+)', '\2,\1'),STREET_ADDRESS  FROM LOCATIONS

--1.3. Для строковых значений колонки street_address, содержащих в конце цифровой код,
--который не отделен от предыдущей подстроки запятой, включить эту запятую.
SELECT regexp_replace(STREET_ADDRESS, '(\D+)(\s\d+$)', '\1,\2'),STREET_ADDRESS  FROM LOCATIONS

--1.4. Для строковых значений колонки phone_number формат XXX.XXX.XXXX преобразовать
--к формату (XXX) XXX-XX-XX
SELECT regexp_replace(PHONE_NUMBER, '(\d{3}).(\d{3}).(\d{2})(\d{2})', '(\1) \2-\3-\4'),PHONE_NUMBER  FROM EMPLOYEES

--1.5. В составных названиях подразделений, содержащих два слова, поменять порядок слов.
--Например: Government Sales преобразуется к Sales of Government.
SELECT regexp_replace(DEPARTMENT_NAME, '(^\S+)\s(\S+$)', '\2 of \1'), DEPARTMENT_NAME FROM DEPARTMENTS

--2.1. Выбрать подразделения, в полном названии которых присутствуют повторяющиеся
--подряд буквы.
SELECT * FROM DEPARTMENTS WHERE regexp_like(DEPARTMENT_NAME, '([a-z])\1', 'i')

--2.2. Для каждого сотрудника выбрать полное название занимаемой им должности и страны
--расположения на основе анализа электронного адреса, сформированного по шаблону A@B.C.D,
--где A – значение колонки email, B,C – части значения колонки job_id, находящиеся перед и после
--символа _, D – значение колонки country_id.
SELECT regexp_replace(EMAIL, '([^@]+@)(\w+).(\w+).(\w+)', '\2_\3') AS job, regexp_substr(EMAIL, '.(\w)$') AS Country FROM EMPLOYEES;

--2.3. Для составных названий подразделений получить множество подстрок. Формат строки
--выборки: Полное название, первая часть, вторая часть.
SELECT DEPARTMENT_NAME, regexp_substr(DEPARTMENT_NAME, '(^\w+)\s'), regexp_substr(DEPARTMENT_NAME,'(\w+$)')
FROM DEPARTMENTS
WHERE regexp_instr(DEPARTMENT_NAME, ' ')>0;

--2.4. Выбрать список близких по смыслу названий должностей, когда совпадают некоторые
--подстроки из строк названий. Формат выборки: Должность1, Должность 2. Примеры пар близких
--по смыслу названий должностей: Finance Manager и Accounting Manager, Sales Manager и Sales
--Representative и т.д.
SELECT j1.JOB_TITLE, j2.JOB_TITLE
FROM JOBS j1, JOBS j2
WHERE (regexp_instr(j2.JOB_TITLE, regexp_substr(j1.JOB_TITLE, '(^\w+)\s')) > 0
OR regexp_instr(j2.JOB_TITLE, regexp_substr(j1.JOB_TITLE, '(\s\w+)$')) > 0)
AND regexp_instr(j2.JOB_TITLE, regexp_substr(j1.JOB_TITLE, '.*')) = 0;

--3.1. Выбрать сотрудников с группировкой по странам и их разделением на три группы в
--каждой стране.
 SELECT NTILE(3) OVER (PARTITION BY l.COUNTRY_ID ORDER BY l.COUNTRY_ID) AS ENTILE, l.COUNTRY_ID, e.EMPLOYEE_ID
  FROM EMPLOYEES e join DEPARTMENTS d  ON (e.DEPARTMENT_ID = d.DEPARTMENT_ID)
  join LOCATIONS l ON (d.LOCATION_ID = l.LOCATION_ID)

--3.2. Выбрать максимальную зарплату сотрудников с группировкой по городам, в которых
--расположены подразделения сотрудников, так чтобы в каждой строке выдавалась максимальная
--зарплата всех сотрудников вплоть до указанного.
SELECT l.LOCATION_ID, e.EMPLOYEE_ID, e.SALARY, max(e.SALARY)
OVER (PARTITION BY l.LOCATION_ID) max_sal
FROM EMPLOYEES e JOIN DEPARTMENTS d ON (e.DEPARTMENT_ID = d.DEPARTMENT_ID)
JOIN LOCATIONS l ON (d.LOCATION_ID = l.LOCATION_ID);

--3.3. Для каждой страны выбрать двух самых высокооплачиваемых сотрудников.

SELECT c_id,f_n, s from
(
    SELECT l.COUNTRY_ID c_id, e.FIRST_NAME f_n, e.SALARY s, ROW_NUMBER()
                                OVER(PARTITION BY l.COUNTRY_ID order by SALARY desc) R
    FROM EMPLOYEES e JOIN DEPARTMENTS d ON (e.DEPARTMENT_ID = d.DEPARTMENT_ID)
                    JOIN LOCATIONS l ON (d.LOCATION_ID = l.LOCATION_ID)
    )
where R <= 2;

--3.4. Для каждой страны выбрать высокооплачиваемых сотрудников, уровень зарплат которых
--находится на втором месте.
SELECT c_id,f_n, s from
  (
    SELECT l.COUNTRY_ID c_id, e.FIRST_NAME f_n, e.SALARY s, ROW_NUMBER()
    OVER(PARTITION BY l.COUNTRY_ID order by SALARY desc) R
    FROM EMPLOYEES e JOIN DEPARTMENTS d ON (e.DEPARTMENT_ID = d.DEPARTMENT_ID)
      JOIN LOCATIONS l ON (d.LOCATION_ID = l.LOCATION_ID)
  )
where R = 2;
--3.5. Выбрать сотрудников, у которых ранг (уровень) зарплат <= 0.25 в группах сотрудников,
--работающих в подразделениях из одной страны.
WITH emp as (
SELECT l.COUNTRY_ID c_id, e.FIRST_NAME f_n, e.SALARY s, CUME_DIST()
OVER ( PARTITION BY l.COUNTRY_ID ORDER BY SALARY DESC ) cume_dist
FROM EMPLOYEES e JOIN DEPARTMENTS d ON (e.DEPARTMENT_ID = d.DEPARTMENT_ID)
JOIN LOCATIONS l ON (d.LOCATION_ID = l.LOCATION_ID)
)
SELECT * FROM emp WHERE cume_dist <= 0.25;
--3.6. Показать минимальную зарплату по текущему сотруднику и предыдущим 2-м
--сотрудникам, сгруппированным в группе по подразделениями и отсортированным в порядке
--убывания зарплаты.
SELECT DEPARTMENT_ID,FIRST_NAME,SALARY,
  min(SALARY) OVER(PARTITION BY DEPARTMENT_ID ORDER BY SALARY DESC
  ROWS BETWEEN 2 PRECEDING AND CURRENT ROW ) AS MIN_3
FROM EMPLOYEES;

--3.7. Выбрать среднюю зарплату сотрудников (с учетом премиальных) за третий месяц всех
--лет и определить ее изменение в процентах по отношению к предыдущему и следующему году.
WITH
    m_y_sal AS
  (	SELECT 	EXTRACT(Month FROM HIRE_DATE) m,
            EXTRACT (Year FROM HIRE_DATE) as y,
            SALARY + NVL(COMMISSION_PCT, 0)*SALARY AS SALARY
     FROM EMPLOYEES
  ),
    m_y_sal_sum AS
  (	SELECT m, y, SUM(SALARY) SALARY
     FROM m_y_sal
     GROUP BY m, y
  ),
    month_list AS (
      SELECT rownum as m FROM dual CONNECT BY level <= 12),
    year_list AS
  ( SELECT distinct extract (year from HIRE_DATE) y
    FROM EMPLOYEES
  ),
    month_year_list AS
  ( SELECT y,m
    FROM month_list, year_list
  ),
    m_y_sal_sum_add AS
  ( SELECT l.y,l.m,NVL(s.SALARY,0) SALARY
    from month_year_list l left	join m_y_sal_sum s on (l.m = s.m and l.y = s.y)
  ),
    m_y_sal_sum2 AS
  ( SELECT m, y, SUM(SALARY) OVER (ORDER BY y, m) Sum_Sal
    FROM m_y_sal_sum_add
  ),
    m_y_sal_sum_l AS
  ( SELECT m, y, Sum_Sal,
      LAG(Sum_Sal,1,Sum_Sal) OVER	(ORDER BY y, m)	Prior_Year,
      LEAD(Sum_Sal, 1, Sum_Sal) OVER (ORDER BY y, m) Next_Year
    FROM m_y_sal_sum2 where m = 3
  )
SELECT 	m,y,sum_sal,prior_year,round(100*prior_year/sum_sal) "p_y_%",
  next_year, round(100*next_year/sum_sal) "n_y_%"
from m_y_sal_sum_l;

--3.8. Выбрать текущие затраты компании на зарплату сотрудникам с квартальной разбивкой за
--все годы работы компании.
WITH
    m_y_sal AS
  (	SELECT 	EXTRACT(Month FROM HIRE_DATE) m,
             EXTRACT (Year FROM HIRE_DATE) as y,
             SALARY + NVL(COMMISSION_PCT, 0)*SALARY AS SALARY
     FROM EMPLOYEES
  ),
    m_y_sal_sum AS
  (	SELECT m, y, SUM(SALARY) SALARY
     FROM m_y_sal
     GROUP BY m, y
  ),
    q_list AS (
      SELECT rownum as q FROM dual CONNECT BY level <= 4),
    year_list AS
  ( SELECT distinct extract (year from HIRE_DATE) y
    FROM EMPLOYEES
  ),
    month_year_list AS
  ( SELECT y,q
    FROM q_list, year_list
  ),
    m_y_sal_sum_add AS
  ( SELECT l.y,l.q,NVL(s.SALARY,0) SALARY
    from month_year_list l left	join m_y_sal_sum s on (l.q*3 >= s.m and (l.q-1)*3 < s.m and l.y = s.y)
  )
SELECT q, y, SUM(SALARY) OVER (ORDER BY y, q) Sum_Sal
FROM m_y_sal_sum_add
