import java.awt.Color;

public class Level {

	private SquareMod[] enemies, targets;
	private int[] targetDX, targetDY, enemieDX, enemieDY;
	private SimpleWindowMod window;
	public int maxScore;
	
	public Level(int numTargets, int numEnemies, int speed, SimpleWindowMod window) {
		this.window = window;
		maxScore = numTargets-1;
		
		enemies = new SquareMod[numEnemies];
		targets = new SquareMod[numTargets];
		targetDX = new int[numTargets];
		targetDY = new int[numTargets];
		enemieDX = new int[numEnemies];
		enemieDY = new int[numEnemies];

		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new SquareMod((int) (Math.random() * window.getWidth() - 30)+15,
					(int) (Math.random() * window.getHeight() - 30)+15, 30);
			enemieDX[i] = speed * (int)(Math.random() * 5) + 1;
			enemieDY[i] = speed * (int)(Math.random() * 5) + 1;
			//byt håll random så att allt inte åker åt samma håll från början
			if(Math.random() < 0.5)enemieDX[i] = -enemieDX[i];
			if(Math.random() > 0.5)enemieDY[i] = -enemieDY[i];
		}
		for (int i = 0; i < targets.length; i++) {
			targets[i] = new SquareMod((int) (Math.random() * window.getWidth() - 30)+20,
					(int) (Math.random() * window.getHeight() - 30)+20, 30);
			targetDX[i] = speed;
			targetDY[i] = speed;
			//byt håll random så att allt inte åker åt samma håll från början
			if(Math.random() < 0.5)targetDX[i] = -targetDX[i];
			if(Math.random() > 0.5)targetDY[i] = -targetDY[i];
		}
	}
	//rita upp fienderna och målen
	public void render() {
		window.setLineWidth(3);
		window.setLineColor(Color.RED);
		for (int i = 0; i < enemies.length; i++) {
			enemies[i].draw(window);
		}
		window.setLineColor(Color.GREEN);
		for (int i = 0; i < targets.length; i++) {
			targets[i].draw(window);
		}
	}
	//rör på allt
	public void move() {
		// rör på fienderna
		for (int i = 0; i < enemies.length; i++) {
			if (enemies[i].getX() < 0 || enemies[i].getX() + enemies[i].getSide() > window.getWidth())
				enemieDX[i] = -enemieDX[i];
			if (enemies[i].getY() < 0 || enemies[i].getY() + enemies[i].getSide() > window.getHeight())
				enemieDY[i] = -enemieDY[i];
			enemies[i].move(enemieDX[i], enemieDY[i]);
		}
		// rör på målen
		for (int i = 0; i < targets.length; i++) {
			// om x är -100 så är kvadraten redan tagen
			if (targets[i].getX() != -100) {
				if (targets[i].getX() < 0 || targets[i].getX() + targets[i].getSide() > window.getWidth())
					targetDX[i] = -targetDX[i];
				if (targets[i].getY() < 0 || targets[i].getY() + targets[i].getSide() > window.getHeight())
					targetDY[i] = -targetDY[i];
				targets[i].move(targetDX[i], targetDY[i]);
			}
		}
	}
	
	//om man kolliderar med ett mål så returnera 1 (värdet som adderas till score), annars returnera 0
	public int checkTarget(SquareMod player){
		int index = collideWithTarget(player);
		if(index == -1)return 0;
		
		targets[index] = new SquareMod(-100, 0, 0);
		targetDX[index] = 0;
		targetDY[index] = 0;
		
		return 1;
	}
	
	//om en target kolliderar med sq så returna indexet av targetet, annars returna -1.
	private int collideWithTarget(SquareMod sq) {
		for (int i = 0; i < targets.length; i++) {
			if ((sq.getX() >= targets[i].getX() || sq.getX() + sq.getSide() >= targets[i].getX())
					&& (sq.getX() <= targets[i].getX() + targets[i].getSide()
							|| sq.getX() + sq.getSide() <= targets[i].getX() + targets[i].getSide())) {
				if ((sq.getY() >= targets[i].getY() || sq.getY() + sq.getSide() >= targets[i].getY())
						&& (sq.getY() <= targets[i].getY() + targets[i].getSide()
								|| sq.getY() + sq.getSide() <= targets[i].getY() + targets[i].getSide())) {
					return i;
				}
			}
		}
		return -1;
	}
	//om en fiende kolliderar med sq så returna indexet av fienden, annars returna -1.
	public int collideWithEnemie(SquareMod sq) {
		for (int i = 0; i < enemies.length; i++) {
			if ((sq.getX() >= enemies[i].getX() || sq.getX() + sq.getSide() >= enemies[i].getX())
					&& (sq.getX() <= enemies[i].getX() + enemies[i].getSide()
							|| sq.getX() + sq.getSide() <= enemies[i].getX() + enemies[i].getSide())) {
				if ((sq.getY() >= enemies[i].getY() || sq.getY() + sq.getSide() >= enemies[i].getY())
						&& (sq.getY() <= enemies[i].getY() + enemies[i].getSide()
								|| sq.getY() + sq.getSide() <= enemies[i].getY() + enemies[i].getSide())) {
					return i;
				}
			}
		}
		return -1;
	}
}
