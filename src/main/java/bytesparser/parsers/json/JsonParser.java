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

    private Parser<BitArray, Integer> integerParser;

    @Override
    public Object parse(Context<BitArray> context) {
        initialise();
        byte last;
        while ((last = context.peekData(8).toBytes()[0]) == ' ') {
            context.getData(8);
        }
        Parser<BitArray, ?> parser = ImmutableMap.of(
                '"', stringParser,
                '{', mapParser,
                '[', listParser
        ).getOrDefault((char) last, integerParser);
        return parser.parse(context);
    }

    private void initialise() {
        if (stringParser == null) {
            stringParser = new JsonStringParser();
        }
        if (mapParser == null) {
            mapParser = new JsonMapParser(this);
        }
        if (listParser == null) {
            listParser = new JsonListParser(this);
        }
        if (integerParser == null) {
            integerParser = new JsonIntegerParser();
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