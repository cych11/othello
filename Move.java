package ca.utoronto.utm.assignment1.othello;
/**
 * Represents a move in the game, defined by a row and column that depicts the
 * coordinates of where the piece will be played. This class stores the position
 * on the game board and provides methods that can access to the values row
 * and column. It also provides a string representation of the move.
 * 
 * @author arnold
 *
 */
public class Move {
	private final int row, col;

	/**
	 * Constructs a Move with the specified row and column.
	 *
	 * @param row row index of the move
	 * @param col column index of the move
	 */
	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Gets the row index of this move.
	 *
	 * @return row index
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the column index of this move.
	 *
	 * @return column index
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Returns a string representation of the move expressed by "(row, col)"
	 *
	 * @return string representation of the move
	 */
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
}
