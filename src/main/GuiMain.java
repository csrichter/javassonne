package main;

import game.Game;
import game.Tile;
import gui.GameFrame;

public class GuiMain {
	public static void main(String[] args) {
		
	Game testgame = new Game(40,40);
	//System.out.print(testgame.toString());
	
	testgame.getTable().placeTile(new Tile(20), 20, 20); //startkarte

	GameFrame testgameFrame = new GameFrame(testgame,108);
	testgameFrame.setVisible(true);//sinnlos,nur damit eclipse nicht meckert
	}
	
}
