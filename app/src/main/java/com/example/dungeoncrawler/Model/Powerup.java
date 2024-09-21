package com.example.dungeoncrawler.Model;

public abstract class Powerup {

    abstract void applyPowerup(Player player);
    public abstract String getType();
    public abstract void setType(PowerupsDecorator powerupsDecorator);
}
