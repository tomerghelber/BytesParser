package bytesparser.parsers;

import bytesparser.Context;
import bytesparser.valuegetters.ValueGetter;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author tomer
 * @since 5/29/17
 */
@Builder
@RequiredArgsConstructor
public class IntegerParser implements Parser<Integer> {

    @NonNull
    private final ValueGetter<Integer> sizeGetter;

    @Override
    public Integer parse(Context context) {
        int size = sizeGetter.get(context);
        byte[] source = context.getData(size * Byte.SIZE).toBytes();
        int value = 0;
        for (int i=0; i < size; i++) {
            value *= 16;
            value += source[i];
        }
        return value;
    }
}
