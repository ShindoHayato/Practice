# SpringBootテスト

SpringBoot開発でのテストは、いくつかの方法があります。

## SpringBoot Test
クラスに`@SpringBootTest`を付与することで、SpringBootのテストが可能になります。

実際にアプリケーションを起動し、DIコンテナを使用して、Beanのインジェクションが実行されます。
実行時間は長くなりますが、実際のアプリケーションに近い形でテストを行うことができます。
※ 全てのテストで`@SpringBootTest`を利用すると、
システム開発では全てのテストを実行するのに数十分かかってしまうこともありうるので、
必要なテストでのみ利用すること

`@Autowired`を利用してBeanのインジェクション、`@MockitoBean`を利用してモックのインジェクションが可能です。
```java
@SpringBootTest
public class MyServiceTest {

    @Autowired
    private MyService myService;

    @MockitoBean
    private MyRepository myRepository;

    @Test
    public void testMyMethod() {
        // モックの設定
        when(myRepository.findById(1)).thenReturn(new MyEntity(1, "test"));

        // テスト対象のメソッドを実行
        MyEntity result = myService.myMethod(1);

        // 結果の検証
        assertEquals(1, result.getId());
        assertEquals("test", result.getName());
        verify(myRepository).findById(1);
    }
}

```

## Mvc Test
MVCテストに関する部分のみ(Controller, ControllerAdvice, Filter, HandlerInterceptorなど)をテストすることができ、
主にControllerのテストを行う場合に利用します。

`@WebMvcTest`を付与することで、MVCに関する部分のみを起動してテストを可能です。  
※もしくは`@SpringBootTest` + `@AutoConfigureMockMvc`、`MockMvc`クラス、MockMvcBuildersを利用する方法もあります

```java
@WebMvcTest(MyController.class)
public class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MyService myService;

    @Test
    public void testGetMyEntity() throws Exception {
        // モックの設定
        when(myService.getMyEntity(1)).thenReturn(new MyEntity(1, "test"));

        // GETリクエストの実行
        mockMvc.perform(get("/my-entity/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("test"));

        // モックの検証
        verify(myService).getMyEntity(1);
    }
}

```

## JUnitのみ
ServiceやUtility、Domainクラスなどのテストを行う場合は、JUnitのみでテストを行うことができます。  
※ Mockitoを利用して、外部の依存関係をモック化することも可能です。
```java
@ExtendWith(MockitoExtension.class)
public class MyServiceTest {

    @Mock
    private MyRepository myRepository;
    
    @InjectMocks
    private MyService myService;
    
    @Test
    public void testMyMethod() {
        // モックの設定
        when(myRepository.findById(1)).thenReturn(new MyEntity(1, "test"));

        // テスト対象のメソッドを実行
        MyEntity result = myService.myMethod(1);

        // 結果の検証
        assertEquals(1, result.getId());
        assertEquals("test", result.getName());
        Mockito.verify(myRepository).findById(1);
    }
}
```

## Repository Test
Repositoryのテストは、実際にインメモリのデータベースなどを使用して実施します  
MyBatisを使用していれば`@MyBatisTest`、JPAを使用していれば`@DataJpaTest`を付与することで、Repositoryのテストが可能になります。  
※ @SpringBootTestを利用することもできます

SpringBootTestの例 ※MyBatisは未検証のため
```java
@SpringBootTest
@Sql(value = "/test-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MERGE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql(value = "/test-data-findById.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testFindById() {
        User user = userRepository.findById(1);
        User expected = new User(1, "Bob");
        Assertions.assertEquals(expected, user);
    }
```

## HTTP Client Test
HTTPクライアントのテストとしては、Mockサーバーのライブラリを使用して、テストを行うことができます。
okhttpのMockWebServerなどが利用されます。

```java
@SpringBootTest
public class MyHttpClientTest {

    @Autowired
    private MyHttpClient myHttpClient;

    private static MockWebServer mockWebServer;
    
    @BeforeAll
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }
    
    @AfterAll
    public static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }
    
    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(myHttpClient, "baseUrl", mockWebServer.url("/").toString() + mockWebServer.getPort());
    }
    
    @Test
    public void testGetRequest() throws Exception {
        // Mockサーバーにレスポンスを設定
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"id\":1,\"name\":\"test\"}"));

        // HTTPクライアントのメソッドを呼び出す
        MyEntity entity = myHttpClient.getMyEntity(1);

        // レスポンスの検証
        assertEquals(1, entity.getId());
        assertEquals("test", entity.getName());

        // Mockサーバーのリクエストを検証
        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("GET", request.getMethod());
        assertEquals("/my-entity/1", request.getPath());
}
```

## その他
validationのテスト

```java
public class User {
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    // Getters and Setters
}

public class ValidationTest {
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidUser() {
        User user = new User("", "a@com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty());
}
```

filterのテスト

```java
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // フィルターの処理
        response.setContentType("application/json");
        
        chain.doFilter(request, response);
    }
}

public class MyFilterTest {
    @Test
    public void testFilter() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        MyFilter filter = new MyFilter();
        filter.doFilter(request, response, chain);

        assertEquals("application/json", response.getContentType());
        verify(chain).doFilter(request, response);
    }
}
```
