package bytesparser.builders;

import bytesparser.parsers.ClassParser;
import bytesparser.parsers.Parser;
import bytesparser.valuegetters.ValueGetter;
import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.util.List;

/**
 * @author tomer
 * @since 5/30/17
 */
public class ClassParserBuilder<T> implements ParserBuilder<T> {

    ValueGetter<T> builder=null;
    List<Pair<String, Parser>> fields = Lists.newArrayListWithCapacity(5);

    @Override
    public ClassParser<T> build() {
        return new ClassParser<>(builder, fields);
    }

    public ClassParserBuilder setBuilder(ValueGetter<T> builder) {
        this.builder = builder;
        return this;
    }

    public ClassParserBuilder<T> addField(String fieldName, Parser fieldParser) {
        fields.add(new Pair<>(fieldName, fieldParser));
        return this;
    }
}
