package model;

import exception.OutOfCoinException;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;

@Getter
public class CleanStrikeGame extends CarromBoard{

    public static final int CONSECUTIVE_NO_STRIKE_MAX = 3;
    public static final int CONSECUTIVE_LOOSING_MAX = 3;
    public static final int MIN_WINNING_POINTS = 5;
    public static final int WINNING_POINT_DIFF = 3;



    private Player striker;

    private Player player1;
    private Player player2;

    public CleanStrikeGame() {
        this(true);
    }

    public CleanStrikeGame(boolean player1Strikes) {
        this(new Player(), new Player(), player1Strikes);
    }

    public CleanStrikeGame(@NonNull Player player1, @NonNull Player player2, boolean player1Strikes) {
        super(NO_OF_COINS,NO_OF_RED_COINS);
        this.player1 = player1;
        this.player2 = player2;
        selectPlayerStrikesFirst(player1Strikes);
    }

    private void selectPlayerStrikesFirst(boolean player1Strikes) {
        if (player1Strikes) {
            this.striker = this.player1;
        } else {
            this.striker = this.player2;
        }
    }

    public Player getWinner() {
        if (((player1.getScore() >= MIN_WINNING_POINTS) || (player2.getScore() >= MIN_WINNING_POINTS))
                && Math.abs(player1.getScore() - player2.getScore()) >= WINNING_POINT_DIFF) {
            return player1.getScore() > player2.getScore() ? player1 : player2;
        }
        return null;
    }

    public String printScore() {
        return (player1.getName() + " : " + player1.getScore()
                + " " + player2.getName() + " : " + player2.getScore());
    }

    public String printFinalResult() {
        return ( player1.getName() + " won the game. Final Score: "  + player1.getScore() +
                 "-" + player2.getScore());
    }

    public boolean isConsecutiveNoStrike() {
        boolean isConsecutive = false;
        List<StrikeType> points = striker.getStrikes();
        int size = points.size();
        if (size >= CONSECUTIVE_NO_STRIKE_MAX) {
            boolean isAllNoStrike = true;
            for (int i = size - CONSECUTIVE_NO_STRIKE_MAX; i < size; i++) {
                if (!StrikeType.NO_STRIKE.equals(points.get(i))) {
                    isAllNoStrike = false;
                }
            }
            isConsecutive = isAllNoStrike;
        }
        return isConsecutive;
    }

    public boolean isFoul() {
        boolean isLoosingPoint = false;
        List<StrikeType> points = striker.getStrikes();
        int size = points.size();
        if (size >= CONSECUTIVE_LOOSING_MAX) {
            boolean isAllNoStrike = true;
            for (int i = size - CONSECUTIVE_LOOSING_MAX; i < size; i++) {
                if (!isLoosingAPoint(points.get(i))) {
                    isAllNoStrike = false;
                }
            }
            isLoosingPoint = isAllNoStrike;
        }
        return isLoosingPoint;
    }

    public boolean isDraw() {
        boolean draw = false;
        if (isGameOver()) {
            Player winner = getWinner();
            if (Objects.isNull(winner)) {
                draw = true;
            }
        }
        return draw;
    }

    public void changePlayer() {
        if (striker.getName().equals(player1.getName())) {
            striker = player2;
        } else {
            striker = player1;
        }
    }

    public void addStrike(StrikeType strikeType) {
        striker.getStrikes().add(strikeType);
        addPoints(strikeType.getPoints());
    }

    public void addPoints(int points) {
        striker.addScore(points);
    }

    public boolean isLastRedCoin() {
        if (!striker.getStrikes().isEmpty()) {
            return StrikeType.RED_STRIKE
                    .equals(striker.getStrikes().get(striker.getStrikes().size() - 1));
        }
        return false;
    }

    private boolean isLoosingAPoint(StrikeType strikeType) {
        return StrikeType.FOUL.equals(strikeType) || StrikeType.STRIKER_STRIKE.equals(strikeType)
                || StrikeType.CONSECUTIVE_3_NO_STRIKE.equals(strikeType) ||
                StrikeType.DEFUNCT_COIN.equals(strikeType);
    }
}
