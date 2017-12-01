package study.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * SQL 工具类
 * 
 * @author Harry Wu
 * @teme 2010-6-1 下午12:08:49
 */
@SuppressWarnings({ "rawtypes" })
public final class SqlUtil {

	/**
	 * date format
	 */
	private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static ThreadLocal threadlocal = new ThreadLocal() {
		@Override
		protected Object initialValue() {
			return new SimpleDateFormat(DATE_FORMAT);
		}
	};

	/**
	 * 返回当前线程date format。
	 * 
	 * @return
	 */
	public static DateFormat getDateFormat() {
		return (DateFormat) threadlocal.get();
	}

	/**
	 * 设置date format
	 * 
	 * @param format
	 *            -- date format
	 */
	public static void setDateFormat(String format) {
		DATE_FORMAT = format;
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * 
	 * @param value
	 *            -- 数据
	 * @return -- SQL片段字符串
	 */
	private static String sqlValue(String value) {
		if (null == value) {
			return "''";
		}
		String v = value.trim();
		int vs = v.length();
		StringBuilder sb = new StringBuilder(2 + vs * 2);
		char c = 0;
		sb.append('\'');
		for (int i = 0; i < vs; i++) {
			c = v.charAt(i);
			// 防止sql注入处理，替换'为''，替换\为\\
			if ('\'' == c) {
				sb.append('\'');
				sb.append('\'');
			} else if ('\\' == c) {
				sb.append('\\');
				sb.append('\\');
			} else {
				sb.append(c);
			}
		}
		sb.append('\'');
		return sb.toString();
	}

	/**
	 * 拼接SQL语法的字段字符串值，默认日期格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param value
	 *            -- 数据
	 * @return -- SQL片段字符串
	 */
	private static String sqlValue(Date value) {
		return "'" + getDateFormat().format(value) + "'";
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * 
	 * @param value
	 *            -- 数据
	 * @param simpleDateFormat
	 *            -- 自定义日期格式
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(Date value, SimpleDateFormat simpleDateFormat) {
		return "'" + simpleDateFormat.format(value) + "'";
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * 
	 * @param value
	 *            -- 数据
	 * @return -- SQL片段字符串
	 */
	private static String sqlValue(Timestamp value) {
		return "'" + value + "'";
	}

	/**
	 * 拼接SQL语法的字段字符串值，适用于基本数据类型
	 * 
	 * @param value
	 * @return
	 */
	private static <T> String sqlValuePrimitive(T value) {
		return value.toString();
	}

	/**
	 * 拼接SQL语法的字段字符串值，适用于数组类型
	 * 
	 * @param value
	 * @return
	 */
	private static <T> String sqlValueArray(T[] value) {
		if (null == value) {
			return "''";
		}
		StringBuilder sql = new StringBuilder(64 + value.length * 32);
		for (int i = 0; i < value.length; i++) {
			sql.append(sqlValue(value[i]));
			if (i < value.length - 1) {
				sql.append(",");
			}
		}
		return sql.toString();
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * 
	 * @param value
	 *            -- 数据
	 *            <p>
	 *            注意：如果字段为datetime类型，object
	 *            value不能为null，因为通过jdbc访问mysql时，datetime类型值为''时，会抛出异常
	 * @return -- SQL片段字符串，如果value为null，返回字符串：''
	 */
	public static String sqlValue(Object value) {
		if (null == value) {
			return "''";
		} else if (value instanceof String) {
			return sqlValue((String) value);
		} else if (value instanceof Date) {
			return sqlValue((Date) value);
		} else if (value instanceof Timestamp) {
			return sqlValue((Timestamp) value);
		} else if (value instanceof Integer || value instanceof Long || value instanceof Short
				|| value instanceof Float || value instanceof Double) {
			// 基本数字类型
			return sqlValuePrimitive(value);
		} else if (value instanceof List) {
			return sqlValueArray(((List) value).toArray());
		} else if (value.getClass().isArray()) {
			// 数组类型，（基本数据类型没法进行autoboxing，需要进行额外处理）
			Class ct = value.getClass().getComponentType();
			if (ct == String.class) {
				return sqlValueArray(String[].class.cast(value));
			} else if (ct == int.class) {
				return sqlValueArray(boxedPrimitiveArray((int[]) value));
			} else if (ct == long.class) {
				return sqlValueArray(boxedPrimitiveArray((long[]) value));
			} else if (ct == short.class) {
				return sqlValueArray(boxedPrimitiveArray((short[]) value));
			} else if (ct == float.class) {
				return sqlValueArray(boxedPrimitiveArray((float[]) value));
			} else if (ct == double.class) {
				return sqlValueArray(boxedPrimitiveArray((double[]) value));
			}
			// 默认,转成Object对象数组
			return sqlValueArray((Object[]) value);
		} else {
			return "'" + value.toString() + "'";
		}
	}

	/**
	 * boxed int array
	 * 
	 * @param array
	 * @return
	 */
	private static Integer[] boxedPrimitiveArray(int[] array) {
		Integer[] result = new Integer[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}

	/**
	 * boxed short array
	 * 
	 * @param array
	 * @return
	 */
	private static Short[] boxedPrimitiveArray(short[] array) {
		Short[] result = new Short[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}

	/**
	 * boxed long array
	 * 
	 * @param array
	 * @return
	 */
	private static Long[] boxedPrimitiveArray(long[] array) {
		Long[] result = new Long[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}

	/**
	 * boxed float array
	 * 
	 * @param array
	 * @return
	 */
	private static Float[] boxedPrimitiveArray(float[] array) {
		Float[] result = new Float[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}

	/**
	 * boxed double array
	 * 
	 * @param array
	 * @return
	 */
	private static Double[] boxedPrimitiveArray(double[] array) {
		Double[] result = new Double[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}

	/**
	 * 用类似PreparedStatement的方式获取需要执行的sql
	 * 
	 * @param prepareSql
	 *            PreparedStatement形式的sql模板，如：select * from tba where dept= ?
	 *            and level > ?
	 * @param params
	 *            参数列表 <br/>
	 *            注意：params不能为对象数组，包括String数组，如：SqlUtil.getSql(
	 *            "select * from tba where name in (?)", new
	 *            String[]{"harry","demo"}) <br/>
	 *            但是基本类型数组是可以的，例如 new int[]{1,2,3}，如需使用对象数组请使用getSqlByList方法
	 * @return 被执行的sql
	 */
	public static String getSql(String prepareSql, Object... params) {
		if (params != null) {
			int length = prepareSql.length();
			StringBuilder result = new StringBuilder(2 + length * 2);
			int paramIndex = 0;
			for (int i = 0; i < length; i++) {
				char c = prepareSql.charAt(i);
				if (c == '?') {
					result.append(sqlValue(params[paramIndex]));
					paramIndex++;
				} else {
					result.append(c);
				}
			}
			return result.toString();
		}
		return prepareSql;
	}

	/**
	 * 用类似PreparedStatement的方式获取需要执行的sql，参数通过List传递
	 * 
	 * @param prepareSql
	 *            PreparedStatement形式的sql模板
	 * @param params
	 *            参数列表
	 * @return
	 */
	public static String getSqlByList(String prepareSql, List<Object> params) {
		if (params != null) {
			int length = prepareSql.length();
			StringBuilder result = new StringBuilder(2 + length * 2);
			int paramIndex = 0;
			for (int i = 0; i < length; i++) {
				char c = prepareSql.charAt(i);
				if (c == '?') {
					result.append(sqlValue(params.get(paramIndex)));
					paramIndex++;
				} else {
					result.append(c);
				}
			}
			return result.toString();
		}
		return prepareSql;
	}

	public static String getUpdateSql(String tableName, Map<String, Object> params, String condition) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" update ").append(tableName).append(" set ");
		for (Map.Entry entry : params.entrySet()) {
			sqlBuilder.append(entry.getKey()).append(SqlUtil.getSql("= ? ", entry.getValue())).append(",");
		}
		String sql = sqlBuilder.toString();
		sql = StringUtils.removeEnd(sql, ",");
		if (StringUtil.isNullOrBlank(condition)) {
			sql += "  " + condition;
		}
		return sql;
	}

	/**
	 * 拼接 insert SQL语句
	 * 
	 * @param tableName
	 *            -- db 表名称
	 * @param columns
	 *            -- 列集合map
	 * @return -- SQL语句
	 */
	public static String getInsertSql(String tableName, Map<String, Object> columns) {
		int columnSize = columns.size();
		StringBuilder sql = new StringBuilder(64 + columnSize * 32);
		sql.append(" insert into ").append(tableName);
		sql.append(" ( ");
		int index = 0;
		for (String item : columns.keySet()) {
			sql.append(item);
			index++;
			if (index != columnSize) {
				sql.append(",");
			}
		}
		sql.append(" )");
		sql.append(" values ( ");
		index = 0;
		for (Map.Entry entry : columns.entrySet()) {
			sql.append(sqlValue(entry.getValue()));
			index++;
			if (index != columnSize) {
				sql.append(",");
			}
		}
		sql.append(" )");
		return sql.toString();
	}
	
	/**
	 * 拼接 insert SQL语句
	 * 
	 * @param tableName
	 *            -- db 表名称
	 * @param columns
	 *            -- 列集合map
	 * @param condition
	 *            -- 查询条件
	 * @return -- SQL语句
	 */
	public static String getSelectBeforeInsertSql(String tableName, Map<String, Object> columns, String condition) {
		int columnSize = columns.size();
		StringBuilder sql = new StringBuilder(64 + columnSize * 32);
		sql.append(" insert into ").append(tableName);
		sql.append(" ( ");
		int index = 0;
		for (String item : columns.keySet()) {
			sql.append(item);
			index++;
			if (index != columnSize) {
				sql.append(",");
			}
		}
		sql.append(" )");
		sql.append(" select ");
		index = 0;
		for (Map.Entry entry : columns.entrySet()) {
			sql.append(sqlValue(entry.getValue()));
			index++;
			if (index != columnSize) {
				sql.append(",");
			}
		}
		sql.append(" FROM DUAL ").append(condition);
		return sql.toString();
	}

}
