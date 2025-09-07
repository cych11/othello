package ca.utoronto.utm.assignment1.othello;

/**
 * The goal here is to print out the probability that Random wins and Greedy
 * wins as a result of playing 10000 games against each other with P1=Random and
 * P2=Greedy. What is your conclusion, which is the better strategy?
 * @author arnold
 *
 */
public class OthelloControllerRandomVSGreedy {
	protected Othello othello;
	private final PlayerRandom player1;
	private final PlayerGreedy player2;

	/**
	 * Constructs a new OthelloController with a new Othello game, ready to play
	 * with one user at the console versus a greedy computer.
	 */
	public OthelloControllerRandomVSGreedy() {
		this.othello = new Othello();
		this.player1 = new PlayerRandom(this.othello, OthelloBoard.P1);
		this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
	}

	/**
	 * Begin a game of Othello with a computer that uses a greedy strategy
	 * and one that picks its move randomly. This function calls the getMove()
	 * function which is taken from the various player classes which have
	 * different implementations of getMove().
	 *
	 * @return a character representing the winner of the game
	 */
	public char play() {
		while (!othello.isGameOver()) {
			Move move = null;
			char whosTurn = othello.getWhosTurn();
			// get the move from either player depending on the value
			// of whosTurn
			if (whosTurn == OthelloBoard.P1) {move = player1.getMove();}
			if (whosTurn == OthelloBoard.P2) {move = player2.getMove();}
			if (move != null) {othello.move(move.getRow(), move.getCol());}
		}
		return othello.getWinner();
	}

	/**
	 * Run main to execute the simulation and print out the two line results.
	 * Output looks like: 
	 * Probability P1 wins=.75 
	 * Probability P2 wins=.20
	 * @param args
	 */
	public static void main(String[] args) {
		int p1wins = 0, p2wins = 0, numGames = 10000;
		// iterate over 10000 games, keeping track of how many games either player won
		for(int x = 0; x < numGames; x++){
			OthelloControllerRandomVSGreedy oc = new OthelloControllerRandomVSGreedy();
			if (oc.play() == OthelloBoard.P1){p1wins++;}
			else if (oc.play() == OthelloBoard.P2){p2wins++;}
		}
		System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
		System.out.println("Probability P2 wins=" + (float) p2wins / numGames);
	}
}
