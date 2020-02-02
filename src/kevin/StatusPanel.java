/**************************************************************
 * Name: Kevin Xu	
 * Date: December 27, 2019	
 * Title: StatusPanel
 * Description: The class that stores and display game status.
 *************************************************************/

package kevin;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusPanel extends JPanel {
	
	//=========================================================================
	private JLabel numTurnsLabel; //Label for number of turns
	private JLabel accuracyLabel; //Label for accuracy
	private JLabel shipsSunkLabel; //Label for ships sunken
	private JLabel shipsRemainingLabel; //Label for ships remaining
	private JLabel difficultyLabel; //Label for difficulty of the game
	private int turns, hits, accuracy, shipsSunk, shipsRemaining, difficulty; //Data stored for the labels
	//=========================================================================
	
	/*****************************************
	 * Constructor 
	 * pre: None
	 * post: A new StatusPanel instance created
	 *****************************************/
	public StatusPanel() {
		
		/* Initialize value for labels */
		turns = 1;
		hits = 0;
		shipsSunk = 0;
		shipsRemaining = 5;

		difficulty = PlayingField.enemyField.getDifficulty();
		if (difficulty == 1) {
			difficultyLabel = new JLabel("Difficulty: Easy");
		} else {
			difficultyLabel = new JLabel("Difficulty: Hard");
		}
		
		/* Initialize labels */
		numTurnsLabel = new JLabel("Turn " + turns);
		accuracyLabel = new JLabel("Accuracy: 100%"); // Later use 100 * hits/(turns-1) + "%" as the current turn is not counted
		shipsSunkLabel = new JLabel("Ships sunk: " + shipsSunk);
		shipsRemainingLabel = new JLabel("Ships remaining: " + shipsRemaining);

		/* Display labels */
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(difficultyLabel);
		add(numTurnsLabel);
		add(accuracyLabel);
		add(shipsSunkLabel);
		add(shipsRemainingLabel);
		
		
		difficultyLabel.setAlignmentX(StatusPanel.CENTER_ALIGNMENT);
		numTurnsLabel.setAlignmentX(StatusPanel.CENTER_ALIGNMENT);
		accuracyLabel.setAlignmentX(StatusPanel.CENTER_ALIGNMENT);
		shipsSunkLabel.setAlignmentX(StatusPanel.CENTER_ALIGNMENT);
		shipsRemainingLabel.setAlignmentX(StatusPanel.CENTER_ALIGNMENT);
	}

	/*****************************************
	 * Return the difficulty of the game
	 * pre: None
	 * post: Difficulty in String returned
	 *****************************************/
	public String getDifficulty() {
		switch (difficulty) {
		case 1:
			return "Easy";
		case 2:
			return "Hard";
		default:
			return null;
		}
	}

	/*************************
	 * Return the turns number
	 * pre: None
	 * post: turns returned
	 * @return
	 *************************/
	public int getTurns() {
		return turns;
	}

	/*************************
	 * Return the accuracy number
	 * pre: None
	 * post: accuracy returned
	 * @return
	 *************************/
	public int getAccuracy() {
		return accuracy;
	}

	/***********************************************
	 * Update turns numebr
	 * pre: Boolean true or false
	 * post: Add or subtract turns and displays turns
	 * @param add
	 ***********************************************/
	public void updateTurns(boolean add) {
		if (add) {
			turns++;
			numTurnsLabel.setText("Turn " + turns);
		} else {
			turns--;
			numTurnsLabel.setText("Turn " + turns);
		}
	}

	/***************************
	 * Update hits
	 * pre: None
	 * Post: hits plus one
	 ***************************/
	public void updateHits() {
		hits++;
	}

	/***************************************
	 * Update accuracy
	 * pre: None
	 * Post: Update and display accuracy
	 ***************************************/
	public void updateAccuracy() {
		accuracy = (int) Math.round(100 * (double) hits / (turns - 1));
		accuracyLabel.setText("Accuracy: " + accuracy + "%");
	}

	/*****************************************************************
	 * Update ship numbers
	 * pre: Ships shot
	 * post: Checks if a ship is sunken and whether the player wins 
	 * @param shipShotThisTime
	 * @return
	 *****************************************************************/
	public int updateShipsNum(int shipShotThisTime) {
		PlayingField.enemyField.getShip(shipShotThisTime).checkSunken();
		if (PlayingField.enemyField.getShip(shipShotThisTime).sunken) {
			shipsSunk++;
			shipsRemaining--;
			shipsSunkLabel.setText("Ships sunk: " + shipsSunk);
			shipsRemainingLabel.setText("Ships remaining: " + shipsRemaining);
			if (shipsRemaining == 0) {
				return 2;
			} else {
				return 1;
			}
		}
		return 0;
	}
	
	/********************************************
	 * Set preferred size for the ocean panel
	 * pre: none
	 * post: Panel's preferred size is set
	 ********************************************/
	public Dimension getPreferredSize() {
		return new Dimension(260, 100);
	}
}

/**
 * Class statusPanel
 * 	public StatusPanel 
		
		turns = 1
		hits = 0
		shipsSunk = 0
		shipsRemaining = 5

		difficulty = PlayingField.enemyField.getDifficulty
		if (difficulty == 1) 
			difficultyLabel = new JLabel("Difficulty: Easy")
		else 
			difficultyLabel = new JLabel("Difficulty: Hard")
		End else if
		
		numTurnsLabel = new JLabel("Turn " + turns)
		accuracyLabel = new JLabel("Accuracy: 100%") 
		shipsSunkLabel = new JLabel("Ships sunken: " + shipsSunk)
		shipsRemainingLabel = new JLabel("Ships remaining: " + shipsRemaining)

		add(difficultyLabel)
		add(numTurnsLabel)
		add(accuracyLabel)
		add(shipsSunkLabel)
		add(shipsRemainingLabel)
	

	Method getDifficulty 
		switch (difficulty) 
		case 1:
			return "Easy"
		case 2:
			return "Hard"
		default:
			return null
		End switch
	End method getDifficulty
	

	Method getTurns 
		return turns
	End method getTurns

	Method getAccuracy 
		return accuracy
	End method getAccuracy

	Method updateTurns(boolean add) 
		if (add) 
			turns++
			numTurnsLabel.setText("Turn " + turns)
		else 
			turns--
			numTurnsLabel.setText("Turn " + turns)
		End else if
	End updateTurns

	Method updateHits 
		hits++
	End method updateHits

	Method updateAccuracy 
		accuracy = Math.round(100 * hits / (turns - 1))
		accuracyLabel.setText("Accuracy: " + accuracy + "%")
	End method updateAccuracy

	Method updateShipsNum(shipShotThisTime) 
		PlayingField.enemyField.getShip(shipShotThisTime).checkSunken
		if (PlayingField.enemyField.getShip(shipShotThisTime).sunken) 
			shipsSunk++
			shipsRemaining--
			shipsSunkLabel.setText("Ships sunken: " + shipsSunk)
			shipsRemainingLabel.setText("Ships remaining: " + shipsRemaining)
			if (shipsRemaining equals 0) 
				return 2
			 else 
				return 1
			End else if
		End if
		return 0
	End method updateShipsNum

	Method getPreferredSize 
		return new Dimension(260, 100)
	End method getPreferredSize
 * End class statusPanel
 */
