package org.bits.bytesparser.valuegetters;

import org.bits.bytesparser.contexts.Context;

/**
 * @author tomer
 * @since 5/29/17
 */
@FunctionalInterface
public interface ValueGetter<S, T> {
    T get(Context<S> context);
}
