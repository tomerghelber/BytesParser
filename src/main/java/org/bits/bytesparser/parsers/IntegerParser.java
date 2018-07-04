package org.bits.bytesparser.parsers;

import org.bits.array.BitArray;
import org.bits.bytesparser.contexts.BytesContext;
import org.bits.bytesparser.contexts.Context;
import org.bits.bytesparser.valuegetters.ValueGetter;

/**
 * @author tomer
 * @since 5/29/17
 */
public class IntegerParser implements Parser<BitArray, Integer> {

    /* --- Members --- */

    private final ValueGetter<BitArray, Integer> sizeGetter;

    /* --- Constructors --- */

    public IntegerParser(ValueGetter<BitArray, Integer> sizeGetter) {
        this.sizeGetter = sizeGetter;
    }

    /* --- Parser Impl. --- */

    @Override
    public Integer parse(Context<BitArray> context) {
        int size = sizeGetter.get(context);
        byte[] source = context.getData(size * Byte.SIZE).toBytes();
        int value = 0;
        for (int i=0; i < size; i++) {
            value *= 16;
            value += source[i];
        }
        return value;
    }

    @Override
    public Integer parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
