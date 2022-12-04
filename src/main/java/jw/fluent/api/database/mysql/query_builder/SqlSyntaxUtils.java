package jw.fluent.api.database.mysql.query_builder;

public final class SqlSyntaxUtils {

    public static final String SELECT = " SELECT ";
    public static final String UPDATE = " UPDATE ";
    public static final String INSERT_INTO = " INSERT INTO ";
    public static final String DELETE_FROM = " DELETE FROM ";
    public static final String GROUP_BY = " GROUP BY ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String WHERE = " WHERE ";
    public static final String IN = " IN ";
    public static final String NOT_IN = " NOT IN ";
    public static final String OR = " OR ";
    public static final String AND = " AND ";
    public static final String SET = " SET ";
    public static final String FROM = " FROM ";
    public static final String DESC = " DESC ";
    public static final String ASC = " ASC ";
    public static final String JOIN = " JOIN ";
    public static final String ON = " ON ";
    public static final String VALUES = " VALUES ";
    public static final String HAVING = " HAVING ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS ";
    public static final String CREATE_TABLE = " CREATE TABLE IF NOT EXISTS ";
    public static final String NOT_NULL = " NOT NULL ";
    public static final String AUTO_INCREMENT = " AUTO_INCREMENT ";
    public static final String PRIMARY_KEY = " PRIMARY KEY ";
    public static final String NULL = " NULL ";

    public static final String EQUALS = " = ";
    public static final String SEMI_COL = ";";
    public static final String OPEN = "(";
    public static final String CLOSE = ")";
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String SINGLE_QUOTE = "'";
    public static final String EMPTY = "";
    public static final String DOT = ".";
    public static String getWrapper(Object object) {

        if(object == null)
        {
            return EMPTY;
        }

        var type = object.getClass().getSimpleName();
        if (type.equals("String")) {
            return SINGLE_QUOTE;
        }

        if (type.equals("UUID")) {
            return SINGLE_QUOTE;
        }

        return EMPTY;
    }

}
