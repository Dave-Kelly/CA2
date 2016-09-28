package CA3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.*;

/**
 * David Kelly c00193216
 * Match view/amend applicaion - menu file (file chooser) open matchResults.txt
 * user selects match, amends: date and score of each match
 * specifies output file
 * appends match information to specified output file
 * calculates match results (matches played, home wins, away wins and draws) after results are appended to file
 */

public class Results extends JFrame implements ActionListener{
	
	private MenuBar menuBar; //Menu bar item
	private Menu file; //File menu item
	//File menu items
	private MenuItem openFile; //Open option - **use to open the file and populate the listbox
	private MenuItem saveFile; //Save option
	private MenuItem close; //Close option
	private File input, output;
	//List Box
	JComboBox  matchList;
	//TextBox
	JTextField homeOut, awayOut, dateOut, score1Out, score2Out, fileOut, totalMatches, totalHomeWins, totalAwayWins, totalDraws; 
	//Panels
	JPanel p1, p2, p3;
	JLabel name1,name2, date, scoreHome, scoreAway, setOutput;
	//Buttons
	JButton edit, append, calcResults;
	private int match, homeWins, awayWins, draws;


	public Results(){
		
		this.setSize(300, 400); //Initial size of the window frame
		this.setTitle("Teams I/O"); //Set title
		WindowCloser listener = new WindowCloser();
		addWindowListener(listener);

		this.getContentPane().setLayout(new BorderLayout()); 

		//MENU BAR********************************************
		// add menu bar
		menuBar = new MenuBar();
		file = new Menu();
		openFile = new MenuItem();
		saveFile = new MenuItem();
		close = new MenuItem();
		this.setMenuBar(menuBar);
		this.menuBar.add(file); 
		file.setLabel("File");
		// OPEN option
		this.openFile.setLabel("Open"); // set  label of the menu item
		this.openFile.addActionListener(this); // add  action listener 
		this.file.add(this.openFile); // add to the "File" menu
		// SAVE option
		this.saveFile.setLabel("Save");
		this.saveFile.addActionListener(this);
		this.file.add(this.saveFile);
		// CLOSE option
		this.close.setLabel("Close");
		this.close.addActionListener(this);
		this.file.add(this.close);
		//Panels
		//P1 holds comboBox and edit button
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2,1));
		//P2 holds textFields and labels
		p2 = new JPanel();
		p2.setLayout(new GridLayout(5,2));
		//P3 holds output controls
		p3 = new JPanel();
		p3.setLayout(new GridLayout(6,1));
		
		//team listBox
		matchList = new JComboBox();
		
		homeOut = new JTextField(10);
		awayOut = new JTextField(10);
		dateOut = new JTextField(10);
		score1Out = new JTextField(10);
		score2Out = new JTextField(10);
		fileOut = new JTextField(5);
		fileOut.setText("Output File Name"); //TextField to specify output file name
		totalMatches = new JTextField(10);
		totalMatches.setText("Total Matches Played : ");
		totalHomeWins = new JTextField(10);
		totalHomeWins.setText("Total Home Wins : ");
		totalAwayWins = new JTextField(10);
		totalAwayWins.setText("Total Away Wins : ");
		totalDraws = new JTextField(10);
		totalDraws.setText("Total Draws : ");
		name1 = new JLabel("Home Team : ");
		name2 = new JLabel("Away Team : ");
		date = new JLabel("Date : ");
		scoreHome = new JLabel("Home Team Score : ");
		scoreAway = new JLabel("Away Team Score : ");
		edit = new JButton("Amend Match Details");
		append = new JButton("Save Changes");
		
		//Add comboBox/buttons to action listener
		this.matchList.addActionListener(this);
		this.edit.addActionListener(this);
		this.append.addActionListener(this);
		
		//Add components to first panel
		p1.add(matchList);
		p1.add(edit);
		
		//Add components to second panel
		p2.add(name1);
		p2.add(homeOut);
		
		p2.add(name2);
		p2.add(awayOut);
		
		p2.add(date);
		p2.add(dateOut);
		
		p2.add(scoreHome);
		p2.add(score1Out);
		
		p2.add(scoreAway);
		p2.add(score2Out);
		
		
		//Add to third panel
		p3.add(fileOut);
		p3.add(append);
		p3.add(totalMatches);
		p3.add(totalHomeWins);
		p3.add(totalAwayWins);
		p3.add(totalDraws);
		
		
		//Add panels to Frame
		this.add(p1, "North");
		this.add(p2, "Center");
		this.add(p3, "South");
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.openFile) {
			JFileChooser open = new JFileChooser(); // open file chooser
			int option = open.showOpenDialog(this); // get the option user selected
				if (option == JFileChooser.APPROVE_OPTION) {
					try {
						// create scanner instance to read chosen file
						Scanner input = new Scanner(new FileReader(open.getSelectedFile().getPath()));
						
						while (input.hasNextLine()){ //while there's something to read
							String line = input.nextLine(); //Assign line to String variable "line"
							matchList.addItem(line); //Add each line to comboBox
						}
					} 
					catch (Exception ex) { // catch any exceptions
							// write to the debug console
							System.out.println(ex.getMessage());
						}
				}
			}
		else if(e.getSource() == edit){
			String n = (String)matchList.getSelectedItem();
			StringTokenizer tokenizer = new StringTokenizer(n); //to specify delimiter: (String n, String delim) in constructor
			homeOut.setText(n);
			while(tokenizer.hasMoreTokens()){ //tokenizing the comboBox string to populate the textFields for editing
				homeOut.setText(tokenizer.nextToken());
				awayOut.setText(tokenizer.nextToken());
				dateOut.setText(tokenizer.nextToken());
				score1Out.setText(tokenizer.nextToken());
				score2Out.setText(tokenizer.nextToken());
				//tokenizer.nextToken();
					
			}
		}
		else if(e.getSource() == append){ //to write screen input to file
			try {
				output = new File(fileOut.getText());
				PrintWriter pw = new PrintWriter(new FileWriter(output, true));
				pw.println(homeOut.getText() + " " + awayOut.getText() + " " + dateOut.getText() + " "  + score1Out.getText()  + " "  + score2Out.getText() );
				homeOut.setText("");
				awayOut.setText("");
				dateOut.setText("");
				
				pw.close();
			}
			catch (IOException e1){
				System.out.println(e1.getMessage());

			}
			finally{ // results are calculated after the match details are appended to the new file
				match++; //increment the total matches - and output the total to screen
				String v = String.valueOf(match);
				totalMatches.setText("Total Matches Played : " + v);
				
				//check home wins - update total and output to screen
				if(Integer.valueOf(score1Out.getText()) > Integer.valueOf(score2Out.getText())){
					homeWins++;
					String s = String.valueOf(homeWins);
					totalHomeWins.setText("Total Home Wins : " + s);
				}
				else if(Integer.valueOf(score1Out.getText()) < Integer.valueOf(score2Out.getText())){
					awayWins++;
					String s = String.valueOf(awayWins);
					totalAwayWins.setText("Total Away Wins : " + s);
				}
				else if(Integer.valueOf(score1Out.getText()) == Integer.valueOf(score2Out.getText())){
					draws++;
					String s = String.valueOf(draws);
					totalDraws.setText("Total Draws : " + s);
				}
			}
		}
		else if(e.getSource() == calcResults){ //to calculate results
			
		}
	}
	private class WindowCloser extends WindowAdapter{
		public void windowClosing(WindowEvent event){ //window closer sub class
			System.exit(0); //close window
			
		}

	}
}



