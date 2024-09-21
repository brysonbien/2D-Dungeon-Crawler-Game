package com.example.dungeoncrawler.Model;

public abstract class PowerupsDecorator extends Powerup {

    protected Powerup decoratedPowerups;
    public PowerupsDecorator(Powerup decoratedPowerups) {
        this.decoratedPowerups = decoratedPowerups;
    }

    @Override
    public void applyPowerup(Player player) {
        decoratedPowerups.applyPowerup(player);
    }
}
