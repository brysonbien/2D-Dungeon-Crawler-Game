package com.example.dungeoncrawler.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.dungeoncrawler.Model.GameSession;
import com.example.dungeoncrawler.Model.LeaderboardEntry;
import com.example.dungeoncrawler.Model.LeaderboardModel;
import java.util.Observable;
import java.util.ArrayList;
import java.util.List;

public class GameSessionViewModel extends Observable {
    private static GameSessionViewModel gameSessionViewModel;
    private double scoreDecreaseRate = 1;
    private double currentScore;
    private GameSession gameSession = GameSession.getInstance();
    private LeaderboardModel leaderboardModel = LeaderboardModel.getInstance();
    public GameSessionViewModel() { }

    /**
     * Game session view model.
     * @return get Instance
     */
    public static GameSessionViewModel getInstance() {
        if (gameSessionViewModel == null) {
            gameSessionViewModel = new GameSessionViewModel();
        }
        return gameSessionViewModel;
    }

    /**
     * Update score.
     * @param scoreHandler handler
     * @param scoreRunnable runnable
     * @return
     */
    public double updateScore(Handler scoreHandler, Runnable scoreRunnable) {
        currentScore = GameSession.getInstance().getCurrentScore();
        currentScore = Math.max(0, currentScore - scoreDecreaseRate);
        GameSession.getInstance().setCurrentScore(currentScore);
        if (currentScore == 0) {
            scoreHandler.removeCallbacks(scoreRunnable);
        }
        notifyObservers();
        return currentScore;
    }

    /**
     * Extract config info .
     * @param intent intent
     */
    public void extractConfigInfo(Intent intent) {
        Bundle gameSettingsInit = intent.getExtras();
        String inputName = gameSettingsInit.getString("NAME");
        int difficulty = gameSettingsInit.getInt("DIFFICULTY");
        int startingHP = gameSettingsInit.getInt("STARTING_HP");
        int characterNum = gameSettingsInit.getInt("CHARACTER_NUM");
        gameSession.setDifficulty(difficulty);
        gameSession.setHP(startingHP);
        gameSession.setCharacterNum(characterNum);
        gameSession.setName(inputName);
    }

    /**
     * Leaderboard setup.
     * @return leaderboard entry
     */
    public List<LeaderboardEntry> leaderboardSetup() {
        Log.d("Leaderboard", "here");
        List<LeaderboardEntry> entryList = leaderboardModel.getLeaderboardEntries();
        Log.d("Leaderboard", Double.toString(entryList.get(0).getScore()));
        int numTopEntries = Math.min(5, entryList.size());
        List<LeaderboardEntry> topEntries = new ArrayList<>(entryList.subList(0, numTopEntries));
        return topEntries;
    }

    /**
     * Get input name.
     * @return input name
     */
    public String getInputName() {
        return gameSession.getInputName();
    }

    /**
     * Get character name.
     * @return character name
     */
    public int getCharacterNum() {
        return gameSession.getCharacterNum();
    }

    /**
     * Get difficulty.
     * @return difficulty
     */
    public double getDifficulty() {
        return gameSession.getDifficulty();
    }

    /**
     * Get score decrease rate.
     * @return decrease rate
     */
    public double getScoreDecreaseRate() {
        return scoreDecreaseRate;
    }

    /**
     * Get health points.
     * @return hp
     */
    public int getHP() {
        return gameSession.getHP();
    }

    /**
     * Send screen dims.
     * @param screenWidth int
     * @param screenHeight int
     */
    public void sendScreenDims(int screenWidth, int screenHeight) {
        gameSession.setScreenWidth(screenWidth);
        gameSession.setScreenHeight(screenHeight);
    }

    /**
     * Set tile map scale.
     * @param tileMapScale float
     */
    public void setTileMapScale(float tileMapScale) {
        gameSession.setTileMapScale(tileMapScale);
    }

    /**
     * Set tile image dim info.
     * @param i int
     */
    public void setTileImageDimInfo(int i) {
        gameSession.setTileImageDimInfo(i);
    }

    /**
     * Set tile map x.
     * @param x float
     */
    public void setTileMapX(float x) {
        gameSession.setTileMapX(x);
    }

    /**
     * Set tile map y.
     * @param y float
     */
    public void setTileMapY(float y) {
        gameSession.setTileMapY(y);
    }

    /**
     * Get screen width.
     * @return screen width
     */
    public float getScreenWidth() {
        return gameSession.getScreenWidth();
    }

    /**
     * Get screen height.
     * @return screen height
     */
    public float getScreenHeight() {
        return gameSession.getScreenHeight();
    }

    /**
     * Get current score.
     * @return current score
     */
    public double getCurrentScore() {
        return currentScore;
    }
}
