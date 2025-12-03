package com.example.demo.logic;

import java.util.HashMap;

public class Reservation_Station extends Buffer_Station {
    public String op;
    public float vj;
    public float vk;
    public String Qj;
    public String Qk;
    public String stationTag;  // Track this station's tag

    public int Executing_Time = -1;
    public void issue(String op, float vj, float vk, String Qj, String Qk){
        this.Busy = 1;
        this.op = op;
        this.vj = vj;
        this.vk = vk;
        this.Qj = Qj;
        this.Qk = Qk;
        this.Executing_Time = Main.getExecutionTime(op);
        resetPublishState();
    }

    public static void InitializeReservationStation(int capacity, HashMap<String, Reservation_Station> RS, String prefix){
        for(int i = 0; i < capacity; i++){
            RS.put(prefix + (i+1), new Reservation_Station() );
        }
    }

    @Override
    public void publish(String tag){
        if(Busy==1 && Executing_Time==0 && !isQueuedForPublish()){
            Main.toBePublished.add(new Main.pair(this, tag));
            markQueuedForPublish();
        }
    }

    public void fillFromCDB(String tag, float value){
        if(this.Qj != null && this.Qj.equals(tag)){
            this.vj = value;
            this.Qj = null;
        }
        if(this.Qk != null && this.Qk.equals(tag)){
            this.vk = value;
            this.Qk = null;
        }
    }

    @Override
    public void clear(){
        this.Busy = 0;
        this.op = null;
        this.vj = 0;
        this.vk = 0;
        this.Qj = null;
        this.Qk = null;
        this.Executing_Time = -1;
        this.stationTag = null;
        resetPublishState();
    }

    @Override
    public void run(){
        if(this.Busy != 1 || this.op == null) {
            return;
        }

        if(this.Qj != null || this.Qk != null) {
            return;
        }

        // Record execution start when we first start executing
        if (!hasExecutionStarted()) {
            markExecutionStarted();
            for (Main.InstructionStatus status : Main.issuedInstructions) {
                if (status.tag != null && status.tag.equals(this.stationTag) && status.execStartCycle == -1) {
                    status.execStartCycle = Main.cycle;
                    break;
                }
            }
        }

        if (this.Executing_Time > 0) {
            this.Executing_Time--;
            if (this.Executing_Time > 0) {
                return;
            }
        }

        if (this.Executing_Time == 0) {
            // Record execution end
            for (Main.InstructionStatus status : Main.issuedInstructions) {
                if (status.tag != null && status.tag.equals(this.stationTag) && status.execEndCycle == -1) {
                    status.execEndCycle = Main.cycle;
                    break;
                }
            }
            
            switch(this.op) {
                case "DADDI":
                case "ADD_D":
                case "ADD_S":
                    this.output = this.vj + this.vk;
                    break;
                case "DSUBI":
                case "SUB_D":
                case "SUB_S":
                    this.output = this.vj - this.vk;
                    break;
                case "MUL_D":
                case "MUL_S":
                    this.output = this.vj * this.vk;
                    break;
                case "DIV_D":
                case "DIV_S":
                    this.output = (this.vk != 0) ? this.vj / this.vk : 0;
                    break;
                default:
                    this.output = 0;
            }
        }
    }

    
}
