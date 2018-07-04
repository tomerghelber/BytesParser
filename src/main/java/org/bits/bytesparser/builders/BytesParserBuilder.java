package org.bits.bytesparser.builders;

import org.bits.array.BitArray;
import org.bits.bytesparser.parsers.BytesParser;
import org.bits.bytesparser.valuegetters.AllValueGetter;
import org.bits.bytesparser.valuegetters.ValueGetter;

/**
 * @author tomer
 * @since 5/29/17
 */
public class BytesParserBuilder<S> implements ParserBuilder<BitArray, byte[]> {

    private ValueGetter<BitArray, Integer> offsetGetter = context -> 0;
    private ValueGetter<BitArray, Integer> lengthGetter = new AllValueGetter<>();

    @Override
    public BytesParser build() {
        return new BytesParser(offsetGetter, lengthGetter);
    }

    public BytesParserBuilder setLength(int length) {
        return setLength((context) -> length);
    }

    public BytesParserBuilder setLength(ValueGetter<BitArray, Integer> lengthGetter) {
        this.lengthGetter  = lengthGetter;
        return this;
    }

    public BytesParserBuilder setOffset(int offset) {
        return setOffset((context) -> offset);
    }

    public BytesParserBuilder setOffset(ValueGetter<BitArray, Integer> offsetGetter) {
        this.offsetGetter  = offsetGetter;
        return this;
    }

    public BytesParserBuilder skip(int i) {
        return setOffset(i);
    }
}
