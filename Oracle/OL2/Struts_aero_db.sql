drop table OrderProducts CASCADE CONSTRAINTS;
drop table SalePrice CASCADE CONSTRAINTS;
drop table PurchasePrice CASCADE CONSTRAINTS;
drop table SalesOrder CASCADE CONSTRAINTS;
drop table PurchaseOrder CASCADE CONSTRAINTS;
drop table Counterparty CASCADE CONSTRAINTS;
drop table Product CASCADE CONSTRAINTS;
drop table Manager CASCADE CONSTRAINTS;
drop table Employee CASCADE CONSTRAINTS;
drop table Department CASCADE CONSTRAINTS;
drop table Job CASCADE CONSTRAINTS;
drop table Location CASCADE CONSTRAINTS;
drop table City CASCADE CONSTRAINTS;
drop table Country CASCADE CONSTRAINTS;


CREATE TABLE Country( 
   country_id NUMERIC(3) NOT NuLL CHECK (country_id > 0), 
   country_name VARCHAR(40) NOT NULL,
   CONSTRAINT country_pk 
          PRIMARY KEY (country_id),
   CONSTRAINT country_name_uk 
          UNIQUE (country_name)
);

CREATE TABLE City( 
   city_id NUMERIC(4) NOT NuLL CHECK (city_id > 0), 
   city_name VARCHAR(40) NOT NULL,
   country_id NUMERIC(3) NOT NULL,
   CONSTRAINT city_pk 
          PRIMARY KEY (city_id),
   CONSTRAINT city_name_uk 
          UNIQUE (city_name),
	FOREIGN KEY (country_id) 
          REFERENCES Country (country_id)
);

CREATE TABLE Location( 
   location_id NUMERIC(5)PRIMARY KEY CHECK (location_id > 0), 
   street VARCHAR(40) NOT NULL,
   postal_code VARCHAR(5) NOT NULL,
   bld VARCHAR(6) NOT NULL,
   apt VARCHAR(5),
	city_id NUMERIC(4) NOT NULL,
	FOREIGN KEY (city_id) 
          REFERENCES City (city_id)
);

CREATE TABLE Job( 
   job_id NUMERIC(3) PRIMARY KEY CHECK (job_id > 0), 
   job_name VARCHAR(40) NOT NULL
);

CREATE TABLE Department( 
   department_id NUMERIC(2) PRIMARY KEY CHECK (department_id > 0), 
   department_name VARCHAR(15) NOT NULL,
   department_type VARCHAR(15) NOT NULL,
   location_id NUMERIC(5) NOT NULL,
	FOREIGN KEY (location_id) 
          REFERENCES Location (location_id)
);

CREATE TABLE Employee( 
   employee_id NUMERIC(4) PRIMARY KEY CHECK (employee_id > 0), 
   employee_first_name VARCHAR(15) NOT NULL,
   employee_last_name VARCHAR(15) NOT NULL,
   hire_date DATE NOT NULL,
   salary FLOAT NOT NULL CONSTRAINT salary_check CHECK (salary > 0),
   comm FLOAT,
   job_id NUMERIC(3) NOT NULL,
   dept_id NUMERIC(2) NOT NULL, 
	FOREIGN KEY (job_id) 
          REFERENCES Job (job_id),
	FOREIGN KEY (dept_id) 
          REFERENCES Department (department_id)
);

CREATE TABLE Manager( 
   mgr_id NUMERIC(2) PRIMARY KEY CHECK (mgr_id > 0), 
   emp_id NUMERIC(4) NOT NULL,
	FOREIGN KEY (emp_id) 
          REFERENCES Employee (employee_id),
	CONSTRAINT emp_id_uk 
          UNIQUE (emp_id)
);

CREATE TABLE Product( 
   product_id NUMERIC(4) PRIMARY KEY CHECK (product_id > 0), 
   product_name VARCHAR(20) NOT NULL,
   product_description VARCHAR(50),
   CONSTRAINT product_name_uk 
          UNIQUE (product_name)   
);

CREATE TABLE Counterparty( 
   counterparty_id NUMERIC(3) PRIMARY KEY CHECK (counterparty_id > 0), 
   counterparty_name VARCHAR(20) NOT NULL,
   counterparty_phone VARCHAR(15),
   location_id NUMERIC(5) NOT NULL,
	FOREIGN KEY (location_id) 
          REFERENCES Location (location_id)
);

CREATE TABLE PurchaseOrder( 
   porder_id NUMERIC(8) PRIMARY KEY CHECK (porder_id > 0), 
   porder_date DATE NOT NULL,
   countp_id NUMERIC(3) NOT NULL,
   emp_id NUMERIC(4) NOT NULL,
	FOREIGN KEY (countp_id) 
          REFERENCES Counterparty (counterparty_id),
	FOREIGN KEY (emp_id) 
          REFERENCES Employee (employee_id)
);

CREATE TABLE SalesOrder( 
   sorder_id NUMERIC(8) PRIMARY KEY CHECK (sorder_id > 0), 
   sorder_date DATE NOT NULL,
   countp_id NUMERIC(3) NOT NULL,
   emp_id NUMERIC(4) NOT NULL,
	FOREIGN KEY (countp_id) 
          REFERENCES Counterparty (counterparty_id),
	FOREIGN KEY (emp_id) 
          REFERENCES Employee (employee_id)
);

CREATE TABLE PurchasePrice( 
   purch_price_date DATE NOT NULL, 
   prod_id NUMERIC(4) NOT NULL,
   countp_id NUMERIC(3) NOT NULL,
   price FLOAT NOT NULL,
   CONSTRAINT purch_price_id 
		PRIMARY KEY (purch_price_date, prod_id, countp_id),
	FOREIGN KEY (countp_id) 
          REFERENCES Counterparty (counterparty_id),
	FOREIGN KEY (prod_id) 
          REFERENCES Product (product_id)
);
CREATE TABLE SalePrice( 
   sale_price_date DATE NOT NULL, 
   prod_id NUMERIC(4) NOT NULL,
   countp_id NUMERIC(3) NOT NULL,
   price FLOAT NOT NULL,
   CONSTRAINT sale_price_id 
		PRIMARY KEY (sale_price_date, prod_id, countp_id),
	FOREIGN KEY (countp_id) 
          REFERENCES Counterparty (counterparty_id),
	FOREIGN KEY (prod_id) 
          REFERENCES Product (product_id)
);
CREATE TABLE OrderProducts( 
   order_prod_id NUMERIC(10) PRIMARY KEY CHECK (order_prod_id > 0), 
   order_id NUMERIC(8) NOT NULL,
   prod_id NUMERIC(4),
   quantity FLOAT NOT NULL,
   price FLOAT NOT NULL,
	FOREIGN KEY (order_id) 
          REFERENCES PurchaseOrder (porder_id),
FOREIGN KEY (order_id) 
            REFERENCES SalesOrder (sorder_id),
	FOREIGN KEY (prod_id) 
          REFERENCES Product (product_id)
);
commit;

