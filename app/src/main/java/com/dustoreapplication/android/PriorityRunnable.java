package com.dustoreapplication.android;

/**
 * Created by 16142
 * on 2020/6/5
 */
public class PriorityRunnable implements Runnable,Comparable<PriorityRunnable> {

    private Integer priority;
    private Runnable runnable;

    public PriorityRunnable(Integer priority,Runnable runnable) {
        this.priority = priority;
        this.runnable = runnable;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public int compareTo(PriorityRunnable o) {
        return Integer.compare(this.getPriority(),o.getPriority());
    }

    @Override
    public void run() {
        this.runnable.run();
    }
}
