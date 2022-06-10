create table order_item (
	id BigInt not null auto_increment,
  	order_id BigInt not null,
  	product_id BigInt not null,
  	quantity int not null,

  	primary key(id),
  	foreign key(order_id) references `order`(id),
  	foreign key(product_id) references product(product_id)
);