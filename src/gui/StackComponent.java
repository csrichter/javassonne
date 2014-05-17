package gui;

import game.Stack;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class StackComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	Stack stack;
	int cardsize;
	public StackComponent(Stack stack){
		cardsize = 108;
		this.stack = stack;
		setPreferredSize(new Dimension(cardsize,cardsize));
		this.addMouseListener(new MyMouseListener());
		
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
			
		if(stack.peekTile() != null) //solange karten auf stapel
		{
			//oberste karte anzeigen
			g.drawImage(stack.peekTile().getImage(),0,0,cardsize,cardsize,null);
		}
		else
			g.drawString("Stapel leer", 20, 54);
			
		
	}
	
	public Stack getStack() {
		return stack;
	}

	class MyMouseListener implements MouseListener{
		public void mouseClicked(MouseEvent e) 
		{
			if(e.getButton() == MouseEvent.BUTTON1){
				stack.peekTile().turn(3); //im uhrzeigersinn
			}
			if(e.getButton() == MouseEvent.BUTTON3){
				stack.peekTile().turn();//gegen uhrzeigersinn
			}
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
