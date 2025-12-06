package com.example.demo.logic;

import java.util.HashMap;

public class Buffer extends Buffer_Station {
    public int Address;
    public float V;
    public String Q;
    public int Executing_Time = -1;
    public String op;
    public int bytesRequested = 4; // default word
    public int offset = 0; // for effective address calc when base pending
    public float storeValue = 0; // value to store (for stores)
    public String storeQ = null; // pending tag for store value
    private boolean storeCommitted = false; // ensure store writes once at completion
    private boolean storePendingWriteback = false; // commit store one cycle later at publish
    

    public void issue(String op, int address, float v, String q, int bytesRequested){
        this.Busy = 1;
        this.op = op;
        this.V = v; // holds base value if Q != null
        this.Q = q;
        this.Address = address;
        this.bytesRequested = bytesRequested;
        this.Executing_Time = -1;
        this.issuedCycle = Main.cycle;
        this.storeValue = 0;
        this.storeQ = null;
        this.storeCommitted = false;
        this.storePendingWriteback = false;
        resetPublishState();
    }

    public void issueStore(String op, int address, float baseVal, String Qbase, float srcVal, String Qsrc, int bytes, int offset){
        this.Busy = 1;
        this.op = op;
        this.V = baseVal;
        this.Q = Qbase;
        this.Address = address;
        this.bytesRequested = bytes;
        this.Executing_Time = -1;
        this.issuedCycle = Main.cycle;
        this.storeValue = srcVal;
        this.storeQ = Qsrc;
        this.offset = offset;
        this.storeCommitted = false;
        this.storePendingWriteback = false;
        resetPublishState();
    }

    public static void InitializeBuffer(int capacity, HashMap<String, Buffer> Buffer, String prefix){
        for(int i = 0; i < capacity; i++){
            Buffer.put(prefix + (i + 1), new Buffer() );
        }
    }


    public void fillFromCDB(String tag, float value){
        if(this.Q != null && this.Q.equals(tag)){
            this.V = value;
            this.Q = null;
            // Recompute effective address if pending
            this.Address = (int) this.V + this.offset;
        }
        if(this.storeQ != null && this.storeQ.equals(tag)){
            this.storeValue = value;
            this.storeQ = null;
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

        boolean isStore = op != null && (op.equals("SW") || op.equals("S.S") || op.equals("S.D"));
        
        if (Q != null) {
            return; // wait for base value
        }
        
        if (isStore && storeQ != null) {
            return; // wait for store value
        }

        if (this.issuedCycle >= 0 && Main.cycle <= this.issuedCycle) {
            return; // do not start in issue cycle
        }

        int blockSize = Math.max(1, Main.cacheBlockSize);
        int blockNumber = Address / blockSize;
        if (Executing_Time == -1 && Main.isBlockBusy(blockNumber)) {
            return; // serialize same-block accesses
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

        if (Executing_Time == -1) {
            if (isStore) {
                // Store: wait for latency, defer memory write until completion
                this.Executing_Time = Main.Storetime;
                Main.markBlockBusy(blockNumber, Main.Storetime);
            } else {
                // Load: read from cache/memory
                DataCache.AccessResult res = Main.loadWord(Address);
                this.output = Float.intBitsToFloat(res.value);
                this.Executing_Time = res.latency;
                Main.markBlockBusy(blockNumber, res.latency);
            }
        }

        if (Executing_Time > 0) {
            Executing_Time--;
            if (Executing_Time > 0) {
                return;
            }
        }

        if (Executing_Time == 0) {
            if (isStore && !storeCommitted) {
                // Defer actual memory write to publish phase (write stage)
                storePendingWriteback = true;
            }
            // Record execution end
            for (Main.InstructionStatus status : Main.issuedInstructions) {
                if (status.tag != null && status.tag.equals(this.stationTag) && status.execEndCycle == -1) {
                    status.execEndCycle = Main.cycle;
                    break;
                }
            }
        }
    }

    @Override
    public void clear() {
        this.Busy = 0;
        this.Address = 0;
        this.V = 0;
        this.Q = null;
        this.Executing_Time = -1;
        this.op = null;
        this.bytesRequested = 4;
        this.offset = 0;
        this.stationTag = null;
        this.storeValue = 0;
        this.storeQ = null;
        this.storeCommitted = false;
        this.storePendingWriteback = false;
        resetPublishState();
    }

    public boolean isStoreOp() {
        return op != null && (op.equals("SW") || op.equals("S.S") || op.equals("S.D"));
    }

    /**
     * Commit a pending store exactly once during publish (write stage).
     * Returns true if a commit occurred this call.
     */
    public boolean commitIfPending() {
        if (isStoreOp() && storePendingWriteback && !storeCommitted) {
            Main.storeWord(Address, storeValue);
            storeCommitted = true;
            storePendingWriteback = false;
            return true;
        }
        return false;
    }

    
}
