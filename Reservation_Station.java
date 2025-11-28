 import java.util.HashMap;

public class Reservation_Station {
    String op;
    int vj;
    int vk;
    String Qj;
    String Qk;
    int Busy;
    int output;
    int Executing_Time = -1;

    public boolean IsBusy(){
        if (Busy ==1)
            return true;
        else
            return false;
    }

    public void issue(String op, int vj, int vk, String Qj, String Qk){
        this.Busy = 1;
        this.op = op;
        this.vj = vj;
        this.vk = vk;
        this.Qj = Qj;
        this.Qk = Qk;
        this.Executing_Time = Main.getExecutionTime(op);
    }

    public static void InitializeReservationStation(int capacity,int exTime, HashMap<String, Reservation_Station> RS, String prefix){
        for(int i = 0; i < capacity; i++){
            RS.put(prefix + i+1, new Reservation_Station() );
        }
    }

    public void publish(String tag){
        if(Busy==1 && Executing_Time==0){
            if(!Main.toBePublished.contains(tag)){
            Main.toBePublished.add(tag);
            }

            Main.toBePublished.remove();
        }
            
      
    }

    public void fillFromCDB(String tag, int value){
        if(this.Qj != null && this.Qj.equals(tag)){
            this.vj = value;
            this.Qj = null;
        }
        if(this.Qk != null && this.Qk.equals(tag)){
            this.vk = value;
            this.Qk = null;
        }
    }

    public void clear(){
        this.Busy = 0;
        this.op = null;
        this.vj = 0;
        this.vk = 0;
        this.Qj = null;
        this.Qk = null;
        this.Executing_Time = 0;
    }




    public void run(){
        if(this.Qj!=null || this.Qk != null)          
            return;
        if (this.Executing_Time>0)
            this.Executing_Time--;
       switch(this.op) {
        case "DADDI":
        case "ADD_D":
        case "ADD_S":
            this.output = this.vj+this.vk;
        case "DSUBI":
        case "SUB_D":
        case "SUB_S":
           this.output = this.vj-this.vk;
        case "MUL_D":
        case "MUL_S":
            this.output = this.vj*this.vk;
        case "DIV_D":
        case "DIV_S":
            this.output = this.vj/this.vk;
                }

        
        
    }

    
}
