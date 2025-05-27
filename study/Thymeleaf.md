# thymeleaf

thymeleafはテンプレートエンジンといわれるもので、主にHTMLにサーバ側のデータを埋め込むために使用されます

SpringBootではspring-boot-starter-thymeleafを依存関係に追加することで使用できます

https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf_ja.html

```java
@Controller
public class ThymeleafController {

    @GetMapping("/hello")
    public String hello(Model model) {
        // Modelオブジェクトにデータを追加
        model.addAttribute("message", "Hello, Thymeleaf!");
        model.addAttribute("items", List.of("Item 1", "Item 2", "Item 3"));
        return "hello"; // hello.htmlを返す
    }
}
```

```html
<!DOCTYPE html>

<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Thymeleaf Example</title>
    <meta charset="UTF-8">
    <!-- resources/static/css/style.cssを読み込む -->
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
    <!-- Modelからのデータを表示 -->
    <h1 th:text="${message}">Default Message</h1>
    <p>Thymeleafを使ったサンプルページです。</p>
    <!-- messageがnullでない場合に表示 -->
    <p th:if="${message != null}">メッセージ: <span th:text="${message}"></span></p>
    <!-- messageがnullの場合に表示 -->
    <p th:unless="${message != null}">メッセージは設定されていません。</p>
    <!-- コンテキストパスを考慮し、Controllerの/submitにPOSTリクエストを送るフォーム -->
    <form th:action="@{/submit}" method="post">
        <input type="text" name="name" placeholder="名前を入力してください">
        <button type="submit">送信</button>
    </form>
    <ul>
        <!-- リスト分li要素を生成 -->
        <li th:each="item : ${items}" th:text="${item}">Item</li>
    </ul>
</body>
```
