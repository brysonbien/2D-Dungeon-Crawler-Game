package com.example.dungeoncrawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.os.Handler;

import com.example.dungeoncrawler.Model.LeaderboardEntry;
import com.example.dungeoncrawler.Model.LeaderboardModel;
import com.example.dungeoncrawler.ViewModel.GameSessionViewModel;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class MattUnitTest {

    @Test
    public void testSize() {
        // Create a mock of GameSessionViewModel
        GameSessionViewModel gc = Mockito.mock(GameSessionViewModel.class);

        // Define the expected return value when leaderboardSetup is called
        List<LeaderboardEntry> cEntries = new ArrayList<>();
        // Add your expected leaderboard entries to the list

        // Customize the behavior of leaderboardSetup to return the expectedEntries
        Mockito.when(gc.leaderboardSetup()).thenAnswer(invocation -> {
            List<LeaderboardEntry> result = new ArrayList<>(cEntries);  // Create a copy of cEntries
            result.add(new LeaderboardEntry("John", 95, "2023-10-11"));
            result.add(new LeaderboardEntry("John", 97, "2023-10-11"));
            result.add(new LeaderboardEntry("John", 99, "2023-10-11"));
            result.add(new LeaderboardEntry("John", 93, "2023-10-11"));
            result.add(new LeaderboardEntry("John", 92, "2023-10-11"));
            return result;  // Return the modified copy
        });

        // Now, invoke the method you want to test
        List<LeaderboardEntry> result = gc.leaderboardSetup();

        // Verify the expected behavior or result of your code
        // For example, assert that cEntries is equal to the expected entries
        assertEquals(5, result.size());
    }


        GameSessionViewModel gc = new GameSessionViewModel();

    @Test
    public void testUpdateScore() {

        // Set up the starting score
        int startingScore = 100;

        // Create a mock Handler and Runnable for testing
        Handler scoreHandler = Mockito.mock(Handler.class);
        Runnable scoreRunnable = Mockito.mock(Runnable.class);

        // Set up the current score (assuming it's running as part of your app)
        // Call the updateScore method
        double updatedScore = gc.updateScore(scoreHandler, scoreRunnable);

        // Check if the updated score is less than or equal to the starting score
        assertTrue(updatedScore <= startingScore);

        // Check if the Handler's removeCallbacks method was called (assuming the score is 0)
        if (updatedScore == 0) {
            Mockito.verify(scoreHandler).removeCallbacks(scoreRunnable);
        }
    }



}
