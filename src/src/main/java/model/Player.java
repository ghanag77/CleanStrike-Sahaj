package model;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Player {

    @NonNull
    private String name;
    private int score;
    private List<StrikeType> strikes = new ArrayList<>();

    public Player() {
        this.name = UUID.randomUUID().toString();
    }

    public Player(String name) {
        this.name = name;
    }

    public void addScore(int points) {
        score += points;
    }
}
