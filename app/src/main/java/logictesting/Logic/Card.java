package logictesting.Logic;

 
 

public class Card implements Cloneable{
    private Type type;
    private Card.Color color;
    private int number;
    public boolean clickedCard = false;
 

    public Card( Card.Type type, Card.Color color, int number) {
        this.type = type;
        this.color = color;
        this.number = number;
    
    }

    public Card clone() {
        try {
            // Perform a shallow copy by calling the superclass clone method
            return (Card) super.clone();
        } catch (CloneNotSupportedException e) {
            // Handle the exception if necessary
            return null;
        }
    }
    
   

    public enum Color {
        RED, BLUE, GREEN, YELLOW, WILD;
    }

    public int getPoints() {
        switch(this.type) {
            case NUMBER:
                return this.number;

            case DRAW_TWO:
            case REVERSE:
            case SKIP:
                return 20;

            case WILD:
            case WILD_DRAW_FOUR:
                return 50;

            default:
                return 0;
        }
    }


    

    // Enumeration for card types
    public enum Type {
        NUMBER, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR;
    }


    @Override
    public String toString() {
        if (type == Type.NUMBER) {
            return color + " " + number;
        } else {
            return color + " " + type;
        }
    }

    public boolean isSameCard(Card card){
        if((card.getType() == getType())  && (card.colorOfCard() == colorOfCard()) && (card.getNumber() == getNumber())){
            return true;
        }
        return false;
    }
    

    public Type getType() {
        return type;
    }

    public Card.Color colorOfCard() {
        return color;
        //player.getDeck(0).colorOfCard()
    }

    public int getNumber() {
        return number;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setColor(Card.Color color) {
        this.color = color;
    }
    public Card.Color getColor() {
        return this.color;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}