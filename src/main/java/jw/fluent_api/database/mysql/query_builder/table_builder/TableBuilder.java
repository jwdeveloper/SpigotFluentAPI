package jw.fluent_api.database.mysql.query_builder.table_builder;

import jw.fluent_api.database.mysql.query_builder.SqlSyntaxUtils;

import java.util.ArrayList;
import java.util.List;

public class TableBuilder {

    private String tableName;
    private final StringBuilder query;
    final List<String> columns;

    private final StringBuilder helperBuilder;

    public TableBuilder() {
        query = new StringBuilder();
        helperBuilder = new StringBuilder();
        columns = new ArrayList<>();
    }

    public TableBuilder createTable(String name) {
        query.append(SqlSyntaxUtils.CREATE_TABLE);
        tableName = name;
        return this;
    }

    public TableBuilder dropTable(String name) {
        query.append(SqlSyntaxUtils.DROP_TABLE);
        tableName = name;
        return this;
    }



    public ColumnBuilder createColumn(String name) {
        return new ColumnBuilder(this, name);
    }

    public TableBuilder index(String indexName, String columnName)
    {
        helperBuilder.append(" INDEX ")
                .append(indexName)
                .append(SqlSyntaxUtils.OPEN)
                .append(columnName)
                .append(SqlSyntaxUtils.CLOSE)
                .append(SqlSyntaxUtils.SPACE);
        return this;
    }

    public TableBuilder foreignKey(String indexName,
                                   String columnName,
                                   String refTableName,
                                   String refColumnName,
                                   String onDelete)
    {
                helperBuilder
                        .append(", INDEX ")
                .append(indexName)
                .append(SqlSyntaxUtils.OPEN)
                .append(columnName)
                .append(SqlSyntaxUtils.CLOSE)
                .append(SqlSyntaxUtils.SPACE)
                 .append(", CONSTRAINT ")
                .append(indexName)
                .append(" FOREIGN KEY ")
                .append(SqlSyntaxUtils.OPEN)
                .append(columnName)
                .append(SqlSyntaxUtils.CLOSE)
                .append(" REFERENCES ")
                .append(refTableName)
                .append(SqlSyntaxUtils.OPEN)
                .append(refColumnName)
                .append(SqlSyntaxUtils.CLOSE)
                .append(" ON DELETE ")
                .append(onDelete)
                .append(SqlSyntaxUtils.SPACE);
        return this;
    }

    public String build() {

        query.append(tableName);
        if (columns.size() == 0)
            return query.append(SqlSyntaxUtils.SEMI_COL).toString();


        query.append(SqlSyntaxUtils.OPEN);
        for (int i = 0; i < columns.size(); i++) {
            query.append(columns.get(i));
            if (i != columns.size() - 1) {
                query.append(SqlSyntaxUtils.COMMA);
            }
        }
         query.append(helperBuilder);
        query.append(SqlSyntaxUtils.CLOSE);
        return query.append(SqlSyntaxUtils.SEMI_COL).toString();
    }
}
