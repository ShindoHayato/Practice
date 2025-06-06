# HTTP

https://developer.mozilla.org/ja/docs/Web/HTTP

HTTP（Hypertext Transfer Protocol）は、クライアントとサーバー間でデータを転送するためのプロトコルです。
主な用途としてはWEBブラウザ(クライアント)とWEBサーバー間でのデータ転送が挙げられます。

クライアントが送信するメッセージをリクエスト、サーバーが返すメッセージをレスポンスと呼びます。

リクエストに含まれるもの
- HTTPメソッド（GET, POST, PUT, DELETEなど）
- リクエストURL
- HTTPバージョン
- ヘッダー情報（Content-Type, Authorizationなど）
- ボディ（POSTやPUTリクエストでデータを送信する場合）

```bash
# リクエストの例
# -X POST でHTTPのPOSTメソッドを指定
# http://... でリクエストURLを指定
# -H 'Content-Type: application/json' でヘッダー情報を指定
# -d '{"key1":"value1", "key2":"value2"}' でリクエストボディをJSON形式で指定

curl -X POST http://example.com/api/resource \
  -H 'Content-Type: application/json' \
  -d '{"key1":"value1", "key2":"value2"}'
```

レスポンスに含まれるもの
- HTTPステータスコード（200 OK, 404 Not Foundなど）
- HTTPバージョン
- ヘッダー情報（Content-Type, Content-Lengthなど）
- ボディ（HTML, JSON, XMLなどのデータ）

```bash
curl --http2 -i http://example.com

# レスポンスの例
# 200 OK HTTPステータスコード
# Content-Typeなど ヘッダー情報
# <!doctype html>以降 ボディ情報

HTTP/1.1 200 OK
Content-Type: text/html
ETag: "84238dfc8092e5d9c0dac8ef93371a07:1736799080.121134"
Last-Modified: Mon, 13 Jan 2025 20:11:20 GMT
Cache-Control: max-age=799
Date: Thu, 05 Jun 2025 12:46:27 GMT
Content-Length: 1256
Connection: keep-alive

<!doctype html>
<html>
<head>
    <title>Example Domain</title>
...
```


##### HTTPメソッド
https://developer.mozilla.org/ja/docs/Web/HTTP/Reference/Methods

- GET: リソースの取得
- POST: リソースの作成
- PUT: リソースの更新
- DELETE: リソースの削除
- PATCH: リソースの部分更新
- OPTIONS: リソースのサポートする通信オプションの取得。プリフライトリクエストで使用される


##### バージョン
HTTP/1.0, HTTP/1.1, HTTP/2, HTTP/3などのバージョンがあります。
現在はトラフィックの割合として、1.x系が3割、2が５割、3が２割程度の割合で使われているらしいです


##### ヘッダー
https://developer.mozilla.org/ja/docs/Web/HTTP/Reference/Headers

メッセージ内容に関連しないが、サーバー側に渡したい情報を指定するためのものです。

主なリクエストヘッダー
- Content-Type: リクエストボディのデータ形式を指定します。
- User-Agent: クライアントの情報を指定します。ブラウザやOSの情報などが含まれます。
- Cookie: クライアントがサーバーに送信するCookie情報を含みます。

主なレスポンスヘッダー
- Content-Type: レスポンスボディのデータ形式を指定します。
- Set-Cookie: クライアントにCookieを設定するためのヘッダーです。

**Cookie**  
https://developer.mozilla.org/ja/docs/Web/HTTP/Guides/Cookies

ヘッダーに含まれる情報で、サーバー側でクライアントの状態を管理するために使用されます。

認証情報、ECサイトでのカート情報、UIの設定、行動記録などを保存するために主に使用されます。


##### レスポンスステータスコード
https://developer.mozilla.org/ja/docs/Web/HTTP/Reference/Status

リクエストの結果を示すためのコードです。
- 2xx: 成功
- 4xx: クライアントエラー
- 5xx: サーバーエラー

知っておくべきステータスコード
- 200 OK: リクエストの成功
- 201 Created: リソースの作成成功 POSTリクエストでよく使われる
- 204 No Content: リクエストは成功したが、レスポンスボディはない DELETEリクエストでよく使われる

- 301 Moved Permanently: リソースが恒久的に移動したことを示す
- 302 Found: リソースが一時的に移動したことを示す

- 400 Bad Request: リクエストが不正であることを示す クライアントの入力ミスなど
- 401 Unauthorized: 認証が必要であることを示す
- 403 Forbidden: リクエストは理解されたが、アクセスが禁止されていることを示す
- 404 Not Found: リクエストされたリソースが見つからないことを示す

- 500 Internal Server Error: サーバー内部でエラーが発生したことを示す
- 502 Bad Gateway: ゲートウェイとして動作するサーバーが無効なレスポンスを受け取ったことを示す
- 503 Service Unavailable: サーバーが一時的に利用できないことを示す メンテナンス中やサーバーダウンなど

##### デベロッパーツール
WEBシステムの開発時に必ず使うツール

ブラウザのデベロッパーツールを使うと、HTTPリクエストとレスポンスの詳細を確認できます。
javascriptのデバッグやHTMLの確認などなど他にも多くの機能があります。
F12キーを押すと開くことができます。

##### CORS
https://developer.mozilla.org/ja/docs/Web/HTTP/Guides/CORS

WEBシステム開発時にたまに遭遇する問題

異なるオリジン(ドメイン、プロトコル、ポート番号)にある選択されたリソースへのアクセス権を与えるようブラウザーに指示するための仕組みです

`https://domain-a.com`から`https://domain-b.com`のリソースにアクセスする場合、サーバー側がCORSヘッダーを設定し許可している必要があります。
※ 単純リクエストではない場合は、OPTIONSメソッドを使用したプリフライトリクエストがブラウザから事前に送信されます。
