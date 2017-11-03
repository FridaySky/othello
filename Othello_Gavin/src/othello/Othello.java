/*
 * Kevin Tran
 * COP 3330, Section 0001
 * University of Central Florida
 */
package othello;

///Imports.
import core.Game;
import userInterface.OthelloUi;

/**
 *
 * @author Kevin Tran
 */
public class Othello
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //Instantiate an instance of class Game.
        Game game = new Game();
        
        //Instantiate an instance of class OthelloUi.
        OthelloUi board = new OthelloUi(game);
        
    }//Closes main method.
}///Closes class Othello.
