package jw.fluent_api.database.mysql.factories;

import jw.fluent_api.database.api.conncetion.DbConnectionFactory;
import jw.fluent_api.database.mysql.models.SqlConnection;
import jw.fluent_plugin.implementation.FluentApi;

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
            FluentApi.logger().error("Connecting to SQL error", e);
            return Optional.empty();
        }
    }
}
