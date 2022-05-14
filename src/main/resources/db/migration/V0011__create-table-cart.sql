create table cart (
    id BigInt not null auto_increment,
    total decimal default 0.0,

    primary key (id),
    foreign key (id) references user(user_id)
);