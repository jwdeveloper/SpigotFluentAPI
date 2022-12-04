package jw.fluent.api.database.api.query_fluent.select;

import jw.fluent.api.database.api.query_fluent.QueryFluent;
import jw.fluent.api.database.api.query_fluent.order.OrderFluent;
import jw.fluent.api.database.api.query_fluent.order.OrderFluentBridge;
import jw.fluent.api.database.api.query_fluent.where.WhereFluent;
import jw.fluent.api.database.api.query_abstract.select.AbstractSelectQuery;

public interface SelectFluent<T> extends AbstractSelectQuery<SelectFluent<T>>, OrderFluentBridge<T>, QueryFluent<T>
{
     SelectFluent<T> join(Class<?> foreignTable);

     WhereFluent<T> where();

     OrderFluent<T> orderBy();
}
