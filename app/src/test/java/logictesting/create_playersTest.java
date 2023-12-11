package logictesting;

import org.junit.jupiter.api.Test;

import logictesting.Logic.create_players;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class create_playersTest {

    @Test
    public void testNamesOfPlayers() {
        create_players createPlayers = new create_players();

        // Test for 2 real players, 3 AI with ML, and 1 AI with Search
        List<String> allNames = createPlayers.names_of_players(2, 3, 1);

        // Check the size of the list
        assertEquals(6, allNames.size());

        // Check individual names
        assertTrue(allNames.contains("Player_1"));
        assertTrue(allNames.contains("Player_2"));
        assertTrue(allNames.contains("Machine_Learning_AI_Player_1"));
        assertTrue(allNames.contains("Machine_Learning_AI_Player_2"));
        assertTrue(allNames.contains("Machine_Learning_AI_Player_3"));
        assertTrue(allNames.contains("Search_based_AI_Player_1"));
    }

    @Test
    public void testNamesOfPlayersWithZeroPlayers() {
        create_players createPlayers = new create_players();

        // Test with zero players of each type
        List<String> allNames = createPlayers.names_of_players(0, 0, 0);

        // Check the size of the list (it should be zero)
        assertEquals(0, allNames.size());
    }

    @Test
    public void testNamesOfPlayersWithNegativePlayers() {
        create_players createPlayers = new create_players();

        // Test with negative players (should be treated as zero)
        List<String> allNames = createPlayers.names_of_players(-1, -2, -3);

        // Check the size of the list (it should be zero)
        assertEquals(0, allNames.size());
    }
}