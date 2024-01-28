package Viewers;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class EasyInstructions extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EasyInstructions frame = new EasyInstructions();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EasyInstructions() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setSize(800,800);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon easyLogoImage = new ImageIcon("Images/easyLogo.png"); 
        JLabel easyLogoLabel = new JLabel(easyLogoImage);
        easyLogoLabel.setLocation(142, 39);
        easyLogoLabel.setSize(562, 128);
        easyLogoLabel.setVisible(true);
        contentPane.add(easyLogoLabel);
        
    	ImageIcon easyRulesImage = new ImageIcon("Images/easyRules.png");
		JLabel easyRulesLabel = new JLabel(easyRulesImage);
		easyRulesLabel.setLocation(33, 276);
		easyRulesLabel.setSize(211, 343);
		easyRulesLabel.setVisible(true);		
		contentPane.add(easyRulesLabel);
		
		ImageIcon easySnakes = new ImageIcon("Images/easySnakes.png");
		JLabel easySnakesLabel = new JLabel(easySnakes);
		easySnakesLabel.setLocation(146, 300);
		easySnakesLabel.setSize(407, 72);
		easySnakesLabel.setVisible(true);		
		contentPane.add(easySnakesLabel);
		
		ImageIcon easyLadders = new ImageIcon("Images/easyLadders.png");
		JLabel easyLaddersLabel = new JLabel(easyLadders);
		easyLaddersLabel.setLocation(130, 370);
		easyLaddersLabel.setSize(407, 72);
		easyLaddersLabel.setVisible(true);		
		contentPane.add(easyLaddersLabel);
		
		ImageIcon easyBoardSizeImage = new ImageIcon("Images/easyBoardSize.png");
		JLabel easyBSLabel = new JLabel(easyBoardSizeImage);
		easyBSLabel.setLocation(152, 178);
		easyBSLabel.setSize(484, 87);
		easyBSLabel.setVisible(true);		
		contentPane.add(easyBSLabel);
        
		ImageIcon backgroundImage = new ImageIcon("Images/backGround.jpg"); 
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 800, 800);
        contentPane.add(backgroundLabel);
        
      
		
	
	}

}
