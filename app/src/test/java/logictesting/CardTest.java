package logictesting;

import logictesting.Logic.Card;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testGetPoints() {
        Card numberCard = new Card(Card.Type.NUMBER, Card.Color.RED, 5);
        assertEquals(5, numberCard.getPoints());

        Card drawTwoCard = new Card(Card.Type.DRAW_TWO, Card.Color.BLUE, -1);
        assertEquals(20, drawTwoCard.getPoints());

        Card skipCard = new Card(Card.Type.SKIP, Card.Color.GREEN, -1);
        assertEquals(20, skipCard.getPoints());

        Card reverseCard = new Card(Card.Type.REVERSE, Card.Color.YELLOW, -1);
        assertEquals(20, reverseCard.getPoints());

        Card wildCard = new Card(Card.Type.WILD, Card.Color.WILD, -1);
        assertEquals(50, wildCard.getPoints());

        Card wildDrawFourCard = new Card(Card.Type.WILD_DRAW_FOUR, Card.Color.WILD, -1);
        assertEquals(50, wildDrawFourCard.getPoints());

         
    }

    @Test
    public void testToString() {
        Card numberCard = new Card(Card.Type.NUMBER, Card.Color.YELLOW, 8);
        assertEquals("YELLOW 8", numberCard.toString());

        Card wildCard = new Card(Card.Type.WILD, Card.Color.WILD, -1);
        assertEquals("WILD WILD", wildCard.toString());

        Card drawTwoCard = new Card(Card.Type.DRAW_TWO, Card.Color.RED, -1);
        assertEquals("RED DRAW_TWO", drawTwoCard.toString());

         
    }

    @Test
    public void testIsSameCard() {
        Card card1 = new Card(Card.Type.NUMBER, Card.Color.RED, 3);
        Card card2 = new Card(Card.Type.NUMBER, Card.Color.RED, 3);
        assertTrue(card1.isSameCard(card2));

        Card wildCard1 = new Card(Card.Type.WILD, Card.Color.WILD, -1);
        Card wildCard2 = new Card(Card.Type.WILD, Card.Color.WILD, -1);
        assertTrue(wildCard1.isSameCard(wildCard2));

        Card drawTwoCard1 = new Card(Card.Type.DRAW_TWO, Card.Color.GREEN, -1);
        Card drawTwoCard2 = new Card(Card.Type.DRAW_TWO, Card.Color.BLUE, -1);
        assertFalse(drawTwoCard1.isSameCard(drawTwoCard2));

        Card reverseCard1 = new Card(Card.Type.REVERSE, Card.Color.YELLOW, -1);
        Card reverseCard2 = new Card(Card.Type.REVERSE, Card.Color.BLUE, -1);
        assertFalse(reverseCard1.isSameCard(reverseCard2));
 
    }
    @Test
    public void testClone() {
        Card originalCard = new Card(Card.Type.NUMBER, Card.Color.GREEN, 7);
        Card clonedCard = originalCard.clone();
        assertNotSame(originalCard, clonedCard);
        assertTrue(originalCard.isSameCard(clonedCard));
    }

    @Test
    public void testSetColor() {
        Card card = new Card(Card.Type.NUMBER, Card.Color.BLUE, 4);
        assertEquals(Card.Color.BLUE, card.colorOfCard());

        card.setColor(Card.Color.RED);
        assertEquals(Card.Color.RED, card.colorOfCard());
    }

}
