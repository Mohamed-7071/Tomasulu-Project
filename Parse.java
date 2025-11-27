public class Parse {
    public static void Parse(String instruction){
        String[] token = instruction.split(" ");
        switch (token[0]) {
            case "DADDI":
                DADDI(token[1], token[2], token[3]);
                //DADDI R1, R1, 24
                break;
            case "DSUBI":
                DSUBI(token[1], token[2], token[3]);
                //DSUBI R1, R1, 24
                break;
            case "ADD.D":
                ADD_D(token[1], token[2], token[3]);
                //ADD.D F1, F2, F3
                break;
            case "ADD.S":
                ADD_S(token[1], token[2], token[3]);
                //ADD.S F1, F2, F3
                break;
            case "SUB.D":
                SUB_D(token[1], token[2], token[3]);
                //SUB.D F1, F2, F3
                break;
            case "SUB.S":
                SUB_S(token[1], token[2], token[3]);
                //SUB.S F1, F2, F3
                break;
            case "MUL.D":
                MUL_D(token[1], token[2], token[3]);
                //MUL.D F1, F2, F3
                break;
            case "MUL.S":
                MUL_S(token[1], token[2], token[3]);
                //MUL.S F1, F2, F3
                break;
            case "DIV.D":
                DIV_D(token[1], token[2], token[3]);
                //DIV.D F1, F2, F3
                break;
            case "DIV.S":
                DIV_S(token[1], token[2], token[3]);
                //DIV.S F1, F2, F3
                break;
            case "LW":
                LW(token[1], token[2], token[3]);
                //LW R1, 0(R2)
                break;
            case "LD":
                LD(token[1], token[2], token[3]);
                //LD R1, 0(R2)
                break;
            case "L.S":
                L_S(token[1], token[2], token[3]);
                //L.S F1, 0(R2)
                break;
            case "L.D":
                L_D(token[1], token[2], token[3]);
                //L.D F1, 0(R2)
                break;
            case "SW":
                SW(token[1], token[2], token[3]);
                //SW R1, 0(R2)
                break;
            case "SD":
                SD(token[1], token[2], token[3]);
                //SD R1, 0(R2)
                break;
            case "S.S":
                S_S(token[1], token[2], token[3]);
                //S.S F1, 0(R2)
                break;
            case "S.D":
                S_D(token[1], token[2], token[3]);
                //S.D F1, 0(R2)
                break;
            case "BNE":
                BNE(token[1], token[2], token[3]);
                //BNE R1, R2, label
                break;
            case "BEQ":
                BEQ(token[1], token[2], token[3]);
                //BEQ R1, R2, label
                break;
        }
    }

    private static void DADDI (String Source,String Operand1,String Operand2){
    int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
    int vk = Integer.parseInt(Operand2);
    String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
    String Qk = null;
    Main.Integer_Stations.get("I1").issue("DADDI", vj, vk, Qj, Qk); 


    }

    private static void ADD_D (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        Main.Add_Stations.get("A1").issue("ADD_D", vj, vk, Qj, Qk); 
    }

    private static void DSUBI (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = Integer.parseInt(Operand2);
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = null;
        Main.Integer_Stations.get("I1").issue("DSUBI", vj, vk, Qj, Qk); 
    }

    private static void ADD_S (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        Main.Add_Stations.get("A1").issue("ADD_S", vj, vk, Qj, Qk); 
    }

    private static void SUB_D (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        Main.Add_Stations.get("A1").issue("SUB_D", vj, vk, Qj, Qk); 
    }

    private static void SUB_S (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        Main.Add_Stations.get("A1").issue("SUB_S", vj, vk, Qj, Qk); 
    }

    private static void MUL_D (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        Main.Mul_Stations.get("M1").issue("MUL_D", vj, vk, Qj, Qk); 
    }

    private static void MUL_S (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        Main.Mul_Stations.get("M1").issue("MUL_S", vj, vk, Qj, Qk); 
    }

    private static void DIV_D (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        Main.Mul_Stations.get("M1").issue("DIV_D", vj, vk, Qj, Qk); 
    }

    private static void DIV_S (String Source,String Operand1,String Operand2){
        int vj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand1).getValue() : 0;
        int vk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ? (Integer)Main.registerMap.get(Operand2).getValue() : 0;
        String Qj = (Main.registerMap.get(Operand1).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand1).getValue();
        String Qk = (Main.registerMap.get(Operand2).getValue() instanceof Integer) ?  null : (String)Main.registerMap.get(Operand2).getValue();
        Main.Mul_Stations.get("M1").issue("DIV_S", vj, vk, Qj, Qk); 
    }

    // private static void LW (String Source,String Operand1,String Operand2){
    //     // LW R1, 0(R2) - offset(base)
    //     int offset = Integer.parseInt(Operand1);
    //     String baseReg = Operand2.substring(1, Operand2.length() - 1); // Remove parentheses
    //     int address = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         offset + (Integer)Main.registerMap.get(baseReg).getValue() : 0;
    //     int v = 0;
    //     int q = (Main.registerMap.get(baseReg).getValue() instanceof Integer) ? 
    //         0 : Integer.parseInt(((String)Main.registerMap.get(baseReg).getValue()).substring(1));
    //     Main.Load_Buffer.get("L1").issue(address, v, q);
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