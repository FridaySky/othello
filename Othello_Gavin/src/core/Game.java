/*
 * Kevin Tran
 * COP 3330, Section 0001
 * University of Central Florida
 */
package core;

///Imports.
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin Tran
 */
public class Game
{
    //Member variables.
    private ArrayList<Player> players;
    public Board board;
    private Player currentPlayer;
    
    //Constructor that calls a method.
    public Game()
    {
        initObjects();
    }
    
    /*
        Start the game.
    */
    private void initObjects()
    {
        //Instantiate member variable 'board'.
        board = new Board();
        
        //Create the players.
        createPlayers();
        
        //Pass the players to the board.
        board.setPlayers(players);
        
        //Set the current player.
        currentPlayer = players.get(Constants.PLAYER_ONE);
        
        //Print the players.
        printPlayers();
    }///Closes initObjects method.
    
    /*
        Ask players for their names, and assign them disc colors.
    */
    private void createPlayers()
    {
        //Instantiate member variable 'players'.
        players = new ArrayList<>();
        
        //Loop through each player.
        for (int i = 0; i < Constants.MAX_PLAYERS; i++)
        {
            //Get player name.
            String playerName = JOptionPane.showInputDialog(null, "Enter player's name");
            
            //Make an instance of class Player.
            Player player = new Player();
            
            //Call method setName.
            player.setName(playerName);
            
            //Assign colors to players.
            if (i == Constants.PLAYER_ONE)
                player.setDiscColor(Constants.DARK);
            else if (i == Constants.PLAYER_TWO)
                player.setDiscColor(Constants.LIGHT);
            
            //Assign initial scores to players.
            player.setScore(Constants.TWO);
            
            //Add the Player instance to the member variable 'players'.
            players.add(player);
            
        }//Closes massive for loop.
    }///Closes createPlayers method.
    
    /*
        Base method to set up calculateScore.
    */
    public void calculateScore()
    {
        //Calculate the score for both dark and light.
        board.calculateScore();
        
        //Set the score for player one as darkCount.
        players.get(Constants.PLAYER_ONE).setScore(board.getDarkCount());
        
        //Set the score for player two as lightCount.
        players.get(Constants.PLAYER_TWO).setScore(board.getLightCount());
    }///Closes calculateScore method.
    
    /*
        Print player information to the system.
    */
    private void printPlayers()
    {
        //Print header.
        System.out.println("The game has the following players:");
        
        //Print information for each player.
        players.stream().forEach((player) -> {
            System.out.println("Player " + player.getName() + " is playing disc color " + player.getDiscColor());
        });
    }///Closes printPlayers method.

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }

    /**
     * @return the board
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(Board board)
    {
        this.board = board;
    }

    /**
     * @return the currentPlayer
     */
    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    /**
     * @param currentPlayer the currentPlayer to set
     */
    public void setCurrentPlayer(Player currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }
    
}///Closes class Game.
