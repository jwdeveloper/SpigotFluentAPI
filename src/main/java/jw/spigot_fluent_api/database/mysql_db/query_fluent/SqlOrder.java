package jw.spigot_fluent_api.database.mysql_db.query_fluent;

import jw.spigot_fluent_api.database.api.database_table.models.TableModel;
import jw.spigot_fluent_api.database.api.query_builder.order_builder.OrderBuilder;
import jw.spigot_fluent_api.database.api.query_fluent.order.OrderFluent;
import jw.spigot_fluent_api.database.api.query_fluent.order.OrderFluentBridge;
import jw.spigot_fluent_api.database.mysql_db.query_builder.SqlSyntaxUtils;
import jw.spigot_fluent_api.database.mysql_db.query_builder.order_builder.OrderBuilderImpl;

import java.sql.Connection;

public class SqlOrder<T>   extends SqlQuery<T> implements OrderFluent<T> {

    private final OrderBuilder orderBuilder;

    public SqlOrder(StringBuilder query, Connection connection, TableModel tableModel) {
        super(query, connection, tableModel);
        orderBuilder = new OrderBuilderImpl(query);
    }

    @Override
    public String getQueryClosed() {
        return  orderBuilder.getQuery().concat(SqlSyntaxUtils.SEMI_COL);
    }

    @Override
    public String getQuery() {
        return orderBuilder.getQuery();
    }

    @Override
    public OrderFluent<T> desc(String table) {
        orderBuilder.desc(table);
        return this;
    }

    @Override
    public OrderFluent<T> asc(String table) {
        orderBuilder.asc(table);
        return this;
    }
}
