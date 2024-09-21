package com.example.dungeoncrawler.Model;

public class CollectablePowerup extends Powerup{
    private PowerupsDecorator powerupsDecorator;

    @Override
    void applyPowerup(Player player) {
        player.applyPowerup(this);
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setType(PowerupsDecorator powerupsDecorator) {
        this.powerupsDecorator = powerupsDecorator;
    }
}
