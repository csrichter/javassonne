package gui;

import game.Game;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;

public class TableComponent extends JComponent {

	
	private static final long serialVersionUID = -5227001973958170476L;
	Game game;
	GameFrame gameframe;
	TileComponent[][] tileCompArray; /* for resizing the cards*/
	
	public TableComponent(GameFrame gameframe, Game game,int cardsize) {	
		
		this.gameframe = gameframe;
		this.game = game;
		setPreferredSize(new Dimension(game.getTable().getLengthX()*cardsize,game.getTable().getLengthY()*cardsize));
		
		tileCompArray = new TileComponent[game.getTable().getLengthX()][game.getTable().getLengthY()];
		
		setLayout(new GridLayout(0,game.getTable().getLengthY()));
		
		for(int i=0; i< game.getTable().getLengthY();i++){
			for(int j=0;j<game.getTable().getLengthX();j++)
			{
				tileCompArray[j][i]= new TileComponent(gameframe,game, j, i,cardsize);
				add(tileCompArray[j][i]);				
			}	
		}
	}

	public void SetCardsize(int cardsize)
	{
		for(int i=0; i< game.getTable().getLengthY();i++){
			for(int j=0;j<game.getTable().getLengthX();j++)
				tileCompArray[j][i].SetCardsize(cardsize);
		}
		super.setPreferredSize(new Dimension(game.getTable().getLengthX()*cardsize,game.getTable().getLengthY()*cardsize));
		//this.updateUI();
	}
}
