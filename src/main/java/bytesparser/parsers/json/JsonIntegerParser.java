package bytesparser.parsers.json;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.parsers.Parser;
import com.google.common.base.Preconditions;

/**
 * @author tomer
 * @since 6/4/17
 */
public class JsonIntegerParser implements Parser<BitArray, Integer> {

    @Override
        public Integer parse(Context<BitArray> context) {
            byte last = context.getData(8).toBytes()[0];
            Preconditions.checkState(last == '-' || ('0' <= last && last <= '9'));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((char) last);
            while (context.getRemand() >= 8 && '0' <= (last = context.peekData(8).toBytes()[0]) && last <= '9') {
                stringBuilder.append((char) context.getData(8).toBytes()[0]);
            }
            return Integer.parseInt(stringBuilder.toString());
        }

        @Override
        public Integer parse(byte[] source) {
            return parse(new BytesContext(source));
        }
}
