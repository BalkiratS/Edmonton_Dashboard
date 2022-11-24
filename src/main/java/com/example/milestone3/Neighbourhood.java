package com.example.milestone3;

import java.util.Map;

public class Neighbourhood {
    int neighbourhoodID;
    String neighbourhoodName;
    AverageAssessedValue averageAssessedValue;
    NeighbourhoodDevelopment neighbourhoodDevelopment;
    Languages languages;

    public Neighbourhood(int id, String neighbourhoodName, AverageAssessedValue averageAssessedValue, NeighbourhoodDevelopment neighbourhoodDevelopment, Languages languages){
        this.neighbourhoodID = id;
        this.neighbourhoodName = neighbourhoodName;
        this.averageAssessedValue = averageAssessedValue;
        this.neighbourhoodDevelopment = neighbourhoodDevelopment;
        this.languages = languages;
    }

    public int getNeighbourhoodID(){
        return neighbourhoodID;
    }

    public Double getAverageAssessedValue() {
        return averageAssessedValue.getAverageAssessedValue();
    }

    public Map<Integer, Double> getNeighbourhoodDevelopment() {
        return neighbourhoodDevelopment.getDevelopment();
    }

    public Map<String, Integer> getLanguages() {
        return languages.getLanguages();
    }
}
