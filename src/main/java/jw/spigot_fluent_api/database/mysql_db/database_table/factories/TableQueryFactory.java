package jw.spigot_fluent_api.database.mysql_db.database_table.factories;

import jw.spigot_fluent_api.database.mysql_db.query_builder.SqlQueryBuilder;
import jw.spigot_fluent_api.database.api.database_table.models.TableModel;

public class TableQueryFactory {

    public static String createTable(Class<?> clazz) {
        return createTable(TableModelFactory.getTableModel(clazz));
    }

    public static String createTable(TableModel tableModel) {
        var builder = SqlQueryBuilder.table();
        builder.createTable(tableModel.getName());

        for (var column : tableModel.getColumnList()) {

            if (column.isForeignKey()) {
                var fk = column.getForeignKeyReference();
                var name = column.getForeignKeyName();
                var type = column.getField().getType().getSimpleName();
                builder.foreignKey("FK_" + type + "_" + fk, name, type, fk, column.getOnDeleteString());
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
        return dropTable(TableModelFactory.getTableModel(clazz));
    }

    public static String dropTable(TableModel model) {
            return SqlQueryBuilder.table().dropTable(model.getName()).build();
    }
}
