package game;

import gui.GameFrame;

import java.util.LinkedList;

public class Game {

	Table table;
	Stack stack;
	LinkedList<Player> players;
	int activePlayer;
	
	

	public boolean debugMode = false; //deactivates all valitity-checks if true
	public boolean autoRotate = true;
	public boolean showSegments = false;
	
	public boolean getAutoRotate(){
		return autoRotate;
	}
	public Game(int tableX,int tableY){
		table = new Table(tableX,tableY);	
		stack = new Stack();
		activePlayer = 0;
		players = new LinkedList<Player>();
	//weitere Objekte erzeugen
	}

	
	public LinkedList<Player> getPlayers(){
		return players;
	}
	public void addPlayer(Player player){
		players.addLast(player);
	}
	public int getactivePlayerNum(){//not used
		return activePlayer;
	}
	public Player getactivePlayer(){
		return players.get(activePlayer);
	}
	public void nextPlayer(){
		players.get(activePlayer).hasFinished();
		activePlayer++;//spieler hochzählen
		if (activePlayer == players.size()){//wenn ende erreicht zu null zurück
			activePlayer = 0;
		}
		//table.lockLastTile();
		
	}
	public void previousPlayer(){
		activePlayer--;//spieler runterzählen
		if (activePlayer == -1){//wenn null erreicht zu ende springen
			activePlayer = players.size()-1;
		}
		//table.unlockLastTile();
		
	}
	
	public Table getTable(){
		return table;
	}
	public Stack getStack(){
		return stack;
	}
	public String toString(){
		return table.toString()+stack.toString();
	}
	public void newGame() {
		Game game = new Game(40,40);
		//System.out.print(testgame.toString());
		
		game.getTable().placeTile(new Tile(20), 20, 20); //startkarte
		//this = game;
		GameFrame testgameFrame = new GameFrame(game,108);
		testgameFrame.setVisible(true);//sinnlos, damit eclipse nicht meckert
		//this = new Game(40,40);
//		table = new Table(40,40);	
//		stack = new Stack();
//		activePlayer = 0;
//		players = new LinkedList<Player>();
	}
	
}
