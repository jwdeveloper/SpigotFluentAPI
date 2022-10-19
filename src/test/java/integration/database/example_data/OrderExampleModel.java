package integration.database.example_data;

import jw.spigot_fluent_api.database.api.database_table.annotations.*;
import jw.spigot_fluent_api.database.mysql_db.utils.SqlTypes;
import lombok.Data;

@Data
@Table(name = "Orders")
public class OrderExampleModel
{
    @Key
    @Column( type = SqlTypes.INT)
    private int id;

    @ForeignKey(columnName = "userId", referencedColumnName = "id")
    private UserExampleModel user;

    @Required
    @Column(size = 10)
    private String productName;

    @Column( type = SqlTypes.INT)
    private int userId;

}
