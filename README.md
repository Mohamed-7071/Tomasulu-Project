Tomasulo Algorithm GUI Simulator

Project Overview
This project is a GUI-based simulator for the Tomasulo algorithm developed for the CSEN 702: Microprocessors course at the German University in Cairo. It is built using Java and JavaFX and provides cycle-by-cycle visualization of MIPS instruction execution using dynamic scheduling.

Key Features
- Supports floating-point operations (ADD, SUB, MUL, DIV), integer operations (ADDI, SUBI), loads (LW, LD, L.S, L.D), stores, and branches (BEQ, BNE).
- Configurable instruction latencies, reservation station sizes, and cache parameters (hit latency, miss penalty, block size, cache size).
- Handles RAW, WAR, and WAW hazards, address conflicts, and common data bus contention.
- Implements byte-addressable memory with a data cache.
- JavaFX GUI displaying reservation stations, buffers, register files, instruction queue, cache, and memory.

Technical Details
- Input: MIPS assembly via text file or GUI input.
- Architecture: All instructions enter the Tomasulo architecture; integer instructions use dedicated reservation stations.
- Branching: BEQ and BNE supported without branch prediction.
- Memory: Word loads check cache hit/miss; on a miss, the full block is fetched from memory.

Requirements
- Java
- JavaFX
- IDE with JavaFX support (IntelliJ, Eclipse, or NetBeans)

Running the Simulator
1. Run the application to launch the JavaFX GUI.
2. Configure latencies, station sizes, and cache settings.
3. Load MIPS code from a .txt file or create instructions using the GUI.
4. Step through cycles to observe execution.
