package com.example.demo.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//this was fully done by AI to read instructions given in a text file
public class MipsInstructionReader {

    public static String[] getInstructions() throws IOException {
        // 1. Define the path to your MIPS instruction file
        String filePath = "mips_instructions.txt"; 
        
        String[] instructionsArray;

            // Convert the String path to a Path object
            Path path = Paths.get(filePath);

            // 2. Read all lines from the file into a List<String>
            List<String> instructionList = Files.readAllLines(path);

            // 3. Convert the List<String> into a String[]
            // .toArray(new String[0]) is the standard way to do this
            return (instructionList.toArray(new String[0]));

    }
}