# JUnit2

今回はテストについての話になります

正常系と異常系  
※準正常系(正しく例外が発生しているか)という区分もある

テストでは入力と出力に注目する　※そこに状態の考慮が追加される

JUnitでの単体テストの場合はテストはメソッド単位  
入力はメソッドの引数。出力はメソッドの戻り値。状態は対象クラスのフィールドの値

jacocoなどのツールでカバレッジを測定し、現場によってはX%も目標などもあります。  
ただし、カバレッジはあくまで目安であり、カバレッジが高いからといってテストが十分であるとは言えません。
※ そこは人間の判断が必要

```java
// 延滞料金を表すクラス
public class FineAmount {
    private final int yen;
    private static final int MAX_CAP = 50000;

    public  FineAmount(int yen) {
        // 引数の値が0以上かつMAX_CAP以下であることを確認
        //// 異常系： -1, 5001、正常系：0 or 50000 の境界値を確認
        if (yen < 0 || yen > MAX_CAP) {
            throw new IllegalArgumentException("延滞料金は0～" + MAX_CAP + "円の範囲で設定してください");
        }
        this.yen = yen;
    }
    
    /** 日額×日数で計算 */
    // 計算処理があるのでテストが必要
    // 正常系：引数がそれぞれ0のケース、０以外のケースを１つ
    // 異常系：引数がそれぞれ負の値のケース
    // intの範囲を超える計算になることは現実的にありえないので、
    public FineAmount calculate(int dailyYen, int days) {
        if (dailyYen < 0 || days < 0) {
            throw new IllegalArgumentException("日額と日数は0以上である必要があります");
        }
        return new FineAmount(dailyYen * days);
    }

    /** 延滞料金(円)を減額 */
    // 正常系：引数が0以外のケースを１つ、totalが0未満になるケースを１つ
    // 異常系：引数が負の値のケース
    public FineAmount subtract(int reductionYen) {
        if (reductionYen < 0) {
            throw new IllegalArgumentException("減額料金は0円以上で設定してください");
        }
        int total = yen - reductionYen;
        if (total < 0) {
            total = 0; // 減額後が0未満にならないようにする
        }
        return new FineAmount(total);
    }

    // Getterはテストの対象外
    public int getYen() {
        return yen;
    }
}
```
```java
class FineAmountTest {
    
    @ParameterizedTest("constructorNormalTest: {0}円")
    @CsvSource({
        "0", // 0円
        "50000", // 最大値
    })
    void constructorNormalTest(int yen) {
        FineAmount fineAmount = new FineAmount(yen);
        assertEquals(yen, fineAmount.getYen());
    }
    
    @ParameterizedTest("constructorErrorTest: {0}円")
    @CsvSource({
        "-1", // 負の値
        "50001", // 最大値を超える値
    })
    void constructorErrorTest(int yen) {
        assertThrows(IllegalArgumentException.class, () -> new FineAmount(yen));
    }
    
    @ParameterizedTest("calculateNormalTest: {0}円/日, {1}日, 期待値: {2}円")
    @CsvSource({
        "0, 0, 0", // 日額0円、日数0日
        "100, 3, 300", // 日額100円、日数3日
    })
    void calculateNormalTest(int dailyYen, int days, int expectedYen) {
        FineAmount fineAmount = new FineAmount(0); // 初期値は0円
        assertEquals(expectedYen, fineAmount.calculate(dailyYen, days).getYen());
    }

    @ParameterizedTest("calculateErrorTest: {0}円/日, {1}日")
    @CsvSource({
        "-1, 1", // 日額が負の値
        "1, -1", // 日数が負の値
    })
    void calculateErrorTest(int dailyYen, int days) {
        FineAmount fineAmount = new FineAmount(0); // 初期値は0円
        assertThrows(IllegalArgumentException.class, () -> fineAmount.calculate(dailyYen, days));
    }
    
    @ParameterizedTest("subtractNormalTest: {0} {1}円")
    @CsvSource({
        "延滞料金の残る減額, 1000, 200, 800", // 減額200円
        "0円未満となる減額, 1000, 2000, 0", // 減額2000円
    })
    void subtractNormalTest(String testName, int baseYen, int reductionYen, int expectedYen) {
        FineAmount base = new FineAmount(baseYen);
        assertEquals(expectedYen, base.subtract(reductionYen).getYen());
    }
    
    @Test
    @DisplayName("subtractErrorTest: 負の値の減額")
    void subtractErrorTest() {
        FineAmount base = new FineAmount(1000);
        assertThrows(IllegalArgumentException.class, () -> base.subtract(-100));
    }
}
```

### カバレッジ

jacocoの導入

springbootでGradleを使用している場合は、以下のように設定します。
```groovy
// pluginsにjacocoを追加
plugins {
    id 'jacoco'
}

// jacocoTestReportタスクを、testタスクの後に実行するように設定
// テスト実行後に毎回レポートを生成する
tasks.named('test') {
    useJUnitPlatform()
    finalizedBy jacocoTestReport
}
```

デフォルトではbuild/reports/jacoco/test/html/index.htmlを参照することでレポートを確認できます

jacocoTestReportタスクを実行することで、テストのカバレッジレポートを生成できます。

https://docs.gradle.org/current/userguide/jacoco_plugin.html
