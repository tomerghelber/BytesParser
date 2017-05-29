package bytesparser.parsers;

import bytesparser.Context;
import bytesparser.valuegetters.ValueGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;

/**
 * @author tomer
 * @since 5/29/17
 */
@RequiredArgsConstructor
public class StringParser implements Parser<String> {

    @NonNull
    private final ValueGetter<Integer> offsetGetter;

    @NonNull
    private final ValueGetter<Integer> lengthGetter;

    @NonNull
    private final ValueGetter<Charset> charsetGetter;

    @Override
    public String parse(Context context) {
        int offset = offsetGetter.get(context) * Byte.SIZE;
        int length = lengthGetter.get(context) * Byte.SIZE;
        byte[] source = context.getData(length).cut(offset, length).toBytes();

        Charset charset = charsetGetter.get(context);
        return new String(source, charset);
    }
}
