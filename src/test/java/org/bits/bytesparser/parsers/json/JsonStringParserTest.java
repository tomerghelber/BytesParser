package org.bits.bytesparser.parsers.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonStringParserTest {
    private JsonParser jsonParser;

    @BeforeEach
    void setup() {
        jsonParser = new JsonParser();
    }

    @Test
    void testJsonWithSimpleString() {
        assertEquals("1234", jsonParser.parse("\"1234\"".getBytes()));
    }

}
