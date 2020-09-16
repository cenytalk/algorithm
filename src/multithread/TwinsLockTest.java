package multithread;


import java.util.concurrent.locks.Lock;

public class TwinsLockTest {
    public void test() {
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        //启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            //worker.setDaemon(true);
            worker.start();
        }

        //每隔1s换行
        /*for (int i = 0; i < 10; i++) {
            SleepUtils.second(2);
            System.out.println();
        }*/

        for (; ; ) {
            SleepUtils.second(2);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        TwinsLockTest test = new TwinsLockTest();
        test.test();
    }
}
