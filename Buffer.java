 import java.util.HashMap;

public class Buffer {
    int Busy;
    int Address;
    int V;
    int Q;
    

    public void issue(int address, int v, int q){
        this.Busy = 1;
        this.V = v;
        this.Q = q;
    }

    public static void InitializeBuffer(int capacity, HashMap<String, Buffer> Buffer, String prefix){
        for(int i = 0; i < capacity; i++){
            Buffer.put(prefix + i+1, new Buffer() );
        }
    }

    public void publish(String tag) {
        if(Busy == 1) {
            Main.toBePublished.add(tag);
        }
    }

    public void run() {
        // Buffer run logic
    }

    
}
