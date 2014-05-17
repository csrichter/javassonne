package gui;

import game.Game;
import game.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

public class PlayerComponent extends JComponent {

private static final long serialVersionUID = 1L;
Player player;
Game game;


public PlayerComponent(final Player player, Game game) {
	super();
	super.setBorder(new LineBorder(Color.BLACK));
	this.player = player;
	this.game = game;
	this.addMouseListener(new MouseTest(){

		@Override
		public void mouseClicked(MouseEvent e) 
		{
			
			if(e.getButton() == MouseEvent.BUTTON1){
				player.increment();
				repaint();
			}
			if(e.getButton() == MouseEvent.BUTTON3){
				player.decrement();
				repaint();
			}
			
		}
	});//end of listener
}


public void paintComponent(Graphics g){
	super.paintComponent(g);
	g.setColor(player.getColor());

	g.fillRect(0, 0, getWidth(), getHeight());
	g.setColor(Color.BLACK);
	g.drawString(player.getName(), 5, 13);
	g.drawString("score:"+new Integer(player.getScore()).toString(),getWidth()-60 ,getHeight()*3/4);
	g.drawString("followers:"+new Integer(player.getRemainingFollowers()).toString(),10 ,getHeight()*3/4);
	
	//if player is active
	if (player.equals(game.getactivePlayer())){
		super.setBorder(new LineBorder(Color.RED,3));
	}
	else
	{
		super.setBorder(new LineBorder(Color.BLACK));
	}
}


}
