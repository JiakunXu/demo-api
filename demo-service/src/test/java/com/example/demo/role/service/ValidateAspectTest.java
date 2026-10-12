package com.example.demo.role.service;

import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotEmpty;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.aspectj.ValidateAspect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ValidateAspectTest {
    private final Target target = new Target();
    private Target proxy() {
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.setProxyTargetClass(true);
        factory.addAspect(new ValidateAspect());
        return factory.getProxy();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void notBlankRejectsWhitespaceBeforeTargetExecutes(String value) {
        assertThatThrownBy(() -> proxy().blank(value)).hasMessage("modifier required");
        assertThat(target.calls).isZero();
    }

    @Test
    void notEmptyRejectsEmptyButAllowsWhitespace() {
        Target proxy = proxy();
        assertThatThrownBy(() -> proxy.empty(null)).hasMessage("参数信息不能为空");
        assertThatThrownBy(() -> proxy.empty("")).hasMessage("参数信息不能为空");
        assertThat(target.calls).isZero();
        proxy.empty(" ");
        assertThat(target.calls).isEqualTo(1);
    }

    @Test
    void notNullRejectsNullEmptyListsAndPrimitiveOrObjectArrays() {
        Target proxy = proxy();
        for (Object argument : new Object[] {null, List.of(), new int[0], new String[0]}) {
            assertThatThrownBy(() -> proxy.required(argument)).hasMessage("参数信息不能为空");
        }
        assertThat(target.calls).isZero();
        proxy.required(List.of("one"));
        proxy.required(new int[] {1});
        proxy.required(new String[] {"one"});
        proxy.required("value");
        assertThat(target.calls).isEqualTo(4);
    }

    @Test
    void validParametersExecuteTargetThroughAspect() {
        proxy().blank("editor");
        assertThat(target.calls).isEqualTo(1);
    }

    public static class Target {
        private int calls;
        public void blank(@NotBlank("modifier required") String value) { calls++; }
        public void empty(@NotEmpty String value) { calls++; }
        public void required(@NotNull Object value) { calls++; }
    }
}
