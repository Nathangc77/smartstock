INSERT INTO tb_stock_movement (moment, quantity, type, reason, product_id, user_id, provider_id)
VALUES ('2024-03-01T10:15:00Z', 50, 0, 'Reposição de estoque', 1, 1, 1),
       ('2024-03-04T16:45:00Z', 100, 0, 'Novo lote recebido do fornecedor', 4, 2, 4);

INSERT INTO tb_stock_movement (moment, quantity, type, reason, product_id, user_id, client_id)
VALUES ('2024-03-02T14:30:00Z', 20, 1, 'Venda realizada', 2, 2, 1),
       ('2024-03-03T09:00:00Z', 10, 1, 'Produto usado para manutenção interna', 3, 1, 2),
       ('2024-03-05T11:20:00Z', 5, 1, 'Amostra para cliente', 5, 2, 1);

