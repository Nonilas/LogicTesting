package logictesting.Logic.Bot;

 

import logictesting.Logic.Card;

public class MonteCarloTree {


    public MonteCarloNode rootNode;

    public MonteCarloTree(){
    }

    public Card search(MonteCarloNode rootNode){
        this.rootNode = rootNode;
        rootNode.expandNode();

        for (int i = 0; i < rootNode.getChildNodes().size(); i++) {
            rootNode.getChildNodes().get(i).RollOut();
            rootNode.incrementVisitCount();
            rootNode.calcTotalScore();
        }


        if(selectBestChildNode() != null){
            return selectBestChildNode().boardCard;
        }
        
        return null;
    }

    public MonteCarloNode selectBestChildNode() {
        // Use UCB1 formula to select the best child node
        MonteCarloNode bestChild = null;
        double bestUCB1Value = -1;

        for (MonteCarloNode child : rootNode.getChildNodes()) {
            double UCB1Value = calculateUCB1Value(child);

            if (UCB1Value > bestUCB1Value) {
                bestUCB1Value = UCB1Value;
                bestChild = child;
            }
        }
        return bestChild;
    }

    private double calculateUCB1Value(MonteCarloNode node) {
        // Implement the UCB1 formula
        // UCB1 = averageScore + explorationFactor * sqrt(log(totalVisits) / nodeVisitCount)
        // You may need to handle cases where nodeVisitCount is zero separately

        double explorationFactor = 1.4; // You can experiment with different values
        double averageScore = (double) node.getTotalScore() / node.getVisitCount();
        double explorationTerm = explorationFactor * Math.sqrt(Math.log(rootNode.getVisitCount()) / node.getVisitCount());

        return averageScore + explorationTerm;
    }

}