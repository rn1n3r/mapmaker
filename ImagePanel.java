import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class ImagePanel extends JPanel
{
    public static BufferedImage image;
    
    public ImagePanel(BufferedImage i)
    {
	super();
	image = i;    
	
       
    }
    
    public void paintComponent(Graphics g)
    {
	super.paintComponent(g);   
	g.drawImage(image,20,20,getWidth(),getHeight(),null);
	
    }
}
