--1.1. Для каждого сотрудника сгенерировать новый электронный адрес по шаблону A@B.C.D,
--где A – значение колонки email, B,C – части значения колонки job_id, находящиеся перед и после
--символа _, D – значение колонки country_id. При создании элементов B и C использовать
--функцию regexp_substr.
--MERGE INTO EMPLOYEES e
--USING
--(
  SELECT e.EMPLOYEE_ID,  e.email,  e.email||'@'||regexp_substr(j.JOB_ID,'^[A-Z]+', 1, 1)
        ||'.'||regexp_substr(j.JOB_ID,'[A-Z]+$', 1, 1)||'.'||l.COUNTRY_ID AS EMAILNEW
  FROM EMPLOYEES e JOIN JOBS j ON (e.JOB_ID = j.JOB_ID)
  join DEPARTMENTS d  ON (e.DEPARTMENT_ID = d.DEPARTMENT_ID)
  join LOCATIONS l ON (d.LOCATION_ID = l.LOCATION_ID)
--) em ON (e.EMPLOYEE_ID = em.EMPLOYEE_ID)
--WHEN MATCHED THEN
--UPDATE SET EMAIL = EMAILNEW;
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


--3.1. Выбрать сотрудников с группировкой по странам и их разделением на три группы в
--каждой стране.
 SELECT NTILE(3) OVER (PARTITION BY l.COUNTRY_ID ORDER BY l.COUNTRY_ID) AS ENTILE, l.COUNTRY_ID, e.EMPLOYEE_ID
  FROM EMPLOYEES e join DEPARTMENTS d  ON (e.DEPARTMENT_ID = d.DEPARTMENT_ID)
  join LOCATIONS l ON (d.LOCATION_ID = l.LOCATION_ID)
