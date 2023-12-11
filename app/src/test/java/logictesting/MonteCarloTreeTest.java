package logictesting;
 

import logictesting.Logic.Bot.MonteCarloNode;
import logictesting.Logic.Bot.MonteCarloTree;
import logictesting.Logic.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class MonteCarloTreeTest {

    @Test
    public void testMonteCarloNode() {
        ArrayList<Card> deck = new ArrayList<>();
        deck.add(new Card(Card.Type.NUMBER, Card.Color.RED, 5));
        deck.add(new Card(Card.Type.SKIP, Card.Color.BLUE, -1));

        MonteCarloNode monteCarloNode = new MonteCarloNode(deck, new Card(Card.Type.NUMBER, Card.Color.RED, 5));

        assertNotNull(monteCarloNode);
        assertNotNull(monteCarloNode.getChildNodes());
        assertEquals(1, monteCarloNode.getVisitCount());
        assertEquals(0, monteCarloNode.getTotalScore());
         
        assertEquals(0, monteCarloNode.getChildNodes().size());

        // Add more assertions as needed
    }

    
    }

    // Add more test methods for MonteCarloNode and MonteCarloTree as needed


