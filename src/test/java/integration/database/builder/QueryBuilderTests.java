package integration.database.builder;

import jw.fluent_api.database.mysql.query_builder.SqlQueryBuilder;
import org.junit.Assert;
import org.junit.Test;

public class QueryBuilderTests {




    @Test
    public void simpleSelect() {
        var result = SqlQueryBuilder
                .select()
                .columns("*")
                .from(TestDto.class)
                .where()
                .isEqual("id", 12)
                .orderBy()
                .desc("id")
                .getQuery();

        System.out.println(result);
    }

    @Test
    public void select() {
        var result = SqlQueryBuilder
                .select()
                .columns("id", "isGood", "name").from(TestDto.class)
                .join()
                .left("players","testDto.id", "player.id")
                .outer("players","testDto.id", "player.id")
                .where().isGreater("id", 21).and().isEqual("name", "Mark")
                .groupBy().table("id").having().isEqual("a","a")
                .orderBy().desc("id")
                .getQuery();
        System.out.println(result);
    }

    @Test
    public void insert() {

        var result = SqlQueryBuilder
                .insert()
                .table(TestDto.class)
                .columns("id", "isGood", "name")
                .values(1, true, "jacek")
                .values(1, true, "packe")
                .values(1, true, "adam")
                .values(1, true, "jacek")
                .getQuery();

        System.out.println(result);
    }

    @Test
    public void update() {
        var builder =  SqlQueryBuilder
                .update()
                .table(TestDto.class)
                .set("isGood", true)
                .set("name", "jacek")
                .where()
                .isEqual("id", 12)
                .getQuery();
        System.out.println(builder);
    }

    @Test
    public void delete() {
        var result = SqlQueryBuilder
                .delete()
                .from(TestDto.class)
                .where()
                .isEqual("id", 12)
                .getQuery();
        System.out.println(result);
    }

    @Test
    public void table() {
        var builder = SqlQueryBuilder.table();
        builder.createTable("Users");

        builder.createColumn("id")
                .setPrimaryKey()
                .setAutoIncrement()
                .setType("INT(6)")
                .addColumn();

        builder.createColumn("nick")
                .setType("VARCHAR(30)")
                .setRequired()
                .addColumn();

        builder.createColumn("uuid")
                .setType("VARCHAR(30)")
                .setRequired()
                .addColumn();

        builder.createColumn("surname")
                .setType("VARCHAR(30)")
                .addColumn();

        var result = builder.build();

        System.out.println(result);
        Assert.assertNotEquals(result,"");
    }


    public class TestDto {
        public int id;
        public boolean isGood;
        public String name;
    }
}
