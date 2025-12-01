package com.example.demo.logic;

public abstract class Buffer_Station extends RegBufStat {
    public int Busy;
    public int output;
    protected boolean queuedForPublish;

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
    }
    
    public abstract void publish(String tag);
    
    public abstract void run();

    public abstract void clear();
}