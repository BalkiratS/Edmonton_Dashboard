package com.example.milestone3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Languages {

    private Map<String, Integer> languages = new HashMap<>();

    /*public static void main(String[] args) throws IOException {
        List<String[]> readFile = readData("/Datasets/2016_Census_-_Dwelling_Unit_by_Language__Neighbourhood_Ward_.csv");
        System.out.println(readFile);
    }*/

    // Test constructor
    public Languages() throws IOException {
        /*List<String[]> readFile = readData("2016_Census_-_Dwelling_Unit_by_Language__Neighbourhood_Ward_");
        System.out.println(readFile);*/
    }

    // Languages main constructor
    public Languages(String neighbourhoodName) {

    }

    public Map<String, Integer> getLanguages() {
        return languages;
    }
}
