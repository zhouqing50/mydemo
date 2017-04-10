package file;

import org.junit.Test;

import java.io.*;

import static java.lang.System.out;

/**
 * Description:
 * User: zhouq
 * Date: 2017/4/10
 */


public class FileReder {
    private static final String filePath = "D:\\tt.txt";
    private static final String fileInPath = "D:\\ttIn.txt";

    @Test
    public void testBufferedReader() throws IOException {
        File f = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line;
        try {
            while ((line = reader.readLine()) != null){
                out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }

    @Test
    public void testOutStream() throws IOException {
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
        FileInputStream inputStream = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream(new File(fileInPath));
        int index;// 每次读取到的字节数组的长度
        byte[] b = new byte[1024];// 用来存储每次读取到的字节数组
        try {
            while ((index = inputStream.read(b)) != -1){
                outputStream.write(b, 0, index);// 写入到输出流
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }
}
