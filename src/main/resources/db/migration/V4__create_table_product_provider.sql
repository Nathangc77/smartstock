create table tb_product_provider (
    product_id bigint not null,
    provider_id bigint not null,
    primary key (product_id, provider_id),
    CONSTRAINT fk_product_provider_product FOREIGN KEY (product_id) REFERENCES tb_product (id),
    CONSTRAINT fk_product_provider_provider FOREIGN KEY (provider_id) REFERENCES tb_provider (id)
    );