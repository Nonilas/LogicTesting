package logictesting.Logic;


import java.util.ArrayList;


//This class is to create list of all the players of UNO
public class create_players {
    //here we will store all the names
    public ArrayList<String> all_names_of_players = new ArrayList<>();
    public ArrayList<String> names_of_players(int nR, int nAI_ML, int nAI_S){
        for (int i = 1; i <= nR; i++){
        String name = "Player_" + i;
        all_names_of_players.add(name);
        }
        for (int j = 1; j <= nAI_ML; j++){
            String name = "Machine_Learning_AI_Player_" + j;
            all_names_of_players.add(name);
        }
        for (int k = 1; k <= nAI_S; k++){
            String name = "Search_based_AI_Player_" + k;
            all_names_of_players.add(name);
        }

        for (String name : all_names_of_players) {
            System.out.println(name);
        }
        return  all_names_of_players;
    }
}