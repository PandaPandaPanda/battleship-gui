/**************************************************************
 * Name: Kevin Xu	
 * Date: December 27, 2019	
 * Title: Ship
 * Description: The class that stores ship information along  
 * with the sub classes Carrier, Battleship, Cruiser, Submarine,
 * Frigate which extends the Ship class.
 *************************************************************/

package kevin;

public class Ship {

	//================================ Declaration
	protected boolean sunken = false; //Determine whether the ship is sunken
	private int intactParts; //Ship's parts remaining
	private int x = -1, y = -1, length; //xy location, length of the ship
	private boolean horizontal; //Whether the ship is horizontal
	//================================
	
	/*****************************************
	 * Constructor 
	 * pre: None
	 * post: A new Ship instance created
	 * @param length
	 *****************************************/
	public Ship(int length) {
		this.length = length;
		intactParts = length;
	}

	/*****************************************
	 * Set location and length for the ship
	 * pre: Ship is placed in the ocean
	 * post: A new Background instance created
	 * @param x
	 * @param y
	 * @param horizontal
	 *****************************************/
	public void setXYHorizontal(int x, int y, boolean horizontal) {
		this.x = x; // Leftmost position of the ship
		this.y = y;// Highest position of the ship
		this.horizontal = horizontal;

	}
	
	/*****************************************
	 * A ship is shot
	 * pre: A ship is shot
	 * post: intactPars subtracted 1
	 *****************************************/
	public void shot() {
		intactParts--;
	}

	/*****************************************
	 * Update if a ship is sunken
	 * pre: A ship is shot
	 * post: sunken = true is the ship is sunken
	 *****************************************/
	public boolean checkSunken() {
		if (intactParts == 0) {
			sunken = true;
			return true;
		}
		return false;
	}

	/****************************************
	 * Return the length of the ship
	 * pre: None
	 * post: length of the ship is returned
	 * @return
	 ***************************************/
	public int getLength() {
		return length;
	}

	/****************************************
	 * Return x value of the ship
	 * pre: None
	 * post: x value of the ship is returned
	 * @return
	 ***************************************/
	public int getX() {
		return x;
	}

	/****************************************
	 * Return the y value of the ship
	 * pre: None
	 * post: y value of the ship is returned
	 * @return
	 ***************************************/
	public int getY() {
		return y;
	}

	/****************************************
	 * Return the whether the ship is horizontal
	 * pre: None
	 * post: True or false returned
	 * @return
	 ***************************************/
	public boolean getHorizontal() {
		return horizontal;
	}
}

class Carrier extends Ship {

	/*****************************************
	 * Constructor 
	 * pre: None
	 * post: A new Carrier instance created
	 * @param length
	 *****************************************/
	public Carrier(int length) {
		super(length);
	}

	/*****************************************
	 * Return the ship label
	 * pre: None
	 * post: Ship label returned
	 * @return
	 *****************************************/
	public int getLabelNumber() {
		return 1;
	}

}

class Battleship extends Ship {

	/*****************************************
	 * Constructor 
	 * pre: None
	 * post: A new Battleship instance created
	 * @param length
	 *****************************************/
	public Battleship(int length) {
		super(length);
	}

	/*****************************************
	 * Return the ship label
	 * pre: None
	 * post: Ship label returned
	 * @return
	 *****************************************/
	public int getLabelNumber() {
		return 2;
	}

}

class Cruiser extends Ship {

	/*****************************************
	 * Constructor 
	 * pre: None
	 * post: A new Battleship instance created
	 * @param length
	 *****************************************/
	public Cruiser(int length) {
		super(length);
	}

	/*****************************************
	 * Return the ship label
	 * pre: None
	 * post: Ship label returned
	 * @return
	 *****************************************/
	public int getLabelNumber() {
		return 3;
	}

}

class Submarine extends Ship {

	/*****************************************
	 * Constructor 
	 * pre: None
	 * post: A new Submarine instance created
	 * @param length
	 *****************************************/
	public Submarine(int length) {
		super(length);
	}

	/*****************************************
	 * Return the ship label
	 * pre: None
	 * post: Ship label returned
	 * @return
	 *****************************************/
	public int getLabelNumber() {
		return 4;
	}

}

class Destroyer extends Ship {

	/*****************************************
	 * Constructor 
	 * pre: None
	 * post: A new Frigate instance created
	 * @param length
	 *****************************************/
	public Destroyer(int length) {
		super(length);
	}

	/*****************************************
	 * Return the ship label
	 * pre: None
	 * post: Ship label returned
	 * @return
	 *****************************************/
	public int getLabelNumber() {
		return 5;
	}

}

/**
 * Class ship
 * 	sunken = false
 * 	Constructor Ship(length) 
		this.length = length
		intactParts = length
	End constructor Ship

	Method setXYHorizontal(x, y, horizontal) 
		this.x = x 
		this.y = y
		this.horizontal = horizontal
	End method setXYHorizontal
	

	Method shot 
		intactParts--
	End method shot

	Method checkSunken 
		if (intactParts equals to 0) 
			sunken = true
		End if
	End method checkSunken
	

	Method getLength 
		return length
	End method getLength

	Method getX 
		return x
	End method getX

	Method getY 
		return y
	End method getY

	Method getHorizontal 
		return horizontal
	End method getHorizontal

End class ship

class Carrier extends Ship 

	Constructor Carrier( length) 
		super(length)
	End constructor Carrier
	
	Method getLabelNumber 
		return 3
End class Carrier

class Battleship extends Ship 

	Constructor Battleship( length) 
		super(length)
	End constructor Battleship
	
	Method getLabelNumber 
		return 3
End class Battleship

class Cruiser extends Ship 

	Constructor Cruiser( length) 
		super(length)
	End constructor Battleship
	
	Method getLabelNumber 
		return 3
End class Cruiser


class Submarine extends Ship 

	public Submarine(length) 
		super(length)
	End constructor Submarine

	
	Method getLabelNumber 
		return 4
	End method getLabelNumber
End class submarine	



class Frigate extends Ship 

	public Frigate(length) 
		super(length)
	End constructor Frigate

	
	Method getLabelNumber 
		return 5
	End method getLabelNumber
End class Frigate	


 */
