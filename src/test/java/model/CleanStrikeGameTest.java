package model;

import static model.CleanStrikeGame.NO_OF_COINS;
import static model.CleanStrikeGame.NO_OF_RED_COINS;


import exception.OutOfCoinException;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CleanStrikeGameTest {
    private CleanStrikeGame cleanStrikeGame;
    private PlayerMoves playerMoves;

    private static Player getPlayer(int strikeSize) {
        return getPlayer(strikeSize, StrikeType.STRIKE);
    }

    private static Player getPlayer(int strikeSize, StrikeType strikeType) {
        Player player = new Player();
        List<StrikeType> strikeTypes = player.getStrikes();
        IntStream.range(0, strikeSize).forEach(i -> strikeTypes.add(strikeType));
        return player;
    }

    private static void testCarramBoardNotNull(CleanStrikeGame cleanStrikeGame) {
        Assert.assertNotNull(cleanStrikeGame);
        Assert.assertNotNull(cleanStrikeGame.getPlayer1());
        Assert.assertNotNull(cleanStrikeGame.getPlayer2());
        Assert.assertNotNull(cleanStrikeGame.getStriker());
        Assert.assertEquals(NO_OF_COINS, cleanStrikeGame.getCoins());
        Assert.assertEquals(CleanStrikeGame.NO_OF_RED_COINS, cleanStrikeGame.getRedCoin());
    }

    private static void assertEqualPlayers(Player expectedPlayer, Player actualPlayer) {
        Assert.assertEquals(expectedPlayer.getName(), actualPlayer.getName());
    }

    private static void addStrike(CleanStrikeGame board, int strikeSize, StrikeType strikeType) {
        IntStream.range(0, strikeSize).forEach(i -> board.addStrike(strikeType));
    }

    @Before
    public void setUp() throws Exception {
        Player player1 = getPlayer(0);
        Player player2 = getPlayer(0);
        cleanStrikeGame = new CleanStrikeGame(player1, player2, true);
        playerMoves = new PlayerMoves(player1, player2,cleanStrikeGame);
    }

    @Test
    public void testCarramBoard_nonNull() {
        CleanStrikeGame cleanStrikeGame = new CleanStrikeGame();
        testCarramBoardNotNull(cleanStrikeGame);
        Assert.assertEquals(cleanStrikeGame.getStriker(), cleanStrikeGame.getPlayer1());
    }

    @Test
    public void testCarramBoard_nonNull_StrikerEqualsPlayer1() {
        CleanStrikeGame cleanStrikeGame = new CleanStrikeGame(true);
        testCarramBoardNotNull(cleanStrikeGame);
        Assert.assertEquals(cleanStrikeGame.getStriker(), cleanStrikeGame.getPlayer1());
    }

    @Test
    public void testCarramBoard_nonNull_StrikerEqualsPlayer2() {
        CleanStrikeGame cleanStrikeGame = new CleanStrikeGame(false);
        testCarramBoardNotNull(cleanStrikeGame);
        Assert.assertEquals(cleanStrikeGame.getStriker(), cleanStrikeGame.getPlayer2());
    }

    @Test(expected = NullPointerException.class)
    public void testCarramBoard_nullPlayers() { new CleanStrikeGame(null, null, false);
    }

    @Test
    public void testCarramBoard_nonNullPlayers() {
        CleanStrikeGame cleanStrikeGame = new CleanStrikeGame(getPlayer(0), getPlayer(0), true);
        testCarramBoardNotNull(cleanStrikeGame);
        Assert.assertEquals(cleanStrikeGame.getStriker(), cleanStrikeGame.getPlayer1());
    }

    @Test
    public void testGetWinner_player1Least5Points_diffLessThen3() {
        cleanStrikeGame.addPoints(5);
        cleanStrikeGame.changePlayer();
        cleanStrikeGame.addPoints(4);
        Assert.assertNull(cleanStrikeGame.getWinner());
    }

    @Test
    public void testGetWinner_playersLessThan5Points() {
        cleanStrikeGame.addPoints(3);
        cleanStrikeGame.changePlayer();
        cleanStrikeGame.addPoints(2);
        Assert.assertNull(cleanStrikeGame.getWinner());
    }

    @Test
    public void testGetWinner_player1Least5Points_diffMoreThen3() {
        cleanStrikeGame.addPoints(5);
        cleanStrikeGame.changePlayer();
        cleanStrikeGame.addPoints(2);
        assertEqualPlayers(cleanStrikeGame.getPlayer1(), cleanStrikeGame.getWinner());
    }

    @Test
    public void testGetWinner_player2Least5Points_diffMoreThen3() {
        cleanStrikeGame.addPoints(1);
        cleanStrikeGame.changePlayer();
        cleanStrikeGame.addPoints(5);
        assertEqualPlayers(cleanStrikeGame.getPlayer2(), cleanStrikeGame.getWinner());
    }

    @Test
    public void isConsecutiveNoStrike_playerLessStrike() {
        addStrike(cleanStrikeGame, 1, StrikeType.STRIKE);
        Assert.assertFalse(cleanStrikeGame.isConsecutiveNoStrike());
    }

    @Test
    public void isConsecutiveNoStrike_playerMoreThan3Strike() {
        addStrike(cleanStrikeGame, 4, StrikeType.STRIKE);
        Assert.assertFalse(cleanStrikeGame.isConsecutiveNoStrike());
    }

    @Test
    public void isConsecutiveNoStrike_playerMoreThan3NoStrike() {
        addStrike(cleanStrikeGame, 3, StrikeType.NO_STRIKE);
        Assert.assertTrue(cleanStrikeGame.isConsecutiveNoStrike());
    }

    @Test
    public void isConsecutiveNoStrike_playerMoreThan3RandomStrike() {
        addStrike(cleanStrikeGame, 2, StrikeType.STRIKE);
        cleanStrikeGame.addStrike(StrikeType.NO_STRIKE);
        cleanStrikeGame.addStrike(StrikeType.STRIKE);
        cleanStrikeGame.addStrike(StrikeType.NO_STRIKE);
        Assert.assertFalse(cleanStrikeGame.isConsecutiveNoStrike());
    }

    @Test
    public void isFoulNoStrike_playerLessStrike() {
        addStrike(cleanStrikeGame, 1, StrikeType.STRIKE);
        Assert.assertFalse(cleanStrikeGame.isFoul());
    }

    @Test
    public void isFoulNoStrike_playerMoreThan3Strike() {
        addStrike(cleanStrikeGame, 4, StrikeType.STRIKE);
        Assert.assertFalse(cleanStrikeGame.isFoul());
    }

    @Test
    public void isFoulNoStrike_playerMoreThan3FoulStrike() {
        addStrike(cleanStrikeGame, 2, StrikeType.NO_STRIKE);
        cleanStrikeGame.addStrike(StrikeType.CONSECUTIVE_3_NO_STRIKE);
        cleanStrikeGame.addStrike(StrikeType.STRIKER_STRIKE);
        cleanStrikeGame.addStrike(StrikeType.FOUL);
        Assert.assertTrue(cleanStrikeGame.isFoul());
    }

    @Test
    public void isFoulNoStrike_playerMoreThan3RandomStrike() {
        addStrike(cleanStrikeGame, 2, StrikeType.STRIKE);
        cleanStrikeGame.addStrike(StrikeType.CONSECUTIVE_3_NO_STRIKE);
        cleanStrikeGame.addStrike(StrikeType.MULTI_STRIKE);
        cleanStrikeGame.addStrike(StrikeType.STRIKER_STRIKE);
        Assert.assertFalse(cleanStrikeGame.isFoul());
    }

    @Test
    public void test_isDraw_NotGameover() {
        Assert.assertFalse(cleanStrikeGame.isDraw());
    }

    @Test
    public void test_isDraw_Gameover_Winner() {
        cleanStrikeGame.addPoints(5);
        cleanStrikeGame.changePlayer();
        cleanStrikeGame.addPoints(2);
        strikeAllCoins();
        Assert.assertFalse(cleanStrikeGame.isDraw());
    }

    @Test
    public void test_isDraw_Gameover_NoWinner() {
        cleanStrikeGame.addPoints(5);
        cleanStrikeGame.changePlayer();
        cleanStrikeGame.addPoints(4);
        strikeAllCoins();
        Assert.assertTrue(cleanStrikeGame.isDraw());
    }

    private void strikeAllCoins() {
        IntStream.range(0, NO_OF_COINS).forEach(i -> cleanStrikeGame.coinsStriked());
        IntStream.range(0, NO_OF_RED_COINS).forEach(i -> cleanStrikeGame.redCoinsStriked());
    }

    @Test
    public void test_changePlayer_player1() {
        cleanStrikeGame.changePlayer();
        assertEqualPlayers(cleanStrikeGame.getPlayer2(), cleanStrikeGame.getStriker());
    }

    @Test
    public void test_changePlayer_player2() {
        cleanStrikeGame.changePlayer();
        cleanStrikeGame.changePlayer();
        assertEqualPlayers(cleanStrikeGame.getPlayer1(), cleanStrikeGame.getStriker());
    }

    @Test(expected = OutOfCoinException.class)
    public void test_redCoinsStriked_moreThanPresent() {
        strikeAllCoins();
        cleanStrikeGame.redCoinsStriked();
    }

    @Test(expected = OutOfCoinException.class)
    public void test_coinsStriked_moreThanPresent() {
        strikeAllCoins();
        cleanStrikeGame.coinsStriked();
    }

    @Test
    public void test_isLastRedCoin_false() {
        Assert.assertFalse(cleanStrikeGame.isLastRedCoin());
        cleanStrikeGame.addStrike(StrikeType.NO_STRIKE);
        Assert.assertFalse(cleanStrikeGame.isLastRedCoin());
    }

    @Test
    public void test_isLastRedCoin_true() {
        cleanStrikeGame.addStrike(StrikeType.RED_STRIKE);
        Assert.assertTrue(cleanStrikeGame.isLastRedCoin());
    }

    @Test
    public void test_printScore() {
        playerMoves.strikes(StrikeType.MULTI_STRIKE);
        playerMoves.strikes(StrikeType.MULTI_STRIKE);
        Assert.assertEquals(playerMoves.getPlayer1().getName() + " : " + playerMoves.getPlayer1().getScore()
                        + " " + playerMoves.getPlayer2().getName() + " : " + playerMoves.getPlayer2().getScore(),
                cleanStrikeGame.printScore());
    }
}
