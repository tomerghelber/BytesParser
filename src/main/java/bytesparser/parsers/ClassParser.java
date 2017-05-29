package bytesparser.parsers;

import bytesparser.Context;
import bytesparser.valuegetters.ValueGetter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import javafx.util.Pair;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author tomer
 * @since 5/29/17
 */
@RequiredArgsConstructor
public class ClassParser<T> implements Parser<T> {

    private final ValueGetter<T> builder;

    private final List<Pair<String, Parser>> fields;

    @Override
    public T parse(Context context) {
        Map<String, Object> fieldsValues = Maps.newHashMap();
        for (Pair<String, Parser> fieldParser: fields) {
            String fieldName = fieldParser.getKey();
            Object fieldValue = fieldParser.getValue().parse(context);
            fieldsValues.put(fieldName, fieldValue);
            context.addField(fieldName, fieldValue);
        }

        T t = builder.get(context);
        Set<Field> fieldSet = Sets.newHashSet(t.getClass().getDeclaredFields());
        for (Field field: fieldSet) {
            if (fieldsValues.containsKey(field.getName())) {
                field.setAccessible(true);
                try {
                    field.set(t, fieldsValues.get(field.getName()));
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return t;
    }
}
