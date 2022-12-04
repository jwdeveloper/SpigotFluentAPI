package jw.fluent.api.database.mysql.utils;

public class SqlTypes {

    public static final String VARCHAR = "VARCHAR";

    public static final String CHAR = "CHAR";

    public static final String TEXT = "TEXT";

    public static final String BIT = "BIT";

    public static final String BOOL = "BOOL";

    public static final String INT = "INT";

    public static final String BIGINT = "BIGINT";

    public static final String FLOAT = "FLOAT";

    public static final String TIMESTAMP = "TIMESTAMP";

    public static final String DATETIME = "DATETIME";

    public static final String TINYINT = "TINYINT";

    public static String fromType(Class<?> type) {
        var name = type.getSimpleName();
        switch (name) {

            case "int":
                return INT;
            case "long":
                return BIGINT;
            case "bool":
                return BOOL;
            case "byte":
                return TINYINT;
            case "float":
                return FLOAT;
            case "dateTime":
                return DATETIME;
            case "char":
                return CHAR;
            default:
                return VARCHAR;
        }
    }
}
