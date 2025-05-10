package com.example.practice.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class TagCollection {
    private final List<String> tags;

    public TagCollection(List<String> tags) {
        if (tags == null) {
            throw new IllegalArgumentException("タグ一覧は必須です");
        }
        // nullチェック・空文字チェック・trim・重複排除
        List<String> filtered = tags.stream()
            .peek(tag -> {
                if (tag == null || tag.trim().isEmpty()) {
                    throw new IllegalArgumentException("タグは空または null にできません");
                }
            })
            .map(String::trim)
            .distinct()
            .collect(Collectors.toList());
        this.tags = Collections.unmodifiableList(filtered);
    }

    public TagCollection add(String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            throw new IllegalArgumentException("追加するタグは空または null にできません");
        }
        String t = tag.trim();
        if (tags.contains(t)) {
            return this;
        }
        List<String> newList = new ArrayList<>(tags);
        newList.add(t);
        return new TagCollection(newList);
    }

    public TagCollection remove(String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            throw new IllegalArgumentException("削除するタグは空または null にできません");
        }
        String t = tag.trim();
        if (!tags.contains(t)) {
            return this;
        }
        List<String> newList = tags.stream()
            .filter(x -> !x.equals(t))
            .collect(Collectors.toList());
        return new TagCollection(newList);
    }
}
