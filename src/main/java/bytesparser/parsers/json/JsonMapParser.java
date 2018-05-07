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
public class JsonMapParser implements Parser<BitArray, Map> {

    public static final String EMPTY_KEY_ERROR = "Found an empty key";
    public static final String EMPTY_VALUE_ERROR = "Found an empty value";

    private final Parser<BitArray, Object> superParser;

    @Override
    public Map parse(Context<BitArray> context) {
        Map map = Maps.newHashMap();
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
                Object key = superParser.parse(context);

                while ((last = context.getData(8).toBytes()[0]) == ' ') ;
                Preconditions.checkState(last == ':');

                while ((context.peekData(8).toBytes()[0]) == ' ') {
                    context.getData(8);
                }
                Preconditions.checkState(context.peekData(8).toBytes()[0] != ',', EMPTY_VALUE_ERROR);
                Preconditions.checkState(context.peekData(8).toBytes()[0] != '}', EMPTY_VALUE_ERROR);
                Object value = superParser.parse(context);
                while ((last = context.getData(8).toBytes()[0]) == ' ') ;
                map.put(key, value);
            } while (last == ',');
        }
        Preconditions.checkState(last == '}');
        return map;
    }

    @Override
    public Map parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
