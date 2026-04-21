# 🧩 Linux ハンズオン２

## ゴール
- ログから必要な情報を自分で抽出できる
- コマンドを「思い出す」ではなく「組み立てる」力をつける
- grep / cut / sort / uniq / awk の実務的な使い方を理解する

---

1. 作業ディレクトリへ移動

```
# cd: ディレクトリ移動
# `~` はホームディレクトリ
# ハンズオン0で作成した演習用ディレクトリへ移動
cd ~/linux_cicd_training/workspace

# pwd: 現在のディレクトリ（作業場所）を表示
pwd
```

2. ログ全体を確認（ユースケース：状況把握）

```
# cat: ファイル内容をそのまま表示
cat logs/app.log
```

3. ERRORを抽出（ユースケース：障害確認）

```
# grep: 文字列検索（パターンに一致する行を抽出）
# ERROR を含む行だけ抽出
grep ERROR logs/app.log
```

4. ERRORの件数を確認（ユースケース：影響度確認）

```
# | (パイプ): 左の出力を右の入力へ渡す
# wc -l: 行数を数える
# ERROR 行数（件数）を数える
grep ERROR logs/app.log | wc -l
```

5. 特定ユーザーのログ確認（ユースケース：原因調査）

```
# tanaka を含む行だけ抽出
grep tanaka logs/app.log
```

6. ERRORかつ特定ユーザー（ユースケース：犯人特定）

```
# grep を2回つなげてAND条件にする
# ERROR かつ tanaka を含む行だけ抽出
grep ERROR logs/app.log | grep tanaka
```

7. 複数ログ検索（ユースケース：横断調査）

```
# logs 配下の全ログから ERROR を検索
# -R: 再帰的に検索
# -n: 行番号を表示
grep -R "ERROR" -n logs
```

8. エラー発生ユーザー抽出（ユースケース：影響範囲）

```
# cut: 区切り文字で列を抜き出す
# -d' ': 区切り文字をスペースにする
# -f4: 4列目を抜き出す
# 例) user=tanaka のような4番目のフィールドを抜き出す
grep ERROR logs/app.log | cut -d' ' -f4
```

9. ユーザー一覧（ユースケース：集計準備）

```
# sort: 文字列を並べ替える
# 抜き出した user=... を並べ替えて一覧化
grep ERROR logs/app.log | cut -d' ' -f4 | sort
```

10. ユーザーごとの件数（ユースケース：頻出エラー分析）

```
# uniq -c: 連続して同じ行が出た回数を数える
# ※ uniq は並んでいないと集計できないため、先に sort する
# sort してから uniq -c で件数を集計
grep ERROR logs/app.log | cut -d' ' -f4 | sort | uniq -c
```

11. 件数順に並べる（ユースケース：優先度判断）

```
# sort -n: 数値として並べ替える
# sort -r: 逆順（降順）
# 件数の多い順に並べる
grep ERROR logs/app.log | cut -d' ' -f4 | sort | uniq -c | sort -nr
```

12. 最新ログ確認（ユースケース：現在状況）

```
# tail: ファイル末尾を表示
# -n 5: 末尾5行を表示
tail -n 5 logs/app.log
```

13. リアルタイム監視（ユースケース：運用監視）

```
# tail -f: 追記される内容をリアルタイムで表示（Ctrl+C で終了）
tail -f logs/app.log
```

14. アクセスログ確認（ユースケース：外部影響確認）

```
# access.log 全体を表示
cat logs/access.log
```

15. 500エラー抽出（ユースケース：重大障害）

```
# ステータスコード 500 を含む行だけ抽出
grep " 500 " logs/access.log
```

16. IPアドレス抽出（ユースケース：アクセス分析）

```
# アクセスログの先頭フィールド（IP）だけを抽出
cut -d' ' -f1 logs/access.log
```

17. ファイル検索（ユースケース：対象ログ探索）

```
# find: ファイル/ディレクトリ検索
# . : カレントディレクトリ配下
# -name: ファイル名で検索（ワイルドカード可）
# カレントディレクトリ配下の .log ファイルを検索
find . -name "*.log"
```

18. 複数条件検索（ユースケース：絞り込み）

```
# ERROR を含む行から、さらに test を含む行に絞る
grep ERROR logs/app.log | grep test
```

19. IP抽出（ユースケース：トラフィック分析）

```
# （再掲）IPだけを抽出
cut -d' ' -f1 logs/access.log
```

20. IPごとの件数（ユースケース：負荷分析）

```
# IP を集計（sort → uniq -c）
cut -d' ' -f1 logs/access.log | sort | uniq -c
```

21. awkで整形（ユースケース：ログ加工）

```
# awk: 列（フィールド）を指定して整形できる
# $1, $3, $5 のように列番号で指定する
# 1列目（日時）/ 3列目（レベル）/ 5列目（action=...）を表示
awk '{print $1, $3, $5}' logs/app.log
```

22. ERRORログ整形（ユースケース：報告用）

```
# ERROR のみ抽出し、日時・ユーザー・アクションを表示
# $1: 日付, $2: 時刻, $4: user=..., $5: action=...
grep ERROR logs/app.log | awk '{print $1, $2, $4, $5}'
```

---

## 学び

① ログ解析の型

```
# 基本パターン（組み合わせが重要）
# grep → cut → sort → uniq → sort
```