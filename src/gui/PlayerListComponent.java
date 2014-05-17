package gui;

import game.Game;
import game.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PlayerListComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	Game game;
	final JPanel playerPanel = new JPanel();
	public PlayerListComponent(final Game game,final GameFrame gameframe) {
		this.game = game;
		LinkedList<Player> players = game.getPlayers();
		setLayout(new BorderLayout());
		
		
		playerPanel.setLayout(new GridLayout(4,1));

		game.addPlayer(new Player("test-player",Color.GREEN));//for testing only
		//add existing players (there shouldn't be any)
		for (Player player : players) {
			playerPanel.add(new PlayerComponent(player,game));
		}
		add(playerPanel,BorderLayout.CENTER);
		playerPanel.setBorder(new LineBorder(Color.BLACK));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton nextPlayerButton = new JButton("next");
		nextPlayerButton.setFocusable(false);

		JButton newPlayerButton = new JButton("add");
		newPlayerButton.setFocusable(false);
		
		JButton newGameButton = new JButton("new Game");
		newGameButton.setFocusable(false);
		
		buttonPanel.add(newPlayerButton,BorderLayout.LINE_START);
		buttonPanel.add(nextPlayerButton,BorderLayout.CENTER);
		buttonPanel.add(newGameButton,BorderLayout.LINE_END);
		
		
		
		add(buttonPanel,BorderLayout.PAGE_START);
		newPlayerButton.addMouseListener(new MouseTest(){

			@Override
			public void mouseClicked(MouseEvent e) 
			{
				
				if(e.getButton() == MouseEvent.BUTTON1){
					addPlayer();
				}
				
			}
		});//end of listener
		nextPlayerButton.addMouseListener(new MouseTest(){

			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getButton() == MouseEvent.BUTTON1){
					//spieler anlegen
					game.nextPlayer();
					playerPanel.repaint();
				}
				
			}
		});//end of listener
		newGameButton.addMouseListener(new MouseTest(){

			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getButton() == MouseEvent.BUTTON1){
					//neues spiel beginnnen
					game.newGame();
					gameframe.dispose();
					
				}
				
			}
		});//end of listener		
	}//end of constructor
	public void addPlayer(){
		//spieler anlegen
		String name = JOptionPane.showInputDialog("enter new name","spieler");
		if(name == null || name.isEmpty())
		{
			//JOptionPane.showMessageDialog(null,"name ungültig");
		}
		else
		{
		Player player = new Player(name);
		game.addPlayer(player);
		playerPanel.add(new PlayerComponent(player,game));
		playerPanel.revalidate();
		playerPanel.repaint();
		}
	}
}
