package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonStringParser implements Parser<BitArray, String> {
    @Override
    public String parse(Context<BitArray> context) {
        byte last = context.getData(8).toBytes()[0];
        Preconditions.checkState(last == '"');
        StringBuilder stringBuilder = new StringBuilder();
        while ((last = context.getData(8).toBytes()[0]) != '"') {
            if (last == '\\') {
                last = context.getData(8).toBytes()[0];
                stringBuilder.append((char)last);
            } else {
                stringBuilder.append((char) last);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
