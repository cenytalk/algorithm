package java_base;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用与队列
 * 其实对这个例子不懂
 */
public class RefQueueExample {
    public static void main(String[] args) {

        Object counter = new Object();
        ReferenceQueue refQueue = new ReferenceQueue<>();
        PhantomReference<Object> p = new PhantomReference<>(counter, refQueue);
        counter = null;
        System.gc();
        try {
            // Remove是一个阻塞方法，可以指定timeout，或者选择一直阻塞
            Reference<Object> ref = refQueue.remove(1000L);
            if (ref != null) {
                // do something
                System.out.println(" 虚引用");
            }
        } catch (InterruptedException e) {
            // Handle it
            System.out.println("抓异常");
        }
    }
}
