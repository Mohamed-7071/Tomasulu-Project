public class Parse {
    public static boolean  parse(String instruction){
        String[] token = instruction.split(" ");
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
            case "LW":
            return LW(token[1], token[2], token[3]);
            //LW R1, 0(R2)
            case "LD":
            return LD(token[1], token[2], token[3]);
            //LD R1, 0(R2)
            case "L.S":
            return L_S(token[1], token[2], token[3]);
            //L.S F1, 0(R2)
            case "L.D":
            return L_D(token[1], token[2], token[3]);
            //L.D F1, 0(R2)
            case "SW":
            return SW(token[1], token[2], token[3]);
            //SW R1, 0(R2)
            case "SD":
            return SD(token[1], token[2], token[3]);
            //SD R1, 0(R2)
            case "S.S":
            return S_S(token[1], token[2], token[3]);
            //S.S F1, 0(R2)
            case "S.D":
            return S_D(token[1], token[2], token[3]);
            //S.D F1, 0(R2)
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
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = Integer.parseInt(Operand2);
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = null;
        for(int i=1;i<=Main.Icapacity;i++){
             if(!Main.Integer_Stations.get("I"+i).IsBusy()){
                Main.Integer_Stations.get("I" + i).issue("DADDI", vj, vk, Qj, Qk);
                return true; 
             }
        }
        return false;
    }

    private static boolean DSUBI (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = Integer.parseInt(Operand2);
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = null;
        for(int i=1;i<=Main.Icapacity;i++){
             if(!Main.Integer_Stations.get("I"+i).IsBusy()){
                Main.Integer_Stations.get("I" + i).issue("DSUBI", vj, vk, Qj, Qk);
                return true; 
             }
        }
        return false;
    }

    private static boolean ADD_D (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Acapacity;i++){
             if(!Main.Add_Stations.get("A"+i).IsBusy()){
                Main.Add_Stations.get("A" + i).issue("ADD_D", vj, vk, Qj, Qk);
                return true; 
             }
        }
        return false;
    }

    private static boolean ADD_S (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Acapacity;i++){
             if(!Main.Add_Stations.get("A"+i).IsBusy()){
                Main.Add_Stations.get("A" + i).issue("ADD_S", vj, vk, Qj, Qk);
                return true; 
             }
        }
        return false;
    }

    private static boolean SUB_D (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Acapacity;i++){
             if(!Main.Add_Stations.get("A"+i).IsBusy()){
                Main.Add_Stations.get("A" + i).issue("SUB_D", vj, vk, Qj, Qk);
                return true; 
             }
        }
        return false;
    }

    private static boolean SUB_S (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Acapacity;i++){
             if(!Main.Add_Stations.get("A"+i).IsBusy()){
                Main.Add_Stations.get("A" + i).issue("SUB_S", vj, vk, Qj, Qk);
                return true; 
             }
        }
        return false;
    }

    private static boolean MUL_D (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Mcapacity;i++){
             if(!Main.Mul_Stations.get("M"+i).IsBusy()){
                Main.Mul_Stations.get("M" + i).issue("MUL_D", vj, vk, Qj, Qk);
                return true; 
             }
        }
        return false;
    }

    private static boolean MUL_S (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Mcapacity;i++){
             if(!Main.Mul_Stations.get("M"+i).IsBusy()){
                Main.Mul_Stations.get("M" + i).issue("MUL_S", vj, vk, Qj, Qk);
                return true; 
             }
        }
        return false;
    }

    private static boolean DIV_D (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Mcapacity;i++){
             if(!Main.Mul_Stations.get("M"+i).IsBusy()){
                Main.Mul_Stations.get("M" + i).issue("DIV_D", vj, vk, Qj, Qk);
                return true; 
             }
        }
        return false;
    }

    private static boolean DIV_S (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        for(int i=1;i<=Main.Mcapacity;i++){
             if(!Main.Mul_Stations.get("M"+i).IsBusy()){
                Main.Mul_Stations.get("M" + i).issue("DIV_S", vj, vk, Qj, Qk);
                return true; 
             }
        }
        return false;
    }



    
    private static void LW (String Source,String Operand1,String Operand2){
       
    }

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

    // private static void BNE (String Operand1,String Operand2,String label){
    //     // BNE R1, R2, label - Branch if not equal
    //     // This would need additional logic for branch handling
    //     // Placeholder implementation
    // }

    // private static void BEQ (String Operand1,String Operand2,String label){
    //     // BEQ R1, R2, label - Branch if equal
    //     // This would need additional logic for branch handling
    //     // Placeholder implementation
    // }
}