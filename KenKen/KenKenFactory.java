package maturana.mat.KenKen;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class KenKenFactory {
    private boolean[][] aCageHere;
    private int dimension;
    LatinSquare square;
    ModifiedKenKenDistribution distribution;
    double[] cumProb;
    public Kenken newRandomKenKen(int dimension, Difficulty difficulty) {
        this.dimension = dimension;
        aCageHere = new boolean[dimension][dimension];
        square = LatinSquare.generateNewSquare(dimension);
        Set<Cage> cages = new HashSet<>();
        Set<Position> positions;
        Operation operation;

        //SETTING THE DIFFICULTIES, FIRST VALUE IS THE MEAN SIZE OF THE DISTRIBUTION
        //STRETCH FACTOR CONTROLS THE VARIABILITY FROM THE MEAN(lower number = more stretch)
        if(difficulty == Difficulty.EASY) {
            distribution = new ModifiedKenKenDistribution(1.75, 1.5);
        }
        else if(difficulty == Difficulty.MEDIUM) {
            distribution = new ModifiedKenKenDistribution(2.5, 1.5);
        }
        else if (difficulty == Difficulty.HARD){
            distribution = new ModifiedKenKenDistribution(3.25, 1.5);
        }
        else{
            //INSANE!
            distribution = new ModifiedKenKenDistribution(5, 1.5);
        }
        //CREATING A CUMULATIVE PROBABILITY TABLE TO BE USED IN GENERATING CAGE SIZE
        cumProb = new double[25];
        cumProb[0] = distribution.getMappedProbability(1, dimension);
        for(int i = 1; i < cumProb.length; i++){
            cumProb[i] = distribution.getMappedProbability(i, dimension) + cumProb[i - 1];
        }
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(!aCageHere[i][j]){
                    Position p = Position.get(i,j);
                    positions = grow(p);
                    if(positions.size() > 2){
                        int rand = (int)(Math.random() * 2);
                            switch(rand){
                                case(0):
                                    operation = Operation.ADDITION;
                                    int sum = getSum(positions);
                                    cages.add(new Cage(positions, operation, sum));
                                    break;
                                case(1):
                                    operation = Operation.MULTIPLICATION;
                                    int product = getProduct(positions);
                                    if (product > 15000) {
                                    	sum = getSum(positions);
                                    	operation = Operation.ADDITION;
                                    	cages.add(new Cage(positions, operation, sum));
                                    }
                                    else {
                                    	cages.add(new Cage(positions, operation, product));
                                    }
                                    break;
                            }
                    }
                    else if(positions.size() == 1){
                        cages.add(new Cage(positions, null, square.getValue(i, j)));
                    }
                    else if(noRemainder(positions)){
                        int rand = (int)(Math.random() * 10);
                        switch(rand){
                            case(0):
                                operation = Operation.ADDITION;
                                int sum = getSum(positions);
                                cages.add(new Cage(positions, operation, sum));
                                break;
                            case(1):
                                operation = Operation.MULTIPLICATION;
                                int product = getProduct(positions);
                                cages.add(new Cage(positions, operation, product));
                                break;
                            case(2):
                                operation = Operation.SUBTRACTION;
                                int difference = getDifference(positions);
                                cages.add(new Cage(positions, operation, difference));
                                break;
                            default:
                                operation = Operation.DIVISION;
                                int quotient = getQuotient(positions);
                                cages.add(new Cage(positions, operation, quotient));
                                break;
                        }
                    }
                    else{
                        int rand = (int)(Math.random() * 9);
                        switch(rand){
                            case(0):
                                operation = Operation.ADDITION;
                                int sum = getSum(positions);
                                cages.add(new Cage(positions, operation, sum));
                                break;
                            case(1):
                                operation = Operation.MULTIPLICATION;
                                int product = getProduct(positions);
                                cages.add(new Cage(positions, operation, product));
                                break;
                            default:
                                operation = Operation.SUBTRACTION;
                                int difference = getDifference(positions);
                                cages.add(new Cage(positions, operation, difference));
                                break;
                        }
                    }
                }
            }
        }
        //optional printing stuff
        for(Cage c: cages){
            System.out.println(c);
        }
        //square.printGrid();
        return new Kenken(dimension, cages);
    }


    // "grows" a cage
    private Set<Position> grow(Position p1){
        Set<Position> positions = new HashSet<>();
        positions.add(p1);
        double a = Math.random();
        Position hold = p1;
        for(int i = 0; i <= determineCageSize(); i++) {
            if(hold != null) {
                    Position p2 = randomAdjacent(hold);
                    if(p2 != null) {
                        positions.add(p2);
                        hold = p2;
                    }
                }
            }
        for(Position p: positions){
            aCageHere[p.getRow()][p.getCol()] = true;
        }
        return positions;
    }

    //helper for grow
    private int determineCageSize(){
        double min = 1;
        double a = Math.random();
        int size = 1;
        for(int i = 0; i < 25; i ++){
            if(a-cumProb[i] < min && a-cumProb[i] > 0){
                min = a - cumProb[i];
                size = i + 1;
            }
        }
        return size;
    }

    //returns a random adjacent position - null if none exists
    private Position randomAdjacent(Position p){
        if(p == null){
            return null;
        }
        int x = p.getRow();
        int y = p.getCol();
        ArrayList<Position> possibles = new ArrayList<>();
        for(int i = x - 1; i < x + 2; i++){
            for(int j = y - 1; j < y + 2; j++){
                int validCheck = i + j - x - y;
                if(Math.abs(validCheck) == 1 && i >= 0 && j >= 0 && i < dimension && j < dimension && positionAvailable(i, j)){
                    possibles.add(Position.get(i, j));
                }
            }
        }
        if(possibles.size() == 0){
            return null;
        }
        int randNum = (int)(Math.random() * possibles.size());
        return possibles.get(randNum);
    }

    //helper in getting a random adjacent cage
    private boolean positionAvailable(int row, int col){
        return !aCageHere[row][col];
    }

    //Sum of a cage
    private int getSum(Set<Position> positions){
        int sum = 0;
        for(Position p: positions){
            sum += square.getValue(p.getRow(), p.getCol());
        }
        return sum;
    }

    //product of a cage
    private int getProduct(Set<Position> positions){
        int product = 1;
        for(Position p: positions){
            product *= square.getValue(p.getRow(), p.getCol());
        }
        return product;
    }

    //difference of cage size 2
    private int getDifference(Set<Position> positions){
        Position[] arr = new Position[2];
        arr = positions.toArray(arr);
        int a = square.getValue(arr[0].getRow(), arr[0].getCol());
        int b = square.getValue(arr[1].getRow(), arr[1].getCol());
        if(a>b){
            return a - b;
        }
        return b - a;
    }

    //quotient of cage size 2
    private int getQuotient(Set<Position> positions){
        Position[] arr = new Position[2];
        arr = positions.toArray(arr);
        int a = square.getValue(arr[0].getRow(), arr[0].getCol());
        int b = square.getValue(arr[1].getRow(), arr[1].getCol());
        if(a>b){
            return a/b;
        }
        return b/a;
    }
    //test if quotient is possible
    private boolean noRemainder(Set<Position> positions){
        Position[] arr = new Position[2];
        arr = positions.toArray(arr);
        int a = square.getValue(arr[0].getRow(), arr[0].getCol());
        int b = square.getValue(arr[1].getRow(), arr[1].getCol());
        if(a > b){
            return a % b == 0;
        }
        else{
            return b % a == 0;
        }
    }

    public static void main(String[] args) {
       new KenKenFactory().newRandomKenKen(9, Difficulty.HARD);
    }

}





