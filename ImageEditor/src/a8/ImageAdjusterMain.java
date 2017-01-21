package a8;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageAdjusterMain {

	public static void main(String[] args) throws IOException {
		Picture p = A8Helper.readFromURL("https://68.media.tumblr.com/308dda68a4c00f6a019c838ea81d2988/tumblr_ok3rg7cuqc1r1gicao1_r1_400.jpg");
		ImageAdjuster ia_widget = new ImageAdjuster(p);
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 8 Image Adjuster");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(ia_widget, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);
	}
}
