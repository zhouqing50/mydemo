package redis;

import static java.lang.System.out;

/**
 * Description:
 * User: zhouq
 * Date: 2017/1/5
 */


public class RedisTest {

  public static void main(String [] args) throws Exception {

      String aa = "aa|cc|dd|";
      String cc = aa.substring(0, aa.length() - 1);
      out.println(cc);
    }

}
