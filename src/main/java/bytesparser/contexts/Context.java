package bytesparser.contexts;

/**
 * Created by tomer on 4/20/17.
 */
public interface Context<S> {

    /* --- Methods --- */

    <F> F getField(String fieldName);

    void addField(String fieldName, Object fieldValue);

    S getData(int size);

    int getRemand();
}
