import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * @author Victor
 * verision 1.0
 * 
 * buggar: spelaren kan spawna i en fiende eller ett mål och därmed dö på direkten eller starta spelet med ett övertag.
 * 
 * */

public class RockCrusher {

	private SimpleWindowMod window;
	private ArrayList<Level> levels = new ArrayList<Level>();
	private int score = 0, currentLevel = 0;
	private SquareMod player = new SquareMod(30, 420, 30);

	public RockCrusher(SimpleWindowMod w) {
		window = w;
		// måste lägga till fler nivåer
		levels.add(new Level(5, 6, 4, window));
		levels.add(new Level(1, 1, 6, window));
		levels.add(new Level(3, 3, 2, window));
		levels.add(new Level(15, 4, 1, window));
		levels.add(new Level(1, 10, 3, window));
		play();
	}

	private void play() {
		int score = 0;
		while (true) {
			//rita upp allt
			window.clear();
			window.setLineColor(Color.BLUE);
			movePlayer();
			player.draw(window);
			levels.get(currentLevel).move();
			levels.get(currentLevel).render();
			//kolla om man har krockat och rita upp score/level
			if(levels.get(currentLevel).collideWithEnemie(player) != -1)gameOver();
			score += levels.get(currentLevel).checkTarget(player);
			window.moveTo(15, 15);
			window.setLineColor(Color.BLACK);
			window.writeText("score : "+score+"/"+(1+levels.get(currentLevel).maxScore)+"          level : "
			+(currentLevel+1)+"/"+levels.size());

			//om man har tagit alla mål
			if(score > levels.get(currentLevel).maxScore){
				score = 0;
				currentLevel++;
				player = new SquareMod(30, 420, 30);
				if(currentLevel >= levels.size())gameOver();//om man har klarat alla levlar
			}
			
			window.waitForEvent();
		}
	}
	//rör på spelaren om det går
	private void movePlayer() {
		switch (window.getKey()) {
		case 'w':
			if (player.getY() > 0)
				player.move(0, -15);
			break;
		case 'a':
			if (player.getX() > 0)
				player.move(-15, 0);
			break;
		case 's':
			if (player.getY() + player.getSide() < window.getHeight())
				player.move(0, 15);
			break;
		case 'd':
			if (player.getX() + player.getSide() < window.getWidth())
				player.move(15, 0);
			break;
		}
	}
	//om man har förlorat ge spelaren ett val att starta om
	private void gameOver(){
		window.clear();
		window.moveTo(470, 350);
		window.writeText("GAME OVER");
		if(JOptionPane.showConfirmDialog(null, "vill du starta om?", "vill du starta om?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			new RockCrusher(window);//starta om
		System.exit(0);//om inte yes så stäng ner
	}
}
