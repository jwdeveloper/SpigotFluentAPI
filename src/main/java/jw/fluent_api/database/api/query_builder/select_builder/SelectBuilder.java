package jw.fluent_api.database.api.query_builder.select_builder;

import jw.fluent_api.database.api.query_abstract.select.AbstractSelectQuery;
import jw.fluent_api.database.api.query_builder.bridge_builder.BridgeBuilder;

public interface SelectBuilder extends AbstractSelectQuery<SelectBuilder>
{
     SelectBuilder columns(String... columns);

     <T> BridgeBuilder from(Class<T> table);

     BridgeBuilder from(String table);
}
