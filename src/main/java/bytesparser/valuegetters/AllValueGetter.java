package bytesparser.valuegetters;

import bytesparser.Context;
import lombok.RequiredArgsConstructor;

/**
 * @author tomer
 * @since 5/29/17
 */
public class AllValueGetter implements ValueGetter<Integer> {

    @Override
    public Integer get(Context context) {
        return new Double(StrictMath.ceil(context.getRemand() / Byte.SIZE)).intValue();
    }
}
