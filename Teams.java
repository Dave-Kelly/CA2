package CA3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.*;

/**
 * David Kelly c00193216
 * Team entry applicaion - menu file (file chooser) open CSLteams.txt
 * user selects team, enters: date and score of each match
 * specifies output file
 * appends match information to specified output file
 */

public class Teams extends JFrame implements ActionListener{
	
	private MenuBar menuBar; //Menu bar item
	private Menu file; //File menu item
	//File menu items
	private MenuItem openFile; //Open option - **use to open the file and populate the listbox
	private MenuItem saveFile; //Save option
	private MenuItem close; //Close option
	private File input, output;
	//List Box
	JComboBox  homeTeamList, awayTeamList, homeScore, awayScore;
	//TextBox
	JTextField homeOut, awayOut, dateOut, fileOut; 
	//Panels
	JPanel p1, p2, p3;
	JLabel name1,name2, date, setOutput;
	//Buttons
	JButton edit, append;


	public Teams(){
		
		this.setSize(300, 300); //Initial size of the window frame
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
		p1.setLayout(new GridLayout(3,1));
		//P2 holds textFields and labels
		p2 = new JPanel();
		p2.setLayout(new GridLayout(3,2));
		//P3 holds output controls
		p3 = new JPanel();
		p3.setLayout(new GridLayout(4,1));
		
		//team listBox
		homeTeamList = new JComboBox();
		awayTeamList = new JComboBox();
		//score ListBox
		String[] score1= {"1","2","3","4","5","6","7","8","9","10"}; //String array - score
		homeScore = new JComboBox(score1);
		homeScore.setSelectedIndex(0); //Set listbox to first element
		
		String[] score2= {"1","2","3","4","5","6","7","8","9","10"}; //String array - score
		awayScore = new JComboBox(score1);
		awayScore.setSelectedIndex(0); //Set listbox to first element
		
		homeOut = new JTextField(10);
		awayOut = new JTextField(10);
		dateOut = new JTextField(10);
		fileOut = new JTextField(5);
		fileOut.setText("Output File Name"); //TextField to specify output file name
		name1 = new JLabel("Home Team : ");
		name2 = new JLabel("Away Team : ");
		date = new JLabel("Date : ");
		edit = new JButton("Add Match Details");
		append = new JButton("Add to File");
		
		//Add comboBox/buttons to action listener
		this.homeTeamList.addActionListener(this);
		this.awayTeamList.addActionListener(this);
		this.homeScore.addActionListener(this);
		this.awayScore.addActionListener(this);
		this.edit.addActionListener(this);
		this.append.addActionListener(this);
		
		//Add components to first panel
		p1.add(homeTeamList);
		p1.add(awayTeamList);
		p1.add(edit);
		
		//Add components to second panel
		p2.add(name1);
		p2.add(homeOut);
		
		p2.add(name2);
		p2.add(awayOut);
		
		p2.add(date);
		p2.add(dateOut);
		
		
		//Add to third panel
		p3.add(homeScore);
		p3.add(awayScore);
		p3.add(fileOut);
		p3.add(append);
		
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
							homeTeamList.addItem(line); //Add each line to comboBox
							awayTeamList.addItem(line); //Add each line to comboBox
						}
					} 
					catch (Exception ex) { // catch any exceptions
							// write to the debug console
							System.out.println(ex.getMessage());
						}
				}
			}
		else if(e.getSource() == edit){
			if(homeTeamList.getSelectedItem() == awayTeamList.getSelectedItem()){
				JOptionPane.showMessageDialog(null, "Selected Teams match, choose a different team");
			}
			else{
				String n = (String)homeTeamList.getSelectedItem();
				String m = (String)awayTeamList.getSelectedItem();
				StringTokenizer tokenizer = new StringTokenizer(n); //to specify delimiter: (String n, String delim) in constructor
				homeOut.setText(n);
				awayOut.setText(m);
				/*while(tokenizer.hasMoreTokens()){
					homeOut.setText(tokenizer.nextToken());
					awayOut.setText(tokenizer.nextToken());
					tokenizer.nextToken();
					
				}*/	
			}
			
		}
		else if(e.getSource() == append){ //to write screen input to file - specify file name !!!(matchResults.txt)!!!
			try {
				output = new File(fileOut.getText());
				PrintWriter pw = new PrintWriter(new FileWriter(output, true));
				String p = (String)homeScore.getSelectedItem();
				String q = (String)awayScore.getSelectedItem();
				pw.println(homeOut.getText() + " " + awayOut.getText() + " " + dateOut.getText() + " "  + p  + " "  + q );
				homeOut.setText("");
				awayOut.setText("");
				dateOut.setText("");
				
				pw.close();
			}
			catch (IOException e1){
				System.out.println(e1.getMessage());

			}
		}
	}
	private class WindowCloser extends WindowAdapter{
		public void windowClosing(WindowEvent event){ //window closer sub class
			System.exit(0); //close window
			
		}

	}
}



