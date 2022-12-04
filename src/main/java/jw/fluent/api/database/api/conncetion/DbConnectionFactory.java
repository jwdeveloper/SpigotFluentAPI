package jw.fluent.api.database.api.conncetion;

import java.util.Optional;

public interface DbConnectionFactory<T,Dto extends DbConnectionDto>
{
    public Optional<T> getConnection(Dto connectionDto);
}
