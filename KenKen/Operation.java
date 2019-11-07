package maturana.mat.KenKen;

import java.util.function.IntBinaryOperator;

public enum Operation implements IntBinaryOperator {
	
	ADDITION {
		@Override
		public int applyAsInt(int left, int right) {
			return left + right;
		}
	}, SUBTRACTION {
		@Override
		public int applyAsInt(int left, int right) {
			return left - right;
		}
	}, MULTIPLICATION {
		@Override
		public int applyAsInt(int left, int right) {
			return left * right;
		}
	}, DIVISION {
		@Override
		public int applyAsInt(int left, int right) {
			return left / right;
		}
	};

	@Override
	public abstract int applyAsInt(int left, int right);

}