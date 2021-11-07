#!/bin/bash
sudo docker pull postgres:latest
sudo docker run -d --name=db --rm -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=BulletinBoard postgres
sudo timeout 2 ping 127.0.0.1
sudo docker exec -it db psql -v ON_ERROR_STOP=1 -U postgres -c "\c BulletinBoard;" -c "
create table roles
(
    id   bigserial    primary key,
    name varchar(20)  not null
);

create table categories
(
	id bigserial primary key,
	name varchar not null
);

create table users
(
	id bigserial primary key,
	login varchar not null,
	password varchar not null,
	firstname varchar not null,
	lastname varchar not null,
	phone_number varchar(11) not null,
	rating double precision not null,
	role_id integer not null,
	foreign key (role_id) references roles (id) on delete cascade,

	constraint valid_number
      check (rating <= 5)
);

create table users_chat
(
	user_id bigserial unique not null,
	name varchar not null,
	foreign key (user_id) references users (id) on delete cascade
);

create table chats
(
	id bigserial primary key,
	seller_id bigint unique not null,
	buyer_id bigint unique not null,
	foreign key (seller_id) references users_chat (user_id) on delete cascade,
	foreign key (buyer_id) references users_chat (user_id) on delete cascade
);

create type role as enum ('seller', 'buyer');

create table messages
(
	id bigserial primary key,
	current_user_role role not null,
	message_text varchar not null,
	time_of_message timestamp with time zone not null,
	chat_id bigint not null,
	foreign key (chat_id) references chats (id) on delete cascade
);

create table posts
(
	id bigserial primary key,
	title varchar not null,
	price double precision,
	is_active boolean not null,
	picture varchar not null,
	description varchar not null,
	post_time timestamp with time zone not null,
	seller_id bigint not null,
	category_id bigint not null,
	priority boolean not null,
	foreign key (seller_id) references users (id) on delete cascade,
	foreign key (category_id) references categories (id) on delete cascade
);

create extension pg_trgm;

create index posts_trgm_idx on posts
  using gin (title gin_trgm_ops);

create table comments
(
	id bigserial primary key,
	post_id bigint not null,
	user_id bigint not null,
	comment_time timestamp with time zone not null,
	comment varchar not null,
	foreign key (post_id) references posts (id) on delete cascade,
	foreign key (user_id) references users (id) on delete cascade
);

create table ratings
(
	id bigserial primary key,
	user_id bigint not null,
	rate double precision not null,
	foreign key (user_id) references users (id) on delete cascade,

  constraint valid_number
      check (id <= 5)
);


insert into roles(name) values ('ROLE_ADMIN');
insert into roles(name) values ('ROLE_USER');

insert into categories values(1, 'furniture');
insert into categories values(2, 'clothes');

insert into users values(1, 'alex', 'password1', 'Alex', 'Bych', '88888888888', 5, 1);
insert into users values(2, 'pasha', 'password2', 'Pavel', 'F', '99999999999', 3, 2);
insert into users values(3, 'masha', 'password3', 'Maria', 'G', '11111111111', 2, 2);

insert into users_chat values(2, 'Pavel');
insert into users_chat values(3, 'Maria');

insert into chats values(1, 3, 2);

insert into messages values(1, 'buyer', 'prove original', '2021-11-11', 1);
insert into messages values(2, 'seller', 'no', '2021-11-11', 1);

insert into posts values(1, 'sofa', 2300, true, 'sofa.png', 'super sofa, very soft', '2020-01-01', 2, 1, false);
insert into posts values(2, 'pants nike', 3000, true, 'pants_nike.png', 'new pants, XXL', '2020-07-11', 2, 2, true);
insert into posts values(3, 'jacket gucci', 40500, true, 'lol.png', 'original', '2021-10-09', 3, 2, false);

insert into comments values(1, 1, 1, '2020-01-02', 'bad sofa');
insert into comments values(2, 1, 3, '2021-11-11', 'its not original lol');
insert into comments values(3, 2, 3, '2021-12-12', 'its not good seller');

insert into ratings values(1, 3, 1);
insert into ratings values(2, 3, 3);"