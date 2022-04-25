create table user (
	user_id Bigint not null auto_increment,
  	email varchar(255) unique not null,
  	password varchar(60) not null,
  
  	primary key(user_id)
);