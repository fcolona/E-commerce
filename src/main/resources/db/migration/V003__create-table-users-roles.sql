CREATE TABLE users_roles (
	id Bigint NOT NULL AUTO_INCREMENT,
  	user_id Bigint NOT NULL,
	role_id Bigint NOT NULL,
  	FOREIGN KEY (user_id) REFERENCES user(user_id),
  	FOREIGN KEY (role_id) REFERENCES role(role_id),
  	PRIMARY KEY (id)
);