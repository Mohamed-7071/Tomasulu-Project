import java.util.HashMap;


public class Main {
    
public static int[] memory = new int[2048];
static HashMap<String, Register> registerMap = new HashMap<>();
static HashMap<String, Reservation_Station> Add_Stations = new HashMap<>();
static HashMap<String, Reservation_Station> Mul_Stations = new HashMap<>();
static HashMap<String, Reservation_Station> Integer_Stations = new HashMap<>();
static HashMap<String, Buffer> Load_Buffer = new HashMap<>();
static HashMap<String, Buffer> Store_Buffer = new HashMap<>();

int Acapacity = 3;
int Mcapacity = 3;
int Icapacity = 3;
int Lcapacity = 3;
int Scapacity = 3;



public static void main(String[] args){
initialize();






}

public static void initialize(){
        Register.InitializeRegisters();
    Reservation_Station.InitializeReservationStations(Acapacity, Add_Stations, "A");
    Reservation_Station.InitializeReservationStations(Mcapacity, Add_Stations, "M");
    Reservation_Station.InitializeReservationStations(Icapacity, Add_Stations, "I");
    Buffer.InitializeBuffer(Lcapacity, Load_Buffer,"I");
    Buffer.InitializeBuffer(Scapacity, Store_Buffer,"S");
}

}


//  registerMap.get("F1").getValue();