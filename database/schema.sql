create database todo;
use todo;

create table user(
id		int not null auto_increment,
user_id varchar(8) not null,
username varchar(50) not null,
name 	varchar(50),
constraint 		user_pk primary key(id)
);

create table task(
id					int not null auto_increment,
description			varchar(150),
priority			int,
due_date			date,
user_id				varchar(8) not null,
constraint			task_id primary key(id),
constraint			user_fk foreign key(user_id) references user(id)
);
