/*****************************************************************************
 * Name: Kevin Xu	
 * Date: December 27, 2019	
 * Title: HomeField
 * Description: The class which stores most run-time data for player's ocean
 *****************************************************************************/

package kevin;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class HomeField extends Ocean {
	
	//========================= Declaration
	private boolean horizontal;
	//=========================
	
	/*****************************************
	 * Constructor 
	 * pre: None
	 * post: EnemyField instantiated
	 * @param difficulty
	 *****************************************/
	public HomeField(int difficulty, String title) {
		super(difficulty, title);
		ship1 = new Carrier(5);
		ship2 = new Battleship(4);
		ship3 = new Cruiser(4);
		ship4 = new Submarine(3);
		ship5 = new Destroyer(2);
		
		horizontal = true;
	}

	/***************************************************************
	 * Check if the user can place the five ships using recursion
	 * pre: none
	 * post: The ships are palced
	 * @param num
	 * @param length
	 * @param ship
	 ***************************************************************/
	protected boolean validPlacement(int x, int y, boolean horizontal, Ship ship) {
		if (horizontal) {
			if (x + ship.getLength()> mapSize)
				return false;
			for (int i = 0; i < ship.getLength(); i++)
				if (OceanMap[y][x + i] != 0) {
					return false; // if overlapping
				}
		} else {
			if (y + ship.getLength() > mapSize)
				return false;
			for (int i = 0; i < ship.getLength(); i++)
				if (OceanMap[y + i][x] != 0) {
					return false; // if overlapping
				}
		}
		return true;
	}
	
	/******************************************
	 * Place the ship if the location is valid
	 * pre: User clicks
	 * post: Ship placed if location is valid
	 * @param x
	 * @param y
	 * @param num
	 * @param ship
	 * @param horizontal
	 * @return
	 *****************************************/
	public boolean placeShip(int x, int y, int num, Ship ship, boolean horizontal) { 

		if (validPlacement(x, y, horizontal, ship)) {

			if (horizontal) { // Generate position
				for (int j = 0; j < ship.getLength(); j++) {
					OceanMap[y][x + j] = num;
					ship.setXYHorizontal(x, y, horizontal);
				}
			} else {
				for (int i = 0; i < ship.getLength(); i++) {
					OceanMap[y + i][x] = num;
				}
				ship.setXYHorizontal(x, y, horizontal);
			}
			
			repaint();
			PlayingField.shipPlaced = true;
			return true;
		}
		return false;
		
	}
	
	/******************************************
	 * Check if request placement is horizontal
	 * pre: placeShip method invoked
	 * post: True or false returned
	 * @return
	 *****************************************/
	public boolean isHorizontal() {
		return horizontal;
	}

	/**************************************************
	 * Set ship placement to be horizontal or vertical
	 * pre: button clicked
	 * post: Placement alignment set
	 * @return
	 *************************************************/
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		double unit = (double) PlayingField.homeField.getBlockLength() / mapSize;

		PlayingField.commentator.setFontSize(unit * mapSize / 250);

		/* Paint ships */
		for (int i = 1; i <= 5; i++) { // Draw my ships
			Ship ship = PlayingField.homeField.getShip(i);
			int xPos = ship.getX();

			if (xPos >= 0) {
				int yPos = ship.getY();
				boolean horizontal = ship.getHorizontal();

				if (horizontal) {
					g2.drawImage(PlayingField.ships[i][1], (int) (unit * xPos), (int) (unit * yPos),
							(int) (PlayingField.homeField.getShip(i).getLength() * unit), (int) unit, this);
				} else {
					g2.drawImage(PlayingField.ships[i][2], (int) (unit * xPos), (int) (unit * yPos), (int) unit,
							(int) (PlayingField.homeField.getShip(i).getLength() * unit), this);
				}
			}
		}

		/* Paint fire and splash */
		for (int y = 0; y < mapSize; y++) {
			for (int x = 0; x < mapSize; x++) {
				if (PlayingField.homeField.FakeMap[y][x] != 0) {
					if (PlayingField.homeField.FakeMap[y][x] == 9) { // Fill up splash icons
						g2.drawImage(PlayingField.splash, (int) (unit * x), (int) (unit * y), (int) unit, (int) unit,
								this);
					} else if (PlayingField.homeField.FakeMap[y][x] == 8) { // Fill up fire icons
						g2.drawImage(PlayingField.fire, (int) (unit * x), (int) (unit * y), (int) unit, (int) unit,
								this);
					}
				}
			}
		}
	}
}

/**
 * class HomeField extends Ocean 

	constructor HomeField(difficulty, title) 
		super(difficulty, title)
		ship1 = new Carrier(5)
		ship2 = new Battleship(4)
		ship3 = new Cruiser(4)
		ship4 = new Submarine(3)
		ship5 = new Destroyer(2)
		
		horizontal = true
	End constructor HomeField

	method validPlacement(x,  y, horizontal, ship) 
		if (horizontal) 
			if (x + ship.getLength larger than mapSize)
				return false
			End if
			
			for (i = 0 i smaller than ship.getLength step 1)
				if (OceanMap[y][x + i] not equals to 0) 
					return false 
				End if
			End for loop
				
		 else 
			if (y + ship.getLength > mapSize)
				return false
			End if
			for ( i = 0 i < ship.getLength i++)
				if (OceanMap[y + i][x] not equals to 0) 
					return false 
				End if
			ENd for
		End else if	
		
		return true
	End method validPlacement
	
	method placeShip( x,  y,  num, ship, horizontal)  

		if (validPlacement(x, y, horizontal, ship)) 

			if (horizontal)  
				for (j = 0 j smaller than ship.getLength step 1) 
					OceanMap[y][x + j] = num
					ship.setXYHorizontal(x, y, horizontal)
				
			 else 
				for ( i = 0 i smaller than ship.getLength step 1) 
					OceanMap[y + i][x] = num
				
				ship.setXYHorizontal(x, y, horizontal)
			
			repaint
			PlayingField.shipPlaced = true
			return true
		
		return false
	End method placeShip
	
	
	@Override
	method paintComponent(Graphics g) 
		super.paintComponent(g)
		Graphics2D g2 = (Graphics2D) g
		double unit = (double) PlayingField.homeField.getBlockLength / mapSize

		PlayingField.commentator.setFontSize(unit * mapSize / 250)

		for (i = 1 i smaller or equals to 5 step 1)  
			Ship ship = PlayingField.homeField.getShip(i)
			 xPos = ship.getX

			if (xPos larger or equals to 0) 
				yPos = ship.getY
				horizontal = ship.getHorizontal

				if (horizontal) 
					g2.drawImage(PlayingField.ships[i][1],  (unit * xPos),  (unit * yPos),
							 (PlayingField.homeField.getShip(i).getLength * unit),  unit, this)
				 else 
					g2.drawImage(PlayingField.ships[i][2],  (unit * xPos),  (unit * yPos),  unit,
							 (PlayingField.homeField.getShip(i).getLength * unit), this)
				End else if
			End if
		End for
	

		for (y = 0 y smaller than mapSize step 1) 
			for ( x = 0 x smaller than mapSize step 1) 
				if (PlayingField.homeField.FakeMap[y][x] not equals to 0) 
					if (PlayingField.homeField.FakeMap[y][x] equals to 9) 
						g2.drawImage(PlayingField.splash,  (unit * x),  (unit * y),  unit,  unit, this)
					 else if (PlayingField.homeField.FakeMap[y][x] equals to 8)  
						g2.drawImage(PlayingField.fire,  (unit * x),  (unit * y),  unit,  unit, this)
					End else if
				End if
			End for
		End for
			

	method isHorizontal 
		return horizontal
	End method isHorizontal

	method setHorizontal(horizontal) 
		this.horizontal = horizontal
	End method setHorizontal
	
End class homeField
 */