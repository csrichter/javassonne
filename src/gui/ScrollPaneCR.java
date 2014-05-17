package gui;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class ScrollPaneCR extends JScrollPane{

	@Override
	public void updateUI() {
		this.setWheelScrollingEnabled(false);
		super.updateUI();
		System.out.println("update scrollpane");
		//super.repaint();
                     
	}
	private static final long serialVersionUID = 1L;

	public ScrollPaneCR(JComponent temp){
		super(temp);
		this.setWheelScrollingEnabled(false);
		
	}
	
	
	
}
