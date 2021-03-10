package model;

import exception.OutOfCoinException;
import lombok.Getter;

@Getter
public class CarromBoard {

    public static final int NO_OF_COINS = 9;
    public static final int NO_OF_RED_COINS = 1;
    private int coins;
    private int redCoin;

    CarromBoard(int coins, int redCoin) {
        this.coins = coins;
        this.redCoin = redCoin;
    }

    public void redCoinsStriked() {
        if (redCoin != 0) {
            redCoin--;
        } else {
            throw new OutOfCoinException();
        }
    }

    public void coinsStriked() {
        if (coins != 0) {
            coins--;
        } else {
            throw new OutOfCoinException();
        }
    }

    public boolean isGameOver() {
        return coins == 0 && redCoin == 0;
    }
}
