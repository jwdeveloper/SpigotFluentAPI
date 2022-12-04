package jw.fluent.api.database.mysql.query_builder.select_builder;

import jw.fluent.api.database.api.query_builder.bridge_builder.BridgeBuilder;
import jw.fluent.api.database.api.query_builder.select_builder.SelectBuilder;
import jw.fluent.api.database.mysql.query_builder.QueryBuilderImpl;
import jw.fluent.api.database.mysql.query_builder.SqlSyntaxUtils;
import jw.fluent.api.database.mysql.query_builder.bridge_builder.BridgeBuilderImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectBuilderImpl extends QueryBuilderImpl implements SelectBuilder {

    private final List<String> columns;
    private final StringBuilder stringBuilderHelper;

    public SelectBuilderImpl() {
        this(new StringBuilder());
    }

    public SelectBuilderImpl(StringBuilder stringBuilder) {
        super(stringBuilder);
        columns = new ArrayList<>();
        stringBuilderHelper = new StringBuilder();
    }

    public SelectBuilder columns(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    public SelectBuilder count(String column) {
        stringBuilderHelper.setLength(0);
        this.columns.add("COUNT(" + column + ")");
        return this;
    }

    public SelectBuilder avg(String column) {
        stringBuilderHelper.setLength(0);
        this.columns.add("AVG(" + column + ")");
        return this;
    }

    public SelectBuilder sum(String column) {
        stringBuilderHelper.setLength(0);
        this.columns.add(stringBuilderHelper
                .append("SUM")
                .append(SqlSyntaxUtils.OPEN)
                .append(column)
                .append(SqlSyntaxUtils.CLOSE)
                .toString());
        return this;
    }

    public <T> BridgeBuilder from(Class<T> table) {
        return from(table.getSimpleName());
    }

    public BridgeBuilder from(String table) {
        query.append(SqlSyntaxUtils.SELECT);
        for (int i = 0; i < columns.size(); i++) {
            query.append(columns.get(i));
            if (i != columns.size() - 1) {
                query.append(SqlSyntaxUtils.COMMA);
            }
        }
        query.append(SqlSyntaxUtils.FROM).append(table);
        return new BridgeBuilderImpl(query);
    }
}
