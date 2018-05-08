package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;

import static bytesparser.parsers.json.JsonParser.peekByte;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonNumberParser implements Parser<BitArray, Double> {

    @Override
        public Double parse(Context<BitArray> context) {
            byte last = context.getData(8).toBytes()[0];
            Preconditions.checkState(last == '-' || ('0' <= last && last <= '9'));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((char) last);
            while (context.getRemand() >= 8 && '0' <= (last = peekByte(context)) && last <= '9') {
                stringBuilder.append((char) context.getData(8).toBytes()[0]);
            }
            if (context.getRemand() >= 8 && '.' == peekByte(context)) {
                stringBuilder.append((char) context.getData(8).toBytes()[0]);
            }
            while (context.getRemand() >= 8 && '0' <= (last = peekByte(context)) && last <= '9') {
                stringBuilder.append((char) context.getData(8).toBytes()[0]);
            }
            return Double.parseDouble(stringBuilder.toString());
        }

        @Override
        public Double parse(byte[] source) {
            return parse(new BytesContext(source));
        }
}
