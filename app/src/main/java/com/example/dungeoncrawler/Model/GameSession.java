package com.example.dungeoncrawler.Model;


public class GameSession {
    private static GameSession gameSession;
    private int startingScore = 100;
    private double currentScore;
    private int difficulty;
    private int hP;
    private int characterNum;
    private int scoreDecreaseRate = 1;
    private String name;
    private int screenWidth;
    private int screenHeight;
    private float tileMapScale;
    private int tileImageDimInfo;
    private int tileMapX;
    private int tileMapY;

    /**
     * Game session.
     */
    private GameSession() {
        this.currentScore = startingScore;
    }

    /**
     * Game session instance.
     * @return instance
     */
    public static GameSession getInstance() {
        if (gameSession == null) {
            gameSession = new GameSession();
        }
        return gameSession;
    }

    /**
     * Get current score.
     * @return current score
     */
    public double getCurrentScore() {
        return currentScore;
    }

    /**
     * Set current score.
     * @param score double
     */
    public void setCurrentScore(double score) {
        currentScore = score;
    }

    /**
     * Set difficulty.
     * @param difficulty double
     */
    public void setDifficulty(int difficulty) {this.difficulty = difficulty;}

    /**
     * Set health points.
     * @param hP int
     */
    public void setHP(int hP) {
        this.hP = hP;
    }

    /**
     * Set character num.
     * @param characterNum int
     */
    public void setCharacterNum(int characterNum) {
        this.characterNum = characterNum;
    }

    /**
     * Set name.
     * @param inputName string
     */
    public void setName(String inputName) {
        this.name = inputName;
    }

    /**
     * Get input name.
     * @return string
     */
    public String getInputName() {
        return name;
    }

    /**
     * Get character num.
     * @return int
     */
    public int getCharacterNum() {
        return characterNum;
    }

    /**
     * Get difficulty.
     * @return double
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Get health points.
     * @return int
     */
    public int getHP() {
        return hP;
    }

    /**
     * Reset game.
     */
    public void resetGame() {
        currentScore = startingScore;
        difficulty = 1;
        hP = 50;
        characterNum = 1;
    }

    /**
     * Set screen width.
     * @param screenWidth int
     */
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    /**
     * Get screen width.
     * @return screen width
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Set screen height.
     * @param screenHeight screen height
     */
    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    /**
     * Get screen height.
     * @return screen height
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Set tile map scale.
     * @param tileMapScale float
     */
    public void setTileMapScale(float tileMapScale) {
        this.tileMapScale = tileMapScale;
    }

    /**
     * Get tile map scale.
     * @return tile map scale
     */
    public float getTileMapScale() {
        return tileMapScale;
    }

    /**
     * Set tile image dim info.
     * @param i int
     */
    public void setTileImageDimInfo(int i) {
        this.tileImageDimInfo = i;
    }

    /**
     * Get tile image dim info.
     * @return tile image dim info
     */
    public int getTileImageDimInfo() {
        //Log.d("helpme", "Tile Image Dim: " + tileImageDimInfo);
        return tileImageDimInfo;
    }

    /**
     * Set tile map x.
     * @param x float
     */
    public void setTileMapX(float x) {
        this.tileMapX = (int) x;
    }

    /**
     * Get tile map x.
     * @return tile map x
     */
    public int getTileMapX() {
        return tileMapX;
    }

    /**
     * Set tile map y.
     * @param y float
     */
    public void setTileMapY(float y) {
        this.tileMapY = (int) y;
    }

    /**
     * Get tile map y.
     * @return tile map y
     */
    public int getTileMapY() {
        return tileMapY;
    }
}
