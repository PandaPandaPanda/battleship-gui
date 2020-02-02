/**************************************************************************************************
 * Name: Kevin Xu	
 * Date: January 11, 2020
 * Title: AI
 * Description: The class which plays against human player
 *************************************************************************************************/

package kevin;

import java.awt.Point;
import java.util.ArrayList;

public class AI {
	
	//===================================== Declaration
	private ArrayList<Point> previousMoves; //Record AI's previous moves
	private boolean isInHittingMode; //If the AI locked on hitting a ship
	private int hittingStartIndex; //Help determine target location for hitting mode
	private int shipSunkByAI; //Ships sunken by AI
	//=====================================
	
	/*****************************************
	 * Constructor 
	 * pre: None 
	 * post: AI instantiated
	 *****************************************/
	public AI() {
		previousMoves = new ArrayList<Point>();
		isInHittingMode = false;
		shipSunkByAI = 0;
	}

	/*******************************************************************************************
	 * Check the previous moves. If there are hits, then continue to fire on the adjacent location
	 * pre: None 
	 * post: Return the location to fire for AI
	 * @return
	 *******************************************************************************************/
	public Point fireOnHits() {
		ArrayList<Point> hitmoves = new ArrayList<Point>();

		// record hit moves
		for (int m = 1; m <= hittingMoveSize(); m++) {
			Point p = getPrevMove(m);
			int type = PlayingField.homeField.OceanMap[p.y][p.x];
			if (type >= 1 && type <= 5) // If hit another component
				hitmoves.add(p);
		}
		
		if (hitmoves.size() == 0) {
			return null;
		} else if (hitmoves.size() == 1) {
			Point p = hitmoves.get(0);
			int x = p.x;
			int y = p.y;

			// Search 4 directions of the single hit
			Point q = new Point(x + 1, y);
			if (isValid(q) && PlayingField.homeField.FakeMap[q.y][q.x] == 0) {
				
				addMove(q);
				return q;
			}
			q = new Point(x, y + 1);
			if (isValid(q) && PlayingField.homeField.FakeMap[q.y][q.x] == 0) {
				
				addMove(q);
				return q;
			}
			q = new Point(x - 1, y);
			if (isValid(q) && PlayingField.homeField.FakeMap[q.y][q.x] == 0) {
				
				addMove(q);
				return q;
			}
			q = new Point(x, y - 1);
			if (isValid(q) && PlayingField.homeField.FakeMap[q.y][q.x] == 0) {
				
				addMove(q);
				return q;
			}
		} else if (hitmoves.size() >= 2) {
			Point p0 = hitmoves.get(0);
			Point p1 = hitmoves.get(1);
			Point q;

			if (p0.getY() == p1.getY()) { // horizontal adjacent
				int xmax = Integer.MIN_VALUE; // Initialize xmax
				for (Point p : hitmoves)
					xmax = Math.max(xmax, p.x); // Find the maximum value of x for the hitting spots
				q = new Point(xmax + 1, p0.y); // The right of the spots on fire
				if (isValid(q) && (PlayingField.homeField.FakeMap[q.y][q.x] == 0)) {	
					addMove(q);
					return q;
				}
				int xmin = Integer.MAX_VALUE;
				for (Point p : hitmoves)
					xmin = Math.min(xmin, p.x);
				q = new Point(xmin - 1, p0.y);
				if (isValid(q) && (PlayingField.homeField.FakeMap[q.y][q.x] == 0)) {
					addMove(q);
					return q;
				}
			} else if (p0.getX() == p1.getX()) { // vertical adjacent
				int ymax = Integer.MIN_VALUE;
				for (Point p : hitmoves)
					ymax = Math.max(ymax, p.y);
				q = new Point(p0.x, ymax + 1);
				if (isValid(q) && (PlayingField.homeField.FakeMap[q.y][q.x] == 0)) {
					
					addMove(q);
					return q;
				}
				int ymin = Integer.MAX_VALUE;
				for (Point p : hitmoves)
					ymin = Math.min(ymin, p.y);
				q = new Point(p0.x, ymin - 1);
				if (isValid(q) && (PlayingField.homeField.FakeMap[q.y][q.x] == 0)) {
					addMove(q);
					return q;
				}
			}
		}
		setIsInHittingMode(false); // Give up this hitting thread
		return new Point((int) (Math.random() * Ocean.mapSize), // Return a randomly generated point
				(int) (Math.random() * Ocean.mapSize));
	}

	/*********************************
	 * Add the move the previousMoves
	 * pre: None 
	 * post: The move added
	 * @param p
	 *********************************/
	public void addMove(Point p) {
		previousMoves.add(p);
	}

	/*********************************
	 * Get previous move point
	 * pre: None 
	 * post: Previous move point got
	 * @param n
	 * @return
	 *********************************/
	public Point getPrevMove(int n) {
		if (n > previousMoves.size() || n < 1)
			return null;
		else
			return previousMoves.get(previousMoves.size() - n);
	}

	/*********************************
	 * Get previous move size
	 * pre: None 
	 * post: Previous move size got
	 * @return
	 *********************************/
	public int prevMoveSize() {
		return previousMoves.size();
	}

	/*************************************
	 * Set isIntHittingMode true or false
	 * pre: None 
	 * post: isIntHittingMode set
	 * @param mode
	 *************************************/
	public void setIsInHittingMode(boolean mode) {
		isInHittingMode = mode;
		if (mode)
			hittingStartIndex = previousMoves.size();
	}

	/*************************************
	 * Get hitting move size
	 * pre: None 
	 * post: Hitting move size get
	 * @return
	 *************************************/
	public int hittingMoveSize() {
		return previousMoves.size() - hittingStartIndex + 1;
	}

	/*************************************
	 * Check if is in hittingMode
	 * pre: None 
	 * post: True or false returned
	 * @return
	 *************************************/
	public boolean isInHittingMode() {
		return isInHittingMode;
	}

	/*************************************
	 * Check if is a valid location
	 * pre: None 
	 * post: True or false returned
	 * @param p
	 * @return
	 *************************************/
	public boolean isValid(Point p) {
		int x = p.x;
		int y = p.y;

		if (x > Ocean.mapSize - 1 || x < 0 || y > Ocean.mapSize - 1 || y < 0) {
			return false;
		}
		return true;
	}

	/*************************************
	 * Get number of ships sunk by AI
	 * pre: None 
	 * post: Return shipSunkByAI
	 * @return
	 *************************************/
	public int getShipSunkByAI() {
		return shipSunkByAI;
	}

	/*************************************
	 * Add one to shipSunkByAI 
	 * pre: None 
	 * post: Added to shipSunkByAI
	 *************************************/
	public void addShipSunkByAI() {
		shipSunkByAI++;
	}
}

/**
 * class AI 

	constructor AI 
		previousMoves = new ArrayListsmaller thanPointlarger than
		isInHittingMode = false
		shipSunkByAI = 0
	End constructor
	
	method fireOnHits 
		ArrayListsmaller thanPointlarger than hitmoves = new ArrayListsmaller thanPointlarger than

		for ( m = 1 m smaller or equals to hittingMoveSize m++) 
			p = getPrevMove(m)
			 type = PlayingField.homeField.OceanMap[p.y][p.x]
			if (type larger or equals to 1 && type smaller or equals to 5) 
				hitmoves.add(p)

		if (hitmoves.size == 0) 
			return null
		 else if (hitmoves.size == 1) 
			p = hitmoves.get(0)
			 x = p.x
			 y = p.y

			// Search 4 directions of the single hit
			q = new Point(x + 1, y)
			if (isValid(q) && PlayingField.homeField.FakeMap[q.y][q.x] == 0) 
				
				addMove(q)
				return q
			
			q = new Point(x, y + 1)
			if (isValid(q) && PlayingField.homeField.FakeMap[q.y][q.x] == 0) 
				
				addMove(q)
				return q
			
			q = new Point(x - 1, y)
			if (isValid(q) && PlayingField.homeField.FakeMap[q.y][q.x] == 0) 
				
				addMove(q)
				return q
			
			q = new Point(x, y - 1)
			if (isValid(q) && PlayingField.homeField.FakeMap[q.y][q.x] == 0) 
				
				addMove(q)
				return q
			
		 else if (hitmoves.size larger or equals to 2) 
			p0 = hitmoves.get(0)
			p1 = hitmoves.get(1)
			q

			if (p0.getY == p1.getY) 
				 xmax = Integer.MIN_VALUE 
				for (p : hitmoves)
					xmax = Math.max(xmax, p.x)
				q = new Point(xmax + 1, p0.y) 
				if (isValid(q) && (PlayingField.homeField.FakeMap[q.y][q.x] == 0)) 	
					addMove(q)
					return q
				
				 xmin = Integer.MAX_VALUE
				for (p : hitmoves)
					xmin = Math.min(xmin, p.x)
				q = new Point(xmin - 1, p0.y)
				if (isValid(q) && (PlayingField.homeField.FakeMap[q.y][q.x] == 0)) 
					addMove(q)
					return q
				
			 else if (p0.getX == p1.getX)
				 ymax = Integer.MIN_VALUE
				for (p : hitmoves)
					ymax = Math.max(ymax, p.y)
				q = new Point(p0.x, ymax + 1)
				if (isValid(q) && (PlayingField.homeField.FakeMap[q.y][q.x] == 0)) 
					
					addMove(q)
					return q
				
				 ymin = Integer.MAX_VALUE
				for (p : hitmoves)
					ymin = Math.min(ymin, p.y)
				q = new Point(p0.x, ymin - 1)
				if (isValid(q) && (PlayingField.homeField.FakeMap[q.y][q.x] == 0)) 
					addMove(q)
					return q

		setIsInHittingMode(false) 
		return new Point(() (Math.random * Ocean.mapSize), 
				() (Math.random * Ocean.mapSize))
				
	End method fireOnHits 

	method addMove(p) 
		previousMoves.add(p)
	End method addMove

	method getPrevMove(n) 
		if (n larger than previousMoves.size or n smaller than 1)
			return null
		else
			return previousMoves.get(previousMoves.size - n)
	End method getPrevMove

	method prevMoveSize 
		return previousMoves.size
	End method prevMoveSize

	method setIsInHittingMode(boolean mode) 
		isInHittingMode = mode
		if (mode)
			hittingStartIndex = previousMoves.size
	End ethod setIsInHittingMode

	method hittingMoveSize 
		return previousMoves.size - hittingStartIndex + 1
	End method hittingMoveSize 

	method isInHittingMode 
		return isInHittingMode
	End method isInHittingMode 

	method isValid(p) 
		 x = p.x
		 y = p.y

		if (x larger than Ocean.mapSize - 1 or x smaller than 0 or y larger than Ocean.mapSize - 1 or y smaller than 0) 
			return false
		return true
	End ethod isValid

	method getShipSunkByAI 
		return shipSunkByAI
	End method getShipSunkByAI 

	method addShipSunkByAI 
		shipSunkByAI++
	End method addShipSunkByAI
	
 End class AI 
 */
