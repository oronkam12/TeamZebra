package Viewers;
import Model.*;
import Controller.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class GuiBoard extends JFrame {

    /**
	 *  This class represents the graphical user interface for the Snakes and Ladders game board.
	 */
	private static final long serialVersionUID = 1L;
	
	// Dimensions of the board
	private final int rows;
    private final int cols;
    
    // Lists to hold snakes, ladders, and players
    private final ArrayList<Snake> snakes;
    private final ArrayList<Ladder> ladders;
    
    // 2D array to represent the game board cells
    private final Cell[][] board;
    
    // Flag to determine if players have been created
//    private boolean botFlag; 
    
    // Game controller to manage game logic
    private final GameController gameController;
    
    private int cellWidth;
    private int cellHeight;
    
    // Lists to hold player names 
    private ArrayList<String> players;
    private ArrayList<Player> allPlayers;
    
    // For manage players Turns
    private Player currentPlayer;
    private JLabel[] playerLabels;
    private JLabel[] markTurnsLabels;
    
    // Buttons for game controls
    JButton btnPlay = new JButton();
    JButton btnPause = new JButton();
    JButton btnRestart = new JButton();
    JButton btnMute = new JButton();
    JButton btnUnmute = new JButton();
    JButton btnInfo = new JButton();
    JButton btnHomePage = new JButton();

    // Count turn's time and count game's duration
    int CTsecond = 30, CTminute = 0, Dsecond = 0, Dminute = 0;
    String CTddSecond, CTddMinute, DddSecond, DddMinute;
    DecimalFormat dFormat = new DecimalFormat("00");
    JLabel lblTime;
    JLabel lblTurnTime;
    public static Timer countDown;
    public static Timer duration;
	public long startTime;
	
	// Reference to the last player
	private Player lastPlayer;
	
	// Panel to display the game board
	public BoardPanel boardPanel;
	
	/**
     * Constructor for the GuiBoard class.
     * 
     * @param rows         Number of rows in the game board.
     * @param cols         Number of columns in the game board.
     * @param snakes       List of snakes present in the game.
     * @param ladders      List of ladders present in the game.
     * @param board        2D array representing the game board cells.
     * @param cellWidth    Width of each cell in pixels.
     * @param cellHeight   Height of each cell in pixels.
     * @param players      List of players' names.
     * @param colors       List of players' colors.
     */
	
    public GuiBoard(int rows, int cols, ArrayList<Snake> snakes,ArrayList<Ladder>ladders, Cell[][] board,int cellWidth,int cellHeight, ArrayList<String> players ,ArrayList<String> colors) {
        this.rows = rows;
        this.cols = cols;
        this.snakes = snakes;
        this.board = board;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.ladders = ladders;
        this.players = players; 
//        this.botFlag = false;
        this.allPlayers = new ArrayList<Player>(); 
        
     // Create player objects and add them to the list
        for(int i=0;i<colors.size();i++) {
        	if(colors.get(i).equals("Red")) {
        		Player p = new Player(rows-1,cols-1,players.get(i),board,Color.RED);
	            allPlayers.add(p);
        	}
        	else if(colors.get(i).equals("Green")) {
        		Player p = new Player(rows-1,cols-1,players.get(i),board,Color.GREEN);
	            allPlayers.add(p);
        	}
        	else if(colors.get(i).equals("Black")) {
        		Player p = new Player(rows-1,cols-1,players.get(i),board,Color.BLACK);
	            allPlayers.add(p);
        	}
        	else if(colors.get(i).equals("Blue")) {
        		Player p = new Player(rows-1,cols-1,players.get(i),board,Color.BLUE);
	            allPlayers.add(p);
        	}
        	else if(colors.get(i).equals("Pink")) {
        		Player p = new Player(rows-1,cols-1,players.get(i),board,Color.PINK);
	            allPlayers.add(p);
        	}
        	else if(colors.get(i).equals("Orange")) {
        		Player p = new Player(rows-1,cols-1,players.get(i),board,Color.ORANGE);
	            allPlayers.add(p);
        	}
        }
        
        // Start duration timer
        durationTimer();
        startTime = System.currentTimeMillis();
        duration.start();
        
        // Set current player to the first player in the list
        this.currentPlayer = allPlayers.get(0);
        gameController = new GameController(this);
   
        // Set up JFrame properties
        setTitle("Snakes and Ladders Board");
        setSize(1000,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        // Create panel to hold the game board
        JPanel panel = new JPanel();
        panel.setBounds(185, 135, 632, 590);
        getContentPane().add(panel);  
        // Add the BoardPanel to the panel, not directly to the content pane
        boardPanel = new BoardPanel(); 
        panel.add(boardPanel);
        new ImageIcon("Assets/background.jpg");

        // creating the display of players turns - names
        int enter = 0; 
        playerLabels = new JLabel[allPlayers.size()];
        for (int i = 0; i < allPlayers.size(); i++) {
        	String con = allPlayers.get(i).getName();
            playerLabels[i] = new JLabel(con);
            playerLabels[i].setBounds(40, 250+enter, 100, 50);
            playerLabels[i].setFont(new Font("Snap ITC", Font.BOLD, 18));
            playerLabels[i].setForeground(new Color(139, 69, 19));
            enter += 40; 
            
            playerLabels[i].setVisible(true);
            getContentPane().add(playerLabels[i]);
    }
       
        // creating the display of players turns - mark names
       enter = 0;
       ImageIcon markerIcon = new ImageIcon("Images/markerIcon2.png"); 
       // creating the markers of the turns
       markTurnsLabels = new JLabel[allPlayers.size()];
       for (int i = 0; i < allPlayers.size(); i++) {
    	   markTurnsLabels[i] = new JLabel("");
    	   markTurnsLabels[i].setBounds(15, 250+enter, 100, 50);
    	   enter += 40;
    	   markTurnsLabels[i].setIcon(markerIcon);
    	   markTurnsLabels[i].setVisible(false);
    	   getContentPane().add(markTurnsLabels[i]);
       }

       // Color the first player's turn
       coloredName();
       
       	// Set up duration timer label and icons
        BufferedImage clockIcon = null;
        try {
        	clockIcon = ImageIO.read(new File("Images/clockIcon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        JLabel lblDuration = new JLabel("");
        lblDuration.setBounds(178, 22, 70, 70);
        lblDuration.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        ImageIcon dimg = new ImageIcon("Images/hourGlass.png");
        lblDuration.setIcon(dimg);
        getContentPane().add(lblDuration);
        
        lblTime = new JLabel("00:00");
        lblTime.setForeground(new Color(255, 250, 240));
        lblTime.setBounds(164, 91, 96, 34);
        lblTime.setHorizontalAlignment(SwingConstants.CENTER);
        lblTime.setFont(new Font("Snap ITC", Font.PLAIN, 20));
        getContentPane().add(lblTime);
        
        lblTurnTime = new JLabel("00:00");
        lblTurnTime.setForeground(new Color(255, 250, 240));
        lblTurnTime.setBounds(485, 94, 96, 34);
        lblTurnTime.setHorizontalAlignment(SwingConstants.CENTER);
        lblTurnTime.setFont(new Font("Snap ITC", Font.PLAIN, 12));
        getContentPane().add(lblTurnTime);
        
        JLabel lblTimer = new JLabel("Time left:");
        lblTimer.setForeground(new Color(255, 250, 240));
        lblTimer.setBounds(405, 91, 100, 34);
        lblTimer.setFont(new Font("Snap ITC", Font.PLAIN, 16));
        getContentPane().add(lblTimer);
        
        // Set up dice roll buttons and icons
        JLabel lblRollDice2 = new JLabel("");
        lblRollDice2.setBounds(80, 550, 100, 100);
        getContentPane().add(lblRollDice2);
        
        JLabel lblDiceRoll1 = new JLabel("");
        lblDiceRoll1.setBounds(10, 550, 110, 100);
        getContentPane().add(lblDiceRoll1);
        
        JButton btnDiceRoll = new JButton("");
        btnDiceRoll.setBounds(10, 643, 140, 100);
        btnDiceRoll.setForeground(new Color(0, 0, 0));
        ImageIcon DiceRollImage = new ImageIcon("Images/btnRollDice.png"); 
        btnDiceRoll.setIcon(DiceRollImage);
        btnDiceRoll.setOpaque(false);
        btnDiceRoll.setContentAreaFilled(false);
        btnDiceRoll.setBorderPainted(false);
        btnDiceRoll.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        	        // ---------------- game rules checks ---------------------------
	     		 	btnDiceRoll.setEnabled(false);

        	        int movement = rollDice();
        	        rollingDiceDisplay(movement);
        	        if(!checkQuestion(movement,currentPlayer)) {
            	        gameController.movePlayer(currentPlayer, boardPanel,movement);
            	        lblTimer.setText("Time left: ");
            	        boardPanel.repaint();// repaint for ladders or snakes cases
        	        }      	        
        	        timerActiovation();
        	        // Delay before move or replacing turn 
        	        Timer timer = new Timer(150*(movement+1), new ActionListener() { // Adjust the delay time in milliseconds (e.g., 2000 for 2 seconds)
        	            @Override
        	            public void actionPerformed(ActionEvent e) {
        	            	coloredName();
    	                    checkObjects();
    	                    btnDiceRoll.setEnabled(true);
    	                    checkObjects();
                            boardPanel.repaint();
    	                }
        	        });
        	        timer.setRepeats(false); // Ensure the timer only fires once
//        	        if (botFlag == false) {
        	        	lastPlayer = currentPlayer;
        	            currentPlayer = nextPlayer(currentPlayer);
        	            checkObjects();
                        boardPanel.repaint();
                        gameController.checkForWin(lastPlayer);
//        	        }
        	        timer.start();
                    boardPanel.repaint();
        	 }
        	 public void checkObjects() {
        		 for(Player p:allPlayers) {
        			 gameController.isObject(p);
        			 repaint();
        		 }
        	 }
        	 
        	 private int rollDice() {
        		 if(cols==7) {
         	        return new Random().nextInt(8);
        		 }
        		 if(cols==10) {
          	        return new Random().nextInt(13);
        		 }
        		 else {
           	        return new Random().nextInt(15);
        		 }
        	 }
        	
        	 private void rollingDiceDisplay(int movement) {
        		 switch (movement) {
        			case 0:
					    lblDiceRoll1.setIcon(new ImageIcon("Images/emptyDice.png"));
					    lblDiceRoll1.setVisible(true);
					    lblRollDice2.setVisible(false);
						break;				 
					case 1:
					    lblDiceRoll1.setIcon(new ImageIcon("Images/1Dice.png"));
					    lblDiceRoll1.setVisible(true);
					    lblRollDice2.setVisible(false);
						break;
					case 2:
					    lblDiceRoll1.setIcon(new ImageIcon("Images/2Dice.png"));
					    lblDiceRoll1.setVisible(true);
					    lblRollDice2.setVisible(false);
					    break;
					case 3:
						lblDiceRoll1.setIcon(new ImageIcon("Images/3Dice.png"));
					    lblDiceRoll1.setVisible(true);
					    lblRollDice2.setVisible(false);
					    break;
					case 4:
						lblDiceRoll1.setIcon(new ImageIcon("Images/4Dice.png"));
					    lblDiceRoll1.setVisible(true);
					    lblRollDice2.setVisible(false);
					    break;
					case 5:
						if (rows != 7) {
							lblDiceRoll1.setIcon(new ImageIcon("Images/5Dice.png"));
						    lblDiceRoll1.setVisible(true);
						    lblRollDice2.setVisible(false);
						}
						break;
					case 6:
						if (rows != 7) {
							lblDiceRoll1.setIcon(new ImageIcon("Images/6Dice.png"));
						    lblDiceRoll1.setVisible(true);
						    lblRollDice2.setVisible(false);
						}
						break;
					default:
					    lblDiceRoll1.setVisible(false);
					    lblRollDice2.setVisible(false);
						break;
				}
    	 	}
    	});
        
        getContentPane().add(btnDiceRoll);
        
        // Set up home page button and icon
        btnHomePage = new JButton("");
        btnHomePage.setBounds(10, 10, 65, 65);
        ImageIcon homePageIcon = new ImageIcon("Images/btnHomePage.png");
        btnHomePage.setIcon(homePageIcon);
        btnHomePage.setOpaque(false);
        btnHomePage.setContentAreaFilled(false);
        btnHomePage.setBorderPainted(false);
        btnHomePage.setVisible(true);
        getContentPane().add(btnHomePage);
        btnHomePage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current GuiBoard window
            	gameController.pauseMusic();
            	duration.stop();
            	if (countDown != null) {
            		countDown.stop();	
            	}	
                dispose();
                // Open a new LoginScreen window
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            }
        });
        
        // Set up mute and unmute buttons and icons              
        btnMute = new JButton("");
        btnMute.setBounds(790, 30, 65, 65);
        ImageIcon muteIcon = new ImageIcon("Images/btnMute.png");
        btnMute.setIcon(muteIcon);
        btnMute.setOpaque(false);
        btnMute.setContentAreaFilled(false);
        btnMute.setBorderPainted(false);
        btnMute.setVisible(false);
        getContentPane().add(btnMute);
       
        btnUnmute = new JButton("");
        btnUnmute.setBounds(790, 30, 65, 65);
        ImageIcon unmuteIcon = new ImageIcon("Images/btnUnmute.png");
        btnUnmute.setIcon(unmuteIcon);
        btnUnmute.setOpaque(false);
        btnUnmute.setContentAreaFilled(false);
        btnUnmute.setBorderPainted(false);
        btnUnmute.setVisible(false);
        
        btnMute.setVisible(true);
        btnMute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameController.pauseMusic();
                btnUnmute.setVisible(true);
                btnMute.setVisible(false);
            }
        });
        
        // Start the music
        gameController.loadMusic("maplestoryMusic.wav");
        gameController.playMusic();

        btnUnmute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameController.playMusic();
                btnUnmute.setVisible(false);
                btnMute.setVisible(true);
            }
        });
                      
        getContentPane().add(btnUnmute);
        
        // Set up information button and icon
        btnInfo = new JButton("");
        btnInfo.setBounds(740, 30, 65, 65);
        ImageIcon infoIcon = new ImageIcon("Images/btnInformation.png");
        btnInfo.setIcon(infoIcon);
        btnInfo.setOpaque(false);
        btnInfo.setContentAreaFilled(false);
        btnInfo.setBorderPainted(false);
        btnInfo.setVisible(true);
        btnInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (rows == 13) {
					HardInstructions hardInstructions = new HardInstructions();
	                hardInstructions.setVisible(true);
				}
				else if(rows == 10) {
					MediumInstructions mediumInstructions = new MediumInstructions();
					mediumInstructions.setVisible(true);
				}
				else {
					EasyInstructions easyInstructions = new EasyInstructions();
					easyInstructions.setVisible(true);
				}
			}
		});
        getContentPane().add(btnInfo);
        
        // Set up restart button and icon
        btnRestart = new JButton("");
        btnRestart.setBounds(855, 215, 70, 70);
        ImageIcon restartIcon = new ImageIcon("Images/btnRestart.png");
        btnRestart.setIcon(restartIcon);
        btnRestart.setOpaque(false);
        btnRestart.setContentAreaFilled(false);
        btnRestart.setBorderPainted(false);
        btnRestart.setVisible(false);
        btnRestart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Dsecond = 0;
		        Dminute = 0;
		        lblTime.setText("00:00");
		        duration.restart();
		        CTsecond = 30;
		        CTminute = 0;
		        lblTurnTime.setText("00:00");
				if (countDown != null) {
					countDown.stop();	
				}
				
		        lblDiceRoll1.setVisible(false);
		        lblRollDice2.setVisible(false);
		        btnPause.setVisible(true);
		        
		        currentPlayer = allPlayers.get(0);
		        for(int i = 0; i < playerLabels.length; i++) {
    	        	if(currentPlayer.getName().equals(playerLabels[i].getText())) {
    	        		playerLabels[i].setFont(new Font("Snap ITC", Font.BOLD, 18));
    	        		markTurnsLabels[i].setVisible(true);
    	        	}
    	        	else {
						markTurnsLabels[i].setVisible(false);
					}
		        }
		        
				for(Player player : allPlayers)
				{
					player.setRow(rows-1);
					player.setCol(cols-1);
				}
				coloredName();
				btnRestart.setVisible(false);
				btnDiceRoll.setEnabled(true);
				btnPlay.setVisible(false);
				btnPause.setEnabled(true);
				boardPanel.repaint();
			}
		});
        getContentPane().add(btnRestart);
        
        // Set up play & pause buttons
        btnPlay = new JButton("");
        btnPlay.setBounds(855, 135, 70, 70);
        ImageIcon playIcon = new ImageIcon("Images/btnPlay.png");
        btnPlay.setIcon(playIcon);
        btnPlay.setOpaque(false);
        btnPlay.setContentAreaFilled(false);
        btnPlay.setBorderPainted(false);
        btnPlay.setVisible(false);
        btnPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				duration.start();
				if (countDown != null) {
					countDown.start();	
				}

				btnRestart.setVisible(false);
				btnPlay.setVisible(false);
				btnPause.setVisible(true);
			}
		});
        getContentPane().add(btnPlay);
        
        btnPause = new JButton("");
        btnPause.setBounds(855, 135, 70, 70);
        ImageIcon pauseIcon = new ImageIcon("Images/btnPause.png"); 
        btnPause.setIcon(pauseIcon);
        btnPause.setOpaque(false);
        btnPause.setContentAreaFilled(false);
        btnPause.setBorderPainted(false);
        btnPause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				duration.stop();
				if (countDown != null) {
					countDown.stop();	
				}
				btnRestart.setVisible(true);
				btnPause.setVisible(false);
			}
		});
        getContentPane().add(btnPause);
        
        JLabel bgLabel = new JLabel("");
        bgLabel.setBounds(0, 0, 1000, 800);
        bgLabel.setIcon(new ImageIcon("Assets/background.jpg"));
        getContentPane().add(bgLabel);

        btnPause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnDiceRoll.setEnabled(false);
				btnPause.setEnabled(false);
				btnPlay.setVisible(true);
			}
		});
        btnPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnDiceRoll.setEnabled(true);
				btnPause.setEnabled(true);
				btnPlay.setVisible(false);
			}
		});
    }
    
    // Color the name of the current player
	public void coloredName() {
    for (int i = 0; i < allPlayers.size(); i++) {
        Player player = allPlayers.get(i);
        if (player.equals(currentPlayer)) {
            playerLabels[i].setForeground(player.getColor()); // Set the color of the player's name
            playerLabels[i].setFont(new Font("Snap ITC", Font.BOLD, 20)); // Set the font style
            markTurnsLabels[i].setVisible(true); // Show the marker for the current player
        } else {
            playerLabels[i].setForeground(new Color(139, 69, 19)); // Set default color for other players
            playerLabels[i].setFont(new Font("Snap ITC", Font.BOLD, 18)); // Set default font style
            markTurnsLabels[i].setVisible(false); // Hide the marker for other players
        }
    }
}
    //  Checks if a question should be asked to the player based on the movement value and the number of columns on the game board.
	// If a question should be asked, the appropriate question difficulty is determined and passed to the game controller.
    public boolean checkQuestion(int movement, Player player) {
    	// For Easy game board
		if(cols == 7) {
			// If the movement is 5, roll an easy question
			if(movement == 5) {
				rolledQuestion("easy");
				player.setAskedQ(true);
				gameController.handleRolledQuestion("1", player);
				return true;
			}
			// If the movement is 6, roll a medium question
			else if(movement == 6) {
				rolledQuestion("medium");
				player.setAskedQ(true);
				gameController.handleRolledQuestion("2", player);
				return true;
			}
			// If the movement is 7, roll a hard question
			else if (movement == 7){
				rolledQuestion("hard");
				player.setAskedQ(true);
				gameController.handleRolledQuestion("3", player);
				return true;
			}
		}
		// For Medium game board
		else if(cols == 10) {
			 // If the movement is 7 or 8, roll an easy question
			if(movement == 7 || movement == 8) {
				rolledQuestion("easy");
				player.setAskedQ(true);
				gameController.handleRolledQuestion("1", player);
				return true;
			}
			// If the movement is 9 or 10, roll a medium question
			else if(movement == 9||movement == 10) {
				rolledQuestion("medium");
				player.setAskedQ(true);
				gameController.handleRolledQuestion("2", player);
				return true;
			}
			// If the movement is 11 or 12, roll a hard question
			else if(movement == 11 || movement == 12) {
				rolledQuestion("hard");
				player.setAskedQ(true);
				gameController.handleRolledQuestion("3", player);
				return true;
			}
		}
		// For Hard game board
		else if(cols==13) {
			 // If the movement is 7 or 8, roll an easy question
			if(movement == 7 || movement == 8) {
				rolledQuestion("easy");
				player.setAskedQ(true);
				gameController.handleRolledQuestion("1", player);
				return true;
			}
			// If the movement is 9 or 10, roll a medium question
			else if(movement == 9 || movement == 10) {
				rolledQuestion("medium");
				player.setAskedQ(true);
				gameController.handleRolledQuestion("2", player);
				return true;
			}
			// If the movement is greater than 10 and less than 15, roll a hard question
			else if(movement > 10 && movement < 15) {
				rolledQuestion("hard");
				player.setAskedQ(true);
				gameController.handleRolledQuestion("3", player);
				return true;
			}
		}
		
		// If none of the conditions are met, return false (no question should be asked)
		return false;
	}

    // Determines the next player in the game.
    private Player nextPlayer(Player p) {
     	if(p == null) {
     		return allPlayers.get(0);
     	}
     	else {
     		for(int i = 0; i < allPlayers.size(); i++) {
         		if(allPlayers.get(i) == p && i+1 < allPlayers.size()) {
         			return allPlayers.get(i+1);
         		}
         	}
     		return allPlayers.get(0);
     	}
     }
    
    // Class representing the game board panel
	public class BoardPanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ImageIcon crownIcon;
		private ImageIcon presentIcon;
		private ImageIcon questionMarkIcon;
        public BoardPanel() {
        	
        	// Loading assets to the board
        	try {
        		BufferedImage image = ImageIO.read(new File("Assets/crown.png"));
        	    Image resizedImage = image.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
        	    crownIcon = new ImageIcon(resizedImage);
        	}
        	catch (IOException e) {
        	    e.printStackTrace();
        	}   
        	
        	try {
        		BufferedImage image = ImageIO.read(new File("Assets/present.png"));
        	    Image resizedImage = image.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
        	    presentIcon = new ImageIcon(resizedImage);
        	}
        	catch (IOException e) {
        	    e.printStackTrace();
        	}
        	try {
        		BufferedImage image = ImageIO.read(new File("Images/qm.png"));
        	    Image resizedImage = image.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
        	    questionMarkIcon = new ImageIcon(resizedImage);
        	}
        	catch (IOException e) {
        	    e.printStackTrace();
        	}
        }
        
        // Function to paint the board with colors (repaint) -- In every change repaint the board again.
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int borderWidth = 1;

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Cell currentCell = board[i][j];
                    int x = j * cellWidth;
                    int y = i * cellHeight;
                    // ---------------- draw cell border ---------------------
                    g.setColor(Color.BLACK); // color of the border of the whole board
                    g.drawRect(x + borderWidth, y + borderWidth, cellWidth - 1 * borderWidth, cellHeight - 1 * borderWidth);
                    // ----------------- draw cell value ----------------------
                    g.setColor(Color.BLACK);
                    g.drawString(Integer.toString(currentCell.getValue()), x + 10, y + 20);
                    if (i == 0 && j == 0) {
                        // Calculate the center position for the crown image in the cell
                        int crownX = x + (cellWidth - crownIcon.getIconWidth()) / 2;
                        int crownY = y + (cellHeight - crownIcon.getIconHeight()) / 2;
                        // Draw the crown icon at the calculated position
                        crownIcon.paintIcon(this, g, crownX, crownY);
                    }
                    if(board[i][j].getSnakeOrLadder() instanceof Present) {
                    	Present temp = (Present)board[i][j].getSnakeOrLadder();
                    	if(temp.isMovement()==true) {
                        	int presentX = x + (cellWidth - presentIcon.getIconWidth()) / 2;
                            int presentY = y + (cellHeight - presentIcon.getIconHeight()) / 2;
                        	presentIcon.paintIcon(this, g, presentX, presentY);
                    	}
                    }
                    else if(board[i][j].getSnakeOrLadder() instanceof QuestionCell) {
                    	int questionX = x + (cellWidth - questionMarkIcon.getIconWidth()) / 2;
                        int questionY = y + (cellHeight - questionMarkIcon.getIconHeight()) / 2;
                        questionMarkIcon.paintIcon(this, g, questionX, questionY);
                    }
                }
            }

            for (Snake snake : snakes) {
                snake.draw((Graphics2D) g, cellWidth,cellHeight);
            }

            for (Ladder l : ladders) {
                l.draw((Graphics2D) g, cellWidth,cellHeight);
            }

            for (Player player : allPlayers) {
                g.setColor(player.getColor());
                int playerX = player.getCol() * cellWidth;
                int playerY = player.getRow() * cellHeight;
                g.fillOval(playerX, playerY, cellWidth, cellHeight);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(cols * cellWidth, rows * cellHeight);
        }
    }	
	
	// Activates the timer 
	void timerActiovation(){
    	if (countDown != null && !countDown.isRunning() ) {
    		countdownTimer();
    		countDown.start();
    	}
    	else if (countDown != null && countDown.isRunning() ){
    		countDown.stop();
    		countdownTimer();
    		countDown.start();
    		CTsecond = 30;
    	}
    	else {
    		countdownTimer();
    		countDown.start();
    		CTsecond = 30;
    	}
	}
	
	// Creates and starts the countdown timer
	public void countdownTimer() {
		countDown = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CTsecond--;
				CTddSecond = dFormat.format(CTsecond);
				CTddMinute = dFormat.format(CTminute);
				lblTurnTime.setText(CTddMinute + ":" + CTddSecond);
				
				if (CTsecond == -1) {
					CTsecond = 0;
					CTddSecond = dFormat.format(CTsecond);
					CTddMinute = dFormat.format(CTminute);
					lblTurnTime.setText(CTddMinute + ":" + CTddSecond);
					countDown.stop();
					turnEnded();
					timerActiovation();
					CTsecond = 30;
				}
			}	
		});
	}
	
	// Handles the end of a player's turn.
	public void turnEnded() {
		String message = currentPlayer.getName() + " your time ran out! \n Turn passed to " + nextPlayer(currentPlayer).getName();
		currentPlayer = nextPlayer(currentPlayer);
		coloredName();
		final JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        final JDialog dialog = pane.createDialog(null, "Turn Ended");
        // Create a Timer that will close the dialog after 1.5 seconds (1500 ms)
        Timer timer = new Timer(3000, e -> dialog.dispose());
        timer.setRepeats(false);
        // Start the timer and make the dialog visible
        timer.start();
        dialog.setVisible(true);
	}
	
	// Displays a message for rolling question
	public void rolledQuestion(String difficulty) {
		String message = "You rolled " + difficulty + " question.";
		final JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        final JDialog dialog = pane.createDialog(null, "A Wild Question Appeared!");
        // Create a Timer that will close the dialog after 1.5 seconds (1500 ms)
        Timer timer = new Timer(1200, e -> dialog.dispose());
        timer.setRepeats(false);
        // Start the timer and make the dialog visible
        timer.start();
        dialog.setVisible(true);
	}
	
	// Creates and starts the duration timer
	public void durationTimer() {
		duration = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Dsecond++;
				DddSecond = dFormat.format(Dsecond);
				DddMinute = dFormat.format(Dminute);
				lblTime.setText(DddMinute + ":" + DddSecond);
				
				if (Dsecond == 60) {
					Dsecond = 0;
					Dminute++;
					DddSecond = dFormat.format(Dsecond);
					DddMinute = dFormat.format(Dminute);
					lblTime.setText(DddMinute + ":" + DddSecond);
				}
			}	
		});
	}
	
	// Getters and setters
    public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Cell[][] getBoard() {
		return board;
	}
	
    public static void main(String[] args) {
       
    }
}
