package jw.fluent_api.database.api.query_builder.order_builder;

import jw.fluent_api.database.api.query_abstract.AbstractQuery;
import jw.fluent_api.database.api.query_abstract.order.AbstractOrderQuery;

public interface OrderBuilder extends AbstractQuery, AbstractOrderQuery<OrderBuilder>
{
    public OrderBuilder desc(String table);

    public OrderBuilder asc(String table);
}
