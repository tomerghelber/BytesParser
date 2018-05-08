package bytesparser.parsers;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static bytesparser.parsers.json.JsonParser.getChar;
import static bytesparser.parsers.json.JsonParser.peekChar;

/**
 * @author tomer
 * @since 6/4/17
 */
@RequiredArgsConstructor
public class MapParser<Key, Value> implements Parser<BitArray, Map<Key, Value>> {

    public static final String EMPTY_KEY_ERROR = "Found an empty key";
    public static final String EMPTY_VALUE_ERROR = "Found an empty value";

    public final char mapStart;
    public final char mapEnd;
    public final char itemSeparator;
    public final char mapValueSeparator = ':';

    private final Function<Context<BitArray>, Key> keyParser;
    private final Function<Context<BitArray>, Character> keyValueSeparatorParser;
    private final Function<Context<BitArray>, Value> valueParser;
    private final Function<Context<BitArray>, Character> itemSeparatorParser;

    @Override
    public Map<Key, Value> parse(Context<BitArray> context) {
        ImmutableMap.Builder<Key, Value> map = ImmutableMap.builder();
        Preconditions.checkState(getChar(context) == mapStart);
        char last = peekChar(context);
        while (last != mapEnd) {
            Key key = keyParser.apply(context);

            // Parser key-value separator
            last = keyValueSeparatorParser.apply(context);
            Preconditions.checkState(last == mapValueSeparator);

            // Parser value
            Value value = valueParser.apply(context);

            // Add to map
            map.put(key, value);

            // Parse item separator
            last = itemSeparatorParser.apply(context);
            Preconditions.checkState(last == itemSeparator || last == mapEnd);
        }
        return map.build();
    }

    @Override
    public Map<Key, Value> parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
