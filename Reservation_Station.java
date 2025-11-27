 import java.util.HashMap;

public class Reservation_Station {
    String op;
    int vj;
    int vk;
    String Qj;
    String Qk;
    int Busy;

    
    public Reservation_Station(){
    }

    public void issue(String op, int vj, int vk, String Qj, String Qk){
        this.Busy = 1;
        this.op = op;
        this.vj = vj;
        this.vk = vk;
        this.Qj = Qj;
        this.Qk = Qk;
    }

    public static void InitializeReservationStation(int capacity, HashMap<String, Reservation_Station> RS, String prefix){
        for(int i = 0; i < capacity; i++){
            RS.put(prefix + i+1, new Reservation_Station() );
        }
    }



    
}
