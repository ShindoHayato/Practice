DELETE FROM `user`;

INSERT IGNORE INTO `user`(id, mail, password, roles, created, last_logined, enabled) VALUES
-- パスワード: password
(1, 'tester3@test.jp', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_ADMIN', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true),
(2, 'tester4@test.jp', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_ADMIN', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true),
(3, 'tester5@test.jp', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_ADMIN', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true);
