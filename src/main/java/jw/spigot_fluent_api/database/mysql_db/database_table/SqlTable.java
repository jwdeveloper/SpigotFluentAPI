package jw.spigot_fluent_api.database.mysql_db.database_table;

import jw.spigot_fluent_api.database.api.query_fluent.select.SelectFluentBridge;
import jw.spigot_fluent_api.database.mysql_db.database_table.factories.TableQueryFactory;
import jw.spigot_fluent_api.database.mysql_db.query_fluent.SqlSelect;
import jw.spigot_fluent_api.database.mysql_db.database_table.change_tacker.SqlChangeTracker;
import jw.spigot_fluent_api.database.mysql_db.database_table.factories.EntryQueryFactory;
import jw.spigot_fluent_api.database.api.database_table.models.TableModel;
import jw.spigot_fluent_api.database.mysql_db.database_table.factories.TableModelFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class SqlTable<T> {

    @Setter
    private Connection connection;
    @Getter
    private final SqlChangeTracker<T> changeTracker;
    private final TableModel tableModel;

    public SqlTable(Class<T> clazz) {
        tableModel = TableModelFactory.getTableModel(clazz);
        changeTracker = new SqlChangeTracker<>(tableModel);
    }

    public SelectFluentBridge<T> select() {
        return new SqlSelect<>(connection,tableModel);
    }

    public <K> List<K> select(String query, Class<T> customClass) {
        return null;
    }

    public List<T> select(String query) {
        return null;
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
        var createTable = TableQueryFactory.createTable(tableModel);
        statement.execute(createTable);
    }

    @SneakyThrows
    public void dropTable()
    {
        var statement = connection.createStatement();
        statement.addBatch("SET FOREIGN_KEY_CHECKS = 0;");
        statement.addBatch(TableQueryFactory.dropTable(tableModel));
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
                case INSERT -> dto.insertQueries.put(entry, EntryQueryFactory.insertQuery(entry, tableModel));
                case UPDATE -> dto.updateQuery.put(entry, EntryQueryFactory.updateQuery(entry, tableModel));
                case DELETE -> dto.deleteQuery.put(entry, EntryQueryFactory.deleteQuery(entry, tableModel));
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
