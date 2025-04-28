package com.example.practice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TagCollectionTest {
    @Test   //コンストラクタの正常系
    void normalTagsTest() {
        TagCollection tags = new TagCollection(List.of("red", "blue", "green", " blue "));
        List<String> result = tags.getTags();
        assertEquals(List.of("red", "blue", "green"), result);
    }

    @Test   //コンストラクタの異常系（nullリスト）
    void nullListExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new TagCollection(null));
    }

    @Test   //コンストラクタの異常系（nullタグ含む）
    void containsNullTagExceptionTest() {
        assertThrows(NullPointerException.class, () -> new TagCollection(List.of("red", null, "green")));
    }

    @Test   // addメソッドの正常系（新規追加）
    void normalAddNewTagTest() {
        TagCollection original = new TagCollection(List.of("red"));
        TagCollection added = original.add("blue");

        assertNotSame(original, added);
        assertEquals(List.of("red", "blue"), added.getTags());
    }

    @Test   // addメソッドの異常系（空白文字）
    void addBlankTagExceptionTest() {
        TagCollection original = new TagCollection(List.of("red"));
        assertThrows(IllegalArgumentException.class, () -> original.add("   "));
    }

    @Test   // addメソッドの既存タグ → 変更なし
    void addExistingTagTest() {
        TagCollection tags = new TagCollection(List.of("red"));
        TagCollection result = tags.add("red");
        assertSame(tags, result);
    }

    @Test   // removeメソッドの正常系（存在するタグ削除）
    void removeExistingTagTest() {
        TagCollection tags = new TagCollection(List.of("red", "blue", "green"));
        TagCollection result = tags.remove("red");

        assertEquals(List.of("blue", "green"), result.getTags());
        assertNotSame(tags, result);
    }

    @Test   // removeメソッドの存在しないタグ → 変更なし
    void removeNonExistentTagTest() {
        TagCollection tags = new TagCollection(List.of("red", "blue", "green"));
        TagCollection result = tags.remove("lemon");

        assertSame(tags, result);
    }

    @Test   // removeメソッドの異常系（nullタグ）
    void removeNullTagTest() {
        TagCollection tags = new TagCollection(List.of("red"));
        assertThrows(IllegalArgumentException.class, () -> tags.remove(null));
    }
}
