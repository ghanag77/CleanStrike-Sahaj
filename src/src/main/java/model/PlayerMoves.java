package model;

import lombok.Getter;

public class PlayerMoves {
    @Getter
    private Player player1;
    @Getter
    private Player player2;
    private Player winner;

    private CleanStrikeGame cleanStrikeGame;

    public PlayerMoves(Player player1, Player player2, CleanStrikeGame cleanStrikeGame) {
        this.player1 = player1;
        this.player2 = player2;
        this.cleanStrikeGame = cleanStrikeGame;
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
                cleanStrikeGame.addStrike(StrikeType.NO_STRIKE);
                cleanStrikeGame.changePlayer();
                break;
            case STRIKER_STRIKE:
                cleanStrikeGame.addStrike(StrikeType.STRIKER_STRIKE);
                cleanStrikeGame.changePlayer();
                break;
            case RED_STRIKE:
                cleanStrikeGame.addStrike(StrikeType.RED_STRIKE);
                cleanStrikeGame.redCoinsStriked();
                break;
            case STRIKE:
                if (!cleanStrikeGame.isLastRedCoin()) {
                    cleanStrikeGame.coinsStriked();
                }
                cleanStrikeGame.addStrike(StrikeType.STRIKE);
                cleanStrikeGame.changePlayer();
                break;
            case MULTI_STRIKE:
                cleanStrikeGame.addStrike(StrikeType.MULTI_STRIKE);
                cleanStrikeGame.changePlayer();
                break;
            case DEFUNCT_COIN:
                cleanStrikeGame.addStrike(StrikeType.DEFUNCT_COIN);
                cleanStrikeGame.coinsStriked();
                cleanStrikeGame.changePlayer();
                break;
        }
        if (cleanStrikeGame.isConsecutiveNoStrike()) {
            cleanStrikeGame.addStrike(StrikeType.CONSECUTIVE_3_NO_STRIKE);
        }
        if (cleanStrikeGame.isFoul()) {
            cleanStrikeGame.addStrike(StrikeType.FOUL);
        }
    }

    public Player getCurrentPlayer() {
        return cleanStrikeGame.getStriker();
    }

    public int getTotalCoins() {
        return cleanStrikeGame.getRedCoin() + cleanStrikeGame.getCoins();
    }
}
