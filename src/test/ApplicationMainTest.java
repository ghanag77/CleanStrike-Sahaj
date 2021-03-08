import model.CarromBoard;
import model.Player;
import model.PlayerMoves;
import model.PlayerMovesTest;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ApplicationMainTest {

    Player player1 = new Player("player1");
    Player player2 = new Player("player2");
    CarromBoard carromBoard = new CarromBoard(player1, player2, true);
    PlayerMoves playerMoves = new PlayerMoves(player1,player2,carromBoard);

    PlayerMovesTest playerMovesTest = new PlayerMovesTest();

    private InputStream getInputStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

    @Test
    public void testCommandLine_Strike() {
        String input = "1 7";
        ApplicationMain.play(getInputStream(input));
        playerMovesTest.testStrike();
    }

    @Test
    public void testCommandLine_RedStrike() {
        String input = "3 7";
        ApplicationMain.play(getInputStream(input));
        playerMovesTest.testRedStrike();
    }

    @Test
    public void testCommandLine_MultiStrike() {
        String input = "2 7";
        ApplicationMain.play(getInputStream(input));
        playerMovesTest.testMultiStrike();
    }

    @Test
    public void testCommandLine_StrikerStrike() {
        String input = "4 7";
        ApplicationMain.play(getInputStream(input));
        playerMovesTest.assertScore(-1, 0);
        assertEquals(playerMovesTest.TOTAL_COINS, playerMoves.getTotalCoins());
    }

    @Test
    public void testCommandLine_DefunctStrike() {
        String input = "5 7";
        ApplicationMain.play(getInputStream(input));
        playerMovesTest.assertScore(-2, 0);
        assertEquals(playerMovesTest.TOTAL_COINS - 1, playerMoves.getTotalCoins());
    }

    @Test
    public void testCommandLine_NoStrike() {
        String input = "6 7";
        ApplicationMain.play(getInputStream(input));
        playerMovesTest.assertScore(0, 0);
        assertEquals(playerMovesTest.TOTAL_COINS, playerMoves.getTotalCoins());
    }

    @Test
    public void testCommandLine_Winner() {
        String input = "1 6 1 6 1 6 3 6 7";
        ApplicationMain.play(getInputStream(input));
        playerMovesTest.assertScore(6, 0);
        assertEquals(playerMovesTest.TOTAL_COINS -4, playerMoves.getTotalCoins());
    }
}

