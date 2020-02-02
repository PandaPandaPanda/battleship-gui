/***************************************************************************************
 * Name: Kevin Xu	
 * Date: December 27, 2019	
 * Title: Commentator
 * Description: Class that updates the label text which gives player instructions
 ***************************************************************************************/

package kevin;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Commentator extends JPanel {
	
	//===================== Declare variable
	private JLabel comment; //JLabel for storing the game instructions
	//=====================

	/******************************************************
	 * Constructor
	 * pre: none
	 * post: A new instance of commentator is instantiated
	 ******************************************************/
	public Commentator() {
		comment = new JLabel("Place the ships on the home field");
		comment.setFont(new Font("Monaco", Font.PLAIN, 12));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(comment);
		comment.setAlignmentX(Commentator.CENTER_ALIGNMENT);
	}

	/**************************************************
	 * Set new font size whenever the frame size changes
	 * pre: Frame size changes
	 * post: The font size is set
	 * @param ratio
	 **************************************************/
	public void setFontSize(double ratio) { 
		if (12 * ratio - (int) (12 * ratio) > 0.2) { //Use if statement to ignore small screen size changes to save memory and avoid glitch
			comment.setFont(new Font("Monaco", Font.PLAIN, (int) (12 * ratio)));
		}
	}
	
	/****************************************
	 * Prompt for user when placing the text
	 * pre: Ship placement button clicked
	 * post: Text set
	 * @param shipLabel
	 ****************************************/
	public void placeShipText(int shipLabel) {
		switch (shipLabel) {
		case 1:
			comment.setText("Place the Carrier (length 5)");
			break;
		case 2:
			comment.setText("Place the Battleship (length 4)");
			break;
		case 3:
			comment.setText("Place the Cruiser (length 4)");
			break;
		case 4:
			comment.setText("Place the Submarine (length 3)");
			break;
		case 5:
			comment.setText("Place the Destroyer (length 2)");
			break;
		}
	}
	
	/********************************************************
	 * Text showing the current alignment for ship placement
	 * pre: Ship rotation button clicked
	 * post: Text set
	 * @param horizontal
	 ********************************************************/
	public void horizontalAlignment(boolean horizontal) {
		if (horizontal) {
			comment.setText("Placing the ship horizontally");
		} else {
			comment.setText("Placing the ship vertically");
		}
	}
	
	public void startGame() {
		comment.setText("Sink all five ships. Click on the Enemy Field to drop the BOMB!");
	}
	
	/******************************************************
	 * User doesn't hit anything
	 * pre: none
	 * post: The label now displays "Splash"
	 ******************************************************/
	public void splash() {
		comment.setText("Splash");
	}

	/*****************************************************
	 * User hits a ship
	 * pre: User clicks the mouth and hit a ship
	 * post: The label now displays which ship is hit
	 * @param shipLabel
	 *****************************************************/
	public void hit(int shipLabel) {
		switch (shipLabel) {
		case 1:
			comment.setText("The Carrier with length 5 is hit");
			break;
		case 2:
			comment.setText("The Battleship with length 4 is hit");
			break;
		case 3:
			comment.setText("The Cruiser with length 4 is hit");
			break;
		case 4:
			comment.setText("The Submarine with length 3 is hit");
			break;
		case 5:
			comment.setText("The Destroyer with length 2 is hit");
			break;
		}
	}

	/********************************************
	 * User sunk a ship and the label is updated
	 * pre: User sunk a ship
	 * post: Label text is updated
	 * @param shipLabel
	 ********************************************/
	public void sunk(int shipLabel) {
		switch (shipLabel) {
		case 1:
			comment.setText("The Carrier is sunk");
			break;
		case 2:
			comment.setText("The Battleship is sunk");
			break;
		case 3:
			comment.setText("The Cruiser is sunk");
			break;
		case 4:
			comment.setText("The Submarine is sunk");
			break;
		case 5:
			comment.setText("The Destroyer is sunk");
			break;
		}
	}

	/***************************************************
	 * Given instructions when user hit a duplicate spot
	 * pre: User hits a duplicated spot
	 * post: Label text is updated
	 ***************************************************/
	public void hitBefore() {
		comment.setText("Hit this spot before");
	}

	/**************************************************************
	 * Given instructions when user click a spot outside the bounds
	 * pre: User click a spot outside the bounds
	 * post: Label text is updated
	 **************************************************************/
	public void outsideBounds() {
		comment.setText("Click on the box inside the ocean");
	}

	/***************************************************
	 * Given instructions when user wins the game
	 * pre: User wins
	 * post: Label text is updated
	 ***************************************************/
	public void win(boolean playerWins) {
		if (playerWins)
			comment.setText("You Won! Congratulations.");
		else 
			comment.setText("You Lost! Better luck next time.");
	}

	/********************************************
	 * Display score board
	 * pre: User input a user name after wining
	 * post: Score board is displayed
	 ********************************************/
	public void scoreBoard() {
		comment.setText("Score Board");
	}
}

/**
 * Class Commentator

	Constructor Commentator 
		comment = new JLabel("Sunk all 5 ships!")
		setLayout(new BoxLayout)
		add(comment)
	End constructor comment

	Method splash
		comment.setText("Splash")
	End method splash

	Method hit (shipLabel) 
		switch (shipLabel) 
		case 1:
			comment.setText("The Carrier (length 5)")
			
		case 2:
			comment.setText("The Battleship (length 4)")
			
		case 3:
			comment.setText("The Cruiser (length 4)")
			
		case 4:
			comment.setText("The Submarine (length 3)")
			
		case 5:
			comment.setText("The Frigate (length 2)")
	End Method hit		
	

	Method sunk(shipLabel) 
		switch (shipLabel) 
		case 1:
			comment.setText("The Carrier is sunk")
			
		case 2:
			comment.setText("The Battleship is sunk")
			
		case 3:
			comment.setText("The Cruiser is sunk")
			
		case 4:
			comment.setText("The Submarine is sunk")
			
		case 5:
			comment.setText("The Frigate is sunk")
	End method sunk
		
	method placeShipText(shipLabel) 
		switch (shipLabel) 
		case 1:
			comment.setText("Place the Carrier with length 5")
			break;
		case 2:
			comment.setText("Place the Battleship with length 4")
			break;
		case 3:
			comment.setText("Place the Cruiser with length 4")
			break;
		case 4:
			comment.setText("Place the Submarine with length 3")
			break;
		case 5:
			comment.setText("Place the Destroyer with length 2")
			break;
	End method placeShipText
	
	method horizontalAlignment(horizontal) 
		if (horizontal) 
			comment.setText("Place the ship horizontally")
		else 
			comment.setText("Place the ship vertically")
	End method horizontalAlignment

	method setFontSize(ratio) 
		comment.setFont(new Font("Monaco", Font.PLAIN, 12 * ratio))
	End methodsetFontSize

	Method hitBefore 
		comment.setText("Hit this spot before")
	End method hitBefore

	Method outsideBounds 
		comment.setText("Click on the box inside the ocean")
	End method outsideBounds

	Method win 
		comment.setText("You Won!")
	End method win

	Method scoreBoard 
		comment.setText("Score Board")
	End method scoreBoard
	
	Method horizontalAlignment(boolean horizontal) 
		if (horizontal) 
			comment.setText("Place the ship horizontally")
		else {
			comment.setText("Place the ship vertically")
		End else if
	End method horizontalAlignment
 * End class Commentator
 */
