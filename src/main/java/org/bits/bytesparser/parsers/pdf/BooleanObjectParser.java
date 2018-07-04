package org.bits.bytesparser.parsers.pdf;

import org.bits.array.BitArray;
import org.bits.bytesparser.contexts.BytesContext;
import org.bits.bytesparser.contexts.Context;
import org.bits.bytesparser.parsers.Parser;

import java.util.Arrays;

import static org.bits.bytesparser.parsers.json.JsonParser.getChar;

/**
 * @author tomer
 * @since 5/8/18
 */
public class BooleanObjectParser implements Parser<BitArray, Boolean> {
    private static final byte[] TRUE = "true".getBytes();

    @Override
    public Boolean parse(Context<BitArray> context) {
        byte[] data = context.getData(4 * Byte.SIZE).toBytes();
        if (Arrays.equals(TRUE, data)) {
            return true;
        }
        getChar(context);
        return false;
    }

    @Override
    public Boolean parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
