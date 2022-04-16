package example_classes.database;

import jw.spigot_fluent_api.database.api.database_table.annotations.*;
import jw.spigot_fluent_api.database.mysql_db.utils.SqlTypes;
import lombok.Data;

@Data
@Table()
public class OrderExampleTable
{
    @Key
    @Column( type = SqlTypes.INT)
    private int id;


    @ForeignKey(columnName = "userId", referencedColumnName = "id")
    private UserExampleTable user;

    @Required
    @Column(size = 10)
    private String productName;

    @Column( type = SqlTypes.INT)
    private int userId;


}
