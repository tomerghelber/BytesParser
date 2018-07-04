package org.bits.bytesparser.contexts;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by tomer on 4/20/17.
 */
public abstract class AbstractContext<S> implements Context<S> {

    /* --- Members --- */

    private final Map<String, Object> fields = new HashMap<>();

    private S source;

    /* --- Constructors --- */

    public AbstractContext(S source) {
        this.source = source;
    }

    /* --- Context Impl. --- */

    @Override
    public <F> F getField(String fieldName) {
        return (F) fields.get(fieldName);
    }

    @Override
    public void addField(String fieldName, Object fieldValue) {
        if (fields.containsKey(fieldName)) {
            throw new RuntimeException();
        }
        fields.put(fieldName, fieldValue);
    }

    /* --- Protected Methods --- */

    protected void setSource(S source) {
        this.source = source;
    }

    protected  S getSource() {
        return this.source;
    }
}
