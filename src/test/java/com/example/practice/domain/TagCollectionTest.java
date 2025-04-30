package com.example.practice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TagCollectionTest {
    @Test
    @DisplayName("コンストラクタの正常系")
    void normalTagsTest() {
        TagCollection tags = new TagCollection(List.of("red", "blue", "green", " blue "));
        List<String> result = tags.getTags();
        assertEquals(List.of("red", "blue", "green"), result);
    }

    @Test
    @DisplayName("コンストラクタの異常系（nullリスト）")
    void nullListExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new TagCollection(null));
    }

    @Test
    @DisplayName("コンストラクタの異常系（nullタグ含む）")
    void containsNullTagExceptionTest() {
        assertThrows(NullPointerException.class, () -> new TagCollection(List.of("red", null, "green")));
    }

    @Test
    @DisplayName("コンストラクタの異常系（空白文字タグ含む）")
    void containsBlankTagExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> new TagCollection(List.of("red", "   ", "green")));
    }

    @Test
    @DisplayName("addメソッドの正常系（新規追加）")
    void normalAddNewTagTest() {
        TagCollection original = new TagCollection(List.of("red"));
        TagCollection added = original.add("blue");

        assertNotSame(original, added);
        assertEquals(List.of("red", "blue"), added.getTags());
    }

    @Test
    @DisplayName("addメソッドの異常系（nullタグ）")
    void addNullTagExceptionTest() {
        TagCollection original = new TagCollection(List.of("red"));
        assertThrows(IllegalArgumentException.class, () -> original.add(null));
    }

    @Test
    @DisplayName("addメソッドの異常系（空白文字タグ）")
    void addBlankTagExceptionTest() {
        TagCollection original = new TagCollection(List.of("red"));
        assertThrows(IllegalArgumentException.class, () -> original.add("   "));
    }

    @Test
    @DisplayName("addメソッドの既存タグ → 変更なし")
    void addExistingTagTest() {
        TagCollection tags = new TagCollection(List.of("red"));
        TagCollection result = tags.add("red");
        assertSame(tags, result);
    }

    @Test
    @DisplayName("removeメソッドの正常系（存在するタグ削除）")
    void removeExistingTagTest() {
        TagCollection tags = new TagCollection(List.of("red", "blue", "green"));
        TagCollection result = tags.remove("red");

        assertEquals(List.of("blue", "green"), result.getTags());
        assertNotSame(tags, result);
    }

    @Test
    @DisplayName("removeメソッドの存在しないタグ → 変更なし")
    void removeNonExistentTagTest() {
        TagCollection tags = new TagCollection(List.of("red", "blue", "green"));
        TagCollection result = tags.remove("lemon");

        assertSame(tags, result);
    }

    @Test
    @DisplayName("removeメソッドの異常系（nullタグ）")
    void removeNullTagExceptionTest() {
        TagCollection tags = new TagCollection(List.of("red"));
        assertThrows(IllegalArgumentException.class, () -> tags.remove(null));
    }

    @Test
    @DisplayName("removeメソッドの異常系（空白文字タグ）")
    void removeBlankTagExceptionTest() {
        TagCollection tags = new TagCollection(List.of("red"));
        assertThrows(IllegalArgumentException.class, () -> tags.remove("   "));
    }
}
