package org.bits.bytesparser.parsers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.bits.array.BitArray;
import org.bits.bytesparser.Shortcuts;
import org.bits.bytesparser.valuegetters.ValueGetter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author tomer
 * @since 5/30/17
 */
class ClassParserTest {

    @Test
    @DisplayName("test simple creation")
    void testSimpleCreation() {
        class ExampleClass {

        }
        ValueGetter<BitArray, ExampleClass> builder = (array)->new ExampleClass();
        Map<String, Parser> fields = Maps.newHashMap();
        ClassParser<ExampleClass> parser = new ClassParser<>(builder, fields);

        ExampleClass actual = parser.parse(new byte[0]);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("test creation with a field")
    void testCreationWithFields() {
        class ExampleClass {
            String field1;

            public ExampleClass() {}
            public ExampleClass(String field1) {
                this.field1 = field1;
            }
        }
        ValueGetter<BitArray, ExampleClass> builder = (array)->new ExampleClass();
        Map<String, Parser> fields = ImmutableMap.of(
                "field1", Shortcuts.string().build()
        );
        ClassParser<ExampleClass> parser = new ClassParser<>(builder, fields);

        ExampleClass actual = parser.parse("testing field1".getBytes());

        ExampleClass expected = new ExampleClass("testing field1");
        assertEquals(expected.field1, actual.field1);
    }

    @Test
    @DisplayName("test creation with a field and a non-field")
    void testCreationWithFieldsAndNonFields() {
        class ExampleClass {
            String field1;

            public ExampleClass() {}
            public ExampleClass(String field1) {
                this.field1 = field1;
            }
        }
        ValueGetter<BitArray, ExampleClass> builder = (array)->new ExampleClass();
        Map<String, Parser> fields = ImmutableMap.of(
                "field1Size", Shortcuts.integer().size(1).build(),
                "field1", Shortcuts.string().setLength(Shortcuts.byField("field1Size")).build()
        );
        ClassParser<ExampleClass> parser = new ClassParser<>(builder, fields);

        ExampleClass actual = parser.parse(new byte[]{0x4, 'h', 'e', 'l', 'l', 'o'});

        ExampleClass expected = new ExampleClass("hell");
        assertEquals(expected.field1, actual.field1);
    }
    @Test
    @DisplayName("test creation with fields")
    void testCreationWithMultipleFields() {
        class ExampleClass {
            String field2;
            String field1;

            ExampleClass() {}
            ExampleClass(String field2, String field1) {
                this.field2 = field2;
                this.field1 = field1;
            }
        }
        ValueGetter<BitArray, ExampleClass> builder = (array)->new ExampleClass();
        Map<String, Parser> fields = ImmutableMap.of(
                "field1Size", Shortcuts.integer().size(1).build(),
                "field1", Shortcuts.string().setLength(Shortcuts.byField("field1Size")).build(),
                "field2", Shortcuts.string().build()
        );
        ClassParser<ExampleClass> parser = new ClassParser<>(builder, fields);
        ExampleClass actual = parser.parse(new byte[]{0x4, 'h', 'e', 'l', 'l', 'o'});

        ExampleClass expected = new ExampleClass("o", "hell");
        assertEquals(expected.field1, actual.field1);
        assertEquals(expected.field2, actual.field2);
    }
}