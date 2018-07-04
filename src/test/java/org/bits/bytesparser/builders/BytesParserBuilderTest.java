package org.bits.bytesparser.builders;

import org.bits.bytesparser.Shortcuts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author tomer
 * @since 5/29/17
 */
class BytesParserBuilderTest {

    @Test
    @DisplayName("Test simple bytes creation")
    void testSimpleStringCreation() throws Exception {
        byte[] example = {'h', 'e', 'l', 'l', 'o'};
        byte[] actual = Shortcuts.bytes().build().parse(example);
        byte[] expected = {'h', 'e', 'l', 'l', 'o'};
        assertArrayEquals(actual, expected);
    }

    @Test
    @DisplayName("Test bytes with size creation")
    void testStringCreationWithSize() throws Exception {
        byte[] example = {'h', 'e', 'l', 'l', 'o'};
        byte[] actual = Shortcuts.bytes().setLength(4).build().parse(example);
        byte[] expected = {'h', 'e', 'l', 'l'};
        assertArrayEquals(actual, expected);
    }

    @Test
    @DisplayName("Test bytes with offset creation")
    void testStringCreationWithOffset() throws Exception {
        byte[] example = {'h', 'e', 'l', 'l', 'o'};
        byte[] actual = Shortcuts.bytes().setOffset(1).build().parse(example);
        byte[] expected = {'e', 'l', 'l', 'o'};
        assertArrayEquals(actual, expected);
    }

    @Test
    @DisplayName("Test bytes with offset creation")
    void testStringCreationWithSkip() throws Exception {
        byte[] example = {'h', 'e', 'l', 'l', 'o'};
        byte[] actual = Shortcuts.bytes().skip(1).build().parse(example);
        byte[] expected = {'e', 'l', 'l', 'o'};
        assertArrayEquals(actual, expected);
    }
}