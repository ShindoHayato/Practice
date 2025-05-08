# Gitの学習
## Gitの基礎知識
- リポジトリ
  - ファイルやディレクトリの変更履歴を記録する場所
  - ローカルリポジトリとリモートリポジトリがある
  - ローカルリポジトリ：自分のPC上にあるリポジトリ
  - リモートリポジトリ：クラウド上にあるリポジトリ
- ３つの状態
  - 作業ディレクトリ(Working Directory)、ステージングエリア(Staging Area)、リポジトリの３つの状態がある(.git Repository)
  - 作業ディレクトリ：ファイルを編集している状態
  - ステージングエリア：リポジトリにコミットする準備ができた状態
  - リポジトリ：コミットされた状態

## Gitでの開発フローにおける注意点
ブランチ
- main(master): 本番環境に対応するブランチ
- develop: 開発環境に対応するブランチ。このブランチから作業用ブランチを作成する

必ず作業用ブランチ上で作業をし、main(master), developブランチで作業はしない

作業ブランチの作成時は、最新のdevelop(developがなければmain)を取り込む

## Gitの基本コマンド
```
# リポジトリの初期化
# .gitディレクトリが作成される
git init

# ディレクトリの確認
ls -al

# ファイルの追加
touch README.md

# git statusでgitの状態を確認
git status

# ファイルをステージングエリアに追加
git add README.md

# git statusでgitの状態を確認
git status

# ファイルをリポジトリにコミット
git commit -m "first commit"

# git statusでgitの状態を確認
git status

# git logでコミット履歴を確認
git log

---
# ファイルの変更
echo "Hello World" > README.md

# git statusでgitの状態を確認
git status

# ファイルをステージングエリアに追加
git add README.md

# git statusでgitの状態を確認
git status

# ファイルをリポジトリにコミット
git commit -m "second commit"

# git logでコミット履歴を確認
git log
```

## GitHubからのクローンからpush, プルリクエストまでの基本操作
```
# リポジトリのクローン
git clone <リポジトリSSH URL>

# ディレクトリの確認
ls -al

# リポジトリへ移動
cd <リポジトリ名>

# ブランチを作成
git switch -c <ブランチ名>

# ブランチの確認
git branch

# ファイルの修正
vim <ファイル名>

# git statusでgitの状態を確認
git status

# ファイルをステージングエリアに追加
git add <ファイル名>

# git statusでgitの状態を確認
git status

# ファイルをリポジトリにコミット
git commit -m "<コミットメッセージ>"

# git statusでgitの状態を確認
git status

# リモートリポジトリにプッシュ
git push origin <ブランチ名>

# ブラウザでGitHubにアクセスし、プルリクエストを作成

# プルリクエストをマージ
```

## コンフリクトの解消
```
## 事前に修正対象のファイルが編集され、プルリクエストが作成されている

# ブランチを作成
git switch -c <ブランチ名>

# ブランチの確認
git branch

# ファイルの修正
vim <ファイル名>

# git statusでgitの状態を確認
git status

# ファイルをステージングエリアに追加
git add <ファイル名>

# git statusでgitの状態を確認
git status

# ファイルをリポジトリにコミット
git commit -m "<コミットメッセージ>"

# リモートリポジトリにプッシュ
git push origin <ブランチ名>

# プルリクエストを作成
# diffを確認


## 事前に作成したプルリクエストがマージされる

# プルリクエストのコンフリクトが発生

# mainブランチに移動
git switch main

# プル
git pull origin main

# 作業ブランチに移動
git switch <ブランチ名>

# マージ
git merge main

# コンフリクトの解消
vim <ファイル名>

# git statusでgitの状態を確認
git status

# ファイルをステージングエリアに追加
git add <ファイル名>

# git statusでgitの状態を確認
git status

# マージコミットを作成
git commit

# リモートリポジトリにプッシュ
git push origin <ブランチ名>

# プルリクエストのコンフリクト解消を確認

# プルリクエストをマージ
```
