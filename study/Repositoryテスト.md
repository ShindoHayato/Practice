# Repositoryテスト
Repositoryのテストは、実際にインメモリのデータベースなどを使用して実施します。

データベースを使うためDIコンテナの起動が必要になります。そのため、
`@SpringBootTest`を利用してテストを実行します。  
※ MyBatisでは`@MyBatisTest`を使用してRepositoryのテストも可能です。

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
テスト実行時はtestディレクトリ下のresourcesを参照するので、`src/test/resources`にSQLファイルを配置が必要になります。

また、プロパティ設定もsrc/test/resources下のapplication.yml(もしくはproperties)を参照するため、
データベースの設定は、テスト用に別途必要になります。
