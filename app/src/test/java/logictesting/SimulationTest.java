package logictesting;

import org.junit.jupiter.api.Test;

import logictesting.Logic.Board;
import logictesting.Logic.Simulation;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    public void testRun() {
        // Mock or create necessary objects for testing
        Board board = new Board(2,true);
        int simAmount = 1;  // Replace with your desired simulation amount
        Simulation simulation = new Simulation(board, simAmount);

        // Call the run method
        simulation.run();

        // Now, make assertions based on the expected behavior of the run method

         
        // Check the end game state
        assertTrue(simulation.endGame());
        assertFalse(simulation.canPlay);  // Assuming canPlay is false after the simulation
        assertEquals(0, simulation.board.getCurrentPlayer().getDeck().size());  // Assuming the game has ended
  

        // Additional assertions based on the expected behavior of your simulation
         
        assertFalse(simulation.nextPlayer);
        assertFalse(simulation.drawCard);
        assertFalse(simulation.unoClicked);

        // If you have methods to retrieve the game state from the State class, you can use them here
        // For example:
        // assertTrue(simulation.state.isGameOver());
        // assertEquals(expectedGameState, simulation.state.getGameState());
        
        // Check other properties of the board or simulation as needed
        assertNotNull(simulation.text);
        assertEquals(-1, simulation.nextIndex);  // Assuming it's set to -1 after the turn
    }
}
