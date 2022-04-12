package com.laptrinhjavaweb.utils;

import com.laptrinhjavaweb.anotation.LikeField;
import com.laptrinhjavaweb.anotation.OperatorField;
import com.laptrinhjavaweb.anotation.SearchObject;
import com.laptrinhjavaweb.exception.ObjectSearchException;

import java.lang.reflect.Field;

public class SqlUtils {

    public static String toQuery(Object searchObject, String... differences) {
        StringBuilder query = new StringBuilder();
        StringBuilder join = new StringBuilder();
        StringBuilder where = new StringBuilder("\nwhere 1=1");

        Class<?> classObj = searchObject.getClass();
        String nameTable = "";
        try {
            if (classObj.isAnnotationPresent(SearchObject.class)) {
                SearchObject searchObjectAno = classObj.getAnnotation(SearchObject.class);
                if (searchObjectAno.tableName().isEmpty())
                    throw new ObjectSearchException("Require table name !!!");
                else {
                    if (searchObjectAno.aliasValue().isEmpty()) {
                        nameTable = searchObjectAno.tableName();
                        query.append("select * from ").append(nameTable);
                    } else {
                        nameTable = searchObjectAno.aliasValue();
                        query.append("select * from ").append(searchObjectAno.tableName()).append(" as ").append(nameTable);
                    }

                }

                Field[] fields = classObj.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object fieldValue = field.get(searchObject);
                    if (ValidateUtil.isValid(fieldValue)) {
                        String fieldName = field.getName().toLowerCase();
                        if (field.isAnnotationPresent(OperatorField.class)) {
                            OperatorField operatorField = field.getAnnotation(OperatorField.class);
                            if (field.getType().toString().equals("class java.lang.String")) {
                                where.append(String.format("\nand %s.%s %s '%s'", nameTable, fieldName, operatorField.operator(), fieldValue.toString()));
                            }
                            if (field.getType().toString().equals("class java.lang.Integer")) {
                                if (fieldName.endsWith("from") || fieldName.endsWith("to")) {
                                    String fieldNameBetween = fieldName.replace("from", "").replace("to", "");
                                    where.append(String.format("\nand %s.%s %s %s",
                                            nameTable, fieldNameBetween,
                                            operatorField.operator(), fieldValue.toString()));
                                } else {
                                    where.append(String.format("\nand %s.%s %s %s", nameTable, fieldName, operatorField.operator(), fieldValue.toString()));
                                }

                            }
                        }
                        if (field.isAnnotationPresent(LikeField.class)) {
                            where.append(String.format("\nand %s.%s like '%s'", nameTable, fieldName,
                                    "%" + fieldValue.toString() + "%"));
                        }
//
                    }
                }
//                Ex different: join:,query:
                for (String item : differences) {
                    String[] str = item.trim().split(",");
                    for (String strItem : str) {
                        if (strItem.startsWith("join")) {
                            String joinDiff = strItem.replace("join:", "");
                            join.append("\n").append(joinDiff);
                        }
                        if (strItem.startsWith("query")) {
                            String queryDiff = strItem.replace("query:", "");
                            where.append("\nand ").append(queryDiff);
                        }
                    }
                }
                query.append(join);
                query.append(where);
                if (searchObjectAno.groupBy())
                    query.append("\ngroup by ").append(nameTable).append(".id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return query.toString();
    }
}
