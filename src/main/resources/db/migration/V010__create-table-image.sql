create table image (
	image_id bigint auto_increment not null,
  	product_id bigint not null,
  	content longblob not null,

  	primary key (image_id),
  	foreign key (product_id) references product(product_id)
)
