# staticはクラスに属する

staticはクラスに属するので、インスタンスを生成しなくてもアクセスできる

```java
public class StaticSample {
    // staticフィールド
    private static int staticField = 0;
    // static定数
    public static final int STATIC_CONSTANT = 100;

    // インスタンスフィールド
    int instanceField = 0;

    // staticイニシャライザ
    static {
        System.out.println("static initializer");
    }
    
    // staticメソッド
    static void staticMethod() {
        staticField++;
        // instanceField++; // staticメソッド内ではインスタンスフィールドにアクセスできない
        System.out.println("static method");
    }

    // インスタンスメソッド
    void instanceMethod() {
        staticField++;
        instanceField++;
        System.out.println("instance method");
    }
    
    static class StaticInnerClass {
        // static内部クラス
        void innerMethod() {
            System.out.println("static inner class method");
        }
    }
    
    class InnerClass {
        // 非static内部クラス
        void innerMethod() {
            System.out.println("inner class method");
        }
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        // staticメソッドの呼び出し
        StaticSample.staticMethod();

        // インスタンスメソッドの呼び出し
        StaticSample sample = new StaticSample();
        sample.instanceMethod();
        // 内部クラスのインスタンス化
        StaticSample.InnerClass inner = sample.new InnerClass();
        inner.innerMethod();
        
        // static内部クラスのインスタンス化
        StaticSample.StaticInnerClass innerStatic = new StaticSample.StaticInnerClass();
        innerStatic.innerMethod();

        // staticフィールドのアクセス
        System.out.println("Static field: " + StaticSample.staticField);

        // インスタンスフィールドのアクセス
        System.out.println("Instance field: " + sample.instanceField);
    }
}
```

- クラスに紐づく定数はstatic finalで定義する
- クラス全体で共通の値を持つフィールドはstaticで定義する
- なんでもかんでもstaticにするのは良くない(クラスに紐づくかインスタンスに紐づくかを考える)
- utilityクラスはstaticメソッドを持つことが多い
  - https://guava.dev/releases/19.0/api/docs/com/google/common/base/Strings.html
  - https://github.com/google/guava/blob/master/guava/src/com/google/common/base/Strings.java
