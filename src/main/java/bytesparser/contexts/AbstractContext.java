package bytesparser.contexts;

import bits.array.BitArray;
import bits.array.simples.BytesAsBitArray;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PROTECTED;

/**
 * Created by tomer on 4/20/17.
 */
public abstract class AbstractContext<S> implements Context<S> {

    /* --- Members --- */

    private final Map<String, Object> fields = new HashMap<>();

    @Setter(value = PROTECTED)
    @Getter(value = PROTECTED)
    private S source;

    /* --- Constructors --- */

    public AbstractContext(S source) {
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

    public abstract S getData(int size);

    public abstract int getRemand();
}
