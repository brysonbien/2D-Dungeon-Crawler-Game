package com.example.dungeoncrawler.Model;

public class MoveUp implements MovementStrategy {
    @Override
    public void move(Player player) {
        player.setY(player.getY() - player.getSpeed());
    }
}
