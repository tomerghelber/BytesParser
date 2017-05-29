package bytesparser;

import bits.array.BitArray;
import bits.array.simples.BytesAsBitArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomer on 4/20/17.
 */
public class Context {

    /* --- Members --- */

    private final Map<String, Object> fields = new HashMap<>();

    private BitArray source;

    /* --- Constructors --- */

    public Context(byte[] source) {
        this(new BytesAsBitArray(source));
    }

    public Context(BitArray source) {
        this.source = source;
    }

    /* --- Methods --- */

    public <F> F getField(String fieldName) {
        return (F) fields.get(fieldName);
    }

    public void addField(String fieldName, Object fieldValue) {
        if (fields.containsKey(fieldName)) {
            throw new RuntimeException();
        }
        fields.put(fieldName, fieldValue);
    }

    public BitArray getData(int size) {
        BitArray ret = source.cut(size);
        source = source.cut(size, source.size());
        return ret;
    }

    public int getRemand() {
        return source.size();
    }
}
