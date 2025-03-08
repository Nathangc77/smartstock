INSERT INTO tb_category (name) VALUES ('Parafusos');
INSERT INTO tb_category (name) VALUES ('Ferramentas');
INSERT INTO tb_category (name) VALUES ('Tintas');
INSERT INTO tb_category (name) VALUES ('Elétricos');

INSERT INTO tb_provider (name, cnpj, email, phone, active) VALUES ('Rei dos parafusos', '59088941000171', 'reidosparafusos@gmail.com', '79996854687', true);
INSERT INTO tb_provider (name, cnpj, email, phone, active) VALUES ('Loja das Ferramentas', '12345678000199', 'contato@lojadasferramentas.com', '11987654321', true);
INSERT INTO tb_provider (name, cnpj, email, phone, active) VALUES ('Tintas Premium', '98765432000188', 'suporte@tintaspremium.com', '21999887766', true);
INSERT INTO tb_provider (name, cnpj, email, phone, active) VALUES ('EletroTech', '55667788000155', 'vendas@eletrotech.com', '31988776655', true);

INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Parafuso Phillips C/bucha 8 Anel 8mm', 0.20, 500, 2, 1);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Martelo de Borracha 500g', 25.00, 100, 2, 2);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Tinta Acrílica Branca 3,6L', 75.50, 50, 2, 3);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Fita Isolante 19mm', 3.50, 300, 2, 4);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Chave de Fenda 6mm', 12.90, 150, 2, 2);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Serra Circular 7 1/4" 1400W', 320.00, 30, 2, 2);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Rolo de Pintura 23cm', 18.50, 80, 2, 2);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Tinta Spray Preto Fosco 400ml', 22.90, 120, 2, 3);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Disjuntor DIN Monofásico 25A', 35.00, 60, 2, 4);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Tomada 10A Branca', 5.50, 500, 2, 4);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Parafuso Sextavado 5mm', 15.00, 200, 2, 1);

INSERT INTO tb_product_provider (product_id, provider_id) VALUES (1, 1);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (2, 2);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (3, 3);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (4, 4);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (5, 2);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (6, 2);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (7, 3);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (8, 3);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (9, 4);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (10, 4);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (11, 1);