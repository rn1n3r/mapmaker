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
    private BufferedImage fire, flower, border = null;


    public Map (int r, int c)
    {
	try
	{
	    fire = ImageIO.read (new File ("fire.png"));
	    flower = ImageIO.read (new File ("water.png"));
	    border = ImageIO.read (new File ("border.png"));
	}
	catch (IOException e)
	{
	}

	rows = r;
	col = c;

	map = new char [rows + 1] [col + 1];

    }


    public int rows ()
    {
	return rows;
    }


    public int col ()
    {
	return col;
    }


    public Graphics add (Graphics g, char tile, int x, int y, boolean mapAdd)
    {
	if (mapAdd)
	    map [x - 1] [y - 1] = tile;
	else
	    map [x] [y] = tile;
	BufferedImage selected = null;
	if ((int) tile == 0)
	{
	    g.drawRect (20 * x - 20, 20 * y - 20, 20, 20);
	}
	else
	{
	    if (tile == 'F')
		selected = fire;
	    else if (tile == 'f')
		selected = flower;
	    else
		selected = border;
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
	    for (int x = 1 ; x <= rows ; x++)
	    {
		for (int y = 1 ; y <= col ; y++)
		{

		    map [x - 1] [y - 1] = (char) filein.read ();

		    g = add (g, map [x - 1] [y - 1], x, y, true);
		}
	    }
	}
	catch (IOException e)
	{
	    System.out.print ("Something wrong!");
	}

	return g;

    }


    public Graphics load (char[] [] temp, Graphics g)
    {
	for (int x = 1 ; x <= rows ; x++)
	{
	    for (int y = 1 ; y <= col ; y++)
	    {

		map [x - 1] [y - 1] = temp [x - 1] [y - 1];

		g = add (g, map [x - 1] [y - 1], x, y, true);
	    }
	}
	return g;
    }


    public char[] [] search (char tile, Graphics g)
    {
	char[] [] temp = map;
	for (int x = 1 ; x <= rows ; x++)
	{
	    for (int y = 1 ; y <= col ; y++)
	    {
		if (map [x - 1] [y - 1] == tile)
		{

		    add (g, 'b', x, y, true);
		}
	    }
	}
	return temp;
    }
}
