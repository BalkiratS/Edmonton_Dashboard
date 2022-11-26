package com.example.milestone3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Languages {

    private final Map<String, Integer> languages = new HashMap<>();

    /*public static void main(String[] args) throws IOException, URISyntaxException {
        Languages languages1 = new Languages(Utils.readData("/Datasets/2016_Census_-_Dwelling_Unit_by_Language__Neighbourhood_Ward_.csv"),
                "CRESTWOOD");
        System.out.println(languages1);
    }*/

    // Languages constructor
    public Languages(List<String[]> data, String neighbourhoodName){
        for (String[] row : data){
            if (neighbourhoodName.equalsIgnoreCase(row[2])){
                languages.put("Arabic", Integer.valueOf(row[4]));
                languages.put("Cantonese", Integer.valueOf(row[5]));
                languages.put("French", Integer.valueOf(row[6]));
                languages.put("German", Integer.valueOf(row[7]));
                languages.put("Mandarin", Integer.valueOf(row[8]));
                languages.put("Indigenous", Integer.valueOf(row[9]));
                languages.put("Punjabi", Integer.valueOf(row[10]));
                languages.put("Spanish", Integer.valueOf(row[11]));
                languages.put("Filipino", Integer.valueOf(row[12]));
                languages.put("Ukrainian", Integer.valueOf(row[13]));
                break;
            }
        }
    }

    public Map<String, Integer> getLanguages() {
        return languages;
    }

    @Override
    public String toString() {
        return String.valueOf(languages);
    }
}
