package bytesparser.builders;

import bytesparser.Shortcuts;
import bytesparser.parsers.Parser;
import com.google.common.base.Charsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author tomer
 * @since 5/29/17
 */
class IntegerParserBuilderTest {

    @Test
    @DisplayName("Test simple integer creation")
    void testSimpleIntegerCreation() throws Exception {
        byte[] example = {0xF};
        Integer actual = Shortcuts.integer().build().parse(example);
        Integer expected = 0xF;
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Test integer with size creation")
    void testIntegerWithSizeCreation() throws Exception {
        byte[] example = {0xF, 0xF};
        Integer actual = Shortcuts.integer().size(1).build().parse(example);
        Integer expected = 0xF;
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Test integer with size creation")
    void testIntegerWithSizeCreation2() throws Exception {
        byte[] example = {0xF, 0xE, 0xD};
        Integer actual = Shortcuts.integer().size(2).build().parse(example);
        Integer expected = 0xFE;
        assertEquals(actual, expected);
    }
}