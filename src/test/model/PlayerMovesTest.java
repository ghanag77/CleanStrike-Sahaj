package model;

import static model.CarromBoard.NO_OF_COINS;
import static model.CarromBoard.NO_OF_RED_COINS;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;

public class PlayerMovesTest {

    static final String PLAYER_1_NAME = "Player1";
    static final String PLAYER_2_NAME = "Player2";
    static final Player player1 = new Player(PLAYER_1_NAME);
    static final Player player2 = new Player(PLAYER_2_NAME);
    public static final int TOTAL_COINS = NO_OF_COINS + NO_OF_RED_COINS;
    private PlayerMoves playerMoves;
    private CarromBoard carromBoard;


    public static void assertEqualsPlayer1(Player actualPlayer) {
        assertEquals(PLAYER_1_NAME, actualPlayer.getName());
    }

    public static void assertEqualsPlayer2(Player actualPlayer) {
        assertEquals(PLAYER_2_NAME, actualPlayer.getName());
    }

    public void assertScore(int player1Score, int player2Score) {
        assertEquals(player1Score, playerMoves.getPlayer1().getScore());
        assertEquals(player2Score, playerMoves.getPlayer2().getScore());
    }

    private void makeStrikes(int times, StrikeType strikeType) {
        IntStream.range(0, times).forEach(i -> playerMoves.strikes(strikeType));
    }

    @Before
    public void setUp() throws Exception {
        playerMoves = new PlayerMoves(player1, player2,carromBoard);
    }

    @Test
    public void test_Strike() {
        playerMoves.strikes(StrikeType.STRIKE);
        testStrike();
    }

    public void testStrike() {
        assertEqualsPlayer2(playerMoves.getCurrentPlayer());
        assertScore(1, 0);
        assertEquals(TOTAL_COINS - 1, playerMoves.getTotalCoins());
    }

    @Test
    public void test_redStrike() {
        playerMoves.strikes(StrikeType.RED_STRIKE);
        testRedStrike();
    }

    public void testRedStrike() {
        assertEqualsPlayer1(playerMoves.getCurrentPlayer());
        assertScore(3, 0);
        assertEquals(TOTAL_COINS - 1, playerMoves.getTotalCoins());
    }

    @Test
    public void test_Strike_afterRed() {
        playerMoves.strikes(StrikeType.RED_STRIKE);
        playerMoves.strikes(StrikeType.STRIKE);
        assertEqualsPlayer2(playerMoves.getCurrentPlayer());
        assertScore(4, 0);
        assertEquals(TOTAL_COINS - 1, playerMoves.getTotalCoins());
    }

    @Test
    public void test_NoStrike() {
        playerMoves.strikes(StrikeType.NO_STRIKE);
        assertEqualsPlayer2(playerMoves.getCurrentPlayer());
        assertScore(0, 0);
        assertEquals(TOTAL_COINS, playerMoves.getTotalCoins());
    }

    @Test
    public void test_Consecutive3NoStrike_player1() {
        makeStrikes(6, StrikeType.NO_STRIKE);
        assertEqualsPlayer1(playerMoves.getCurrentPlayer());
        assertScore(-1, 0);
        assertEquals(TOTAL_COINS, playerMoves.getTotalCoins());
    }

    @Test
    public void test_foul_player1() {
        makeStrikes(6, StrikeType.NO_STRIKE);
        playerMoves.strikes(StrikeType.STRIKER_STRIKE);
        playerMoves.strikes(StrikeType.STRIKE);
        playerMoves.strikes(StrikeType.STRIKER_STRIKE);
        playerMoves.strikes(StrikeType.STRIKE);
        assertEqualsPlayer1(playerMoves.getCurrentPlayer());
        assertScore(-4, 1);
        assertEquals(TOTAL_COINS - 2, playerMoves.getTotalCoins());
    }


    @Test
    public void test_defunct_player1() {
        playerMoves.strikes(StrikeType.DEFUNCT_COIN);
        assertEqualsPlayer2(playerMoves.getCurrentPlayer());
        assertScore(-2, 0);
        assertEquals(TOTAL_COINS - 1, playerMoves.getTotalCoins());
    }

    @Test
    public void test_multiStrike_player1() {
        playerMoves.strikes(StrikeType.MULTI_STRIKE);
        testMultiStrike();
    }

    public void testMultiStrike() {
        assertEqualsPlayer2(playerMoves.getCurrentPlayer());
        assertScore(2, 0);
        assertEquals(TOTAL_COINS, playerMoves.getTotalCoins());
    }

    @Test
    public void test_printScore() {
        playerMoves.strikes(StrikeType.MULTI_STRIKE);
        playerMoves.strikes(StrikeType.MULTI_STRIKE);
        assertEquals(playerMoves.getPlayer1().getName() + " : " + playerMoves.getPlayer1().getScore()
                        + " " + playerMoves.getPlayer2().getName() + " : " + playerMoves.getPlayer2().getScore(),
                playerMoves.printScore());
    }

    private InputStream getInputStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

}