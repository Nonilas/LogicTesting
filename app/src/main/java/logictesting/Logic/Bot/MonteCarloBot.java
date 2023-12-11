package logictesting.Logic.Bot;

import java.util.ArrayList;

import logictesting.Logic.Board;
import logictesting.Logic.Card;
import logictesting.Logic.Card.Color;
import logictesting.Logic.Player;    

 

 
 

public class MonteCarloBot extends Player implements Bot{

    ArrayList<Card> deck;
    Card boardCard;
    Card.Color boardColor;
    Board board;
    public MonteCarloTree monteCarloTree;

    public MonteCarloBot(int number){
        super(number);
    }

    @Override
    public void makeMove() {
        // TODO Auto-generated method stub
    }

    @Override
    public Card selectCard() {
        // TODO Auto-generated method stub
        monteCarloTree = new MonteCarloTree();
        MonteCarloNode rootNode = new MonteCarloNode(deck, boardCard);

        Card card = monteCarloTree.search(rootNode);

        if(card != null){
            for (int i = 0; i < deck.size(); i++) {
                if(deck.get(i).isSameCard(card)){
                    return deck.get(i);
                }
            }
        }
        return null;
        }

    @Override
    public Color selectColor() {
        // TODO Auto-generated method stub
        return Card.Color.BLUE;
    }

    @Override
    public void setState(ArrayList<Card> deck, Card boardCard, Color boardColor, Board board) {
        // TODO Auto-generated method stub
        this.deck = deck;
        this.boardCard = boardCard;
        this.boardColor = boardColor;
        this.board = board;
    }
}