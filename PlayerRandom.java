package ca.utoronto.utm.assignment1.othello;

import java.util.ArrayList;
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 * 
 * @author arnold
 *
 */
public class PlayerRandom {
	private final Random rand = new Random();
	public static final int DIMENSION = 8; // This is a 8x8 game
	private final Othello othello;
	private final char player;

	/**
	 * Constructs a PlayerRandom object with a reference to the current Othello game
	 * and the character representing the player ('X' or 'O').
	 *
	 * @param othello the Othello game instance
	 * @param player  the character representing the player ('X' or 'O')
	 */
	public PlayerRandom(Othello othello, char player) {
		this.othello = othello;
		this.player = player;
	}

	/**
	 * This function iterates over every possible move that could be made
	 * by the player, adding them to an ArrayList consisting of Move objects,
	 * then returns a random Move object inside the ArrayList.
	 *
	 * @return a valid Move object representing the player's move
	 */
	public Move getMove() {
		// create the ArrayList that will contain all the possible moves
		// this player can make
		ArrayList<Move> moves = new ArrayList<>();
		// copyBoard method is called to keep a replica of the board
		// before this player has moved. This is used so all the possible
		// moves can be simulated before finding the best move available
		OthelloBoard boardCopy = copyBoard(othello.board);
		// iterate over all the tiles on the board
		for(int i = 0; i < DIMENSION; i++) {
			for(int j = 0; j < DIMENSION; j++) {
				// check if placing a tile at coordinates (i, j) is a valid move
				// and if so, add it to the ArrayList
				if (boardCopy.move(i, j, this.player)) {
					moves.add(new Move(i, j));
				}
				// return boardCopy to its original state where this player has
				// not made a move yet
				boardCopy = copyBoard(othello.board);
			}
		}
		// return a new Move object from the ArrayList using a random index
		// that is within the bounds of the ArrayList's size.
		if (moves.isEmpty()){return null;}
		return moves.get(rand.nextInt(moves.size()));
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
		// iterate over all tiles of the board, getting the value of
		// the original board and setting it to the same coordinate
		// of the copy board
		for(int i = 0; i < DIMENSION; i++) {
			for(int j = 0; j < DIMENSION; j++) {
				boardCopy.set(i, j, board.get(i, j));
			}
		}
		return boardCopy;
	}
}
