package model;

import static model.CleanStrikeGame.NO_OF_COINS;
import static model.CleanStrikeGame.NO_OF_RED_COINS;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.IntStream;

public class PlayerMovesTest {

    static final String PLAYER_1_NAME = "Player1";
    static final String PLAYER_2_NAME = "Player2";
    static final Player player1 = new Player(PLAYER_1_NAME);
    static final Player player2 = new Player(PLAYER_2_NAME);
    public static final int TOTAL_COINS = NO_OF_COINS + NO_OF_RED_COINS;
    private PlayerMoves playerMoves;
    private CleanStrikeGame cleanStrikeGame;

    public PlayerMovesTest() {
        try {
            setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void assertEqualsPlayer1(Player actualPlayer) {
        Assert.assertEquals(PLAYER_1_NAME, actualPlayer.getName());
    }

    public static void assertEqualsPlayer2(Player actualPlayer) {
        Assert.assertEquals(PLAYER_2_NAME, actualPlayer.getName());
    }

    public void assertScore(int player1Score, int player2Score) {
        Assert.assertEquals(player1Score, playerMoves.getPlayer1().getScore());
        Assert.assertEquals(player2Score, playerMoves.getPlayer2().getScore());
    }

    private void makeStrikes(int times, StrikeType strikeType) {
        IntStream.range(0, times).forEach(i -> playerMoves.strikes(strikeType));
    }

    @Before
    public void setUp() throws Exception {
        cleanStrikeGame = new CleanStrikeGame(player1, player2, true);
        playerMoves = new PlayerMoves(player1, player2, cleanStrikeGame);
    }

    @Test
    public void test_Strike() {
        playerMoves.strikes(StrikeType.STRIKE);
        testStrike();
    }

    public void testStrike() {
        assertEqualsPlayer1(playerMoves.getCurrentPlayer());
        assertScore(1, 0);
        Assert.assertEquals(TOTAL_COINS - 1, playerMoves.getTotalCoins());
    }

    @Test
    public void test_redStrike() {
        playerMoves.strikes(StrikeType.RED_STRIKE);
        testRedStrike();
    }

    public void testRedStrike() {
        assertEqualsPlayer1(playerMoves.getCurrentPlayer());
        assertScore(3, 0);
        Assert.assertEquals(TOTAL_COINS - 2, playerMoves.getTotalCoins());
    }

    @Test
    public void test_Strike_afterRed() {
        playerMoves.strikes(StrikeType.STRIKE);
        assertEqualsPlayer1(playerMoves.getCurrentPlayer());
        assertScore(2, 3);
        Assert.assertEquals(TOTAL_COINS - 1, playerMoves.getTotalCoins());
    }

    @Test
    public void test_NoStrike() {
        playerMoves.strikes(StrikeType.NO_STRIKE);
        assertEqualsPlayer2(playerMoves.getCurrentPlayer());
        assertScore(2, 4);
        Assert.assertEquals(TOTAL_COINS, playerMoves.getTotalCoins());
    }

    @Test
    public void test_Consecutive3NoStrike_player1() {
        makeStrikes(6, StrikeType.NO_STRIKE);
        assertEqualsPlayer1(playerMoves.getCurrentPlayer());
        assertScore(-1, 0);
        Assert.assertEquals(TOTAL_COINS, playerMoves.getTotalCoins());
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
        Assert.assertEquals(TOTAL_COINS - 2, playerMoves.getTotalCoins());
    }

    @Test
    public void test_defunct_player1() {
        playerMoves.strikes(StrikeType.DEFUNCT_COIN);
        assertEqualsPlayer2(playerMoves.getCurrentPlayer());
        Assert.assertEquals(TOTAL_COINS - 1, playerMoves.getTotalCoins());
    }

    @Test
    public void test_multiStrike_player1() {
        playerMoves.strikes(StrikeType.MULTI_STRIKE);
        testMultiStrike();
    }

    public void testMultiStrike() {
        assertEqualsPlayer2(playerMoves.getCurrentPlayer());
        assertScore(2, 0);
        Assert.assertEquals(TOTAL_COINS, playerMoves.getTotalCoins());
    }


    private InputStream getInputStream(String input) {
        return new ByteArrayInputStream(input.getBytes());
    }

}