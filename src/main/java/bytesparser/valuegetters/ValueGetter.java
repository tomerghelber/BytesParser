package bytesparser.valuegetters;

import bytesparser.Context;

/**
 * @author tomer
 * @since 5/29/17
 */
@FunctionalInterface
public interface ValueGetter<T> {
    T get(Context context);
}
