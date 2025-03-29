-- SCHEMA admin
CREATE SCHEMA IF NOT EXISTS admin;
CREATE TABLE IF NOT EXISTS admin.tenant
(
    id      uuid NOT NULL,
    name    character varying(100) NOT NULL,
    schema  character varying(50) NOT NULL,
    status  character varying(8) NOT NULL,
    CONSTRAINT pk_tenant PRIMARY KEY (id),
    CONSTRAINT uk_tenant_name UNIQUE (name),
    CONSTRAINT uk_tenant_schema UNIQUE (schema)
    );

CREATE TABLE IF NOT EXISTS admin."user"
(
    id        bigint NOT NULL,
    username  character varying(100) NOT NULL,
    status    character varying(8) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uk_user_username UNIQUE (username)
    );

CREATE TABLE IF NOT EXISTS admin.user_tenant
(
    user_id    bigint NOT NULL,
    tenant_id  uuid NOT NULL,
    CONSTRAINT pk_user_tenant PRIMARY KEY (user_id, tenant_id),
    CONSTRAINT fk_user_tenant_user FOREIGN KEY (user_id) REFERENCES admin."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_user_tenant_tenant FOREIGN KEY (tenant_id) REFERENCES admin.tenant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

CREATE TABLE IF NOT EXISTS admin.user_role
(
    user_id    bigint NOT NULL,
    role_name  character varying(20) NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_name),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES admin."user" (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

-- SCHEMA ey
CREATE SCHEMA IF NOT EXISTS ey;
CREATE TABLE IF NOT EXISTS ey.customer
(
    id          uuid NOT NULL,
    email       character varying(100) NOT NULL,
    first_name  character varying(50) NOT NULL,
    last_name   character varying(50) NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id),
    CONSTRAINT uk_customer_email UNIQUE (email)
    );

CREATE SCHEMA IF NOT EXISTS ems;
CREATE TABLE IF NOT EXISTS ems.customer
(
    id          uuid NOT NULL,
    email       character varying(100) NOT NULL,
    first_name  character varying(50) NOT NULL,
    last_name   character varying(50) NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id),
    CONSTRAINT uk_customer_email UNIQUE (email)
    );

CREATE SCHEMA IF NOT EXISTS euro;
CREATE TABLE IF NOT EXISTS euro.customer
(
    id          uuid NOT NULL,
    email       character varying(100) NOT NULL,
    first_name  character varying(50) NOT NULL,
    last_name   character varying(50) NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id),
    CONSTRAINT uk_customer_email UNIQUE (email)
    );

-- INSERTS SCHEMA ADMIN
DELETE FROM admin.user_role;
DELETE FROM admin.user_tenant;
DELETE FROM admin."user";
DELETE FROM admin.tenant;

INSERT INTO admin.tenant (id, name, schema, status) VALUES ('0195e34f-35d1-76f4-b997-12edc288d008', 'EY', 'ey', 'ACTIVE');
INSERT INTO admin.tenant (id, name, schema, status) VALUES ('0195e34f-35d1-7446-b135-a64727046173', 'EMS', 'ems', 'ACTIVE');
INSERT INTO admin.tenant (id, name, schema, status) VALUES ('0195e34f-35d1-7754-9c78-b5d448cd8d6a', 'EURO', 'euro', 'INACTIVE');

INSERT INTO admin."user" (id, username, status) VALUES (1, 'andersonbritogarcia@gmail.com', 'ACTIVE');

INSERT INTO admin.user_tenant (user_id, tenant_id) VALUES (1, '0195e34f-35d1-76f4-b997-12edc288d008');
INSERT INTO admin.user_tenant (user_id, tenant_id) VALUES (1, '0195e34f-35d1-7446-b135-a64727046173');
INSERT INTO admin.user_tenant (user_id, tenant_id) VALUES (1, '0195e34f-35d1-7754-9c78-b5d448cd8d6a');

INSERT INTO admin.user_role (user_id, role_name) VALUES (1, 'ADMIN');
INSERT INTO admin.user_role (user_id, role_name) VALUES (1, 'APPROVER');
INSERT INTO admin.user_role (user_id, role_name) VALUES (1, 'PHARMACIST');

-- INSERTS SCHEMAS INDIVIDUAIS
DELETE FROM ey.customer;

INSERT INTO ey.customer(id, email, first_name, last_name) VALUES ('0195e34f-35d1-7fe4-aadb-c7be4e741118', 'anderson@tech.com', 'Anderson', 'Garcia');
INSERT INTO ey.customer(id, email, first_name, last_name) VALUES ('0195e34f-35d1-7bb6-a5b7-7eb4356dad43', 'yasmine@tech.com', 'Yasmine', 'Brito');
INSERT INTO ey.customer(id, email, first_name, last_name) VALUES ('0195e34f-35d1-7f95-b657-1c21ab382597', 'olga@tech.com', 'Olga', 'Garcia');

DELETE FROM ems.customer;

INSERT INTO ems.customer(id, email, first_name, last_name) VALUES ('0195e34f-35d1-7d95-86e8-8047af3e142e', 'joset@tech.com', 'José', 'Garcia');
