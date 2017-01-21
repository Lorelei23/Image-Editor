package a8;

public class GrayPixel implements Pixel {

	private double intensity;
	private double red;
	private double green;
	private double blue;
	private double weighted_red;
	private double weighted_green;
	private double weighted_blue;

	private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' '};


	public GrayPixel(double intensity) {
		if (intensity < 0.0 || intensity > 1.0) {
			throw new IllegalArgumentException("Intensity of gray pixel is out of bounds.");
		}
		this.intensity = intensity;
	}

	@Override
	public double getRed() {
		return getIntensity();
	}

	@Override
	public double getBlue() {
		return getIntensity();
	}

	@Override
	public double getGreen() {
		return getIntensity();
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	@Override
	public char getChar() {
		return PIXEL_CHAR_MAP[(int) (getIntensity()*10.0)];
	}
	
	public Pixel blend(Pixel p, double weight){
		if(p==null){
			throw new RuntimeException("Pixel cannot be null.");
		}
		
		if (weight < 0.0 || weight > 1.0){
			throw new RuntimeException("Weight out of bounds");
		}
		weighted_red = (this.red * weight) + (p.getRed() * (1.0 - weight));
		weighted_green = (this.green * weight) + (p.getGreen() * (1.0 - weight));
		weighted_blue = (this.blue * weight) + (p.getBlue() * (1.0 - weight));
		
		ColorPixel blend = new ColorPixel(weighted_red, weighted_green, weighted_blue);
		
		return blend;}
}
