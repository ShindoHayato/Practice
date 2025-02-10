INSERT IGNORE INTO `user`(id, mail, password, roles, created, last_logined, enabled) VALUES
-- adminパスワード: password
(1, 'test_user@test.jp', '$2a$10$oCj5iwYAVJ78fm8VkrOnMOG3caMrMl0t94Wc/CM8bC0DAM867gyPK', 'ROLE_USER', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true),
(2, 'test_admin', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_ADMIN', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true),
(3, 'test_admin@test.jp', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_ADMIN', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true),
(4, 'test_user_disable@test.jp', '$2a$10$oCj5iwYAVJ78fm8VkrOnMOG3caMrMl0t94Wc/CM8bC0DAM867gyPK', 'ROLE_USER', '2022-01-01 00:00:00', '2022-01-01 00:00:00', false);
