create table product (
	product_id bigint  not null auto_increment,
  	name varchar(255) not null,
  	quantity int not null,
	price decimal not null,
  
  	primary key(product_id)
)