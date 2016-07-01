package de.dittich.sv.gui.panel.bilder;

import gui.ImageScaler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DragPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Rectangle rect;
    private Image image;
    private int image_left=0;
    private int image_top=0;
    
    public DragPanel() {
        super(null);
        MouseHandler handler = new MouseHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);
        setOpaque(false);
    }
    
    public void setImage1( String path ) {
        image = new ImageIcon(path).getImage();
        repaint();
    }

    public void setImage( BufferedImage image ) {
    	int width = this.getWidth();//350
    	int height = this.getHeight();//450
    	BufferedImage imageScale = new ImageScaler().scaleImage(image, new Dimension(width,height));
        this.image=imageScale;
        repaint();
    }

    public void paintComponent(Graphics g2) {
    	Graphics2D g = (Graphics2D)g2;
    	Stroke oldStroke = g.getStroke();
        super.paintComponent(g);
        g.setColor( Color.white );
        Dimension d = getSize();
        g.fillRect( 0, 0, d.width, d.height );
        if ( image != null ) {
            image_left = (d.width-image.getWidth(null))/2;
            image_top = (d.height-image.getHeight(null))/2;
            g.drawImage(image,image_left,image_top,null);
        }
        if ((rect!=null) && (rect.width>1) && (rect.height>1)) {
            g.setColor(new Color(0xA0000052));
            g.setStroke(new BasicStroke(2));
            g.drawRect(rect.x,rect.y,rect.width,rect.height);
            g.setStroke(oldStroke);
        }
    }
    
    private class MouseHandler implements MouseListener, MouseMotionListener {
        private boolean dragRectangle = false;
        private Point lastPoint;
        
        public void mousePressed(MouseEvent e) {
            // there is already a rectangle
            if(rect != null) {
                // we want to drag this one
                if(rect.contains(e.getPoint())) {
                    dragRectangle = true;
                    return;
                // we're not going to drag it
                } else {
                    dragRectangle = false;
                    rect = null;
                }
            }
            // by now there won't be a rectangle
            lastPoint = e.getPoint();
            rect = new Rectangle(lastPoint.x, lastPoint.y, 1, 1);
            
            repaint();
        }
        
        public void mouseDragged(MouseEvent e) {
            Point currentPoint = e.getPoint();
            // dragging?
            if(dragRectangle) {
                rect.x += currentPoint.x - lastPoint.x;
                rect.y += currentPoint.y - lastPoint.y;
                //rect.y = rect.x*9/7;
            } else {
                // Ok, we're changing its size -  Ratio: 7/9
                rect.width = currentPoint.x - rect.x;
                //rect.height = currentPoint.y - rect.y;
                rect.height = rect.width*9/7;
                
                // the above code might introduce troubles
                if(rect.width < 0) {
                    rect.x = rect.x + rect.width;
                    rect.width = -rect.width;
                }
                if(rect.height < 0) {
                    rect.y = rect.y + rect.height;
                    rect.height = -rect.height;
                }
            }
            // store point
            lastPoint = currentPoint;
            repaint();
        }

        public void mouseClicked(MouseEvent arg0) {}
        public void mouseReleased(MouseEvent arg0) {
        	DragPanel dp = (DragPanel)arg0.getSource();
        	BufferedImage img = dp.chop();
        	BufferedImage imgCut = new ImageScaler().scaleImage(img, new Dimension(210,270));
        	ImageIcon chopIcon = new ImageIcon(new ImageScaler().scaleImage(img, new Dimension(105,135)));
        	PnlChopImage.getInstance().setChopIcon(chopIcon);
        	PnlChopImage.getInstance().getLblChopImage().setIcon(chopIcon);
        }
        public void mouseEntered(MouseEvent arg0) {}
        public void mouseExited(MouseEvent arg0) {}
        public void mouseMoved(MouseEvent arg0) {}
    }

    public BufferedImage chop() {
        // Wenn der Rahmen auserhalb des Bildes beginnt abschneiden
        if (rect.x<image_left) { rect.width-=image_left-rect.x; rect.x=image_left; }
        if (rect.y<image_top) { rect.height-=image_top-rect.y; rect.y=image_top; }
        if (rect.width+rect.x>image_left+image.getWidth(null)) rect.width-=rect.width+rect.x-(image_left+image.getWidth(null));
        if (rect.height+rect.y>image_top+image.getHeight(null)) rect.height-=rect.height+rect.y-(image_top+image.getHeight(null));
        
        // Kopie des ausgewählten Bereis erzeugen und als Image festlegen
        //ImageFilter cropFilter = new CropImageFilter( rect.x-image_left, rect.y-image_top, rect.width, rect.height );
        ImageFilter cropFilter = new CropImageFilter( rect.x-image_left, rect.y-image_top, rect.width, rect.width*9/7 );
        Image imgChop = createImage( new FilteredImageSource(image.getSource(),cropFilter));
        //setImage(createImage( new FilteredImageSource(image.getSource(),cropFilter)));

        // Auswahl löschen
        rect=null;
        
        return toBufferedImage(imgChop);
    }
    
    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}