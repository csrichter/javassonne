package main;
import game.Game;
import game.Tile;

public class TextMain {

	public static void main(String args[]){
		Game testgame = new Game(4,3);
		testgame.getTable().placeTile(new Tile(1), 0, 0);
		testgame.getTable().placeTile(new Tile(2), 1, 0);
		testgame.getTable().placeTile(new Tile(6), 2, 0);
		testgame.getTable().placeTile(new Tile(2), 0, 1);
		testgame.getTable().placeTile(new Tile(6), 2, 1);
		//System.out.println(testgame.getTable().getLengthX());
		System.out.print(testgame.toString());
	
	}


}
