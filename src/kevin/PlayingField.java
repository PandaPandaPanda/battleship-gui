/**************************************************************************************************
 * Name: Kevin Xu	
 * Date: December 27, 2019	
 * Title: PlayingField
 * Description: The class which stores most run-time data and responsible for taking user input 
 *************************************************************************************************/

package kevin;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class PlayingField implements ActionListener {
	
	// ============================================================================================= Declaration
	private static final int CARRIER = 1, BATTLESHIP = 2, CRUISER = 3, SUBMARINE = 4, DESTROYER = 5; // Constants of the ships
	
	/* Main components */
	static Background contentPane; // Content panel
	static EnemyField enemyField; // Instance of the EnemyField Class
	static HomeField homeField;// Instance of the HomeField Class
	static StatusPanel statusPane; // Instance of the statusPanel
	static Commentator commentator; // Instance of Commentator
	static Score score; // Instance of Score
	static JFrame frame; // Frame of the program
	
	/* Images */
	private static Image backgroundImg; // Background image
	protected static Image splash, fire; // Image of splash and fire
	protected static Image[][] ships = new Image[6][3]; // Array that stores the image the ships

	/* Ship placement component */
	static JPanel shipsPane;
	static JPanel battlePane; // Panel for two oceans
	protected static JButton carrierB, battleshipB, cruiserB, submarineB, destroyerB; //Buttons for ship placement
	
	private static int selectedShip = 0; //Selected ship label number when placing
	private int shipPlacedNum; //Number of ships placed
	static boolean shipPlaced = true; //Check if the current shup selected has been placed
	
	/* Cover page Components */
	private JLabel initLabel1, initLabel2; // Label used for game cover
	private JButton initButtonEasy, initButtonHard; // Button used for game cover

	/* AI */
	static AI ai;

	/* Listeners */
	private static MouseListener homeFieldML;
	private static MouseListener enemyFieldML;
	// =============================================================================================

	/*****************************************
	 * Constructor 
	 * pre: None 
	 * post: A instance of PlayingField is created
	 *****************************************/
	public PlayingField() {
		ships[CARRIER][1] = (new ImageIcon("Images/carrier.gif")).getImage();
		ships[BATTLESHIP][1] = (new ImageIcon("Images/battleship.gif")).getImage();
		ships[CRUISER][1] = (new ImageIcon("Images/cruiser.gif")).getImage();
		ships[SUBMARINE][1] = (new ImageIcon("Images/submarine.gif")).getImage();
		ships[DESTROYER][1] = (new ImageIcon("Images/destroyer.gif")).getImage();
		ships[CARRIER][2] = (new ImageIcon("Images/carrierv.gif")).getImage();
		ships[BATTLESHIP][2] = (new ImageIcon("Images/battleshipv.gif")).getImage();
		ships[CRUISER][2] = (new ImageIcon("Images/cruiserv.gif")).getImage();
		ships[SUBMARINE][2] = (new ImageIcon("Images/submarinev.gif")).getImage();
		ships[DESTROYER][2] = (new ImageIcon("Images/destroyerv.gif")).getImage();
		splash = (new ImageIcon("Images/splash.gif")).getImage();
		fire = (new ImageIcon("Images/firex.gif")).getImage();

		init();
		coverPage();
	}

	/*****************************************
	 * Set up the frame
	 * pre: None
	 * post: The frame is set
	 *****************************************/
	public void init() {
		/* Create and set up the frame */
		frame = new JFrame("Battleship");
		frame.setMinimumSize(new Dimension(755, 580));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try // Set up background
		{
			backgroundImg = ImageIO.read(new File("Images//background.jpg"));
		} catch (IOException exc) {
		}

		contentPane = new Background(backgroundImg);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.RAISED));

		/* Add content pane to frame */
		frame.add(contentPane, BorderLayout.CENTER);
	}

	/*****************************************
	 * Set up the cover page
	 * pre: None
	 * post: The cover page is set
	 *****************************************/
	public void coverPage() {
		initLabel1 = new JLabel("Battleship");
		initLabel2 = new JLabel("by Kevin Xu");
		initLabel1.setFont(new Font("Monaco", Font.BOLD, 20));
		initLabel2.setFont(new Font("Monaco", Font.BOLD, 20));
		initLabel1.setForeground(Color.white);
		initLabel2.setForeground(Color.white);
		initLabel1.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		initLabel2.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		initLabel1.setBorder(BorderFactory.createEmptyBorder(150, 50, 0, 50));
		initLabel2.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));

		contentPane.add(initLabel1);
		contentPane.add(initLabel2);
		
		/* Create and add button that is centered */
		initButtonEasy = new JButton("Easy Mode (10x10 Map)");
		initButtonEasy.setActionCommand("easy");
		initButtonEasy.addActionListener(this);
		initButtonEasy.setAlignmentX(JButton.CENTER_ALIGNMENT);
		contentPane.add(initButtonEasy);
		initButtonHard = new JButton("Hard Mode (15x15 Map)");
		initButtonHard.setActionCommand("hard");
		initButtonHard.addActionListener(this);
		initButtonHard.setAlignmentX(JButton.CENTER_ALIGNMENT);
		contentPane.add(initButtonHard);
		frame.setVisible(true);
	}

	/*************************************************
	 * Set up two battle ocean and do ship placement
	 * pre: None
	 * post: The game is ready to start
	 * @param difficulty
	 *************************************************/
	public void setUp(int difficulty) {
		shipPlacedNum = 0;
		
		/* Instantiate the HomeField and EnemyField */
		if (difficulty == 1) { 
			enemyField = new EnemyField(1, "Enemy Field");
			homeField = new HomeField(1, "Home Field");
		} else if (difficulty == 2) {
			enemyField = new EnemyField(2, "Enemy Field");
			homeField = new HomeField(2, "Home Field");
		}
		
		battlePane = new JPanel();
		battlePane.setLayout(new GridLayout(1,2));
		commentator = new Commentator();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(commentator);
		contentPane.add(battlePane);
		battlePane.add(enemyField);
		battlePane.add(homeField);
		
		shipButtonSetUp();

		homeFieldML = new MouseListener() { // Checks user's every click
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (selectedShip >= 1 && selectedShip <= 5 && shipPlaced == false) {
					int unit = PlayingField.homeField.getBlockLength() / Ocean.mapSize;
					int x = (int) (e.getX() / unit);
					int y = (int) (e.getY() / unit);
					if (homeField.withinBounds(x, y)) {
						if (homeField.placeShip(x, y, selectedShip, homeField.getShip(selectedShip), homeField.isHorizontal())) {
							shipPlacedNum++;
							
							if (shipPlacedNum >= 5) {
								contentPane.remove(shipsPane);
								commentator.startGame();
								game();
							}
						}	
					}
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		};
		
		homeField.addMouseListener(homeFieldML);
		
		frame.setSize(new Dimension(755, 580));
	}
	
	/*************************************************
	 * Set up ship placement buttons
	 * pre: None
	 * post: Ship placement buttons is set
	 *************************************************/
	public void shipButtonSetUp() {
		
		ButtonHandler itemHandler = new ButtonHandler();
		
		/* Ships Button */
		(carrierB = new JButton("Carrier", new ImageIcon(ships[CARRIER][1]))).addActionListener(itemHandler);
		(battleshipB = new JButton("Battleship", new ImageIcon(ships[BATTLESHIP][1]))).addActionListener(itemHandler);
		(cruiserB = new JButton("Cruiser",new ImageIcon(ships[CRUISER][1]))).addActionListener(itemHandler);
		(submarineB = new JButton("Submarine",new ImageIcon(ships[SUBMARINE][1]))).addActionListener(itemHandler);
		(destroyerB = new JButton("Destroyer",new ImageIcon(ships[DESTROYER][1]))).addActionListener(itemHandler);

		/* Rotation Button */
		JButton rotateB = new JButton("Set Vertical"); 
		rotateB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rotateB.getText().equals("Set Vertical")) {
					rotateB.setText("Set Horizontal");
					homeField.setHorizontal(false);
	        		commentator.horizontalAlignment(false);
				} else {
					rotateB.setText("Set Vertical");
					homeField.setHorizontal(true);
	        		commentator.horizontalAlignment(true);
				}
			}
		});

		/* Setup Layout */
		JPanel shipsTop = new JPanel(new GridLayout(1,2));
		shipsTop.add(carrierB);
		shipsTop.add(cruiserB);

		JPanel shipsBottom = new JPanel(new GridLayout(1,3));
		shipsBottom.add(battleshipB);
		shipsBottom.add(submarineB);
		shipsBottom.add(destroyerB);

		shipsPane = new JPanel(new GridLayout(3,1));
		shipsPane.setBorder(new TitledBorder("Select A Ship To Place on the Home Field (Default horizontal)"));

		shipsPane.add(shipsTop);
		shipsPane.add(shipsBottom);
		shipsPane.add(rotateB);
		shipsPane.setPreferredSize(new Dimension(260, 100));
		
		contentPane.add(shipsPane);
	}
	
	/*************************************************
	 * Subclass for handling ship placement buttons
	 * pre: Ship placement buttons instantiated
	 * post: Respond to ship placement button clicks
	 *************************************************/
	class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {		
			if (shipPlaced == true) { //Allow another ship placement only when the current ship is placed
				((JButton) e.getSource()).setEnabled(false);
				shipPlaced = false;
				if (e.getSource() == carrierB) {
					selectedShip = CARRIER;
					commentator.placeShipText(CARRIER);
				}
				if (e.getSource() == battleshipB) {
					selectedShip = BATTLESHIP;
					commentator.placeShipText(BATTLESHIP);
				}
				if (e.getSource() == cruiserB) {
					selectedShip = CRUISER;
					commentator.placeShipText(CRUISER);
				}
				if (e.getSource() == submarineB) {
					selectedShip = SUBMARINE;
					commentator.placeShipText(SUBMARINE);
				}

				if (e.getSource() == destroyerB) {
					selectedShip = DESTROYER;
					commentator.placeShipText(DESTROYER);
				}
			}
		}
	}
	
	/*****************************************
	 * Starts the main game
	 * pre: The user chose a difficulty
	 * post: A game starts
	 * @param difficulty
	 *****************************************/
	public static void game() {
		
		homeField.removeMouseListener(homeFieldML); //No longer respond to clicks on homeField
		
		/* Instantiate AI player */
		ai = new AI();

		/* Listening to clicks on enemyField */
		enemyFieldML = new MouseListener() { // Checks user's every click
			@Override
			public void mouseClicked(MouseEvent e) {
				int unit = PlayingField.enemyField.getBlockLength() / Ocean.mapSize;
				int x = (int) (e.getX() / unit);
				int y = (int) (e.getY() / unit);

				if (enemyField.withinBounds(x, y)) {
					if (enemyField.checkHittenBefore(x, y)) {
						commentator.hitBefore();
						System.out.println("Hitten before");
					} else { //This is a valid click
						
						/* Process Player's Action */
						int shipLabel = enemyField.checkHitShip(x, y);
						enemyField.repaint();
						
						if (shipLabel <= 5 && shipLabel >= 1) {

							commentator.hit(shipLabel);

							enemyField.getShip(shipLabel).shot();

							statusPane.updateTurns(true);
							statusPane.updateHits();
							statusPane.updateAccuracy();
							System.out.println("Hit ship");

							switch (statusPane.updateShipsNum(shipLabel)) {
							case 1:
								commentator.sunk(shipLabel);
								break;
							case 2:
								commentator.win(true);
								statusPane.updateTurns(false);

								gameOver(true);
								break;
							}
						} else {

							statusPane.updateTurns(true);
							statusPane.updateAccuracy();

							commentator.splash();

							System.out.println("Splash");
						}
						
						/* Invoke the AI to do work after each player's click*/
						
						int shipLabelNum;
						Point p;
						
						// check previous moves
						if (ai.isInHittingMode()) {
							p = ai.fireOnHits();
							shipLabelNum = homeField.checkHitShip(p.x, p.y);
						} else {	
							do {
								p = new Point((int) (Math.random()*Ocean.mapSize), 
										(int) (Math.random()*Ocean.mapSize));
								
							} while (homeField.checkHittenBefore(p.x, p.y));
							ai.addMove(p);
							
							shipLabelNum = homeField.checkHitShip(p.x, p.y);
							if (shipLabelNum >= 1 && shipLabelNum <= 5) {
								ai.setIsInHittingMode(true); //Focus on hitting this ship
							}
						}

						homeField.repaint();
						if (shipLabelNum <= 5 && shipLabelNum >= 1) {

							homeField.getShip(shipLabelNum).shot();
							
							if (homeField.getShip(shipLabelNum).checkSunken()) {
								ai.setIsInHittingMode(false);
								ai.addShipSunkByAI();
								if (ai.getShipSunkByAI() >= 5) {
									commentator.win(false);  //Game over, yet player did not win
									gameOver(false);
								}
							}
						}
					} 
					System.out.println(x + "," + y);// these co-ords are relative to the component
				} else {
					commentator.outsideBounds();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		};
		
		enemyField.addMouseListener(enemyFieldML);

		/* Put on the statusPane */
		statusPane = new StatusPanel();
		contentPane.add(statusPane);
		
		contentPane.revalidate();
	}
	
	/*****************************************
	 * Game over and instantiate the Score class 
	 * pre: User wins the game
	 * post: Score class instantiated
	 *****************************************/
	public static void gameOver(boolean playerWins) {
		enemyField.removeMouseListener(enemyFieldML);
		contentPane.remove(enemyField);
		
		frame.setSize(755, 670);
		
		score = new Score(playerWins);
		contentPane.add(score, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eventName = e.getActionCommand();

		/* Remove cover page components */
		initLabel1.setVisible(false);
		contentPane.remove(initLabel1);
		initLabel2.setVisible(false);
		contentPane.remove(initLabel2);
		initButtonEasy.setVisible(false);
		contentPane.remove(initButtonEasy);
		initButtonHard.setVisible(false);
		contentPane.remove(initButtonHard);
		contentPane.background(false); // Game starts remove background
		contentPane.repaint();

		/* Start the game */
		if (eventName.equals("easy")) {
			setUp(1);
		}

		if (eventName.equals("hard")) {
			setUp(2);
		}
	}
}


/**
 * class PlayingField implements ActionListener 
	
	CARRIER = 1, BATTLESHIP = 2, CRUISER = 3, SUBMARINE = 4, FRIGATE = 5
	electedShip = 0;
	shipPlaced = true;
	protected static Image[][] ships = new Image[6][3]

	public PlayingField 
		ships[CARRIER][1] = (new ImageIcon("Images/carrier.gif")).getImage
		ships[BATTLESHIP][1] = (new ImageIcon("Images/battleship.gif")).getImage
		ships[CRUISER][1] = (new ImageIcon("Images/cruiser.gif")).getImage
		ships[SUBMARINE][1] = (new ImageIcon("Images/submarine.gif")).getImage
		ships[FRIGATE][1] = (new ImageIcon("Images/frigate.gif")).getImage
		ships[CARRIER][2] = (new ImageIcon("Images/carrierv.gif")).getImage
		ships[BATTLESHIP][2] = (new ImageIcon("Images/battleshipv.gif")).getImage
		ships[CRUISER][2] = (new ImageIcon("Images/cruiserv.gif")).getImage
		ships[SUBMARINE][2] = (new ImageIcon("Images/submarinev.gif")).getImage
		ships[FRIGATE][2] = (new ImageIcon("Images/frigatev.gif")).getImage
		splash = (new ImageIcon("Images/splash.gif")).getImage
		fire = (new ImageIcon("Images/firex.gif")).getImage

		init
		coverPage
	

	Method init 
		frame = new JFrame("Battleship")
		frame.setMinimumSize(new Dimension(260, 455))
		
		backgroundImg = ImageIO.read(new File("Images//background.jpg"))
		contentPane = new Background(backgroundImg)

		frame.add(contentPane)
	End method init
	

	Method coverPage 
		initLabel1 = new JLabel("Battleship")
		initLabel2 = new JLabel("by Kevin Xu")

		contentPane.add(initLabel1)
		contentPane.add(initLabel2)

		initButtonEasy = new JButton("Easy Mode (10x10 Map)")
		initButtonEasy.setActionCommand("easy")
		initButtonEasy.addActionListener(this)
		contentPane.add(initButtonEasy)
		initButtonHard = new JButton("Hard Mode (15x15 Map)")
		initButtonHard.setActionCommand("hard")
		initButtonHard.addActionListener(this)
		contentPane.add(initButtonHard)
	End method coverPage
	
	setUp(difficulty) 
		shipPlacedNum = 0
		
		if (difficulty equals to 1)  
			enemyField = new EnemyField(1, "Enemy Field")
			homeField = new HomeField(1, "Home Field")
		 else if (difficulty equals to 2) 
			enemyField = new EnemyField(2, "Enemy Field")
			homeField = new HomeField(2, "Home Field")
		
		
		battlePane = new JPanel
		commentator = new Commentator
		contentPane.add(commentator)
		contentPane.add(battlePane)
		battlePane.add(enemyField)
		battlePane.add(homeField)
		
		shipButtonSetUp

		homeFieldML = new MouseListener 

			public void mouseClicked(MouseEvent e) 
				
				if (selectedShip larger than or equals to 1 and selectedShip smaller than or equals to 5 and shipPlaced equals to false) 
					unit = PlayingField.homeField.getBlockLength / Ocean.mapSize
					x =  (e.getX / unit)
					y =  (e.getY / unit)
					if (homeField.withinBounds(x, y)) 
						if (homeField.placeShip(x, y, selectedShip, homeField.getShip(selectedShip), homeField.isHorizontal)) 
							shipPlacedNum++

							if (shipPlacedNum larger than or equals to 5) 
								contentPane.remove(shipsPane)
								game
								System.out.println("Finish Setting up")
		
		homeField.addMouseListener(homeFieldML)
	End method setUp
	
	subClass ButtonHandler
		Override actionPerformed(ActionEvent e) 		
			if (shipPlaced equals to true)  
				((JButton) e.getSource()).setEnabled(false)
				shipPlaced = false
				if (e.getSource equals to carrierB) 
					selectedShip = CARRIER
					commentator.placeShipText(CARRIER)
				
				if (e.getSource equals to battleshipB) 
					selectedShip = BATTLESHIP
					commentator.placeShipText(BATTLESHIP)
				
				if (e.getSource equals to cruiserB) 
					selectedShip = CRUISER
					commentator.placeShipText(CRUISER)
				
				if (e.getSource equals to submarineB) 
					selectedShip = SUBMARINE
					commentator.placeShipText(SUBMARINE)
				

				if (e.getSource equals to destroyerB) 
					selectedShip = DESTROYER
					commentator.placeShipText(DESTROYER)
				
			End override actionPerformed
	End subClass ButtonHandler
	
	method game(difficulty) 
		homeField.removeMouseListener(homeFieldML)

		ai = new AI

		enemyFieldML = new MouseListener  

			public void mouseClicked(MouseEvent e) 
				unit = PlayingField.enemyField.getBlockLength / Ocean.mapSize
				x =  (e.getX / unit)
				y =  (e.getY / unit)

				if (enemyField.withinBounds(x, y)) 
					if (enemyField.checkHittenBefore(x, y)) 
						commentator.hitBefore
					 else  			
						shipLabel = enemyField.checkHitShip(x, y)
						enemyField.repaint
						
						if (shipLabel smaller than or equals to 5 and shipLabel larger than or equals to 1) 

							commentator.hit(shipLabel)

							enemyField.getShip(shipLabel).shot

							statusPane.updateTurns(true)
							statusPane.updateHits
							statusPane.updateAccuracy
							System.out.println("Hit ship")

							switch (statusPane.updateShipsNum(shipLabel)) 
							case 1:
								commentator.sunk(shipLabel)
								break
							case 2:
								commentator.win(true)
								statusPane.updateTurns(false)

								gameOver(true)
								break
							End switch
						else 

							statusPane.updateTurns(true)
							statusPane.updateAccuracy

							commentator.splash

							System.out.println("Splash")
						
						End else if 
						
						if (ai.isInHittingMode) 
							p = ai.fireOnHits
							shipLabelNum = homeField.checkHitShip(p.x, p.y)
							System.out.println("Target" + p.x + " " + p.y)
						else 	
							do 
								p = new Point((Math.random*Ocean.mapSize), 
										 (Math.random*Ocean.mapSize))
								
								System.out.println("Target" + p.x + " " + p.y)
								
							while (homeField.checkHittenBefore(p.x, p.y))
							
							ai.addMove(p)
							
							shipLabelNum = homeField.checkHitShip(p.x, p.y)
							if (shipLabelNum larger than or equals to 1 and shipLabelNum smaller than or equals to 5) 
								ai.setIsInHittingMode(true)
								System.out.println("Hitting Mode")
						End else if	
						

						homeField.repaint
						if (shipLabelNum smaller than or equals to 5 and shipLabelNum larger than or equals to 1) 

							homeField.getShip(shipLabelNum).shot
							
							if (homeField.getShip(shipLabelNum).checkSunken()) 
								ai.setIsInHittingMode(false)
								ai.addShipSunkByAI()
								if (ai.getShipSunkByAI() larger than or equals to 5) 
									commentator.win(false)
									gameOver(false)
								End if
							End if
						End if
				else 
					commentator.outsideBounds
				End else if			
	End method game

	method gameOver 
		enemyField.removeMouseListener(enemyFieldML)
		contentPane.remove(enemyField)
		contentPane.remove(enemyField)

		score = new Score
		contentPane.add(score)
	End method gameOver

	Override actionPerformed(ActionEvent e) 
		String eventName = e.getActionCommand
		
		contetnPane.removeAll
		contentPane.repaint

		if (eventName.equals("easy")) 
			game(1)
		End if

		if (eventName.equals("hard")) 
			game(2)
		End if
	End override actionPerformed
 */
