package maturana.mat.KenKen;


public class Position {
		
		private int row, col;

		// private constructor - retrieve position objects from static factory method get()
		private Position(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		public static Position get(int row, int col) {
			return new Position(row, col);
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + col;
			result = prime * result + row;
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
			Position other = (Position) obj;
			if (col != other.col)
				return false;
			if (row != other.row)
				return false;
			return true;
		}
		
		@Override
		public String toString(){
		    return "Row: " + getRow() + " Col: " + getCol();
		}
		
		
		
}