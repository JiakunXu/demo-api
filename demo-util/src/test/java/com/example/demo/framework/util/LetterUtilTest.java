package com.example.demo.framework.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class LetterUtilTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t\n", "123", "@user", "😀", "éclair"})
    void blankOrUnsupportedNamesUseFallback(String name) {
        assertThat(LetterUtil.getLetter(name)).isEqualTo("#");
    }

    @ParameterizedTest
    @CsvSource({"alice, A", "Bob, B", "zebra, Z", "张三, Z", "李四, L", "王五, W", "陈六, C", "北京, B", "上海, S", "广州, G"})
    void extractsLatinOrChineseInitial(String name, String expected) {
        assertThat(LetterUtil.getLetter(name)).isEqualTo(expected);
    }
}
