package com.example.milestone3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<String[]> readData (String csvFileName) throws IOException {
        // Create a stream to read the CSV file
        BufferedReader reader = Files.newBufferedReader(Paths.get(csvFileName));

        // Skip the header and check file for empty - this assumes the first line is a header
        reader.readLine();

        // Create 2D List of Arrays to store all rows of data as String
        List<String[]> listOArrays = new ArrayList<>();

        // Read the file line by line and store all rows into a 2D array
        String line;
        while ((line = reader.readLine()) != null) {
            // Split a line by comma works for simple CSV files
            listOArrays.add(line.split(","));
        }

        return new ArrayList<>(listOArrays); // return a deep copy of List of Arrays
    }
}
