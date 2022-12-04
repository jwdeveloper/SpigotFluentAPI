package jw.fluent.api.database.api.query_abstract.select;

import jw.fluent.api.database.api.query_abstract.AbstractQuery;

public interface AbstractSelectQuery<T> extends AbstractQuery
{
    T count(String column);

    T avg(String column);

    T sum(String column);
}
