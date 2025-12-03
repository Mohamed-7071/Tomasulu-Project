package com.example.demo.logic;

public abstract class Buffer_Station extends RegBufStat {
    public int Busy;
    public float output;
    protected boolean queuedForPublish;
    protected boolean executionStarted;  // Track if execution has started

    public boolean IsBusy(){
        return Busy == 1;
    }

    protected boolean isQueuedForPublish() {
        return queuedForPublish;
    }

    protected void markQueuedForPublish() {
        this.queuedForPublish = true;
    }

    protected void resetPublishState() {
        this.queuedForPublish = false;
        this.executionStarted = false;
    }
    
    protected boolean hasExecutionStarted() {
        return executionStarted;
    }
    
    protected void markExecutionStarted() {
        this.executionStarted = true;
    }
    
    public abstract void publish(String tag);
    
    public abstract void run();

    public abstract void clear();
}