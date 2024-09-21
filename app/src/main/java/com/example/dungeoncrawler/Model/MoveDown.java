package com.example.dungeoncrawler.Model;

public class MoveDown implements MovementStrategy {
    @Override
    public void move(Player player) {
        player.setY(player.getY() + player.getSpeed());
    }
}
