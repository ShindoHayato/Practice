# MyBatis

https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/

mybatis-spring-boot-starterを依存関係に追加することで使用できます。

```gradle
dependencies {
  // バージョンの指定が必要
  implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4'
}
```

### MyBatisの設定
最低限必要なものを記載します

application.yml
```yaml
mybatis:
  # Mapper XML の配置場所（classpathベース）src/main/resources配下の.xmlファイルをみる
  mapper-locations: classpath:/mappers/*.xml
```

Mapperインターフェイス
```java
@Mapper
public interface UserMapper {
    
    User findById(int id);
    void insert(User user);
}
```

Mapper XML src/main/resources/mappers/UserMapper.xmlに作成する
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 上部は固定タグ -->

<!-- 対応するMapperインターフェイスの名前空間 -->
<mapper namespace="com.example.demo.repository.mapper.UserMapper">

    <!-- idはMapperインターフェイスのメソッド名と一致させる -->
    <!-- resultMapは結果のマッピングを定義 resultMapタグのidに一致 -->
    <!-- #{id}はMapperインターフェイスのメソッドの引数 -->
    <select id="findById" resultMap="UserResult">
        SELECT
            id,
            mail,
            password,
            roles,
            created,
            last_logined,
            enabled
        FROM `user`
        WHERE `user`.id = #{id}
    </select>

    <!-- typeはMapperインターフェイスの戻り値の型 -->
    <!-- propertyはエンティティのフィールド名、columnはDBのカラム名 -->
    <resultMap id="UserResult" type="com.example.demo.entity.User">
        <id property="id" column="id"/>
        <result property="mail" column="mail"/>
        <result property="password" column="password"/>
        <result property="roles" column="roles"/>
        <result property="created" column="created"/>
        <result property="lastLogined" column="last_logined"/>
        <result property="enabled" column="enabled"/>
    </resultMap>

    <!-- idはMapperインターフェイスのメソッド名と一致させる -->
    <!-- #{user}はMapperインターフェイスのメソッドの引数のオブジェクト -->
    <insert id="insert">
        INSERT INTO `user`
        (
            mail,
            password,
            roles,
            created,
            last_logined,
            enabled
        )
        VALUES
        (
            #{user.mail},
            #{user.password},
            #{user.roles},
            #(user.created),
            #{user.lastLogined},
            #{user.enabled}
        )
    </insert>
</mapper>
```

Repository実装
```java
@Repository
@RequiredArgsConstructor
public class UserRepository {

    // MapperインターフェイスをDIで注入
    private final UserMapper userMapper;

    public User findById(int id) {
        return userMapper.findById(id);
    }

    public void insert(@Param("user") User user) {
        userMapper.insert(user);
    }
}
```

## おまけ
MyBatis Generatorを使うと、DBのテーブル定義から自動的にMapperインターフェイスやXMLを生成できます。  
※ 使っている現場もあるそうです
