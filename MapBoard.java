import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import java.io.*;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.*;
import java.awt.image.BufferedImage;


public class MapBoard extends JFrame
{
    //Initialize global variables
    private Map map;
    private BufferedImage bi;
    private Graphics g;
    private JPanel panel, menu;

    private JComboBox tileSelect;
    
    private JMenuBar bar = new JMenuBar();
    private JMenu menuUp = new JMenu("File");
    private JMenuItem saveFile, savePic, loadFile;
    public MapBoard (Map map)
    {

	this.map = map;
	int r = map.rows ();
	int c = map.col ();
	bi = new BufferedImage (20 * r, 20 * c, BufferedImage.TYPE_INT_RGB); // BufferedImage that will be the visual map
	
	// Set as all white
	g = bi.getGraphics ();
	g.setColor (Color.white);
	g.fillRect (0, 0, 600, 600);
	
	//Paint onto JPanel
	panel = new ImagePanel(bi);
	panel.setPreferredSize (new Dimension (620, 620));

	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	
	//Add listeners for painting
	panel.addMouseListener (new GridListener ());
	panel.addMouseMotionListener (new GridListener ());
	       
	
	//Panel for map-making options!
	menu = new JPanel ();
	menu.setPreferredSize (new Dimension (200, 600));
	
	
	String[] tiles = {"Fire", "Flower"};
	tileSelect = new JComboBox (tiles);
	
	//Adding more listeners
	menu.add (tileSelect);
	

	
	//JMenuBar Stuff
	saveFile = new JMenuItem("Save map");
	savePic = new JMenuItem("Save as picture");
	loadFile = new JMenuItem("Load file");
	
	saveFile.addActionListener(new MenuSelect());
	savePic.addActionListener(new MenuSelect());
	loadFile.addActionListener(new MenuSelect());
	
	menuUp.add(saveFile);
	menuUp.add(savePic);
	menuUp.add(loadFile);
	bar.add(menuUp);
	this.setJMenuBar(bar);
	
	JSplitPane split = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, panel, menu);
	split.setDividerLocation (620 + split.getInsets ().left);
	this.getContentPane ().add (split);
       
	
	pack ();
	repaint ();
    }


    public static void main (String[] args)
    {
	MapBoard board = new MapBoard (new Map (30, 30));
	board.setVisible (true);
    }


    class MenuSelect implements ActionListener
    {
	public void actionPerformed (ActionEvent e)
	{
	    if (e.getSource ().equals (savePic))
	    {
		try
		{
		    File output = new File ("map.png");
		    ImageIO.write (bi, "png", output);
		}
		catch (IOException f)
		{
		    System.out.print ("Something went wrong");
		}

	    }
	    else if (e.getSource().equals(saveFile))
	    {
		map.save ();
	    }
	    
	    else 
	    {
		new Map(30,30);
		map.load("save.txt",g);
		repaint();
	    }
	}
    }


    class GridListener implements MouseMotionListener, MouseListener // both needed for drag and press
    {

	private char getCharTile () // method to determine appropriate character to add to array
	{
	    if (((String) tileSelect.getSelectedItem ()).equals ("Fire"))
		return 'F';
	    else if (((String) tileSelect.getSelectedItem ()).equals ("Flower"))
		return 'f';
	    else
		return 'S';
	}
	
	private void add (MouseEvent e)
	{
      
	    if (e.getX ()/20 > 0 && e.getY ()/20 > 0 && e.getX () / 20 <= map.rows () && e.getY () / 20 <= map.col ())
		g = map.add (g, getCharTile (), e.getX () / 20, e.getY () / 20);

	    repaint ();

	}
	public void mouseDragged (MouseEvent e)
	{
	    add (e);

	}

	public void mousePressed (MouseEvent e)
	{
	    add (e);
	}

	public void mouseClicked (MouseEvent e)
	{

	}

	public void mouseEntered (MouseEvent e)
	{
	    
	}

	public void mouseExited (MouseEvent e)
	{
	}

	public void mouseReleased (MouseEvent e)
	{
	}

	public void mouseMoved (MouseEvent e)
	{
	    
	}



    }


    class ImagePanel extends JPanel
    {
	public BufferedImage image;

	public ImagePanel (BufferedImage i)
	{
	    super ();
	    image = i;


	}

	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);
	    Graphics2D g2d = (Graphics2D)g; // Using Grpahics2D because using GRaphics seems to completely mess up the size of the tiles
	    
	    g2d.drawImage(image, 20,20,null);
	    
	    
	   
	    // Add numbers along the border
	    
	    for (int i = 0; i < map.rows(); i+=5)
	    {
		g.drawString (i +"", (i+1)*20, 15);
	    }
	    for (int i = 0; i < map.col(); i+= 5)
	    {
		g.drawString (i +"", 2,(i+1)*20+10);
	    }
	    

	}
    }
}

