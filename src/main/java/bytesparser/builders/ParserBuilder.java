package bytesparser.builders;

import bytesparser.parsers.Parser;

/**
 * @author tomer
 * @since 5/29/17
 */
@FunctionalInterface
public interface ParserBuilder<S, T> {
    Parser<S, T> build();
}