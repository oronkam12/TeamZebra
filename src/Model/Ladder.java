package Model;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Viewers.ObjectInterface;

public class Ladder extends Object implements ObjectInterface{

	public Ladder(Cell headCell, Cell buttomCell) {
		super(headCell, buttomCell);
		try {
	        // Use relative path from the project root directory
	        BufferedImage image = ImageIO.read(new File("Assets/ladder.png"));
	        this.image = image;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void MovePlayer(Player player) {
		player.setRow(this.getHeadCell().getRow());
		player.setCol(this.getHeadCell().getCol());
		
	}
	
	//Defining the locations of the ladder on the board axis
	public ArrayList<Integer> init(Graphics2D g, int cellWidth,int cellHeight) {
		ArrayList<Integer> list = new ArrayList<>();
		Cell headCell = this.getHeadCell();

	    //Set dimensions in pixels - start
	    int startX = (headCell.getCol() + 1) * cellWidth;
	    int startY = (headCell.getRow() + 1) * cellHeight;

	    int endX = (this.getButtomCell().getCol() + 1) * cellWidth;
	    int endY = (this.getButtomCell().getRow() + 1) * cellHeight;
	    //Set dimensions in pixels - end
	    list.add(startX);
	    list.add(startY);
	    list.add(endX);
	    list.add(endY);
	    return list;
	}
	
	//Rotation of image to fit board
	public ArrayList<Double> reshape(ArrayList<Integer> list) {
		//Calculate angle to rotate image
	    double angleInRadians = Math.atan2(list.get(3) - list.get(1), list.get(2) - list.get(0)) + (105* Math.PI / 180.0) - (10* Math.PI / 180.0) ;

	    //Calculate the middle of the image
	    int midX = (list.get(0) + list.get(2)) / 2;
	    int midY = (list.get(1) +list.get(3)) / 2;
	    ArrayList<Double> reshaped = new ArrayList<Double>();
	    reshaped.add(angleInRadians);
	    reshaped.add((double) midX);
	    reshaped.add((double) midY);
	    return reshaped;
		
	}
	
	
	public void plot(ArrayList<Double> list,Graphics2D g, int cellWidth,int cellHeight) {
		//Loading and scaling the image to fit board (Some of the calculations are for design implementations)
	    BufferedImage snakeImage = this.getImage();
	    Image resizedSnakeImage = snakeImage.getScaledInstance(cellWidth/(Math.abs(headCell.getCol()-buttomCell.getCol())+1) * (Math.abs(headCell.getCol() - this.getButtomCell().getCol()) + 2),  cellHeight*2 * (Math.abs(headCell.getRow() - this.getButtomCell().getRow())), Image.SCALE_SMOOTH);

	    //Calculating middle location of scaled image
	    int drawX = (int) (list.get(1) - resizedSnakeImage.getWidth(null) / 2);
	    int drawY = (int) (list.get(2) - resizedSnakeImage.getHeight(null) / 2);

	    //Transforming the scaled image in rotation and scales
	    AffineTransform transform = new AffineTransform();
	    transform.translate(drawX - cellWidth / 2, drawY - cellHeight / 2);
	    transform.rotate(list.get(0), resizedSnakeImage.getWidth(null) / 2.0, resizedSnakeImage.getHeight(null) / 2.0);

	    //Drawing in the GUI
	    g.drawImage(resizedSnakeImage, transform, null);  // Fix here
	}
	
	


	@Override
	public String toString() {
		return "Ladder [headCell=" + headCell + ", buttomCell=" + buttomCell ;
	}
	
	

}
