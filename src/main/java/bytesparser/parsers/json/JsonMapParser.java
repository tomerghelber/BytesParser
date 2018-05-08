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
import static bytesparser.parsers.json.JsonListParser.ITEM_SEPARATOR;
import static bytesparser.parsers.json.JsonParser.*;

/**
 * @author tomer
 * @since 6/4/17
 */
@RequiredArgsConstructor
public class JsonMapParser<Value> implements Parser<BitArray, Map<String, Value>> {

    public static final String EMPTY_KEY_ERROR = "Found an empty key";
    public static final String EMPTY_VALUE_ERROR = "Found an empty value";

    public static final char MAP_START = '{';
    public static final char MAP_END = '}';
    public static final char MAP_VALUE_SEPARATOR = ':';

    private final Parser<BitArray, Object> superParser;
    private final Parser<BitArray, String> stringParser;

    @Override
    public Map<String, Value> parse(Context<BitArray> context) {
        ImmutableMap.Builder<String, Value> map = ImmutableMap.builder();
        char last = getChar(context);
        Preconditions.checkState(last == MAP_START);
        last = peekChar(context);
        if (last != MAP_END) {
            do {
                last = skipWhitespaces(context);
                Preconditions.checkState(last != MAP_END, EMPTY_ITEM_ERROR);
                Preconditions.checkState(last != ITEM_SEPARATOR, EMPTY_ITEM_ERROR);
                Preconditions.checkState(last != MAP_VALUE_SEPARATOR, EMPTY_KEY_ERROR);
                String key = stringParser.parse(context);

                last = getAfterWhitespaces(context);
                Preconditions.checkState(last == MAP_VALUE_SEPARATOR);

                last = skipWhitespaces(context);
                Preconditions.checkState(last != ITEM_SEPARATOR, EMPTY_VALUE_ERROR);
                Preconditions.checkState(last != MAP_END, EMPTY_VALUE_ERROR);
                Value value = (Value) superParser.parse(context);

                last = getAfterWhitespaces(context);

                map.put(key, value);
            } while (last == ITEM_SEPARATOR);
        }
        Preconditions.checkState(last == MAP_END);
        return map.build();
    }

    @Override
    public Map<String, Value> parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
