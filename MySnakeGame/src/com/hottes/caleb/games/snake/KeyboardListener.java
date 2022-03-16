package com.hottes.caleb.games.snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
	private int code = 0;
	boolean enterPressed = false;
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		code = 0;
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		code = e.getKeyCode();
		if (code == 10) {
			enterPressed  = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 10) {
			enterPressed = false;
		}
	}
	
	public int getKey() {
	
		return code;
	}
	
	

}
