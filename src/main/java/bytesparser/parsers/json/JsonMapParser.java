package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.parsers.MapParser;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;

import static bytesparser.parsers.json.JsonListParser.EMPTY_ITEM_ERROR;
import static bytesparser.parsers.json.JsonListParser.ITEM_SEPARATOR;
import static bytesparser.parsers.json.JsonParser.skipWhitespaces;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonMapParser<Value> extends MapParser<String, Value> {

    public static final char MAP_START = '{';
    public static final char MAP_END = '}';
    public static final char MAP_VALUE_SEPARATOR = ':';

    public JsonMapParser(JsonParser superParser, final Parser<BitArray, String> stringParser) {
        super(MAP_START, MAP_END, ITEM_SEPARATOR, context->{
            char last = skipWhitespaces(context);
            Preconditions.checkState(last != MAP_END, EMPTY_ITEM_ERROR);
            Preconditions.checkState(last != ITEM_SEPARATOR, EMPTY_ITEM_ERROR);
            Preconditions.checkState(last != MAP_VALUE_SEPARATOR, EMPTY_KEY_ERROR);
            return stringParser.parse(context);
        }, JsonParser::getAfterWhitespaces, context->{
            char last = skipWhitespaces(context);
            Preconditions.checkState(last != ITEM_SEPARATOR, EMPTY_VALUE_ERROR);
            Preconditions.checkState(last != MAP_END, EMPTY_VALUE_ERROR);
            return (Value) superParser.parse(context);
        }, JsonParser::getAfterWhitespaces);
    }
}
