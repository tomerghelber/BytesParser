package org.bits.bytesparser.parsers.json;

import org.bits.bytesparser.parsers.ListParser;
import com.google.common.base.Preconditions;

import static org.bits.bytesparser.parsers.json.JsonParser.skipWhitespaces;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonListParser<Item> extends ListParser<Item> {

    public static final char LIST_START = '[';
    public static final char LIST_END = ']';
    public static final char ITEM_SEPARATOR = ',';

    public JsonListParser(final JsonParser superParser) {
        super(LIST_START, LIST_END, ITEM_SEPARATOR, context -> {
            char last = skipWhitespaces(context);
            Preconditions.checkState(last != ITEM_SEPARATOR, EMPTY_ITEM_ERROR);
            Preconditions.checkState(last != LIST_END, EMPTY_ITEM_ERROR);
            return (Item) superParser.parse(context);
        }, JsonParser::getAfterWhitespaces);
    }
}
