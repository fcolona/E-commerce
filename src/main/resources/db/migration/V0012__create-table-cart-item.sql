create table cart_item (
    id BigInt not null auto_increment,
    cart_id BigInt not null,
    product_id BigInt not null,
    quantity int not null,

    primary key (id),
    foreign key (cart_id) references cart(id),
    foreign key (product_id) references product(product_id)
);