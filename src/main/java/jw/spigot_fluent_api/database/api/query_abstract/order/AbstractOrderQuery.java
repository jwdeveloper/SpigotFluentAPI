package jw.spigot_fluent_api.database.api.query_abstract.order;

import jw.spigot_fluent_api.database.api.query_abstract.AbstractQuery;

public interface AbstractOrderQuery<T> extends AbstractQuery
{
     T desc(String table);

     T asc(String table);
}
