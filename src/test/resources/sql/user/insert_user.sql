DELETE FROM `user`;

INSERT IGNORE INTO `user`(id, mail, password, roles, created, last_logined, enabled) VALUES
-- パスワード: password
(1, 'tester1@test.jp', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_USER', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true),
(2, 'tester2@test.jp', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_ADMIN', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true),
(3, 'tester3@test.jp', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_ADMIN', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true),
(4, 'tester4@test.jp', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_ADMIN', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true),
(5, 'tester5@test.jp', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_ADMIN', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true),
(6, 'tester6@test.jp', '$2a$10$/bGgp.eMJ8IW1UKatZgKKuLzcfIY9eJMqgz.HlX4CJjQuLTh1ic/y', 'ROLE_ADMIN', '2022-01-01 00:00:00', '2022-01-01 00:00:00', true);