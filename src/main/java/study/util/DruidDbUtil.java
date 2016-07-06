/**
 * Copyright 2015 ProficientCity Inc.
 */
package study.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.*;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * 数据库操作工具类
 *
 * @author Wilson Chen
 */
public class DruidDbUtil {

    private static Logger logger = LoggerFactory.getLogger(DruidDbUtil.class);

    /**
     * 多数据源 名字与数据源Map
     */
    private static Map<String, DataSource> dataSourceMap;

    static {
        registerPoolFromProperties("/druid.properties");
    }

    /**
     * 执行ID自增长insert操作
     *
     * @param poolAlias 连接池别名
     * @param sql
     * @return 自增长ID
     */
    public static int executeAutoIncInsert(String poolAlias, String sql) {
        Connection cnn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            cnn = DruidDbUtil.getConnection(poolAlias);
            stmt = cnn.createStatement();
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                int newId = rs.getInt(1);
                return newId;
            } else {
                return -1;
            }
        } catch (SQLException e) {
            logger.error("executeAutoIncInsert fail[{}]", sql, e);
            throw new RuntimeException("executeAutoIncInsert fail", e);
        } finally {
            CloseUtil.closeSilently(rs);
            CloseUtil.closeSilently(stmt);
            CloseUtil.closeSilently(cnn);
        }
    }

    /**
     * 执行ID自增长insert操作
     *
     * @param poolAlias 连接池别名
     * @param sql
     * @return 自增长ID
     */
    public static long executeAutoIncToLong(String poolAlias, String sql) {
        Connection cnn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            cnn = DruidDbUtil.getConnection(poolAlias);
            stmt = cnn.createStatement();
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                long newId = rs.getLong(1);
                return newId;
            } else {
                return -1;
            }

        } catch (SQLException e) {
            logger.error("executeAutoIncInsert fail[{}]", sql, e);
            throw new RuntimeException("executeAutoIncInsert fail", e);
        } finally {
            CloseUtil.closeSilently(rs);
            CloseUtil.closeSilently(stmt);
            CloseUtil.closeSilently(cnn);
        }
    }


    public static int executeUpdate(String poolAlias, String tableName, Map<String, Object> params, String condition) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" update ").append(tableName).append(" set ");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sqlBuilder.append(entry.getKey()).append(SqlUtil.getSql("= ? ", entry.getValue())).append(",");
        }
        String sql = sqlBuilder.toString();
        sql = StringUtils.removeEnd(sql, ",");
        if (!StringUtil.isNullOrBlank(condition)) {
            sql += "  " + condition;
        }
        logger.debug("sql4update" + tableName + ": " + sql);
        return DruidDbUtil.executeUpdate(poolAlias, sql);
    }

    /**
     * 执行update操作
     *
     * @param poolAlias 连接池别名
     * @param sql
     * @return 更新影响行数
     */
    public static int executeUpdate(String poolAlias, String sql) {
        Connection cnn = null;
        Statement stmt = null;
        try {
            cnn = DruidDbUtil.getConnection(poolAlias);
            stmt = cnn.createStatement();
            int updateCount = stmt.executeUpdate(sql);
            return updateCount;
        } catch (SQLException e) {
            logger.error("executeUpdate fail[{}]", sql, e);
            throw new RuntimeException("executeUpdate fail", e);
        } finally {
            CloseUtil.closeSilently(stmt);
            CloseUtil.closeSilently(cnn);
        }
    }

    public static ResultSet executeQuery(String poolAlias, String sql) {
        Connection cnn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            cnn = DruidDbUtil.getConnection(poolAlias);
            stmt = cnn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            logger.error("executeUpdate fail[{}]", sql, e);
            throw new RuntimeException("executeUpdate fail", e);
        } finally {
            CloseUtil.closeSilently(rs);
            CloseUtil.closeSilently(stmt);
            CloseUtil.closeSilently(cnn);
        }
    }

    /**
     * 执行Query操作，返回查询结果
     *
     * @param poolAlias
     * @param sql
     * @return - 当查询结果集为空时，返回结果非NULL
     */
    public static List<Map<String, Object>> executeQueryForMap(String poolAlias, String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DruidDbUtil.getConnection(poolAlias);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            List<Map<String, Object>> rowData = new ArrayList<>();
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        row.put(meta.getColumnLabel(i), rs.getObject(i));
                    }
                    rowData.add(row);
                }
            }
            return rowData;
        } catch (SQLException e) {
            logger.error("executeQuery fail[{}]", sql, e);
            throw new RuntimeException("executeQuery fail", e);
        } finally {
            CloseUtil.closeSilently(rs);
            CloseUtil.closeSilently(stmt);
            CloseUtil.closeSilently(conn);
        }
    }

    public static Object querySingleValue(String poolAlias, String sql) {
        Connection cnn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            cnn = DruidDbUtil.getConnection(poolAlias);
            stmt = cnn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getObject(1);
            }
            return null;
        } catch (SQLException e) {
            logger.error("executeUpdate fail[{}]", sql, e);
            throw new RuntimeException("executeUpdate fail", e);
        } finally {
            CloseUtil.closeSilently(rs);
            CloseUtil.closeSilently(stmt);
            CloseUtil.closeSilently(cnn);
        }
    }

    /**
     * 执行query操作
     *
     * @param poolAlias 连接池别名
     * @param sql
     * @param clazz     映射对象类型
     * @return 对象列表
     */
    public static <K> List<K> executeQuery(String poolAlias, String sql, Class<K> clazz) {
        Connection cnn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            cnn = DruidDbUtil.getConnection(poolAlias);
            stmt = cnn.createStatement();
            rs = stmt.executeQuery(sql);
            return getMappingBeans(rs, clazz);
        } catch (SQLException e) {
            logger.error("executeUpdate fail[{}]", sql, e);
            throw new RuntimeException("executeUpdate fail", e);
        } finally {
            CloseUtil.closeSilently(rs);
            CloseUtil.closeSilently(stmt);
            CloseUtil.closeSilently(cnn);
        }
    }

    /**
     * 自动映射数据库结果集到对象列表
     *
     * @param rs    数据库结果集
     * @param clazz 对象类型
     * @return 对象列表
     */
    public static <K> List<K> getMappingBeans(ResultSet rs, Class<K> clazz) {
        if (null == rs) {
            return null;
        }
        try {
            // 获取结果集的meta数据
            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();
            // 构建clazz method map
            Method[] methods = clazz.getMethods();
            Map<String, Method> methodMap = new HashMap<String, Method>();
            for (Method method : methods) {
                methodMap.put(method.getName(), method);
            }
            // 遍历结果集
            List<K> rt = new ArrayList<K>();
            while (rs.next()) {
                // 创建bean对象
                K bean = clazz.newInstance();
                for (int i = 1; i <= columns; i++) {
                    // 根据column name构造bean的set method name
                    String columnName = meta.getColumnLabel(i);
                    String setMethodName = "set" + StringUtil.getCamelCaseString("_" + columnName);
                    // 获取Bean方法
                    Method method = methodMap.get(setMethodName);
                    if (method != null) {
                        // 调用Bean方法填充值
                        Object columnValue = rs.getObject(i);
                        // TODO 数字基本类型在数据库中为null时，set方法会异常，所以先判断columnValue不为空
                        if (columnValue != null) {
                            // TODO maybe here should convert to columnValue to
                            // concrete java type
                            method.invoke(bean, columnValue);
                        }
                    }
                }
                System.out.println(JSON.toJSONString(bean));
                rt.add(bean);
            }
            return rt;
        } catch (Exception ex) {
            throw new RuntimeException("getMappingBeans失败", ex);
        }
    }

    /**
     * 根据连接池别名取数据库连接
     *
     * @param alias -- 连接池别名
     * @return -- 数据库连接
     * @throws -- 运行时应用异常(RuntimeAppException)
     */
    public static Connection getConnection(String alias) {

        Connection conn = null;
        try {
            conn = dataSourceMap.get(alias).getConnection();
            //logger.debug("通过别名[{}]获取数据库连接", alias);
        } catch (Exception ex) {
            CloseUtil.closeSilently(conn);
            logger.error("根据别名[{}]取数据库连接失败", alias, ex);
            throw new RuntimeException(ex);
        }
        return conn;
    }

    /**
     * 根据xml配置文件注册数据库连接池
     *
     * @param confile -- properties 配置文件路径
     * @throws -- 运行时应用异常(RuntimeAppException)
     */
    public static synchronized void registerPoolFromProperties(String confile) {
        confile = MoreObjects.firstNonNull(confile, "/druid.properties");

        Properties p = new Properties();
        InputStream inputStream = null;
        try {
            dataSourceMap = new HashMap<>();
            //java应用
            confile = DruidDbUtil.class.getClassLoader().getResource("").getPath()
                    + confile;
            File file = new File(confile);
            inputStream = new BufferedInputStream(new FileInputStream(file));
            p.load(inputStream);

            String aliasNameArr[] = p.getProperty("aliasNames").split(",");
            Map<String, Map> dataSourceParamMap = new HashMap<String, Map>();
            for (String aliasName : aliasNameArr) {
                dataSourceParamMap.put(aliasName, new HashMap<String, Object>());
            }
            Iterator iterator = p.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String dataSource = entry.getKey().toString().split("\\.")[0];
                if (!Objects.equals("aliasNames", entry.getKey()) && dataSourceParamMap.containsKey(dataSource)) {
                    String propertyName = entry.getKey().toString().split("\\.")[1];
                    dataSourceParamMap.get(dataSource).put(propertyName, entry.getValue());
                }
            }
            for (String dataSourceName : dataSourceParamMap.keySet()) {
                DataSource dataSource = DruidDataSourceFactory.createDataSource(dataSourceParamMap.get(dataSourceName));
//                DruidDataSource druidDataSource = (DruidDataSource)dataSource;
//                druidDataSource.
                dataSourceMap.put(dataSourceName, dataSource);
//                SQLUtils.
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 执行query单列字段操作
     *
     * @param poolAlias 连接池别名
     * @param sql
     * @param clazz     映射对象类型
     * @return 对象列表
     */
    public static <K> List<K> executeSingleColumnQuery(String poolAlias, String sql, Class<K> clazz) {
        Connection cnn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<K> result = null;
        try {
            cnn = DruidDbUtil.getConnection(poolAlias);
            stmt = cnn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs != null) {
                result = new ArrayList<K>();
                while (rs.next()) {
                    @SuppressWarnings("unchecked")
                    K columnValue = (K) rs.getObject(1);
                    result.add(columnValue);
                }
            }
            return result;
        } catch (SQLException e) {
            logger.error("executeSingleColumnQuery fail[{}]", sql, e);
            throw new RuntimeException("executeSingleColumnQuery fail", e);
        } finally {
            CloseUtil.closeSilently(rs);
            CloseUtil.closeSilently(stmt);
            CloseUtil.closeSilently(cnn);
        }
    }
}
