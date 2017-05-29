package bytesparser.parsers;

import bytesparser.Context;
import bytesparser.valuegetters.ValueGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author tomer
 * @since 5/29/17
 */
@RequiredArgsConstructor
public class BytesParser implements Parser<byte[]> {

    @NonNull
    private final ValueGetter<Integer> offsetGetter;

    @NonNull
    private final ValueGetter<Integer> lengthGetter;

    @Override
    public byte[] parse(Context context) {
        int offset = offsetGetter.get(context) * Byte.SIZE;
        int length = lengthGetter.get(context) * Byte.SIZE;
        return context.getData(length).cut(offset, length).toBytes();
    }
}
