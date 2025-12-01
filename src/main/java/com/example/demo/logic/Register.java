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
         Register f1 = new Register();
        Main.registerMap.put("F1", f1);
        
        Register f2 = new Register();
        f2.setValue(10);
        Main.registerMap.put("F2", f2);
        
        Register f3 = new Register();
        f3.setValue(4);
        Main.registerMap.put("F3", f3);
        
        Register f4 = new Register();
        f4.setValue(8);
        Main.registerMap.put("F4", f4);
        
        Register f5 = new Register();
        f5.setValue(3);
        Main.registerMap.put("F5", f5);
        
        Register f6 = new Register();
        f6.setValue(1);
        Main.registerMap.put("F6", f6);
        
        Register f7 = new Register();
        f7.setValue(11);
        Main.registerMap.put("F7", f7);
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
