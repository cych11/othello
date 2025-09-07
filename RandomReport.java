package ca.utoronto.utm.assignment1.othello;
import java.util.Random;

public class RandomReport {
    public static void main(String[] args) {
        // let X be the event where P1/X wins a game of Othello
        // let O be the event where P2/O wins a game of Othello
        // let T be the event where there's a tie/no winner
        // after 100000 simulated games, P(X), P(O), P(T) was
        // approximately 45.543% and 50.241%, and 0.04216% respectively
        // for this experiment lets say that
        // P(X) = 0.45
        // P(O) = 0.5
        // P(T) = 0.05
        // in this block of code, we simulate 100 games played
        // 500 times, meaning 50000 total games are being
        // simulated. the reason why we break up the games
        // into 500 game increments is so that we can see
        // how many times in a 500-game span that P1 wins
        // more than P2, then get a large sample space to get
        // a rough estimate of how often it happens
        Random rand = new Random();
        int p1Wins = 0, p2Wins = 0, p1WinsMore = 0;
        for (int x=0;x<100;x++){
            for (int y=0;y<500;y++) {
                int random = rand.nextInt(100);
                if (random >= 50 && random < 95) {p1Wins++;}
                else {p2Wins++;}
            }
            if (p1Wins >= p2Wins){p1WinsMore++;}
            p1Wins = 0;
            p2Wins = 0;
        }
        float p = (float) p1WinsMore / 100;
        if (p == 0){System.out.println("in 100 simulations of 500 games played, P1 never won more games than P2");}
        else {System.out.println("probability that P1 has greater than or equal wins to P2: "+p);}
        // often the value of p will be less than 0.05, meaning that the null hypothesis
        // should be rejected as the chances of P(X) >= P(O) are very unlikely
    }
}
