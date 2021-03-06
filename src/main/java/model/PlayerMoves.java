package model;

import lombok.Getter;
import lombok.NonNull;

public class PlayerMoves {
    @Getter
    private Player player1;
    @Getter
    private Player player2;
    private Player winner;

    private CarromBoard carromBoard;

    public PlayerMoves(Player player1, Player player2, CarromBoard carromBoard) {
        this.player1 = player1;
        this.player2 = player2;
        this.carromBoard = carromBoard;
    }

    public String printScore() {
        return (player1.getName() + " : " + player1.getScore()
                + " " + player2.getName() + " : " + player2.getScore());
    }

    public void play(int caseType) {
        switch (caseType) {
            case 1:
                strikes(StrikeType.STRIKE);
                break;
            case 2:
                strikes(StrikeType.MULTI_STRIKE);
                break;
            case 3:
                strikes(StrikeType.RED_STRIKE);
                break;
            case 4:
                strikes(StrikeType.STRIKER_STRIKE);
                break;
            case 5:
                strikes(StrikeType.DEFUNCT_COIN);
                break;
            case 6:
                strikes(StrikeType.NO_STRIKE);
                break;
        }
    }

    public void strikes(StrikeType strikeType) {

        switch (strikeType) {
            case NO_STRIKE:
                carromBoard.addStrike(StrikeType.NO_STRIKE);
                carromBoard.changePlayer();
                break;
            case STRIKER_STRIKE:
                carromBoard.addStrike(StrikeType.STRIKER_STRIKE);
                carromBoard.changePlayer();
                break;
            case RED_STRIKE:
                carromBoard.addStrike(StrikeType.RED_STRIKE);
                carromBoard.redCoinsStriked();
                break;
            case STRIKE:
                if (!carromBoard.isLastRedCoin()) {
                    carromBoard.coinsStriked();
                }
                carromBoard.addStrike(StrikeType.STRIKE);
                carromBoard.changePlayer();
                break;
            case MULTI_STRIKE:
                carromBoard.addStrike(StrikeType.MULTI_STRIKE);
                carromBoard.changePlayer();
                break;
            case DEFUNCT_COIN:
                carromBoard.addStrike(StrikeType.DEFUNCT_COIN);
                carromBoard.coinsStriked();
                carromBoard.changePlayer();
                break;
        }
        if (carromBoard.isConsecutiveNoStrike()) {
            carromBoard.addStrike(StrikeType.CONSECUTIVE_3_NO_STRIKE);
        }
        if (carromBoard.isFoul()) {
            carromBoard.addStrike(StrikeType.FOUL);
        }
    }

    public Player getCurrentPlayer() {
        return carromBoard.getStriker();
    }

    public int getTotalCoins() {
        return carromBoard.getRedCoin() + carromBoard.getCoins();
    }
}
