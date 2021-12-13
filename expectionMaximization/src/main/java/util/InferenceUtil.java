package util;

import domain.Model;

import java.util.Map;


public class InferenceUtil {
    private Model[] models;
    private double[] input;

    public InferenceUtil(Model[] models,double[] input)
    {
        this.models = models;
        this.input = input;
    }
    public double computeProbDist(Map<String,Double > classes)
    {
        int classId = Integer.MIN_VALUE;
        double max = Double.MIN_VALUE,accuracy = 0.0d;
        String actual="",predicted="";

        for(int j = 0; j< models.length ; j++ )
        {
            double sum=0.0d;
            for(int i = 0; i< input.length-1 ; i++ )
            {
                double constant = 1.0d / (Math.sqrt(2 * Math.PI * (Math.pow((models[j].getStatsSignatures())[i].getStdDev() , 2))));
                double power = -0.5 * (Math.pow((input[i] - (models[j].getStatsSignatures())[i].getMean())/(models[j].getStatsSignatures())[i].getStdDev(),2));
                double prob = constant * Math.exp(power);
                sum+=prob;
            }
            if(sum>max)
            {
                max = sum;
                classId = j;
            }
        }

        for (Map.Entry<String,Double > entry : classes.entrySet())
        {
            if (entry.getValue().equals(input[input.length-1])) {
                actual = entry.getKey();
                System.out.println("Actual : It is the type "+actual);
            }
        }

        for (Map.Entry<String,Double > entry : classes.entrySet())
        {
            if (entry.getValue().equals((double)classId)) {
                predicted = entry.getKey();
                System.out.println("Predicted : It is the type "+predicted);
            }
        }
        System.out.println();

        if(actual.equals(predicted))
        {
            accuracy = 1.0d;
        }

        return accuracy;
    }

}


