package com.example.dungeoncrawler.Model;

public class MoveRight implements MovementStrategy {
    @Override
    public void move(Player player) {
        player.setX(player.getX() + player.getSpeed());
    }
}
