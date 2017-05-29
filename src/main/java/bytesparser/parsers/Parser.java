package bytesparser.parsers;

import bytesparser.Context;

/**
 * Created by tomer on 4/20/17.
 */
@FunctionalInterface
public interface Parser<T> {
    T parse(Context context);

    default T parse(byte[] source) {
        return parse(new Context(source));
    }
}
