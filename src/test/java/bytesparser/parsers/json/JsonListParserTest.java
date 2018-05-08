package bytesparser.parsers.json;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static bytesparser.parsers.json.JsonListParser.EMPTY_ITEM_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonListParserTest {
    private JsonParser jsonParser;

    @BeforeEach
    void setup() {
        jsonParser = new JsonParser();
    }

    @Test
    void testJsonWithEmptyList() {
        assertEquals(Collections.emptyList(), jsonParser.parse("[]".getBytes()));
    }

    @Test
    void testJsonWithList() {
        assertEquals(ImmutableList.of(1234.0, "1234"), jsonParser.parse("[1234, \"1234\"]".getBytes()));
    }

    @Test
    void testJsonWithEmptyItemInListBoth() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->jsonParser.parse("[,]".getBytes()));
        assertEquals(EMPTY_ITEM_ERROR, exception.getMessage());
    }

    @Test
    void testJsonWithEmptyItemInListLast() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->jsonParser.parse("[1234,]".getBytes()));
        assertEquals(EMPTY_ITEM_ERROR, exception.getMessage());
    }

    @Test
    void testJsonWithEmptyItemInListFirst() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->jsonParser.parse("[,\"1234\"]".getBytes()));
        assertEquals(EMPTY_ITEM_ERROR, exception.getMessage());
    }
}
