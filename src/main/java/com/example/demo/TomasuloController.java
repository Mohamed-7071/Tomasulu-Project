package com.example.demo;

import com.example.demo.logic.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TomasuloController {
    
    @FXML
    private TextArea instructionInput;
    
    @FXML
    private TextField addLatencyField;
    
    @FXML
    private TextField mulLatencyField;
    
    @FXML
    private TextField intLatencyField;
    
    @FXML
    private TextField loadLatencyField;
    
    @FXML
    private TextField storeLatencyField;
    
    @FXML
    private TextField addCapacityField;
    
    @FXML
    private TextField mulCapacityField;
    
    @FXML
    private TextField intCapacityField;
    
    @FXML
    private TextField loadCapacityField;
    
    @FXML
    private TextField storeCapacityField;
    
    @FXML
    private Label cycleLabel;
    
    @FXML
    private TableView<StationDisplay> addStationsTable;
    
    @FXML
    private TableView<StationDisplay> mulStationsTable;
    
    @FXML
    private TableView<StationDisplay> intStationsTable;
    
    @FXML
    private TableView<BufferDisplay> loadBufferTable;
    
    @FXML
    private TableView<BufferDisplay> storeBufferTable;
    
    @FXML
    private TableView<RegisterDisplay> registerTable;
    
    @FXML
    private Button startButton;
    
    @FXML
    private Button stepButton;
    
    @FXML
    private Button resetButton;
    
    private boolean isRunning = false;
    
    @FXML
    public void initialize() {
        // Set default values
        addLatencyField.setText("2");
        mulLatencyField.setText("10");
        intLatencyField.setText("1");
        loadLatencyField.setText("2");
        storeLatencyField.setText("2");
        
        addCapacityField.setText("3");
        mulCapacityField.setText("3");
        intCapacityField.setText("3");
        loadCapacityField.setText("3");
        storeCapacityField.setText("3");
        
        // Sample instructions
        instructionInput.setText("ADD.D F1, F2, F3\nMUL.D F4, F1, F5\nSUB.D F6, F7, F8");
        
        setupTables();
    }
    
    private void setupTables() {
        // Setup Add Stations Table
        TableColumn<StationDisplay, String> addTagCol = new TableColumn<>("Tag");
        addTagCol.setCellValueFactory(data -> data.getValue().tagProperty());
        
        TableColumn<StationDisplay, String> addBusyCol = new TableColumn<>("Busy");
        addBusyCol.setCellValueFactory(data -> data.getValue().busyProperty());
        
        TableColumn<StationDisplay, String> addOpCol = new TableColumn<>("Op");
        addOpCol.setCellValueFactory(data -> data.getValue().opProperty());
        
        TableColumn<StationDisplay, String> addVjCol = new TableColumn<>("Vj");
        addVjCol.setCellValueFactory(data -> data.getValue().vjProperty());
        
        TableColumn<StationDisplay, String> addVkCol = new TableColumn<>("Vk");
        addVkCol.setCellValueFactory(data -> data.getValue().vkProperty());
        
        TableColumn<StationDisplay, String> addQjCol = new TableColumn<>("Qj");
        addQjCol.setCellValueFactory(data -> data.getValue().qjProperty());
        
        TableColumn<StationDisplay, String> addQkCol = new TableColumn<>("Qk");
        addQkCol.setCellValueFactory(data -> data.getValue().qkProperty());
        
        if (addStationsTable != null) {
            addStationsTable.getColumns().addAll(addTagCol, addBusyCol, addOpCol, addVjCol, addVkCol, addQjCol, addQkCol);
        }
        
        // Similar setup for other tables...
    }
    
    @FXML
    protected void onStartClick() {
        if (!isRunning) {
            initializeSimulation();
            isRunning = true;
            startButton.setText("Stop");
            runSimulation();
        } else {
            isRunning = false;
            startButton.setText("Start");
        }
    }
    
    @FXML
    protected void onStepClick() {
        if (!isRunning) {
            if (Main.cycle == 0) {
                initializeSimulation();
            }
            executeOneCycle();
            updateDisplay();
        }
    }
    
    @FXML
    protected void onResetClick() {
        isRunning = false;
        Main.cycle = 0;
        Main.curInstruction = 0;
        Main.running = true;
        startButton.setText("Start");
        cycleLabel.setText("Cycle: 0");
        clearTables();
    }
    
    private void initializeSimulation() {
        // Get latencies from UI
        Main.Addtime = Integer.parseInt(addLatencyField.getText());
        Main.Multime = Integer.parseInt(mulLatencyField.getText());
        Main.Inttime = Integer.parseInt(intLatencyField.getText());
        Main.Loadtime = Integer.parseInt(loadLatencyField.getText());
        Main.Storetime = Integer.parseInt(storeLatencyField.getText());
        
        // Get capacities from UI
        Main.Acapacity = Integer.parseInt(addCapacityField.getText());
        Main.Mcapacity = Integer.parseInt(mulCapacityField.getText());
        Main.Icapacity = Integer.parseInt(intCapacityField.getText());
        Main.Lcapacity = Integer.parseInt(loadCapacityField.getText());
        Main.Scapacity = Integer.parseInt(storeCapacityField.getText());
        
        // Get instructions from UI
        String instructionText = instructionInput.getText();
        Main.instructions = instructionText.split("\n");
        
        // Initialize the Tomasulo structures
        Main.initialize();
        
        Main.cycle = 0;
        Main.curInstruction = 0;
        Main.running = true;
    }
    
    private void runSimulation() {
        Thread simulationThread = new Thread(() -> {
            while (isRunning && Main.running && Main.curInstruction < Main.instructions.length) {
                executeOneCycle();
                
                Platform.runLater(this::updateDisplay);
                
                try {
                    Thread.sleep(500); // Delay for visualization
                } catch (InterruptedException e) {
                    break;
                }
            }
            
            Platform.runLater(() -> {
                isRunning = false;
                startButton.setText("Start");
            });
        });
        
        simulationThread.setDaemon(true);
        simulationThread.start();
    }
    
    private void executeOneCycle() {
        Main.cycle++;
        
        // Try to issue next instruction
        if (Main.curInstruction < Main.instructions.length) {
            if (Parse.parse(Main.instructions[Main.curInstruction])) {
                Main.curInstruction++;
            }
        }
        
        // Publish results
        Main.publish();
        
        // Execute stations
        Main.run();
    }
    
    private void updateDisplay() {
        cycleLabel.setText("Cycle: " + Main.cycle);
        
        // Update Add Stations
        updateAddStations();
        
        // Update Mul Stations
        updateMulStations();
        
        // Update Int Stations
        updateIntStations();
        
        // Update Load Buffer
        updateLoadBuffer();
        
        // Update Store Buffer
        updateStoreBuffer();
        
        // Update Registers
        updateRegisters();
    }
    
    private void updateAddStations() {
        if (addStationsTable == null) return;
        
        ObservableList<StationDisplay> data = FXCollections.observableArrayList();
        for (String key : Main.Add_Stations.keySet()) {
            Reservation_Station station = Main.Add_Stations.get(key);
            data.add(new StationDisplay(
                key,
                station.Busy == 1 ? "Yes" : "No",
                station.op != null ? station.op : "",
                String.valueOf(station.vj),
                String.valueOf(station.vk),
                station.Qj != null ? station.Qj : "",
                station.Qk != null ? station.Qk : ""
            ));
        }
        addStationsTable.setItems(data);
    }
    
    private void updateMulStations() {
        if (mulStationsTable == null) return;
        
        ObservableList<StationDisplay> data = FXCollections.observableArrayList();
        for (String key : Main.Mul_Stations.keySet()) {
            Reservation_Station station = Main.Mul_Stations.get(key);
            data.add(new StationDisplay(
                key,
                station.Busy == 1 ? "Yes" : "No",
                station.op != null ? station.op : "",
                String.valueOf(station.vj),
                String.valueOf(station.vk),
                station.Qj != null ? station.Qj : "",
                station.Qk != null ? station.Qk : ""
            ));
        }
        mulStationsTable.setItems(data);
    }
    
    private void updateIntStations() {
        if (intStationsTable == null) return;
        
        ObservableList<StationDisplay> data = FXCollections.observableArrayList();
        for (String key : Main.Integer_Stations.keySet()) {
            Reservation_Station station = Main.Integer_Stations.get(key);
            data.add(new StationDisplay(
                key,
                station.Busy == 1 ? "Yes" : "No",
                station.op != null ? station.op : "",
                String.valueOf(station.vj),
                String.valueOf(station.vk),
                station.Qj != null ? station.Qj : "",
                station.Qk != null ? station.Qk : ""
            ));
        }
        intStationsTable.setItems(data);
    }
    
    private void updateLoadBuffer() {
        if (loadBufferTable == null) return;
        
        ObservableList<BufferDisplay> data = FXCollections.observableArrayList();
        for (String key : Main.Load_Buffer.keySet()) {
            Buffer buffer = Main.Load_Buffer.get(key);
            data.add(new BufferDisplay(
                key,
                buffer.Busy == 1 ? "Yes" : "No",
                String.valueOf(buffer.Address),
                String.valueOf(buffer.V),
                buffer.Q != null ? buffer.Q : ""
            ));
        }
        loadBufferTable.setItems(data);
    }
    
    private void updateStoreBuffer() {
        if (storeBufferTable == null) return;
        
        ObservableList<BufferDisplay> data = FXCollections.observableArrayList();
        for (String key : Main.Store_Buffer.keySet()) {
            Buffer buffer = Main.Store_Buffer.get(key);
            data.add(new BufferDisplay(
                key,
                buffer.Busy == 1 ? "Yes" : "No",
                String.valueOf(buffer.Address),
                String.valueOf(buffer.V),
                buffer.Q != null ? buffer.Q : ""
            ));
        }
        storeBufferTable.setItems(data);
    }
    
    private void updateRegisters() {
        if (registerTable == null) return;
        
        ObservableList<RegisterDisplay> data = FXCollections.observableArrayList();
        for (String key : Main.registerMap.keySet()) {
            Register reg = Main.registerMap.get(key);
            Object value = reg.getValue();
            data.add(new RegisterDisplay(
                key,
                value instanceof Integer ? String.valueOf((Integer)value) : "",
                value instanceof String ? (String)value : ""
            ));
        }
        registerTable.setItems(data);
    }
    
    private void clearTables() {
        if (addStationsTable != null) addStationsTable.getItems().clear();
        if (mulStationsTable != null) mulStationsTable.getItems().clear();
        if (intStationsTable != null) intStationsTable.getItems().clear();
        if (loadBufferTable != null) loadBufferTable.getItems().clear();
        if (storeBufferTable != null) storeBufferTable.getItems().clear();
        if (registerTable != null) registerTable.getItems().clear();
    }
    
    // Helper classes for TableView
    public static class StationDisplay {
        private final javafx.beans.property.SimpleStringProperty tag;
        private final javafx.beans.property.SimpleStringProperty busy;
        private final javafx.beans.property.SimpleStringProperty op;
        private final javafx.beans.property.SimpleStringProperty vj;
        private final javafx.beans.property.SimpleStringProperty vk;
        private final javafx.beans.property.SimpleStringProperty qj;
        private final javafx.beans.property.SimpleStringProperty qk;
        
        public StationDisplay(String tag, String busy, String op, String vj, String vk, String qj, String qk) {
            this.tag = new javafx.beans.property.SimpleStringProperty(tag);
            this.busy = new javafx.beans.property.SimpleStringProperty(busy);
            this.op = new javafx.beans.property.SimpleStringProperty(op);
            this.vj = new javafx.beans.property.SimpleStringProperty(vj);
            this.vk = new javafx.beans.property.SimpleStringProperty(vk);
            this.qj = new javafx.beans.property.SimpleStringProperty(qj);
            this.qk = new javafx.beans.property.SimpleStringProperty(qk);
        }
        
        public javafx.beans.property.SimpleStringProperty tagProperty() { return tag; }
        public javafx.beans.property.SimpleStringProperty busyProperty() { return busy; }
        public javafx.beans.property.SimpleStringProperty opProperty() { return op; }
        public javafx.beans.property.SimpleStringProperty vjProperty() { return vj; }
        public javafx.beans.property.SimpleStringProperty vkProperty() { return vk; }
        public javafx.beans.property.SimpleStringProperty qjProperty() { return qj; }
        public javafx.beans.property.SimpleStringProperty qkProperty() { return qk; }
    }
    
    public static class BufferDisplay {
        private final javafx.beans.property.SimpleStringProperty tag;
        private final javafx.beans.property.SimpleStringProperty busy;
        private final javafx.beans.property.SimpleStringProperty address;
        private final javafx.beans.property.SimpleStringProperty value;
        private final javafx.beans.property.SimpleStringProperty q;
        
        public BufferDisplay(String tag, String busy, String address, String value, String q) {
            this.tag = new javafx.beans.property.SimpleStringProperty(tag);
            this.busy = new javafx.beans.property.SimpleStringProperty(busy);
            this.address = new javafx.beans.property.SimpleStringProperty(address);
            this.value = new javafx.beans.property.SimpleStringProperty(value);
            this.q = new javafx.beans.property.SimpleStringProperty(q);
        }
        
        public javafx.beans.property.SimpleStringProperty tagProperty() { return tag; }
        public javafx.beans.property.SimpleStringProperty busyProperty() { return busy; }
        public javafx.beans.property.SimpleStringProperty addressProperty() { return address; }
        public javafx.beans.property.SimpleStringProperty valueProperty() { return value; }
        public javafx.beans.property.SimpleStringProperty qProperty() { return q; }
    }
    
    public static class RegisterDisplay {
        private final javafx.beans.property.SimpleStringProperty name;
        private final javafx.beans.property.SimpleStringProperty value;
        private final javafx.beans.property.SimpleStringProperty qi;
        
        public RegisterDisplay(String name, String value, String qi) {
            this.name = new javafx.beans.property.SimpleStringProperty(name);
            this.value = new javafx.beans.property.SimpleStringProperty(value);
            this.qi = new javafx.beans.property.SimpleStringProperty(qi);
        }
        
        public javafx.beans.property.SimpleStringProperty nameProperty() { return name; }
        public javafx.beans.property.SimpleStringProperty valueProperty() { return value; }
        public javafx.beans.property.SimpleStringProperty qiProperty() { return qi; }
    }
}
