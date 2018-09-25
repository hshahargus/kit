package com.argusoft.kite.util;

import java.util.Map;
import java.util.Set;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.LoggerFactory;

/**
 * This class is used to define common d/b functions used in project
 *
 * @author kvithlani
 */
public class DbUtil {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DbUtil.class);
    //Query to retrieve primary key column from given table name
    private final static String primaryKeyColumnQueryString = "SELECT column_name as column_name FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS TC INNER JOIN"
            + " INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KU ON TC.CONSTRAINT_TYPE = 'PRIMARY KEY' AND TC.CONSTRAINT_NAME = KU.CONSTRAINT_NAME and ku.table_name=:tableName";

    public static Integer updateRecord(Session session, Map<String, Object> fieldsMap, String tableName) {

        SQLQuery primaryKeyColumnQuery = session.createSQLQuery(primaryKeyColumnQueryString);
        primaryKeyColumnQuery.setParameter("tableName", tableName);
        primaryKeyColumnQuery.addScalar("column_name", StandardBasicTypes.STRING);
        String keyColumn = (String) primaryKeyColumnQuery.uniqueResult();
        if (!fieldsMap.isEmpty()) {
            Object keyColumnValue = fieldsMap.get(keyColumn);
            StringBuilder params = new StringBuilder();
            Set<String> columnSet = fieldsMap.keySet();
            for (String column : columnSet) {
                //Restrict updation of key column value which is primary key
                if (!column.equals(keyColumn)) {
                    params.append("\"").append(column).append("\"=:").append(column).append(",");
                }
            }
            params.deleteCharAt(params.length() - 1);
            LOGGER.info("UPDATE " + tableName + " SET " + params + " WHERE " + keyColumn + "=?");
            SQLQuery updateQuery = session.createSQLQuery("UPDATE " + tableName + " SET " + params + " WHERE " + keyColumn + "=:" + keyColumn);
            for (Map.Entry<String, Object> entrySet : fieldsMap.entrySet()) {
                String key = entrySet.getKey();
                //Restrict updation of key column value which is primary key
                if (!key.equals(keyColumn)) {
                    Object value = entrySet.getValue();
                    updateQuery.setParameter(key, value);
                }
            }
            updateQuery.setParameter(keyColumn, keyColumnValue);
            return updateQuery.executeUpdate();
        }
        return null;
    }

    public static Integer insertRecord(Session session, Map<String, Object> fieldsMap, String tableName) {

        if (!fieldsMap.isEmpty()) {
            StringBuilder columnNames = new StringBuilder();
            StringBuilder columnValues = new StringBuilder();
            Set<String> columnSet = fieldsMap.keySet();
            for (String column : columnSet) {
                columnNames.append("\"").append(column).append("\"").append(",");
                columnValues.append(":").append(column).append(",");
            }
            columnNames.deleteCharAt(columnNames.length() - 1);
            columnValues.deleteCharAt(columnValues.length() - 1);
            LOGGER.info("INSERT INTO " + tableName + "(" + columnNames + ") VALUES(" + columnValues + ")");
            SQLQuery insertQuery = session.createSQLQuery("INSERT INTO " + tableName + "(" + columnNames + ") VALUES(" + columnValues + ")");
            for (Map.Entry<String, Object> entrySet : fieldsMap.entrySet()) {
                String key = entrySet.getKey();
                //Restrict updation of key column value which is primary key
                Object value = entrySet.getValue();
                insertQuery.setParameter(key, value);
            }
            return insertQuery.executeUpdate();
        }
        return null;
    }
}
