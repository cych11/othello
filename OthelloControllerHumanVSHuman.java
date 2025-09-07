package ca.utoronto.utm.assignment1.othello;

/**
 * Run the main from this class to play two humans against each other. Only
 * minimal modifications to this class are permitted.
 * 
 * @author arnold
 *
 */
public class OthelloControllerHumanVSHuman {
	protected Othello othello;
	private final PlayerHuman player1, player2;

	/**
	 * Constructs a new OthelloController with a new Othello game, ready to play
	 * with two users at the console.
	 */
	public OthelloControllerHumanVSHuman() {
		this.othello = new Othello();
		this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
		this.player2 = new PlayerHuman(this.othello, OthelloBoard.P2);
	}

	/**
	 * Begin a game of Othello with 2 humans. This function calls the
	 * getMove() function  which is taken from the various player
	 * classes which have different implementations of getMove().
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
			this.reportMove(whosTurn, move);
			othello.move(move.getRow(), move.getCol());
		}
		this.reportFinal();
	}

	protected void reportMove(char whosTurn, Move move) {
		System.out.println(whosTurn + " makes move " + move + "\n");
	}

	private void report() {
		
		String s = othello.getBoardString() + OthelloBoard.P1 + ":"
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  " 
				+ othello.getWhosTurn() + " moves next";
		System.out.println(s);
	}

	protected void reportFinal() {
		
		String s = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) 
				+ "  " + othello.getWinner() + " won\n";
		System.out.println(s);
	}
	/**
	 * Run main to play two Humans against each other at the console.
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloControllerHumanVSHuman oc = new OthelloControllerHumanVSHuman();
		oc.play();
	}

}
