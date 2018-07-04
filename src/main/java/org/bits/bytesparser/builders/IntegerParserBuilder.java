package org.bits.bytesparser.builders;

import org.bits.array.BitArray;
import org.bits.bytesparser.parsers.IntegerParser;
import org.bits.bytesparser.valuegetters.AllValueGetter;
import org.bits.bytesparser.valuegetters.ValueGetter;

/**
 * @author tomer
 * @since 5/29/17
 */
public class IntegerParserBuilder implements ParserBuilder<BitArray, Integer> {

    private ValueGetter<BitArray, Integer> sizeGetter = new AllValueGetter<>();

    @Override
    public IntegerParser build() {
        return new IntegerParser(sizeGetter);
    }

    public IntegerParserBuilder size(int i) {
        return size(context -> i);
    }

    public IntegerParserBuilder size(ValueGetter<BitArray, Integer> sizeGetter) {
        this.sizeGetter = sizeGetter;
        return this;
    }
}
