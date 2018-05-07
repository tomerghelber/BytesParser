package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author tomer
 * @since 6/4/17
 */
@RequiredArgsConstructor
public class JsonListParser<Item> implements Parser<BitArray, List<Item>> {

    public static final String EMPTY_ITEM_ERROR = "Found an empty item";

    private final Parser<BitArray, Object> superParser;

    @Override
    public List<Item> parse(Context<BitArray> context) {
        List<Item> list = Lists.newArrayList();
        byte last = context.getData(8).toBytes()[0];
        Preconditions.checkState(last == '[');
        last = context.peekData(8).toBytes()[0];
        if (last != ']') {
            do {
                while ((context.peekData(8).toBytes()[0]) == ' ') {
                    context.getData(8);
                }
                Preconditions.checkState(context.peekData(8).toBytes()[0] != ',', EMPTY_ITEM_ERROR);
                Preconditions.checkState(context.peekData(8).toBytes()[0] != ']', EMPTY_ITEM_ERROR);
                list.add((Item) superParser.parse(context));
                while ((context.peekData(8).toBytes()[0]) == ' ') {
                    context.getData(8);
                }
                last = context.getData(8).toBytes()[0];
            } while (last == ',');
        }
        Preconditions.checkState(last == ']');
        return list;
    }

    @Override
    public List<Item> parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
