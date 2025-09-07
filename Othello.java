package ca.utoronto.utm.assignment1.othello;

import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 * @author arnold
 *
 */
public class Othello {
	public static final int DIMENSION = 8; // This is an 8x8 game
	private char whosTurn = OthelloBoard.P1; // P1 moves first!
	private int numMoves = 0;
	protected final OthelloBoard board = new OthelloBoard(DIMENSION);

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		return whosTurn;
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row x coordinate of the move
	 * @param col y coordinate of the move
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
		char currPlayer = getWhosTurn();
		numMoves += 1;
		if (!board.move(row, col, currPlayer)) {return false;}
		// check which player just made a move, and if the other player
		// has a valid move on the board, change variable whosTurn
		if (currPlayer == OthelloBoard.P1){
			if (board.hasMove() == OthelloBoard.P2 || board.hasMove() == OthelloBoard.BOTH){
				whosTurn = OthelloBoard.P2;
			}
		}
		else {
			if (board.hasMove() == OthelloBoard.P1 || board.hasMove() == OthelloBoard.BOTH){
				whosTurn = OthelloBoard.P1;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		int count = 0;
		for(int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++) {
				if (board.get(i, j) == player){count++;}
			}
		}
		return count;
	}

	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
        if (isGameOver()) {
            int p1Tokens = getCount(OthelloBoard.P1);
            int p2Tokens = getCount(OthelloBoard.P2);
            if (p1Tokens > p2Tokens) {
                return OthelloBoard.P1;
            } else if (p1Tokens < p2Tokens) {
                return OthelloBoard.P2;
            }
        }
        return OthelloBoard.EMPTY;
    }

	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		int total_pieces = (getCount(OthelloBoard.P1)
				+ getCount(OthelloBoard.P2));
        return (total_pieces == DIMENSION * DIMENSION ||
				board.hasMove() == OthelloBoard.EMPTY ||
				getCount(OthelloBoard.P1) == 0 ||
				getCount(OthelloBoard.P2) == 0);
    }

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		StringBuilder s = new StringBuilder();

		// list out column indices
		s.append("  ");
		for (int col = 0; col < DIMENSION; col++) {s.append(col).append(" ");}
		s.append('\n');
		// separate lines
		s.append(" +");
        s.append("-+".repeat(DIMENSION));
		s.append('\n');
		// enter values of each tile
		for (int row = 0; row < DIMENSION; row++) {
			s.append(row).append("|");
			for (int col = 0; col < DIMENSION; col++) {
				s.append(board.get(row, col)).append("|");
			}
			s.append(row).append("\n");
			// separate each row
			s.append(" +");
            s.append("-+".repeat(DIMENSION));
			s.append('\n');
		}
		// list out column indices on bottom
		s.append("  ");
		for (int col = 0; col < DIMENSION; col++) {s.append(col).append(" ");}
		s.append('\n');

		return s.toString();
	}

	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Random rand = new Random();
		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if (o.move(row, col)) {
				System.out.println("makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}
		System.out.println(o.getCount(OthelloBoard.P1));
		System.out.println(o.getCount(OthelloBoard.P2));

	}
}