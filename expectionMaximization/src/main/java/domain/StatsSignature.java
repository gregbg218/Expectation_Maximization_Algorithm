package domain;

public class StatsSignature {
    private double mean;
    private double stdDev;


    public StatsSignature(double mean , double stdDev)
    {
        this.mean = mean;
        this.stdDev = stdDev;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getStdDev() {
        return stdDev;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }


}
