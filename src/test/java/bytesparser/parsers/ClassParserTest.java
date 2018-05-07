package bytesparser.parsers;

import bits.array.BitArray;
import bytesparser.Shortcuts;
import bytesparser.valuegetters.ValueGetter;
import com.google.common.collect.Lists;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        List<Pair<String, Parser>> fields = Lists.newArrayList();
        ClassParser<ExampleClass> parser = new ClassParser<>(builder, fields);

        ExampleClass actual = parser.parse(new byte[0]);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("test creation with a field")
    void testCreationWithFields() {
        @NoArgsConstructor
        @AllArgsConstructor
        class ExampleClass {
            @Getter
            private String field1;
        }
        ValueGetter<BitArray, ExampleClass> builder = (array)->new ExampleClass();
        List<Pair<String, Parser>> fields = Lists.newArrayList(
                new Pair<>("field1", Shortcuts.string().build())
        );
        ClassParser<ExampleClass> parser = new ClassParser<>(builder, fields);

        ExampleClass actual = parser.parse("testing field1".getBytes());

        ExampleClass expected = new ExampleClass("testing field1");
        assertEquals(expected.getField1(), actual.getField1());
    }

    @Test
    @DisplayName("test creation with a field and a non-field")
    void testCreationWithFieldsAndNonFields() {
        @NoArgsConstructor
        @AllArgsConstructor
        class ExampleClass {
            @Getter
            private String field1;
        }
        ValueGetter<BitArray, ExampleClass> builder = (array)->new ExampleClass();
        List<Pair<String, Parser>> fields = Lists.newArrayList(
                new Pair<>("field1Size", Shortcuts.integer().size(1).build()),
                new Pair<>("field1", Shortcuts.string().setLength(Shortcuts.byField("field1Size")).build())
        );
        ClassParser<ExampleClass> parser = new ClassParser<>(builder, fields);

        ExampleClass actual = parser.parse(new byte[]{0x4, 'h', 'e', 'l', 'l', 'o'});

        ExampleClass expected = new ExampleClass("hell");
        assertEquals(expected.getField1(), actual.getField1());
    }
    @Test
    @DisplayName("test creation with fields")
    void testCreationWithMultipleFields() {
        @NoArgsConstructor
        @AllArgsConstructor
        class ExampleClass {
            @Getter
            private String field2;
            @Getter
            private String field1;
        }
        ValueGetter<BitArray, ExampleClass> builder = (array)->new ExampleClass();
        List<Pair<String, Parser>> fields = Lists.newArrayList(
                new Pair<>("field1Size", Shortcuts.integer().size(1).build()),
                new Pair<>("field1", Shortcuts.string().setLength(Shortcuts.byField("field1Size")).build()),
                new Pair<>("field2", Shortcuts.string().build())
        );
        ClassParser<ExampleClass> parser = new ClassParser<>(builder, fields);
        ExampleClass actual = parser.parse(new byte[]{0x4, 'h', 'e', 'l', 'l', 'o'});

        ExampleClass expected = new ExampleClass("o", "hell");
        assertEquals(expected.getField1(), actual.getField1());
        assertEquals(expected.getField2(), actual.getField2());
    }
}