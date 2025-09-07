package ca.utoronto.utm.assignment1.othello;

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 * 
 * Othello makes use of the OthelloBoard.
 * 
 * @author arnold
 *
 */
public class OthelloBoard {
	
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;

	public OthelloBoard(int dim) {
		this.dim = dim;
		board = new char[this.dim][this.dim];
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.board[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
		this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
	}

	/**
	 *
	 * @return the dimension of the game board
	 */
	public int getDimension() {return this.dim;}

	/**
	 * 
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
	public static char otherPlayer(char player) {
		if (player == P1){return P2;}
		else if (player == P2){return P1;}
		return EMPTY;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
		if (validCoordinate(row, col)){
			return this.board[row][col];
		}
		return EMPTY;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether (row,col) is a position on the board. Example: (6,12) is not
	 *         a position on the board.
	 */
	private boolean validCoordinate(int row, int col) {
        return 0 <= row && row <= dim - 1 && 0 <= col && col <= dim - 1;
    }

	/**
	 * Check if there is an alternation of P1 next to P2, starting at (row,col) in
	 * direction (drow,dcol). That is, starting at (row,col) and heading in
	 * direction (drow,dcol), you encounter a sequence of at least one P1 followed
	 * by a P2, or at least one P2 followed by a P1. The board is not modified by
	 * this method. Why is this method important? If
	 * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
	 * assuming that square is EMPTY, is a valid move, resulting in a collection of
	 * P2 being flipped.
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
	 *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
	 *         alternation
	 */
	private char alternation(int row, int col, int drow, int dcol) {
		if((row < 0 || dim - 1 < row) || (col < 0 || dim - 1 < col) || this.board[row][col] == EMPTY){return EMPTY;}
		char other = this.board[row][col];
		char player = otherPlayer(other);
		int xdirection = row + drow;
		int ydirection = col + dcol;
		boolean alternate = false;
		// check if direction is out of bounds
		if (!validCoordinate(xdirection, ydirection) || (xdirection == row && ydirection == col)){
			return EMPTY;
		}
		while(validCoordinate(xdirection, ydirection)){
			if(this.board[xdirection][ydirection] == player){
				return player;
			}
			if(this.board[xdirection][ydirection] == EMPTY){
				if (!alternate) {
					return EMPTY;
				}
				return other;
			}
			xdirection += drow;
			ydirection += dcol;
		}
		// System.out.println("just empty");
		return EMPTY;
	}

	/**
	 * flip all other player tokens to player, starting at (row,col) in direction
	 * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
	 * result in a flip to OOOO
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   the row direction, in {-1,0,1}
	 * @param dcol   the col direction, in {-1,0,1}
	 * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               flip to.
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flip(int row, int col, int drow, int dcol, char player) {
		// local variable total keeps track of the tokens flipped, and xDirection and yDirection
		// are variables that get incremented in the direction of drow and dcol.
		// They are used to keep track of the direction that the function goes towards
		int total = 0, xDirection = row, yDirection = col;
		// if there is not alternation found or the tile at the given coordinates already
		// belong to the given player, return -1
		if(alternation(row, col, drow, dcol) == EMPTY || this.board[row][col] == player){return -1;}
		// increment xDirection and yDirection until either this.board[xDirection][yDirection] is either
		// out of bounds, or not the given player
		while(validCoordinate(xDirection, yDirection) && this.board[xDirection][yDirection] != player){
			// convert the token at coordinates (xDirection, yDirection) to the player
			this.board[xDirection][yDirection] = player;
			total += 1;
			xDirection += drow; // increment xDirection and yDirection in the direction of drow and dcol
			yDirection += dcol;
		}
		return total;
	}

	/**
	 * Return which player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1,P2,EMPTY
	 */
	private char hasMove(int row, int col, int drow, int dcol) {
		// return EMPTY if the coordinates (row, col) are out of bounds, it is already
		// occupied by either players, or if there is no alternation for either players
		char playerMove = alternation(row + drow, col + dcol, drow, dcol);
		if (!validCoordinate(row, col) || this.board[row][col] != EMPTY ||
				playerMove == EMPTY){return EMPTY;}
		// check if there is an alternation for both players, and return the char
		// value of the player that does have an alternation
		if(playerMove == P1){return P1;}
		return P2;
	}

	/**
	 *
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	public char hasMove() {
		// initialize boolean variables that keep track if either player
		// can move somewhere on the board
		boolean p1HasMove = false, p2HasMove = false;
		// iterate over every tile on the board
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				// if the spot at coordinates (i, j) are empty,
				// check if either player have a valid move at (i, j).
				// this is done using the helper method checkMoves,
				// which checks if the player can move in all 8
				// possible directions
				if(this.board[i][j] == EMPTY){
					p1HasMove = p1HasMove || checkMoves(i, j, P1);
					p2HasMove = p2HasMove || checkMoves(i, j, P2);
				}
				if(p1HasMove && p2HasMove){return BOTH;}
			}
		}
		if (p1HasMove) {return P1;}
		else if (p2HasMove) {return P2;}
		return EMPTY;
	}

	private boolean checkMoves(int row, int col, char player) {
		// iterate over all possible combinations of drow and dcol and
		// check if the player has a move in that direction
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (hasMove(row, col, i, j) == player) {return true;}
			}
		}
		return false;
	}

	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 *
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {
		// HINT: Use some of the above helper methods to get this methods
		// job done!!
		boolean moveDone = false;
		// iterate over all possible directions to see whether a move can
		// be made in that direction
		for(int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				// if a move is possible, flip all tokens in that direction
				// to the char value of the player
				if (hasMove(row, col, i, j) == player){
					int xDirection = row + i;
					int yDirection = col + j;
					int total = flip(xDirection, yDirection, i, j, player);
					if (total != -1){
						moveDone = true;
					}
				}
			}
		}
		// once moves have been iterated through in every direction,
		// if a move has been made, place the players token at the original
		// coordinates
		if (moveDone){this.board[row][col] = player;}
		return moveDone;
	}

	/**
	 *
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {
		int count = 0;
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				// iterate over every tile on the board and if it
				// belongs to the player, increment the count by 1
				if (this.board[row][col] == player){
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Helper function used for the copyBoard method. Set the
	 * desired coordinate to the player inputted
	 *
	 * @param row row index to set
	 * @param col col index to set
	 * @param player P1 or P2
	 */
	public void set(int row, int col, char player){
		this.board[row][col] = player;
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		/**
		 * See assignment web page for sample output.
		 */
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += this.board[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard. Output is on assignment page.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		OthelloBoard ob = new OthelloBoard(8);
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				ob.board[row][col] = P1;
			}
		}
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));

		// Should all be blank
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (row == 0 || col == 0) {
					ob.board[row][col] = P2;
				}
			}
		}
		System.out.println(ob.toString());

		// Should all be P2 (O) except drow=0,dcol=0
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		// Can't move to (4,4) since the square is not empty
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));

		ob.board[4][4] = EMPTY;
		ob.board[2][4] = EMPTY;

		System.out.println(ob.toString());

		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
			}
		}
		System.out.println("who has a move=" + ob.hasMove());
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
		System.out.println(ob.toString());
	}
}
