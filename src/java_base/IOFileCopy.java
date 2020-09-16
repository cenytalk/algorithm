package java_base;

import java.io.*;

/**
 *  第12讲 | Java有几种文件拷贝方式？哪一种最高效？
 * java.io 文件拷贝
 */
public class IOFileCopy {

    public static void copyFileByStream(File source, File dest) throws
            IOException {
        try (InputStream is = new FileInputStream(source);
             OutputStream os = new FileOutputStream(dest);) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File source = new File("D:\\class\\Foo.java");
        File dest = new File("D:\\class\\Copy.java");
        IOFileCopy.copyFileByStream(source, dest);
    }

}
