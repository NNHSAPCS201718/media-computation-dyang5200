import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.awt.Color;
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
  
  public void keepOnlyBlue()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] rowArray : pixels)
      {
          for(Pixel pixelObj : rowArray)
          {
              pixelObj.setRed(0);
              pixelObj.setGreen(0);
          }
      }
  }
  
  public void negate()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] rowArray : pixels)
      {
          for(Pixel pixelObj : rowArray)
          {
              int red = pixelObj.getRed();
              pixelObj.setRed(255-red);
              
              int green = pixelObj.getGreen();
              pixelObj.setGreen(255-green);
              
              int blue = pixelObj.getBlue();
              pixelObj.setBlue(255-blue);
          }
      }
  }
  
  public void grayscale()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] rowArray : pixels)
      {
          for(Pixel pixelObj : rowArray)
          {
              int total = pixelObj.getRed() + pixelObj.getBlue() + 
                          pixelObj.getGreen();
              int avg = total / 3;
              
              pixelObj.setRed(avg);
              pixelObj.setBlue(avg);
              pixelObj.setGreen(avg);
          }
      }
  }
  
  /**
   * Modifies pixel colors to make fish easier to see
   */
  public void fixUnderwater()
  {
      Pixel[][] pixels = this.getPixels2D();
      Pixel pixel = null;
      for(Pixel[] rowArray : pixels)
      {
          for(Pixel pixelObj : rowArray)
          {
              if(pixelObj.getBlue() > pixelObj.getGreen())
              {
                  pixelObj.setBlue(pixelObj.getBlue() + 20);
              }
              else
              {
                  pixelObj.setBlue(pixelObj.getBlue() - 20);
              }
          }
      }
  }
  
  /**
   * Copy a region of the specified source Picture object into this
   *    Picture object at the specified location.
   *    Make sure destination Picture object is large enough to 
   *    fit copied Picture
   *    
   * @param sourcePicture       the source picture
   * @param startSourceRow      the row the source picture starts in
   * @param endSourceRow        the row the source picture ends in
   * @param startSourceCol      the col the source picture starts in
   * @param endSourceCol        the col the source picture ends in
   * @param startDestRow        the row the destination picture starts in
   * @param startDestCol        the col the destination picture starts in
   */
  public void cropAndCopy(Picture sourcePicture, int startSourceRow, 
            int endSourceRow, int startSourceCol, int endSourceCol,
            int startDestRow, int startDestCol )
  {
      Pixel[][] pixels = sourcePicture.getPixels2D();
      Pixel[][] destPixels = this.getPixels2D();
      Pixel sourcePixel = null;
      Pixel destPixel = null;
      
      
      for(int row = startSourceRow; row<= endSourceRow; row++)
      {
          for(int col = startSourceCol; col<= endSourceCol; col++)
          {
              sourcePixel = pixels[row][col];
              destPixel = destPixels[row + (startDestRow - startSourceRow)][col
                            + (startDestCol - startSourceCol)];
              destPixel.setColor(sourcePixel.getColor());
          }
      }
  }
  
  /**
   * scale this Picture object and return a new Picture object, 
   *    scaling the picture by 50% 
   *    (the width and height will each be half their original values)
   */
  public Picture scaleByHalf()
  {
      Pixel[][] pixels = this.getPixels2D();
      int height = this.getHeight();
      int width = this.getWidth();
      
      Pixel[][] destPixels = this.getPixels2D();
      Pixel sourcePixel = null;
      Pixel destPixel = null;
      
      Picture canvas = new Picture(height/2, width/2);

      for(int row=0; row<pixels.length; row++)
      {
          for(int col = 0; col<pixels[0].length; col++)
          {
              sourcePixel = pixels[row][col];
              destPixel = destPixels[row/2][col/2];
              destPixel.setColor(sourcePixel.getColor());
          }
      }
      canvas.cropAndCopy(this, 0,0,height/2,width/2, 0,0);
      return canvas;
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
  
  /**
   * Mirrors a picture around a mirror placed vertically from right to left
   */
  public void mirrorVerticalRightToLeft()
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = this.getWidth();
    for (int row = 0; row < this.getHeight(); row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = this.getPixel(col, row);
        rightPixel = this.getPixel(width - 1 - col, row);
        leftPixel.setColor(rightPixel.getColor());
      }
    } 
  }
  
  /**
   * Mirrors a picture around a mirror placed horizontally.
   * Mirror from top to bottom
   */
  public void mirrorHorizontal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    for (int row = 0; row < pixels.length / 2; row++)
    {
      for (int col = 0; col < pixels[0].length; col++)
      {
        topPixel = pixels[row][col];
        bottomPixel = pixels[pixels.length - 1 - row][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    } 
  }
  
  /**
   * Mirrors just a square part of the picture from bottom left to right
   *    around a mirror placed on the diagonal line
   *    Diagonal line is where row index = col index
   */
  public void mirrorDiagonal()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int numOfRows = pixels.length;
    int numOfCols = pixels[0].length;
    int bounds = 0;
    if(numOfRows>numOfCols)
    {
        bounds = numOfCols;
    }
    else
    {
        bounds = numOfRows;
    }
    
    for (int row = 0; row < bounds; row++)
    {
      for (int col = 0; col < bounds; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[col][row];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        count++;
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
    System.out.println("Count is: "+ count);
  }
  
  /**
   * Mirrors arms on the snowman to make a snowman with 4 arms
   */
  public void mirrorArms()
  {
    // 159<row<192
    // 105<col<293
    int mirrorPoint = 194;
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    
    for (int row = 159; row < mirrorPoint; row++)
    {
      for (int col = 105; col < 293; col++)
      {
        topPixel = pixels[row][col];      
        bottomPixel = pixels[mirrorPoint - row + mirrorPoint][col];
        bottomPixel.setColor(topPixel.getColor());
      }
    }
  }
  
  /**
   * Mirros the seagul so that there are two seagulls
   */
  public void mirrorGull()
  {
      // 234<row<320
      // 237<col<344
      int mirrorPoint = 344;
      Pixel leftPixel = null;
      Pixel rightPixel = null;
      Pixel[][] pixels = this.getPixels2D();
      
      for (int row = 234; row < 320; row++)
      {
          for (int col = 237; col < mirrorPoint; col++)
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

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture gtech = new Picture("GeorgiaTech.jpg");
    Picture gtech2 = new Picture("GeorgiaTech.jpg");
    Picture gtech3 = new Picture("GeorgiaTech.jpg");
    Picture gtech4 = new Picture("GeorgiaTech.jpg");
    Picture bee = new Picture("YellowJacket.jpg");
    
    this.cropAndCopy(gtech,16,255,0,255,0,0);   //original image
    
    gtech2.grayscale();
    gtech2.sepia();
    this.cropAndCopy(gtech2,16,255,0,255,0,255);
    
    gtech3.negate();
    this.cropAndCopy(gtech3,16,255,0,255,240,0);
    
    gtech4.edgeDetection(25);
    gtech4.mirrorVerticalRightToLeft();
    this.cropAndCopy(gtech4,16,255,0,255,240,255);
    
    this.write("collage.jpg");
  }
  
  
  /**
   * Applies the sepia filter onto an image
   */
  public void sepia()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] rowArray : pixels)
      {
          for(Pixel pixelObj : rowArray)
          {
              if(pixelObj.getRed() < 60)
              {
                  pixelObj.setRed( (int) (pixelObj.getRed() * .9));
                  pixelObj.setBlue( (int) (pixelObj.getBlue() * .9));
                  pixelObj.setGreen( (int) (pixelObj.getGreen() * .9));
              }
              else if(pixelObj.getRed() < 190)
              {
                  pixelObj.setBlue( (int) (pixelObj.getBlue() * .8));
              }
              else
              {
                  pixelObj.setBlue( (int) (pixelObj.getBlue() *.9));
              }
          }
      }
  }
  
  public void moreRed()
  {
      Pixel[][] pixels = this.getPixels2D();
      for(Pixel[] rowArray : pixels)
      {
          for(Pixel pixelObj : rowArray)
          {
              if(pixelObj.getBlue() < 100)
              {
                  pixelObj.setRed( (int) (pixelObj.getRed() * 1.1));
                  pixelObj.setBlue( (int) (pixelObj.getBlue() * .9));
                  pixelObj.setGreen( (int) (pixelObj.getGreen() * 1.05));
              }
              else if(pixelObj.getBlue() < 200)
              {
                  pixelObj.setBlue( (int) (pixelObj.getBlue() * .8));
                  pixelObj.setGreen( (int) (pixelObj.getGreen() * .9));
              }
              else
              {
                  pixelObj.setRed( (int) (pixelObj.getRed() * 1.1));
              }
          }
      }
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
        double avgLeft = leftPixel.getAverage();
        double avgRight = rightPixel.getAverage();
        
        double distance = Math.abs(avgLeft - avgRight);
        
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.WHITE);
        else
          leftPixel.setColor(Color.BLACK);
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
