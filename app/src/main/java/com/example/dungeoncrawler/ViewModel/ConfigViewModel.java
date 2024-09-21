package com.example.dungeoncrawler.ViewModel;

import android.widget.RadioGroup;

import com.example.dungeoncrawler.R;

public class ConfigViewModel {
    private static int difficulty;
    private static int startingHP;
    private static int characterNum;
    /**
     * Handles the difficulty character selection
     * @param difficultyRG hardness
     * @param characterRG character rg
     * @return int[] this is the value
     *
     */
    public static int[] handleConfigSelection(RadioGroup difficultyRG, RadioGroup characterRG) {
        RadioGroup difficultyRadioGroup = difficultyRG;
        RadioGroup characterRadioGroup = characterRG;
        int checkedDifficultyButtonId = difficultyRadioGroup.getCheckedRadioButtonId();
        int checkedCharacterButtonId = characterRadioGroup.getCheckedRadioButtonId();
        if (checkedDifficultyButtonId == R.id.radioEasy) {
            difficulty = 1;
            startingHP = 150;
        } else if (checkedDifficultyButtonId == R.id.radioNormal) {
            difficulty = 2;
            startingHP = 100;
        } else if (checkedDifficultyButtonId == R.id.radioHard) {
            difficulty = 3;
            startingHP = 50;
        } else {
            difficulty = 1;
            startingHP = 150;
        }
        if (checkedCharacterButtonId == R.id.radioPumpkin) {
            characterNum = 1;
        } else if (checkedCharacterButtonId == R.id.radioKnight) {
            characterNum = 2;
        } else if (checkedCharacterButtonId == R.id.radioWizard) {
            characterNum = 3;
        } else {
            characterNum = 1;
        }
        int[] info = {difficulty, startingHP, characterNum};
        return info;
    }

    public static int getCharacterNum() {
        return characterNum;
    }


    /**
     * Get difficulty.
     * @return difficulty
     */
    public static int getDifficulty() {
        return difficulty;
    }
}
