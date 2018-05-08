package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonParser implements Parser<BitArray, Object> {

    private Parser<BitArray, String> stringParser;

    private Parser<BitArray, Map> mapParser;

    private Parser<BitArray, List> listParser;

    private Parser<BitArray, Double> numberParser;

    public static byte getAfterWhitespaces(Context<BitArray> context) {
        byte last;
        while ((last = getByte(context)) == ' ');
        return last;
    }

    public static byte skipWhitespaces(Context<BitArray> context) {
        byte last;
        while ((last = peekByte(context)) == ' ') {
            getByte(context);
        }
        return last;
    }

    public static byte peekByte(Context<BitArray> context) {
        return context.peekData(8).toBytes()[0];
    }

    public static byte getByte(Context<BitArray> context) {
        return context.getData(8).toBytes()[0];
    }

    @Override
    public Object parse(Context<BitArray> context) {
        initialise();
        byte last = skipWhitespaces(context);
        Parser<BitArray, ?> parser = ImmutableMap.of(
                '"', stringParser,
                '{', mapParser,
                '[', listParser
        ).getOrDefault((char) last, numberParser);
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
            numberParser = new JsonNumberParser();
        }
    }

    @Override
    public Object parse(byte[] source) {
        Context<BitArray> context = new BytesContext(source);
        Object object = parse(context);
        while (context.getRemand() > 0 && (context.getData(8).toBytes()[0]) == ' ');
        Preconditions.checkState(context.getRemand() == 0, "Didn't finished the buffer");
        return object;
    }
}