package org.bits.bytesparser.parsers;

import org.bits.array.BitArray;
import org.bits.bytesparser.contexts.BytesContext;
import org.bits.bytesparser.contexts.Context;
import org.bits.bytesparser.valuegetters.ValueGetter;

import java.nio.charset.Charset;

/**
 * @author tomer
 * @since 5/29/17
 */
public class StringParser implements Parser<BitArray, String> {

    /* --- Members --- */

    private final ValueGetter<BitArray, Integer> offsetGetter;

    private final ValueGetter<BitArray, Integer> lengthGetter;

    private final ValueGetter<BitArray, Charset> charsetGetter;

    /* --- Constructors --- */

    public StringParser(ValueGetter<BitArray, Integer> offsetGetter, ValueGetter<BitArray, Integer> lengthGetter, ValueGetter<BitArray, Charset> charsetGetter) {
        this.offsetGetter = offsetGetter;
        this.lengthGetter = lengthGetter;
        this.charsetGetter = charsetGetter;
    }

    /* --- Parser Impl. --- */

    @Override
    public String parse(Context<BitArray> context) {
        int offset = offsetGetter.get(context) * Byte.SIZE;
        int length = lengthGetter.get(context) * Byte.SIZE;
        byte[] source = context.getData(length).cut(offset, length).toBytes();

        Charset charset = charsetGetter.get(context);
        return new String(source, charset);
    }

    @Override
    public String parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
