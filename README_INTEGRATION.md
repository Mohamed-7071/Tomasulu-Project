# Tomasulo Algorithm Simulator - Integration Complete

## What's Been Set Up

Your Tomasulo algorithm backend has been successfully integrated with the JavaFX frontend!

## File Structure

```
src/main/java/com/example/demo/
├── logic/                          ← Your backend logic
│   ├── Main.java                   ← Core Tomasulo simulator
│   ├── Reservation_Station.java   ← Reservation stations
│   ├── Buffer.java                 ← Load/Store buffers
│   ├── Register.java               ← Register file
│   ├── Parse.java                  ← Instruction parser
│   ├── Buffer_Station.java         ← Base class
│   └── RegBufStat.java             ← Base class
├── HelloApplication.java           ← Main UI with integrated logic
├── HelloController.java            ← Original controller
├── Launcher.java                   ← App launcher
└── TomasuloController.java         ← Alternative controller (not used)

src/main/resources/com/example/demo/
├── hello-view.fxml                 ← Original FXML
└── tomasulo-view.fxml              ← Alternative FXML (not used)
```

## How to Use the Application

1. **Run the Application**
   - Click the "Run Java" button in VS Code, or
   - Run: `.\mvnw.cmd clean javafx:run` in PowerShell

2. **Load a Program**
   - Click "Load Program" button
   - Enter instructions (one per line), for example:
     ```
     ADD.D F1, F2, F3
     MUL.D F4, F1, F5
     SUB.D F6, F7, F8
     ```
   - Click OK

3. **Configure Settings** (optional)
   - Click "Configure" button
   - Set latencies (cycles) for each operation type
   - Set capacities for each reservation station type
   - Click OK

4. **Run the Simulation**
   - Click "Continue" button to execute one cycle at a time
   - Watch the cycle counter update
   - Switch between tabs to see different components:
     - **Instruction Queue**: Shows instruction execution status
     - **Reservation Stations**: Shows Add/Mul/Integer stations
     - **Load/Store Buffers**: Shows buffer contents
     - **Register File**: Shows register values and dependencies
     - **Cache**: Placeholder for cache visualization

5. **Reset**
   - Click "Reset" to start over

## Supported Instructions

Currently implemented:
- `ADD.D`, `ADD.S` - Floating point addition
- `SUB.D`, `SUB.S` - Floating point subtraction
- `MUL.D`, `MUL.S` - Floating point multiplication
- `DIV.D`, `DIV.S` - Floating point division
- `DADDI`, `DSUBI` - Integer arithmetic with immediate

## Key Features

✅ Reservation stations for Add, Multiply, and Integer operations
✅ Load and Store buffers
✅ Register renaming and dependency tracking
✅ Common Data Bus (CDB) for result broadcasting
✅ Cycle-by-cycle execution
✅ Visual display of all components
✅ Configurable latencies and capacities

## Next Steps

To enhance the simulator, you could:
- Complete the Load/Store instruction implementations (currently commented out in Parse.java)
- Add branch instruction support (BNE, BEQ)
- Implement the instruction queue display
- Add instruction timing visualization
- Implement cache visualization
- Add auto-run mode (continuous execution)
- Add save/load program features

## Troubleshooting

If you encounter issues:
1. Make sure JDK 21 or 25 is installed
2. Verify JAVA_HOME is set correctly
3. Try: `.\mvnw.cmd clean compile` to rebuild
4. Check the execution log at the bottom of the window for errors

Enjoy your Tomasulo simulator!
