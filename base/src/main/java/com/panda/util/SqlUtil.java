package com.panda.util;


import java.lang.reflect.Field;

public class SqlUtil {

    public static String SELECT = "select";

    public static String SPACE = " ";

    public static String FROM = "from";

    public static String WHERE = "where";

    public static String AND = "and";

    public static String EQUAL = "=";

    public static String NO_EQUAL = "!=";

    public static String SPILT = ",";

    /**
     *  根据class 创建sql
     * @param cls
     * @return
     */
    public static String getSelectSql(Class<?> cls) {

        Field[] declaredFields = cls.getDeclaredFields();

        StringBuffer sql = new StringBuffer();
        sql.append(SELECT).append(SPACE);

        for (int i = 0; i < declaredFields.length - 1; i++) {
            sql.append(declaredFields[i].getName()).append(SPILT);
        }

        sql.append(declaredFields[declaredFields.length - 1].getName()).append(SPACE);

        sql.append(FROM).append(SPACE);
        sql.append(cls.getSimpleName().toLowerCase());

        return sql.toString();
    }

}
