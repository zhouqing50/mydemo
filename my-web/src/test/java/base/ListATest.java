package base;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

/**
 * Description:
 * User: zhouq
 * Date: 2017/1/5
 */


public class ListATest {

    @Test
    public void long2Date() {
//        long longTime = new Date().getTime();
        long longTime = 1484103032912l;

        out.println(longTime);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        java.util.Date dt = new Date(longTime);
        String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
        out.println(sDateTime);

        out.println(new Date(1484103032912l));
    }

    @Test
    public void ye() {
        List<String> lists = Lists.newArrayList("aa", "bb", "cc");
        lists.forEach(a -> {
            if (a.equals("bb")) {
                return;
            }
            out.println(a);
        });
    }
}
