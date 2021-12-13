package domain;

import bootstrap.Trainer;
import util.InferenceUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InferenceEngine {
    public static Logger logger = LoggerFactory.getLogger(InferenceEngine.class);
    private int numberOfClasses;
    private Map<String,Double > classes;
    private Model[] models;

    public InferenceEngine(int numberOfClasses)
    {

        this.numberOfClasses = numberOfClasses;
        models = new Model[numberOfClasses];
        this.classes= new HashMap<String,Double >();
    }

    public void prepareModels() throws IOException
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter filename/filePath");
        String filename = sc.nextLine();

        System.out.println("Enter number of features");
        int noOfFeatures = sc.nextInt();

        System.out.println("Enter training percentage");
        double trainingPercentage = sc.nextDouble();

        System.out.println("Enter number of classes");
        int noOfClasses = sc.nextInt();
        for(int z=0 ; z < noOfClasses ; z++)
        {
            Scanner sc1 = new Scanner(System.in);
            System.out.println("Enter type "+(z+1));
            String type = sc1.nextLine();
            double typeId = z;
            classes.put(type,typeId);
        }

        Trainer trainer = new Trainer(filename, noOfFeatures,trainingPercentage,classes);
        trainer.computeDatasets();

        List<double[]> trainingData = trainer.getTrainingData();
        List<double[]> testData = trainer.getTestData();

        for(int a = 0; a<numberOfClasses; a++)
        {
            models[a] = new Model(noOfFeatures);
        }

        for(int i = 0 ; i< trainingData.size(); i++)
        {
            for(int j = 0 ; j<trainingData.get(i).length-1; j++)
            {
                double[] row = trainingData.get(i);
                int classIdNo = row.length-1;
                double classId = row[classIdNo];
                models[(int)classId].features.get(j).add(row[j]);
            }
        }

        for(int b = 0; b<numberOfClasses; b++)
        {
            models[b].getStats();
        }
        System.out.println("##################Testing##################");
        logger.info("##################Testing##################");
        test(testData);
        logger.info("Ending Expectation Maximization");

    }
    public void test(List<double[]> testData)
    {
        double accuracy = 0.0d;
        for(int i = 0 ; i< testData.size(); i++)
        {
            InferenceUtil inferenceUtil = new InferenceUtil(models ,testData.get(i));
            accuracy += inferenceUtil.computeProbDist(classes);
        }
        System.out.println("Accuracy is "+(accuracy*100/testData.size()));
        logger.info("Accuracy is "+(accuracy*100/testData.size()));
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(int numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public Map<String, Double> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, Double> classes) {
        this.classes = classes;
    }

    public Model[] getModels() {
        return models;
    }

    public void setModels(Model[] models) {
        this.models = models;
    }
}
