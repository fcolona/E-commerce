create table products_categories (
	product_id bigint not null,
  	category_id bigint not null,
  
  	foreign key(product_id) references product(product_id),
  	foreign key(category_id) references category(category_id)
)