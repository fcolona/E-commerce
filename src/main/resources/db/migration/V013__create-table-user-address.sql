create table user_address (
    address_id BigInt not null auto_increment,
    user_id BigInt not null,
    address_line1 varchar(255) not null,
    address_line2 varchar(255) not null,
    city varchar(255) not null,
    postal_code varchar(255) not null,
    country varchar(255) not null,
    mobile varchar(255) not null,

    primary key (address_id),
    foreign key (user_id) references user(user_id)
)