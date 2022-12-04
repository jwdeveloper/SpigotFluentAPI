package jw.fluent.api.database.api.query_builder.select_builder;

import jw.fluent.api.database.api.query_builder.bridge_builder.BridgeBuilder;
import jw.fluent.api.database.api.query_abstract.select.AbstractSelectQuery;

public interface SelectBuilder extends AbstractSelectQuery<SelectBuilder>
{
     SelectBuilder columns(String... columns);

     <T> BridgeBuilder from(Class<T> table);

     BridgeBuilder from(String table);
}
