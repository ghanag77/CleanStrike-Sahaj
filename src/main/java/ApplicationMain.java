import model.CleanStrikeGame;
import model.Player;
import model.PlayerMoves;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class ApplicationMain {
    public static void main(String[] args) {
        play(System.in);
    }

    public static void play(InputStream inputStream) {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player winner;
        CleanStrikeGame cleanStrikeGame = new CleanStrikeGame(player1, player2, true);
        PlayerMoves playerMoves = new PlayerMoves(player1,player2, cleanStrikeGame);
        int caseType = -1;
        Scanner scanner = new Scanner(inputStream);

        do {
            System.out
                    .println(cleanStrikeGame.getStriker().getName() + " Choose an outcome from the list below");
            System.out.println("1. STRIKE");
            System.out.println("2. Multistrike");
            System.out.println("3. Red strike");
            System.out.println("4. Striker strike");
            System.out.println("5. Defunct coin");
            System.out.println("6. None");
            System.out.println("7. Exit");
            caseType = scanner.nextInt();
            playerMoves.play(caseType);
            winner = cleanStrikeGame.getWinner();
            if (Objects.nonNull(winner)) {
                System.out.println(cleanStrikeGame.printFinalResult());
                break;
            }
            System.out.println(cleanStrikeGame.printScore());
        } while (!(cleanStrikeGame.isGameOver() || caseType == 7));
    }
}

