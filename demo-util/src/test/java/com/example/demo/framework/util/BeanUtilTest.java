package com.example.demo.framework.util;

import com.example.demo.framework.util.UtilFixtures.Person;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

class BeanUtilTest {
    @Test
    void jsonCopyPreservesPropertiesAndCreatesIndependentNestedObjects() {
        Person source = UtilFixtures.person("张三");
        Person copy = BeanUtil.copy(source, Person.class);
        assertThat(copy).usingRecursiveComparison().isEqualTo(source);
        assertThat(copy).isNotSameAs(source);
        assertThat(copy.getAddress()).isNotSameAs(source.getAddress());
        copy.getAddress().setCity("上海");
        assertThat(source.getAddress().getCity()).isEqualTo("杭州");
    }

    @Test
    void supplierCopyCreatesBeanWithShallowPropertyReferences() {
        Person source = UtilFixtures.person("张三");
        Person copy = BeanUtil.copy(source, Person::new);
        assertThat(copy).usingRecursiveComparison().isEqualTo(source);
        assertThat(copy).isNotSameAs(source);
        assertThat(copy.getAddress()).isSameAs(source.getAddress());
    }

    @Test
    void listOverloadsPreserveOrderAndCreateSeparateElements() {
        List<Person> source = List.of(UtilFixtures.person("first"), UtilFixtures.person("second"));
        for (List<Person> copy : List.of(BeanUtil.copy(source, Person.class), BeanUtil.copy(source, Person::new))) {
            assertThat(copy).extracting(Person::getName).containsExactly("first", "second");
            assertThat(copy.getFirst()).isNotSameAs(source.getFirst());
            assertThat(copy.get(1)).isNotSameAs(copy.getFirst());
        }
    }

    @Test
    void nullSourcesDoNotInvokeSupplier() {
        AtomicInteger created = new AtomicInteger();
        Supplier<Person> supplier = () -> { created.incrementAndGet(); return new Person(); };
        assertThat(BeanUtil.copy((Object) null, Person.class)).isNull();
        assertThat(BeanUtil.copy((List<?>) null, Person.class)).isNull();
        assertThat(BeanUtil.copy((Object) null, supplier)).isNull();
        assertThat(BeanUtil.copy((List<?>) null, supplier)).isNull();
        assertThat(created).hasValue(0);
    }

    @Test
    void emptyListsDoNotCreateBeans() {
        assertThat(BeanUtil.copy(List.of(), Person.class)).isEmpty();
        assertThat(BeanUtil.copy(List.of(), () -> { throw new AssertionError("supplier must not run"); })).isEmpty();
    }
}
