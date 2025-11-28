 import java.util.HashMap;

public class Buffer extends Buffer_Station {
    int Address;
    int V;
    String Q;
    int Executing_Time;
    

    public void issue(int address, int v, String q){
        this.Busy = 1;
        this.V = v;
        this.Q = q;
    }

    public static void InitializeBuffer(int capacity, HashMap<String, Buffer> Buffer, String prefix){
        for(int i = 0; i < capacity; i++){
            Buffer.put(prefix + i+1, new Buffer() );
        }
    }


    public void fillFromCDB(String tag, int value){
        if(this.Q == null) return;
        if(this.Q == tag){
            this.V = value;
            this.Q = null;
        }
    }

    @Override
    public void publish(String tag){
        if(Busy==1 && Executing_Time==0){
            Main.pair p = new Main().new pair(this, tag);
            if(!Main.toBePublished.contains(p)){
                Main.toBePublished.add(p);
            }
            Main.toBePublished.remove();
        }
    }

    @Override
    public void run() {
        // Buffer run logic
    }

    @Override
    public void clear() {
        this.Busy = 0;
        this.Address = 0;
        this.V = 0;
        this.Q = null;
    }

    
}
