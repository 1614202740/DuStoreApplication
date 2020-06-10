package com.dustoreapplication.android;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 16142
 * on 2020/5/25
 */
public class MyThreadFactory implements ThreadFactory {

    private AtomicInteger atomicInteger = new AtomicInteger();
    private boolean isDaemon;

    public MyThreadFactory(boolean isDaemon){
        this.isDaemon = isDaemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        atomicInteger.incrementAndGet();
        Thread thread = new MyWorkThread(atomicInteger, r);
        thread.setDaemon(isDaemon);
        return thread;
    }

    class MyWorkThread extends Thread{
        private AtomicInteger atomicInteger;

        private MyWorkThread(AtomicInteger atomicInteger, Runnable runnable){
            super(runnable);
            this.atomicInteger = atomicInteger;
        }

        public AtomicInteger getAtomicInteger() {
            return atomicInteger;
        }
    }
}
