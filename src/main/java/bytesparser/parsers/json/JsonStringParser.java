package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;

import static bytesparser.parsers.json.JsonParser.getChar;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonStringParser implements Parser<BitArray, String> {
    @Override
    public String parse(Context<BitArray> context) {
        char last = getChar(context);
        Preconditions.checkState(last == '"');
        StringBuilder stringBuilder = new StringBuilder();
        while ((last = getChar(context)) != '"') {
            if (last == '\\') {
                last = getChar(context);
                stringBuilder.append(last);
            } else {
                stringBuilder.append(last);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
