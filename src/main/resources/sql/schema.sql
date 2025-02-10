CREATE TABLE IF NOT EXISTS `user` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `mail` VARCHAR(128) NOT NULL, -- メールアドレス
    `password` VARCHAR(64) NOT NULL, -- パスワードのハッシュ値 spring security
    `roles` VARCHAR(255) NOT NULL, -- ロール カンマ区切り文字列 "ROLE_USER,ROLE_ADMIN" spring security
    `created` DATETIME NOT NULL, -- 作成時刻
    `last_logined` DATETIME NOT NULL, -- 最終ログイン時刻
    `enabled` BOOLEAN NOT NULL, -- 有効、無効フラグ
    UNIQUE KEY (`mail`)
);