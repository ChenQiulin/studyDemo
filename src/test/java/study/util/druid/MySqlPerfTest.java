package study.util.druid;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import junit.framework.TestCase;
import org.junit.Ignore;

import java.util.List;


@Ignore
public class MySqlPerfTest extends TestCase {

    private String sql;

    protected void setUp() throws Exception {
//        sql = "SELECT * FROM T";
        sql = "SELECT ID, NAME, AGE FROM USER WHERE ID = ?";

//        sql = Utils.readFromResource("benchmark/sql/ob_sql.txt");
    }

    public void test_pert() throws Exception {
        for (int i = 0; i < 10; ++i) {
            perfMySql(sql);
        }
    }

    long perfMySql(String sql) {
        long startYGC = TestUtils.getYoungGC();
        long startYGCTime = TestUtils.getYoungGCTime();
        long startFGC = TestUtils.getFullGC();

        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < 1000 * 1000; ++i) {
            execMySql(sql);
        }
        long millis = System.currentTimeMillis() - startMillis;

        long ygc = TestUtils.getYoungGC() - startYGC;
        long ygct = TestUtils.getYoungGCTime() - startYGCTime;
        long fgc = TestUtils.getFullGC() - startFGC;

        System.out.println("MySql\t" + millis + ", ygc " + ygc + ", ygct " + ygct + ", fgc " + fgc);
        return millis;
    }

    private String execMySql(String sql) {
        StringBuilder out = new StringBuilder();
        MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);
        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        for (SQLStatement statement : statementList) {
            statement.accept(visitor);
            visitor.println();
        }
        return out.toString();
    }
}