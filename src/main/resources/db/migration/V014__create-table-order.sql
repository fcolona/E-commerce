create table `order` (
	id BigInt not null auto_increment,
  	user_id BigInt not null,
  	address_id BigInt not null,
  	stripe_id varchar(255) not null unique,
  	status varchar(60) not null,

  	primary key (id),
  	foreign key (user_id) references user(user_id),
  	foreign key (address_id) references user_address(address_id)
);