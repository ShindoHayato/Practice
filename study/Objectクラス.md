# Objectクラス

Objectクラスは全てのクラスの親クラスとして、Javaの基本的な機能を提供します。  
Ojbectクラスがもつメソッドをオーバーライドすることで、クラスの動作をカスタマイズします。

- https://docs.oracle.com/javase/jp/17/docs/api/java.base/java/lang/Object.html
- https://docs.oracle.com/javase/tutorial/java/IandI/objectclass.html


### 主要なメソッド
- `public boolean equals(Object obj)`
オブジェクトの等価性を比較するメソッド。デフォルトでは、オブジェクトの参照が同じかどうかを比較します。

コレクションオブジェクトで、引数に渡したオブジェクトと同じかどうかを比較する場合は、equalsメソッドをオーバーライドする必要があります。 
list.containsメソッドなどで、内部ではequalsメソッドが呼び出されます。

他にはjunitのassertEqualsメソッドなどでも、equalsメソッドが呼び出されます。

```java

// equalsメソッドをオーバーライドしていないクラス
NotOverrideClass obj1 = new NotOverrideClass();
NotOverrideClass obj2 = new NotOverrideClass();
if (obj1.equals(obj2)) {
    // 等しくないため実行されない
    System.out.println("obj1とobj2は等しい");
}
```
```java
public class OverrideClass {
    String name;
    String address;
    
    public OverrideClass(String name, String address) {
        this.name = name;
        this.address = address;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OverrideClass)) {
            return false;
        }
        
        OverrideClass that = (OverrideClass) obj;
        return this.name.equals(that.name) && this.address.equals(that.address);
    }
}

OverrideClass obj1 = new OverrideClass("John", "Tokyo");
OverrideClass obj2 = new OverrideClass("John", "Tokyo");
if (obj1.equals(obj2)) {
    // 等しいため実行される
    System.out.println("obj1とobj2は等しい");
}

```


##### `public int hashCode()`  
オブジェクトのハッシュコードを返すメソッド。  
等価なオブジェクトは同じハッシュコードを持たなければならない(Javaの規約)ため、
equalsメソッドをオーバーライドした場合は、hashCodeメソッドもオーバーライドする必要があります。

HashMapやHashSetなどのコレクションで、オブジェクトを格納・検索する際にハッシュコードを使用します。

```java

public class OverrideClass {
    String name;
    String address;
    
    public OverrideClass(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // equalsメソッドをオーバーライド
    // ...
    
    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}
```


##### `public String toString()`  
オブジェクトの文字列表現を返すメソッド。デフォルトでは、クラス名とハッシュコードを含む文字列を返します。

System.out.printlnメソッドでオブジェクトを出力する際、
+演算子で文字列とオブジェクトを結合する際など、toStringメソッドの呼び出しは多くあります。

```java
public class OverrideClass {
    String name;
    String address;
    
    public OverrideClass(String name, String address) {
        this.name = name;
        this.address = address;
    }
    
    @Override
    public String toString() {
        return "OverrideClass{name='" + name + "', address='" + address + "'}";
    }
}
```


##### `protected Object clone()`  
オブジェクトの複製を作成するメソッド。デフォルトでは、シャロークローンを作成します。

シャロークローン: オブジェクトのフィールドをそのままコピーすること。オブジェクトの参照は同じまま。

ディープクローン: オブジェクトのフィールドを新しいオブジェクトとしてコピーすること。オブジェクトの参照は異なる。



### Lombok
Lombokを使用すると、equalsメソッドやhashCodeメソッド、toStringメソッドを自動生成できます。  
上記のメソッドを手動で実装するのは大変なので、javaでの開発ではLombokを使用することが一般的です。

- https://projectlombok.org/features/

```java
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Getter;
import lombok.AllArgsConstructor;

@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public class LombokClass {
    private String name;
    private String address;
}
```
