import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;


public class Main {
public static boolean running=true;
public static int cycle = 0;    
public static int curInstruction = 0;
public static int[] memory = new int[2048];
static HashMap<String, Register> registerMap = new HashMap<>();
static HashMap<String, Reservation_Station> Add_Stations = new HashMap<>();
static HashMap<String, Reservation_Station> Mul_Stations = new HashMap<>();
static HashMap<String, Reservation_Station> Integer_Stations = new HashMap<>();
static HashMap<String, Buffer> Load_Buffer = new HashMap<>();
static HashMap<String, Buffer> Store_Buffer = new HashMap<>();
public static int[] cache = new int[30];
public static PriorityQueue<String> toBePublished = new PriorityQueue<>(new Comparator<String>() {
    @Override
    public int compare(String tag1, String tag2) {
        int count1 = countDependencies(tag1);
        int count2 = countDependencies(tag2);
        return Integer.compare(count2, count1); // Higher count = higher priority
    }
});

static int Addtime;
static int Multime;
static int Inttime;
static int Loadtime;
static int Storetime;
static String[] instructions;
static int Acapacity = 3;
static int Mcapacity = 3;
static int Icapacity = 3;
static int Lcapacity = 3;
static int Scapacity = 3;



public static void main(String[] args){
initialize();
while(running){
    cycle++;
    
    if(Parse.parse(instructions[curInstruction])){
        curInstruction++;
        }
    publish();

    run();
    }





}

public static void initialize(){
    Register.InitializeRegisters();
    Reservation_Station.InitializeReservationStation(Acapacity, Add_Stations, "A");
    Reservation_Station.InitializeReservationStation(Mcapacity, Mul_Stations, "M");
    Reservation_Station.InitializeReservationStation(Icapacity, Integer_Stations, "I");
    Buffer.InitializeBuffer(Lcapacity, Load_Buffer,"I");
    Buffer.InitializeBuffer(Scapacity, Store_Buffer,"S");
}
public static void publish(){
for(String key : Add_Stations.keySet()){
    Add_Stations.get(key).publish(key);
}
for(String key : Mul_Stations.keySet()){
    Mul_Stations.get(key).publish(key);
}
for(String key : Integer_Stations.keySet()){
    Integer_Stations.get(key).publish(key);
}
for(String key : Load_Buffer.keySet()){
    Load_Buffer.get(key).publish(key);
}
for(String key : Store_Buffer.keySet()){
    Store_Buffer.get(key).publish(key);
}
for(String key : registerMap.keySet()){
    registerMap.get(key).publish(key);}

    String CDB = toBePublished.remove();
    

     for(Reservation_Station station : Add_Stations.values()) {
        station.fillFromCDB(CDB,);
        }
    }
    
    // Count in Mul_Stations
    for(Reservation_Station station : Mul_Stations.values()) {
        if(tag.equals(station.Qj) || tag.equals(station.Qk)) {
            count++;
        }
    }
    
    // Count in Integer_Stations
    for(Reservation_Station station : Integer_Stations.values()) {
        if(tag.equals(station.Qj) || tag.equals(station.Qk)) {
            count++;
        }
    }
    
    // Count in Load_Buffer (checking Q field if it's a tag)
    for(Buffer buffer : Load_Buffer.values()) {
        if(buffer.Q != 0 && tag.equals("L" + buffer.Q)) {
            count++;
        }
    }
    
    // Count in Store_Buffer (checking Q field if it's a tag)
    for(Buffer buffer : Store_Buffer.values()) {
        if(buffer.Q != 0 && tag.equals("S" + buffer.Q)) {
            count++;
        }
    }
    
    // Count in registers
    for(Register register : registerMap.values()) {
        if(tag.equals(register.Qi)) {
            count++;
        }

}


public static void run(){
for(Reservation_Station station:Add_Stations.values()){
    station.run();
}
for(Reservation_Station station:Mul_Stations.values()){
    station.run();
}
for(Reservation_Station station:Integer_Stations.values()){
        station.run();
}
for(Buffer buffer:Load_Buffer.values()){
    buffer.run();
}
for(Buffer buffer:Store_Buffer.values()){
    buffer.run();
}
    
}

public static int getExecutionTime(String instructionType) {
    switch(instructionType.toUpperCase()) {
        case "ADD_D":
        case "ADD_S":
        case "SUB_D":
        case "SUB_S":
            return Addtime;
        
        case "MUL_D":
        case "MUL_S":
        case "DIV_D":
        case "DIV_S":
            return Multime;
        
        case "L_D":
        case "L_S":
        case "LD":
        case "LW":
            return Loadtime;
        
        case "S_D":
        case "S_S":
        case "SD":
        case "SW":
            return Storetime;
        
        case "DADDI":
        case "DSUBI":
        case "BNE":
        case "BEQ":
            return Inttime;
        
        default:
            return Inttime;
    }
}

public static int countDependencies(String tag) {
    int count = 0;
    
    // Count in Add_Stations
    for(Reservation_Station station : Add_Stations.values()) {
        if(tag.equals(station.Qj) || tag.equals(station.Qk)) {
            count++;
        }
    }
    
    // Count in Mul_Stations
    for(Reservation_Station station : Mul_Stations.values()) {
        if(tag.equals(station.Qj) || tag.equals(station.Qk)) {
            count++;
        }
    }
    
    // Count in Integer_Stations
    for(Reservation_Station station : Integer_Stations.values()) {
        if(tag.equals(station.Qj) || tag.equals(station.Qk)) {
            count++;
        }
    }
    
    // Count in Load_Buffer (checking Q field if it's a tag)
    for(Buffer buffer : Load_Buffer.values()) {
        if(buffer.Q != 0 && tag.equals("L" + buffer.Q)) {
            count++;
        }
    }
    
    // Count in Store_Buffer (checking Q field if it's a tag)
    for(Buffer buffer : Store_Buffer.values()) {
        if(buffer.Q != 0 && tag.equals("S" + buffer.Q)) {
            count++;
        }
    }
    
    // Count in registers
    for(Register register : registerMap.values()) {
        if(tag.equals(register.Qi)) {
            count++;
        }
    }
    
    return count;
}


}


//  registerMap.get("F1").getValue();