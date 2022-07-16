-- create table if not exists account
-- (
--     id      bigint NOT NULL
--     primary key,
--     name    varchar(255),
--     number  varchar(255),
--     surname varchar(255)
-- );
--
-- create table if not exists currency
-- (
--     id bigint not null primary key,
--     name varchar(255)
-- );
--
-- create table if not exists cash
-- (
--     id      bigint not null
--     primary key,
--     balance numeric(19, 2)
-- );
--
-- create table if not exists roles
-- (
--     id   bigint not null
--     primary key,
--     name varchar(255)
-- );
--
-- create table if not exists transfer
-- (
--     id           bigint not null
--     primary key,
--     amount       numeric,
--     comment      varchar(255),
--     status       integer,
--     recipient_id bigint
--     references account(id),
--     sender_id    bigint
--     references account(id)
-- );
--
-- create table if not exists users
-- (
--     id       bigint not null
--     primary key,
--     password varchar(255) not null,
--     username varchar(255)
--     unique
-- );
--
-- create table if not exists users_roles
-- (
--     user_id bigint not null
--     references users(id),
--     role_id bigint not null
--     references roles(id)
-- );

create table if not exists account (id  bigserial not null, name varchar(255), number varchar(255), surname varchar(255), transfer_id int8 references transfer(id), unic_code int4, primary key (id));
create table if not exists cash (id  bigserial not null, balance int8, primary key (id));
create table if not exists currency (id  bigserial not null, name varchar(255), primary key (id));
create table if not exists roles (id  bigserial not null, name varchar(255), primary key (id));
create table if not exists transfer (id  bigserial not null, amount int8 not null, comment varchar(255), status varchar(255), unic_code int4, cash_id int8 references cash(id), currency_id int8 references currency(id), recipient_id int8 references account(id), sender_id int8 references account(id), primary key (id));
create table if not exists users (id  bigserial not null, password varchar(255) not null, username varchar(255), primary key (id));
create table if not exists users_roles (user_id int8 not null references users(id), role_id int8 not null references roles(id));

