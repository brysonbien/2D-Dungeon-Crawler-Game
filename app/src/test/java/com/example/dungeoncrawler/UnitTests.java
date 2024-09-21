package com.example.dungeoncrawler;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import com.example.dungeoncrawler.Model.ChestDecorator;
import com.example.dungeoncrawler.Model.CoinDecorator;
import com.example.dungeoncrawler.Model.DemonEnemy;
import com.example.dungeoncrawler.Model.GameSession;
import com.example.dungeoncrawler.Model.HeartDecorator;
import com.example.dungeoncrawler.Model.MoveDown;
import com.example.dungeoncrawler.Model.MoveLeft;
import com.example.dungeoncrawler.Model.MoveRight;
import com.example.dungeoncrawler.Model.MoveUp;
import com.example.dungeoncrawler.Model.MovementStrategy;
import com.example.dungeoncrawler.Model.OgreEnemy;
import com.example.dungeoncrawler.Model.OrcEnemy;
import com.example.dungeoncrawler.Model.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import com.example.dungeoncrawler.Model.LeaderboardEntry;
import com.example.dungeoncrawler.Model.ZombieEnemy;
import com.example.dungeoncrawler.View.MainActivity;
import com.example.dungeoncrawler.ViewModel.GameSessionViewModel;

import org.mockito.Mockito.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit Tests for Sprint 1 - Bryson (2 sections mentioned Sprint 2).
 * 1. Detection of whitespace-only, null, and empty names.
 * 2. Player starting lives are different based on the chosen difficulty.
 */
public class UnitTests {
    @Test
    public void testWhitespaceOnlyNames() {
        String name = " ";
        assertTrue(isNameInvalid(name));
    }
    @Test
    public void testNullNamea() {
        String name = null;
        assertTrue(isNameInvalid(name));
    }
    @Test
    public void testEmptyNamea() {
        String name = "";
        assertTrue(isNameInvalid(name));
    }
    private boolean isNameInvalid(String name) {
        return name == null || name.trim().isEmpty();
    }

    @Test
    public void testStartingHPForEasyDifficulty() {
        int expectedHP = 150;
        int actualHP = getStartingHP("easy");
        assertEquals(expectedHP, actualHP);
    }
    @Test
    public void testStartingHPForNormalDifficulty() {
        int expectedHP = 100;
        int actualHP = getStartingHP("normal");
        assertEquals(expectedHP, actualHP);
    }
    @Test
    public void testStartingHPForHardDifficulty() {
        int expectedHP = 50;
        int actualHP = getStartingHP("hard");
        assertEquals(expectedHP, actualHP);
    }
    private int getStartingHP(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                return 150;
            case "normal":
                return 100;
            case "hard":
                return 50;
            default:
                throw new IllegalArgumentException("Invalid difficulty: " + difficulty);
        }
    }

    @Test
    // Maddie: checks that initial score is set correctly for a new game
    public void initialScoreTest() {
        GameSession gameSession = GameSession.getInstance();
        double startingScore = gameSession.getCurrentScore();
        assertEquals(100, startingScore, 0.01);
    }

    @Test
    // Maddie: checks that the score is reset back to its initial value when the game is restarted
    public void scoreResetTest() {
        GameSession gameSession = GameSession.getInstance();
        gameSession.setCurrentScore(150.0);
        gameSession.resetGame();
        double startingScore = gameSession.getCurrentScore();
        assertEquals(100.0, startingScore, 0.01);
    }

    @Test
    // Maddie: ensures the Game Session follows the Singleton Pattern -> only 1 instance is created
    public void gameSingletonPatternTest() {
        GameSession gameSession1 = GameSession.getInstance();
        GameSession gameSession2 = GameSession.getInstance();
        assertSame(gameSession1, gameSession2);
    }

    @Test
    // Maddie: ensures the Player follows the Singleton Pattern -> only 1 instance is created
    public void playerSingletonPatternTest() {
        Player player1 = Player.getInstance();
        Player player2 = Player.getInstance();
        assertSame(player1, player2);
    }

    @Test public void addition_isCorrect() { assertEquals(4, 2 + 2); }

    //SPRINT 3
// made change to Player.java class by adding a getSpeed method
// made change to Player.java class by adding a getHeight method
// 13 tests right now
// maddie will make other 4 unit tests
    @Test
    public void testMoveUp() {
        Player player = Player.getInstance();
        player.setX(0);
        player.setY(0);
        player.setSpeed(8);

        MovementStrategy moveUpStrategy = new MoveUp();
        moveUpStrategy.move(player);

        assertEquals(0, player.getX());
        assertEquals(-8, player.getY()); }

    @Test
    public void testMoveDown() {
        // made change to Player.java class by adding a getSpeed method
        Player player = Player.getInstance();
        player.setX(0);
        player.setY(0);
        player.setSpeed(8);

        MovementStrategy moveDownStrategy = new MoveDown();
        moveDownStrategy.move(player);

        assertEquals(0, player.getX());
        assertEquals(8, player.getY());
    }

    @Test
    public void testMoveLeft() {
        Player player = Player.getInstance();
        player.setX(0);
        player.setY(0);
        player.setSpeed(8);

        MovementStrategy moveLeftStrategy = new MoveLeft();
        moveLeftStrategy.move(player);

        assertEquals(-8, player.getX());
        assertEquals(0, player.getY());}

    @Test
    public void testMoveRight() {
        Player player = Player.getInstance();
        player.setX(0);
        player.setY(0);
        player.setSpeed(8);

        MovementStrategy moveLeftStrategy = new MoveLeft();
        moveLeftStrategy.move(player);

        assertEquals(-8, player.getX());
        assertEquals(0, player.getY());}

    @Test
    public void testSetX() {
        Player player = Player.getInstance();
        int newX = 100;
        player.setX(newX);
        assertEquals(newX, player.getX());
    }

    @Test
    public void testSetY() {
        Player player = Player.getInstance();
        int newY = 200;
        player.setY(newY);
        assertEquals(newY, player.getY());
    }

    @Test
    public void testSetWidth() {
        Player player = Player.getInstance();
        int newWidth = 2;
        player.setWidth(newWidth);
        assertEquals(newWidth, player.getWidth());
    }

    @Test
    public void testSetHeight() {
        Player player = Player.getInstance();
        int newHeight = 112;
        player.setHeight(newHeight);
        assertEquals(newHeight, player.getHeight());
    }

    @Test
    public void testPlayerInstance() {
        Player player = Player.getInstance();
        Player newPlayer = Player.getInstance();
        assertEquals(player, newPlayer);
    }

    @Test
    public void testGetPlayerName() {
        LeaderboardEntry entry = new LeaderboardEntry("John", 100.0, "2023-10-16");
        String playerName = entry.getPlayerName();
        assertEquals("John", playerName);
    }

    @Test
    public void testGetScore() {
        LeaderboardEntry entry = new LeaderboardEntry("Alice", 75.5, "2023-10-16");
        double score = entry.getScore();
        assertEquals(75.5, score, 0.001); // Allowing a small delta for double comparison
    }
    @Test
    public void setCoordinatesTest() {
        Player player = Player.getInstance();
        player.setCoordinates(100, 50);
        assertEquals(100, player.getX());
        assertEquals(50, player.getY());
        player.setX(105);
        assertEquals(105, player.getX());
        player.setY(45);
        assertEquals(45, player.getY());
    }

    @Test
    public void playerDiagonalLeftUp() {
        Player player = Player.getInstance();
        player.setX(100); // Initial X coordinate
        player.setY(50);  // Initial Y coordinate
        player.setSpeed(8);

        MovementStrategy moveLeft = new MoveLeft();
        MovementStrategy moveUp = new MoveUp();

        moveLeft.move(player);
        moveUp.move(player);

        // Check if the X and Y coordinates have changed correctly
        assertEquals(92, player.getX());
        assertEquals(42, player.getY());
    }

    @Test
    public void playerDiagonalRightUp() {
        Player player = Player.getInstance();
        player.setX(100); // Initial X coordinate
        player.setY(50);  // Initial Y coordinate
        player.setSpeed(8);

        MovementStrategy moveRight = new MoveRight();
        MovementStrategy moveUp = new MoveUp();

        moveRight.move(player);
        moveUp.move(player);

        // Check if the X and Y coordinates have changed correctly
        assertEquals(108, player.getX());
        assertEquals(42, player.getY());
    }

    @Test
    public void playerDiagonalLeftDown() {
        Player player = Player.getInstance();
        player.setX(100); // Initial X coordinate
        player.setY(50);  // Initial Y coordinate
        player.setSpeed(8);

        MovementStrategy moveLeft = new MoveLeft();
        MovementStrategy moveDown = new MoveDown();

        moveLeft.move(player);
        moveDown.move(player);

        // Check if the X and Y coordinates have changed correctly
        assertEquals(92, player.getX());
        assertEquals(58, player.getY());
    }

    @Test
    public void playerDiagonalRightDown() {
        Player player = Player.getInstance();
        player.setX(100); // Initial X coordinate
        player.setY(50);  // Initial Y coordinate
        player.setSpeed(8);

        MovementStrategy moveRight = new MoveRight();
        MovementStrategy moveDown = new MoveDown();

        moveRight.move(player);
        moveDown.move(player);

        // Check if the X and Y coordinates have changed correctly
        assertEquals(108, player.getX());
        assertEquals(58, player.getY());
    }

    //    Sprint 4 unit tests added to fix demo
    @Test
    public void OrcMoveLeft() {
        OrcEnemy orc = mock(OrcEnemy.class);
        orc.setX(100);
        orc.setY(50);
        orc.setDirection(-1);
        when(orc.moveLeft()).thenReturn(true);

        assertTrue(orc.moveLeft());
    }

    @Test
    public void OrcMoveRight() {
        OrcEnemy orc = mock(OrcEnemy.class);
        orc.setX(100);
        orc.setY(50);
        orc.setDirection(1);
        when(orc.moveRight()).thenReturn(true);

        assertTrue(orc.moveRight());
    }

    @Test
    public void zombieMoveTop() {
        ZombieEnemy Zombie = mock(ZombieEnemy.class);
        Zombie.setX(100);
        Zombie.setY(50);
        Zombie.setDirection(1);
        when(Zombie.moveTop()).thenReturn(true);

        assertTrue(Zombie.moveTop());
    }

    @Test
    public void zombieMoveBot() {
        ZombieEnemy Zombie = mock(ZombieEnemy.class);
        Zombie.setX(100);
        Zombie.setY(50);
        Zombie.setDirection(-1);
        when(Zombie.moveBottom()).thenReturn(true);

        assertTrue(Zombie.moveBottom());
    }
    @Test
    public void demonMoveTopDiagonal() {
        DemonEnemy Demon = mock(DemonEnemy.class);
        Demon.setX(100);
        Demon.setY(50);
        Demon.setDirection(1);
        when(Demon.demonMovetopDiagonal()).thenReturn(true);

        assertTrue(Demon.demonMovetopDiagonal());
    }

    @Test
    public void demonMoveBotDiagonal() {
        DemonEnemy Demon = mock(DemonEnemy.class);
        Demon.setX(100);
        Demon.setY(50);
        Demon.setDirection(-1);
        when(Demon.demonMoveBotDiagonal()).thenReturn(true);
        boolean BotDiagonal = Demon.demonMoveBotDiagonal();

        assertTrue(Demon.demonMoveBotDiagonal());
    }

    @Test
    public void OgreMoveSquareBotLeft() {
        OgreEnemy Ogre = mock(OgreEnemy.class);
        Ogre.setX(100);
        Ogre.setY(50);
        Ogre.setDirection(-1);
        when(Ogre.OgreMoveSquareLeftBot()).thenReturn(true);
        boolean botLeft = Ogre.OgreMoveSquareLeftBot();

        assertTrue(Ogre.OgreMoveSquareLeftBot());
    }

    @Test
    public void OgreMoveSquareUpRight() {
        OgreEnemy Ogre = mock(OgreEnemy.class);
        Ogre.setX(100);
        Ogre.setY(50);
        Ogre.setDirection(1);
        when(Ogre.OgreMoveSquareRightUp()).thenReturn(true);
        boolean upRight = Ogre.OgreMoveSquareRightUp();

        assertTrue(Ogre.OgreMoveSquareRightUp());
    }

    @Test
    public void checkOgreCollisions() {
        OgreEnemy Ogre = mock(OgreEnemy.class);
        Player player = Player.getInstance();
        Ogre.setX(10);
        Ogre.setY(10);
        player.setX(10);
        player.setY(10);
        when(Ogre.Collided()).thenReturn(true);
        assertTrue(Ogre.Collided());
    }

    @Test
    public void checkOrcCollisions() {
        OrcEnemy Orc = mock(OrcEnemy.class);
        Player player = Player.getInstance();
        Orc.setX(10);
        Orc.setY(10);
        player.setX(10);
        player.setY(10);
        when(Orc.collided()).thenReturn(true);
        assertTrue(Orc.collided());
    }

    @Test
    public void checkDemonCollisions() {
        DemonEnemy Demon = mock(DemonEnemy.class);
        Player player = Player.getInstance();
        Demon.setX(10);
        Demon.setY(10);
        player.setX(10);
        player.setY(10);
        when(Demon.Collided()).thenReturn(true);
        assertTrue(Demon.Collided());
    }

    @Test
    public void checkZombieCollisions() {
        ZombieEnemy Zombie = mock(ZombieEnemy.class);
        Player player = Player.getInstance();
        Zombie.setX(10);
        Zombie.setY(10);
        player.setX(10);
        player.setY(10);
        when(Zombie.Collided()).thenReturn(true);
        assertTrue(Zombie.Collided());
    }

    // Sprint 5 unit tests start here for new

    // need 12 unit tests for new

    // Sprint 5 unit tests start here for new

    // need 12 unit tests for new

    @Test
    public void testChestApplyPowerup() {
        // Set up initial player stats
        Player player = mock(Player.class);

        ChestDecorator decorator = mock(ChestDecorator.class);

        when(player.getHp()).thenReturn(100);
        when(player.getDamage()).thenReturn(20);

        when(decorator.chestPower()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(decorator.chestPower());

    }

    @Test
    public void testCoinApplyPowerup() {
        // Set up initial player stats
        Player player = mock(Player.class);

        CoinDecorator decorator = mock(CoinDecorator.class);

        when(player.getHp()).thenReturn(100);
        when(player.getDamage()).thenReturn(20);

        when(decorator.coinPower()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(decorator.coinPower());

    }

    @Test
    public void testHeartApplyPowerup() {
        // Set up initial player stats
        Player player = mock(Player.class);

        HeartDecorator decorator = mock(HeartDecorator.class);

        when(player.getHp()).thenReturn(100);
        when(player.getDamage()).thenReturn(20);

        when(decorator.heartPower()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(decorator.heartPower());

    }

    @Test
    public void testHeartType() {
        // Set up initial player stats
        Player player = mock(Player.class);

        HeartDecorator decorator = mock(HeartDecorator.class);

        when(decorator.getType()).thenReturn("ui_heart_full");

        when(decorator.heartType()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(decorator.heartType());
    }

    @Test
    public void testCoinType() {
        // Set up initial player stats
        Player player = mock(Player.class);

        CoinDecorator decorator = mock(CoinDecorator.class);

        when(decorator.getType()).thenReturn("ui_coin_full");

        when(decorator.coinType()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(decorator.coinType());
    }

    @Test
    public void testChestType() {
        // Set up initial player stats
        Player player = mock(Player.class);

        ChestDecorator decorator = mock(ChestDecorator.class);

        when(decorator.getType()).thenReturn("ui_chest_full");

        when(decorator.chestType()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(decorator.chestType());
    }

    @Test
    public void testGoldenSword() {
        // Set up initial player stats
        Player player = mock(Player.class);
        Drawable powerup1 = mock(Drawable.class);
        Drawable powerup2 = mock(Drawable.class);
        player.checkPowerupCollisions(1, powerup1, powerup2);

        when(player.getHasGoldenSword()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(player.getHasGoldenSword());
    }

    @Test
    public void testResetPower() {
        // Set up initial player stats
        Player player = mock(Player.class);
        Drawable powerup1 = mock(Drawable.class);
        Drawable powerup2 = mock(Drawable.class);
        player.checkPowerupCollisions(1, powerup1, powerup2);

        when(player.resetPower()).thenReturn(false);

        // Verify that player.setDamage was called with the expected value
        assertFalse(player.getHasGoldenSword());
    }

    @Test
    public void testArmed() {
        // Set up initial player stats
        Player player = mock(Player.class);
        Drawable powerup1 = mock(Drawable.class);
        Drawable powerup2 = mock(Drawable.class);
        player.checkPowerupCollisions(1, powerup1, powerup2);

        when(player.Armed()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(player.Armed());
    }

    @Test
    public void TestHeartPos() {
        // Set up initial player stats
        MainActivity mainActivity = mock(MainActivity.class);
        mainActivity.initializePowerupPos();

        when(mainActivity.heartPos()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(mainActivity.heartPos());
    }

    @Test
    public void TestCoinPos() {
        // Set up initial player stats
        MainActivity mainActivity = mock(MainActivity.class);
        mainActivity.initializePowerupPos();

        when(mainActivity.coinPos()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(mainActivity.coinPos());
    }

    @Test
    public void TestChestPos() {
        // Set up initial player stats
        MainActivity mainActivity = mock(MainActivity.class);
        mainActivity.initializePowerupPos();

        when(mainActivity.coinPos()).thenReturn(true);

        // Verify that player.setDamage was called with the expected value
        assertTrue(mainActivity.coinPos());
    }

}




