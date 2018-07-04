package org.bits.bytesparser.parsers;

import org.bits.array.BitArray;
import org.bits.bytesparser.contexts.BytesContext;
import org.bits.bytesparser.contexts.Context;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static org.bits.bytesparser.parsers.json.JsonParser.getChar;
import static org.bits.bytesparser.parsers.json.JsonParser.peekChar;

/**
 * @author tomer
 * @since 6/4/17
 */
public class MapParser<Key, Value> implements Parser<BitArray, Map<Key, Value>> {

    /* --- Constants --- */

    public static final String EMPTY_KEY_ERROR = "Found an empty key";
    public static final String EMPTY_VALUE_ERROR = "Found an empty value";

    /* --- Members --- */

    public final char mapStart;
    public final char mapEnd;
    public final char mapValueSeparator;
    public final char itemSeparator;

    private final Function<Context<BitArray>, Key> keyParser;
    private final Function<Context<BitArray>, Character> keyValueSeparatorParser;
    private final Function<Context<BitArray>, Value> valueParser;
    private final Function<Context<BitArray>, Character> itemSeparatorParser;

    /* --- Constructors --- */

    public MapParser(char mapStart, char mapEnd, char mapValueSeparator, char itemSeparator,
                     Function<Context<BitArray>, Key> keyParser,
                     Function<Context<BitArray>, Character> keyValueSeparatorParser,
                     Function<Context<BitArray>, Value> valueParser,
                     Function<Context<BitArray>, Character> itemSeparatorParser) {
        this.mapStart = mapStart;
        this.mapEnd = mapEnd;
        this.mapValueSeparator = mapValueSeparator;
        this.itemSeparator = itemSeparator;
        this.keyParser = keyParser;
        this.keyValueSeparatorParser = keyValueSeparatorParser;
        this.valueParser = valueParser;
        this.itemSeparatorParser = itemSeparatorParser;
    }

    /* --- Parser Impl. --- */

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
