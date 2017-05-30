package bytesparser.parsers;

import bits.array.BitArray;
import bytesparser.contexts.AbstractContext;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.valuegetters.ValueGetter;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author tomer
 * @since 5/29/17
 */
@Builder
@RequiredArgsConstructor
public class IntegerParser implements Parser<BitArray, Integer> {

    @NonNull
    private final ValueGetter<BitArray, Integer> sizeGetter;

    @Override
    public Integer parse(Context<BitArray> context) {
        int size = sizeGetter.get(context);
        byte[] source = context.getData(size * Byte.SIZE).toBytes();
        int value = 0;
        for (int i=0; i < size; i++) {
            value *= 16;
            value += source[i];
        }
        return value;
    }

    @Override
    public Integer parse(byte[] source) {
        return parse(new BytesContext<>(source));
    }
}
