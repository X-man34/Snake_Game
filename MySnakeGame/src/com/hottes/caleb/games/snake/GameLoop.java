package com.hottes.caleb.games.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;

public class GameLoop extends JPanel{

		public static final int HEIGHT = 700;
		public static final int WIDTH = 700;
		public static final int UNIT_SIZE = 25;
		public static final int GAME_UNITS = (HEIGHT * WIDTH)/25;
		final int snakeX[] = new int[GAME_UNITS];
		final int snakeY[] = new int[GAME_UNITS];
		boolean gameOver = false;
		boolean running = false;
		public long updatePeriod;
		int bodyParts = 5;
		int applesEaten = 0;
		int appleX;
		int appleY;
		char direction = 'r';
		KeyboardListener keyb;
		Random random;
		public GameLoop(KeyboardListener keyb) {
			this.keyb = keyb;
			this.updatePeriod = 150;
			this.random = new Random();
			this.setSize(this.HEIGHT, this.WIDTH);
			
		}
		
		public void startMainThread() {
			new Thread(new Runnable() {
				
				private long simulationLastMillis;
				@Override
				public void run() {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					newApple();
					running  = true;
					simulationLastMillis = System.currentTimeMillis() + 100;
					while (running) {
						if (System.currentTimeMillis() - simulationLastMillis > updatePeriod) {
							long currentTime = System.currentTimeMillis();
							
							// Do stuff
							checkControls();
							move();
							checkCollisions();
							checkApple();
							repaint();
							simulationLastMillis += updatePeriod;
						}
						
					}
					gameOver = true;
				}
			}).start();//end the thread decleration and start it
		}
		
		public void gameOver(Graphics g) {
			//game over text
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,75));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("GAME OVER",(WIDTH - metrics.stringWidth("GAME OVER"))/2,HEIGHT/2);
			
			//use enter to restart text
			g.setColor(Color.WHITE);
			g.setFont(new Font("Ariel", Font.PLAIN, 25));
			metrics = getFontMetrics(g.getFont());
			g.drawString("Enter to restart", (WIDTH - metrics.stringWidth("Enter to restart"))/2,(HEIGHT/2) + 60);
			//score
			g.setColor(Color.white);
			g.setFont(new Font("Ariel",Font.BOLD,25));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("Score"
					+ ""
					+ ""
					+ ""
					+ ": "+applesEaten,(WIDTH - metrics2.stringWidth("GAME OVER"+applesEaten))/2,(HEIGHT/2)+30);

		}

		public void restartGame() {
			running = false;
			//reset the variables that need to be reset and the other reset stuff
			bodyParts = 5;
			applesEaten = 0;
			direction = 'r';
			running = true;
			startMainThread();
		}
		public void move(){
			for(int i = bodyParts;i>0;i--) {
				snakeX[i] = snakeX[i-1];
				snakeY[i] = snakeY[i-1];
			}
			
			switch(direction) {
			case 'u':
			    snakeY[0] = snakeY[0] - UNIT_SIZE;
				break;
			case 'd':
				snakeY[0] = snakeY[0] + UNIT_SIZE;
				break;
			case 'l':
				snakeX[0] = snakeX[0] - UNIT_SIZE;
				break;
			case 'r':
				snakeX[0] = snakeX[0] + UNIT_SIZE;
				break;
			}
			
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// Background:
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());

			
			//snake
			for (int i = 0; i <bodyParts; i++) {
				if (i ==0) {
					g.setColor(new Color(18,157,34));
					g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(54,200,80));
					g.fillRect(snakeX[i], snakeY[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//apple
			g.setColor(Color.RED);
			g.fillOval(appleX,appleY,this.UNIT_SIZE,this.UNIT_SIZE);
			
			//score
			g.setColor(Color.white);
			g.setFont(new Font("Ariel",Font.BOLD,25));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten,(WIDTH - metrics.stringWidth("GAME OVER"+applesEaten))/2,25);
			if (!running) {
				gameOver(g);
			}
			
		}
		
		public void checkCollisions() {
			for (int i = bodyParts; i > 0; i--) {
			 if ((snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
				 	running = false;
				}
			}
			
			//check if head hits left border
			if (snakeX[0] < 0) {
				running  = false;
			}
			//check if head hits right border
			if (snakeX[0] > (WIDTH)) {
				running = false;
			}
			//check if head hits top
			if (snakeY[0] < 0) {
				running = false;
			}
			//check if head hits bottom
			if (snakeY[0] > (HEIGHT)) {
				running = false;
			}
		}

		public void checkControls() {
			int code = keyb.getKey();
			
			if ((code == 37) && (direction != 'r')) {
				direction = 'l';
			}else if ((code == 38) && (direction != 'd')) {
				direction = 'u';
			}else if ((code == 39) && (direction != 'l')) {
				direction = 'r';
			}else if ((code == 40) && (direction != 'u')) {
				direction = 'd';
			}
		}
		
		
		public void newApple() {
			appleX = random.nextInt((int)(WIDTH/UNIT_SIZE))*UNIT_SIZE;
			appleY = random.nextInt((int)(HEIGHT/UNIT_SIZE))*UNIT_SIZE;
			
		}
		
		public void checkApple() {
			if ((snakeX[0] == appleX) && (snakeY[0] == appleY)) {
				bodyParts++;
				applesEaten++;
				newApple();
			}
		}
}
