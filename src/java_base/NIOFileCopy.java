package java_base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 第12讲 | Java有几种文件拷贝方式？哪一种最高效？
 * java.nio 文件拷贝
 */
public class NIOFileCopy {

    public static void copyFileByChannel(File source, File dest) throws
            IOException {
        try (FileChannel sourceChannel = new FileInputStream(source)
                .getChannel();
             FileChannel targetChannel = new FileOutputStream(dest).getChannel
                     ()) {
            for (long count = sourceChannel.size(); count > 0; ) {
                long transferred = sourceChannel.transferTo(
                        sourceChannel.position(), count, targetChannel);
                sourceChannel.position(sourceChannel.position() + transferred);
                count -= transferred;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File source = new File("D:\\class\\Foo.java");
        File dest = new File("D:\\class\\NIOCopy.java");

        NIOFileCopy.copyFileByChannel(source,dest);
    }
}
