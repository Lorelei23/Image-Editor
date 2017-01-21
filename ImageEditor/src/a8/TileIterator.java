package a8;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class TileIterator implements Iterator<SubPicture>{
	
	Picture source;
	int cur_x = 0;
	int cur_y = 0;
	int tile_width;
	int tile_height;
	int max_x;
	int max_y;
	
	public TileIterator(Picture source, int tile_width, int tile_height){
	
		if(source == null) {
			throw new IllegalArgumentException("Source for tile iterator cannot be null.");
		}

		this.source = source;
		this.tile_width = tile_width;
		this.tile_height = tile_height;
		max_x = source.getWidth() / tile_width * tile_width - 1;
		max_y = source.getHeight() / tile_height * tile_height - 1;
		
	}

	public boolean hasNext(){
		return cur_y <= max_y;
	}

	public SubPicture next(){
		if(!hasNext()){
			throw new NoSuchElementException("No next subpicture in window iteration.");
		}

		Coordinate corner_a = new Coordinate(cur_x, cur_y);
		Coordinate corner_b = new Coordinate((cur_x + tile_width - 1), (cur_y + tile_height - 1));
		
		cur_x += tile_width;
		if(cur_x > max_x){
			cur_x = 0;
			cur_y += tile_height;
		}
		

		return source.extract(corner_a, corner_b);

	}
}
