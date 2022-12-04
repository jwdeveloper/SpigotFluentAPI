package jw.fluent.api.database.mysql.query_builder.update_builder;

import jw.fluent.api.database.api.query_builder.update_builder.UpdateBuilder;
import jw.fluent.api.database.api.query_builder.update_builder.UpdateConditionsQuery;
import jw.fluent.api.database.api.query_builder.where_builders.WhereBuilder;
import jw.fluent.api.database.mysql.query_builder.where_builders.WhereBuilderImpl;
import jw.fluent.api.database.mysql.query_builder.QueryBuilderImpl;
import jw.fluent.api.database.mysql.query_builder.SqlSyntaxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateBuilderImpl extends QueryBuilderImpl implements UpdateBuilder, UpdateConditionsQuery {
    private final List<String> insertValues;
    private final StringBuilder stringBuilderHelper;

    public UpdateBuilderImpl() {
        this(new StringBuilder());
    }

    public UpdateBuilderImpl(StringBuilder query) {
        super(query);
        insertValues = new ArrayList<>();
        stringBuilderHelper = new StringBuilder();
        query.append(SqlSyntaxUtils.UPDATE);
    }

    public UpdateConditionsQuery table(Class<?> tableClass) {
        return table(tableClass.getSimpleName());
    }

    public UpdateConditionsQuery table(String table) {
        query.append(table);
        query.append(SqlSyntaxUtils.SET);
        return this;
    }

    public UpdateConditionsQuery set(String column, Object value) {
        final var wrapper = SqlSyntaxUtils.getWrapper(value);
        stringBuilderHelper.setLength(0);
        insertValues.add(stringBuilderHelper.append(column)
                .append(SqlSyntaxUtils.EQUALS)
                .append(wrapper)
                .append(value)
                .append(wrapper)
                .toString());
        return this;
    }

    public UpdateConditionsQuery set(HashMap<String, Object> values) {
        for (final var entrySet : values.entrySet()) {
            set(entrySet.getKey(), entrySet.getValue());
        }
        return this;
    }

    @Override
    public String getQuery() {
        prepareSetValues();
        return super.getQuery();
    }

    public WhereBuilder where() {
        prepareSetValues();
        return new WhereBuilderImpl(query).where();
    }

    private void prepareSetValues() {
        for (int i = 0; i < insertValues.size(); i++) {
            query.append(insertValues.get(i));
            if (i != insertValues.size() - 1) {
                query.append(SqlSyntaxUtils.COMMA);
            }
        }
    }
}
