package logictesting;

 

import logictesting.Logic.Card;
import logictesting.Logic.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        // Create a new Player instance before each test
        player = new Player(1);
    }

    @Test
    public void testCheckCards() {
        // Test the checkCards method
        Card card = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        player.addCard(card);
        assertTrue(player.checkCards(card));
    }

    @Test
    public void testGetDeck() {
        // Test the getDeck method
        assertNotNull(player.getDeck());
        assertTrue(player.getDeck().isEmpty());
    }

    @Test
    public void testAddCard() {
        // Test the addCard method
        Card card = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        player.addCard(card);
        assertEquals(1, player.getDeck().size());
        assertTrue(player.getDeck().contains(card));
    }

    @Test
    public void testRemoveCard() {
        // Test the removeCard method
        Card card = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        player.addCard(card);
        player.removeCard(card);
        assertTrue(player.getDeck().isEmpty());
    }

    @Test
    public void testCalculatePoints() {
        // Test the calculatePoints method
        Card card1 = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        Card card2 = new Card(Card.Type.DRAW_TWO, Card.Color.BLUE, -1);
        player.addCard(card1);
        player.addCard(card2);
        assertEquals(25, player.calculatePoints());
    }

    @Test
    public void testGetDeckLength() {
        // Test the getDeckLength method
        Card card1 = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        Card card2 = new Card(Card.Type.DRAW_TWO, Card.Color.BLUE, -1);
        player.addCard(card1);
        player.addCard(card2);
        assertEquals(2, player.getDeckLength());
    }

    @Test
    public void testGetNumber() {
        // Test the getNumber method
        assertEquals(1, player.getNumber());
    }

    @Test
    public void testGetScore() {
        // Test the getScore method
        assertEquals(0, player.getScore());
    }

    @Test
    public void testSetDeck() {
        // Test the setDeck method
        Card card1 = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        Card card2 = new Card(Card.Type.DRAW_TWO, Card.Color.BLUE, -1);
        player.addCard(card1);
        player.addCard(card2);

        ArrayList<Card> newDeck = new ArrayList<>();
        Card card3 = new Card(Card.Type.SKIP, Card.Color.GREEN, -1);
        newDeck.add(card3);

        player.setDeck(newDeck);

        assertEquals(newDeck, player.getDeck());
    }

    @Test
    public void testSetScore() {
        // Test the setScore method
        player.setScore(10);
        assertEquals(10, player.getScore());
    }

    // Add more test methods as needed
}
