create table image (
	image_id bigint auto_increment,
  	product_id bigint,
  	content longblob,

  	primary key (image_id),
  	foreign key (product_id) references product(product_id)
)
