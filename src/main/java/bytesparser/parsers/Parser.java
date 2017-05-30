package bytesparser.parsers;

import bytesparser.contexts.Context;

/**
 * Created by tomer on 4/20/17.
 */
public interface Parser<S, T> {
    T parse(Context<S> context);
    T parse(byte[] source);
}
