package maturana.mat.KenKen;

public class Board {

	int dimension;
	int[][] board;

	public Board(int c) {
		dimension = c;
		board = new int[c][c];
		getSquare(board);
	}
 
	public void getSquare(int[][] board) {
		for (int a = 0; a < dimension; a++) {
			for (int b = 0; b < dimension; b++) {
				while (board[a][b] == 0) {
				getValue(a, b);
				}
				System.out.println("Row: " + (a + 1) + " Col: " + (b + 1) + " Value: " + board[a][b]);
			}
		}
		
	}

	public boolean checkRow(int a, int row, int col) {
		for (int b = 0; b < dimension; b++) {
			if (b != col) {
			if (a == board[row][b])
				return false;
			}

		}
		return true;
	}

	public boolean checkCol(int a, int row, int col) {
		for (int b = 0; b < dimension; b++) {
			if (b != row) {
			if (a == board[b][col])
				return false;
			}
		}
		return true;
	}

	public boolean checkValue(int a, int row, int col) {
		return (checkRow(a, row, col) && checkCol(a, row, col));
	}

	public boolean checkAll(int row, int col) {
		for (int a = 1; a <= dimension; a++) {
			if (checkValue(a, row, col) == true) {
				return true;
			}
		}
		return false;
	}
	
	public void printBoard(){
		for (int a = 0; a < dimension; a++) {
			for (int b = 0; b < dimension; b++) {
				System.out.print(board[a][b] + " " );
			}
			System.out.println();
		}
	}	
	
	//returns true if there is a 0 in the array
	public boolean checkZeros() {
		int count = 0;
		for (int a = 0; a < dimension; a++) {
			for (int b = 0; b < dimension; b++) {
				if (board[a][b] == 0)
					count++;
			}
		}
		return (count > 0);
	}
	
	public boolean checkSingle(int row, int col) {
		int count = 0; 
		for (int a = 1; a < dimension +1; a++) {
			if (checkValue(a, row, col) == true) {
				count++;
			}
		}
		return (count == 1); 
	}
	
	public void getValue(int row, int col){
		if (checkAll(row, col) == true) {
			board[row][col] = (int)(Math.random()*dimension +1);
			while (checkValue(board[row][col], row, col) == false){
				if (board[row][col] == dimension) {
					board[row][col] = 1;
				}
				else {
					board[row][col]++;
				}
			}			
		}
		else{
			backtrace(row, col -1);
		}	
	    //System.out.println("Row: " + (row + 1) + " Col: " + (col + 1) + " Value: " + board[row][col]);
	}
	
	//try changing to boolean soon
	public void backtrace(int row, int col) {
			board[row][col] = 0;
			if (checkSingle(row, col) == true) {
				backtrace(row, col -1);
			}
			if (board[row][col] == dimension) {
				board[row][col] = 1;
			}
			else {
				board[row][col]++;
			}
			while (checkValue(board[row][col], row, col) == false){
				if (board[row][col] == dimension) {
					board[row][col] = 1;
				}
				else {
					board[row][col]++;
				}
			}
			//System.out.println("Row: " + (row + 1) + " Col: " + (col + 1) + " Value: " + board[row][col] + " BACKTRACE");
	}

}
