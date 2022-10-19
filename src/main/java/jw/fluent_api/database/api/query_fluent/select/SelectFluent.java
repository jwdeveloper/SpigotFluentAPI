package jw.fluent_api.database.api.query_fluent.select;

import jw.fluent_api.database.api.query_abstract.select.AbstractSelectQuery;
import jw.fluent_api.database.api.query_fluent.QueryFluent;
import jw.fluent_api.database.api.query_fluent.order.OrderFluent;
import jw.fluent_api.database.api.query_fluent.order.OrderFluentBridge;
import jw.fluent_api.database.api.query_fluent.where.WhereFluent;

public interface SelectFluent<T> extends AbstractSelectQuery<SelectFluent<T>>, OrderFluentBridge<T>,  QueryFluent<T>
{
     SelectFluent<T> join(Class<?> foreignTable);

     WhereFluent<T> where();

     OrderFluent<T> orderBy();
}
