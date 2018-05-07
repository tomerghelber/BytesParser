package bytesparser;

import bytesparser.builders.BytesParserBuilder;
import bytesparser.builders.ClassParserBuilder;
import bytesparser.builders.IntegerParserBuilder;
import bytesparser.builders.StringParserBuilder;
import bytesparser.valuegetters.ValueGetter;

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

    public static <S ,G> ValueGetter<S, G> byField(final String field) {
        return context -> context.getField(field);
    }
}
