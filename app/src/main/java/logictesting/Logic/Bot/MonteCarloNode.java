package logictesting.Logic.Bot;

import java.lang.reflect.Array;
import java.util.*;

import logictesting.Logic.Board;
import logictesting.Logic.Card;
import logictesting.Logic.Player;
import logictesting.Logic.State;

public class MonteCarloNode {
    Card boardCard;
    ArrayList<Card> deck;
    private int visitCount;
    private int totalScore;
    private ArrayList<MonteCarloNode> childNodes;
    public ArrayList<Card> maindeck;

    public MonteCarloNode(ArrayList<Card> deck, Card boardCard) {
        this.boardCard = boardCard.clone();
        this.deck = cloneDeck(deck);
        this.visitCount = 1;
        this.totalScore = 0;
        this.childNodes = new ArrayList<>();
    }

    private ArrayList<Card> cloneDeck(ArrayList<Card> deck) {
        // Function to create a new deck with cloned cards of the old deck.
        ArrayList<Card> newDeck = new ArrayList<Card>();
        for (Card card: deck) {
            newDeck.add(card.clone());
        }
        return newDeck;
    }

    private ArrayList<Card> createArbitraryDeck (int nrOfCards) {
        generateMainDeck();
        ArrayList<Card> arbitraryDeck = new ArrayList<Card>();

        for (int i=0; i<nrOfCards; i++) {
            arbitraryDeck.add(maindeck.get(0));
            maindeck.remove(0);
        }

        return arbitraryDeck;
    }

    public void RollOut() {

        // Initialize a random player and a mcplayer.
        Player randomBot1 = new RandomBot(1);
        randomBot1.setDeck(createArbitraryDeck(deck.size()));

        Player mcBot = new RandomBot(2);
        mcBot.setDeck(deck);

        // Give mc bot his deck, give randomBot a random deck with same length as mcBot.

        ArrayList<Player> players = new ArrayList<Player>();
        players.add(randomBot1);
        players.add(mcBot);

        Board board = new Board(players, boardCard);
        int winner  = board.winner;

        incrementVisitCount();

        if(winner == 2){
            addToTotalScore(1);
        }
        else{
            addToTotalScore(0);
        }
    }

    public void expandNode() {
        for (Card card : deck){
            if(checkChoose(card)){
                ArrayList<Card> nextDeck = new ArrayList<>();

                for (int i = 0; i < deck.size(); i++) {
                    if(deck.get(i) != card){
                        nextDeck.add(deck.get(i));
                    }
                }
                childNodes.add(new MonteCarloNode(nextDeck,card));
            }
        }
    }

    public void incrementVisitCount() {
        this.visitCount++;
    }

    public void addToTotalScore(int score) {
        this.totalScore += score;
    }

    public void calcTotalScore () {
        // Method that calculates the total score for a node
        // This consists of the total score of itself + sum( total score of the children )
        int scoreOfChildren = 0;

        for (MonteCarloNode child : childNodes) {
            scoreOfChildren += child.getTotalScore();
        }

        totalScore = totalScore + scoreOfChildren;
    }

    public boolean checkChoose(Card playerChosenCard){


        // check the color of cards
        if (playerChosenCard.colorOfCard() == boardCard.colorOfCard() || playerChosenCard.colorOfCard() == Card.Color.BLUE){
            return true;
        }

        // check the wild cards
        if (playerChosenCard.getType() == Card.Type.WILD){
            return true;
        }
        // check the wild draw cards
        if (playerChosenCard.getType() == Card.Type.WILD_DRAW_FOUR)
        {
            for (Card card: deck) {
                if (card.getType() == Card.Type.WILD){
                    continue;
                }
                //    if (checkChoose(card))
                //        return false;
            }

            return true;
        }

        // check draw2 cards
        // ALLOWS TO COMBOS
        if (boardCard.getType() == Card.Type.DRAW_TWO) {
            if (playerChosenCard.getType() == Card.Type.DRAW_TWO) {
                return true;
            } else if (playerChosenCard.colorOfCard() == boardCard.colorOfCard()) {
                return true;
            } else {
                return false;
            }
        }
        // revers card case
        if (boardCard.getType().equals(Card.Type.REVERSE) && playerChosenCard.getType().equals(Card.Type.REVERSE)){
            return true;
        }
        // check the number of number cards
        if (playerChosenCard.getType().equals(Card.Type.NUMBER) && boardCard.getType().equals(Card.Type.NUMBER)){
            if (playerChosenCard.getNumber() == boardCard.getNumber()){
                return true;
            }
        }
        // check the skip cards
        if (playerChosenCard.getType().equals(Card.Type.SKIP) && boardCard.getType().equals(Card.Type.SKIP)){
            return true;
        }
        return false;
    }

    public void generateMainDeck(){
        maindeck = new ArrayList<>();
        // Generate number and special cards (0-9) for each color, and (1-9) for each color
        for (int i = 0; i < 2; i++) {
            for (Card.Color color : Card.Color.values()) {

                if (color != Card.Color.WILD) {

                    for (int number = i; number <= 9; number++) {

                        maindeck.add(new Card(Card.Type.NUMBER , color,  number));

                        maindeck.add(new Card( Card.Type.NUMBER , color,  number));
                    }
                    this.maindeck.add(new Card( Card.Type.SKIP, color, -1));
                    this.maindeck.add(new Card( Card.Type.REVERSE, color, -1));
                    this.maindeck.add(new Card( Card.Type.DRAW_TWO, color, -1));
                }
            }
        }
        // Generate Wild and Wild Draw Four cards
        for (int i = 0; i < 4; i++) {
            this.maindeck.add(new Card( Card.Type.WILD, Card.Color.WILD, -1));
            this.maindeck.add(new Card( Card.Type.WILD_DRAW_FOUR, Card.Color.WILD, -1));
        }

        ArrayList<Card> removeCards = new ArrayList<>();

        for (Card mainCard : maindeck) {
            for (Card deckCard : deck) {
                if (deckCard.isSameCard(mainCard)) {
                    removeCards.add(mainCard);
                    break;
                }
            }
        }

        maindeck.remove(removeCards);
        Collections.shuffle(maindeck);
    }

    // Getter and setter methods

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    public void setTotalScore() {
        calcTotalScore();
    }

    public ArrayList<MonteCarloNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(ArrayList<MonteCarloNode> childNodes) {
        this.childNodes = childNodes;
    }
}