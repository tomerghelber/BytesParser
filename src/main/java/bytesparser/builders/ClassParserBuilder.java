package bytesparser.builders;

import bits.array.BitArray;
import bytesparser.parsers.ClassParser;
import bytesparser.parsers.Parser;
import bytesparser.valuegetters.ValueGetter;
import com.google.common.collect.Lists;
import javafx.util.Pair;
import lombok.AllArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author tomer
 * @since 5/30/17
 */
@AllArgsConstructor(access = PRIVATE)
public class ClassParserBuilder<T> implements ParserBuilder<BitArray, T> {

    private ValueGetter<BitArray, T> builder;

    private final List<Pair<String, Parser>> fields;

    public ClassParserBuilder() {
        this(null,  Lists.newArrayList());
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
        fields.add(new Pair<>(fieldName, fieldParser));
        return this;
    }
}
