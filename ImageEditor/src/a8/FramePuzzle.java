package a8;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class FramePuzzle extends JPanel implements MouseListener, KeyListener{

	private PictureView picture_view;
	private JPanel main_panel;
	private Picture blank;
	private PictureView temp;
	private PictureView blank_view;
	private PictureView[] picture_list;
	int blank_index = 24;
	int clicked_index;

	public FramePuzzle(Picture picture){
		setLayout(new BorderLayout());

		picture_view = new PictureView(picture.createObservable());
		blank = new PictureImpl(picture_view.getPicture().getWidth()/5, 
				picture_view.getPicture().getHeight()/5);
		
		//setting color for blank tile
		for(int x = 0; x < blank.getWidth(); x++){
			for(int y = 0; y < blank.getHeight(); y++){
				blank.setPixel(x, y, new GrayPixel(1));
			}
		}
		
		blank_view = new PictureView(blank.createObservable());

		picture_list = new PictureView[25];

		main_panel = new JPanel();
		main_panel.setLayout(new GridLayout(5,5));

		TileIterator tile_iterator = new TileIterator(picture_view.getPicture(), 
				picture_view.getPicture().getWidth()/5, 
				picture_view.getPicture().getHeight()/5);

		//adding iterated pictures to main panel, adding listeners to each
		for(int i = 0; i < 24; i++){
			picture_list[i] = (new PictureView(tile_iterator.next().createObservable()));
			picture_list[i].addMouseListener(this);
			picture_list[i].addKeyListener(this);
			main_panel.add(picture_list[i]);
		}

		//adding final blank tile to grid
		picture_list[24]=(blank_view);
		picture_list[24].addMouseListener(this);
		picture_list[24].addKeyListener(this);
		main_panel.add(picture_list[24]);

		main_panel.addKeyListener(this);
		main_panel.setFocusable(true);
		add(main_panel, BorderLayout.CENTER);

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		//getting index of clicked tile
		for(int i = 0; i < 25; i++){
			if(picture_list[i] == e.getSource()){
				clicked_index = i;
			}
		}

		/*
		 * Swapping tiles accordingly, using the value of clicked index and
		 * blank index to determine how many swaps are needed
		 */

		//if above or below
		if(blank_index%5 == clicked_index%5){	

			//if above
			if(clicked_index < blank_index){
				int num_swaps = (blank_index - clicked_index)/5;
				for(int i = 0; i < num_swaps; i++){

					temp = new PictureView(picture_list[blank_index - 5].getPicture().createObservable());
					picture_list[blank_index - 5].setPicture(blank.createObservable());
					picture_list[blank_index].setPicture(temp.getPicture());
					blank_index = blank_index - 5;
					this.revalidate();
					this.repaint();
				}

			//if below
			}else if(clicked_index > blank_index){
				int num_swaps = (clicked_index - blank_index)/5;
				for(int i = 0; i < num_swaps; i++){

					temp = new PictureView(picture_list[blank_index + 5].getPicture().createObservable());
					picture_list[blank_index + 5].setPicture(blank.createObservable());
					picture_list[blank_index].setPicture(temp.getPicture());
					blank_index = blank_index + 5;
					this.revalidate();
					this.repaint();
				}
			}

		}

		//if left
		if(clicked_index >= (blank_index - (blank_index%5)) 
				&& clicked_index < blank_index){
			int num_swaps = blank_index - clicked_index;
			for(int i = 0; i < num_swaps; i++){

				temp = new PictureView(picture_list[blank_index - 1].getPicture().createObservable());
				picture_list[blank_index - 1].setPicture(blank.createObservable());
				picture_list[blank_index].setPicture(temp.getPicture());
				blank_index = blank_index - 1;
				this.revalidate();
				this.repaint();
			}

		}

		//if right
		if(clicked_index >= blank_index 
				&& clicked_index < (blank_index + 5 - (blank_index % 5))){
			int num_swaps = clicked_index - blank_index;
			for(int i = 0; i < num_swaps; i++){

				temp = new PictureView(picture_list[blank_index + 1].getPicture().createObservable());
				picture_list[blank_index + 1].setPicture(blank.createObservable());
				picture_list[blank_index].setPicture(temp.getPicture());
				blank_index = blank_index + 1;
				this.revalidate();
				this.repaint();
			}

		}
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

	@Override
	public void keyPressed(KeyEvent e) {

		//left arrow
		if(e.getKeyCode() == 37){
			if(blank_index%5 != 0){
				temp = new PictureView(picture_list[blank_index - 1].getPicture().createObservable());
				picture_list[blank_index - 1].setPicture(blank.createObservable());
				picture_list[blank_index].setPicture(temp.getPicture());
				blank_index = blank_index - 1;
				this.revalidate();
				this.repaint();
			}
		}

		//up arrow
		if(e.getKeyCode() == 38){
			if(blank_index > 4){
				temp = new PictureView(picture_list[blank_index - 5].getPicture().createObservable());
				picture_list[blank_index - 5].setPicture(blank.createObservable());
				picture_list[blank_index].setPicture(temp.getPicture());
				blank_index = blank_index - 5;
				this.revalidate();
				this.repaint();
			}
		}

		//right arrow
		if(e.getKeyCode() == 39){
			if(blank_index%5 != 4){
				temp = new PictureView(picture_list[blank_index + 1].getPicture().createObservable());
				picture_list[blank_index + 1].setPicture(blank.createObservable());
				picture_list[blank_index].setPicture(temp.getPicture());
				blank_index = blank_index + 1;
				this.revalidate();
				this.repaint();
			}
		}

		//down arrow
		if(e.getKeyCode() == 40){
			if(blank_index < 20){
				temp = new PictureView(picture_list[blank_index + 5].getPicture().createObservable());
				picture_list[blank_index + 5].setPicture(blank.createObservable());
				picture_list[blank_index].setPicture(temp.getPicture());
				blank_index = blank_index + 5;
				this.revalidate();
				this.repaint();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub


	}





}
