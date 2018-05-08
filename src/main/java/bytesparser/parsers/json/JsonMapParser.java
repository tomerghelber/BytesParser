package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static bytesparser.parsers.json.JsonListParser.EMPTY_ITEM_ERROR;
import static bytesparser.parsers.json.JsonParser.*;

/**
 * @author tomer
 * @since 6/4/17
 */
@RequiredArgsConstructor
public class JsonMapParser<Value> implements Parser<BitArray, Map<String, Value>> {

    public static final String EMPTY_KEY_ERROR = "Found an empty key";
    public static final String EMPTY_VALUE_ERROR = "Found an empty value";

    private final Parser<BitArray, Object> superParser;
    private final Parser<BitArray, String> stringParser;

    @Override
    public Map<String, Value> parse(Context<BitArray> context) {
        ImmutableMap.Builder<String, Value> map = ImmutableMap.builder();
        byte last = getByte(context);
        Preconditions.checkState(last == '{');
        last = peekByte(context);
        if (last != '}') {
            do {
                last = skipWhitespaces(context);
                Preconditions.checkState(last != '}', EMPTY_ITEM_ERROR);
                Preconditions.checkState(last != ',', EMPTY_ITEM_ERROR);
                Preconditions.checkState(last != ':', EMPTY_KEY_ERROR);
                String key = stringParser.parse(context);

                last = getAfterWhitespaces(context);
                Preconditions.checkState(last == ':');

                last = skipWhitespaces(context);
                Preconditions.checkState(last != ',', EMPTY_VALUE_ERROR);
                Preconditions.checkState(last != '}', EMPTY_VALUE_ERROR);
                Value value = (Value) superParser.parse(context);

                last = getAfterWhitespaces(context);

                map.put(key, value);
            } while (last == ',');
        }
        Preconditions.checkState(last == '}');
        return map.build();
    }

    @Override
    public Map<String, Value> parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
