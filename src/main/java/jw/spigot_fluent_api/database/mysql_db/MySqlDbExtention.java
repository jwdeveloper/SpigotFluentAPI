package jw.spigot_fluent_api.database.mysql_db;

import jw.spigot_fluent_api.database.mysql_db.factories.SqlConnectionFactory;
import jw.spigot_fluent_api.database.mysql_db.factories.SqlDbContextFactory;
import jw.spigot_fluent_api.database.mysql_db.models.SqlConnection;
import jw.spigot_fluent_api.database.mysql_db.models.SqlDbContext;
import jw.spigot_fluent_api.database.mysql_db.models.SqlTable;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;

import java.sql.Connection;

public class MySqlDbExtention<T> implements PluginPipeline {
    private final Class contextType;
    private final SqlConnection connectionDto;
    private Connection connection;
    private SqlDbContext context;

    public MySqlDbExtention(Class<T> contextType, SqlConnection connectionDto)
    {
        this.contextType = contextType;
        this.connectionDto = connectionDto;
    }

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {
        var conn = new SqlConnectionFactory().getConnection(connectionDto);
        if (conn.isEmpty()) {
            throw new Exception("Can not establish connection");
        }
        connection = conn.get();
        context = SqlDbContextFactory.getDbContext(contextType);
        context.setConnection(connection);
        for (var table : context.tables) {
            var sqlTable = (SqlTable) table;
            sqlTable.createTable();
        }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {
        if (connection == null) {
            return;
        }
        connection.close();
    }
}
