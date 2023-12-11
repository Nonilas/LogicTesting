package logictesting.Logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

 

import logictesting.Logic.Bot.Bot;

import java.io.FileWriter;
import java.io.IOException;

public class Simulation{
    
    public Board board;
    public boolean nextPlayer = false;
    public boolean drawCard = false;
    public boolean unoClicked = false;
    public Card playerChoosenCard;
    public Card.Color color;
    public boolean canPlay = true;
    public String text;
    public int nextIndex;
    public int numberOfSimulations = 0 ;
    public int numberOfTurns;
    public ArrayList<Integer> simulationResults = new ArrayList<>();
    public State state;
    public int simAmount;

    public Simulation(Board board, int simAmount){
        this.board = board;
        this.simAmount = simAmount;
        state = new State(board, this);
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void setSimAmount(int simAmount){
        this.simAmount = simAmount;
    }

    public void run(){

        while(true){
            numberOfTurns++;

            Card.Color botColor = Card.Color.BLUE;

            if(canPlay){
                if(board.penaltyCase()){
                    text = "penalty Cards were taken Move to the Next Player";
                    nextIndex = (board.getCurrentIndex()+1)%board.getPlayers().size();
                    canPlay = false;
                    nextPlayer = true;
                }
                else if(board.checkTheCards()){
                    text = "Take a Card From Deck";
                    nextIndex = (board.getCurrentIndex()+1)%board.getPlayers().size();
                    Card c = board.giveCardToPlayer();
                    if(board.checkChoose(c)){
                        nextIndex = board.playTurn(c);
                        drawCard = false;
                        canPlay = false;
                        nextPlayer = true;
                        text = "Card was drawn and played";
                        if(board.chooseColor){
                            board.setBoardColor(botColor);
                        }
                    }
                    else{
                        nextIndex = (board.getCurrentIndex()+1)%board.getPlayers().size();
                        drawCard = false;
                        canPlay = false;
                        nextPlayer = true;
                        text = "Card was drawn";
                    }
                }
                else{

                    try {
                        Bot bot = (Bot) board.getCurrentPlayer();
                        bot.setState(board.getCurrentPlayer().getDeck(), board.boardCard, board.getBoardColor(), board);
                        playerChoosenCard = bot.selectCard();
                    } catch (Exception e) {
                        // Handle other exceptions
                        e.printStackTrace();  // or log the exception
                    }

                    

                    nextIndex = board.playTurn(playerChoosenCard);
                    playerChoosenCard.clickedCard = false;
                    if(board.setIndex){
                        canPlay = false;
                        nextPlayer = true;
                        board.setIndex = false;
                        if(board.chooseColor){
                            board.setBoardColor(botColor);
                        }
                        board.chooseColor = false;
                    } 
                }
            }

            if(board.getCurrentPlayer().getDeck().size() == 1){
                unoClicked = true;
            }

            state.setState();
            state.saveStateToCSV();

            if(endGame()){
                numberOfTurns = 0;

                if(numberOfSimulations == simAmount){
                    break;
                }
                else{
                    Collections.shuffle(board.players);
                    board.initiliaze();
                }
            }
            else{
                switchToNextPlayer();
            }
        }
    }  

    public void setPlayerChoosenCard(Card card){
        playerChoosenCard = card;
    }

    public boolean endGame(){
        if(board.getCurrentPlayer().getDeck().size() == 0){
            canPlay = false;
            numberOfSimulations++;
            return true;
        }
        return false;
    }

    public void switchToNextPlayer(){
        if(nextPlayer){
            if(board.getCurrentPlayer().getDeck().size() == 1){
                if(!unoClicked){
                    if(board.getMaindeck().size() == 0){
                        board.generatemainDeck();
                    }
                    board.getCurrentPlayer().addCard(board.maindeck.get(0));
                    board.maindeck.remove(0);
                }
                else{
                    unoClicked = false;
                }
            }
            playerChoosenCard = null;
            board.setCurrentIndex(nextIndex);
            nextIndex = -1;
            nextPlayer = false;
            canPlay = true;
            text = "";
        }        
    }
}