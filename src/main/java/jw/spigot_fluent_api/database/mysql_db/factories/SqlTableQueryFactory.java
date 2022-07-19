package jw.spigot_fluent_api.database.mysql_db.factories;

import jw.spigot_fluent_api.database.mysql_db.query_builder.SqlQueryBuilder;
import jw.spigot_fluent_api.database.api.database_table.models.TableModel;

public class SqlTableQueryFactory {

    public static String createTable(Class<?> clazz) {
        return createTable(SqlTableModelFactory.getTableModel(clazz));
    }

    public static String createTable(TableModel tableModel) {
        var builder = SqlQueryBuilder.table();
        builder.createTable(tableModel.getName());

        for (var column : tableModel.getColumnList()) {

            if (column.isForeignKey()) {
                var fk = column.getForeignKeyReference();
                var name = column.getForeignKeyName();
                var tableName = column.getForeignKeyTableName();
                builder.foreignKey("FK_" + tableName + "_" + fk, name, tableName, fk, column.getOnDeleteString());
                continue;
            }

            var columnBuilder = builder
                    .createColumn(column.getName())
                    .setType(column.getType(), column.getSize());

            if (column.isKey()) {
                if (column.isAutoIncrement())
                    columnBuilder.setAutoIncrement();

                if (column.isPrimaryKey())
                    columnBuilder.setPrimaryKey();

            }

            if (column.isRequired())
                columnBuilder.setRequired();


            columnBuilder.addColumn();
        }

        return builder.build();
    }

    public static String dropTable(Class<?> clazz) {
        return dropTable(SqlTableModelFactory.getTableModel(clazz));
    }

    public static String dropTable(TableModel model) {
            return SqlQueryBuilder.table().dropTable(model.getName()).build();
    }
}