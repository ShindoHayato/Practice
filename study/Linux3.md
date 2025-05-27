# Linux

## ネットワーク管理


ping: サーバーへの到達性を確認するコマンド。ICMPパケットを送信し、応答を受信する
```
ping compass-japan03.com
# 3回送信して応答を待つ
ping -c 3 compass-japan03.com
```

TCP/UDP
- TCP: 信頼性のある通信。接続管理や再送制御や確認応答を行う
- UDP: 信頼性のない通信。TCPのような処理を行わず、データを送信する


IPアドレス
Ipv4とIpv6があります。Ipv4は32ビット、Ipv6は128ビットのアドレスです。  
例：  
v4: 192.168.0.1  
v6: 2001:0db8:85a3:0000:0000:8a2e:0370:7334

プライベートIPアドレス
特定の範囲のIPアドレスはプライベートIPアドレスとして定義されています。
* 10.0.0.0 ~ 10.255.255.255: 10.0.0.0/8
* 172.16.0.0 ~ 172.31.255.255: 172.16.0.0/12
* 192.168.0.0 ~ 192.168.255.255: 192.168.0.0/16


traceroute: パケットが通過する経路を表示
```
traceroute compass-japan03.com

# 結果
traceroute to compass-japan03.com (219.94.162.237), 64 hops max, 52 byte packets
 1  192.168.0.1 (192.168.0.1)  3.590 ms  2.377 ms  2.277 ms
 2  ...省略...

```


NIC:ネットワークインターフェースカードの略。
ネットワーク機器とコンピュータを接続するためのインターフェース。

ip a: ネットワークインターフェースの情報を表示
```
ip a

# 結果
# lo0: ループバックインターフェース
lo0: flags=8049<UP,LOOPBACK,RUNNING,MULTICAST> mtu 16384
        inet 127.0.0.1/8 lo0
        inet6 ::1/128
        inet6 fe80::1/64 scopeid 0x1
en3: ...省略...
```
※ ループバックインターフェイスはローカルで通信を確認するための仮想ネットワークインターフェイス


ip route: ルーティングテーブルを表示(どこへパケットを送信するかの情報)
```
ip route

# 結果
default via 192.168.0.1 dev en0
127.0.0.0/8 via 127.0.0.1 dev lo0
127.0.0.1/32 via 127.0.0.1 dev lo0
169.254.0.0/16 dev en0  scope link
192.168.0.0/24 dev en0  scope link
192.168.0.1/32 dev en0  scope link
192.168.0.3/32 dev en0  scope link
224.0.0.0/4 dev en0  scope link
255.255.255.255/32 dev en0  scope link
```

DNS: ドメイン名をIPアドレスに変換するシステム(Domain Name System)  
例：compass-japan03.comを192.168.5.5に変換する

どのDNSサーバにアクセスするかは、Linux/Macでは/etc/resolv.conf、WindowsではDNS設定で確認できます

ホストファイル: ドメイン名とIPアドレスの対応を記述したファイル  
windows: C:\Windows\System32\drivers\etc\hosts  
macOS, linux: /etc/hosts
```
127.0.0.1       localhost # localhostを127.0.0.1に紐づける
255.255.255.255 broadcasthost # broadcasthostを255.255.255.255に紐づける
```


nsllokup: ドメイン名のIPアドレスを調べる
```
nslookup compass-japan03.com

# 結果
Server:         2001:268:fd07:4::1
Address:        2001:268:fd07:4::1#53

Non-authoritative answer:
Name:   compass-japan03.com
Address: 219.94.162.237
```

FQDN(Fully Qualified Domain Name): ホスト名とドメイン名を組み合わせたもの

ポート番号: ネットワーク上のソケットを識別する番号

外部との通信をするための窓口と考えるとわかりやすいです

| ポート番号範囲 | 用途                      |
| --- |-------------------------|
| 0 ~ 1023 | システムポート/WELL KNOWN PORT |
| 1024 ~ 49151 | ユーザポート/REGISTERED PORT  |
| 49152 ~ 65535 | ダイナミックポート               |

主なポート番号
| ポート番号 | 用途 |
| --- | --- |
| 20 | FTPデータポート |
| 21 | FTP制御ポート |
| 22 | SSH |
| 23 | Telnet |
| 25 | SMTP |
| 53 | DNS |
| 80 | HTTP |
| 110 | POP3 |
| 143 | IMAP |
| 443 | HTTPS |
| 993 | IMAPS |
| 995 | POP3S |


## プロセス管理

Linux では実行中のプログラム（アプリケーション）を管理する単位をプロセスと呼びます

```
# よく使うプロセス表示
# a:全てのユーザのプロセスを表示、u:プロセスのリソース情報も表示、x:端末に紐づかないプロセスも表示
ps aux 

# psコマンドでPIDを確認して、killコマンドでプロセスを終了させる
# プロセスの強制終了
kill -9 <PID>
```

```
# プロセスの状態をリアルタイムで表示
top
```


```
# バックグランドでの実行
# 10秒間のスリープをバックグラウンドで実行
sleep 30 &

# ジョブの一覧表示
jobs
# 結果
[1]+  12345 Stopped                 sleep 30

# CTRL+Zでジョブを停止後バックグランドで実行させる
bg

# ジョブをフォアグラウンドで実行
# %1はジョブ番号。jobsコマンドで確認できる
fg %1 
```

## 他

```
# ディスクの使用量を表示
# h: 人間が読みやすい形式で表示
df -h

# ディレクトリの使用量を表示
# h: 人間が読みやすい形式で表示
du -h

# メモリの使用量を表示
free -h
```
