# JUnit

JUnitはjavaの単体テストを行うためのテスティングフレームワークです

アノテーションや検証機能を使って、テストコードを簡潔に記述することができます


アノテーション
```java

@Test
public void testMethod() {
    // テストコード
}

@BeforeEach
public void setUp() {
    // 各テストメソッド実行前に実行される
    // 各テスト共通の初期化処理をここに記述
    // 例: テスト対象のオブジェクトを初期化
    // 例: テストデータを準備
    // 例: モックオブジェクトを作成
}

@AfterEach
public void tearDown() {
    // 各テストメソッド実行後に実行される
    // 各テスト共通の後処理をここに記述
    // 例: テスト対象のオブジェクトをクリーンアップ
    // 例: テストデータを削除
    // 例: モックオブジェクトをクリーンアップ
}

@BeforeAll
public static void setUpAll() {
    // クラスの全テスト実行前に1回だけ実行される
    // 全テスト共通の初期化処理をここに記述
    // 例: テスト環境のセットアップ
}

@AfterAll
public static void tearDownAll() {
    // クラスの全テスト実行後に1回だけ実行される
    // 全テスト共通の後処理をここに記述
    // 例: テスト環境のクリーンアップ
}

// 記号含めて日本語で記載できるため、DisplayNameを使用するチームは多いです
@DisplayName("テストメソッドの説明")
@Test
public void testMethod() {
    // テストコード
}

@ParameterizedTest
@ValueSource(ints = {1, 2, 3})
public void testMethod(int value) {
    // valueには1, 2, 3が順番に渡される
    // テストコード
}

@ParameterizedTest
@MethodSource("dataProvider")
public void testMethod(int value) {
    // valueにはdataProviderメソッドから返された値が渡される
    // テストコード
}
private static Stream<Arguments> dataProvider() {
    return Stream.of(
        Arguments.of(1),
        Arguments.of(2),
        Arguments.of(3)
    );
}

@ParameterizedTest
@CsvSource({
    "1, 2, 3",
    "4, 5, 9"
})
public void testMethod(int a, int b, int expected) {
    // a, bにはそれぞれ1, 2, 4, 5が順番に渡される
    // expectedには3, 9が順番に渡される
    // テストコード
}

※ @~~Souceは他にも@EnumSourceや@NullAndEmptySourceなど便利なものが用意されています
```

検証メソッド
```java
Assetions.assertEquals(expected, actual);
Assetions.assertNotEquals(expected, actual);
Assetions.assertTrue(condition);
Assetions.assertFalse(condition);
Assetions.assertNull(object);
Assetions.assertNotNull(object);
Assetions.assertThrows(Exception.class, () -> {
    // 例外が発生する処理
});
```


テストの考え方
1. 初期化
2. 実行
3. 検証
4. 後処理

実装例
```java
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int divide(int a, int b) {
        if (b == 0) throw new IllegalArgumentException("Cannot divide by zero.");
        return a / b;
    }
}

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        // 各テストメソッド実行前に対象クラスのインスタンスを生成
        calculator = new Calculator();
    }

    @Test
    public void testAdd() {
        // 期待値
        int expected = 5;
        // 実行結果
        int result = calculator.add(2, 3);
        // 検証
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testDivide() {
        int expected = 3;
        int result = calculator.divide(6, 2);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testDivideByZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(6, 0);
        });
    }
}
```
