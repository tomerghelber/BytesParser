package bytesparser.parsers;

import bits.array.BitArray;
import bytesparser.contexts.AbstractContext;
import bytesparser.contexts.BytesContext;
import bytesparser.contexts.Context;
import bytesparser.valuegetters.ValueGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;

/**
 * @author tomer
 * @since 5/29/17
 */
@RequiredArgsConstructor
public class StringParser implements Parser<BitArray, String> {

    @NonNull
    private final ValueGetter<BitArray, Integer> offsetGetter;

    @NonNull
    private final ValueGetter<BitArray, Integer> lengthGetter;

    @NonNull
    private final ValueGetter<BitArray, Charset> charsetGetter;

    @Override
    public String parse(Context<BitArray> context) {
        int offset = offsetGetter.get(context) * Byte.SIZE;
        int length = lengthGetter.get(context) * Byte.SIZE;
        byte[] source = context.getData(length).cut(offset, length).toBytes();

        Charset charset = charsetGetter.get(context);
        return new String(source, charset);
    }

    @Override
    public String parse(byte[] source) {
        return parse(new BytesContext<>(source));
    }
}
