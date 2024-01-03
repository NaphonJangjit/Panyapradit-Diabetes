package ai.thanakorn.naphon.diabetes;

import naphon.ai.ai.exceptions.AIException;
import naphon.ai.classify.KNearestNeighbors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File csvFile = null;
        csvFile = new File("src/data.csv");
        Scanner scanner;
        try {
            scanner = new Scanner(csvFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<double[]> keys = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] spline = line.split(",");
            double bmi = Double.parseDouble(spline[0]);
            double sugarLevel = Double.parseDouble(spline[1]);
            String isDiabetes = toValues(Integer.parseInt(spline[2]));
            keys.add(new double[]{bmi, sugarLevel});
            values.add(isDiabetes);
        }
        KNearestNeighbors knn = new KNearestNeighbors();
        Scanner input = new Scanner(System.in);
        try {
            knn.train(keys, values);
            System.out.println(knn.predict(new double[]{input.nextDouble(), input.nextDouble()}, 2));
        } catch (AIException e) {
            throw new RuntimeException(e);
        }
    }

    static String toValues(int v) {
        if (v == 0) {
            return "No";
        } else {
            return "Yes";
        }
    }
}