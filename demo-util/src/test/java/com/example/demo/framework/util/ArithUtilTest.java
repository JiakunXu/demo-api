package com.example.demo.framework.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class ArithUtilTest {
    @Test
    void decimalArithmeticAvoidsBinaryFloatingPointArtifacts() {
        assertThat(ArithUtil.add(0.1, 0.2)).isEqualTo(0.3);
        assertThat(ArithUtil.sub(0.3, 0.1)).isEqualTo(0.2);
        assertThat(ArithUtil.mul(0.1, 0.2)).isEqualTo(0.02);
        assertThat(ArithUtil.div(0.3, 0.1)).isEqualTo(3.0);
        assertThat(ArithUtil.add(-0.1, 0.1)).isZero();
    }

    @Test
    void defaultDivisionKeepsTenDecimalPlaces() {
        assertThat(ArithUtil.div(1, 3)).isEqualTo(0.3333333333);
        assertThat(ArithUtil.div(2, 3)).isEqualTo(0.6666666667);
    }

    @ParameterizedTest
    @CsvSource({"1, 8, 2, 0.13", "-1, 8, 2, -0.13", "5, 2, 0, 3", "0, 2, 2, 0"})
    void divisionUsesHalfUp(double dividend, double divisor, int scale, double expected) {
        assertThat(ArithUtil.div(dividend, divisor, scale)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"1.005, 2, 1.01", "-1.005, 2, -1.01", "2.5, 0, 3", "-2.5, 0, -3"})
    void roundingHandlesTiesAndNegativeNumbers(double value, int scale, double expected) {
        assertThat(ArithUtil.round(value, scale)).isEqualTo(expected);
    }

    @Test
    void invalidScaleAndDivisionByZeroAreRejected() {
        assertThatThrownBy(() -> ArithUtil.div(1, 2, -1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ArithUtil.round(1, -1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ArithUtil.div(1, 0)).isInstanceOf(ArithmeticException.class);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void nonFiniteValuesCannotBeUsedAsDecimals(double value) {
        assertThatThrownBy(() -> ArithUtil.add(value, 1)).isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> ArithUtil.sub(1, value)).isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> ArithUtil.mul(value, 1)).isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> ArithUtil.div(value, 1)).isInstanceOf(NumberFormatException.class);
        assertThatThrownBy(() -> ArithUtil.round(value, 2)).isInstanceOf(NumberFormatException.class);
    }
}
