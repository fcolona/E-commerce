create table user (
	id Bigint not null,
  	password varchar(60) not null,
  	email varchar(255) not null,
  
  	primary key(id)
);