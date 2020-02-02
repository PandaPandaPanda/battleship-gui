/*****************************************************************************
 * Name: Kevin Xu	
 * Date: December 27, 2019	
 * Title: EnemyField
 * Description: The class which stores most run-time data for AI's ocean
 *****************************************************************************/

package kevin;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class EnemyField extends Ocean {

	/*****************************************
	 * Constructor 
	 * pre: None
	 * post: EnemyField instantiated
	 * @param difficulty
	 *****************************************/
	public EnemyField(int difficulty, String title) {
		super(difficulty, title);
		placeShip(ship1.getLabelNumber(), ship1.getLength(), ship1);
		placeShip(ship2.getLabelNumber(), ship2.getLength(), ship2);
		placeShip(ship3.getLabelNumber(), ship3.getLength(), ship3);
		placeShip(ship4.getLabelNumber(), ship4.getLength(), ship4);
		placeShip(ship5.getLabelNumber(), ship5.getLength(), ship5);

		debug();
	}

	/*******************************************************
	 * Prints the location of the ships (For debug use only
	 * pre: none
	 * post: Prints the location of the ships
	 *******************************************************/
	public void debug() {
		for (int y = 0; y < mapSize; y++) {
			for (int x = 0; x < mapSize; x++) {
				System.out.print(OceanMap[y][x] + " ");
			}
			System.out.println();
		}
	}

	/************************************************
	 * Randomly place the five ships using recursion
	 * pre: none
	 * post: The ships are palced
	 * @param num
	 * @param length
	 * @param ship
	 ************************************************/
	public void placeShip(int num, int length, Ship ship) { 
		boolean horizontal = true;
		int x, y;

		if ((int) (Math.random() * (2)) == 0) {
			horizontal = false;
		}

		if (horizontal) { // Generate position
			x = (int) (Math.random() * (mapSize + 1 - length));
			y = (int) (Math.random() * (mapSize));
		} else {
			x = (int) (Math.random() * (mapSize));
			y = (int) (Math.random() * (mapSize + 1 - length));
		}

		boolean canPlaceTheShip = true;

		if (horizontal) { //Recursively checks for spots to place the ship
			for (int i = 0; i < length; i++) {
				if (OceanMap[y][x + i] != 0) {
					canPlaceTheShip = false;
				}
			}

			if (canPlaceTheShip) {
				for (int j = 0; j < length; j++) { // Pass the test can now place the ship
					OceanMap[y][x + j] = num;
				}
				ship.setXYHorizontal(x, y, horizontal);
			} else {
				placeShip(num, length, ship);
			}
		} else {
			for (int i = 0; i < length; i++) {
				if (OceanMap[y + i][x] != 0) {
					canPlaceTheShip = false;
				}
			}

			if (canPlaceTheShip) {
				for (int i = 0; i < length; i++) {// Pass the test can now place the ship
					OceanMap[y + i][x] = num;
				}
				ship.setXYHorizontal(x, y, horizontal);
			} else {
				placeShip(num, length, ship);
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) { // Prints out the ocean that is updated with the splash, fire, and ships
												// images
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		double unit = (double) PlayingField.enemyField.getBlockLength() / mapSize;

		PlayingField.commentator.setFontSize(unit * mapSize / 250);
		for (int y = 0; y < mapSize; y++)
			for (int x = 0; x < mapSize; x++) {
				if (PlayingField.enemyField.FakeMap[y][x] != 0) {
					if (PlayingField.enemyField.FakeMap[y][x] == 9) { // Fill up splash icons
						g2.drawImage(PlayingField.splash, (int) (unit * x), (int) (unit * y), (int) unit, (int) unit,
								this);
					} else if (PlayingField.enemyField.FakeMap[y][x] == 8) { // Fill up fire icons
						g2.drawImage(PlayingField.fire, (int) (unit * x), (int) (unit * y), (int) unit, (int) unit,
								this);
						int shipShotThisTime = PlayingField.enemyField.OceanMap[y][x];
						if (PlayingField.enemyField.getShip(shipShotThisTime).sunken) {// Draw ship image when ship is
																						// sunken
							Ship ship = PlayingField.enemyField.getShip(shipShotThisTime);
							int xPos = ship.getX();
							int yPos = ship.getY();
							boolean horizontal = ship.getHorizontal();

							if (horizontal) {
								g2.drawImage(PlayingField.ships[shipShotThisTime][1], (int) (unit * xPos),
										(int) (unit * yPos),
										(int) (PlayingField.enemyField.getShip(shipShotThisTime).getLength() * unit),
										(int) unit, this);
							} else {
								g2.drawImage(PlayingField.ships[shipShotThisTime][2], (int) (unit * xPos),
										(int) (unit * yPos), (int) unit,
										(int) (PlayingField.enemyField.getShip(shipShotThisTime).getLength() * unit),
										this);
							}
						}
					}
				}
			}
	}
}


/**
 * public class EnemyField extends Ocean 

	Constructor EnemyField(difficulty, title) 
		super(difficulty, title)
		placeShip(ship1.getLabelNumber, ship1.getLength, ship1)
		placeShip(ship2.getLabelNumber, ship2.getLength, ship2)
		placeShip(ship3.getLabelNumber, ship3.getLength, ship3)
		placeShip(ship4.getLabelNumber, ship4.getLength, ship4)
		placeShip(ship5.getLabelNumber, ship5.getLength, ship5)

		debug
	End Constructor EnemyField

	method debug 
		for ( y = 0 y smaller than mapSize step 1) 
			for ( x = 0 x smaller than mapSize step 1) 
				System.out.print(OceanMap[y][x] + " ")
			End for
		End for
	End method debug
		
	

	method placeShip(num, length, ship)  
		boolean horizontal = true
		 x, y

		if ( (Math.random * (2)) equals to 0) 
			horizontal = false
		

		if (horizontal) 
			x =  (Math.random * (mapSize + 1 - length))
			y =  (Math.random * (mapSize))
		 else 
			x =  (Math.random * (mapSize))
			y =  (Math.random * (mapSize + 1 - length))
		End else if

		boolean canPlaceTheShip = true

		if (horizontal)
			for ( i = 0 i smaller than length step 1) 
				if (OceanMap[y][x + i] != 0) 
					canPlaceTheShip = false
				End if
			End for
		End if
			

			if (canPlaceTheShip) 
				for ( j = 0 j smaller than length step 1) 
					OceanMap[y][x + j] = num
				
				ship.setXYHorizontal(x, y, horizontal)
			 else 
				placeShip(num, length, ship)
			End else if
			
		 else 
			for ( i = 0 i smaller than length step 1) 
				if (OceanMap[y + i][x] != 0) 
					canPlaceTheShip = false
				End if
			End for
		End else if
			

			if (canPlaceTheShip) 
				for ( i = 0 i smaller than length step 1)
					OceanMap[y + i][x] = num
				
				ship.setXYHorizontal(x, y, horizontal)
			 else 
				placeShip(num, length, ship)
			End else if
			
	End method placeShip	
	
	
	public void paintComponent(Graphics g) 
		super.paintComponent(g)
		Graphics2D g2 = (Graphics2D) g
		double unit = (double) PlayingField.enemyField.getBlockLength / mapSize

		PlayingField.commentator.setFontSize(unit * mapSize / 250)
		for ( y = 0 y smaller than mapSize step 1)
			for ( x = 0 x smaller than mapSize step 1) 
				if (PlayingField.enemyField.FakeMap[y][x] != 0) 
					if (PlayingField.enemyField.FakeMap[y][x] == 9) 
						g2.drawImage(PlayingField.splash,  (unit * x),  (unit * y),  unit,  unit,
								this)
					 else if (PlayingField.enemyField.FakeMap[y][x] == 8) 
						g2.drawImage(PlayingField.fire,  (unit * x),  (unit * y),  unit,  unit,
								this)
						 shipShotThisTime = PlayingField.enemyField.OceanMap[y][x]
						if (PlayingField.enemyField.getShip(shipShotThisTime).sunken) 
							Ship ship = PlayingField.enemyField.getShip(shipShotThisTime)
							 xPos = ship.getX
							 yPos = ship.getY
							boolean horizontal = ship.getHorizontal

							if (horizontal) 
								g2.drawImage(PlayingField.ships[shipShotThisTime][1],  (unit * xPos),
										 (unit * yPos),
										 (PlayingField.enemyField.getShip(shipShotThisTime).getLength * unit),
										 unit, this)
							 else 
								g2.drawImage(PlayingField.ships[shipShotThisTime][2],  (unit * xPos),
										 (unit * yPos),  unit,
										 (PlayingField.enemyField.getShip(shipShotThisTime).getLength * unit),
										this)
							End else if
						End if
					End if
				End if
			End for
		End for
	End paint component
 * 
 */