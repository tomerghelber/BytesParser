package org.bits.bytesparser.parsers;

import org.bits.array.BitArray;
import org.bits.bytesparser.contexts.BytesContext;
import org.bits.bytesparser.contexts.Context;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.Function;

import static org.bits.bytesparser.parsers.json.JsonParser.getChar;
import static org.bits.bytesparser.parsers.json.JsonParser.peekChar;

/**
 * @author tomer
 * @since 6/4/17
 */
public class ListParser<Item> implements Parser<BitArray, List<Item>> {

    /* --- Constants --- */

    public static final String EMPTY_ITEM_ERROR = "Found an empty item";

    /* --- Members --- */

    private final char listStart;
    private final char listEnd;
    private final char itemSeparator;
    private final Function<Context<BitArray>, Item> parseItem;
    private final Function<Context<BitArray>, Character> parseItemSeparator;

    /* --- Constructors --- */

    public ListParser(char listStart, char listEnd, char itemSeparator, Function<Context<BitArray>, Item> parseItem, Function<Context<BitArray>, Character> parseItemSeparator) {
        this.listStart = listStart;
        this.listEnd = listEnd;
        this.itemSeparator = itemSeparator;
        this.parseItem = parseItem;
        this.parseItemSeparator = parseItemSeparator;
    }

    /* --- Parser Impl. --- */

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
