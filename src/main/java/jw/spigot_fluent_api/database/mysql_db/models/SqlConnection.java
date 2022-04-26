package jw.spigot_fluent_api.database.mysql_db.models;
import jw.spigot_fluent_api.database.api.conncetion.DbConnectionDto;
import lombok.Data;

@Data
public class SqlConnection implements DbConnectionDto
{
    private String server;

    private String database;

    private boolean autoReconnect=true;

    private boolean useSll =true;

    private String user;

    private String password;

    public String getConnectionString()
    {
       return new StringBuilder()
                .append("jdbc:mysql://")
                .append(server)
                .append("/")
                .append(database)
                .append("?")
                .append("autoReconnect=")
                .append(autoReconnect)
                .append("&")
                .append("useSSL=")
                .append(useSll)
                .toString();
    }
}
