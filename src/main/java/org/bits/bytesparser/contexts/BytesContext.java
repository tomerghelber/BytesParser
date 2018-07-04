package org.bits.bytesparser.contexts;

import org.bits.array.BitArray;
import org.bits.array.simples.BytesAsBitArray;

/**
 * Created by tomer on 4/20/17.
 */
public class BytesContext extends AbstractContext<BitArray> {

    /* --- Members --- */

    /* --- Constructors --- */

    public BytesContext(byte[] source) {
        this(new BytesAsBitArray(source));
    }

    public BytesContext(BitArray source) {
        super(source);
    }

    /* --- Methods --- */

    @Override
    public BitArray getData(int size) {
        BitArray ret = peekData(size);
        setSource(getSource().cut(size, getSource().size()));
        return ret;
    }

    @Override
    public BitArray peekData(int size) {
        return getSource().cut(0, size);
    }

    @Override
    public int getRemand() {
        return getSource().size();
    }
}
