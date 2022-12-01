package com.example.milestone3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static com.example.milestone3.Utils.readData;


public class Neighbourhoods{

    List<Neighbourhood> neighbourhoods = new ArrayList<>();

    public Neighbourhoods(){
        List<String[]> assessedValueData = new ArrayList<>();
        List<String[]> developmentData = new ArrayList<>();
        List<String[]> languageData = new ArrayList<>();
        try{
            assessedValueData = readData("/Datasets/Property_Assessment_Data__Current_Calendar_Year_ (1).csv");
            developmentData = readData("/Datasets/Mature_Neighbourhood_Reinvestment.csv");
            languageData = readData("/Datasets/2016_Census_-_Dwelling_Unit_by_Language__Neighbourhood_Ward_.csv");
        }
        catch (IOException | URISyntaxException exception){
            System.out.println("Error in file");
        }

        for (Map.Entry<Integer, String> entry: getAllNeighbourhoodsIDName(assessedValueData).entrySet()){
            neighbourhoods.add(new Neighbourhood(entry.getKey(), entry.getValue(), new AverageAssessedValue(assessedValueData, entry.getKey()), new NeighbourhoodDevelopment(developmentData, entry.getValue()), new Languages(languageData, entry.getValue())));
        }

    }

    private Map<Integer,String> getAllNeighbourhoodsIDName(List<String[]> assessedValueData){
        Map<Integer, String> idNameMap = new HashMap<>();
        for (String[] row: assessedValueData){
            if (!row[5].isEmpty()){
            idNameMap.put(Integer.parseInt(row[5]), row[6]);}
        }
        return idNameMap;
    }

    public List<Neighbourhood> getNeighbourhoodsList(){
        return neighbourhoods.stream()
                .sorted(Comparator.comparing(Neighbourhood::getAverageAssessedValue))
                .toList();
    }

    public List<String> getUniqueNeighbourhoodNames(){
        List<String> neighbourhoodNames = new ArrayList<>();
        for (Neighbourhood neighbourhood: neighbourhoods){
            neighbourhoodNames.add(neighbourhood.getNeighbourhoodName());
        }
        return neighbourhoodNames;
    }

    public List<Neighbourhood> getNeighbourhoodsInRange(Double min, Double max){
        List<Neighbourhood> returnList = new ArrayList<>();
        for (Neighbourhood neighbourhood: neighbourhoods){
            if (neighbourhood.getAverageAssessedValue() >= min && neighbourhood.getAverageAssessedValue() <= max){
                returnList.add(neighbourhood);
            }
        }
        return returnList;
    }

    public Neighbourhood getNeighbourhoodByName(String nName){
        for (Neighbourhood neighbourhood: neighbourhoods){
            if (neighbourhood.getNeighbourhoodName().equalsIgnoreCase(nName)){
                return neighbourhood;
            }
        }
        return null;
    }
}
