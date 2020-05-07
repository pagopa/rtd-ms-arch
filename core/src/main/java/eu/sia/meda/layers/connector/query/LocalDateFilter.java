package eu.sia.meda.layers.connector.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/** In HBase we cannot perform logic directly on LocalDate fields, we have to create redundant integer fields (epochDays) and execute logic on it */
@Data @NoArgsConstructor
public class LocalDateFilter implements CriteriaFilter<LocalDate> {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate equalsTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate greaterThan;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate greaterOrEqualTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lessThan;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lessOrEqualTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Collection<LocalDate> in;
    private Boolean isNull;
    /** @see CriteriaFilter#isAndAggregate()  */
    private boolean andAggregate = true;

    @Override
    public boolean accept(Class<?> fieldClazz) {
        return LocalDate.class.isAssignableFrom(fieldClazz);
    }

    public LocalDateFilter(LocalDate equalsTo){
        this.equalsTo=equalsTo;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static LocalDateFilter valueOf(String equalsTo){
        return new LocalDateFilter(LocalDate.parse(equalsTo, DateTimeFormatter.ISO_DATE));
    }

    public String toString(){
        List<String> out = new ArrayList<>(4);
        if(equalsTo!=null){
            out.add(formatDate(equalsTo));
        }
        if(greaterThan!=null){
            out.add(String.format("greaterThan=%s", formatDate(greaterThan)));
        }
        if(greaterOrEqualTo!=null){
            out.add(String.format("greaterOrEqualTo=%s", formatDate(greaterOrEqualTo)));
        }
        if(lessThan!=null){
            out.add(String.format("lessThan=%s", formatDate(lessThan)));
        }
        if(lessOrEqualTo!=null){
            out.add(String.format("lessOrEqualTo=%s", formatDate(lessOrEqualTo)));
        }
        if(in!=null){
            out.add(String.format("in=%s", in.stream().map(this::formatDate).collect(Collectors.joining(","))));
        }
        if(isNull!=null){
            out.add(String.format("isNull=%s", isNull));
        }
        return String.join(andAggregate?" && ":" || ", out);
    }
    private String formatDate(LocalDate localDate){
        return localDate.format(DateTimeFormatter.ISO_DATE);
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Path<LocalDate> field) {
        List<Predicate> predicates = new ArrayList<>();

        if(equalsTo!=null){
            predicates.add(builder.equal(field, equalsTo));
        }
        if(greaterThan !=null){
            predicates.add(builder.greaterThan(field, greaterThan));
        }
        if(greaterOrEqualTo !=null){
            predicates.add(builder.greaterThanOrEqualTo(field, greaterOrEqualTo));
        }
        if(lessThan !=null){
            predicates.add(builder.lessThan(field, lessThan));
        }
        if(lessOrEqualTo !=null){
            predicates.add(builder.lessThanOrEqualTo(field, lessOrEqualTo));
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
