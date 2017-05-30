package bytesparser.valuegetters;

import bytesparser.contexts.Context;

/**
 * @author tomer
 * @since 5/29/17
 */
public class AllValueGetter<S> implements ValueGetter<S, Integer> {

    @Override
    public Integer get(Context context) {
        return new Double(StrictMath.ceil(context.getRemand() / Byte.SIZE)).intValue();
    }
}
