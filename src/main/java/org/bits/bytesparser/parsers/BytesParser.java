package org.bits.bytesparser.parsers;

import org.bits.array.BitArray;
import org.bits.bytesparser.contexts.BytesContext;
import org.bits.bytesparser.contexts.Context;
import org.bits.bytesparser.valuegetters.ValueGetter;

/**
 * @author tomer
 * @since 5/29/17
 */
public class BytesParser implements Parser<BitArray, byte[]> {

    /* --- Members --- */

    private final ValueGetter<BitArray, Integer> offsetGetter;

    private final ValueGetter<BitArray, Integer> lengthGetter;

    /* --- Constructors --- */

    public BytesParser(ValueGetter<BitArray, Integer> offsetGetter, ValueGetter<BitArray, Integer> lengthGetter) {
        this.offsetGetter = offsetGetter;
        this.lengthGetter = lengthGetter;
    }

    /* --- Parser Impl. --- */

    @Override
    public byte[] parse(Context<BitArray> context) {
        int offset = offsetGetter.get(context) * Byte.SIZE;
        int length = lengthGetter.get(context) * Byte.SIZE;
        return context.getData(length).cut(offset, length).toBytes();
    }

    @Override
    public byte[] parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
