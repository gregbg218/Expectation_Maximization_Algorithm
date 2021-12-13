package bootstrap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Trainer {
    private String inputFile;
    private double trainingPercentage;
    private int noOfFeatures;
    private List<double[]> trainingData;
    private List<double[]> testData;
    private Map<String,Double > classes;

    public Trainer(String inputFile ,int noOfFeatures, double trainingPercentage, Map<String,Double > classes) throws IOException
    {
        this.inputFile = inputFile;
        this.noOfFeatures = noOfFeatures;
        this.trainingPercentage = trainingPercentage;
        this.trainingData = new ArrayList<>();
        this.testData = new ArrayList<>();
        this.classes= classes;
        computeDatasets();
    }

    public void computeDatasets() throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get(inputFile));
        int dataSetSize = lines.size()-1;

        Set<Integer> trainingIndices = new HashSet<>();
        int trainingDataSize = (int) ((dataSetSize*trainingPercentage)/100.0d);
        while(trainingIndices.size() < trainingDataSize)
        {
            int index = ThreadLocalRandom.current().nextInt(0,dataSetSize);
            trainingIndices.add(index);
        }

        for(int i = 0; i < dataSetSize; i++)
        {
            if(trainingIndices.contains(i))
            {
                String[] line = lines.get(i).split(",");
                double[]  row = new double[noOfFeatures+1];
                for(int k=0;k<line.length;k++)  //for converting to double
                {
                    if (classes.containsKey(line[k]))
                        row[k] = classes.get(line[k]);
                    else
                        row[k] = Double.parseDouble(line[k]);

                }
                trainingData.add(row);
            }
            else
            {
                String[] line = lines.get(i).split(",");
                double[]  row = new double[noOfFeatures+1];
                for(int k=0;k<line.length;k++)  //for converting to double
                {
                    if (classes.containsKey(line[k]))
                        row[k] = classes.get(line[k]);
                    else
                        row[k] = Double.parseDouble(line[k]);

                }
                testData.add(row);
            }
        }
    }



    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public double getTrainingPercentage() {
        return trainingPercentage;
    }

    public void setTrainingPercentage(double trainingPercentage) {
        this.trainingPercentage = trainingPercentage;
    }

    public void setTestData(List<double[]> testData) {
        this.testData = testData;
    }

    public Map<String, Double> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, Double> classes) {
        this.classes = classes;
    }

    public List<double[]> getTrainingData() {
        return trainingData;
    }

    public void setTrainingData(List<double[]> trainingData) {
        this.trainingData = trainingData;
    }

    public List<double[]> getTestData() {
        return testData;
    }

    public int getNoOfFeatures() {
        return noOfFeatures;
    }

    public void setNoOfFeatures(int noOfFeatures) {
        this.noOfFeatures = noOfFeatures;
    }
}

