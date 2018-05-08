package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static bytesparser.parsers.json.JsonParser.*;

/**
 * @author tomer
 * @since 6/4/17
 */
@RequiredArgsConstructor
public class JsonListParser<Item> implements Parser<BitArray, List<Item>> {

    public static final String EMPTY_ITEM_ERROR = "Found an empty item";

    public static final char LIST_START = '[';
    public static final char LIST_END = ']';
    public static final char ITEM_SEPARATOR = ',';

    private final Parser<BitArray, Object> superParser;

    @Override
    public List<Item> parse(Context<BitArray> context) {
        ImmutableList.Builder<Item> list = ImmutableList.builder();
        char last = getChar(context);
        Preconditions.checkState(last == LIST_START);
        last = peekChar(context);
        while (last != LIST_END) {
            last = skipWhitespaces(context);
            Preconditions.checkState(last != ITEM_SEPARATOR, EMPTY_ITEM_ERROR);
            Preconditions.checkState(last != LIST_START, EMPTY_ITEM_ERROR);
            list.add((Item) superParser.parse(context));
            last = getAfterWhitespaces(context);
            Preconditions.checkState(last == ITEM_SEPARATOR || last == LIST_END);
        }
        return list.build();
    }

    @Override
    public List<Item> parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
