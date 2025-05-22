# Configuration

アプリケーションの複数箇所で使用するBean(DIコンテナに入れたいオブジェクト)を定義したい場合、`@Configuration`を使用してBeanを定義します

例えば、時刻を統一するためのClock定義や、データソースの設定、HttpClientの設定など
他にもシングルトンとして扱いたいオブジェクトを定義することもあります

```java
@Configuration
public class AppConfig {
    
    @Bean
    public Clock clock() {
        // Asia/TokyoのタイムゾーンのClockを返す
        return Clock.system(ZoneId.of("Asia/Tokyo"));
    }
    
    @Bean
    public RestTemplate restTemplate() {
        // RestTemplateというHttpClientのBeanを定義
        // RestTemplateBuilderを使用して、接続タイムアウトと読み取りタイムアウトを設定
        return RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        // ObjectMapperのBeanを定義
        // JacksonのObjectMapperを使用して、JSONのシリアライズとデシリアライズを行う
        // Java 8以降のDate/Time APIを使用するために、JavaTimeModuleを登録
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
```
