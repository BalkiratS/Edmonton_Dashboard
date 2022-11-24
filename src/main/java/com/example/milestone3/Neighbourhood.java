package com.example.milestone3;

import java.util.List;
import java.util.Map;

public class Neighbourhood {
    int neighbourhoodID;
    String neighbourhoodName;
    AverageAssessedValue averageAssessedValue;
    NeighbourhoodDevelopment neighbourhoodDevelopment;

    public Neighbourhood(int id, String neighbourhoodName, AverageAssessedValue averageAssessedValue, NeighbourhoodDevelopment neighbourhoodDevelopment){
        this.neighbourhoodID = id;
        this.neighbourhoodName = neighbourhoodName;
        this.averageAssessedValue = averageAssessedValue;
        this.neighbourhoodDevelopment = neighbourhoodDevelopment;
    }

    public int getNeighbourhoodID(){
        return neighbourhoodID;
    }

    public Double getAverageAssessedValue() {
        return averageAssessedValue.getAverageAssessedValue();
    }
}
