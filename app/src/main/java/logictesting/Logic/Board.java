package logictesting.Logic;

import java.util.ArrayList;
 
import java.util.Collections;
 

 
import logictesting.Logic.Bot.MonteCarloBot;
import logictesting.Logic.Bot.RandomBot;
import logictesting.Logic.Bot.Bot;

public class Board implements Cloneable {
    public ArrayList<Player> players;
    private int currentIndex;
    public ArrayList<Card> maindeck;
    public Card boardCard;
    private Card.Color boardColor;
    private ArrayList<Card> penaltyCards;
    public boolean chooseColor;
    public boolean setIndex = false;
    public boolean isClockwise = true; // true for clockwise, false for counter-clockwise
    public boolean simulation;

    public boolean endGame = false;
    public boolean nextPlayer = false;
    public boolean drawCard = false;
    public boolean unoClicked = false;
    public Card playerChoosenCard;
    public boolean canPlay = true;
    public int nextIndex;
    public int winner = -1;


    public Board(int num, boolean simulation) {
        this.simulation = simulation;
        this.maindeck = new ArrayList<>();
        this.players = new ArrayList<>();
        this.penaltyCards = new ArrayList<>();
        this.currentIndex = 0;
        generatemainDeck();
        setPlayers(num);
        distributeCards();
    }

    public Board(ArrayList<Player> players , Card boardCard){
        this.simulation = true;
        maindeck = new ArrayList<>();
        this.players = players;
        this.penaltyCards = new ArrayList<>();
        this.currentIndex = 0;
        this.boardColor = boardCard.colorOfCard();
        this.boardCard = boardCard;
        generatemainDeck();

        simulate();
    }

    public  void simulate(){
        while(!endGame){

            Bot bot = (Bot) getCurrentPlayer();
            bot.setState(getCurrentPlayer().getDeck(), boardCard, getBoardColor(),this);
            playerChoosenCard = bot.selectCard();
            Card.Color botColor = bot.selectColor();

            playCard(playerChoosenCard,botColor);
        }

        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getDeck().size() == 0){
                winner = players.get(i).getNumber();
            }
        }
    }

    public void playCard(Card playerChoosenCard, Card.Color botColor){
        this.playerChoosenCard = playerChoosenCard;
        if(canPlay){
            if(penaltyCase()){
                nextIndex = (getCurrentIndex()+1)%getPlayers().size();
                canPlay = false;
                nextPlayer = true;
            }
            else if(checkTheCards()){
                nextIndex = (getCurrentIndex()+1)%getPlayers().size();
                Card c = giveCardToPlayer();
                if(checkChoose(c)){
                    nextIndex = playTurn(c);
                    drawCard = false;
                    canPlay = false;
                    nextPlayer = true;
                    if(chooseColor){
                        setBoardColor(botColor);
                    }
                }
                else{
                    nextIndex = (getCurrentIndex()+1)%getPlayers().size();
                    drawCard = false;
                    canPlay = false;
                    nextPlayer = true;
                }
                
            }
            else if(playerChoosenCard != null){
                nextIndex = playTurn(playerChoosenCard);
                playerChoosenCard.clickedCard = false;
                if(setIndex){
                    canPlay = false;
                    nextPlayer = true;
                    setIndex = false;
                    if(chooseColor){
                        setBoardColor(botColor);
                    }
                    chooseColor = false;
                } 
            }
        }
        if(getCurrentPlayer().getDeck().size() == 1){
            unoClicked = true;
        }

        if(endGame()){
            endGame = true;
        }
        else{
            switchToNextPlayer();
        }
    }

    public boolean endGame(){
        if(getCurrentPlayer().getDeck().size() == 0){
            canPlay = false;
            return true;
        }
        return false;
    }
    
    @Override
    public Board clone() {
        try {
            // Perform a shallow copy by calling the superclass clone method
            return (Board) super.clone();
        } catch (CloneNotSupportedException e) {
            // Handle the exception if necessary
            return null;
        }
    }


    public boolean canPlay(){
        if(penaltyCase() || checkTheCards()){
            //go the the next player
            return true;
        }
        return false;
    }

    public void initiliaze(){
        this.maindeck = new ArrayList<>();
        this.penaltyCards = new ArrayList<>();
        this.currentIndex = 0;
        generatemainDeck();
        restartDecks();
        distributeCards();
    }

    public void restartDecks(){
        for (int i = 0; i < players.size(); i++) {
            players.get(i).getDeck().clear();
        }
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }
    public ArrayList<Card> getMaindeck() {
        return maindeck;
    }
    public void setMaindeck(ArrayList<Card> maindeck) {
        this.maindeck = maindeck;
    }
    public int getCurrentIndex(){
        return currentIndex;
    }
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Player getCurrentPlayer(){
        return players.get(currentIndex);
    }

    public void setPlayers(int num) {
        // Pas dit aan naar het gewenste aantal spelers

        int botName1 = 1;
        Player monteCarloBot = new MonteCarloBot(botName1);
        players.add(monteCarloBot);

        int botName2 = 2;
        Player randomBot1 = new RandomBot(botName2);
        players.add(randomBot1);



        // int botName3 = 3;
        // Player randomBot2 = new RandomBot(botName3);
        // players.add(randomBot2);

        // int botName2 = 2;
        // Player randomBot2 = new RandomBot(botName2);
        // players.add(randomBot2);

        // String playername = "Player";
        // Player player = new Player(playername);
        // players.add(player);

        // for (int i = 1; i <= num; i++) {
        //     String playerName = "Player " + i;
        //     Player player = new Player(playerName);
        //     players.add(player);
        // }
    }
    public void setBoardCard(Card boardCard) {
        this.boardCard = boardCard;
    }
    public Card getBoardCard() {
        return boardCard;
    }
    public void setBoardColor(Card.Color boardColor) {
        this.boardColor = boardColor;
    }
    public Card.Color getBoardColor() {
        return boardColor;
    }
    public ArrayList<Card> getPenaltyCards() {
        return penaltyCards;
    }
    public void setPenaltyCards(ArrayList<Card> penaltyCards) {
        this.penaltyCards = penaltyCards;
    }

    public void generatemainDeck() {
        if(!simulation){
            // Generate number and special cards (0-9) for each color, and (1-9) for each color
            for (int i = 0; i < 2; i++) {
                for (Card.Color color : Card.Color.values()) {

                    if (color != Card.Color.WILD) {
                        
                        for (int number = i; number <= 9; number++) {

                             
                            this.maindeck.add(new Card( Card.Type.NUMBER , color,  number));
                        }
                        
                        
                        this.maindeck.add(new Card( Card.Type.SKIP, color, -1));


                        
                        this.maindeck.add(new Card(Card.Type.REVERSE, color, -1));

 
                        this.maindeck.add(new Card( Card.Type.DRAW_TWO, color, -1));
                    }
                }
            }
            // Generate Wild and Wild Draw Four cards
            for (int i = 0; i < 4; i++) {
                
                this.maindeck.add(new Card( Card.Type.WILD, Card.Color.WILD, -1));
 
                this.maindeck.add(new Card(Card.Type.WILD_DRAW_FOUR, Card.Color.WILD, -1));
            }  
        }
        else{
            // Generate number and special cards (0-9) for each color, and (1-9) for each color
            for (int i = 0; i < 2; i++) {
                for (Card.Color color : Card.Color.values()) {

                    if (color != Card.Color.WILD) {
                        
                        for (int number = i; number <= 9; number++) {

                            this.maindeck.add(new Card(  Card.Type.NUMBER , color,  number));

                            this.maindeck.add(new Card( Card.Type.NUMBER , color,  number));
                        }
                        this.maindeck.add(new Card(Card.Type.SKIP, color, -1));
                        this.maindeck.add(new Card(Card.Type.REVERSE, color, -1));
                        this.maindeck.add(new Card(Card.Type.DRAW_TWO, color, -1));
                    }
                }
            }
            // Generate Wild and Wild Draw Four cards
            for (int i = 0; i < 4; i++) {
                this.maindeck.add(new Card( Card.Type.WILD, Card.Color.WILD, -1));
                this.maindeck.add(new Card( Card.Type.WILD_DRAW_FOUR, Card.Color.WILD, -1));
            }
        }
        
        // Shuffle the deck
        Collections.shuffle(this.maindeck);
    }

    public void distributeCards(){

        for (int n = 0; n < 7; n++) {
            for (Player p: players) {
                // get the card to the player
                p.addCard(this.maindeck.get(0));
                this.maindeck.remove(0);
            }
        }

        // set the board card
        while (!(this.maindeck.get(0).getType() == Card.Type.NUMBER))
        {
            boardCard = this.maindeck.get(0);
            this.maindeck.remove(0);
            this.maindeck.add(boardCard);
        }

        boardCard = this.maindeck.get(0);
        boardColor = boardCard.colorOfCard();
        this.maindeck.remove(0);
    }

    public int playTurn(Card playerChoosenCard) {

        if(checkChoose(playerChoosenCard)){
            players.get(currentIndex).removeCard(playerChoosenCard);

            if (playerChoosenCard.getType() == Card.Type.WILD || playerChoosenCard.getType() == Card.Type.WILD_DRAW_FOUR){
                //ask the player choosen color
                applyChoose(playerChoosenCard,Card.Color.RED);
                chooseColor = true;
            }
            else{
                applyChoose(playerChoosenCard, playerChoosenCard.colorOfCard());
            }
            return setIndex(playerChoosenCard, currentIndex);
        }

        return currentIndex;
        
    }

    public boolean penaltyCase(){
        // the draw case

        if (penaltyCards.size() != 0)
        {
            int n = penaltyCards.size();
            for (; n > 0; n--)
            {
                players.get(currentIndex).addCard(penaltyCards.get(0));
                penaltyCards.remove(0);
            }
            return true;
        }
        return  false;
    }

    public boolean checkChoose(Card playerChosenCard){

        // check the color of cards
        if (playerChosenCard.colorOfCard() == boardColor){
            return true;
        }

        // check the wild cards
        if (playerChosenCard.getType() == Card.Type.WILD){
            return true;
        } 
        // check the wild draw cards
        if (playerChosenCard.getType() == Card.Type.WILD_DRAW_FOUR)
        {
            for (Card card: players.get(currentIndex).getDeck()) {
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
        if (boardCard.getType() == Card.Type.DRAW_TWO && penaltyCards.size() == 0) {
            if (playerChosenCard.getType() == Card.Type.DRAW_TWO) {
                return true;
            } else if (playerChosenCard.colorOfCard() == boardColor) {
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



    public boolean checkTheCards(){
        // check the player cards
        if (!checkPlayerCards(players.get(currentIndex)))
        {
            return true;
        }
        return false;
    }

    public  Card giveCardToPlayer()
    {   
        if(maindeck.size()==0){
            generatemainDeck();
        }

        Card c = maindeck.get(0);
        players.get(currentIndex).addCard(maindeck.get(0));
        maindeck.remove(0);
        return c;
    }

    private  boolean checkPlayerCards(Player player)
    {
        for (Card card: player.getDeck())
        {
            if (checkChoose(card))
                return true;
        }
        return false;
    }
   
    public void applyChoose(Card playerChoosenCard, Card.Color choosenColor)
    {   
        changeBoardCard(playerChoosenCard);
        boardColor = choosenColor;
        if (playerChoosenCard.getType().equals(Card.Type.WILD_DRAW_FOUR))
        {
            for (int n = 0; n < 4; n++)
            {
                if(maindeck.size() == 0){
                    generatemainDeck();
                }
                penaltyCards.add(maindeck.get(0));
                maindeck.remove(0);
            }
        }
        else if (playerChoosenCard.getType().equals(Card.Type.DRAW_TWO))
        {
            for (int n = 0; n < 2; n++)
            {
                if(maindeck.size() == 0){
                    generatemainDeck();
                }
                penaltyCards.add(maindeck.get(0));
                maindeck.remove(0);
            }
        } 
    }

    private void changeBoardCard(Card newCard)
    {
        boardCard = newCard;
    }

    public int setIndex(Card playerChoosenCard, int currentPlayerindex)
    {
        // skip card case
        if (playerChoosenCard.getType() == Card.Type.SKIP){
            setIndex = true;
            return (currentPlayerindex + 2)%players.size();
        }

        // reverse card case
        else if (playerChoosenCard.getType() == Card.Type.REVERSE)
        {
            currentPlayerindex =  reversePlayers(currentPlayerindex);
            currentIndex = currentPlayerindex;
            setIndex = true;
            return (currentPlayerindex+1)%players.size();
        }
    
        else{
            setIndex = true;
            return (currentPlayerindex+1)%players.size();
        }
    }

    private  int reversePlayers(int currentPlayerIndex)
    {
        // hold the player for swap
        Player holdPlayer = players.get(currentPlayerIndex);
        Collections.reverse(players);
        return currentPlayerIndex = players.indexOf(holdPlayer);   
    }

    public void switchToNextPlayer(){
        if(nextPlayer){
            if(getCurrentPlayer().getDeck().size() == 1){
                if(!unoClicked){
                    if(getMaindeck().size() == 0){
                        generatemainDeck();
                    }
                    getCurrentPlayer().addCard(maindeck.get(0));
                    maindeck.remove(0);
                }
                else{
                    unoClicked = false;
                }
            }
            playerChoosenCard = null;
            setCurrentIndex(nextIndex);
            nextIndex = -1;
            nextPlayer = false;
            canPlay = true;
        }
    }
}