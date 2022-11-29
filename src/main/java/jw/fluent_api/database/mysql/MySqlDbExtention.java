package jw.fluent_api.database.mysql;

import jw.fluent_api.database.mysql.factories.SqlConnectionFactory;
import jw.fluent_api.database.mysql.factories.SqlDbContextFactory;
import jw.fluent_api.database.mysql.models.SqlConnection;
import jw.fluent_api.database.mysql.models.SqlDbContext;
import jw.fluent_api.database.mysql.models.SqlTable;
import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.api.FluentApiExtention;
import jw.fluent_plugin.implementation.FluentApi;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlDbExtention<T> implements FluentApiExtention {
    private final Class contextType;
    private final SqlConnection connectionDto;
    private Connection connection;
    private SqlDbContext context;

    public MySqlDbExtention(Class<T> contextType, SqlConnection connectionDto) {
        this.contextType = contextType;
        this.connectionDto = connectionDto;
    }

    @Override
    public void onConfiguration(FluentApiBuilder builder) {

        builder.container().register(SqlDbContext.class, LifeTime.SINGLETON, (x) ->
        {
            context = SqlDbContextFactory.getDbContext(contextType);
            return context;
        });
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {
        var conn = new SqlConnectionFactory().getConnection(connectionDto);
        if (conn.isEmpty()) {
            throw new Exception("Can not establish connection");
        }
        connection = conn.get();
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
    public void onFluentApiDisabled(FluentApi fluentAPI) throws SQLException {
        if (connection == null) {
            return;
        }
        connection.close();
    }
}
