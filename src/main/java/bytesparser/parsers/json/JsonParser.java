package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.parsers.NumberParser;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import static bits.Bits.BITS_IN_BYTE;
import static bytesparser.parsers.json.JsonListParser.LIST_START;
import static bytesparser.parsers.json.JsonMapParser.MAP_START;
import static bytesparser.parsers.json.JsonStringParser.STRING_START;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonParser implements Parser<BitArray, Object> {

    public static final char JSON_ESCAPING = '\\';

    private Parser<BitArray, String> stringParser;

    private Parser<BitArray, Map> mapParser;

    private Parser<BitArray, List> listParser;

    private Parser<BitArray, Number> numberParser;

    public static char getAfterWhitespaces(Context<BitArray> context) {
        char last;
        while (Character.isWhitespace(last = getChar(context)));
        return last;
    }

    public static char skipWhitespaces(Context<BitArray> context) {
        char last;
        while (Character.isWhitespace(last = peekChar(context))) {
            getByte(context);
        }
        return last;
    }

    public static char peekChar(Context<BitArray> context) {
        return (char) context.peekData(BITS_IN_BYTE).toBytes()[0];
    }

    private static byte getByte(Context<BitArray> context) {
        return context.getData(BITS_IN_BYTE).toBytes()[0];
    }

    public static char getChar(Context<BitArray> context) {
        return (char) getByte(context);
    }

    @Override
    public Object parse(Context<BitArray> context) {
        initialise();
        char last = skipWhitespaces(context);
        Parser<BitArray, ?> parser = ImmutableMap.of(
                STRING_START, stringParser,
                MAP_START, mapParser,
                LIST_START, listParser
        ).getOrDefault(last, numberParser);
        return parser.parse(context);
    }

    private void initialise() {
        if (stringParser == null) {
            stringParser = new JsonStringParser();
        }
        if (mapParser == null) {
            mapParser = new JsonMapParser(this, stringParser);
        }
        if (listParser == null) {
            listParser = new JsonListParser(this);
        }
        if (numberParser == null) {
            numberParser = new NumberParser();
        }
    }

    @Override
    public Object parse(byte[] source) {
        Context<BitArray> context = new BytesContext(source);
        Object object = parse(context);
        while (context.getRemand() > 0 && Character.isWhitespace(getChar(context)));
        Preconditions.checkState(context.getRemand() == 0, "Didn't finished the buffer");
        return object;
    }
}