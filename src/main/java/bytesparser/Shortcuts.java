package bytesparser;

import bytesparser.builders.*;
import bytesparser.parsers.ClassParser;

/**
 * @author tomer
 * @since 5/29/17
 */
public class Shortcuts {
    public static StringParserBuilder string() {
        return new StringParserBuilder();
    }

    public static IntegerParserBuilder integer() {
        return new IntegerParserBuilder();
    }

    public static BytesParserBuilder bytes() {
        return new BytesParserBuilder();
    }

    public static <T> ClassParserBuilder<T> classParser() {
        return new ClassParserBuilder<>();
    }
}
