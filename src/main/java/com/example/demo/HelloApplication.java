package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private VBox centerContainer;

    @Override
    public void start(Stage stage) {

        // ========== TOP BAR ==========
        Button loadButton = new Button("Load Program");
        Button configureButton = new Button("Configure");
        Button continueButton = new Button("Continue");
        Button resetButton = new Button("Reset");

        Label cycleLabel = new Label("Cycle: 0");

        HBox topBar = new HBox(10, loadButton, configureButton, continueButton, resetButton, cycleLabel);
        topBar.setStyle("-fx-padding: 10; -fx-background-color: #e6e6e6;");

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
        centerContainer.getChildren().add(createRS_Table());

        centerContainer.getChildren().add(new Label("Multiply Stations:"));
        centerContainer.getChildren().add(createRS_Table());

        centerContainer.getChildren().add(new Label("Divide Stations:"));
        centerContainer.getChildren().add(createRS_Table());
    }

    private TableView<Object> createRS_Table() {
        TableView<Object> table = new TableView<>();
        table.setPrefHeight(130);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().addAll(
                new TableColumn<>("Name"),
                new TableColumn<>("Busy"),
                new TableColumn<>("Op"),
                new TableColumn<>("Vj"),
                new TableColumn<>("Vk"),
                new TableColumn<>("Qj"),
                new TableColumn<>("Qk")
        );

        return table;
    }

    private void showLoadStoreBuffers() {
        centerContainer.getChildren().clear();

        centerContainer.getChildren().add(new Label("Load Buffers:"));
        centerContainer.getChildren().add(createLS_Table());

        centerContainer.getChildren().add(new Label("Store Buffers:"));
        centerContainer.getChildren().add(createLS_Table());
    }

    private TableView<Object> createLS_Table() {
        TableView<Object> table = new TableView<>();
        table.setPrefHeight(130);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().addAll(
                new TableColumn<>("Name"),
                new TableColumn<>("Busy"),
                new TableColumn<>("Address"),
                new TableColumn<>("V"),
                new TableColumn<>("Q")
        );

        return table;
    }

    private void showRegisterFile() {
        centerContainer.getChildren().clear();

        TableView<Object> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        table.getColumns().addAll(
                new TableColumn<>("Register"),
                new TableColumn<>("Value"),
                new TableColumn<>("Qi")
        );

        centerContainer.getChildren().add(table);
    }

    private void showCache() {
        centerContainer.getChildren().clear();

        Label msg = new Label("Cache Configuration will be displayed after simulation starts");
        msg.setPadding(new Insets(20));

        centerContainer.getChildren().add(msg);
    }

    public static void main(String[] args) {
        launch();
    }
}
