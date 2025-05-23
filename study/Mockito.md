# Mockito

Mockitoは、Javaのモックライブラリであり、テストのためにオブジェクトをモック化し、
オブジェクトの振る舞いを変更したり、呼び出しを検証することができます。

Mock (完全なモック化)
```java
public class MockitoTest {
    @Test
    public void testMockito() {
        // モックオブジェクトの作成
        List<String> mockedList = Mockito.mock(List.class);

        // モックオブジェクトの振る舞いを定義
        Mockito.when(mockedList.get(0)).thenReturn("Hello, World!");
        // モックオブジェクトの振る舞いを定義2
        Mockito.doReturn("Hello, World2!").when(mockedList).get(0);
        // モックオブジェクトの振る舞いを定義 例外
        Mockito.doThrow(new RuntimeException("Error!")).when(mockedList).get(0);
        // モックオブジェクトの振る舞いを定義 何もしない 戻り値がvoidの場合に使用
        Mockito.doNothing().when(mockedList).clear();

        // モックオブジェクトのメソッドを呼び出す
        String result = mockedList.get(0);
        mockedList.add("Hello, World!");

        // 結果の検証
        assertEquals("Hello, World!", result);

        // モックオブジェクトのメソッドが呼び出されたかどうかを検証
        Mockito.verify(mockedList).get(0);
    }
}

// アノテーションを使用した場合
@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    @Mock
    List<String> mockedList;

    @Test
    public void testMockito() {
        // モックオブジェクトの作成
        // List<String> mockedList = Mockito.mock(List.class);

        // モックオブジェクトの振る舞いを定義
        Mockito.when(mockedList.get(0)).thenReturn("Hello, World!");

        // モックオブジェクトのメソッドを呼び出す
        String result = mockedList.get(0);

        // 結果の検証
        assertEquals("Hello, World!", result);

        // モックオブジェクトのメソッドが呼び出されたかどうかを検証
        Mockito.verify(mockedList).get(0);
    }
}
```

Spy (一部のモック化)
```java
public class MockitoTest {
    @Test
    public void testMockito() {
        // スパイオブジェクトの作成
        Point point = Mockito.spy(Point.class);

        // スパイオブジェクトの振る舞いを定義
        Mockito.when(point.getX()).thenReturn(10.0);
        // リアルオブジェクトのメソッドを呼び出す
        point.move(1,2);

        // moveメソッドはリアルオブジェクトのメソッドが呼び出されるので、X,Yは1,2になる
        // ただし、getXメソッドはモック化されているので、10.0が返る
        assertEquals(10.0, point.getX());
        assertEquals(2, point.getY());
    }
}

// アノテーションを使用した場合
public class MockitoTest {

    @Spy
    List<String> realList;

    @Test
    public void testMockito() {
        // スパイオブジェクトの作成
        // List<String> spyList = Mockito.spy(realList);

        // スパイオブジェクトの振る舞いを定義
        Mockito.doReturn("Hello, World!").when(realList).get(0);

        // スパイオブジェクトのメソッドを呼び出す
        String result = realList.get(0);
        realList.add("Hello, World!"); // 追加可能

        // 結果の検証
        assertEquals("Hello, World!", result);

        // スパイオブジェクトのメソッドが呼び出されたかどうかを検証
        Mockito.verify(realList).get(0);
    }
}
```

InjectMocks (依存関係の注入)
```java
@ExtendWith(MockitoExtension.class)
public class MockitoTest {
    @InjectMocks
    private MyService myService;

    @Mock
    private MyRepository myRepository;

    @Test
    public void testMockito() {
        // モックオブジェクトの振る舞いを定義
        Mockito.when(myRepository.findById(1)).thenReturn(new MyEntity(1, "Hello, World!"));

        // サービスメソッドを呼び出す
        MyEntity result = myService.getEntityById(1);

        // 結果の検証
        assertEquals("Hello, World!", result.getName());
    }
}

class MyService {
    private final MyRepository myRepository;

    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public MyEntity getEntityById(int id) {
        return myRepository.findById(id);
    }
}

class MyRepository {
    public MyEntity findById(int id) {
        // データベースからエンティティを取得する処理
        return new MyEntity(id, "Real Entity");
    }
}

class MyEntity {
    private final int id;
    private final String name;

    public MyEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```

staticmock
```java
public class MockitoTest {
    @Test
    public void testStaticMock() {
        // スタティックメソッドのモック化
        try(MockedStatic<MyStaticClass> mockedStatic = Mockito.mockStatic(MyStaticClass.class)) {
            // スタティックメソッドの振る舞いを定義
            mockedStatic.when(MyStaticClass::staticMethod).thenReturn("Mocked Value");
            mockedStatic.when(MyStaticClass::staticMethod).thenThrow(new RuntimeException("Error!"));

            // スタティックメソッドを呼び出す
            String result = MyStaticClass.staticMethod();

            // 結果の検証
            assertEquals("Mocked Value", result);
        }
    }
}

class MyStaticClass {
    public static String staticMethod() {
        return "Real Value";
    }
}
```
