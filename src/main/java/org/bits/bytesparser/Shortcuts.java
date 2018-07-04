package org.bits.bytesparser;

import org.bits.bytesparser.builders.BytesParserBuilder;
import org.bits.bytesparser.builders.ClassParserBuilder;
import org.bits.bytesparser.builders.IntegerParserBuilder;
import org.bits.bytesparser.builders.StringParserBuilder;
import org.bits.bytesparser.valuegetters.ValueGetter;

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
