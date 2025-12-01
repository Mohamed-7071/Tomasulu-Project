package com.example.demo.logic;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
public class Main {
    public static class pair {
        private final Buffer_Station value;
        private final String tag;

        public pair(Buffer_Station value, String tag) {
            this.value = value;
            this.tag = tag;
        }

        public Buffer_Station getValue() {
            return value;
        }

        public String getTag() {
            return tag;
        }
    }

    public static boolean running = true;
    public static int cycle = 0;    
    public static int curInstruction = 0;
    public static int[] memory = new int[2048];
    public static HashMap<String, Register> registerMap = new HashMap<>();
    public static HashMap<String, Reservation_Station> Add_Stations = new HashMap<>();
    public static HashMap<String, Reservation_Station> Mul_Stations = new HashMap<>();
    public static HashMap<String, Reservation_Station> Integer_Stations = new HashMap<>();
    public static HashMap<String, Buffer> Load_Buffer = new HashMap<>();
    public static HashMap<String, Buffer> Store_Buffer = new HashMap<>();
    public static int[] cache = new int[30];
    public static PriorityQueue<pair> toBePublished = new PriorityQueue<>(new Comparator<pair>() {
        @Override
        public int compare(pair tag1, pair tag2) {
            int count1 = countDependencies(tag1.getTag());
            int count2 = countDependencies(tag2.getTag());
            return Integer.compare(count2, count1); // Higher count = higher priority
        }
    });

    public static int Addtime;
    public static int Multime;
    public static int Inttime;
    public static int Loadtime;
    public static int Storetime;
    public static String[] instructions;
    public static int Acapacity = 3;
    public static int Mcapacity = 3;
    public static int Icapacity = 3;
    public static int Lcapacity = 3;
    public static int Scapacity = 3;

    public static void main(String[] args){
        initialize();
        while(running){
            executeCycle();
        }
    }

    // Execute one cycle of the Tomasulo algorithm
    public static void executeCycle() {
        if (!running || instructions == null) {
            return;
        }

        cycle++;

        if (curInstruction < instructions.length) {
            if (Parse.parse(instructions[curInstruction])){
                curInstruction++;
            }
        }

        publish();
        run();

    }

    public static void initialize(){
        // Clear existing data structures
        registerMap.clear();
        Add_Stations.clear();
        Mul_Stations.clear();
        Integer_Stations.clear();
        Load_Buffer.clear();
        Store_Buffer.clear();
        toBePublished.clear();
        
        // Reset counters
        cycle = 0;
        curInstruction = 0;
        running = true;
        
        // Initialize all components
        Register.InitializeRegisters();
        Reservation_Station.InitializeReservationStation(Acapacity, Add_Stations, "A");
        Reservation_Station.InitializeReservationStation(Mcapacity, Mul_Stations, "M");
        Reservation_Station.InitializeReservationStation(Icapacity, Integer_Stations, "I");
        Buffer.InitializeBuffer(Lcapacity, Load_Buffer, "L");
        Buffer.InitializeBuffer(Scapacity, Store_Buffer, "S");
    }


    public static void publish(){
        // Collect all stations/buffers that are ready to publish
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

        // If there's something ready to publish, broadcast it on CDB
        if (!toBePublished.isEmpty()) {
            pair CDB = toBePublished.remove();
            String tag = CDB.getTag();
            Buffer_Station Bstation = CDB.getValue();
            int output = Bstation.output;

            // Broadcast to all stations and buffers
            for(Reservation_Station station : Add_Stations.values()) {
                station.fillFromCDB(tag, output);
            }
            
            for(Reservation_Station station : Mul_Stations.values()) {
                station.fillFromCDB(tag, output);
            }
            
            for(Reservation_Station station : Integer_Stations.values()) {
                station.fillFromCDB(tag, output);
            }
            
            for(Buffer buffer : Load_Buffer.values()) {
                buffer.fillFromCDB(tag, output);
            }
            
            for(Buffer buffer : Store_Buffer.values()) {
                buffer.fillFromCDB(tag, output);
            }

            for(Register register : registerMap.values()) {
                register.fillFromCDB(tag, output);
            }
            
            // Clear the station/buffer that published
            Bstation.clear();
        }
    }

    public static void run(){
        for(Reservation_Station station : Add_Stations.values()){
            station.run();
        }
        for(Reservation_Station station : Mul_Stations.values()){
            station.run();
        }
        for(Reservation_Station station : Integer_Stations.values()){
            station.run();
        }
        for(Buffer buffer : Load_Buffer.values()){
            buffer.run();
        }
        for(Buffer buffer : Store_Buffer.values()){
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
        if(buffer.Q != null && tag.equals("L" + buffer.Q)) {
            count++;
        }
    }
    
    // Count in Store_Buffer (checking Q field if it's a tag)
    for(Buffer buffer : Store_Buffer.values()) {
        if(buffer.Q != null && tag.equals("S" + buffer.Q)) {
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