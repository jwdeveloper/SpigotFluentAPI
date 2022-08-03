package jw.spigot_fluent_api.database.api.query_fluent.select;

import jw.spigot_fluent_api.database.api.query_abstract.select.AbstractSelectQuery;
import jw.spigot_fluent_api.database.api.query_fluent.QueryFluent;
import jw.spigot_fluent_api.database.api.query_fluent.order.OrderFluent;
import jw.spigot_fluent_api.database.api.query_fluent.order.OrderFluentBridge;
import jw.spigot_fluent_api.database.api.query_fluent.where.WhereFluent;

public interface SelectFluent<T> extends AbstractSelectQuery<SelectFluent<T>>, OrderFluentBridge<T>,  QueryFluent<T>
{
     SelectFluent<T> join(Class<?> foreignTable);

     WhereFluent<T> where();

     OrderFluent<T> orderBy();
}
