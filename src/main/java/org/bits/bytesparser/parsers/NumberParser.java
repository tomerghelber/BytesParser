package org.bits.bytesparser.parsers;

import org.bits.array.BitArray;
import org.bits.bytesparser.contexts.BytesContext;
import org.bits.bytesparser.contexts.Context;
import com.google.common.base.Preconditions;

import static org.bits.bytesparser.parsers.json.JsonParser.getChar;
import static org.bits.bytesparser.parsers.json.JsonParser.peekChar;

/**
 * @author tomer
 * @since 6/4/17
 */
public class NumberParser implements Parser<BitArray, Number> {

    @Override
        public Number parse(Context<BitArray> context) {
            char last = getChar(context);
            Preconditions.checkState(last == '-' || Character.isDigit(last));
            StringBuilder stringBuilder = new StringBuilder().append(last);
            while (context.getRemand() >= Byte.SIZE && Character.isDigit(peekChar(context))) {
                stringBuilder.append(getChar(context));
            }
            if (context.getRemand() >= Byte.SIZE && '.' == peekChar(context)) {
                stringBuilder.append(getChar(context));
                Preconditions.checkState(Character.isDigit(peekChar(context)));
                while (context.getRemand() >= Byte.SIZE && Character.isDigit(peekChar(context))) {
                    stringBuilder.append(getChar(context));
                }
                return Double.parseDouble(stringBuilder.toString());
            } else {
                return Long.parseLong(stringBuilder.toString());
            }
        }

        @Override
        public Number parse(byte[] source) {
            return parse(new BytesContext(source));
        }
}
