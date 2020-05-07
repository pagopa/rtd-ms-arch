package eu.sia.meda.layers.connector.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/** To accept a filter on the input class */
public interface CriteriaFilter<T> {
    /** To test if the selected field is supported by the filter */
    boolean accept(Class<?> fieldClazz);
    /** In case of multiple predicate on the current field, if this field is true all the predicates will be aggregated using AND, otherwise OR */
    boolean isAndAggregate();
    void setAndAggregate(boolean value);
    T getEqualsTo();
    Predicate toPredicate(CriteriaBuilder builder, Path<T> field);

    default Predicate combinePredicates(CriteriaBuilder criteriaBuilder, Predicate... predicates){
        Predicate out = null;
        if(predicates!=null && predicates.length>0){
            for (Predicate predicate : predicates) {
                if(predicate!=null){
                    if(out==null){
                        out=predicate;
                    } else {
                        if(isAndAggregate()){
                            out=criteriaBuilder.and(out, predicate);
                        } else {
                            out=criteriaBuilder.or(out, predicate);
                        }
                    }
                }
            }
        }
        return out;
    }
}
