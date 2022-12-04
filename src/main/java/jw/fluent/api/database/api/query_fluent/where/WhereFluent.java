package jw.fluent.api.database.api.query_fluent.where;

import jw.fluent.api.database.api.query_abstract.where.AbstractWhereQuery;
import jw.fluent.api.database.api.query_fluent.QueryFluent;
import jw.fluent.api.database.api.query_fluent.order.OrderFluentBridge;

public interface WhereFluent<T> extends AbstractWhereQuery<WhereFluent<T>>, OrderFluentBridge<T>, QueryFluent<T> {
}
