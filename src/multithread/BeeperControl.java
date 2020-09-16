package multithread;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.util.concurrent.*;

public class BeeperControl {
    private final ScheduledExecutorService scheduler = Executors.
            newScheduledThreadPool(1);

    public void beeperForAnHour() {

        final Runnable beeper = new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = new Date();
                System.out.println("beep:" + sdf.format(date));
            }
        };

        final ScheduledFuture<?> beeperHandle = scheduler.
                scheduleAtFixedRate(beeper, 5, 5, TimeUnit.SECONDS);

        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                beeperHandle.cancel(true);
            }
        }, 120, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        BeeperControl bc = new BeeperControl();
        bc.beeperForAnHour();
    }
}
