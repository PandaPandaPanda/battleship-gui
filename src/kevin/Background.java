/**************************************************************
 * Name: Kevin Xu	
 * Date: December 27, 2019	
 * Title: Background
 * Description: Class that paints the background of the cover.
 **************************************************************/

package kevin;

import java.awt.*;
import javax.swing.*;

public class Background extends JPanel {
	
	// ================================ Declare variables
	private Image img; //Store the image
	private boolean keepBackground = true; //Boolean variable to inform the program whether or not to keep the background
	// ================================

	/*****************************************
	 * Constructor 
	 * pre: Image input 
	 * post: A new Background instance created
	 * @param img
	 *****************************************/
	public Background(Image img) {
		this.img = img;
	}

	/********************************************************
	 * User update the keepBackground variable
	 * pre: none
	 * post: The keepBackground boolean variable is modified
	 * @param keep
	 ********************************************************/
	public void background(boolean keep) {
		if (keep == true) {
			keepBackground = true;
		} else {
			keepBackground = false;
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		if (keepBackground == false) {
			super.paintComponent(g);
		}
	}
}

/**
 * Class Background
 * 	Constructor Background(img)
 * 		this img = img
 * 	End constructor Background
 * 
 * 	Constructor background(keep) 
		if (keep equals true) 
			keepBackground = true
		else 
			keepBackground = false
		End else if
	End constructor Background

	paintComponent(Graphics g) 

		g.drawImage(img, 0, 0, getWidth(), getHeight(), this)
		if (keepBackground equals false) 
			super.paintComponent(g)
		ENd else if
	End override paintComponent
 * End class Background
 */