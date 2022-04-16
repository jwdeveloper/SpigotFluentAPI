package jw.spigot_fluent_api.database.mysql_db.connection;
import jw.spigot_fluent_api.database.api.conncetion.DbConnection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class SqlConnection implements DbConnection<Connection, SqlConnectionDto> {

    public Optional<Connection> getConnection(SqlConnectionDto connectionDto)
    {
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
