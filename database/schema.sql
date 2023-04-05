drop database if exists railway;

create database railway;

use railway;

create table user (
	user_id	varchar(8) not null primary key,
    username varchar(255) not null unique,
    name varchar(255),
    check (username regexp '^[a-zA-Z0-9]*$')
);

create table task (
	task_id	int not null primary key auto_increment,
    description varchar(255) not null,
    priority tinyint,
    due_date date,
    user_id	varchar(8) not null,
    constraint task_user_fk foreign key (user_id) references user(user_id),
    check (priority >= 1 AND priority <= 3)
);