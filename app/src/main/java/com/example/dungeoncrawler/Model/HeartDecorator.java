package com.example.dungeoncrawler.Model;

public class HeartDecorator extends PowerupsDecorator {
    //when the player collects the heart power-up, it increases player health
    String type = "ui_heart_full";
    public HeartDecorator(Powerup decoratedPowerups) {
        super(decoratedPowerups);
    }

    @Override
    public void applyPowerup(Player player) {
        super.applyPowerup(player);
        player.setHp(player.getHp() + 5);
    }

    @Override
    public String getType() {
        return "ui_heart_full";
    }
    @Override
    public void setType(PowerupsDecorator powerupsDecorator) {
        this.type = null;
    }

    public boolean heartPower() {
        return true;
    }

    public boolean heartType() {
        return true;
    }
}
