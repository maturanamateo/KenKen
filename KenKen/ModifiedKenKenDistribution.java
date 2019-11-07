package maturana.mat.KenKen;

public class ModifiedKenKenDistribution {
	//stretch factor makes the distribution wider/narrower to maniuplate parent distribution function to mirror
    //KenKen cage sizes - adjustable if necesary(lower value = more spread out cage sizes)
    double mean;
    double stretchFactor;
    public ModifiedKenKenDistribution(double mean, double stretchFactor){
        this.mean = mean;
        this.stretchFactor = stretchFactor;
    }
    //Method to get a desired cage size probability using given mean, stretch factor
    private double probability(int cageSize){
        double firstTerm = Math.exp(-cageSize * stretchFactor);
        double secondTerm = Math.pow(cageSize * stretchFactor, mean);
        double thirdTerm = stretchFactor;
        return (firstTerm * secondTerm * thirdTerm);
    }

    //returns sum of all unmapped(not scaled between 0 and 1) probabilities for a KenKen of dimension n
    private double getDistributionSum(int dimension){
        double sum = 0;
        for(int i = 1; i <= dimension; i++){
            sum += probability(i);
        }
        return sum;
    }
    //Probability of a cage being size 2 with KenKen 4x4 is returned by
    //getMappedProbability(2,4)- assumed difficulty setting
    public double getMappedProbability(int cageSize, int dimension){
        return probability(cageSize)/getDistributionSum(dimension);
    }

}
