import java.util.HashMap;


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



static String[] instructions;
static int Acapacity = 3;
static int Mcapacity = 3;
static int Icapacity = 3;
static int Lcapacity = 3;
static int Scapacity = 3;

//i need these defined so i can use them in parse, atleast temporarily

public static void main(String[] args){
initialize();
while(running){
    cycle++;
    
    if(Parse.parse(instructions[curInstruction])){
        curInstruction++;
        }
    Publish
    run
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

}


//  registerMap.get("F1").getValue();