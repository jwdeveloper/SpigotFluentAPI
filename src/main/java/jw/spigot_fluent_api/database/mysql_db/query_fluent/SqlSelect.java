package jw.spigot_fluent_api.database.mysql_db.query_fluent;
import jw.spigot_fluent_api.database.api.database_table.models.TableModel;
import jw.spigot_fluent_api.database.api.query_fluent.order.OrderFluent;
import jw.spigot_fluent_api.database.api.query_fluent.select.SelectFluentBridge;
import jw.spigot_fluent_api.database.api.query_fluent.select.SelectFluent;
import jw.spigot_fluent_api.database.api.query_fluent.where.WhereFluent;
import jw.spigot_fluent_api.database.mysql_db.query_builder.SqlSyntaxUtils;
import jw.spigot_fluent_api.database.mysql_db.query_builder.select_builder.SelectBuilderImpl;
import lombok.SneakyThrows;

import java.sql.Connection;

public class SqlSelect<T> extends SqlQuery<T> implements SelectFluentBridge<T>, SelectFluent<T>
{
    private String[] columns;
    private final SelectBuilderImpl selectBuilder;


    public SqlSelect(Connection connection, TableModel tableModel) {
        super(connection, tableModel);
        selectBuilder = new SelectBuilderImpl();
    }

    public SelectFluent<T> columns(String ... columns)
    {
        this.columns = columns;
        return this;
    }

    @Override
    public SelectFluent<T> count(String column) {
        selectBuilder.count(column);
        return this;
    }

    @Override
    public SelectFluent<T> avg(String column) {
        selectBuilder.avg(column);
        return this;
    }

    @Override
    public SelectFluent<T> sum(String column) {
        selectBuilder.sum(column);
        return this;
    }

    public WhereFluent<T> where()
    {
        getQuery();
        return new SqlWhere<T>(query,connection,tableModel);
    }

    @Override
    public OrderFluent<T> orderBy()
    {
        getQuery();
        return new SqlOrder<T>(query,connection,tableModel);
    }

    @SneakyThrows
    public SelectFluent<T> join(Class<?> foreignTable)
    {
        var foreignColumns = tableModel.getForeignKeys();
        var containsKey = foreignColumns.stream()
                .filter(c -> c.getField().getType().equals(foreignTable))
                .findFirst();
        if(containsKey.isEmpty())
        {
            throw new Exception("Not ForeignKey not found");
        }
        if(joinedColumns.contains(containsKey.get()))
        {
            throw new Exception("ForeignKey already joined");
        }
        this.joinedColumns.add(containsKey.get());
        return this;
    }


    @Override
    public String getQueryClosed() {
        return getQuery().concat(SqlSyntaxUtils.SEMI_COL);
    }

    @Override
    public String getQuery() {

        if(columns == null)
            selectBuilder.columns("*");
        else
            selectBuilder.columns(columns);

        var bridgeBuilder = selectBuilder.from(tableModel.getName());
        for (var column: joinedColumns)
        {
            bridgeBuilder.join()
                    .inner(tableModel.getName(),
                    column.getForeignKeyName(),
                    column.getType(),
                    column.getForeignKeyReference());
        }
        return query.append(selectBuilder.getQuery()).toString();
    }



}
