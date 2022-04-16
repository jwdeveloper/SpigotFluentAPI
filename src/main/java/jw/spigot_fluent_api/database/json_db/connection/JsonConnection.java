package jw.spigot_fluent_api.database.json_db.connection;

import jw.spigot_fluent_api.database.api.conncetion.DbConnection;
import jw.spigot_fluent_api.database.mysql_db.connection.SqlConnectionDto;

import java.io.File;
import java.util.Optional;

public class JsonConnection implements DbConnection<File, JsonConnectionDto>
{
    @Override
    public Optional<File> getConnection(JsonConnectionDto  dto) {
        return Optional.empty();
    }
}
