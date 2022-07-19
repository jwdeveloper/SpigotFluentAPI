package jw.spigot_fluent_api.database.mysql_db.factories;

import jw.spigot_fluent_api.database.api.conncetion.DbConnectionFactory;
import jw.spigot_fluent_api.database.mysql_db.models.SqlConnection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class SqlConnectionFactory implements DbConnectionFactory<Connection, SqlConnection> {

    public Optional<Connection> getConnection(SqlConnection connectionDto) {
        try {
            final var url = connectionDto.getConnectionString();
            var connection = DriverManager.getConnection(url, connectionDto.getUser(), connectionDto.getPassword());
            if (connection == null || connection.isClosed()) {
                return Optional.empty();
            }
            return Optional.of(connection);
        } catch (SQLException e) {
            FluentLogger.error("Connecting to SQL error", e);
            return Optional.empty();
        }
    }
}