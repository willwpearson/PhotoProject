package pixLab.classes;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple(int startRow, int endRow, int startCol, int mirrorPoint)
  {
    //int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = startRow; row <= endRow; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = startCol; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  public void animeFilter(int startRow, int startCol)
  {
	  Pixel fromPixel = null;
	  Pixel toPixel = null;
	  Picture anime = new Picture("Transparent Eye Filter.png");
	  Pixel[][] toPixels = this.getPixels2D();// The base layer.
	  Pixel[][] fromPixels = anime.getPixels2D();// The layer we are adding to the picture.
	  int fromRow = 0;
	  for (int toRow = startRow; fromRow < fromPixels.length && toRow < toPixels.length; toRow++)
	  {
		  int fromCol = 0;
		  for(int toCol = startCol; fromCol < fromPixels[0].length && toCol < toPixels[0].length; toCol++)
		  {
			  fromPixel = fromPixels[fromRow][fromCol];
			  toPixel = toPixels[toRow][toCol];
			  if(!fromPixel.isTransparent())
			  {
				  toPixel.setColor(fromPixel.getColor());
			  }
			  fromCol++;
		  }
		  fromRow++;
	  }
	  
  }
  
  public void glitchWrap()
  {
	  Pixel[][] pixels = this.getPixels2D();
	  int shiftAmount = (int) (.40 * pixels[0].length);
	  int width = pixels[0].length;
	  
	  for (int row = 0; row < pixels.length; row++)
	  {
		  Color [] currentColors = new Color[pixels[0].length];
		  
		  for(int col = 0; col < pixels[row].length; col++)
		  {
			  currentColors[col] = pixels[row][col].getColor();
		  }
		  
		  for(int col = 0; col < pixels[0].length; col++)
		  {
			  pixels[row][col].setColor(currentColors[(col + shiftAmount) % width]);
		  }
	  }
  }
  
  public void glitchShift()
  {
	  Pixel[][] pixels = this.getPixels2D();
	  int shiftRow = (int) (.25 * pixels[0].length);
	  int shiftCol = (int) (.1 * pixels.length);
	  
	  for (int col = 0; col < pixels[0].length; col++)
	  {  
		  for(int row = 0; row < pixels.length; row ++)
		  {
			 int temp = row + shiftRow;
			 int tempCol = col + shiftCol;
			 if(temp < pixels.length)
			 {
				 if(tempCol < pixels[0].length)
				 {
					 pixels[temp][col].setBlue(pixels[row][col].getBlue());
					 pixels[temp][col].setRed(pixels[row][col].getRed());
				 }
			 }
		  }
	  }
  }
  
  public void glitchRectangle()
  {
	  Pixel[][] pixels = this.getPixels2D();
	  
	  int firstRow = pixels.length - 50;
	  int firstCol = pixels[0].length - 40;
	  int secondRow = 100;
	  int secondCol = 50;
	  for(int i = firstRow; i < pixels.length; i++)
	  {
		  for(int j = firstCol; j < pixels[0].length; j++)
		  {
			  pixels[i][j].setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
		  }
	  }
	  for(int i = secondRow; i < pixels.length - 50; i ++)
	  {
		  for(int j = secondCol; j < pixels[0].length - 500; j++)
		  {
			  pixels[i][j].setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)));
		  }
	  }
  }
  
  public void classMirror()
  {
	  Pixel[][] pixels = this.getPixels2D();
	    Pixel leftPixel = null;
	    Pixel rightPixel = null;
	    int width = (int)(pixels[0].length * .80);
	    for (int row = pixels.length / 2 - 40; row < pixels.length / 2; row++)
	    {
	      for (int col = 0; col < width / 2; col++)
	      {
	        leftPixel = pixels[row][col];
	        rightPixel = pixels[row][width - 1 - col];
	        rightPixel.setColor(leftPixel.getColor());
	      }
	    }
  }
  
  public void addMessage(String message, int xPos, int yPos, Color messageColor)
  {
	   Graphics2D graphics2d = this.createGraphics();
	   
	   graphics2d.setPaint(messageColor);
	   
	   graphics2d.setFont(new Font("Helvetica",Font.BOLD,16));

	   graphics2d.drawString(message,xPos,yPos);
	   
  }
  
  public void bobRoss()
  {
	  Picture bobRoss = new Picture("BobRoss.png");
	  Pixel[][] bobPixels = bobRoss.getPixels2D();
	  Pixel[][] pixels = this.getPixels2D();
	  Pixel currentPixel = null;
	  
	  for(int row = 0; row < pixels.length; row++)
	  {
		  for(int col = 0; col < pixels[0].length; col++)
		  {
			  currentPixel = pixels[row][col];
			  Double colorDistance = Math.sqrt((currentPixel.getRed() - 255) ^ 2 + (currentPixel.getGreen() - 165) ^ 2 + (currentPixel.getBlue() - 0) ^ 2);
			  if(colorDistance < 180.0 && row < bobPixels.length && col < bobPixels[0].length)
			  {
				  currentPixel.setColor(bobPixels[row][col].getColor());
			  }
		  }
	  }  
  }
  
  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
