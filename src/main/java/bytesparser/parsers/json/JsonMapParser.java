package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static bytesparser.parsers.json.JsonListParser.EMPTY_ITEM_ERROR;

/**
 * @author tomer
 * @since 6/4/17
 */
@RequiredArgsConstructor
public class JsonMapParser<Key, Value> implements Parser<BitArray, Map<Key, Value>> {

    public static final String EMPTY_KEY_ERROR = "Found an empty key";
    public static final String EMPTY_VALUE_ERROR = "Found an empty value";

    private final Parser<BitArray, Object> superParser;

    @Override
    public Map<Key, Value> parse(Context<BitArray> context) {
        Map<Key, Value> map = Maps.newHashMap();
        byte last = context.getData(8).toBytes()[0];
        Preconditions.checkState(last == '{');
        last = context.peekData(8).toBytes()[0];
        if (last != '}') {
            do {
                while ((context.peekData(8).toBytes()[0]) == ' ') {
                    context.getData(8);
                }
                Preconditions.checkState(context.peekData(8).toBytes()[0] != '}', EMPTY_ITEM_ERROR);
                Preconditions.checkState(context.peekData(8).toBytes()[0] != ',', EMPTY_ITEM_ERROR);
                Preconditions.checkState(context.peekData(8).toBytes()[0] != ':', EMPTY_KEY_ERROR);
                Key key = (Key) superParser.parse(context);

                while ((last = context.getData(8).toBytes()[0]) == ' ') ;
                Preconditions.checkState(last == ':');

                while ((context.peekData(8).toBytes()[0]) == ' ') {
                    context.getData(8);
                }
                Preconditions.checkState(context.peekData(8).toBytes()[0] != ',', EMPTY_VALUE_ERROR);
                Preconditions.checkState(context.peekData(8).toBytes()[0] != '}', EMPTY_VALUE_ERROR);
                Value value = (Value) superParser.parse(context);
                while ((last = context.getData(8).toBytes()[0]) == ' ') ;
                map.put(key, value);
            } while (last == ',');
        }
        Preconditions.checkState(last == '}');
        return map;
    }

    @Override
    public Map<Key, Value> parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
