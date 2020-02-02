/**************************************************************************************************
 * Name: Kevin Xu	
 * Date: December 27, 2019	
 * Title: Score
 * Description: The class that takes care of the database and displaying score board as the game ends
 **************************************************************************************************/

package kevin;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Score extends JPanel implements ActionListener {

	// ================================================================================ Declaration
	private JLabel promptLabel; //Prompt label
	private JTextField nameTextField; //Text field to intakes the user name
	private JPanel promptPanel; //Prompt panel
	private JButton confirmButton; //Confirm button
	private JTable scoreTable; //Component of the table
	private JScrollPane scorePane; //Component of the table
	private DefaultTableModel model; //Component of the table
	private static String name; //Name of the player
	
	private static File score = new File("database/Score Board.txt"); //Instance of the score board file
	private static String[] title; //Title of the items in the score board
	private static ArrayList<String[]> data = new ArrayList<String[]>(); //Stores the score board data
	// ================================================================================

	/*****************************************
	 * Constructor 
	 * pre: None
	 * post: A new Score instance created
	 *****************************************/
	public Score(boolean playerWins) {
		if (playerWins) {
		promptLabel = new JLabel("Enter your name: ");
		promptLabel.setFont(new Font("Monaco", Font.PLAIN, 12));
		confirmButton = new JButton("Confirm:");
		confirmButton.addActionListener(this);
		nameTextField = new JTextField(10);
		promptPanel = new JPanel();
		promptPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		promptPanel.setLayout(new FlowLayout());
		promptPanel.add(promptLabel);
		promptPanel.add(nameTextField);

		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(promptPanel);
		promptPanel.setAlignmentX(Score.CENTER_ALIGNMENT);
		this.add(confirmButton);
		confirmButton.setAlignmentX(Score.CENTER_ALIGNMENT);
		} else {
			setUpRestartButton();
		}
	}

	/*****************************************
	 * Read the data from Score Board.txt 
	 * pre: None
	 * post: Data read from the file
	 *****************************************/
	public static void read() {

		// ===================== Declaration
		FileReader in;
		BufferedReader readFile;
		StringTokenizer temp;
		// =====================

		try {
			in = new FileReader(score);
			readFile = new BufferedReader(in);
			String textLine;

			/* Read titles */
			temp = new StringTokenizer(readFile.readLine(), " "); // Read course names
			int tokenCounts = temp.countTokens();

			title = new String[tokenCounts];
			for (int i = 0; i < tokenCounts; i++) {
				title[i] = temp.nextToken();
			}

			/* Read turns, accuracy, Name */
			while ((textLine = readFile.readLine()) != null) {
				temp = new StringTokenizer(textLine, " ");
				String[] tempArray = new String[tokenCounts];

				for (int i = 0; i < tokenCounts; i++) {
					tempArray[i] = temp.nextToken();
				}
				data.add(tempArray);
			}
			readFile.close();
			in.close();

		} catch (FileNotFoundException e) {
			System.out.println("File does not exist or could not be found.");
			System.err.println("FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Problem reading file");
			System.err.println("IOException: " + e.getMessage());
		}
	}

	/*****************************************
	 * Write data on the score board file 
	 * pre: User finish the game
	 * post: Data written
	 *****************************************/
	public static void write() {
		// ===================== Declaration
		FileWriter out;
		BufferedWriter writeFile;
		String outputLine = "";
		int turns = PlayingField.statusPane.getTurns();
		String difficulty = PlayingField.statusPane.getDifficulty();
		// =====================

		read();
		String[] tempArray = new String[4];
		tempArray[0] = difficulty;
		tempArray[1] = Integer.toString(turns);
		tempArray[2] = Integer.toString(PlayingField.statusPane.getAccuracy());
		tempArray[3] = name;

		if (data.isEmpty()) { //Search for the correct place to put the new score
			data.add(tempArray);
		} else {
			boolean alreadyWritten = false;
			if (data.get(0)[0].equals("Easy") && difficulty.equals("Hard")) {// Storing the first hard level score in
																				// data
																				// base
				data.add(0, tempArray);
				alreadyWritten = true;
			} else {
				for (int i = 0; i < data.size(); i++) {
					if (difficulty.equals(data.get(i)[0]) && turns < Integer.parseInt(data.get(i)[1])
							|| (difficulty.equals("Hard") && data.get(i)[0].equals("Easy"))) { // Find the correct place
																								// to
																								// insert the score
						data.add(i, tempArray);
						alreadyWritten = true;
						break;
					}
				}
			}
			if (!alreadyWritten) {
				data.add(tempArray); // The score is the worst, insert at the end
			}
		}
		try {
			out = new FileWriter(score);
			writeFile = new BufferedWriter(out);

			for (int i = 0; i < title.length; i++) {
				outputLine += title[i] + " ";
			}

			writeFile.write(outputLine);

			for (int i = 0; i < data.size(); i++) {
				tempArray = data.get(i);
				outputLine = "\n";
				for (int j = 0; j < tempArray.length; j++) {
					outputLine += tempArray[j] + " ";
				}
				writeFile.write(outputLine);
			}

			writeFile.close(); // Close the buffered writer and flush the text
			out.close(); // Close all other IO
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist or could not be found.");
			System.err.println("FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Problem writing file");
			System.err.println("IOException: " + e.getMessage());
		}

	}

	/********************************
	 * Convert arratList to 2D array
	 * pre: Given arrayList
	 * post: Converted to 2d array
	 * @param data
	 * @return
	 ********************************/
	public static String[][] arrayListTo2DArray(ArrayList<String[]> data) {// Convert arrayList to 2D array to be used
																			// for creating JTable
		String[][] array2D = new String[data.size()][data.get(0).length];

		for (int i = 0; i < array2D.length; i++) {
			for (int j = 0; j < array2D[0].length; j++) {
				array2D[i][j] = data.get(i)[j];
			}
		}
		return array2D;
	}

	/********************************
	 * Display the score using a JTable
	 * pre: None
	 * post: Score displayed
	 * @return
	 ********************************/
	public void displayScores() {

		PlayingField.commentator.scoreBoard();
		promptLabel.setVisible(false);
		this.remove(promptLabel);
		promptPanel.setVisible(false);
		this.remove(promptPanel);
		confirmButton.setVisible(false);
		this.remove(confirmButton);
		nameTextField.setVisible(false);
		this.remove(nameTextField);
		this.revalidate();

		model = new DefaultTableModel(arrayListTo2DArray(data), title) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		scoreTable = new JTable(model);
		scorePane = new JScrollPane(scoreTable);
		add(scorePane);
		scorePane.setAlignmentX(Score.CENTER_ALIGNMENT);

		setUpRestartButton();
	}

	public void setUpRestartButton() {
		JButton restartButton = new JButton(new AbstractAction("Play Again?") {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayingField.frame.dispose();
				new PlayingField();
			}
		});
		add(restartButton);
		restartButton.setAlignmentX(Score.CENTER_ALIGNMENT);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		name = nameTextField.getText();
		PlayingField.contentPane.remove(PlayingField.battlePane);
		write();
		displayScores();
	}

}

/**
 * public class Score
 * 	Constructor Score 
		promptLabel = new JLabel("Enter your name: ")

		confirmButton = new JButton("Confirm:")
		confirmButton.addActionListener(this)
		nameTextField = new JTextField(10)
		promptPanel = new JPanel

		promptPanel.add(promptLabel)
		promptPanel.add(nameTextField)

		this.add(promptPanel)
		this.add(confirmButton)
	End constructor Score

	Method read

		try 
			in = new FileReader(score)
			readFile = new BufferedReader(in)

			temp = new StringTokenizer(readFile.readLine, " ")
			tokenCounts = temp.countTokens

			title = new String[tokenCounts]
			for (i = 0 i < tokenCounts step 1) 
				title[i] = temp.nextToken
			End for 

			while ((textLine = readFile.readLine) not equals null) 
				temp = new StringTokenizer(textLine, " ")
				String[] tempArray = new String[tokenCounts]

				for (i = 0 i < tokenCounts step 1) 
					tempArray[i] = temp.nextToken
				
				data.add(tempArray)
			
			readFile.close
			in.close
		End try catch	

	method write 

		String outputLine = ""
		turns = PlayingField.statusPane.getTurns
		String difficulty = PlayingField.statusPane.getDifficulty

		read
		
		String[] tempArray = new String[4]
		tempArray[0] = difficulty
		tempArray[1] = Integer.toString(turns)
		tempArray[2] = Integer.toString(PlayingField.statusPane.getAccuracy)
		tempArray[3] = name

		if (data.isEmpty) 
			data.add(tempArray)
		 else 
			alreadyWritten = false
			if (data.get(0)[0].equals("Easy") and difficulty.equals("Hard")) 
																				
				data.add(0, tempArray)
				alreadyWritten = true
			 else 
				for (i = 0 i < data.size step 1) 
					if (difficulty.equals(data.get(i)[0]) and turns smaller than Integer.parseInt(data.get(i)[1])
							or (difficulty.equals("Hard") and data.get(i)[0].equals("Easy")))  
																							
						data.add(i, tempArray)
						alreadyWritten = true
						break
					End if statement
				ENd for loop
			End else if		
				
			
			if (!alreadyWritten) 
				data.add(tempArray) 
			
		
		try 
			out = new FileWriter(score)
			writeFile = new BufferedWriter(out)

			for (i = 0 i < title.length step 1) 
				outputLine += title[i] + " "
			End for loop

			writeFile.write(outputLine)

			for (i = 0 i smaller than data.size step 1) 
				tempArray = data.get(i)
				outputLine = "\n"
				for (j = 0 j < tempArray.length step 1) 
					outputLine += tempArray[j] + " "
				End for loop
				writeFile.write(outputLine)
			End for loop

			writeFile.close
			out.close
		End try catch
	
	Method arrayListTo2DArray(data) 
																		
		String[][] array2D = new String[data.size][data.get(0).length]

		for (i = 0 i < array2D.length step 1) 
			for (j = 0 j < array2D[0].length step 1) 
				array2D[i][j] = data.get(i)[j]
			End for
		End for
		
		return array2D
	End method arrayListTo2DArray

	Method displayScores 

		PlayingField.commentator.scoreBoard

		this.remove(promptLabel)

		this.remove(promptPanel)

		this.remove(confirmButton)

		this.remove(nameTextField)

		model = new DefaultTableModel(arrayListTo2DArray(data), title) 
			
			Method isCellEditable(row, column) 
				return false
			End method
		
		scoreTable = new JTable(model)
		scorePane = new JScrollPane(scoreTable)
		add(scorePane)
		scorePane.setAlignmentX(Score.CENTER_ALIGNMENT)

		JButton restartButton = new JButton(new AbstractAction("Play Again?") 
			
			Override actionPerformed(ActionEvent e) 
				PlayingField.frame.dispose
				new PlayingField
			End override actionPerformed
		add(restartButton)
	End method displayScores
	
	Override actionPerformed(ActionEvent e) 
		name = nameTextField.getText
		write
		displayScores
	End override actionPerformed
 * End class Score
 */
