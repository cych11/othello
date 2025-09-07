package ca.utoronto.utm.assignment1.othello;

/**
 * This controller uses the Model classes to allow the Human player P1 to play
 * the computer P2. The computer, P2 uses a random strategy. 
 * 
 * @author arnold
 *
 */
public class OthelloControllerHumanVSRandom extends OthelloControllerHumanVSHuman{
	protected Othello othello;
	private final PlayerHuman player1;
	private final PlayerRandom player2;

	/**
	 * Constructs a new OthelloController with a new Othello game, ready to play
	 * with one user at the console versus a greedy computer.
	 */
	public OthelloControllerHumanVSRandom() {
		this.othello = new Othello();
		this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
		this.player2 = new PlayerRandom(this.othello, OthelloBoard.P2);
	}

	/**
	 * Begin a game of Othello with a human and a computer that uses
	 * its move randomly. This function calls the getMove() function
	 * which is taken from the various player classes which have
	 * different implementations of getMove().
	 */
	public void play() {
		while (!othello.isGameOver()) {
			this.report();
			Move move = null;
			char whosTurn = othello.getWhosTurn();
			// get the move from either player depending on the value
			// of whosTurn
			if (whosTurn == OthelloBoard.P1) {move = player1.getMove();}
			if (whosTurn == OthelloBoard.P2) {move = player2.getMove();}
			if (move != null) {
				this.reportMove(whosTurn, move);
				othello.move(move.getRow(), move.getCol());
			}
		}
		System.out.println(this.othello.getBoardString());
		this.reportFinal();
	}

	private void report() {
		String s = othello.getBoardString() + OthelloBoard.P1 + ":"
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
				+ othello.getWhosTurn() + " moves next";
		System.out.println(s);
	}

	/**
	 * Run main to play a Human (P1) against the computer P2. 
	 * The computer uses a random strategy, that is, it randomly picks 
	 * one of its possible moves.
	 * The output should be almost identical to that of OthelloControllerHumanVSHuman.

	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloControllerHumanVSRandom oc = new OthelloControllerHumanVSRandom();
		oc.play(); // this should work
	}
}
