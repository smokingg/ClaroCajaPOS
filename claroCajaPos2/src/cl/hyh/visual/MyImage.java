package cl.hyh.visual;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyImage extends JPanel {

	   private BufferedImage image;

	    public MyImage( String filename ) {
	       try {                
	          image = ImageIO.read(new File(filename));
	       } catch (IOException ex) {
	            // handle exception...
	       }
	    }

	    public MyImage( InputStream stream ) {
		       try {                
		          image = ImageIO.read(stream);
		       } catch (IOException ex) {
		            // handle exception...
		       }
		    }


	    public void paintComponent(Graphics g) {
	        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters

	    }

}
