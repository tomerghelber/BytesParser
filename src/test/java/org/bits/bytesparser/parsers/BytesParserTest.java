package org.bits.bytesparser.parsers;

import org.bits.array.BitArray;
import org.bits.bytesparser.contexts.BytesContext;
import org.bits.bytesparser.valuegetters.AllValueGetter;
import org.bits.bytesparser.valuegetters.ValueGetter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * @author tomer
 * @since 5/30/17
 */
class BytesParserTest {
    @Test
    void parseSimple() {
        byte[] bytes = new byte[] {1,2,3};
        ValueGetter<BitArray, Integer> offsetGetter = context -> 0;
        ValueGetter<BitArray, Integer> lengthGetter = new AllValueGetter<>();
        BytesParser parser = new BytesParser(offsetGetter, lengthGetter);

        BytesContext context = spy(new BytesContext(bytes));

        byte[] actual = parser.parse(context);
        assertArrayEquals(bytes, actual);

        verify(context).getData(24);
    }

    @Test
    void parseWithOffset() {
        byte[] bytes = new byte[] {1,2,3};
        ValueGetter<BitArray, Integer> offsetGetter = context -> 2;
        ValueGetter<BitArray, Integer> lengthGetter = new AllValueGetter<>();
        BytesParser parser = new BytesParser(offsetGetter, lengthGetter);

        BytesContext context = spy(new BytesContext(bytes));

        byte[] actual = parser.parse(context);
        assertArrayEquals(new byte[]{3}, actual);

        verify(context).getData(24);
    }
}