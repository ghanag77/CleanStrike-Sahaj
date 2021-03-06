import model.CarromBoard;
import model.Player;
import model.PlayerMoves;

import java.util.Objects;
import java.util.Scanner;

public class ApplicationMain {
    public static void main(String[] args) {

        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Player winner;
        CarromBoard carromBoard = new CarromBoard(player1, player2, true);
        PlayerMoves playerMoves = new PlayerMoves(player1,player2,carromBoard);
        int caseType = -1;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out
                    .println(carromBoard.getStriker().getName() + " Choose an outcome from the list below");
            System.out.println("1. STRIKE");
            System.out.println("2. Multistrike");
            System.out.println("3. Red strike");
            System.out.println("4. Striker strike");
            System.out.println("5. Defunct coin");
            System.out.println("6. None");
            System.out.println("7. Exit");
            caseType = scanner.nextInt();
            playerMoves.play(caseType);
            winner = carromBoard.getWinner();
            if (Objects.nonNull(winner)) {
                caseType = 7;
            }
            System.out.println(playerMoves.printScore());
        } while (!(carromBoard.isGameOver() || caseType == 7));

        System.out.println(playerMoves.printScore());
    }
}

