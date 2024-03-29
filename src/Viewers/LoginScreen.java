package Viewers;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

import Model.CustomButton;

public class LoginScreen extends JFrame {
      private ImageIcon cubeImage ;
      private ImageIcon gameIconImage;
      private ImageIcon screenImage;
      private ImageIcon logoImage;
      /**
       * Create the frame.
       */
      public LoginScreen() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
            JPanel contentPane = new JPanel();
            contentPane.setBackground(SystemColor.activeCaption);
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            contentPane.setLayout(null);
        setLocationRelativeTo(null);
            
            cubeImage  = new ImageIcon("Assets/cube_test.png");
            JLabel cubeLabel = new JLabel(cubeImage);
            cubeLabel.setBounds(0,550,200,200);
            cubeLabel.setVisible(true);
            contentPane.add(cubeLabel);   
            
            logoImage  = new ImageIcon("Assets/cooltext451386517641332.png");
            JLabel logoLabel = new JLabel(logoImage);
            logoLabel.setBounds(100,150,560,125);
            logoLabel.setVisible(true);
            contentPane.add(logoLabel);          
            
            gameIconImage  = new ImageIcon("Assets/ladder2.png");
            JLabel gameIconLabel = new JLabel(gameIconImage);
            gameIconLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
            gameIconLabel.setForeground(new Color(255, 255, 255));
            gameIconLabel.setBounds(500,450,250,250);
            gameIconLabel.setVisible(true);
            contentPane.add(gameIconLabel);
            
            // Start button for starting a game
            CustomButton startBtn = new CustomButton("START", 295, 338, 200, 50, e ->{
                  LoginScreen.this.setVisible(false);
                  GameLobby gameLobby = new GameLobby();
                  
                  gameLobby.setVisible(true);
                                    
                  // opening back the last page
                  gameLobby.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                  gameLobby.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // This code is executed when InstructionsPage is closed
                        LoginScreen.this.setVisible(true);
                    }
                });          
            });
            startBtn.setFont(new Font("Stencil", Font.BOLD, 18));
            contentPane.add(startBtn);
            
            // Instructions button for reading game's instructions
            CustomButton instructionsBtn = new CustomButton("instructions",295, 405, 200, 60,e->{
                  LoginScreen.this.setVisible(false);
                  InstructionsPage ip = new InstructionsPage();
                  ip.setVisible(true);
                  // opening back the last page
                  ip.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                  ip.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        // This code is executed when InstructionsPage is closed
                        LoginScreen.this.setVisible(true);
                    }
                });
            });
            instructionsBtn.setFont(new Font("Stencil", Font.BOLD, 18));
            contentPane.add(instructionsBtn);
            
            // Questions button for quiz master page 
            CustomButton questionsBtn = new CustomButton("Questions",295,470,200,60,e->{
                  System.out.println("b");
            });
            questionsBtn.setFont(new Font("Stencil", Font.BOLD, 18));
            questionsBtn.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                        QuizMaster qm = new QuizMaster();
                        qm.setVisible(true);
                        setVisible(false);
                  }
            });
            contentPane.add(questionsBtn);
            
            // History button for watch previous games
            CustomButton historyBtn = new CustomButton("HISTORY",295,540,200,60,e->{
                	setVisible(false);
                	new History().setVisible(true);
            });
            historyBtn.setFont(new Font("Stencil", Font.BOLD, 18));
            contentPane.add(historyBtn);
            
            // Exit button for quit the game 
            CustomButton exitBtn = new CustomButton("EXIT",295,610,200,60,e->{
                  SwingUtilities.getWindowAncestor((JButton)e.getSource()).dispose();
            });
            exitBtn.setFont(new Font("Stencil", Font.BOLD, 18));
            contentPane.add(exitBtn);

            screenImage  = new ImageIcon("Assets/wS62pVGA.jpg");
            JLabel screenLabel = new JLabel(screenImage);
            screenLabel.setBounds(0,0,800,800);
            screenLabel.setVisible(true);
            contentPane.add(screenLabel);

      }

      public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                  public void run() {
                        try {
                              LoginScreen loginScreen = new LoginScreen();
                              loginScreen.setVisible(true);
                        } catch (Exception e) {
                              e.printStackTrace();
                        }
                  }
            });
      }
}