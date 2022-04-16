package jw.spigot_fluent_api.database.api.conncetion;

import jw.spigot_fluent_api.database.mysql_db.connection.SqlConnectionDto;

import java.sql.Connection;
import java.util.Optional;

public interface DbConnection<T,Dto extends DbConnectionDto>
{
    public Optional<T> getConnection(Dto connectionDto);
}
