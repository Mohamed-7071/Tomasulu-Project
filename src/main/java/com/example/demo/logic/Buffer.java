package com.example.demo.logic;

import java.util.HashMap;

public class Buffer extends Buffer_Station {
    public int Address;
    public int V;
    public String Q;
    public int Executing_Time = -1;
    

    public void issue(int address, int v, String q){
        this.Busy = 1;
        this.V = v;
        this.Q = q;
        this.Address = address;
        resetPublishState();
    }

    public static void InitializeBuffer(int capacity, HashMap<String, Buffer> Buffer, String prefix){
        for(int i = 0; i < capacity; i++){
            Buffer.put(prefix + (i + 1), new Buffer() );
        }
    }


    public void fillFromCDB(String tag, int value){
        if(this.Q == null) return;
        if(this.Q.equals(tag)){
            this.V = value;
            this.Q = null;
        }
    }

    @Override
    public void publish(String tag){
        if(Busy==1 && Executing_Time==0 && !isQueuedForPublish()){
            Main.toBePublished.add(new Main.pair(this, tag));
            markQueuedForPublish();
        }
    }

    @Override
    public void run() {
        if (Busy != 1) {
            return;
        }
        if (Executing_Time > 0) {
            Executing_Time--;
        }
    }

    @Override
    public void clear() {
        this.Busy = 0;
        this.Address = 0;
        this.V = 0;
        this.Q = null;
        this.Executing_Time = -1;
        resetPublishState();
    }

    
}
