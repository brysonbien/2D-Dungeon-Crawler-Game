package com.example.dungeoncrawler.Model;

public class ChestDecorator extends PowerupsDecorator {
    //when player collects treasure from chest, the full chest image with change to the empty chest image
    //collecting the treasure from the chest will switch out the regular sword weapon with the gold sword weapon, increasing player damage
    String type = "chest_full_open_anim_f2";
    public ChestDecorator(Powerup decoratedPowerups) {
        super(decoratedPowerups);
    }

    @Override
    public void applyPowerup(Player player) {
        super.applyPowerup(player);
        //switch swords
        player.setDamage(player.getHp() + 10);
        changeType();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(PowerupsDecorator powerupsDecorator) {
        this.type = null;
    }

    public void changeType() {
        type = "chest_empty_open_anim_f2";
    }

    public boolean chestPower() {
        return true;
    }

    public boolean chestType() {
        return true;
    }
}
