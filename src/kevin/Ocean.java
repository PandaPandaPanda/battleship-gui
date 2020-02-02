/**************************************************************************
 * Name: Kevin Xu	
 * Date: December 27, 2019	
 * Title: Ocean
 * Description: Class that draws the ocean and saves the ship location data
 *************************************************************************/

package kevin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Ocean extends JPanel {
	
	// ================================== Declaration
	public static final int SPLASH = 9, FIRE = 8, WATER = 0; // Constant for splash, fire and water
	private String title;
	
	protected Carrier ship1; // Variables storing the five ships
	protected Battleship ship2;
	protected Cruiser ship3;
	protected Submarine ship4;
	protected Destroyer ship5;
	protected int length; // Length of the panel
	protected double unit; // Length of the each unit of square
	protected int difficulty; // Difficulty of the game (Hard or Easy)
	protected static Image oceanImg; // Image for the ocean

	protected static int mapSize; // Size of the map (Hard is 15, Easy is 10)

	public int[][] OceanMap; // The map stores the ship locations
	public int[][] FakeMap; // The map showing to the user
	// ==================================
	
	/********************************************
	 * Constructor
	 * pre: none
	 * post: An instance of the ocean is created
	 * @param difficulty
	 ********************************************/
	public Ocean(int difficulty, String title) {
		this.title = title;
		
		try { //Display the ocean image
			oceanImg = ImageIO.read(new File("Images//ocean.jpg"));
		} catch (IOException exc) {
		}

		this.difficulty = difficulty;
		if (difficulty == 1) {
			mapSize = 10;
		} else {
			mapSize = 15;
		}
		OceanMap = new int[mapSize][mapSize];
		FakeMap = new int[mapSize][mapSize];
		
		ship1 = new Carrier(5);
		ship2 = new Battleship(4);
		ship3 = new Cruiser(4);
		ship4 = new Submarine(3);
		ship5 = new Destroyer(2);
	}

	/********************************************
	 * Set preferred size for the ocean panel
	 * pre: none
	 * post: Panel's preferred size is set
	 ********************************************/
	public Dimension getPreferredSize() {
		return new Dimension(length, length + 15);
	}


	/*************************************************
	 * Check whether user's click is within the bounds
	 * pre: User clicks
	 * post: True or false returned
	 * @param x
	 * @param y
	 * @return
	 *************************************************/
	public boolean withinBounds(int x, int y) {
		if (x < mapSize && y < mapSize) {
			return true;
		} else {
			return false;
		}
	}

	/********************************************
	 * Checks whether user clicks on a duplicated spot
	 * pre: User clicks
	 * post: True or false returned
	 * @param x
	 * @param y
	 * @return
	 ********************************************/
	public boolean checkHittenBefore(int x, int y) {
		if (FakeMap[y][x] == SPLASH || FakeMap[y][x] == FIRE) {
			return true;
		} else {
			return false;
		}
	}

	/********************************************
	 * Checks whether user hits a ship
	 * pre: User clicks
	 * post: True or false returned
	 * @param x
	 * @param y
	 * @return
	 ********************************************/
	public int checkHitShip(int x, int y) {
		if (OceanMap[y][x] <= 5 && OceanMap[y][x] >= 1) {
			FakeMap[y][x] = FIRE;
			return OceanMap[y][x];
		} else {
			FakeMap[y][x] = SPLASH;
			return SPLASH;
		}
	}

	/***************************************
	 * Return the instance of a ship
	 * pre: None
	 * post: Instance of a ship is returned
	 * @param num
	 * @return
	 ***************************************/
	public Ship getShip(int num) {
		switch (num) {
		case 1:
			return ship1;
		case 2:
			return ship2;
		case 3:
			return ship3;
		case 4:
			return ship4;
		case 5:
			return ship5;
		}
		return null;
	}

	/**************************************
	 * Get the length of each block
	 * pre: None
	 * post: Length of a block is returned
	 **************************************/
	public int getBlockLength() {
		return length;
	}
	
	public double getBlockUnit() {
		return unit;
	}

	/**************************************
	 * Get the difficulty of the game
	 * pre: None
	 * post: Difficulty of the game is returned
	 **************************************/
	public int getDifficulty() {
		return difficulty;
	}

	@Override
	public void paintComponent(Graphics g) { // Paints out the ocean where the user is going to be playing with
		Graphics2D g2 = (Graphics2D) g;

		Rectangle r = PlayingField.battlePane.getBounds();
		length = Math.min(r.height - 20, r.width/2 - 10); // Leave some height to draw title
		unit = (double) length / mapSize;

		g.drawImage(oceanImg, 0, 0, length, length, this);

		g2.setColor(new Color(0, 100, 90));
		for (int i = 1; i < mapSize; i++) {
			g2.drawLine((int) (i * unit), 0, (int) (i * unit), length);
			g2.drawLine(0, (int) (i * unit), length, (int) (i * unit));
		}
		g2.drawString(title, (int)(unit * (mapSize / 2 - 1)), length + 15);
	}
}

/**
 * Class Ocean
 * 	private static final  SPLASH = 9 
	private static final  FIRE = 8

	Constructor Ocean(difficulty) 
		oceanImg = ImageIO.read(new File("Images//ocean.jpg"))
		
		this.difficulty = difficulty
		if (difficulty equals 1) 
			mapSize = 10
		 else 
			mapSize = 15
		End else if 
		
		OceanMap = new [mapSize][mapSize]
		FakeMap = new [mapSize][mapSize]

		ship1 = new Carrier(5)
		ship2 = new Battleship(4)
		ship3 = new Cruiser(4)
		ship4 = new Submarine(3)
		ship5 = new Frigate(2)
		placeShip(ship1.getLabelNumber, ship1.getLength, ship1)
		placeShip(ship2.getLabelNumber, ship2.getLength, ship2)
		placeShip(ship3.getLabelNumber, ship3.getLength, ship3)
		placeShip(ship4.getLabelNumber, ship4.getLength, ship4)
		placeShip(ship5.getLabelNumber, ship5.getLength, ship5)

	End constructor Ocean

	Method getPreferredSize 
		return new Dimension(251, 270)
	End method getPreferredSize

	Method placeShip( num,  length, Ship ship) 
		boolean horizontal = true

		if (((Math.random * (2)) equals 0) 
			horizontal = false
		End if

		if (horizontal)  // Generate position
			x = (Math.random * (mapSize + 1 - length))
			y = (Math.random * (mapSize))
		 else 
			x = (Math.random * (mapSize))
			y = (Math.random * (mapSize + 1 - length))
		End else if

		boolean canPlaceTheShip = true

		if (horizontal) 
			for (i = 0 i smaller than length step 1) 
				if (OceanMap[y][x + i] != 0) 
					canPlaceTheShip = false
				End if
			End for loop	

			if (canPlaceTheShip) 
				for (j = 0 j smaller than length step 1) 
					OceanMap[y][x + j] = num
				
				ship.setXYHorizontal(x, y, horizontal)
			 else 
				placeShip(num, length, ship)
			End else if 
			
		else 
		
			for (i = 0 i smaller than length step 1) 
				if (OceanMap[y + i][x] != 0) 
					canPlaceTheShip = false
				End if
			End for loop

			if (canPlaceTheShip) 
				for ( i = 0 i smaller than length step 1) // Pass the test can now place the ship
					OceanMap[y + i][x] = num
				
				ship.setXYHorizontal(x, y, horizontal)
			 else 
				placeShip(num, length, ship)
			End else if
		End else if
		
	

	Method withinBounds(x, y) 
		if (x smaller than mapSize && y smaller than mapSize) 
			return true
		else 
			return false
		End else if
	End method withinBounds
	

	Method checkHittenBefore(x, y) 
		if (FakeMap[y][x] equals SPLASH or FakeMap[y][x] equals FIRE) 
			return true
		 else 
			return false
		End else if
	End method checkHittenBefore
	

	Method checkHitShip(x, y) 
		if (OceanMap[y][x] smaller than= 5 and OceanMap[y][x] larger than or equals to 1) 
			FakeMap[y][x] = FIRE
			return OceanMap[y][x]
		else 
			FakeMap[y][x] = SPLASH
			return SPLASH
		End else if
	End method checkHitShip
	

	Method getShip(num) 
		switch (num) 
		case 1:
			return ship1
		case 2:
			return ship2
		case 3:
			return ship3
		case 4:
			return ship4
		case 5:
			return ship5
		return null
	End method getShip

	Method getBlockLength 
		return length
	End method getBlockLength

	Method getDifficulty 
		return difficulty
	End method getBlockLength
		
	Override paintComponent(Graphics g) 
		Graphics2D g2 = (Graphics2D) g

		Rectangle r = this.getBounds
		length = Minimum(r.height, r.width)
		unit = length / mapSize
		
		g.drawImage(oceanImg, 0, 0, length, length, this)

		g2.setColor(new Color(0, 100, 90))
		for (i = 1 i smaller than mapSize step 1) 
			g2.drawLine(() (i * unit), 0, () (i * unit), length)
			g2.drawLine(0, () (i * unit), length, () (i * unit))
		End for
	End override paintComponent
 * End class Ocean
 */
