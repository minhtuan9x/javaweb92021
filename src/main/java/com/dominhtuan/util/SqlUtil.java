package com.dominhtuan.util;

public class SqlUtil {
	public static void buildQueryUsingLike(String column, String value, StringBuilder where) {
		if (CheckInputUtil.isValid(value)) {
			where.append(String.format("\nand %s like '%s'", column, "%" + value + "%"));
		}
	}

	public static void buildQueryUsingLike(String column, String value, StringBuilder where, StringBuilder join,
			String joinQuery) {
		if (CheckInputUtil.isValid(value)) {
			join.append(joinQuery);
			where.append(String.format("\nand %s like '%s'", column, "%" + value + "%"));
		}
	}

	public static void buildQueryUsingOperator(String column, String operator, Object value, StringBuilder where) {
		if (CheckInputUtil.isValid(value)) {
			String valuePsd = (value instanceof String) ? ("'" + value + "'") : (value.toString());
			where.append(String.format("\nand %s %s %s", column, operator, valuePsd));
		}
	}

	public static void buildQueryUsingOperator(String column, String operator, Object value, StringBuilder where,
			StringBuilder join, String joinQuery) {
		if (CheckInputUtil.isValid(value)) {
			join.append(joinQuery);
			String valuePsd = (value instanceof String) ? ("'" + value.toString() + "'") : (value.toString());
			where.append(String.format("\nand %s %s %s", column, operator, valuePsd));
		}
	}

	public static void buildQueryUsingBetween(String column, Object from, Object to, StringBuilder where) {
		if (CheckInputUtil.isValid(from) || CheckInputUtil.isValid(to)) {
			String fromPsd = (!CheckInputUtil.isValid(from)) ? "0" : from.toString();
			String toPsd = (!CheckInputUtil.isValid(to)) ? String.valueOf(Integer.MAX_VALUE) : to.toString();
			where.append(String.format("\nand %s between %s and %s ", column, fromPsd, toPsd));
		}
	}

	public static void buildQueryUsingBetween(String column, Object from, Object to, StringBuilder where,
			StringBuilder join, String joinQuery) {
		if (CheckInputUtil.isValid(from) || CheckInputUtil.isValid(to)) {
			String fromPsd = (!CheckInputUtil.isValid(from)) ? "0" : from.toString();
			String toPsd = (!CheckInputUtil.isValid(to))
					? String.valueOf(from instanceof Integer ? Integer.MAX_VALUE : Double.MAX_VALUE)
					: to.toString();
			join.append(joinQuery);
			where.append(String.format("\nand %s between %s and %s ", column, fromPsd, toPsd));
		}
	}
}
