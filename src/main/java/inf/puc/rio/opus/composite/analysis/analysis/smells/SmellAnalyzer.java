package inf.puc.rio.opus.composite.analysis.analysis.smells;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmellAnalyzer {


    /*
     * To projects Ant, Presto, Tomcat
     * */
    public void getSmellsFromComposites() {
        // Get composites
        // Get refactorings from composites
        // Get commits from refactorings
        // Get smells from commits running Organic
    }

    public static void main(String[] args) {
        SmellAnalyzer analyzer = new SmellAnalyzer();
        analyzer.analyzeRelevantMetrics();
    }

    public Map<String, Double> populateMapFromCSV(String path) {

        // Create an object of file reader
        // class with CSV file as a parameter.
        FileReader filereader = null;
        Map<String, Double> items = new HashMap<>();
        try {
            filereader = new FileReader(path);
            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReader(filereader);

            List<String[]> allData = csvReader.readAll();

            // print Data
            for (String[] row : allData) {

                String key = row[0];
                String value = row[1];
                double metricValue = Double.parseDouble(value);
                items.put(key, metricValue);
                System.out.println(key);
                System.out.println(metricValue);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return items;
    }

    public void analyzeRelevantMetrics() {
        String pathLM = "C:\\Users\\anaca\\Documents\\lm-lem\\xabberandroid\\median_lm_xabberandroid.csv";
        String pathLEM = "C:\\Users\\anaca\\Documents\\lm-lem\\xabberandroid\\median_lem_xabberandroid.csv";
        Map<String, Double> metricsLM = populateMapFromCSV(pathLM);
        Map<String, Double> metricsLEM = populateMapFromCSV(pathLEM);


        for (Map.Entry<String, Double> entryLM : metricsLM.entrySet()) {
            String keyLM = entryLM.getKey();
            Double valueLM = entryLM.getValue();


            for (Map.Entry<String, Double> entryLEM : metricsLEM.entrySet()) {
                String keyLEM = entryLEM.getKey();
                Double valueLEM = entryLEM.getValue();

                if (keyLM.equals(keyLEM)) {
                    System.out.println("Chave: " + keyLM + ", Valor: " + valueLEM);
                }
            }

        }


    }

}
