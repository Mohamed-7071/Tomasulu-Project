package com.example.demo.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//this was fully done by AI to read instructions given in a text file
public class MipsInstructionReader {

    public static String[] getInstructions(Stage stage) throws IOException {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open MIPS Instruction File");
        
        // Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
            "Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Set initial directory to project directory
        File initialDir = new File(System.getProperty("user.dir"));
        if (initialDir.exists()) {
            fileChooser.setInitialDirectory(initialDir);
        }
        
        // Show open file dialog
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            // Read all lines from the selected file
            Path path = file.toPath();
            List<String> instructionList = Files.readAllLines(path);
            
            // Convert the List<String> into a String[]
            return instructionList.toArray(new String[0]);
        } else {
            // User cancelled the file selection
            throw new IOException("No file selected");
        }
    }
    
    // Fallback method for default file
    public static String[] getInstructions() throws IOException {
        File defaultFile = new File("mips_instructions.txt");
        if (defaultFile.exists()) {
            Path path = defaultFile.toPath();
            List<String> instructionList = Files.readAllLines(path);
            return instructionList.toArray(new String[0]);
        } else {
            throw new IOException("Default file 'mips_instructions.txt' not found");
        }
    }
}