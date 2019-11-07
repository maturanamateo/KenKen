package maturana.mat.KenKen;

import java.util.ArrayList;
import java.util.Collections;
//Times can vary
//generated a 9x9 in .004 seconds
// generated a 100x100 in .253 seconds
//1000 x 1000 in 37.2 seconds
//Also less code than before 
public class LatinSquare {
    int dimension;
    private int[][] grid;
    LatinSquare(int d) {
        dimension = d;
        grid = new int[dimension][dimension];
        createGrid();
        for(int i = 0; i < dimension * dimension; i++) {
            swapRow();
            swapCol();
        }
    }
    void printGrid(){
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(grid[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private void createGrid(){
        //basic grid, no randomness
        for (int i = 1; i <= dimension; i++) {
            for (int j = 1; j <= dimension; j++) {
                grid[i - 1][j - 1] = (i + j) % dimension + 1;
            }
        }
    }
    void swapRow(){
        ArrayList<Integer> ints = new ArrayList<Integer>();
        for(int i = 0; i < dimension; i++){
            ints.add(i);
        }
        Collections.shuffle(ints);
        int fromRow = ints.get(0);
        int toRow = ints.get(1);
        for(int i = 0; i < dimension; i++){
            int hold = grid[fromRow][i];
            grid[fromRow][i] = grid[toRow][i];
            grid[toRow][i] = hold;
        }
    }
    void swapCol(){
        ArrayList<Integer> ints = new ArrayList<>();
        for(int i = 0; i < dimension; i++){
            ints.add(i);
        }
        Collections.shuffle(ints);
        int fromCol = ints.get(0);
        int toCol = ints.get(1);
        for(int i = 0; i < dimension; i++){
            int hold = grid[i][fromCol];
            grid[i][fromCol] = grid[i][toCol];
            grid[i][toCol] = hold;
        }
    }
    public int getValue(int row, int col){
        return grid[row][col];
    }
    public static LatinSquare generateNewSquare(int dimension){
        return new LatinSquare(dimension);

    }


}

