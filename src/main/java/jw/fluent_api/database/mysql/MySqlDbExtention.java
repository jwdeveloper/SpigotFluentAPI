package jw.fluent_api.database.mysql;

import jw.fluent_api.database.mysql.factories.SqlConnectionFactory;
import jw.fluent_api.database.mysql.factories.SqlDbContextFactory;
import jw.fluent_api.database.mysql.models.SqlConnection;
import jw.fluent_api.database.mysql.models.SqlDbContext;
import jw.fluent_api.database.mysql.models.SqlTable;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.api.PluginAction;
import jw.fluent_plugin.api.options.PipelineOptions;

import java.sql.Connection;

public class MySqlDbExtention<T> implements PluginAction {
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
    public void pluginEnable(PipelineOptions options) throws Exception {
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


       /*var table = (SqlTable<Player>)context.tables.get(0);
        var player = table.select()
                .columns("Name","Age","Gender")
                .where().isEqual("Age",2)
                .and().isEqual("Name","Adam")
                .toList();*/
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {
        if (connection == null) {
            return;
        }
        connection.close();
    }
}
