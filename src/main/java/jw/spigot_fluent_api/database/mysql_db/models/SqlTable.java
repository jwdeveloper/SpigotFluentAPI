package jw.spigot_fluent_api.database.mysql_db.models;

import jw.spigot_fluent_api.database.api.database_table.DbTable;
import jw.spigot_fluent_api.database.api.query_fluent.select.SelectFluentBridge;
import jw.spigot_fluent_api.database.mysql_db.factories.SqlEntryQueryFactory;
import jw.spigot_fluent_api.database.mysql_db.factories.SqlTableQueryFactory;
import jw.spigot_fluent_api.database.mysql_db.query_fluent.SqlSelect;
import jw.spigot_fluent_api.database.mysql_db.change_tacker.SqlChangeTracker;
import jw.spigot_fluent_api.database.api.database_table.models.TableModel;
import jw.spigot_fluent_api.database.mysql_db.factories.SqlTableModelFactory;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class SqlTable<T> implements DbTable<T> {

    @Setter
    private Connection connection;
    @Getter
    private final SqlChangeTracker<T> changeTracker;
    final TableModel tableModel;

    public SqlTable(Class<T> clazz) {
        tableModel = SqlTableModelFactory.getTableModel(clazz);
        changeTracker = new SqlChangeTracker<>(tableModel);
    }

    public SelectFluentBridge<T> select() {
        return new SqlSelect<>(connection,tableModel);
    }

    public SqlEntry<T> update(T entity) {
        return changeTracker.update(entity);
    }

    public SqlEntry<T> insert(T entity) {
        return changeTracker.insert(entity);
    }

    public SqlEntry<T> delete(T entity) {
        return changeTracker.delete(entity);
    }

    @SneakyThrows
    public void createTable()
    {
        var statement = connection.createStatement();
        var createTableQuery = SqlTableQueryFactory.createTable(tableModel);
        FluentLogger.success(createTableQuery);
        statement.execute(createTableQuery);
    }

    @SneakyThrows
    public void dropTable()
    {
        var statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS = 0;");
        statement.addBatch(SqlTableQueryFactory.dropTable(tableModel));
        statement.addBatch("SET FOREIGN_KEY_CHECKS = 1;");
        statement.executeBatch();
    }

    public void saveChanges() throws SQLException {

        final var queriesDto = getEntryQueryDto();

        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        executeInsertQuery(queriesDto.insertQueries);
        executeBatchQuery(queriesDto.updateQuery);
        executeBatchQuery(queriesDto.deleteQuery);
        connection.commit();

        changeTracker.clear();
    }

    private EntryQueryDto getEntryQueryDto() {
        final var entries = changeTracker.getTrackedEntries();
        final var dto = new EntryQueryDto();
        for (final var entry : entries) {
            switch (entry.getAction()) {
                case INSERT -> dto.insertQueries.put(entry, SqlEntryQueryFactory.insertQuery(entry, tableModel));
                case UPDATE -> dto.updateQuery.put(entry, SqlEntryQueryFactory.updateQuery(entry, tableModel));
                case DELETE -> dto.deleteQuery.put(entry, SqlEntryQueryFactory.deleteQuery(entry, tableModel));
            }
        }
        return dto;
    }

    private void executeInsertQuery(HashMap<SqlEntry<T>, String> queries) throws SQLException {
        for (final var entrySet : queries.entrySet()) {
            final var query = entrySet.getValue();
            final var entity = entrySet.getKey();
            final var statement = connection.createStatement();
            final var result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            //FluentLogger.success("Result  " + result);
            System.out.println(query);
            final var resultSet = statement.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new SQLException("Can not get new Key");
            }
            final var key = resultSet.getInt(1);
            entity.setKey(key);
        }
    }


    private void executeBatchQuery(HashMap<SqlEntry<T>, String> queries) throws SQLException {

        var statement = connection.createStatement();
        for (var entrySet : queries.entrySet()) {
            var query = entrySet.getValue();
            System.out.println(query);
            statement.addBatch(query);
        }
        var result = statement.executeBatch();
    }

    private class EntryQueryDto {
        HashMap<SqlEntry<T>, String> insertQueries = new HashMap();
        HashMap<SqlEntry<T>, String> updateQuery = new HashMap();
        HashMap<SqlEntry<T>, String> deleteQuery = new HashMap();
    }
}
