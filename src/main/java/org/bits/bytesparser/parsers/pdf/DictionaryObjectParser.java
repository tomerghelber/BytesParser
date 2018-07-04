package org.bits.bytesparser.parsers.pdf;

import org.bits.array.BitArray;
import org.bits.bytesparser.parsers.MapParser;
import org.bits.bytesparser.parsers.Parser;
import org.bits.bytesparser.parsers.json.JsonParser;

/**
 * @author tomer
 * @since 5/8/18
 */
public class DictionaryObjectParser extends MapParser<Object, Object> {

    public static final byte[] DICTIONARY_START = "<<".getBytes();
    public static final byte[] DICTIONARY_END = ">>".getBytes();
    public static final char DICTIONARY_SEPARATOR = ' ';
    public static final char MAP_VALUE_SEPARATOR = '\n';

    public DictionaryObjectParser(char mapStart, char mapEnd, final PDFParser superParser, final Parser<BitArray, Object> nameParser) {
        super(mapStart, mapEnd, DICTIONARY_SEPARATOR, MAP_VALUE_SEPARATOR, nameParser::parse, JsonParser::getChar, superParser::parse, JsonParser::getChar);
    }
}
