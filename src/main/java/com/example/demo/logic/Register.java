package com.example.demo.logic;

public class Register extends RegBufStat {

    float value;
    String Qi;


    public Object getValue() {
        if (Qi != null) {
            return (String) Qi;
        }
        else {
            return (float) value;
        }
    }

    public void setValue(float value) {
        this.value = value;
        this.Qi = null;
    }

    public void setQi(String Qi) {
        this.Qi = Qi;
    }

    public void fillFromCDB(String tag, float value) {
        if (this.Qi != null && this.Qi.equals(tag)) {
            this.value = value;
            this.Qi = null;
        }
    }

    public static void InitializeRegisters() {
        Register f0 = new Register();
        f0.setValue(12f);
        Main.registerMap.put("F0", f0);

         Register f1 = new Register();
        Main.registerMap.put("F1", f1);
        
        Register f2 = new Register();
        f2.setValue(2f);
        Main.registerMap.put("F2", f2);
        
        Register f3 = new Register();
        f3.setValue(4f);
        Main.registerMap.put("F3", f3);
        
        Register f4 = new Register();
        f4.setValue(4f);
        Main.registerMap.put("F4", f4);
        
        Register f5 = new Register();
        f5.setValue(3f);
        Main.registerMap.put("F5", f5);
        
        Register f6 = new Register();
        f6.setValue(3.99f);
        Main.registerMap.put("F6", f6);
        
        Register f7 = new Register();
        f7.setValue(11f);
        Main.registerMap.put("F7", f7);

        Main.registerMap.put("F8", new Register());
        Main.registerMap.put("F9", new Register());
        Main.registerMap.put("F10", new Register());
        Main.registerMap.put("F11", new Register());
        Main.registerMap.put("F12", new Register());

        Register r1 = new Register();
        r1.setValue(32f);
        Main.registerMap.put("R1", r1);

        Register r2 = new Register();
        r2.setValue(0f);
        Main.registerMap.put("R2", r2);
        
        Main.registerMap.put("R3", new Register());
        Main.registerMap.put("R4", new Register());
        Main.registerMap.put("R5", new Register());
        Main.registerMap.put("R6", new Register());
    }

}
