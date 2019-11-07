package maturana.mat.KenKen;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//import maturana.mat.KenKen.Kenken.Position;

public class Cage {

	private Set<Position> positions;
	private Operation operation;
	private int target;
	
	public Cage(Set<Position> positions, Operation operation, int target) {
		this.positions = new HashSet<>(positions);
		this.operation = operation;
		this.target = target;
	}

	/**
	 * Determines the given board object satisfies this cage's constraints
	 */
	public boolean isSatisfied(int[][] board) {
		return positions.stream()
			.map(position -> board[position.getRow()][position.getCol()])
			.sorted((num1, num2) -> num2 - num1)
			.mapToInt(Integer::intValue)
			.reduce(operation)
			.getAsInt() == target;
	}
	
	public Set<Position> getPositions() {
		return new HashSet<>(positions);
	}

	public Operation getOperation() {
		return operation;
	}

	public int getTarget() {
		return target;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((positions == null) ? 0 : positions.hashCode());
		result = prime * result + target;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cage other = (Cage) obj;
		if (operation != other.operation)
			return false;
		if (positions == null) {
			if (other.positions != null)
				return false;
		} else if (!positions.equals(other.positions))
			return false;
		if (target != other.target)
			return false;
		return true;
	}
	
	@Override
	public String toString(){
	    String s = " ";
	    for(Position p: getPositions()){
	        s = s + p.toString() + " ";
	    }
	    return "Position Coordinates: " + s + " Operation: " + getOperation() + " Target Number: " + getTarget();
	}

	public static void main(String[] args) {
		int[][] kenken = {
			{ 3, 1, 4, 2},
			{ 1, 2, 3, 4},
			{ 4, 3, 2, 1},
			{ 2, 4, 1, 3}
		};
		
		Cage cage = new Cage(new HashSet<>(Arrays.asList(Position.get(2, 1), Position.get(3, 1))), Operation.SUBTRACTION, 1);
		System.out.println(cage.isSatisfied(kenken));
		
	}
	
}