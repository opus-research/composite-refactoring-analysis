package inf.puc.rio.opus.composite.analysis.analysis.smells;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.*;

public class SmellAnalyzer {


    public static void main(String[] args) {
        SmellAnalyzer analyzer = new SmellAnalyzer();


        String projects[]={"geoserver", "hystrix", "javadriver", "jitwatch",
                "materialdialogs", "materialdrawer", "mockito", "quasar",
                "restassured", "xabberandroid" };

        ArrayList<String> projectList = new ArrayList<String>(Arrays.asList(projects));



        projectList.forEach( project-> {

            List<String> relevantMetrics = analyzer.analyzeRelevantMetrics(project);

            relevantMetrics.stream().forEach(System.out::println);

            analyzer.writeRelevantMetrics(project, relevantMetrics);
        });




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

    public List<String> analyzeRelevantMetrics(String project) {
        String pathLM = "C:\\Users\\anaca\\Documents\\lm-lem\\" + project + "\\median_lm_" + project +".csv";
        String pathLEM = "C:\\Users\\anaca\\Documents\\lm-lem\\" + project +"\\median_lem_" + project +".csv";
        Map<String, Double> metricsLM = populateMapFromCSV(pathLM);
        Map<String, Double> metricsLEM = populateMapFromCSV(pathLEM);
        List<String> relevantMetrics = new ArrayList<>();

        for (Map.Entry<String, Double> entryLM : metricsLM.entrySet()) {
            String keyLM = entryLM.getKey();
            Double valueLM = entryLM.getValue();

            for (Map.Entry<String, Double> entryLEM : metricsLEM.entrySet()) {
                String keyLEM = entryLEM.getKey();
                Double valueLEM = entryLEM.getValue();

                if (keyLM.equals(keyLEM)) {

                    double difference = valueLM - valueLEM;
                    difference = Math.abs(difference);

                    if(difference >= 0.2){
                        relevantMetrics.add(keyLEM);
                    }
                }
            }

        }

        return relevantMetrics;

    }



    public void writeRelevantMetrics(String project, List<String> relevantMetrics){
        File relevantMetricsFile = new File("relevant-metrics-" + project + ".txt");
        try {
            FileWriter writer = new FileWriter(relevantMetricsFile);

            for (String relevantMetric : relevantMetrics) {

                    writer.write(relevantMetric);
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}
