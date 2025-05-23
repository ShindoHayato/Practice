package com.example.practice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class TagCollectionTest {
    @Test
    @DisplayName("コンストラクタの正常系")
    void normalTagsTest() {
        TagCollection tags = new TagCollection(List.of("red", "blue", "green", " blue "));
        List<String> result = tags.getTags();
        assertEquals(List.of("red", "blue", "green"), result);
    }

    @ParameterizedTest(name = "{index} - {2}")
    @MethodSource("provideInvalidTagLists")
    @DisplayName("コンストラクタの異常系テスト（nullリスト、nullタグ、空白タグ）")
    void constructorThrowsExceptionForInvalidInputs(List<String> input, Class<? extends Throwable> expectedException, String description) {
        assertThrows(expectedException, () -> new TagCollection(input));
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> provideInvalidTagLists() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of(null, IllegalArgumentException.class, "nullリスト"),
            org.junit.jupiter.params.provider.Arguments.of(Arrays.asList("red", null, "green"), IllegalArgumentException.class, "nullタグ含む"),
            org.junit.jupiter.params.provider.Arguments.of(List.of("red", "   ", "green"), IllegalArgumentException.class, "空白タグ含む")
        );
    }

    @Test
    @DisplayName("addメソッドの正常系（新規追加）")
    void normalAddNewTagTest() {
        TagCollection original = new TagCollection(List.of("red"));
        TagCollection added = original.add("blue");

        assertNotSame(original, added);
        assertEquals(List.of("red", "blue"), added.getTags());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("addメソッドの異常系（nullタグ、空白文字タグ）")
    void addNullOrEmptyTagTest(String input) {
        TagCollection original = new TagCollection(List.of("red"));
        assertThrows(IllegalArgumentException.class, () -> original.add(input));
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

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("removeメソッドの異常系（nullタグ、空白文字タグ）")
    void removeNullOrEmptyTagTest(String input) {
        TagCollection tags = new TagCollection(List.of("red"));
        assertThrows(IllegalArgumentException.class, () -> tags.remove(input));
    }
}
