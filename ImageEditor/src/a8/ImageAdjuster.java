package a8;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageAdjuster extends JPanel implements ChangeListener{

	private PictureView picture_view;
	private Picture temp_picture;

	private JPanel blur_panel;
	private JPanel saturation_panel;
	private JPanel brightness_panel;

	private JLabel blur_label;
	private JLabel saturation_label;
	private JLabel brightness_label;

	private JSlider blur_slider;
	private JSlider brightness_slider;
	private JSlider saturation_slider;


	public ImageAdjuster(Picture picture) {
		setLayout(new BorderLayout());

		//creating a copy of source picture
		temp_picture = new PictureImpl(picture.getWidth(), picture.getHeight());
		for(int x = 0; x < picture.getWidth(); x++){
			for(int y = 0; y < picture.getHeight(); y++){
				temp_picture.setPixel(x, y, picture.getPixel(x, y));
			}
		}

		picture_view = new PictureView(picture.createObservable());
		add(picture_view, BorderLayout.CENTER);

		JPanel slider_panel = new JPanel();
		slider_panel.setLayout(new GridLayout(3,1));

		blur_panel = new JPanel();
		saturation_panel = new JPanel();
		brightness_panel = new JPanel();

		blur_label = new JLabel("Blur: ");
		saturation_label = new JLabel("Saturation: ");
		brightness_label = new JLabel("Brightness: ");

		blur_slider = new JSlider(0, 5, 0);
		saturation_slider = new JSlider(-100, 100, 0);
		brightness_slider = new JSlider(-100, 100, 0);

		blur_slider.setMajorTickSpacing(1);
		saturation_slider.setMajorTickSpacing(25);
		brightness_slider.setMajorTickSpacing(25);

		blur_slider.setPaintTicks(true);
		saturation_slider.setPaintTicks(true);
		brightness_slider.setPaintTicks(true);

		blur_slider.setPaintLabels(true);
		saturation_slider.setPaintLabels(true);
		brightness_slider.setPaintLabels(true);

		blur_panel.add(blur_label, BorderLayout.WEST);
		blur_panel.add(blur_slider, BorderLayout.EAST);
		saturation_panel.add(saturation_label, BorderLayout.WEST);
		saturation_panel.add(saturation_slider, BorderLayout.EAST);
		brightness_panel.add(brightness_label, BorderLayout.WEST);
		brightness_panel.add(brightness_slider, BorderLayout.EAST);


		slider_panel.add(blur_panel);
		slider_panel.add(saturation_panel);
		slider_panel.add(brightness_panel);

		add(slider_panel, BorderLayout.SOUTH);

		blur_slider.addChangeListener(this);
		saturation_slider.addChangeListener(this);
		brightness_slider.addChangeListener(this);

	}
	
	
	
	
	/*
	 * Helper method for blur.
	 * input: slider value
	 * output: blurred image created by iterating through each pixel 
	 * and setting it to the average of n pixels surrounding it
	 */

	public Picture blur(Picture picture, int blur_value){

		for(int x = 0; x < picture.getWidth(); x++){
			for(int y = 0; y < picture.getHeight(); y++){
				double red = 0.0;
				double green = 0.0;
				double blue = 0.0;
				int count = 0;

				for(int x2 = x - blur_value; x2 <= x + blur_value; x2++){
					for(int y2 = y - blur_value; y2 <= y + blur_value; y2++){
						try{
							Pixel p = picture.getPixel(x2, y2);
							red += p.getRed();
							green += p.getGreen();
							blue += p.getBlue();
							count++;
						
						}catch(RuntimeException e){}
					}//end y2
				}//end x2
				
				double avgRed = red / (double)count;
				double avgGreen = green / (double)count;
				double avgBlue = blue / (double)count;

				Pixel blurredPixel = new ColorPixel(avgRed, avgGreen, avgBlue);
				picture_view.getPicture().setPixel(x, y, blurredPixel);
			}//end y
		}//end x
		return picture_view.getPicture();
	}
	
	
	
	/*
	 * Helper method for saturation.
	 * input: slider value
	 * output: if greater than 0, sets each pixel to 
	 * the given formula which accounts for factor and brightness
	 * if equal to 0, each pixel stays the same.
	 * if less than 0, each pixel is set according to a formula
	 * that takes into account the greatest color value of the pixel
	 */

	public Picture saturate(Picture picture, int sat_value){
		for(int x = 0; x < picture.getWidth(); x++){
			for(int y = 0; y < picture.getHeight(); y++){

				Pixel p = picture.getPixel(x, y);
				double f = (double)sat_value;
				double b = p.getIntensity();
				double red = p.getRed();
				double green = p.getGreen();
				double blue = p.getBlue();
				double newRed = 0.0;
				double newGreen = 0.0;
				double newBlue = 0.0;

				if(f >= -100 && f <=0){
					try{
						newRed = red * (1.0 + (f / 100.0)) - (b * f / 100.0);
						newGreen = green * (1.0 + (f / 100.0)) - (b * f / 100.0);
						newBlue = blue * (1.0 + (f / 100.0)) - (b * f / 100.0);
				
					}catch(RuntimeException e){
						newRed = 0.0;
						newGreen = 0.0;
						newBlue = 0.0;
					}
				}else if(f > 0 && f <= 100){
					double a = Math.max(red, Math.max(green, blue));
					
					try{
						newRed = red * ((a + ((1.0 - a) * (f / 100.0))) / a);
						newGreen = green * ((a + ((1.0 - a) * (f / 100.0))) / a);
						newBlue = blue * ((a + ((1.0 - a) * (f / 100.0))) / a);
					
					}catch(RuntimeException e){
						newRed = 0.0;
						newGreen = 0.0;
						newBlue = 0.0;
					}
				}
				Pixel satPixel = new ColorPixel(newRed, newGreen, newBlue);
				picture_view.getPicture().setPixel(x, y, satPixel);
		
			}//end y
		}//end x
		return picture_view.getPicture();
	}


	
	
	
	/*
	 * Helper method for brightness.
	 * input: slider value
	 * output: if input is greater than 0, each pixel is made lighter
	 * using the blend method
	 * if input is equal to zero, pixels stay the same
	 * if input is less than 0, each pixel is made darker using the 
	 * blend method
	 */
	public void brightness(Picture picture, int bright_value){
		
		for(int x = 0; x < picture.getWidth(); x++){
			for(int y = 0; y < picture.getHeight(); y++){
				
				Pixel p = picture.getPixel(x, y);
				Pixel white = new ColorPixel(1.0, 1.0, 1.0);
				Pixel black = new ColorPixel(0.0, 0.0, 0.0);

				if(bright_value >= -100 && bright_value < 0){
					p = black.blend(p, (bright_value/-100.0));
					picture_view.getPicture().setPixel(x, y, p);
				
				}else if(bright_value == 0){
					picture_view.getPicture().setPixel(x, y, p);
				
				}else if(bright_value > 0 && bright_value <= 100){
					p = white.blend(p, (bright_value/100.0));
					picture_view.getPicture().setPixel(x, y, p);
				}

			}//end y
		}//end x
	}

	
	
	
	/*
	 * Helper method that chains all three previous methods.
	 * input: blur, saturation, and brightness slider values
	 * output: passes these values into the helper methods in the following order:
	 * blur -> saturation -> brightness
	 */
	public void adjust(Picture picture, int blur_val, int sat_val, int bright_val){
		Picture blur_pic = blur(picture, blur_val);
		Picture sat_pic = saturate(blur_pic, sat_val);
		brightness(sat_pic, bright_val);
	}

	
	
	@Override
	public void stateChanged(ChangeEvent e) {

		//does not run until user releases slider
		if(blur_slider.getValueIsAdjusting() == false 
				&& saturation_slider.getValueIsAdjusting() == false 
				&& brightness_slider.getValueIsAdjusting() == false){
			
			adjust(temp_picture, blur_slider.getValue(), 
					saturation_slider.getValue(), 
					brightness_slider.getValue());
		}
	}

}
