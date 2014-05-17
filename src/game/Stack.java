package game;

import java.util.Collections;
import java.util.LinkedList;

public class Stack {
LinkedList<Tile> tiles;

public Stack(){
	tiles = Tile.getAllTilesForStack();
	Collections.shuffle(tiles);
	//for(int i=0; i<70;i++)
	//	tiles.removeFirst();
	//System.out.println(tiles.size());
}
	


public Tile fetchTile(){
	return tiles.removeFirst();
}
public void returnTile(Tile t)
{
	tiles.addFirst(t);
}
public Tile peekTile(){
	return tiles.peekFirst();
}
public boolean isEmpty(){
	if (tiles.size() == 0)
		return true;
	else
		return false;
}
public String toString(){
	return tiles.toString();
}



}
