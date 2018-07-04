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
public class PDFParser implements Parser<BitArray, Object> {
    @Override
    public Object parse(Context<BitArray> context) {
        return null;
    }

    @Override
    public Object parse(byte[] source) {
        Context<BitArray> context = new BytesContext(source);
        Object object = parse(context);
        while (context.getRemand() > 0 && Character.isWhitespace(getChar(context)));
        Preconditions.checkState(context.getRemand() == 0, "Didn't finished the buffer");
        return object;
    }
}
