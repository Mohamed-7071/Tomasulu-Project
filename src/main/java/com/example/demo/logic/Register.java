package com.example.demo.logic;

public class Register extends RegBufStat {

    int value;
    String Qi;


    public Object getValue() {
        if (Qi != null) {
            return (String) Qi;
        }
        else {
            return (int) value;
        }
    }

    public void setValue(int value) {
        this.value = value;
        this.Qi = null;
    }

    public void setQi(String Qi) {
        this.Qi = Qi;
    }

    public void fillFromCDB(String tag, int value) {
        if (this.Qi != null && this.Qi.equals(tag)) {
            this.value = value;
            this.Qi = null;
        }
    }

    public static void InitializeRegisters() {
        Main.registerMap.put("F1", new Register());
        Main.registerMap.put("F2", new Register());
        Main.registerMap.put("F3", new Register());
        Main.registerMap.put("F4", new Register());
        Main.registerMap.put("F5", new Register());
        Main.registerMap.put("F6", new Register());
        Main.registerMap.put("F7", new Register());
        Main.registerMap.put("F8", new Register());
        Main.registerMap.put("F9", new Register());
        Main.registerMap.put("F10", new Register());
        Main.registerMap.put("F11", new Register());
        Main.registerMap.put("F12", new Register());
        Main.registerMap.put("R1", new Register());
        Main.registerMap.put("R2", new Register());
        Main.registerMap.put("R3", new Register());
        Main.registerMap.put("R4", new Register());
        Main.registerMap.put("R5", new Register());
        Main.registerMap.put("R6", new Register());
    }

}
