package com.example.milestone3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.milestone3.Utils.readData;

public class test {

    public static void main(String[] args) throws IOException, URISyntaxException {

        Neighbourhoods neighbourhoods = new Neighbourhoods();

        List<String[]> developmentData = new ArrayList<>();
        developmentData = readData("/Datasets/Mature_Neighbourhood_Reinvestment.csv");

        NeighbourhoodDevelopment bellevue = new NeighbourhoodDevelopment(developmentData, "Bellevue");

        //answer for year 2009 should be 86476 = 40000 + 46476
        System.out.println(bellevue.development.get(2009));

//        for (String[] row : developmentData){
//            if(row[2].equals("Bellevue")){
//                System.out.println("found: "+ row[2]);
//            }
//
//        }

//        for (int i = 0; i < 100; i++) {
//
//            System.out.println(Arrays.toString(developmentData.get(i)));
//        }
    }
}
