package maturana.mat.KenKen;

import java.util.HashSet;
import java.util.Set;

public class Kenken {

	private int dimension;
	private Set<Cage> cages;
	
	public Kenken(int dimension, Set<Cage> cages) {
		this.dimension = dimension;
		this.cages = new HashSet<>(cages);
	}
	
	public boolean solved(int[][] board) {
		if (!checkLegalValues(board))
			return false;
		for (int i = 0; i < board.length; i++)
			if (!checkCol(board, i) || !checkRow(board, i))
				return false;
		
		return cages.stream().allMatch((cage) -> cage.isSatisfied(board));
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public Set<Cage> getCages() {
		return new HashSet<>(cages);
	}
	
	
	/**
	 * Validates that the specified column in the given board object has distinct values
	 */
	private boolean checkCol(int[][] board, int col) {
		for (int row = 0; row < board.length; row++) {
			for (int nextRow = row + 1; nextRow < board.length; nextRow++) {
				if (board[row][col] == board[nextRow][col]) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Validates that the specified row in the given board object has distinct values
	 */
	private boolean checkRow(int[][] board, int row) {
		for (int col = 0; col < board[0].length; col++) {
			for (int nextCol = col + 1; nextCol < board[0].length; nextCol++) {
				if (board[row][col] == board[row][nextCol]) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Validates that for each int i in the given board object, 1 <= i <= dimension
	 */
	private boolean checkLegalValues(int[][] board) {
		for (int[] row : board) {
			for (int num : row) {
				if (num < 1 || num > dimension) {
					return false;
				}
			}
		}
		return true;
	}
	
	
}


