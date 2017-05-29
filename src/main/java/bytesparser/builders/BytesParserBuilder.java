package bytesparser.builders;

import bytesparser.parsers.BytesParser;
import bytesparser.parsers.Parser;
import bytesparser.parsers.StringParser;
import bytesparser.valuegetters.AllValueGetter;
import bytesparser.valuegetters.ValueGetter;
import com.google.common.base.Charsets;

import java.nio.charset.Charset;

/**
 * @author tomer
 * @since 5/29/17
 */
public class BytesParserBuilder implements ParserBuilder<byte[]> {

    private ValueGetter<Integer> offsetGetter = context -> 0;
    private ValueGetter<Integer> lengthGetter = new AllValueGetter();

    @Override
    public BytesParser build() {
        return new BytesParser(offsetGetter, lengthGetter);
    }

    public BytesParserBuilder setLength(int length) {
        return setLength((context) -> length);
    }

    public BytesParserBuilder setLength(ValueGetter<Integer> lengthGetter) {
        this.lengthGetter  = lengthGetter;
        return this;
    }

    public BytesParserBuilder setOffset(int offset) {
        return setOffset((context) -> offset);
    }

    public BytesParserBuilder setOffset(ValueGetter<Integer> offsetGetter) {
        this.offsetGetter  = offsetGetter;
        return this;
    }

    public BytesParserBuilder skip(int i) {
        return setOffset(i);
    }
}
