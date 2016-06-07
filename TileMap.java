package ayy.lmao;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TileMap {
	
	private int width, height, tileSize;
	private final String path;
	private int[] tiles;
	
	public TileMap(String path, int tileSize) {
		this.tileSize = tileSize;
		this.path = path;
		try{
			load();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void load() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(path));
		this.width = Integer.parseInt(br.readLine());
		this.height = Integer.parseInt(br.readLine());
		tiles = new int[width * height]; 
		
		
		for(int y = 0; y < height; y++){
			char[] line = br.readLine().toCharArray();
			for(int x = 0; x < width; x++){
				tiles[x + y * width] = Integer.parseInt(new StringBuilder().append(line[x]).toString());
			}
		}
		
		
		
		br.close();
	}
	public void update(){
		
	}
	public void draw(Graphics2D g2d){
		g2d.setColor(Color.RED);
		g2d.fillRect(0, 0, 15, 15);
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				g2d.setColor(getColor(tiles[x + y * width]));
				g2d.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
			}
		}
	}
	
	private Color getColor(int value){
		switch(value){
			case 1: return Color.WHITE;
			case 0: return Color.BLUE;
			default: return Color.BLUE;
		}
	}
}
