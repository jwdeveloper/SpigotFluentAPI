package jw.fluent.api.database.api.query_builder.group_builder;

import jw.fluent.api.database.api.query_builder.order_builder.OrderBuilder;
import jw.fluent.api.database.api.query_abstract.AbstractQuery;
import jw.fluent.api.database.api.query_builder.where_builders.WhereBuilder;

public interface GroupBuilder extends AbstractQuery
{
     GroupBuilder groupBy();

     GroupBuilder table(String table);

     WhereBuilder having();

     OrderBuilder orderBy();
}
