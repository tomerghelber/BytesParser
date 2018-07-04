package org.bits.bytesparser.parsers.pdf;

import org.bits.array.BitArray;
import org.bits.bytesparser.contexts.BytesContext;
import org.bits.bytesparser.contexts.Context;
import org.bits.bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;

import static org.bits.bytesparser.parsers.json.JsonParser.getChar;

/**
 * @author tomer
 * @since 5/8/18
 */
public class StringObjectParser implements Parser<BitArray, String> {

    public static final char NORMAL_STRING_START = '(';
    public static final char NORMAL_STRING_END = ')';
    public static final char HEX_STRING_START = '<';
    public static final char HEX_STRING_END = '>';

    @Override
    public String parse(Context<BitArray> context) {
        char last = getChar(context);
        Preconditions.checkState((last == NORMAL_STRING_START) || (last == HEX_STRING_START));
        if (last == NORMAL_STRING_START) {
            return simpleString(context);
        } else {
            return hexString(context);
        }
    }

    @Override
    public String parse(byte[] source) {
        return parse(new BytesContext(source));
    }

    private String hexString(Context<BitArray> context) {
        char last = getChar(context);
        StringBuilder string = new StringBuilder();
        while (last != HEX_STRING_END) {
            StringBuilder charBuilder = new StringBuilder().append(last);
            last = getChar(context);
            if (last == HEX_STRING_END) {
                charBuilder.append('0');
            } else {
                charBuilder.append(last);
                last = getChar(context);
            }
            string.append((char) Byte.parseByte(charBuilder.toString(), 16));
        }
        return string.toString();
    }

    private String simpleString(Context<BitArray> context) {
        char last = getChar(context);
        StringBuilder string = new StringBuilder();
        while (last != NORMAL_STRING_END) {
            string.append(last);
            last = getChar(context);
        }
        return string.toString();
    }
}
