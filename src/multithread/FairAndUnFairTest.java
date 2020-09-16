package multithread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairAndUnFairTest {
    private static Lock  fairLock=new ReentrantLock2(true);
    private static Lock unfairLock=new ReentrantLock2(false);
    private static CountDownLatch start;

    public void fair(){
        testLock(fairLock);
    }

    public void unfair(){
        testLock(unfairLock);
    }

    public void testLock(Lock lock){
        start=new CountDownLatch(1);
        for(int i=0;i<5;i++){
            Thread thread=new Job(lock);
            thread.setName(""+i);
            thread.start();
        }
        start.countDown();
    }


    private static final class ReentrantLock2 extends ReentrantLock {
        private static final long serialVersionUID = -7900926736907012313L;

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        /**
         * 该方法返回正在等待获取锁的线程列表
         *
         * @return
         */
        @Override
        protected Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<Thread>(super.getQueuedThreads());
            //由于列表是逆序输出，在此对其进行翻转
            Collections.reverse(arrayList);
            return arrayList;
        }
    }

    private static final class Job extends Thread {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                start.await();
            }catch (InterruptedException e){

            }
            for(int i=1;i<2;i++){
                lock.lock();
                try {

                }finally {

                }
            }
            super.run();
        }

        public String toString(){
            return getName();
        }

    }
}
