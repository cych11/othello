package ca.utoronto.utm.assignment1.othello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * PlayerHuman makes a move that is inputted by the user if and only the move is
 * valid.
 * 
 * @author arnold
 *
 */
public class PlayerHuman {
	// instantiate error messages to inform the user that if an error happened,
	// the user knows what was the cause
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	private static final String IO_ERROR_MESSAGE = "I/O Error";
	// create a new BufferedReader object to read the input that is entered by the user
	private static final BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	private final Othello othello;
	private final char player;

	/**
	 * Constructs a PlayerHuman object with a reference to the current Othello game
	 * and the character representing the player ('X' or 'O').
	 *
	 * @param othello the Othello game instance
	 * @param player  the character representing the player ('X' or 'O')
	 */
	public PlayerHuman(Othello othello, char player) {
		this.othello = othello;
		this.player = player;
	}

	/**
	 * Prompts the player to input move (row and column) and returns the move.
	 * The method will only return a move when the output is within the
	 * game board and is a valid move.
	 *
	 * @return a valid Move object representing the player's move
	 */
	public Move getMove() {
		// receive the inputs from the user for row and col
		int row = getMove("row: ");
		int col = getMove("col: ");
		// if the move is invalid, prompt the user to enter a different coordinate
		while (!othello.move(row, col)){
			System.out.println("Invalid Move, enter a different coordinate");
			row = getMove("row: ");
			col = getMove("col: ");
		}
		// return a new Move object containing the coordinates inputted by the user
		return new Move(row, col);
	}

	/**
	 * Prompts the player for an input and once it is confirmed within
	 * the bounds of the game board, it gets returned.
	 *
	 * @param message the message to prompt the user (ex., "row: " or "col: ")
	 * @return the input entered by the user, or -1 if an error occurred
	 */
	private int getMove(String message) {
		// instantiate the row/col that will get entered by the user,
		// and the lower and upper bound of the board
		int move, lower = 0, upper = 7;
		while (true) {
			try {
				// print the message that informs the user whether they are
				// entering a value for the row or column
				System.out.print(message);
				// convert the input to a string
				String line = PlayerHuman.stdin.readLine();
				// convert the string into an integer, if the input is not
				// able to be converted to an integer, an exception
				// will be caught, printing an INVALID_INPUT_MESSAGE error
				move = Integer.parseInt(line);
				if (lower <= move && move <= upper) {
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (IOException e) {
				// occurs when an input or output error occurred
				System.out.println(INVALID_INPUT_MESSAGE);
				break;
			} catch (NumberFormatException e) {
				// occurs when the string cannot be converted to an integer
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
		return -1;
	}
}
