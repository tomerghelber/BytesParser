package bytesparser.contexts;

import bits.array.BitArray;
import bits.array.simples.BytesAsBitArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomer on 4/20/17.
 */
public class BytesContext<S> extends AbstractContext<BitArray> {

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
        BitArray ret = getSource().cut(size);
        setSource(getSource().cut(size, getSource().size()));
        return ret;
    }

    @Override
    public int getRemand() {
        return getSource().size();
    }
}
