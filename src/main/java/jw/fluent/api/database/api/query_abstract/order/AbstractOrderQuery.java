package jw.fluent.api.database.api.query_abstract.order;

import jw.fluent.api.database.api.query_abstract.AbstractQuery;

public interface AbstractOrderQuery<T> extends AbstractQuery
{
     T desc(String table);

     T asc(String table);
}
