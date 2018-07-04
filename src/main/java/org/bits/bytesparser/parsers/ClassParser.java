package org.bits.bytesparser.parsers;

import org.bits.array.BitArray;
import org.bits.bytesparser.contexts.BytesContext;
import org.bits.bytesparser.contexts.Context;
import org.bits.bytesparser.valuegetters.ValueGetter;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * @author tomer
 * @since 5/29/17
 */
public class ClassParser<T> implements Parser<BitArray, T> {

    /* --- Members --- */

    private final ValueGetter<BitArray, T> builder;

    private final Map<String, Parser> fields;

    /* --- Constructors --- */

    public ClassParser(ValueGetter<BitArray, T> builder, Map<String, Parser> fields) {
        this.builder = builder;
        this.fields = fields;
    }

    /* --- Constructors --- */

    @Override
    public T parse(Context<BitArray> context) {
        Map<String, Object> fieldsValues = Maps.newHashMap();
        for (Map.Entry<String, Parser> fieldParser: fields.entrySet()) {
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

    @Override
    public T parse(byte[] source) {
        return parse(new BytesContext(source));
    }
}
