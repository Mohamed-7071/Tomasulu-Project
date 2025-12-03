package com.example.demo.logic;

public class Parse {
    public static boolean  parse(String instruction){
        String[] token = instruction.split(" ");
        if(token[0].endsWith(":")){
          token[0]=token[1];
          token[1]=token[2];
          token[2]=token[3];
          token[3]=token[4];
        }
        switch (token[0]) {
            case "DADDI":
            return DADDI(token[1], token[2], token[3]);
            //DADDI R1, R1, 24
            case "DSUBI":
            return DSUBI(token[1], token[2], token[3]);
            //DSUBI R1, R1, 24
            case "ADD.D":
            return ADD_D(token[1], token[2], token[3]);
            //ADD.D F1, F2, F3
            case "ADD.S":
            return ADD_S(token[1], token[2], token[3]);
            //ADD.S F1, F2, F3
            case "SUB.D":
            return SUB_D(token[1], token[2], token[3]);
            //SUB.D F1, F2, F3
            case "SUB.S":
            return SUB_S(token[1], token[2], token[3]);
            //SUB.S F1, F2, F3
            case "MUL.D":
            return MUL_D(token[1], token[2], token[3]);
            //MUL.D F1, F2, F3
            case "MUL.S":
            return MUL_S(token[1], token[2], token[3]);
            //MUL.S F1, F2, F3
            case "DIV.D":
            return DIV_D(token[1], token[2], token[3]);
            //DIV.D F1, F2, F3
            case "DIV.S":
            return DIV_S(token[1], token[2], token[3]);
            //DIV.S F1, F2, F3
            // case "LW":
            // return LW(token[1], token[2], token[3]);
            // //LW R1, 0(R2)
            // case "LD":
            // return LD(token[1], token[2], token[3]);
            // //LD R1, 0(R2)
            // case "L.S":
            // return L_S(token[1], token[2], token[3]);
            // //L.S F1, 0(R2)
            // case "L.D":
            // return L_D(token[1], token[2], token[3]);
            // //L.D F1, 0(R2)
            // case "SW":
            // return SW(token[1], token[2], token[3]);
            // //SW R1, 0(R2)
            // case "SD":
            // return SD(token[1], token[2], token[3]);
            // //SD R1, 0(R2)
            // case "S.S":
            // return S_S(token[1], token[2], token[3]);
            // //S.S F1, 0(R2)
            // case "S.D":
            // return S_D(token[1], token[2], token[3]);
            // //S.D F1, 0(R2)
            case "BNE":
            return BNE(token[1], token[2], token[3]);
            //BNE R1, R2, label
            case "BEQ":
            return BEQ(token[1], token[2], token[3]);
            //BEQ R1, R2, label
            default:
            return false;
        }
    }
// static int integer_reservation_station_number=1;
// static int Add_reservation_station_number=1;
// static int Mul_reservation_station_number=1;
    private static boolean DADDI (String Source,String Operand1,String Operand2){
        float vj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float vk = Float.parseFloat(Operand2);
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = null;
        for(int i=1;i<=Main.Icapacity;i++){
             if(!Main.Integer_Stations.get("I"+i).IsBusy()){
                String tag = "I" + i;
                Main.Integer_Stations.get(tag).issue("DADDI", vj, vk, Qj, Qk);
                Main.Integer_Stations.get(tag).stationTag = tag;
                Main.registerMap.get(Source).setQi(tag);
                Main.recordIssue(tag, Main.curInstruction);
                return true; 
             }
        }
        return false;
    }

    private static boolean DSUBI (String Source,String Operand1,String Operand2){
        float vj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float vk = Float.parseFloat(Operand2);
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = null;
        for(int i=1;i<=Main.Icapacity;i++){
             if(!Main.Integer_Stations.get("I"+i).IsBusy()){
                String tag = "I" + i;
                Main.Integer_Stations.get(tag).issue("DSUBI", vj, vk, Qj, Qk);
                Main.Integer_Stations.get(tag).stationTag = tag;
                Main.registerMap.get(Source).setQi(tag);
                Main.recordIssue(tag, Main.curInstruction);
                return true; 
             }
        }
        return false;
    }

    private static boolean ADD_D (String Source,String Operand1,String Operand2){
        float vj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float vk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Acapacity;i++){
             if(!Main.Add_Stations.get("A"+i).IsBusy()){
                String tag = "A" + i;
                Main.Add_Stations.get(tag).issue("ADD_D", vj, vk, Qj, Qk);
                Main.Add_Stations.get(tag).stationTag = tag;
                Main.registerMap.get(Source).setQi(tag);
                Main.recordIssue(tag, Main.curInstruction);
                return true; 
             }
        }
        return false;
    }

    private static boolean ADD_S (String Source,String Operand1,String Operand2){
        float vj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float vk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Acapacity;i++){
             if(!Main.Add_Stations.get("A"+i).IsBusy()){
                String tag = "A" + i;
                Main.Add_Stations.get(tag).issue("ADD_S", vj, vk, Qj, Qk);
                Main.Add_Stations.get(tag).stationTag = tag;
                Main.registerMap.get(Source).setQi(tag);
                Main.recordIssue(tag, Main.curInstruction);
                return true; 
             }
        }
        return false;
    }

    private static boolean SUB_D (String Source,String Operand1,String Operand2){
        float vj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float vk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Acapacity;i++){
             if(!Main.Add_Stations.get("A"+i).IsBusy()){
                String tag = "A" + i;
                Main.Add_Stations.get(tag).issue("SUB_D", vj, vk, Qj, Qk);
                Main.Add_Stations.get(tag).stationTag = tag;
                Main.registerMap.get(Source).setQi(tag);
                Main.recordIssue(tag, Main.curInstruction);
                return true; 
             }
        }
        return false;
    }

    private static boolean SUB_S (String Source,String Operand1,String Operand2){
        float vj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float vk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Acapacity;i++){
             if(!Main.Add_Stations.get("A"+i).IsBusy()){
                String tag = "A" + i;
                Main.Add_Stations.get(tag).issue("SUB_S", vj, vk, Qj, Qk);
                Main.Add_Stations.get(tag).stationTag = tag;
                Main.registerMap.get(Source).setQi(tag);
                Main.recordIssue(tag, Main.curInstruction);
                return true; 
             }
        }
        return false;
    }

    private static boolean MUL_D (String Source,String Operand1,String Operand2){
        float vj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float vk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Mcapacity;i++){
             if(!Main.Mul_Stations.get("M"+i).IsBusy()){
                String tag = "M" + i;
                Main.Mul_Stations.get(tag).issue("MUL_D", vj, vk, Qj, Qk);
                Main.Mul_Stations.get(tag).stationTag = tag;
                Main.registerMap.get(Source).setQi(tag);
                Main.recordIssue(tag, Main.curInstruction);
                return true; 
             }
        }
        return false;
    }

    private static boolean MUL_S (String Source,String Operand1,String Operand2){
        float vj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float vk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Mcapacity;i++){
             if(!Main.Mul_Stations.get("M"+i).IsBusy()){
                String tag = "M" + i;
                Main.Mul_Stations.get(tag).issue("MUL_S", vj, vk, Qj, Qk);
                Main.Mul_Stations.get(tag).stationTag = tag;
                Main.registerMap.get(Source).setQi(tag);
                Main.recordIssue(tag, Main.curInstruction);
                return true; 
             }
        }
        return false;
    }

    private static boolean DIV_D (String Source,String Operand1,String Operand2){
        float vj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float vk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Mcapacity;i++){
             if(!Main.Mul_Stations.get("M"+i).IsBusy()){
                String tag = "M" + i;
                Main.Mul_Stations.get(tag).issue("DIV_D", vj, vk, Qj, Qk);
                Main.Mul_Stations.get(tag).stationTag = tag;
                Main.registerMap.get(Source).setQi(tag);
                Main.recordIssue(tag, Main.curInstruction);
                return true; 
             }
        }
        return false;
    }

    private static boolean DIV_S (String Source,String Operand1,String Operand2){
        float vj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float vk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Float) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Mcapacity;i++){
             if(!Main.Mul_Stations.get("M"+i).IsBusy()){
                String tag = "M" + i;
                Main.Mul_Stations.get(tag).issue("DIV_S", vj, vk, Qj, Qk);
                Main.Mul_Stations.get(tag).stationTag = tag;
                Main.registerMap.get(Source).setQi(tag);
                Main.recordIssue(tag, Main.curInstruction);
                return true; 
             }
        }
        return false;
    }



    // Load/Store instructions - commented out for now
    // private static void LW (String Source,String Operand1,String Operand2){
    // }

    // private static void LD (String Source,String Operand1,String Operand2){
    //     // LD R1, 0(R2) - offset(base)
    //     int offset = Integer.parseInt(Operand1);
    //     String baseReg = Operand2.substring(1, Operand2.length() - 1); // Remove parentheses
    //     int address = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         offset + (Integer)Main.registerMap.get(baseReg).getValue() : 0;
    //     int v = 0;
    //     int q = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         0 : Integer.parseInt(((String)Main.registerMap.get(baseReg).getValue()).substring(1));
    //     Main.Load_Buffer.get("L1").issue(address, v, q);
    // }

    // private static void L_S (String Source,String Operand1,String Operand2){
    //     // L.S F1, 0(R2) - offset(base)
    //     int offset = Integer.parseInt(Operand1);
    //     String baseReg = Operand2.substring(1, Operand2.length() - 1); // Remove parentheses
    //     int address = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         offset + (Integer)Main.registerMap.get(baseReg).getValue() : 0;
    //     int v = 0;
    //     int q = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         0 : Integer.parseInt(((String)Main.registerMap.get(baseReg).getValue()).substring(1));
    //     Main.Load_Buffer.get("L1").issue(address, v, q);
    // }

    // private static void L_D (String Source,String Operand1,String Operand2){
    //     // L.D F1, 0(R2) - offset(base)
    //     int offset = Integer.parseInt(Operand1);
    //     String baseReg = Operand2.substring(1, Operand2.length() - 1); // Remove parentheses
    //     int address = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         offset + (Integer)Main.registerMap.get(baseReg).getValue() : 0;
    //     int v = 0;
    //     int q = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         0 : Integer.parseInt(((String)Main.registerMap.get(baseReg).getValue()).substring(1));
    //     Main.Load_Buffer.get("L1").issue(address, v, q);
    // }

    // private static void SW (String Source,String Operand1,String Operand2){
    //     // SW R1, 0(R2) - offset(base)
    //     int offset = Integer.parseInt(Operand1);
    //     String baseReg = Operand2.substring(1, Operand2.length() - 1); // Remove parentheses
    //     int address = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         offset + (Integer)Main.registerMap.get(baseReg).getValue() : 0;
    //     int v = (Main.registerMap.get(Source).getValue() instanceof Integer) ? 
    //         (Integer)Main.registerMap.get(Source).getValue() : 0;
    //     int q = (Main.registerMap.get(Source).getValue() instanceof Integer) ? 
    //         0 : Integer.parseInt(((String)Main.registerMap.get(Source).getValue()).substring(1));
    //     Main.Store_Buffer.get("S1").issue(address, v, q);
    // }

    // private static void SD (String Source,String Operand1,String Operand2){
    //     // SD R1, 0(R2) - offset(base)
    //     int offset = Integer.parseInt(Operand1);
    //     String baseReg = Operand2.substring(1, Operand2.length() - 1); // Remove parentheses
    //     int address = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         offset + (Integer)Main.registerMap.get(baseReg).getValue() : 0;
    //     int v = (Main.registerMap.get(Source).getValue() instanceof Integer) ? 
    //         (Integer)Main.registerMap.get(Source).getValue() : 0;
    //     int q = (Main.registerMap.get(Source).getValue() instanceof Integer) ? 
    //         0 : Integer.parseInt(((String)Main.registerMap.get(Source).getValue()).substring(1));
    //     Main.Store_Buffer.get("S1").issue(address, v, q);
    // }

    // private static void S_S (String Source,String Operand1,String Operand2){
    //     // S.S F1, 0(R2) - offset(base)
    //     int offset = Integer.parseInt(Operand1);
    //     String baseReg = Operand2.substring(1, Operand2.length() - 1); // Remove parentheses
    //     int address = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         offset + (Integer)Main.registerMap.get(baseReg).getValue() : 0;
    //     int v = (Main.registerMap.get(Source).getValue() instanceof Integer) ? 
    //         (Integer)Main.registerMap.get(Source).getValue() : 0;
    //     int q = (Main.registerMap.get(Source).getValue() instanceof Integer) ? 
    //         0 : Integer.parseInt(((String)Main.registerMap.get(Source).getValue()).substring(1));
    //     Main.Store_Buffer.get("S1").issue(address, v, q);
    // }

    // private static void S_D (String Source,String Operand1,String Operand2){
    //     // S.D F1, 0(R2) - offset(base)
    //     int offset = Integer.parseInt(Operand1);
    //     String baseReg = Operand2.substring(1, Operand2.length() - 1); // Remove parentheses
    //     int address = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         offset + (Integer)Main.registerMap.get(baseReg).getValue() : 0;
    //     int v = (Main.registerMap.get(Source).getValue() instanceof Integer) ? 
    //         (Integer)Main.registerMap.get(Source).getValue() : 0;
    //     int q = (Main.registerMap.get(Source).getValue() instanceof Integer) ? 
    //         0 : Integer.parseInt(((String)Main.registerMap.get(Source).getValue()).substring(1));
    //     Main.Store_Buffer.get("S1").issue(address, v, q);
    // }

    // BNE R1, R2, label - Branch if not equal
    private static boolean BNE(String Operand1, String Operand2, String label) {
        float v1 = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float v2 = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand2).getValue() : 0;
        String Q1 = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? null : (String)Main.registerMap.get(Operand1).getValue();
        String Q2 = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? null : (String)Main.registerMap.get(Operand2).getValue();
        
        // If either operand is waiting on a result, we can't branch yet
        if (Q1 != null || Q2 != null) {
            return false;
        }
        
        // Branch if not equal
        if (v1 != v2) {
            // Find the label in the instruction array and set curInstruction
            for (int i = 0; i < Main.instructions.length; i++) {
                if (Main.instructions[i].trim().startsWith(label + ":")) {
                    // Set to i-1 because Main.executeCycle will increment curInstruction after parse returns true
                    Main.curInstruction = i - 1;
                    return true;
                }
            }
        }
        return true; // Continue to next instruction if equal
    }

    // BEQ R1, R2, label - Branch if equal
    private static boolean BEQ(String Operand1, String Operand2, String label) {
        float v1 = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand1).getValue() : 0;
        float v2 = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? (Float)Main.registerMap.get(Operand2).getValue() : 0;
        String Q1 = (Main.registerMap.get(Operand1).getValue() instanceof Float) ? null : (String)Main.registerMap.get(Operand1).getValue();
        String Q2 = (Main.registerMap.get(Operand2).getValue() instanceof Float) ? null : (String)Main.registerMap.get(Operand2).getValue();
        
        // If either operand is waiting on a result, we can't branch yet
        if (Q1 != null || Q2 != null) {
            return false;
        }
        
        // Branch if equal
        if (v1 == v2) {
            // Find the label in the instruction array and set curInstruction
            for (int i = 0; i < Main.instructions.length; i++) {
                if (Main.instructions[i].trim().startsWith(label + ":")) {
                    // Set to i-1 because Main.executeCycle will increment curInstruction after parse returns true
                    Main.curInstruction = i - 1;
                    return true;
                }
            }
        }
        return true; // Continue to next instruction if not equal
    }
}