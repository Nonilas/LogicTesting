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
        player = new Player(1);
    }
    @Test
    public void testCheckCards() {
        Card card = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        player.addCard(card);

        assertTrue(player.checkCards(card));
    }
    @Test
    public void testGetDeck() {
        assertNotNull(player.getDeck());
        assertTrue(player.getDeck().isEmpty());

    }
    @Test
    public void testAddCard() {
        Card card = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        player.addCard(card);
        assertEquals(1, player.getDeck().size());
        assertTrue(player.getDeck().contains(card));

    }
    @Test
    public void testRemoveCard() {
        Card card = new Card(Card.Type.NUMBER, Card.Color.RED, 5);

        player.addCard(card);
        player.removeCard(card);
        assertTrue(player.getDeck().isEmpty());
    }
    @Test
    public void testCalculatePoints() {
        Card card1 = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        Card card2 = new Card(Card.Type.DRAW_TWO, Card.Color.BLUE,-1);
        player.addCard(card1);
        player.addCard(card2);
        assertEquals(25, player.calculatePoints());
    }
    @Test
    public void testGetDeckLength() {
        Card card1 = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        Card card2 = new Card(Card.Type.DRAW_TWO, Card.Color.BLUE,-1);
        player.addCard(card1);
        player.addCard(card2);
        assertEquals(2, player.getDeckLength());
    }
    @Test
    public void testGetNumber() {
        assertEquals(1, player.getNumber());
    }
    @Test
    public void testGetScore() {
        
        assertEquals(0, player.getScore());
    }
    @Test
    public void testSetDeck() {
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

        player.setScore(10);
        assertEquals(10, player.getScore());
    }
}
