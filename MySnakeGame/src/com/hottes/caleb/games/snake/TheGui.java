package com.hottes.caleb.games.snake;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;



public class TheGui  implements ActionListener{
	KeyboardListener keyb;
	JFrame frame;
	GameLoop gameLoop;
	boolean userPlaying = true;
	
	public void runGame() {
		keyb = new KeyboardListener();
		frame = new JFrame();
		frame.addKeyListener(keyb);
		frame.setResizable(false);
		frame.setSize(737,762);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		gameLoop = new GameLoop(keyb);
		frame.add(BorderLayout.CENTER, gameLoop);
		frame.setTitle("Snake");
		Image im = Toolkit.getDefaultToolkit().getImage("src/resources/icon.png");
		frame.setIconImage(im);
		frame.setVisible(true);
		gameLoop.startMainThread();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		userPlaying = false;
		
	}
	public void end () {
		frame.dispose();
	}
	
	

}
