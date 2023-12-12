package logictesting;

import logictesting.Logic.Bot.MonteCarloNode;
import logictesting.Logic.Bot.MonteCarloTree;
import logictesting.Logic.Card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class MonteCarloTreeTest {

    private MonteCarloNode monteCarloNode;
    private ArrayList<Card> deck;

    @BeforeEach
    public void setUp() {
        deck = new ArrayList<>();
        deck.add(new Card(Card.Type.NUMBER, Card.Color.RED, 5));
        deck.add(new Card(Card.Type.SKIP, Card.Color.BLUE, -1));
        monteCarloNode = new MonteCarloNode(deck, new Card(Card.Type.NUMBER, Card.Color.RED, 5));
    }

    @Test
    public void testMonteCarloNodeInitialization() {
        assertNotNull(monteCarloNode);
        assertNotNull(monteCarloNode.getChildNodes());
        assertEquals(1, monteCarloNode.getVisitCount());
        assertEquals(0, monteCarloNode.getTotalScore());
        assertEquals(0, monteCarloNode.getChildNodes().size());
    }

    @Test
    public void testNodeExpansion() {
        monteCarloNode.expandNode();
        assertTrue(monteCarloNode.getChildNodes().size() > 0);
    }

    @Test
    public void testRollOut() {
        monteCarloNode.RollOut();
        assertTrue(monteCarloNode.getTotalScore() >= 0);
    }

    @Test
    public void testSelectBestChildNode() {
        MonteCarloTree monteCarloTree = new MonteCarloTree();
        monteCarloTree.rootNode = monteCarloNode;
        monteCarloNode.expandNode();

        Card bestCard = monteCarloTree.search(monteCarloNode);
        assertNotNull(bestCard);
    }

    @Test
    public void testTotalScoreCalculation() {
        monteCarloNode.expandNode();
        monteCarloNode.calcTotalScore();
        assertTrue(monteCarloNode.getTotalScore() >= 0);
    }
    
    }

    


