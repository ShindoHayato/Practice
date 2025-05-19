# SpringBoot

SpringBootは主にWEBアプリケーションを開発するためのフレームワークです。  
Springフレームワークをベースにしており、設定や依存関係の管理が簡単に行えるようになっています

アノテーションベースで設定を行います

SpringBootは、以下のような特徴を持っています。
- **自動設定**: SpringBootは、アプリケーションの依存関係に基づいて自動的に設定を行います。これにより、開発者は設定ファイルを手動で作成する必要がなくなります。
  - spring-boot-starter-xxxを追加することで、自動的に設定が行われます。
- **組み込みWebサーバー**: SpringBootは、TomcatやJettyなどの組み込みサーバーを提供しています。これにより、アプリケーションを簡単に実行できるようになります。
  - spring-boot-starter-webを追加することで、組み込みWebサーバーが利用可能になります。(デフォルトはTomcat)


### DI(Dependency Injection: 依存性注入)コンテナ
DI: クラスごとの結合度を下げる(疎結合にする)ために、クラスの依存関係を外部から注入する仕組み

```java
// 密結合なクラス
class UserService {
    private final UserRepository userRepository;

    public UserService() {
        // UserRepositoryのインスタンスを直接生成: 結合度が高い
        this.userRepository = new UserRepository();
    }
}

// 疎結合なクラス
class UserService {
    private final UserRepository userRepository;

    // 外からUserRepositoryのインスタンスを注入: 結合度が低い
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

// 疎結合なクラスでも、結局インスタンスを生成する必要がある
// UserRepositoryがinterfaceの場合であれば、ダミークラスを作成してテストすることができるのは利点
UserRepository userRepository = new UserRepository();
UserService userService = new UserService(userRepository);
```

DIコンテナは、インスタンスのライフサイクル(生成、初期化、破棄)を管理し、
インスタンス生成時に必要な依存関係を自動的に注入します

Springではアノテーションを使用してDIコンテナへの登録、インスタンスの注入を行います
```java
@Component // DIコンテナに登録するためのアノテーション
public class UserService {
    private final UserRepository userRepository;

    // コンストラクタに@Autowiredを付与することで、DIコンテナが自動的にインスタンスを注入
    // 引数をもつコンストラクタ１つのみのコンポーネントの場合、@Autowiredは省略可能
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

@Configuration // Bean定義クラスを示すアノテーション
public class AppConfig {
    @Bean // DIコンテナに登録するためのアノテーション
    public UserRepository userRepository() {
        return new UserRepository();
    }

    @Bean // DIコンテナに登録するためのアノテーション
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
```
DIコンテナのメリット
- インスタンスのライフサイクルを管理できる
- インスタンスのスコープを管理できる(singleton, prototype, request, session, application)
- 疎結合となるため、テストが容易になる


注意点
- Bean(DIコンテナに登録されたオブジェクト)はデフォルトでシングルトン
```java
// アンチパターン
@Service
public class UserService {
    // UserServiceインスタンスはシングルトン
  　// フィールドは共有されてしまう
    private String name;

    public void registerName(String name) {
        // 複数で同時にアクセスすると、nameが上書きされる可能性がある
        this.name = name;
        DB.save(name);
    }

}
```


基本コンポーネント
- @Controller: コントローラークラスを示すアノテーション
  - コントローラの役割は、リクエストを受け取り、パラメータの検証、レスポンスの生成を行うことです
- @Service: サービスクラスを示すアノテーション
  - ビジネスロジックのフローを定義する役割を持ちます
- @Repository: リポジトリクラスを示すアノテーション
  - 主にデータベースとのやり取りを行う役割を持ちます。データの永続化や取得を行います

上記３つはSpringの３層アーキテクチャにおける役割を示すためのアノテーションです
