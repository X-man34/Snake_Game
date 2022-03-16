package com.hottes.caleb.games.snake;

public class Main {

	public static void main(String[] args) {
	while (true) {
		TheGui gui = new TheGui();
		gui.runGame();
		while (gui.userPlaying) {
			if(gui.keyb.enterPressed) {
				break;
			}
		}
		gui.end();
	}
	
	}

}
