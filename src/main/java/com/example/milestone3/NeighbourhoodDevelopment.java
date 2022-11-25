package com.example.milestone3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeighbourhoodDevelopment {

    Map<Integer, Double> development = new HashMap<Integer, Double>();

    NeighbourhoodDevelopment(List<String[]> data, String neighbourhoodName){

        for (String[] row : data){
            if(row[2].equals(neighbourhoodName)){
                if(!development.containsKey(Integer.parseInt(row[0]))){
                    development.put(Integer.parseInt(row[0]), Double.parseDouble(row[3]));
                }else {
                    development.put(Integer.parseInt(row[0]),
                            development.get(Integer.parseInt(row[0])) + Double.parseDouble(row[3]));
                }
            }

        }


    }

    public Map<Integer, Double> getDevelopment(){
        return development;
    }

}
