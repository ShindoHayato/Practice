# Controllerテスト

SpringBootでのControllerのテストは一般的には`@WebMvcTest`, MockMvcクラスを利用して行います

MockMvcRequestBuildersを利用することで、HTTPリクエストをモックし、
MockMvcResultMatchersを利用して、レスポンスの検証を行います。

```java
@WebMvcTest
public class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MyService myService;

    // 画面を返すControllerのテスト
    @Test
    public void testGetMyEntity() throws Exception {
        // モックの設定
        Mockito.when(myService.getMyEntity(1)).thenReturn(new MyEntity(1, "test"));

        // GETリクエストの実行とレスポンスの検証
        mockMvc.perform(MockMvcRequestBuilders.get("/my-entity/1"))
                // レスポンスが200 OKであることを検証
                .andExpect(MockMvcResultMatchers.status().isOk())
                // ビュー名が"myEntityView"であることを検証
                .andExpect(MockMvcResultMatchers.view().name("myEntityView"))
                // modelに"myEntity"という属性が存在し、その値がMyEntity(1, "test")であることを検証
                .andExpect(MockMvcResultMatchers.model().attribute("myEntity", new MyEntity(1, "test")));
        
        Mockito.verify(myService).getMyEntity(1);
    }
    
    // REST APIを返すControllerのテスト
    @Test
    public void testPostMyEntity() throws Exception {
        // モックの設定
        MyEntity newEntity = new MyEntity(2, "new entity");
        Mockito.when(myService.createMyEntity(Mockito.any(MyEntity.class))).thenReturn(newEntity);

        // POSTリクエストの実行とレスポンスの検証
        mockMvc.perform(MockMvcRequestBuilders.post("/my-entity")
                        // リクエストボディにJSON形式のデータを設定
                        .contentType(MediaType.APPLICATION_JSON)
                        // リクエストボディの内容を設定
                        .content("{\"id\":2,\"name\":\"new entity\"}"))
                // レスポンスが201 Createdであることを検証
                .andExpect(MockMvcResultMatchers.status().isCreated())
                // レスポンスのContent-Typeがapplication/jsonであることを検証
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // レスポンスボディのJSONを検証
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("new entity"));

        Mockito.verify(myService).createMyEntity(Mockito.any(MyEntity.class));
    }
}
```
