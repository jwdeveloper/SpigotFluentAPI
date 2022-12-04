package jw.fluent.api.database.api.query_abstract.where;

import jw.fluent.api.database.api.query_abstract.AbstractQuery;

public interface AbstractWhereQuery<T> extends AbstractQuery
{
     T isEqual(String column, Object value);

     T isNotEqual(String column, Object value);

     T isGreater(String column, Number value);

     T isNotGreater(String column, Number value);

     T isIn(String column, String subQuery);

     T isIn(String column, Object... values);

     T isNotIn(String column, String subQuery);

     T isNotIn(String column, Object... values);

     T or();

     T and();

     T custom(String custom);
}
