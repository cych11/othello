package ca.utoronto.utm.assignment1.othello;

/**
 * PlayerGreedy makes a move by considering all possible moves that the player
 * can make. Each move leaves the player with a total number of tokens.
 * getMove() returns the first move which maximizes the number of
 * tokens owned by this player. In case of a tie, between two moves,
 * (row1,column1) and (row2,column2) the one with the smallest row wins. In case
 * both moves have the same row, then the smaller column wins.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * See the examples supplied in the assignment handout.
 * 
 * @author arnold
 *
 */

public class PlayerGreedy {
	public static final int DIMENSION = 8; // This is a 8x8 game
	private final Othello othello;
	private final char player;

	/**
	 * Constructs a PlayerGreedy object with a reference to the current Othello game
	 * and the character representing the player ('X' or 'O').
	 *
	 * @param othello the Othello game instance
	 * @param player  the character representing the player ('X' or 'O')
	 */
	public PlayerGreedy(Othello othello, char player) {
		this.othello = othello;
		this.player = player;
	}

	/**
	 * This function considers all possible moves that could be made and
	 * returns the move that results in the most amount of tokens gained.
	 * Further explanation of the function is provided in the header of
	 * the class.
	 *
	 * @return a valid Move object representing the move that causes
	 * the most amount of tokens gained
	*/
	public Move getMove() {
		// keep track of coordinates of best move
		int bestMove = 0, bestMoveX = 0, bestMoveY = 0;
		// keep track of the # tokens player has before move
		int curr_pieces = othello.getCount(this.player);
		// copyBoard method is called to keep a replica of the board
		// before this player has moved.
		OthelloBoard boardCopy = copyBoard(othello.board);
		// iterate over all the tiles on the board
		for(int i = 0; i < DIMENSION; i++) {
			for(int j = 0; j < DIMENSION; j++) {
				// check if placing a tile at coordinates (i, j) is a valid move
				if (boardCopy.move(i, j, this.player)) {
					// find amount of tokens that now belong to the player
					int temp_pieces = boardCopy.getCount(this.player);
					// if the tokens gained is higher than all the previously
					// attempted moves, change variables
					if ((temp_pieces - curr_pieces) > bestMove){
						bestMove = temp_pieces - curr_pieces;
						bestMoveX = i;
						bestMoveY = j;
					}
				}
				// revert boardCopy to its original state, where no move has been made yet
				boardCopy = copyBoard(othello.board);
			}
		}
		return new Move(bestMoveX, bestMoveY);
	}

	/**
	 * This private function takes all the values inside one board
	 * and copies its values to another board.
	 *
	 * @param board board that will be copied
	 * @return a new copy of board
	 */
	private OthelloBoard copyBoard(OthelloBoard board){
		OthelloBoard boardCopy = new OthelloBoard(DIMENSION);
		// iterate over all tiles of the board to get values of original board,
		// set that value to boardCopy
		for(int i = 0; i < DIMENSION; i++) {
			for(int j = 0; j < DIMENSION; j++) {
				boardCopy.set(i, j, board.get(i, j));
			}
		}
		return boardCopy;
	}
}
