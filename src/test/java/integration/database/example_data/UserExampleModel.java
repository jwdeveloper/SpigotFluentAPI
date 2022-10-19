package integration.database.example_data;

import jw.fluent_api.database.api.database_table.annotations.Column;
import jw.fluent_api.database.api.database_table.annotations.Key;
import jw.fluent_api.database.api.database_table.annotations.Required;
import jw.fluent_api.database.api.database_table.annotations.Table;
import jw.fluent_api.database.mysql.utils.SqlTypes;
import lombok.Data;

import java.util.UUID;

@Data
@Table(name = "Users")
public class UserExampleModel
{
    @Key
    @Column( type = SqlTypes.INT)
    private int id;

    @Required
    @Column(size = 10)
    private String nick;

    @Column(size = 36)
    private UUID uuid;

    @Column
    private String surname;

    @Column(type = SqlTypes.BOOL, name = "online")
    private boolean isOnline;

    private String imNotColumn;
}