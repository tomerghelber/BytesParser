package org.bits.bytesparser.parsers.pdf;

import org.bits.bytesparser.parsers.ListParser;
import org.bits.bytesparser.parsers.json.JsonParser;

/**
 * @author tomer
 * @since 5/8/18
 */
public class ArrayObjectParser extends ListParser<Object> {

    public static final char ARRAY_START = '[';
    public static final char ARRAY_END = ']';
    public static final char ITEM_SEPARATOR = ' ';

    public ArrayObjectParser(PDFParser superParser) {
        super(ARRAY_START, ARRAY_END, ITEM_SEPARATOR, superParser::parse, JsonParser::getChar);
    }
}
