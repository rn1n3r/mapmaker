import java.awt.*;
import javax.swing.*;
import java.awt.event.*;  // Needed for ActionListener
import java.io.*;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class Map
{
    private char[] [] map;
    private int rows, col;
    private BufferedImage fire, flower = null;


    public Map (int r, int c)
    {
	try
	{
	    fire = ImageIO.read (new File ("fire.png"));
	    flower = ImageIO.read (new File ("water.png"));
	}
	catch (IOException e)
	{
	}

	rows = r;
	col = c;

	map = new char [rows] [col];

    }


    public int rows ()
    {
	return rows;
    }


    public int col ()
    {
	return col;
    }


    public Graphics add (Graphics g, char tile, int x, int y)
    {
	map [x - 1] [y - 1] = tile;
	BufferedImage selected = null;
	if ((int)tile == 0)
	{
	    g.drawRect(20 * x - 20, 20 * y - 20, 20, 20);
	}
	else
	{
	    if (tile == 'F')
		selected = fire;
	    else if (tile == 'f')
		selected = flower;
	    g.drawImage (selected, 20 * x - 20, 20 * y - 20, 20, 20, null);
	}

	return g;
    }


    public void save ()
    {
	BufferedWriter out = null;
	try
	{
	    out = new BufferedWriter (new FileWriter (new File ("save.txt")));
	    for (int x = 0 ; x < rows ; x++)
	    {
		for (int y = 0 ; y < col ; y++)
		{
		    
		    out.write (map [x] [y]);
		}
	    }

	}
	catch (Exception e)
	{

	}
	finally
	{
	    try
	    {
		out.close ();
	    }
	    catch (Exception e)
	    {
	    }
	}
    }


    public Graphics load (String fname, Graphics g)
    {
	InputStream in = getClass ().getResourceAsStream (fname);
	BufferedReader filein = new BufferedReader (new InputStreamReader (in));
	try
	{
	    for (int x = 0 ; x < rows ; x++)
	    {
		for (int y = 0 ; y < col ; y++)
		{
		    
		    map [x] [y] = (char) filein.read ();
	      
		    g = add (g, map [x] [y], x + 1, y + 1);
		}
	    }
	}
	catch (IOException e)
	{
	    System.out.print ("Something wrong!");
	}

	return g;

    }
}
