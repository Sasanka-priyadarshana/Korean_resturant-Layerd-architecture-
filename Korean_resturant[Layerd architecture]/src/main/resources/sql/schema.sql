drop database if exists korean_resturant;
create database if not exists korean_resturant;

use korean_resturant;

create table user(
 username varchar(16)  PRIMARY KEY,
 pw int(10)
);
INSERT INTO user VALUES('sasanka','1');


create table employees(
 e_id varchar(20)PRIMARY KEY,
 email varchar(50),
 address varchar(50),
 tel_number int(20),
 username varchar(20),
 CONSTRAINT FOREIGN KEY(username)REFERENCES user(username) ON DELETE CASCADE ON UPDATE CASCADE
);

create table salary(
 s_id varchar(10)PRIMARY KEY,
 payment varchar(10),
 date Date,
 e_id varchar(20),
 CONSTRAINT FOREIGN KEY(e_id)REFERENCES employees(e_id)ON DELETE CASCADE ON UPDATE CASCADE

);

create table suppliers(
 sup_id varchar(10)PRIMARY KEY,
 address varchar(10),
 username varchar(16),
 CONSTRAINT FOREIGN KEY(username)REFERENCES user(username)ON DELETE CASCADE ON UPDATE CASCADE

);

create table attendance(
 a_id varchar(10)PRIMARY KEY,
 e_id varchar(20),
 date Date,
 time Time,
 CONSTRAINT FOREIGN KEY(e_id)REFERENCES employees(e_id)ON DELETE CASCADE ON UPDATE CASCADE
);

create table customer(
 c_id varchar(20)PRIMARY KEY,
 name varchar(10),
 tel_number int(10)
);

create table room(
 room_no varchar(10)PRIMARY KEY,
 description varchar(20),
 availability varchar(30)
);

create table stock_item(
 stock_item_id varchar(10)PRIMARY KEY,
 stock_name varchar(20),
 qty int(10),
 unit_price varchar(10)
);

create table orders(
 o_id varchar(10)PRIMARY KEY,
 date Date,
 type varchar(10),
 status varchar(10),
 total int(10),
 c_id varchar(20),
 CONSTRAINT FOREIGN KEY(c_id)REFERENCES customer(c_id)ON DELETE CASCADE ON UPDATE CASCADE

);

create table menu_item(
 item_code varchar(10)PRIMARY KEY,
 unit_price varchar(10),
 description varchar(10),
 name varchar(10)
);

create table order_details(
 o_id varchar(10),
 item_code varchar(10),
  qty int(10),
 CONSTRAINT FOREIGN KEY(o_id)REFERENCES orders(o_id)ON DELETE CASCADE ON UPDATE CASCADE,
 CONSTRAINT FOREIGN KEY(item_code)REFERENCES menu_item(item_code)ON DELETE CASCADE ON UPDATE CASCADE
);

create table payment(
 p_id varchar(10)PRIMARY KEY,
 amount varchar(10),
 o_id varchar(10),
 CONSTRAINT FOREIGN KEY(o_id)REFERENCES orders(o_id)ON DELETE CASCADE ON UPDATE CASCADE

);

create table supplier_orders(
 sup_oid varchar(10)PRIMARY KEY,
 date Date,
 sup_id varchar(10),
 CONSTRAINT FOREIGN KEY(sup_id)REFERENCES suppliers(sup_id) ON DELETE CASCADE ON UPDATE CASCADE

);


create table sup_order_stock(
 stock_item_id varchar(10),
 sup_oid varchar(10),
 CONSTRAINT FOREIGN KEY(stock_item_id)REFERENCES stock_item(stock_item_id)ON DELETE CASCADE ON UPDATE CASCADE,
 CONSTRAINT FOREIGN KEY(sup_oid)REFERENCES supplier_orders(sup_oid)ON DELETE CASCADE ON UPDATE CASCADE
);