# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table products (
  id                        varchar(255) not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_products primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table products;

SET FOREIGN_KEY_CHECKS=1;

