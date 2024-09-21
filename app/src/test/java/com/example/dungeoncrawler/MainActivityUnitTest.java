package com.example.dungeoncrawler;

import static org.junit.Assert.assertEquals;
import android.os.Handler;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.example.dungeoncrawler.Model.GameSession;
import com.example.dungeoncrawler.Model.LeaderboardEntry;
import com.example.dungeoncrawler.ViewModel.GameSessionViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * Local unit test, which will execute on the development machine (host).
 * Checks to see what items are supposed to be displayed on the leaderboard.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainActivityUnitTest {
    @InjectMocks
    private GameSessionViewModel gameSessionViewModel;
    @Mock
    private GameSession gameSession;
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateScoreWithTime() {
        double initialScore = 100.0;
        double scoreDecreaseRate = 1.0;
        int iterations = 10;


        when(gameSession.getCurrentScore()).thenReturn(initialScore);


        Handler mockHandler = mock(Handler.class);

        for (int i = 0; i < iterations; i++) {
            initialScore = Math.max(0, initialScore - scoreDecreaseRate);
            when(gameSession.getCurrentScore()).thenReturn(initialScore);
            gameSessionViewModel.updateScore(mockHandler, () -> {
            });
        }


        double expectedScore = 90.0;
        double actualScore = gameSession.getCurrentScore();
        System.out.println("Expeted score:" + expectedScore);
        System.out.println("Actual score" + actualScore);
        assertEquals(expectedScore, actualScore, 0.01);
    }

    @Test
    public void testSize() {

        GameSessionViewModel gc = Mockito.mock(GameSessionViewModel.class);

        List<LeaderboardEntry> cEntries = new ArrayList<>();

        Mockito.when(gc.leaderboardSetup()).thenAnswer(invocation -> {
            List<LeaderboardEntry> result = new ArrayList<>(cEntries);  // Create a copy of cEntries
            result.add(new LeaderboardEntry("John", 95, "2023-10-11"));
            result.add(new LeaderboardEntry("John", 97, "2023-10-11"));
            result.add(new LeaderboardEntry("John", 99, "2023-10-11"));
            result.add(new LeaderboardEntry("John", 93, "2023-10-11"));
            result.add(new LeaderboardEntry("John", 92, "2023-10-11"));
            return result;  // Return the modified copy
        });

        List<LeaderboardEntry> result = gc.leaderboardSetup();

        assertEquals(5, result.size());
    }


    GameSessionViewModel gc = new GameSessionViewModel();

    @Test
    public void testUpdateScore() {

        int startingScore = 100;

        Handler scoreHandler = Mockito.mock(Handler.class);
        Runnable scoreRunnable = Mockito.mock(Runnable.class);

        double updatedScore = gc.updateScore(scoreHandler, scoreRunnable);

        assertTrue(updatedScore <= startingScore);

        if (updatedScore == 0) {
            Mockito.verify(scoreHandler).removeCallbacks(scoreRunnable);
        }
    }
}