INSERT INTO tb_category (name) VALUES ('Parafusos');
INSERT INTO tb_category (name) VALUES ('Ferramentas');
INSERT INTO tb_category (name) VALUES ('Tintas');
INSERT INTO tb_category (name) VALUES ('Elétricos');
INSERT INTO tb_category (name) VALUES ('Aço');

INSERT INTO tb_provider (name, cnpj, email, phone, active) VALUES ('Rei dos parafusos', '59088941000171', 'reidosparafusos@gmail.com', '79996854687', true);
INSERT INTO tb_provider (name, cnpj, email, phone, active) VALUES ('Loja das Ferramentas', '12345678000199', 'contato@lojadasferramentas.com', '11987654321', true);
INSERT INTO tb_provider (name, cnpj, email, phone, active) VALUES ('Tintas Premium', '98765432000188', 'suporte@tintaspremium.com', '21999887766', true);
INSERT INTO tb_provider (name, cnpj, email, phone, active) VALUES ('EletroTech', '55667788000155', 'vendas@eletrotech.com', '31988776655', true);

INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Parafuso Phillips C/bucha 8 Anel 8mm', 0.20, 500, 1, 1);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Martelo de Borracha 500g', 25.00, 100, 1, 2);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Tinta Acrílica Branca 3,6L', 75.50, 50, 1, 3);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Fita Isolante 19mm', 3.50, 300, 1, 4);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Chave de Fenda 6mm', 12.90, 150, 1, 2);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Serra Circular 7 1/4" 1400W', 320.00, 30, 1, 2);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Rolo de Pintura 23cm', 18.50, 80, 1, 2);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Tinta Spray Preto Fosco 400ml', 22.90, 120, 1, 3);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Disjuntor DIN Monofásico 25A', 35.00, 60, 1, 4);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Tomada 10A Branca', 5.50, 500, 1, 4);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Parafuso Sextavado 5mm', 15.00, 200, 1, 1);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Eletrodo Aço Carbono E6013 2,50mm Embalagem 1kg', 31.00, 50, 0, 5);
INSERT INTO tb_product (name, price, quantity, unit_measure, category_id) VALUES ('Cabo Flexível 2,5mm Vermelho', 2.30, 200, 2, 4);

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
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (12, 4);
INSERT INTO tb_product_provider (product_id, provider_id) VALUES (13, 4);

INSERT INTO tb_user (name, email, password) VALUES ('Maria Brown', 'maria@gmail.com', '$2a$10$K6U.VlbYY6vWizonW6GFB.9G3QSJ9JzfQudivOwJzPHqZcac.FM8G');
INSERT INTO tb_user (name, email, password) VALUES ('Alex Green', 'alex@gmail.com', '$2a$10$K6U.VlbYY6vWizonW6GFB.9G3QSJ9JzfQudivOwJzPHqZcac.FM8G');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);

INSERT INTO tb_stock_movement (moment, quantity, type, reason, product_id, user_id) VALUES ('2024-03-01T10:15:00Z', 50, 0, 'Reposição de estoque', 1, 1);
INSERT INTO tb_stock_movement (moment, quantity, type, reason, product_id, user_id) VALUES ('2024-03-02T14:30:00Z', 20, 1, 'Venda realizada', 2, 2);
INSERT INTO tb_stock_movement (moment, quantity, type, reason, product_id, user_id) VALUES ('2024-03-03T09:00:00Z', 10, 1, 'Produto usado para manutenção interna', 3, 1);
INSERT INTO tb_stock_movement (moment, quantity, type, reason, product_id, user_id) VALUES ('2024-03-04T16:45:00Z', 100, 0, 'Novo lote recebido do fornecedor', 4, 2);
INSERT INTO tb_stock_movement (moment, quantity, type, reason, product_id, user_id) VALUES ('2024-03-05T11:20:00Z', 5, 1, 'Amostra para cliente', 5, 2);
