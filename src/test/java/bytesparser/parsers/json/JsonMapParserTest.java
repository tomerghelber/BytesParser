package bytesparser.parsers.json;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static bytesparser.parsers.json.JsonListParser.EMPTY_ITEM_ERROR;
import static bytesparser.parsers.json.JsonMapParser.EMPTY_KEY_ERROR;
import static bytesparser.parsers.json.JsonMapParser.EMPTY_VALUE_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonMapParserTest {
    private JsonParser jsonParser;

    @BeforeEach
    void setup() {
        jsonParser = new JsonParser();
    }

    @Test
    void testJsonWithEmptyMap() {
        assertEquals(Collections.emptyMap(), jsonParser.parse("{}".getBytes()));
    }

    @Test
    void testJsonWithMap() {
        assertEquals(ImmutableMap.of("key", "value"), jsonParser.parse("{\"key\" : \"value\"}".getBytes()));
    }

    @Test
    void testJsonWithEmptyKeyInMap() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->jsonParser.parse("{:\"value\"}".getBytes()));
        assertEquals(EMPTY_KEY_ERROR, exception.getMessage());
    }

    @Test
    void testJsonWithEmptyValueInMap() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->jsonParser.parse("{\"key\":}".getBytes()));
        assertEquals(EMPTY_VALUE_ERROR, exception.getMessage());
    }

    @Test
    void testJsonWithEmptyItemInMapBoth() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->jsonParser.parse("{,}".getBytes()));
        assertEquals(EMPTY_ITEM_ERROR, exception.getMessage());
    }

    @Test
    void testJsonWithEmptyItemInMapLast() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->jsonParser.parse("{\"key\":\"value\",}".getBytes()));
        assertEquals(EMPTY_ITEM_ERROR, exception.getMessage());
    }

    @Test
    void testJsonWithEmptyItemInMapFirst() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->jsonParser.parse("{,\"key\":\"value\"}".getBytes()));
        assertEquals(EMPTY_ITEM_ERROR, exception.getMessage());
    }
}