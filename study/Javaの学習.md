# Javaの学習
## プリミティブ型とオブジェクト型
プリミティブ型は、int, long, short, byte, float, double, char, booleanの8種類 直接値を格納するため、メモリの使用量が少なく、処理速度が速い

オブジェクト型は、String, Integer, Double, Character, Booleanなどのクラス型 オブジェクトを生成して、そのオブジェクトの参照を格納するため、メモリの使用量が多く、処理速度が遅い

プリミティブ型が使える場面では、プリミティブ型を使う

プリミティブ型(intなど)のラッパー型(Integerなど)はnullを表現したいときなどに使うことがある

```
//　オートボクシング
Integer a = 100; // intをIntegerに変換
//　アンボクシング
int b = a; // Integerをintに変換
```

## if文について
```
int a = 100;
int b = 1oo;
if (a == b) -> true

int[] a = {1, 2, 3};
int[] b = {1, 2, 3};
int c = a;
if (a == b) -> false
if (a == c) -> true
```
プリミティブ型であれば値を比較するが、オブジェクト型であれば参照(同じオブジェクトかどうか)を比較する

オブジェクト型の比較はequalsメソッドを使用する
※ ただし、equalsメソッドをオーバーライドしていない場合は、デフォルトのequalsメソッドが使用されるため、参照を比較することになる
```
Integer a = 200;
Integer b = 200;
if (a == b) -> false
if (a.equals(b)) -> true

// 配列の場合、中身を比較するためにはArraysクラスのequalsメソッドを使用する
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};
if (Arrays.equals(a, b)) -> true

// 特殊なケース
// Integerはintのラッパークラスであり、デフォルトで-128から127までの値はキャッシュされているため、==で比較してもtrueになる
Integer c = 100;
Integer d = 100;
if (c == d) -> true
if (c.equals(d)) -> true
```

## for文について
```
for (int i = 0; i < 10; i++) {
    if (i == 5) {
        continue; // 5のときはスキップ
    }
    if (i == 8) {
        break; // 8のときはループを抜ける
    }
    System.out.println(i);
}

public int sum(int n){
    int sum = 0;
    for (int i = n; i <= 10; i++) {
        sum += i;
        if (sum > 20) {
            return sum; // 20を超えたらループを抜ける
        }
    }
    return sum;
}
```
