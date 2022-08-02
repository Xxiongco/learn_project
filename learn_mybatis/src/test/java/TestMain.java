import com.panda.domian.Student;
import com.panda.util.SqlUtil;
import org.junit.jupiter.api.Test;

public class TestMain {

    @Test
    public void testGetSql() {

        String selectSql = SqlUtil.getSelectSql(Student.class);

        System.out.println(selectSql);

    }
}
