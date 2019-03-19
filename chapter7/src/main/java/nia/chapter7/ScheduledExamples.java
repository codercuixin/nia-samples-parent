package nia.chapter7;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * * @Author: cuixin
 * * @Date: 2019/3/19 9:15
 */
public class ScheduledExamples {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void main(String[] args) throws InterruptedException {
        schedule();
    }
    /**
     * Listing 7.2 Scheduling a task with a ScheduledExecutorService
     */
    public static void schedule() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        ScheduledFuture<?> future = executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("Now it is 60 seconds later");
            }
        }, 60, TimeUnit.SECONDS);

        //...
        Thread.sleep(120000);
        executor.shutdown();
    }


    /**
     * Listing 7.3 Scheduling a task with EventLoop
     */
    public static void scheduleViaEventLoop(){
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("Now it is 60 seconds later");
            }
        }, 60, TimeUnit.SECONDS);
    }

    /**
     * Listing 7.4 Scheduling a recurring task with EventLoop
     */
    public static void scheduleFixedViaEventLoop(){
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run every 60 seconds");
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

    /**
     * Listing 7.5 Canceling a task using ScheduledFuture
      */
    public static void cancellingTaskUsingSchedulingFuture(){
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Run every 60 seconds");
            }
        }, 60, 60, TimeUnit.SECONDS);
        //
        boolean mayInterruptIfRunning =false;
        future.cancel(mayInterruptIfRunning);
    }

}
