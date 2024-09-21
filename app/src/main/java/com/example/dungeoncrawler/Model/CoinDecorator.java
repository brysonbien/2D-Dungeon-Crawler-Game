package com.example.dungeoncrawler.Model;

public class CoinDecorator extends PowerupsDecorator {
    //when player collects coin power-ups, player speed increases (or enemy speed decreases, not sure yet?)
    String type = "coin_anim_f0";
    public CoinDecorator(Powerup decoratedPowerups) {
        super(decoratedPowerups);
    }

    @Override
    public void applyPowerup(Player player) {
        super.applyPowerup(player);
        player.setSpeed(player.getSpeed() + 2);
    }

    @Override
    public String getType() {
        return "coin_anim_f0";
    }

    @Override
    public void setType(PowerupsDecorator powerupsDecorator) {
        this.type = null;
    }

    public boolean coinPower() {
        return true;
    }

    public boolean coinType() {
        return true;
    }


}
