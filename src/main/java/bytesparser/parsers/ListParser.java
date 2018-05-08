package bytesparser.parsers;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

import static bytesparser.parsers.json.JsonParser.getChar;
import static bytesparser.parsers.json.JsonParser.peekChar;

/**
 * @author tomer
 * @since 6/4/17
 */
@RequiredArgsConstructor
public class ListParser<Item> implements Parser<BitArray, List<Item>> {

    public static final String EMPTY_ITEM_ERROR = "Found an empty item";

    private final char listStart;
    private final char listEnd;
    private final char itemSeparator;
    private final Function<Context<BitArray>, Item> parseItem;
    private final Function<Context<BitArray>, Character> parseItemSeparator;

    @Override
    public List<Item> parse(Context<BitArray> context) {
        ImmutableList.Builder<Item> list = ImmutableList.builder();
        Preconditions.checkState(getChar(context) == listStart);
        char last = peekChar(context);
        while (last != listEnd) {
            // Parser item
            Item item = parseItem.apply(context);

            // Add Item
            list.add(item);

            // Parse item separator
            last = parseItemSeparator.apply(context);
            Preconditions.checkState(last == itemSeparator || last == listEnd);
        }
        return list.build();
    }

    @Override
    public List<Item> parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
