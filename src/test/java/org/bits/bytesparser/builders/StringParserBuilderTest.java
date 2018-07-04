package org.bits.bytesparser.builders;

import org.bits.bytesparser.Shortcuts;
import com.google.common.base.Charsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author tomer
 * @since 5/29/17
 */
class StringParserBuilderTest {

    @Test
    @DisplayName("Test simple string creation")
    void testSimpleStringCreation() throws Exception {
        byte[] example = {'h', 'e', 'l', 'l', 'o'};
        String actual = Shortcuts.string().build().parse(example);
        String expected = "hello";
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Test string with size creation")
    void testStringCreationWithSize() throws Exception {
        byte[] example = {'h', 'e', 'l', 'l', 'o'};
        String actual = Shortcuts.string().setLength(4).build().parse(example);
        String expected = "hell";
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Test string with offset creation")
    void testStringCreationWithOffset() throws Exception {
        byte[] example = {'h', 'e', 'l', 'l', 'o'};
        String actual = Shortcuts.string().setOffset(1).build().parse(example);
        String expected = "ello";
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Test string with charset object")
    void testStringCreationWithCharset() throws Exception {
        byte[] example = {0x0, 'h', 0x0, 'e', 0x0, 'l', 0x0, 'l', 0x0, 'o'};
        String actual = Shortcuts.string().setCharset(Charsets.UTF_16).build().parse(example);
        String expected = "hello";
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Test string with charset string")
    void testStringCreationWithCharsetString() throws Exception {
        byte[] example = {0x0, 'h', 0x0, 'e', 0x0, 'l', 0x0, 'l', 0x0, 'o'};
        String actual = Shortcuts.string().setCharset("UTF_16").build().parse(example);
        String expected = "hello";
        assertEquals(actual, expected);
    }
}