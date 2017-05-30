package bytesparser.builders;

import bits.array.BitArray;
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
public class StringParserBuilder implements ParserBuilder<BitArray, String> {

    private ValueGetter<BitArray, Integer> offsetGetter = context -> 0;
    private ValueGetter<BitArray, Integer> lengthGetter = new AllValueGetter<>();
    private ValueGetter<BitArray, Charset> charsetGetter = context -> Charsets.UTF_8;

    @Override
    public Parser<BitArray, String> build() {
        return new StringParser(offsetGetter, lengthGetter, charsetGetter);
    }

    public StringParserBuilder setLength(int length) {
        return setLength((context) -> length);
    }

    public StringParserBuilder setLength(ValueGetter<BitArray, Integer> lengthGetter) {
        this.lengthGetter  = lengthGetter;
        return this;
    }

    public StringParserBuilder setOffset(int offset) {
        return setOffset((context) -> offset);
    }

    public StringParserBuilder setOffset(ValueGetter<BitArray, Integer> offsetGetter) {
        this.offsetGetter  = offsetGetter;
        return this;
    }

    public StringParserBuilder setCharset(String charsetString) {
        Charset charset = Charset.forName(charsetString);
        return setCharset((context) -> charset);
    }

    public StringParserBuilder setCharset(Charset charset) {
        return setCharset((context) -> charset);
    }

    public StringParserBuilder setCharset(ValueGetter<BitArray, Charset> charsetGetter) {
        this.charsetGetter  = charsetGetter;
        return this;
    }
}
