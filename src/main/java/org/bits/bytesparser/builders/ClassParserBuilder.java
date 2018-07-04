package org.bits.bytesparser.builders;

import org.bits.array.BitArray;
import org.bits.bytesparser.parsers.ClassParser;
import org.bits.bytesparser.parsers.Parser;
import org.bits.bytesparser.valuegetters.ValueGetter;
import com.google.common.collect.Maps;

import java.util.Map;


/**
 * @author tomer
 * @since 5/30/17
 */
public class ClassParserBuilder<T> implements ParserBuilder<BitArray, T> {

    private ValueGetter<BitArray, T> builder;

    private final Map<String, Parser> fields;

    public ClassParserBuilder() {
        this(null, Maps.newHashMap());
    }

    public ClassParserBuilder(ValueGetter<BitArray, T> builder, Map<String, Parser> fields) {
        this.builder = builder;
        this.fields = fields;
    }

    @Override
    public ClassParser<T> build() {
        return new ClassParser<>(builder, fields);
    }

    public ClassParserBuilder setBuilder(ValueGetter<BitArray, T> builder) {
        this.builder = builder;
        return this;
    }

    public ClassParserBuilder<T> addField(String fieldName, Parser fieldParser) {
        fields.put(fieldName, fieldParser);
        return this;
    }
}
