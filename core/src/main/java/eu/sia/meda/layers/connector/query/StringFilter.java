package eu.sia.meda.layers.connector.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data @NoArgsConstructor
public class StringFilter implements CriteriaFilter<String> {
    private String equalsTo;
    /** Actually not supported by HBase!
     * see com.impetus.kundera.persistence.KunderaCriteriaBuilder
     * */
    private String startsWith;
    /** Actually not supported by HBase!
     * see com.impetus.kundera.persistence.KunderaCriteriaBuilder
     * */
    private Collection<String> in;
    /** Actually not supported by HBase!
     * see com.impetus.kundera.persistence.KunderaCriteriaBuilder
     * */
    private Boolean isNull;
    /** @see CriteriaFilter#isAndAggregate()  */
    private boolean andAggregate = true;

    @Override
    public boolean accept(Class<?> fieldClazz) {
        return String.class.isAssignableFrom(fieldClazz);
    }

    public StringFilter(String equalsTo){
        this.equalsTo=equalsTo;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StringFilter valueOf(String equalsTo){
        return new StringFilter(equalsTo);
    }

    public void setStartsWith(String value) {
        if(value!=null) {
            this.startsWith = String.format("%s%%", value);
        } else {
            this.startsWith=null;
        }
    }

    public String toString(){
        List<String> out = new ArrayList<>(4);
        if(equalsTo!=null){
            out.add(equalsTo);
        }
        if(startsWith!=null){
            out.add(String.format("startsWith=%s", startsWith));
        }
        if(in!=null){
            out.add(String.format("in=%s", in.toString()));
        }
        if(isNull!=null){
            out.add(String.format("isNull=%s", isNull));
        }
        return String.join(" && ", out);
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Path<String> field) {
        List<Predicate> predicates = new ArrayList<>();

        if(equalsTo!=null){
            predicates.add(builder.equal(field, equalsTo));
        }
        if(startsWith!=null){
            predicates.add(builder.like(field, startsWith));
        }
        if(in!=null){
            predicates.add(field.in(in));
        }
        if(isNull!=null){
            if(isNull){
                predicates.add(field.isNull());
            } else {
                predicates.add(field.isNotNull());
            }
        }

        return combinePredicates(builder, predicates.toArray(new Predicate[0]));
    }
}
