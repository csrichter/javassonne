package gui;

import game.Game;
import game.Segment;
import game.Table;
import game.Tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

public class TileComponent extends JComponent {
	
	private int cardsize;
	Game game;
	private Table table;
	private int posX;
	private int posY;
	GameFrame gameframe;
	Color color;
	Tile tile;
	
	private static final long serialVersionUID = 1L;

	public TileComponent(final GameFrame gameframe,final Game game,final int posX, final int posY,int cardsize) {
		super();
		//System.out.println("construct");
		table = game.getTable();
		tile = table.getTile(posX, posY);
		this.cardsize = cardsize;
		this.game = game;
		this.posX = posX;
		this.posY = posY;
		color = new Color(0,0,0,1);
		//final TileComponent tileComp = this;
		
		super.setSize(cardsize,cardsize);
		super.setBorder(new LineBorder(Color.black));
		this.addMouseListener(new MouseTest(){
			
			@Override
			public void mouseEntered(MouseEvent e) {
				gameframe.setActiveTileComp(TileComponent.this);
				//solange stapel nicht leer oberste karte rotiern
				if(game.getAutoRotate()&& game.getStack().peekTile() != null)
				{
					for(int i=0; i<4;i++)
					{
						//turn card if it doesnt fit
						if(!table.fits(game.getStack().peekTile(), posX, posY))
								game.getStack().peekTile().turn();
						gameframe.getStackcomponent().repaint();
					}
						
				}
				revalidateColor();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				color = new Color(0,0,0,1);
				gameframe.setActiveTileComp(null);
				repaint();
			}
	
			@Override
			public void mousePressed(MouseEvent e) 
			{	
			if(!e.isShiftDown()){
				
				if(e.getButton() == MouseEvent.BUTTON1){
					//wenn keine Karte liegt
					if(tile == null){
						if(game.debugMode && game.getStack().peekTile()!=null)
						{
							//karte legen
							table.placeTile(game.getStack().fetchTile(), posX, posY);
							tile = table.getTile(posX, posY);
							game.getactivePlayer().placeCard();
						}
						//regeln prüfen
						else if(game.getStack().peekTile()==null)
							JOptionPane.showMessageDialog(gameframe, "stapel leer");
						else if(game.getactivePlayer().hasPlacedCard())
							JOptionPane.showMessageDialog(gameframe, "spieler hat schon gelegt");
						else if((!hasNeighbor() | !table.fits(game.getStack().peekTile(), posX, posY)))
						{
							JOptionPane.showMessageDialog(gameframe, "karte passt nicht");
						}
						else
						{
							//karte legen
							table.placeTile(game.getStack().fetchTile(), posX, posY);
							tile = table.getTile(posX, posY);
							game.getactivePlayer().placeCard();
						}
					}
					else
					{
						//mausposition abfragen
						int x,y; //pos on tile
						x = (int)Math.floor(e.getPoint().getX()/(getWidth())*5); 
						y = (int)Math.floor(e.getPoint().getY()/(getHeight())*5);

						Point clickedPart = new Point(x,y);
						
				
						//System.out.println(clickedPart);
						
						if(tile.getFollower(clickedPart) == null)
						{
							//regeln prüfen
							if (!currentTileWasPlacedLast() && !game.debugMode)
								JOptionPane.showMessageDialog(gameframe, "karte wurde nicht zuletzt lelegt");
							else if(tile.getFollowerCount() != 0 && !game.debugMode)
							{
								JOptionPane.showMessageDialog(gameframe, "hier sitzt bereits ein gefolgsmann");
							}
							else if((!game.getactivePlayer().canPlaceFollower() )&& !game.debugMode)
							{
								//System.out.println("hier darf kein Gefolgsmann mehr gesetzt werden");
								JOptionPane.showMessageDialog(gameframe, "Spieler hat schon gefolgsmann gesetzt");
							}
							else if(game.getactivePlayer().getRemainingFollowers() ==0)
							{
								JOptionPane.showMessageDialog(gameframe, "aktiver Spieler hat keine Gefolgsleute mehr");
							}
							else
							{

								//gefolgsman setzen
								tile.placeFollower(game.getactivePlayer().getFollower(),new Point(posX,posY), clickedPart);
								System.out.println("gefolgsman gesetzt");
								//game.nextPlayer();
							}
						}
						else
						{
							//gefolgsmann entfernen und dem zugehörigen spieler zurück geben
							tile.getFollower(clickedPart).getPlayer().returnFollower(
									tile.removeFollower(clickedPart));
						
							System.out.println("gefolgsman entfernt");
						}

					}

				}
				if(e.getButton() == MouseEvent.BUTTON3){
					//System.out.println("mouse3");
					if(tile == null){
						//nichts
					}
					else
					{
						//karte auf stapel zurück und vom tisch entfernen
						game.getStack().returnTile(table.remove(posX, posY));
						tile = table.getTile(posX, posY);
					}
				}

				repaint(); 
				gameframe.getStackcomponent().repaint();
				System.out.println("pos:"+posX+","+posY);
				
				if(e.getButton() == MouseEvent.BUTTON2){//print debug messages
					if(tile != null){
						System.out.println("cardnumber"+tile.getName());
						tile.printStructure();
						System.out.println("fits:"+table.fits(tile, posX, posY));
					}
					
	
					
				}
			}}
		});
	}
	public void SetCardsize(int cardsize)
	{
		this.cardsize = cardsize;
		super.setSize(cardsize,cardsize);
		super.repaint();
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//System.out.println("paint");
		//System.out.println(gameframe.getCardsize());
		if(tile != null)
		{
			g.drawImage(tile.getImage(),0,0,cardsize,cardsize,null);
			Point testpunkt;
			for(int i=0;i<5;i++){
				for(int j=0;j<5;j++){
					testpunkt = new Point(i,j);
					if(tile.getFollower(testpunkt) != null){
						//if there is a follower
						g.setColor(tile.getFollower(testpunkt).getPlayer().getColor());
						g.fillOval((int)(this.getHeight()/5*i), 
								(int)(this.getWidth()/5*j)
								, getHeight()/5, getWidth()/5);
						g.setColor(Color.black);
						g.drawOval((int)(this.getHeight()/5*i), 
								(int)(this.getWidth()/5*j)
								, getHeight()/5, getWidth()/5);
					}

				}}
			//color segments
			if(game.showSegments)
			{
				Segment[][] segmentArray = tile.getSegmentArray();
				for(int i=0;i<5;i++){
					for(int j=0;j<5;j++)
					{
						g.setColor(segmentArray[i][j].getColor());
						//int fifthcardsize = (int)Math.floor(cardsize/5);
						g.fillRect(i*cardsize/5,j*cardsize/5,cardsize/5,cardsize/5);
					}
				}
			}
		
		}else
		{
			g.setColor(color);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		
	}
	private boolean currentTileWasPlacedLast(){
		return tile.equals(table.getLastplacedTile());}
	private boolean hasNeighbor(){
		boolean retVal = (table.getTile(posX+1, posY) != null)
				| (table.getTile(posX-1, posY) != null)
				| (table.getTile(posX, posY+1) != null)
				| (table.getTile(posX, posY-1) != null);
		//true, wenn mindestens an einer Kante eine andere Karte liegt
		return retVal;
	}
	
	public void revalidateColor(){
		if(hasNeighbor()&& game.getStack().peekTile() != null &&table.fits(game.getStack().peekTile(), posX, posY))
		{
			color = new Color(0,255,0,127);
		}
		else
		{
			color = new Color(255,0,0,127);
		}
		repaint();
	}
}
