package bytesparser.builders;

import bytesparser.parsers.IntegerParser;
import bytesparser.parsers.Parser;
import bytesparser.parsers.StringParser;
import bytesparser.valuegetters.AllValueGetter;
import bytesparser.valuegetters.ValueGetter;
import com.google.common.base.Charsets;
import lombok.NonNull;

import java.nio.charset.Charset;

/**
 * @author tomer
 * @since 5/29/17
 */
public class IntegerParserBuilder implements ParserBuilder<Integer> {

    private ValueGetter<Integer> sizeGetter = new AllValueGetter();

    @Override
    public IntegerParser build() {
        return new IntegerParser(sizeGetter);
    }

    public IntegerParserBuilder size(int i) {
        return size(context -> i);
    }

    public IntegerParserBuilder size(ValueGetter<Integer> sizeGetter) {
        this.sizeGetter = sizeGetter;
        return this;
    }
}
