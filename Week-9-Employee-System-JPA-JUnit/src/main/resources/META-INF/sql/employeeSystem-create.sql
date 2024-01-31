-- this version of EmployeeSystem DDL does not have FK constraints
-- so that during testing one can easily drop/truncate tables without
-- Referential Integrity violations
-- In production, the company DBA will probably want FK constraints
DROP TABLE EMPLOYEE IF EXISTS;

CREATE TABLE EMPLOYEE (
    EMP_ID INTEGER IDENTITY NOT NULL,
    EMAIL VARCHAR,
    FNAME VARCHAR,
    LNAME VARCHAR,
    SALARY DOUBLE,
    TITLE VARCHAR,
    ADDR_ID INTEGER,
    CREATED_DATE TIMESTAMP,
    UPDATED_DATE TIMESTAMP,
    VERSION INTEGER,
    PRIMARY KEY (EMP_ID)
);

DROP TABLE EMPLOYEE_TASKS IF EXISTS;

CREATE TABLE EMPLOYEE_TASKS (
    OWNER_EMP_ID INTEGER,
    TASK_DESCRIPTION VARCHAR,
    TASK_DONE BOOLEAN,
    TASK_END TIMESTAMP,
    TASK_START TIMESTAMP
);

DROP TABLE ADDRESS IF EXISTS;

CREATE TABLE ADDRESS (
    ADDR_ID INTEGER IDENTITY NOT NULL,
    CITY VARCHAR,
    COUNTRY VARCHAR,
    POSTAL VARCHAR,
    STATE VARCHAR,
    STREET VARCHAR,
    CREATED_DATE TIMESTAMP,
    UPDATED_DATE TIMESTAMP,
    VERSION INTEGER,
    PRIMARY KEY (ADDR_ID)
);

DROP TABLE PHONE IF EXISTS;

CREATE TABLE PHONE (
    PHONE_ID INTEGER IDENTITY NOT NULL,
    PHONE_TYPE VARCHAR(1),
    AREACODE VARCHAR,
    PHONENUMBER VARCHAR,
    MAP_COORDS VARCHAR,
    PROVIDER VARCHAR,
    DEPARTMENT VARCHAR,
    OWNING_EMP_ID INTEGER,
    CREATED_DATE TIMESTAMP,
    UPDATED_DATE TIMESTAMP,
    VERSION INTEGER,
    PRIMARY KEY (PHONE_ID)
);

DROP TABLE EMP_PROJ IF EXISTS;
DROP TABLE PROJECT IF EXISTS;

CREATE TABLE PROJECT (
    PROJ_ID INTEGER IDENTITY NOT NULL,
    DESCRIPTION VARCHAR,
    NAME VARCHAR,
    CREATED_DATE TIMESTAMP,
    UPDATED_DATE TIMESTAMP,
    VERSION INTEGER,
    PRIMARY KEY (PROJ_ID)
);

CREATE TABLE EMP_PROJ (
    EMP_ID INTEGER NOT NULL,
    PROJ_ID INTEGER NOT NULL
);