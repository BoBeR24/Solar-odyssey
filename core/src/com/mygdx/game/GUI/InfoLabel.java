package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.SupportiveClasses.Timer;
import java.time.format.DateTimeFormatter;

public class InfoLabel {
    private Odyssey game;
    private Timer timer;

    public InfoLabel(Odyssey game, Timer timer) {
        this.game = game;
        this.timer = timer;
    }

    public void draw() {
        float timePassed = timer.getTimePassed();

        int daysPassed = (int) (timePassed / 3600) / 24;
        int hours = (int) ((timePassed / 3600) % 24);
        int minutes = (int) ((timePassed / 60) % 60);
        int seconds = (int) (timePassed % 60);

        float labelX = Gdx.graphics.getWidth() - 500;
        float labelY = Gdx.graphics.getHeight() - 100;

        game.font.draw(game.batch, "Date of launch: April 1st", labelX, labelY);

        game.font.draw(game.batch, "Current date: " + timer.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), labelX, labelY - 50);

        game.font.draw(game.batch, "Days passed: " + daysPassed, labelX, labelY - 150);
        game.font.draw(game.batch, hours + ":" + minutes + ":" + seconds, labelX, labelY - 100);
    }
}
