package logictesting;

import logictesting.Logic.Board;
import logictesting.Logic.Card;
import logictesting.Logic.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        // Create a new Board instance before each test
        board = new Board(2, false);
    }

    @Test
    public void testPlayCard() {
    
        Board board = new Board(4,false);

        Card playerChoosenCard = new Card(Card.Type.NUMBER, Card.Color.RED, 5);  
        Card.Color botColor = Card.Color.BLUE;  

     
        board.playCard(playerChoosenCard, botColor);

        
        assertTrue(board.canPlay);  

     
        assertFalse(board.nextPlayer || board.drawCard);

        if (board.endGame) {
            assertFalse(board.canPlay);
            assertFalse(board.nextPlayer);
            assertFalse(board.drawCard);
            assertFalse(board.unoClicked);}
           
        }
    @Test
    public void testClone() {
        Board clonedBoard = board.clone();
        assertNotSame(board, clonedBoard);
        assertEquals(board.getPlayers().size(), clonedBoard.getPlayers().size());
      
    }

    @Test
    public void testCanPlay() {
        // Your test logic for the canPlay method
        // ...
    }
    @Test
    public void testRestartDecks() {
        // Test the restartDecks method
        board.restartDecks();
        // Add assertions to verify that all player decks are cleared
        for (Player player : board.getPlayers()) {
            assertTrue(player.getDeck().isEmpty());
        }
    }

    @Test
    public void testGetPlayers() {
        // Test the getPlayers method
        assertNotNull(board.getPlayers());
        assertEquals(2, board.getPlayers().size());
        // Add more assertions as needed
    }

    @Test
    public void testSetBoardCard() {
        // Test the setBoardCard method
        Card newCard = new Card(Card.Type.NUMBER, Card.Color.BLUE, 5);
        board.setBoardCard(newCard);
        assertEquals(newCard, board.getBoardCard());
    }

    // Add more test methods as needed
}
