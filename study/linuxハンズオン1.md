# 🧩 Linux ハンズオン1

## 前提
ハンズオン0を完了していること

## ゴール
- ファイル操作（ls / cp / mv / rm）を理解すること
- 検索（grep / find）を使えるようになること
- 権限操作・プロセス操作の基礎を理解すること

---

1. 作業ディレクトリへ移動

```
# cd: ディレクトリ移動
# `~` はホームディレクトリ
cd ~/linux_cicd_training/workspace

# pwd: 現在のディレクトリ（作業場所）を表示
pwd
```

2. ファイル一覧を確認

```
# ls: ファイル/ディレクトリ一覧
ls

# -l: パーミッション/所有者/サイズ/更新時刻などを表示（詳細表示）
# -a: . で始まる隠しファイルも表示
ls -la
```

3. src配下を確認

```
# src ディレクトリの中身を確認
ls src

# tree: ディレクトリ構造をツリー表示
tree src
```

4. ファイル内容を確認

```
# cat: ファイル内容をそのまま表示
cat src/config/app.conf
```

5. ファイルをコピー

```
# cp: ファイルをコピー
# 末尾が / の場合は「ディレクトリへコピー」を意味する

# 事前確認: コピー元/コピー先を確認
ls -l src/config/app.conf
ls -ld backup/

cp src/config/app.conf backup/

# 事後確認: コピー先にファイルがあるか
ls -l backup/app.conf
```

6. ファイルを移動

```
# mv: ファイル/ディレクトリの移動（=リネームも同じコマンド）

# 事前確認: 移動元/移動先を確認
ls -l backup/app.conf
ls -ld tmp/

mv backup/app.conf tmp/

# 事後確認: 元から消えているか / 移動先にあるか
ls -l backup/
ls -l tmp/app.conf
```

7. ファイルを削除

```
# rm: ファイル削除
# ルール: 誤削除防止のため -i を必ず付ける（削除前に確認が出る）
# -i: interactive（削除前に確認）

# 事前確認: 本当に消す対象か確認
ls -l tmp/app.conf

rm -i tmp/app.conf

# 事後確認: 削除できたか（存在しなければエラーになる）
ls -l tmp/app.conf
```

8. ディレクトリを作成

```
# mkdir: ディレクトリ作成

# 事前確認: すでに存在しないか（存在するなら中身を見て判断）
ls -ld test_dir

mkdir test_dir

# 事後確認: 作成できたか
ls -ld test_dir
```

9. ディレクトリごとコピー

```
# cp -r: ディレクトリを再帰的にコピー（ディレクトリ配下も含めてコピー）

# 事前確認: コピー元/コピー先を確認
ls -ld src/js
ls -ld test_dir/

cp -r src/js test_dir/

# 事後確認: コピー先の構造を確認
tree test_dir/js
```

10. ログを検索

```
# grep: 文字列検索
# ここでは logs/app.log から "ERROR" を含む行だけ抽出
grep ERROR logs/app.log
```

11. 検索＋行番号

```
# -n: マッチした行番号も表示（ログの場所特定に便利）
grep -n ERROR logs/app.log
```

12. 複数条件検索

```
# パイプ(|): 左の出力を右の入力へ渡す
# 1) ERROR を含む行を抽出 → 2) その中から test を含む行へ絞り込み
grep ERROR logs/app.log | grep test
```

13. ファイル検索

```
# find: ファイル/ディレクトリを検索
# . : カレントディレクトリ配下
# -name: 名前で検索（ワイルドカード可）
find . -name "*.log"
```

14. サイズ確認

```
# -l: 詳細表示
# -h: 人間が読みやすい単位（KB/MBなど）で表示
ls -lh tmp
```

15. 権限変更

```
# chmod: パーミッション（実行権限など）を変更
# +x: 実行権限を付与

# 事前確認: 現在のパーミッション
ls -l permissions/run.sh

chmod +x permissions/run.sh

# 事後確認: パーミッションを含む詳細表示で確認
ls -l permissions/run.sh
```

16. 実行

```
# ./... : カレントディレクトリ配下のファイルを実行
# （パスに . が入っていないと、同名コマンドを PATH 上から探しに行く）

# 事前確認: 実行権限があるか
ls -l permissions/run.sh

./permissions/run.sh

# 事後確認: 終了ステータス（0なら成功）
echo $?
```

17. プロセス起動

```
# プロセス練習用のディレクトリへ移動
cd process
pwd

# 事前確認: すでに起動していないか
ps aux | grep dummy

# &: バックグラウンド実行（ターミナルを占有しない）
./dummy_worker.sh &

# 事後確認: ジョブ一覧（バックグラウンド起動できているか）
jobs
```

18. プロセス確認

```
# ps aux: 実行中プロセス一覧（a=他ユーザー含む端末, u=ユーザー表示, x=ttyなし含む）
# grep: 一覧から dummy を含む行だけ抽出
ps aux | grep dummy

# 確認: ログが増えているか（バックグラウンドで動いている証拠）
tail -n 3 worker.log
```

19. プロセス停止

```
# kill: PID（プロセスID）を指定して終了シグナルを送る
# まずは ps で PID を見つけてから実行する

# 事前確認: 対象プロセスとPIDを確認
ps aux | grep dummy

# 例）kill 12345
kill 12345

# 事後確認: 停止できたか（該当プロセスが出ないこと）
ps aux | grep dummy
```


---

## 学び

① Linux基本操作
- ls / cp / mv / rm / mkdir

② 検索・調査
- grep / find

③ 権限・プロセス
- chmod / ps / kill
