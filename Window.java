package com.gafw.graphics.window;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class Window {
	
	private JFrame frame;
	private final Dimension dim;
	private String title;
	private Canvas canvas;
	
	public Window(String title, int width, int height, int scale, Canvas main) {
		this.title = title;
		this.canvas = main;
		this.dim = new Dimension(width * scale, height * scale);
		
		init();
		//main.start();
	}
	
	private void init(){
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setPreferredSize(dim);
		frame.setMinimumSize(dim);
		frame.setMaximumSize(dim);
		
		int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
				height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setLocation((int)(width - dim.getWidth()) / 2, (int)(height - dim.getHeight()) / 2);
		frame.add(canvas);
		frame.setVisible(true);
	}
	
	public JFrame getFrame(){return frame;}
	public int getWidht(){return (int)dim.getWidth();}
	public int getHeight(){return (int)dim.getHeight();}
	public Dimension getDim(){return dim;}
	public String getTitle(){return title;}
	
	public void setTitle(String title){frame.setTitle(title);}
}
