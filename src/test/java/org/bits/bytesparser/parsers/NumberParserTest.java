package org.bits.bytesparser.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author tomer
 * @since 6/4/17
 */
public class NumberParserTest {
    private NumberParser parser;

    @BeforeEach
    void setup() {
        parser = new NumberParser();
    }

    @Test
    void testJsonWithSimpleInteger() {
        assertEquals(1234L, parser.parse("1234".getBytes()));
    }

    @Test
    void testJsonWithNegativeInteger() {
        assertEquals(-1234L, parser.parse("-1234".getBytes()));
    }
    @Test
    void testJsonWithSimpleDouble() {
        assertEquals(1234.5, parser.parse("1234.5".getBytes()));
    }

    @Test
    void testJsonWithNegativeDouble() {
        assertEquals(-1234.5, parser.parse("-1234.5".getBytes()));
    }
}
