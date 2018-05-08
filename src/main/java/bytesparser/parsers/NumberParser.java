package bytesparser.parsers;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import com.google.common.base.Preconditions;

import static bits.Bits.BITS_IN_BYTE;
import static bytesparser.parsers.json.JsonParser.getChar;
import static bytesparser.parsers.json.JsonParser.peekChar;

/**
 * @author tomer
 * @since 6/4/17
 */
public class NumberParser implements Parser<BitArray, Double> {

    @Override
        public Double parse(Context<BitArray> context) {
            char last = getChar(context);
            Preconditions.checkState(last == '-' || Character.isDigit(last));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(last);
            while (context.getRemand() >= BITS_IN_BYTE && Character.isDigit(peekChar(context))) {
                stringBuilder.append(getChar(context));
            }
            if (context.getRemand() >= BITS_IN_BYTE && '.' == peekChar(context)) {
                stringBuilder.append(getChar(context));
                Preconditions.checkState(Character.isDigit(peekChar(context)));
                while (context.getRemand() >= BITS_IN_BYTE && Character.isDigit(peekChar(context))) {
                    stringBuilder.append(getChar(context));
                }
            }
            return Double.parseDouble(stringBuilder.toString());
        }

        @Override
        public Double parse(byte[] source) {
            return parse(new BytesContext(source));
        }
}
