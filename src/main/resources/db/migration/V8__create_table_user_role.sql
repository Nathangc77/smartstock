create table tb_user_role (
    user_id bigint not null,
    role_id bigint not null,
    primary key (role_id, user_id),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES tb_user (id),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES tb_role (id)
    );