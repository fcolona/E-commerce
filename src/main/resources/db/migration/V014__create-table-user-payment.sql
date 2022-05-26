create table user_payment (
    payment_id BigInt not null auto_increment,
    user_id BigInt not null,
    payment_type varchar(255) not null,
    provider varchar(255) not null,
    expiry date not null,

    primary key (payment_id),
    foreign key (user_id) references user(user_id)
)