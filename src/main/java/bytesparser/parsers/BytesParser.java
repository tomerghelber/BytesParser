package bytesparser.parsers;

import bits.array.BitArray;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.valuegetters.ValueGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author tomer
 * @since 5/29/17
 */
@RequiredArgsConstructor
public class BytesParser implements Parser<BitArray, byte[]> {

    @NonNull
    private final ValueGetter<BitArray, Integer> offsetGetter;

    @NonNull
    private final ValueGetter<BitArray, Integer> lengthGetter;

    @Override
    public byte[] parse(Context<BitArray> context) {
        int offset = offsetGetter.get(context) * Byte.SIZE;
        int length = lengthGetter.get(context) * Byte.SIZE;
        return context.getData(length).cut(offset, length).toBytes();
    }

    @Override
    public byte[] parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
