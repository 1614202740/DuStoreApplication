package com.dustoreapplication.android;

import android.telecom.Call;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by 16142
 * on 2020/6/5
 */
public class ComparableFutureTask extends FutureTask implements Comparable<ComparableFutureTask> {

    private Integer priority;

    public ComparableFutureTask(Integer priority, Callable<Integer> callable) {
        super(callable);
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public int compareTo(ComparableFutureTask o) {
        return Integer.compare(this.getPriority(),o.getPriority());
    }
}
