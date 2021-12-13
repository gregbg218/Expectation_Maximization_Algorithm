package domain;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class Model {
    private int noOfFeatures;
    public List<List<Double>> features = new ArrayList<List<Double>>();
    private StatsSignature[] statsSignatures;

    public Model(int noOfFeatures)
    {
        this.noOfFeatures = noOfFeatures;
        statsSignatures = new StatsSignature[noOfFeatures];
        for(int i= 0 ; i<noOfFeatures; i++)
        {
            List<Double> feature = new ArrayList<Double>();
            features.add(feature);
        }
    }

    public void getStats()
    {
        for(int i= 0 ; i<noOfFeatures; i++)
        {
            statsSignatures[i] = computeStats(features.get(i));
        }
    }

    public static StatsSignature computeStats (List<Double> values)
    {
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
        for(double value : values)
        {
            doubleSummaryStatistics.accept(value);
        }

        StatsSignature statsSignature = new StatsSignature(doubleSummaryStatistics.getAverage(), getStdDev(values , doubleSummaryStatistics.getAverage()));
        return statsSignature;
    }

    public static double getStdDev(List<Double> values, double mean)
    {
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
        double sum=0.0d ;
        for(double value : values)
        {
            sum+= Math.pow((value - mean), 2.0d);
        }
        return Math.pow((sum / (double) values.size()), 0.5d);
    }

    public int getNoOfFeatures() {
        return noOfFeatures;
    }

    public void setNoOfFeatures(int noOfFeatures) {
        this.noOfFeatures = noOfFeatures;
    }

    public List<List<Double>> getFeatures() {
        return features;
    }

    public void setFeatures(List<List<Double>> features) {
        this.features = features;
    }

    public StatsSignature[] getStatsSignatures() {
        return statsSignatures;
    }

    public void setStatsSignatures(StatsSignature[] statsSignatures) {
        this.statsSignatures = statsSignatures;
    }
}
