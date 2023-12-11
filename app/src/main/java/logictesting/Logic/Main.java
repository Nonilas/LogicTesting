package logictesting.Logic;


public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        int simAmount = 5000;
    
        Board board = new Board(0, true);
        Simulation simulation = new Simulation(board,simAmount);
        simulation.run();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Execution time: " + executionTime + " milliseconds");
    
    }
}