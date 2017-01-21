package a8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspector extends JPanel implements MouseListener {

	private PictureView picture_view;
	private JLabel xLabel = new JLabel("X: ---");
	private JLabel yLabel = new JLabel("Y: ---");
	private JLabel redLabel = new JLabel("Red: ---");
	private JLabel greenLabel = new JLabel("Green: ---");
	private JLabel blueLabel = new JLabel("Blue: ---");
	private JLabel brightnessLabel = new JLabel("Brightness: ---");
	
	public PixelInspector(Picture picture) {
		setLayout(new BorderLayout());
		
		picture_view = new PictureView(picture.createObservable());
		picture_view.addMouseListener(this);
		add(picture_view, BorderLayout.CENTER);
		
		JPanel left_panel = new JPanel();
		left_panel.setLayout(new GridLayout(6,1));
		
		brightnessLabel.setMinimumSize(new Dimension(128, 41));
		brightnessLabel.setMaximumSize(new Dimension(128, 41));
		left_panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		
		left_panel.add(xLabel);
		left_panel.add(yLabel);
		left_panel.add(redLabel);
		left_panel.add(greenLabel);
		left_panel.add(blueLabel);
		left_panel.add(brightnessLabel);
		
		add(left_panel, BorderLayout.WEST);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		xLabel.setText("X: " + e.getX());
		yLabel.setText("Y: " + e.getY());
		Pixel pixel = picture_view.getPicture().getPixel(e.getX(), e.getY());
		
		double red = Math.round(pixel.getRed()*100.0)/100.0;
		double green = Math.round(pixel.getGreen()*100.0)/100.0;
		double blue = Math.round(pixel.getBlue()*100.0)/100.0;
		double brightness = Math.round(pixel.getIntensity()*100.0)/100.0;
		
		redLabel.setText("Red: " + red);
		greenLabel.setText("Green: " + green);
		blueLabel.setText("Blue: " + blue);
		brightnessLabel.setText("Brightness: " + brightness);
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
