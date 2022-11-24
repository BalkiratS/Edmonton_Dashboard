package com.example.milestone3;

import java.io.IOException;
import java.util.List;

import static com.example.milestone3.Utils.readData;


public class Neighbourhoods {

    public static void main(String[] args) throws IOException {

        List<String[]> data = readData("Mature_Neighbourhood_Reinvestment.csv");

        System.out.println(data);
    }
}
