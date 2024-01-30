package com.example.milestone3;
import java.util.List;

public class AverageAssessedValue {
    private double averageAssessedValue;

    public AverageAssessedValue(List<String[]> data, int nID){
        int count = 0;
        for (String[] row : data){
            if (!row[5].isEmpty() && nID == Integer.parseInt(row[5]) && row[15].equals("RESIDENTIAL") && Double.parseDouble(row[8]) != 0){
                averageAssessedValue += Double.parseDouble(row[8]);
                count++;
            }
        }
        averageAssessedValue = averageAssessedValue/count;
    }

    public double getAverageAssessedValue() {
        return averageAssessedValue;
    }
}
