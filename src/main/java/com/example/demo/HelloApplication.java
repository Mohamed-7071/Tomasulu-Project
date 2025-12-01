package com.example.demo;

import com.example.demo.logic.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

public class HelloApplication extends Application {

    private VBox centerContainer;
    private Label cycleLabel;
    private boolean isRunning = false;
    private Thread simulationThread;
    
    // Tables for displaying data
    private TableView<StationRow> addStationsTable;
    private TableView<StationRow> mulStationsTable;
    private TableView<StationRow> intStationsTable;
    private TableView<BufferRow> loadBuffersTable;
    private TableView<BufferRow> storeBuffersTable;
    private TableView<RegisterRow> registerTable;
    private TextArea logArea;

    @Override
    public void start(Stage stage) {

        // ========== TOP BAR ==========
        Button loadButton = new Button("Load Program");
        Button configureButton = new Button("Configure");
        Button continueButton = new Button("Continue");
        Button resetButton = new Button("Reset");

        cycleLabel = new Label("Cycle: 0");

        HBox topBar = new HBox(10, loadButton, configureButton, continueButton, resetButton, cycleLabel);
        topBar.setStyle("-fx-padding: 10; -fx-background-color: #e6e6e6;");
        
        // ========== BUTTON HANDLERS ==========
        loadButton.setOnAction(e -> loadProgram());
        configureButton.setOnAction(e -> showConfiguration());
        continueButton.setOnAction(e -> runCycle());
        resetButton.setOnAction(e -> resetSimulation());

        // ========== TABS ==========
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Prevent TabPane from growing and breaking UI
        tabPane.setPrefHeight(40);
        tabPane.setMinHeight(40);
        tabPane.setMaxHeight(40);

        Tab instructionQueueTab = new Tab("Instruction Queue");
        Tab reservationStationsTab = new Tab("Reservation Stations");
        Tab loadStoreTab = new Tab("Load/Store Buffers");
        Tab registerFileTab = new Tab("Register File");
        Tab cacheTab = new Tab("Cache");

        tabPane.getTabs().addAll(
                instructionQueueTab,
                reservationStationsTab,
                loadStoreTab,
                registerFileTab,
                cacheTab
        );

        // ========== CENTER CONTAINER (CHANGES PER TAB) ==========
        centerContainer = new VBox();
        centerContainer.setPadding(new Insets(10));
        centerContainer.setSpacing(15);
        VBox.setVgrow(centerContainer, Priority.ALWAYS);

        showInstructionQueue();

        // ========== TAB SWITCHING ==========
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            switch (newTab.getText()) {
                case "Instruction Queue" -> showInstructionQueue();
                case "Reservation Stations" -> showReservationStations();
                case "Load/Store Buffers" -> showLoadStoreBuffers();
                case "Register File" -> showRegisterFile();
                case "Cache" -> showCache();
            }
        });

        // ========== BOTTOM LOG ==========
        TextArea logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(130);
        logArea.appendText("""
                Execution Log:
                
                """);

        // ========== MAIN LAYOUT ==========
        VBox centerArea = new VBox(tabPane, centerContainer);
        VBox.setVgrow(centerContainer, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(centerArea);
        root.setBottom(logArea);

        Scene scene = new Scene(root, 1100, 700);
        stage.setTitle("Tomasulo Algorithm Simulator");
        stage.setScene(scene);
        stage.show();
    }

    // ============================================================
    // =============== TAB CONTENT FUNCTIONS ======================
    // ============================================================

    private void showInstructionQueue() {
        centerContainer.getChildren().clear();

        TableView<Object> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        table.getColumns().addAll(
                new TableColumn<>("Instruction"),
                new TableColumn<>("Issue"),
                new TableColumn<>("Exec Start"),
                new TableColumn<>("Exec End"),
                new TableColumn<>("Write")
        );

        centerContainer.getChildren().add(table);
    }

    private void showReservationStations() {
        centerContainer.getChildren().clear();

        centerContainer.getChildren().add(new Label("Add/Sub Stations:"));
        addStationsTable = createRS_Table();
        centerContainer.getChildren().add(addStationsTable);

        centerContainer.getChildren().add(new Label("Multiply Stations:"));
        mulStationsTable = createRS_Table();
        centerContainer.getChildren().add(mulStationsTable);

        centerContainer.getChildren().add(new Label("Integer Stations:"));
        intStationsTable = createRS_Table();
        centerContainer.getChildren().add(intStationsTable);
        
        updateAllTables();
    }

    private TableView<StationRow> createRS_Table() {
        TableView<StationRow> table = new TableView<>();
        table.setPrefHeight(130);

        TableColumn<StationRow, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        
        TableColumn<StationRow, String> busyCol = new TableColumn<>("Busy");
        busyCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBusy()));
        
        TableColumn<StationRow, String> opCol = new TableColumn<>("Op");
        opCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOp()));
        
        TableColumn<StationRow, String> vjCol = new TableColumn<>("Vj");
        vjCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVj()));
        
        TableColumn<StationRow, String> vkCol = new TableColumn<>("Vk");
        vkCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVk()));
        
        TableColumn<StationRow, String> qjCol = new TableColumn<>("Qj");
        qjCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQj()));
        
        TableColumn<StationRow, String> qkCol = new TableColumn<>("Qk");
        qkCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQk()));

        table.getColumns().addAll(nameCol, busyCol, opCol, vjCol, vkCol, qjCol, qkCol);

        return table;
    }

    private void showLoadStoreBuffers() {
        centerContainer.getChildren().clear();

        centerContainer.getChildren().add(new Label("Load Buffers:"));
        loadBuffersTable = createLS_Table();
        centerContainer.getChildren().add(loadBuffersTable);

        centerContainer.getChildren().add(new Label("Store Buffers:"));
        storeBuffersTable = createLS_Table();
        centerContainer.getChildren().add(storeBuffersTable);
        
        updateAllTables();
    }

    private TableView<BufferRow> createLS_Table() {
        TableView<BufferRow> table = new TableView<>();
        table.setPrefHeight(130);

        TableColumn<BufferRow, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        
        TableColumn<BufferRow, String> busyCol = new TableColumn<>("Busy");
        busyCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBusy()));
        
        TableColumn<BufferRow, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        
        TableColumn<BufferRow, String> vCol = new TableColumn<>("V");
        vCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getV()));
        
        TableColumn<BufferRow, String> qCol = new TableColumn<>("Q");
        qCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQ()));

        table.getColumns().addAll(nameCol, busyCol, addressCol, vCol, qCol);

        return table;
    }

    private void showRegisterFile() {
        centerContainer.getChildren().clear();

        registerTable = new TableView<>();
        VBox.setVgrow(registerTable, Priority.ALWAYS);

        TableColumn<RegisterRow, String> regCol = new TableColumn<>("Register");
        regCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegister()));
        
        TableColumn<RegisterRow, String> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
        
        TableColumn<RegisterRow, String> qiCol = new TableColumn<>("Qi");
        qiCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQi()));

        registerTable.getColumns().addAll(regCol, valueCol, qiCol);

        centerContainer.getChildren().add(registerTable);
        
        updateAllTables();
    }

    private void showCache() {
        centerContainer.getChildren().clear();

        Label msg = new Label("Cache Configuration will be displayed after simulation starts");
        msg.setPadding(new Insets(20));

        centerContainer.getChildren().add(msg);
    }

    // ============================================================
    // =============== SIMULATION CONTROL METHODS =================
    // ============================================================
    
    private void loadProgram() {
        try {
            // Load instructions from file using MipsInstructionReader
            Main.instructions = MipsInstructionReader.getInstructions();
            
            if (logArea != null) {
                logArea.appendText("Program loaded with " + Main.instructions.length + " instructions from file\n");
            }
            
            // Initialize with default settings if not already set
            if (Main.Addtime == 0) Main.Addtime = 2;
            if (Main.Multime == 0) Main.Multime = 10;
            if (Main.Inttime == 0) Main.Inttime = 1;
            if (Main.Loadtime == 0) Main.Loadtime = 2;
            if (Main.Storetime == 0) Main.Storetime = 2;
            
            if (Main.Acapacity == 0) Main.Acapacity = 3;
            if (Main.Mcapacity == 0) Main.Mcapacity = 3;
            if (Main.Icapacity == 0) Main.Icapacity = 3;
            if (Main.Lcapacity == 0) Main.Lcapacity = 3;
            if (Main.Scapacity == 0) Main.Scapacity = 3;
            
            Main.initialize();
            
            cycleLabel.setText("Cycle: 0");
            updateAllTables();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Program Loaded");
            alert.setHeaderText("Success!");
            alert.setContentText("Loaded " + Main.instructions.length + " instructions from mips_instructions.txt");
            alert.showAndWait();
            
        } catch (java.io.IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Loading Program");
            alert.setHeaderText("Could not read instruction file");
            alert.setContentText("Make sure 'mips_instructions.txt' exists in the project directory.\n\nError: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    private void showConfiguration() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Configuration");
        dialog.setHeaderText("Set latencies and capacities:");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField addLatency = new TextField(String.valueOf(Main.Addtime));
        TextField mulLatency = new TextField(String.valueOf(Main.Multime));
        TextField intLatency = new TextField(String.valueOf(Main.Inttime));
        TextField loadLatency = new TextField(String.valueOf(Main.Loadtime));
        TextField storeLatency = new TextField(String.valueOf(Main.Storetime));
        
        TextField addCap = new TextField(String.valueOf(Main.Acapacity));
        TextField mulCap = new TextField(String.valueOf(Main.Mcapacity));
        TextField intCap = new TextField(String.valueOf(Main.Icapacity));
        TextField loadCap = new TextField(String.valueOf(Main.Lcapacity));
        TextField storeCap = new TextField(String.valueOf(Main.Scapacity));
        
        grid.add(new Label("Add Latency:"), 0, 0);
        grid.add(addLatency, 1, 0);
        grid.add(new Label("Mul Latency:"), 0, 1);
        grid.add(mulLatency, 1, 1);
        grid.add(new Label("Int Latency:"), 0, 2);
        grid.add(intLatency, 1, 2);
        grid.add(new Label("Load Latency:"), 0, 3);
        grid.add(loadLatency, 1, 3);
        grid.add(new Label("Store Latency:"), 0, 4);
        grid.add(storeLatency, 1, 4);
        
        grid.add(new Label("Add Capacity:"), 2, 0);
        grid.add(addCap, 3, 0);
        grid.add(new Label("Mul Capacity:"), 2, 1);
        grid.add(mulCap, 3, 1);
        grid.add(new Label("Int Capacity:"), 2, 2);
        grid.add(intCap, 3, 2);
        grid.add(new Label("Load Capacity:"), 2, 3);
        grid.add(loadCap, 3, 3);
        grid.add(new Label("Store Capacity:"), 2, 4);
        grid.add(storeCap, 3, 4);
        
        dialog.getDialogPane().setContent(grid);
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == okButton) {
                Main.Addtime = Integer.parseInt(addLatency.getText());
                Main.Multime = Integer.parseInt(mulLatency.getText());
                Main.Inttime = Integer.parseInt(intLatency.getText());
                Main.Loadtime = Integer.parseInt(loadLatency.getText());
                Main.Storetime = Integer.parseInt(storeLatency.getText());
                
                Main.Acapacity = Integer.parseInt(addCap.getText());
                Main.Mcapacity = Integer.parseInt(mulCap.getText());
                Main.Icapacity = Integer.parseInt(intCap.getText());
                Main.Lcapacity = Integer.parseInt(loadCap.getText());
                Main.Scapacity = Integer.parseInt(storeCap.getText());
                
                if (logArea != null) {
                    logArea.appendText("Configuration updated\n");
                }
            }
        });
    }
    
    private void runCycle() {
        if (Main.instructions == null || Main.instructions.length == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Program");
            alert.setHeaderText("Please load a program first!");
            alert.showAndWait();
            return;
        }
        
        if (!Main.running) {
            if (logArea != null) {
                logArea.appendText("Simulation complete!\n");
            }
            return;
        }
        
        // Execute one cycle in Main
        String currentInstr = (Main.curInstruction < Main.instructions.length) ? 
            Main.instructions[Main.curInstruction] : null;
        int prevInstruction = Main.curInstruction;
        
        Main.executeCycle();
        
        // Log if instruction was issued
        if (prevInstruction < Main.curInstruction && currentInstr != null) {
            if (logArea != null) {
                logArea.appendText("Cycle " + Main.cycle + ": Issued instruction " + currentInstr + "\n");
            }
        }
        
        // Update UI
        cycleLabel.setText("Cycle: " + Main.cycle);
        updateAllTables();
        
        // Check if completed
        if (!Main.running && logArea != null) {
            logArea.appendText("All instructions completed!\n");
        }
    }
    
    private void resetSimulation() {
        Main.cycle = 0;
        Main.curInstruction = 0;
        Main.running = true;
        
        if (Main.instructions != null && Main.instructions.length > 0) {
            Main.initialize();
        }
        
        cycleLabel.setText("Cycle: 0");
        if (logArea != null) {
            logArea.clear();
            logArea.appendText("Execution Log:\n\n");
            logArea.appendText("Simulation reset\n");
        }
        updateAllTables();
    }
    
    private void updateAllTables() {
        // Update all tables based on current tab selection
        Platform.runLater(() -> {
            if (addStationsTable != null) updateReservationStationTable(addStationsTable, Main.Add_Stations);
            if (mulStationsTable != null) updateReservationStationTable(mulStationsTable, Main.Mul_Stations);
            if (intStationsTable != null) updateReservationStationTable(intStationsTable, Main.Integer_Stations);
            if (loadBuffersTable != null) updateBufferTable(loadBuffersTable, Main.Load_Buffer);
            if (storeBuffersTable != null) updateBufferTable(storeBuffersTable, Main.Store_Buffer);
            if (registerTable != null) updateRegisterTable();
        });
    }
    
    private void updateReservationStationTable(TableView<StationRow> table, java.util.HashMap<String, Reservation_Station> stations) {
        ObservableList<StationRow> data = FXCollections.observableArrayList();
        for (String key : stations.keySet()) {
            Reservation_Station station = stations.get(key);
            data.add(new StationRow(
                key,
                station.Busy == 1 ? "Yes" : "No",
                station.op != null ? station.op : "-",
                String.valueOf(station.vj),
                String.valueOf(station.vk),
                station.Qj != null ? station.Qj : "-",
                station.Qk != null ? station.Qk : "-"
            ));
        }
        table.setItems(data);
    }
    
    private void updateBufferTable(TableView<BufferRow> table, java.util.HashMap<String, Buffer> buffers) {
        ObservableList<BufferRow> data = FXCollections.observableArrayList();
        for (String key : buffers.keySet()) {
            Buffer buffer = buffers.get(key);
            data.add(new BufferRow(
                key,
                buffer.Busy == 1 ? "Yes" : "No",
                String.valueOf(buffer.Address),
                String.valueOf(buffer.V),
                buffer.Q != null ? buffer.Q : "-"
            ));
        }
        table.setItems(data);
    }
    
    private void updateRegisterTable() {
        ObservableList<RegisterRow> data = FXCollections.observableArrayList();
        for (String key : Main.registerMap.keySet()) {
            Register reg = Main.registerMap.get(key);
            Object value = reg.getValue();
            data.add(new RegisterRow(
                key,
                value instanceof Integer ? String.valueOf((Integer)value) : "-",
                value instanceof String ? (String)value : "-"
            ));
        }
        registerTable.setItems(data);
    }

    public static void main(String[] args) {
        launch();
    }
    
    // ============================================================
    // =============== DATA MODEL CLASSES =========================
    // ============================================================
    
    public static class StationRow {
        private final SimpleStringProperty name;
        private final SimpleStringProperty busy;
        private final SimpleStringProperty op;
        private final SimpleStringProperty vj;
        private final SimpleStringProperty vk;
        private final SimpleStringProperty qj;
        private final SimpleStringProperty qk;
        
        public StationRow(String name, String busy, String op, String vj, String vk, String qj, String qk) {
            this.name = new SimpleStringProperty(name);
            this.busy = new SimpleStringProperty(busy);
            this.op = new SimpleStringProperty(op);
            this.vj = new SimpleStringProperty(vj);
            this.vk = new SimpleStringProperty(vk);
            this.qj = new SimpleStringProperty(qj);
            this.qk = new SimpleStringProperty(qk);
        }
        
        public String getName() { return name.get(); }
        public String getBusy() { return busy.get(); }
        public String getOp() { return op.get(); }
        public String getVj() { return vj.get(); }
        public String getVk() { return vk.get(); }
        public String getQj() { return qj.get(); }
        public String getQk() { return qk.get(); }
    }
    
    public static class BufferRow {
        private final SimpleStringProperty name;
        private final SimpleStringProperty busy;
        private final SimpleStringProperty address;
        private final SimpleStringProperty v;
        private final SimpleStringProperty q;
        
        public BufferRow(String name, String busy, String address, String v, String q) {
            this.name = new SimpleStringProperty(name);
            this.busy = new SimpleStringProperty(busy);
            this.address = new SimpleStringProperty(address);
            this.v = new SimpleStringProperty(v);
            this.q = new SimpleStringProperty(q);
        }
        
        public String getName() { return name.get(); }
        public String getBusy() { return busy.get(); }
        public String getAddress() { return address.get(); }
        public String getV() { return v.get(); }
        public String getQ() { return q.get(); }
    }
    
    public static class RegisterRow {
        private final SimpleStringProperty register;
        private final SimpleStringProperty value;
        private final SimpleStringProperty qi;
        
        public RegisterRow(String register, String value, String qi) {
            this.register = new SimpleStringProperty(register);
            this.value = new SimpleStringProperty(value);
            this.qi = new SimpleStringProperty(qi);
        }
        
        public String getRegister() { return register.get(); }
        public String getValue() { return value.get(); }
        public String getQi() { return qi.get(); }
    }
}
