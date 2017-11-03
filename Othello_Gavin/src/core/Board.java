/*
 * Kevin Tran
 * COP 3330, Section 0001
 * University of Central Florida
 */
package core;

///Imports.
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin Tran
 */
public class Board
{
    //Member variables.
    private Disc[][] board;
    private int darkCount;
    private int lightCount;
    private ArrayList<Player> players;
    
    //Constructor that calls a method.
    public Board()
    {
        initObjects();
    }
    
    /*
        Make the board and set the four initial pieces in the center of the board.
    */
    private void initObjects()
    {
        //Declare the size of board.
        board = new Disc[Constants.ROWS][Constants.COLUMNS];
        
        //Initialize each object in the array.
        for (int row = 0; row < Constants.ROWS; row++)
            for (int col = 0; col < Constants.COLUMNS; col++)
                board[row][col] = new Disc();
        
        //Compute the starting positions of the board.
        int topMiddleRow = Constants.ROWS/2 - 1;
        int bottomMiddleRow = Constants.ROWS/2;
        int leftMiddleColumn = Constants.COLUMNS/2 - 1;
        int rightMiddleColumn = Constants.COLUMNS/2;
    
        //Assign colors to the starting positions.
        board[topMiddleRow][leftMiddleColumn].setDiscColor(Constants.LIGHT);
        board[topMiddleRow][rightMiddleColumn].setDiscColor(Constants.DARK);
        board[bottomMiddleRow][leftMiddleColumn].setDiscColor(Constants.DARK);
        board[bottomMiddleRow][rightMiddleColumn].setDiscColor(Constants.LIGHT);
    }///Closes initObjects method.
    
    /*
        Calculate the player scores.
    */
    public void calculateScore()
    {
        //Initialize the scores.
        darkCount = 0;
        lightCount = 0;
        
        //Loop through 2D array and check for dark and light. Increment counters respectively.
        for (int row = 0; row < Constants.ROWS; row++)
        {
            for (int col = 0; col < Constants.COLUMNS; col++)
            {
                if (board[row][col].getDiscColor() == Constants.DARK)
                    darkCount++;
                else if (board[row][col].getDiscColor() == Constants.LIGHT)
                    lightCount++;
            }//Closes inner for loop.
        }//Closes outer for loop.
        
        //Update the players' scores!
        players.get(Constants.PLAYER_ONE).setScore(darkCount);
        players.get(Constants.PLAYER_TWO).setScore(lightCount);
    }///Closes calculate score method!
    
    /*
        Row is the clicked row. Col is the clicked column. Color is the current player's disc color.
        This method determines whether the user click is even a valid move.
    */
    public boolean isValidMove(int row, int col, Color color)
    {
        //Initialize the move to invalid.
        boolean valid = false;
        
        //Check all directions to see if the discs can be flipped.
        if (checkUp(row, col, color)) //1
            valid = true;
        if (checkUpLeft(row, col, color)) //2
            valid = true;
        if (checkLeft(row, col, color)) //3
            valid = true;
        if (checkDownLeft(row, col, color)) //4
            valid = true;
        if (checkDown(row, col, color)) //5
            valid = true;
        if (checkDownRight(row, col, color)) //6
            valid = true;
        if (checkRight(row, col, color)) //7
            valid = true;
        if (checkUpRight(row, col, color)) //8
            valid = true;
        
        //Compute the score after each move.
        calculateScore();
        
        //---------------------------------------
        
        //This variable serves to keep track of the next player's color.
        Color nextColor;
        
        //If it is the dark player's turn, then we set nextColor equal to the light player's color.
        if (color == getPlayers().get(Constants.PLAYER_ONE).getDiscColor())
            nextColor = getPlayers().get(Constants.PLAYER_TWO).getDiscColor();
        
        //Otherwise if it is the light player's turn, then we set nextColor equal to the dark player's color.
        else
            nextColor = getPlayers().get(Constants.PLAYER_ONE).getDiscColor();
        
        //Determine if the game is over from the next player's perspective.
        if (gameOver(nextColor, darkCount, lightCount))
        {
            //Notify the players that the game is over!
            JOptionPane.showMessageDialog(null, "The game is over!");

            //Check to see if player one has won. If so, notify the players!
            if (darkCount > lightCount)
                JOptionPane.showMessageDialog(null, "Player " + getPlayers().get(Constants.PLAYER_ONE).getName() + " wins!");
            
            //Else, check to see if player two has won. Notify the players if that is the case.
            else if (darkCount < lightCount)
                JOptionPane.showMessageDialog(null, "Player " + getPlayers().get(Constants.PLAYER_TWO).getName() + " wins!");
            
            //Otherwise, the game has ended in a tie!
            else
                JOptionPane.showMessageDialog(null, "The game has ended in a draw!");
            
        }//Closes gameOver if statement.

        return valid;
    }///Closes isValidMove method.
    
    /*
        Row is the clicked row. Col is the clicked column. Color is the current player's disc color.
        Method determines whether the discs can be flipped in this direction.
    */
    private boolean checkUp(int row, int col, Color color)
    {
        //Early initializations.
        int flipSquares = 0;
        int checkRow = row - 1; //row-1 because we are not checking the current row.
        boolean matchFound = false;
        boolean validMove = false;
        
        //If there are still rows to check and if we haven't found our color on the other end.
        while (checkRow >= 0 && !matchFound)
        {
            //If the next square is empty, this is not a valid move.
            if (board[checkRow][col].getDiscColor() == Constants.EMPTY)
                return validMove;
            
            //If the next square is of the opposite color, that square gets flipped.
            else if (board[checkRow][col].getDiscColor() != color)
                flipSquares++;
            
            //Ends the sequence of flipping since we've ran into a square that is of the player's color.
            else
                matchFound = true;
            
            //Check the next row.
            checkRow--;
            
        }//Closes while loop.
        
        //If the user move's is valid:
        if (matchFound && flipSquares > 0)
        {
            //Place the new disc.
            board[row][col].setDiscColor(color);
            
            while (flipSquares > 0)
            {
                //Bookkeeping.
                row--;
                flipSquares--;
                
                //Update the disc color for the flipped discs.
                board[row][col].setDiscColor(color);
                
            }//Closes while loop.
            
            //User move is valid.
            validMove = true;
            
        }//Closes if statement.
        
        //User move is false.
        else
            validMove = false;
        
        return validMove;
    }///Closes checkUp method.

    /*
        Row is the clicked row. Col is the clicked column. Color is the current player's disc color.
        Method determines whether the discs can be flipped in this direction.
    */
    private boolean checkUpLeft(int row, int col, Color color)
    {
        //Early initializations.
        int flipSquares = 0;
        int checkRow = row - 1; //row-1 because we are not checking the current row.
        int checkCol = col - 1; //col-1 because we are not checking the current column.
        boolean matchFound = false;
        boolean validMove = false;
        
        //If there are still rows and columns to check and if we haven't found our color on the other end.
        while (checkRow >= 0 && checkCol >= 0 && !matchFound)
        {
            //If the next square is empty, this is not a valid move.
            if (board[checkRow][checkCol].getDiscColor() == Constants.EMPTY)
                return validMove;
            
            //If the next square is of the opposite color, that square gets flipped.
            else if (board[checkRow][checkCol].getDiscColor() != color)
                flipSquares++;
            
            //Ends the sequence of flipping since we've ran into a square that is of the player's color.
            else
                matchFound = true;
            
            //Check the next row and column.
            checkRow--;
            checkCol--;
            
        }//Closes while loop.
        
        //If the user move's is valid:
        if (matchFound && flipSquares > 0)
        {
            //Place the new disc.
            board[row][col].setDiscColor(color);
            
            while (flipSquares > 0)
            {
                //Bookkeeping.
                row--;
                col--;
                flipSquares--;
                
                //Update the disc color for the flipped discs.
                board[row][col].setDiscColor(color);
                
            }//Closes while loop.
            
            //User move is valid.
            validMove = true;
            
        }//Closes if statement.
        
        //User move is false.
        else
            validMove = false;
        
        return validMove;
    }///Closes checkUpLeft method.

    /*
        Row is the clicked row. Col is the clicked column. Color is the current player's disc color.
        Method determines whether the discs can be flipped in this direction.
    */
    private boolean checkLeft(int row, int col, Color color)
    {
        //Early initializations.
        int flipSquares = 0;
        int checkCol = col - 1; //col-1 because we are not checking the current column.
        boolean matchFound = false;
        boolean validMove = false;
        
        //If there are still columns to check and if we haven't found our color on the other end.
        while (checkCol >= 0 && !matchFound)
        {
            //If the next square is empty, this is not a valid move.
            if (board[row][checkCol].getDiscColor() == Constants.EMPTY)
                return validMove;
            
            //If the next square is of the opposite color, that square gets flipped.
            else if (board[row][checkCol].getDiscColor() != color)
                flipSquares++;
            
            //Ends the sequence of flipping since we've ran into a square that is of the player's color.
            else
                matchFound = true;
            
            //Check the next column.
            checkCol--;
            
        }//Closes while loop.
        
        //If the user move's is valid:
        if (matchFound && flipSquares > 0)
        {
            //Place the new disc.
            board[row][col].setDiscColor(color);
            
            while (flipSquares > 0)
            {
                //Bookkeeping.
                col--;
                flipSquares--;
                
                //Update the disc color for the flipped discs.
                board[row][col].setDiscColor(color);
                
            }//Closes while loop.
            
            //User move is valid.
            validMove = true;
            
        }//Closes if statement.
        
        //User move is false.
        else
            validMove = false;
        
        return validMove;
    }///Closes checkLeft method.

    /*
        Row is the clicked row. Col is the clicked column. Color is the current player's disc color.
        Method determines whether the discs can be flipped in this direction.
    */
    private boolean checkDownLeft(int row, int col, Color color)
    {
        //Early initializations.
        int flipSquares = 0;
        int checkRow = row + 1; //row+1 because we are not checking the current row.
        int checkCol = col - 1; //col-1 because we are not checking the current column.
        boolean matchFound = false;
        boolean validMove = false;
        
        //If there are still rows and columns to check and if we haven't found our color on the other end.
        while (checkRow < Constants.ROWS && checkCol >= 0 && !matchFound)
        {
            //If the next square is empty, this is not a valid move.
            if (board[checkRow][checkCol].getDiscColor() == Constants.EMPTY)
                return validMove;
            
            //If the next square is of the opposite color, that square gets flipped.
            else if (board[checkRow][checkCol].getDiscColor() != color)
                flipSquares++;
            
            //Ends the sequence of flipping since we've ran into a square that is of the player's color.
            else
                matchFound = true;
            
            //Check the next row and column.
            checkRow++;
            checkCol--;
            
        }//Closes while loop.
        
        //If the user move's is valid:
        if (matchFound && flipSquares > 0)
        {
            //Place the new disc.
            board[row][col].setDiscColor(color);
            
            while (flipSquares > 0)
            {
                //Bookkeeping.
                row++;
                col--;
                flipSquares--;
                
                //Update the disc color for the flipped discs.
                board[row][col].setDiscColor(color);
                
            }//Closes while loop.
            
            //User move is valid.
            validMove = true;
            
        }//Closes if statement.
        
        //User move is false.
        else
            validMove = false;
        
        return validMove;
    }///Closes checkDownLeft method.
    
    /*
        Row is the clicked row. Col is the clicked column. Color is the current player's disc color.
        Method determines whether the discs can be flipped in this direction.
    */
    private boolean checkDown(int row, int col, Color color)
    {
        //Early initializations.
        int flipSquares = 0;
        int checkRow = row + 1; //row+1 because we are not checking the current row
        boolean matchFound = false;
        boolean validMove = false;
        
        //If there are still rows to check and if we haven't found our color on the other end.
        while (checkRow < Constants.ROWS && !matchFound)
        {
            //If the next is empty, this is not a valid move.
            if (board[checkRow][col].getDiscColor() == Constants.EMPTY)
                return validMove;
            
            //If the next square is of the opposite color, that square gets flipped.
            else if (board[checkRow][col].getDiscColor() != color)
                flipSquares++;
            
            //Ends the sequence of flipping since we've ran into a square that is of the player's color.
            else
                matchFound = true;
            
            //Check the next row.
            checkRow++;
            
        }//Closes while loop.
        
        //If the user move's is valid:
        if (matchFound && flipSquares > 0)
        {
            //Place the new disc.
            board[row][col].setDiscColor(color);
            
            //Flip the squares.
            while (flipSquares > 0)
            {
                //Bookkeeping.
                row++;
                flipSquares--;
                
                //Update the disc color for the flipped discs.
                board[row][col].setDiscColor(color);
                
            }//Closes while loop.
            
            //User move is valid.
            validMove = true;
            
        }//Closes if statement.
        
        //User move is false.
        else
            validMove = false;
        
        return validMove;
    }///Closes checkDown method.
    
    /*
        Row is the clicked row. Col is the clicked column. Color is the current player's disc color.
        Method determines whether the discs can be flipped in this direction.
    */
    private boolean checkDownRight(int row, int col, Color color)
    {
        //Early initializations.
        int flipSquares = 0;
        int checkRow = row + 1; //row+1 because we are not checking the current row.
        int checkCol = col + 1;//col+1 because we are not checking the current column.
        boolean matchFound = false;
        boolean validMove = false;
        
        //If there are still rows and columns to check and if we haven't found our color on the other end.
        while (checkRow < Constants.ROWS && checkCol < Constants.COLUMNS && !matchFound)
        {
            //If the next square is empty, this is not a valid move.
            if (board[checkRow][checkCol].getDiscColor() == Constants.EMPTY)
                return validMove;
            
            //If the next square is of the opposite color, that square gets flipped.
            else if (board[checkRow][checkCol].getDiscColor() != color)
                flipSquares++;
            
            //Ends the sequence of flipping since we've ran into a square that is of the player's color.
            else
                matchFound = true;
            
            //Check the next row and column.
            checkRow++;
            checkCol++;
            
        }//Closes while loop.
        
        //If the user move's is valid:
        if (matchFound && flipSquares > 0)
        {
            //Place the new disc.
            board[row][col].setDiscColor(color);
            
            while (flipSquares > 0)
            {
                //Bookkeeping.
                row++;
                col++;
                flipSquares--;
                
                //Update the disc color for the flipped discs.
                board[row][col].setDiscColor(color);
                
            }//Closes while loop.
            
            //User move is valid.
            validMove = true;
            
        }//Closes if statement.
        
        //User move is false.
        else
            validMove = false;
        
        return validMove;
    }///Closes checkDownRight method.
    
    /*
        Row is the clicked row. Col is the clicked column. Color is the current player's disc color.
        Method determines whether the discs can be flipped in this direction.
    */
    private boolean checkRight(int row, int col, Color color)
    {
        //Early initializations.
        int flipSquares = 0;
        int checkCol = col + 1; //col+1 because we are not checking the current column.
        boolean matchFound = false;
        boolean validMove = false;
        
        //If there are still columns to check and if we haven't found our color on the other end.
        while (checkCol < Constants.COLUMNS && !matchFound)
        {
            //If the next square is empty, this is not a valid move.
            if (board[row][checkCol].getDiscColor() == Constants.EMPTY)
                return validMove;
            
            //If the next square is of the opposite color, that square gets flipped.
            else if (board[row][checkCol].getDiscColor() != color)
                flipSquares++;
            
            //Ends the sequence of flipping since we've ran into a square that is of the player's color.
            else
                matchFound = true;
            
            //Check the next column.
            checkCol++;
            
        }//Closes while loop.
        
        //If the user move's is valid:
        if (matchFound && flipSquares > 0)
        {
            //Place the new disc.
            board[row][col].setDiscColor(color);
            
            while (flipSquares > 0)
            {
                //Bookkeeping.
                col++;
                flipSquares--;
                
                //Update the disc color for the flipped discs.
                board[row][col].setDiscColor(color);
                
            }//Closes while loop.
            
            //User move is valid.
            validMove = true;
        }//Closes if statement.
        
        //User move is false.
        else
            validMove = false;
        
        return validMove;
    }///Closes checkRight method.
    
    
    /*
        Row is the clicked row. Col is the clicked column. Color is the current player's disc color.
        Method determines whether the discs can be flipped in this direction.
    */
    private boolean checkUpRight(int row, int col, Color color)
    {
        //Early initializations.
        int flipSquares = 0;
        int checkRow = row - 1; //row-1 because we are not checking the current row.
        int checkCol = col + 1;//col+1 because we are not checking the current column.
        boolean matchFound = false;
        boolean validMove = false;
        
        //If there are still rows and columns to check and if we haven't found our color on the other end.
        while (checkRow >= 0 && checkCol < Constants.COLUMNS && !matchFound)
        {
            //If the next square is empty, this is not a valid move.
            if (board[checkRow][checkCol].getDiscColor() == Constants.EMPTY)
                return validMove;
            
            //If the next square is of the opposite color, that square gets flipped.
            else if (board[checkRow][checkCol].getDiscColor() != color)
                flipSquares++;
            
            //Ends the sequence of flipping since we've ran into a square that is of the player's color.
            else
                matchFound = true;
            
            //Check the next row and column.
            checkRow--;
            checkCol++;
            
        }//Closes while loop.
        
        //If the user move's is valid:
        if (matchFound && flipSquares > 0)
        {
            //Place the new disc.
            board[row][col].setDiscColor(color);
            
            while (flipSquares > 0)
            {
                //Bookkeeping.
                row--;
                col++;
                flipSquares--;
                
                //Update the disc color for the flipped discs.
                board[row][col].setDiscColor(color);
                
            }//Closes while loop.
            
            //User move is valid.
            validMove = true;
        }//Closes if statement.
        
        //User move is false.
        else
            validMove = false;
        
        return validMove;
    }///Closes checkUpRight method.
    
    /*
        This method determines whether the game can still continue.
    */
    public boolean gameOver(Color color, int darkScore, int lightScore)
    {
        //If the board is completely filled, then the game is automatically over.
        if (darkScore + lightScore == 64)
            return true;
        
        //Check if a player has lost all of their discs.
        if (darkScore == 0 || lightScore == 0)
            return true;

        //This helper method will check whether the current player still has a valid move to be played
        //and will short-circuit and return false if there is still a valid move to be played.
        if (hasMove(color))
            return false;
        
        //Otherwise, the game has ended.
        else
            return true;
        
    }///Closes gameOver method.
    
    /*
        This helper method is used in gameOver to check if the current player still has a valid move to be played.
    */
    private boolean hasMove(Color color)
    {
        //Loop through the board and find an empty square on the grid.
        //Run the checkDirection functions from there, and return true if there is a valid move.
        for (int row = 0; row < Constants.ROWS; row++)
        {
            for (int col = 0; col < Constants.COLUMNS; col++)
            {
                if (board[row][col].getDiscColor() == Constants.EMPTY)
                {
                    if (validUp(row, col, color)) //1
                        return true;
                    if (validUpLeft(row, col, color))//2
                        return true;
                    if (validLeft(row, col, color)) //3
                        return true;
                    if (validDownLeft(row, col, color)) //4
                        return true;
                    if (validDown(row, col, color)) //5
                        return true;
                    if (validDownRight(row, col, color)) //6
                        return true;
                    if (validRight(row, col, color)) //7
                        return true;
                    if (validUpRight(row, col, color)) //8
                        return true;
                }//Closes search for empty square.
            }//Closes column loop.
        }//Closes row loop.

        //If there wasn't an empty square, then the game must be over by default.
        //Or if we weren't able to return up there because there were no more valid moves, then also return false.
        return false;
        
    }///Closes hasMove method.
    
    /*
        This method check for whether there is a valid move in this direction in the event a disc is placed at row and col.
    */
    private boolean validUp(int row, int col, Color color)
    {
        //There's no need to check the current square.
        int checkRow = row - 1;
        
        //Make sure we are within array boundaries.
        if (checkRow >= 0 && checkRow < Constants.ROWS)
        {
            //The immediately adjacent square is empty or contains the same colored disc, so there is no valid move in this direction.
            if (board[checkRow][col].getDiscColor() == Constants.EMPTY || //first parameter
                board[checkRow][col].getDiscColor() == color) //second parameter
                return false;
            
            //Otherwise, that immediately adjacent square must be occupied by an opposing disc.
            else
            {   
                //For as long as we are inbounds and run into discs of the opposing color, then keep checking in this direction.
                do
                {
                    checkRow--;
                } while (checkRow >= 0 && board[checkRow][col].getDiscColor() != Constants.EMPTY //Two parameters.
                         && board[checkRow][col].getDiscColor() != color); //One parameter.
                
                //If the current square is out-of-bounds or empty, then the move is not valid.
                if (checkRow < 0 || board[checkRow][col].getDiscColor() == Constants.EMPTY)
                    return false;
                
                //Otherwise, the current square contains our color, making this a valid direction since we can surround the opposing discs.
                else
                    return true;
                
            }//Closes opposing disc else statement.
        }//Closes array boundaries if statement.
        
        //We are not in bounds, so this is definitely not a valid direction.
        else
            return false;
        
    }///Closes validUp method.
    
    /*
        This method check for whether there is a valid move in this direction if a disc was placed at row and col.
    */
    private boolean validUpLeft(int row, int col, Color color)
    {
        //There's no need to check the current square.
        int checkRow = row - 1;
        int checkCol = col - 1;
        
        //Make sure we are within array boundaries.
        if ( (checkRow >= 0 && checkRow < Constants.ROWS) && (checkCol >= 0 && checkCol < Constants.COLUMNS) )
        {
            //The immediately adjacent square is empty or contains the same colored disc, so there is no valid move in this direction.
            if (board[checkRow][checkCol].getDiscColor() == Constants.EMPTY || //first parameter
                board[checkRow][checkCol].getDiscColor() == color) //second parameter
                return false;
            
            //Otherwise, that immediately adjacent square must be occupied by an opposing disc.
            else
            {   
                //For as long as we are inbounds and run into discs of the opposing color, then keep checking in this direction.
                do
                {
                    checkRow--;
                    checkCol--;
                } while (checkRow >= 0 && checkCol >= 0 && //Two parameters.
                         board[checkRow][checkCol].getDiscColor() != Constants.EMPTY && //One parameter.
                         board[checkRow][checkCol].getDiscColor() != color); //One parameter.
                
                //If the current square is out-of-bounds or empty, then the move is not valid.
                if (checkRow < 0 || checkCol < 0 || board[checkRow][checkCol].getDiscColor() == Constants.EMPTY)
                    return false;
                
                //Otherwise, the current square contains our color, making this a valid direction since we can surround the opposing discs.
                else
                    return true;
                
            }//Closes opposing disc else statement.
        }//Closes array boundaries if statement.
        
        //We are not in bounds, so this is definitely not a valid direction.
        else
            return false;
        
    }///Closes validUpLeft method.
    
    /*
        This method check for whether there is a valid move in this direction if a disc was placed at row and col.
    */
    private boolean validLeft(int row, int col, Color color)
    {
        //There's no need to check the current square.
        int checkCol = col - 1;
        
        //Make sure we are within array boundaries.
        if (checkCol >= 0 && checkCol < Constants.COLUMNS)
        {
            //The immediately adjacent square is empty or contains the same colored disc, so there is no valid move in this direction.
            if (board[row][checkCol].getDiscColor() == Constants.EMPTY || //first parameter
                board[row][checkCol].getDiscColor() == color) //second parameter
                return false;
            
            //Otherwise, that immediately adjacent square must be occupied by an opposing disc.
            else
            {   
                //For as long as we are inbounds and run into discs of the opposing color, then keep checking in this direction.
                do
                {
                    checkCol--;
                } while (checkCol >= 0 && board[row][checkCol].getDiscColor() != Constants.EMPTY //Two parameters.
                         && board[row][checkCol].getDiscColor() != color); //One parameter.
                
                //If the current square is out-of-bounds or empty, then the move is not valid.
                if (checkCol < 0 || board[row][checkCol].getDiscColor() == Constants.EMPTY)
                    return false;
                
                //Otherwise, the current square contains our color, making this a valid direction since we can surround the opposing discs.
                else
                    return true;
                
            }//Closes opposing disc else statement.
        }//Closes array boundaries if statement.
        
        //We are not in bounds, so this is definitely not a valid direction.
        else
            return false;
        
    }///Closes validLeft method.
    
    /*
        This method check for whether there is a valid move in this direction if a disc was placed at row and col.
    */
    private boolean validDownLeft(int row, int col, Color color)
    {
        //There's no need to check the current square.
        int checkRow = row + 1;
        int checkCol = col - 1;
        
        //Make sure we are within array boundaries.
        if ( (checkRow >= 0 && checkRow < Constants.ROWS) && (checkCol >= 0 && checkCol < Constants.COLUMNS) )
        {
            //The immediately adjacent square is empty or contains the same colored disc, so there is no valid move in this direction.
            if (board[checkRow][checkCol].getDiscColor() == Constants.EMPTY || //first parameter
                board[checkRow][checkCol].getDiscColor() == color) //second parameter
                return false;
            
            //Otherwise, that immediately adjacent square must be occupied by an opposing disc.
            else
            {   
                //For as long as we are inbounds and run into discs of the opposing color, then keep checking in this direction.
                do
                {
                    checkRow++;
                    checkCol--;
                } while (checkRow < Constants.ROWS && checkCol >= 0 && //Two parameters.
                         board[checkRow][checkCol].getDiscColor() != Constants.EMPTY && //One parameter.
                         board[checkRow][checkCol].getDiscColor() != color); //One parameter.
                
                //If the current square is out-of-bounds or empty, then the move is not valid.
                if (checkRow >= Constants.ROWS || checkCol < 0 || board[checkRow][checkCol].getDiscColor() == Constants.EMPTY)
                    return false;
                
                //Otherwise, the current square contains our color, making this a valid direction since we can surround the opposing discs.
                else
                    return true;
                
            }//Closes opposing disc else statement.
        }//Closes array boundaries if statement.
        
        //We are not in bounds, so this is definitely not a valid direction.
        else
            return false;
        
    }///Closes validDownLeft method.
    
    /*
        This method check for whether there is a valid move in this direction if a disc was placed at row and col.
    */
    private boolean validDown(int row, int col, Color color)
    {
        //There's no need to check the current square.
        int checkRow = row + 1;
        
        //Make sure we are within array boundaries.
        if (checkRow >= 0 && checkRow < Constants.ROWS)
        {
            //The immediately adjacent square is empty or contains the same colored disc, so there is no valid move in this direction.
            if (board[checkRow][col].getDiscColor() == Constants.EMPTY || //first parameter
                board[checkRow][col].getDiscColor() == color) //second parameter
                return false;
            
            //Otherwise, that immediately adjacent square must be occupied by an opposing disc.
            else
            {   
                //For as long as we are inbounds and run into discs of the opposing color, then keep checking in this direction.
                do
                {
                    checkRow++;
                } while (checkRow < Constants.ROWS && board[checkRow][col].getDiscColor() != Constants.EMPTY //Two parameters.
                         && board[checkRow][col].getDiscColor() != color); //One parameter.
                
                //If the current square is out-of-bounds or empty, then the move is not valid.
                if (checkRow >= Constants.ROWS || board[checkRow][col].getDiscColor() == Constants.EMPTY)
                    return false;
                
                //Otherwise, the current square contains our color, making this a valid direction since we can surround the opposing discs.
                else
                    return true;
                
            }//Closes opposing disc else statement.
        }//Closes array boundaries if statement.
        
        //We are not in bounds, so this is definitely not a valid direction.
        else
            return false;
        
    }///Closes validDown method.
    
    /*
        This method check for whether there is a valid move in this direction if a disc was placed at row and col.
    */
    private boolean validDownRight(int row, int col, Color color)
    {
        //There's no need to check the current square.
        int checkRow = row + 1;
        int checkCol = col + 1;
        
        //Make sure we are within array boundaries.
        if ( (checkRow >= 0 && checkRow < Constants.ROWS) && (checkCol >= 0 && checkCol < Constants.COLUMNS) )
        {
            //The immediately adjacent square is empty or contains the same colored disc, so there is no valid move in this direction.
            if (board[checkRow][checkCol].getDiscColor() == Constants.EMPTY || //first parameter
                board[checkRow][checkCol].getDiscColor() == color) //second parameter
                return false;
            
            //Otherwise, that immediately adjacent square must be occupied by an opposing disc.
            else
            {   
                //For as long as we are inbounds and run into discs of the opposing color, then keep checking in this direction.
                do
                {
                    checkRow++;
                    checkCol++;
                } while (checkRow < Constants.ROWS && checkCol < Constants.COLUMNS && //Two parameters.
                         board[checkRow][checkCol].getDiscColor() != Constants.EMPTY && //One parameter.
                         board[checkRow][checkCol].getDiscColor() != color); //One parameter.
                
                //If the current square is out-of-bounds or empty, then the move is not valid.
                if (checkRow >= Constants.ROWS || checkCol >= Constants.COLUMNS || board[checkRow][checkCol].getDiscColor() == Constants.EMPTY)
                    return false;
                
                //Otherwise, the current square contains our color, making this a valid direction since we can surround the opposing discs.
                else
                    return true;
                
            }//Closes opposing disc else statement.
        }//Closes array boundaries if statement.
        
        //We are not in bounds, so this is definitely not a valid direction.
        else
            return false;
        
    }///Closes validDownRight method.
    
    /*
        This method check for whether there is a valid move in this direction if a disc was placed at row and col.
    */
    private boolean validRight(int row, int col, Color color)
    {
        //There's no need to check the current square.
        int checkCol = col + 1;
        
        //Make sure we are within array boundaries.
        if (checkCol >= 0 && checkCol < Constants.COLUMNS)
        {
            //The immediately adjacent square is empty or contains the same colored disc, so there is no valid move in this direction.
            if (board[row][checkCol].getDiscColor() == Constants.EMPTY || //first parameter
                board[row][checkCol].getDiscColor() == color) //second parameter
                return false;
            
            //Otherwise, that immediately adjacent square must be occupied by an opposing disc.
            else
            {   
                //For as long as we are inbounds and run into discs of the opposing color, then keep checking in this direction.
                do
                {
                    checkCol++;
                } while (checkCol < Constants.COLUMNS && board[row][checkCol].getDiscColor() != Constants.EMPTY //Two parameters.
                         && board[row][checkCol].getDiscColor() != color); //One parameter.
                
                //If the current square is out-of-bounds or empty, then the move is not valid.
                if (checkCol >= Constants.COLUMNS || board[row][checkCol].getDiscColor() == Constants.EMPTY)
                    return false;
                
                //Otherwise, the current square contains our color, making this a valid direction since we can surround the opposing discs.
                else
                    return true;
                
            }//Closes opposing disc else statement.
        }//Closes array boundaries if statement.
        
        //We are not in bounds, so this is definitely not a valid direction.
        else
            return false;
        
    }///Closes validRight method.
    
    /*
        This method check for whether there is a valid move in this direction if a disc was placed at row and col.
    */
    private boolean validUpRight(int row, int col, Color color)
    {
        //There's no need to check the current square.
        int checkRow = row - 1;
        int checkCol = col + 1;
        
        //Make sure we are within array boundaries.
        if ( (checkRow >= 0 && checkRow < Constants.ROWS) && (checkCol >= 0 && checkCol < Constants.COLUMNS) )
        {
            //The immediately adjacent square is empty or contains the same colored disc, so there is no valid move in this direction.
            if (board[checkRow][checkCol].getDiscColor() == Constants.EMPTY || //first parameter
                board[checkRow][checkCol].getDiscColor() == color) //second parameter
                return false;
            
            //Otherwise, that immediately adjacent square must be occupied by an opposing disc.
            else
            {   
                //For as long as we are inbounds and run into discs of the opposing color, then keep checking in this direction.
                do
                {
                    checkRow--;
                    checkCol++;
                } while (checkRow >= 0 && checkCol < Constants.COLUMNS && //Two parameters.
                         board[checkRow][checkCol].getDiscColor() != Constants.EMPTY && //One parameter.
                         board[checkRow][checkCol].getDiscColor() != color); //One parameter.
                
                //If the current square is out-of-bounds or empty, then the move is not valid.
                if (checkRow < 0 || checkCol >= Constants.COLUMNS || board[checkRow][checkCol].getDiscColor() == Constants.EMPTY)
                    return false;
                
                //Otherwise, the current square contains our color, making this a valid direction since we can surround the opposing discs.
                else
                    return true;
                
            }//Closes opposing disc else statement.
        }//Closes array boundaries if statement.
        
        //We are not in bounds, so this is definitely not a valid direction.
        else
            return false;
        
    }///Closes validUpRight method.
    
    /**
     * @return the board
     */
    public Disc[][] getBoard()
    {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(Disc[][] board)
    {
        this.board = board;
    }

    /**
     * @return the darkCount
     */
    public int getDarkCount()
    {
        return darkCount;
    }

    /**
     * @param darkCount the darkCount to set
     */
    public void setDarkCount(int darkCount)
    {
        this.darkCount = darkCount;
    }

    /**
     * @return the lightCount
     */
    public int getLightCount()
    {
        return lightCount;
    }

    /**
     * @param lightCount the lightCount to set
     */
    public void setLightCount(int lightCount)
    {
        this.lightCount = lightCount;
    }

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
    
}///Closes class Board.
