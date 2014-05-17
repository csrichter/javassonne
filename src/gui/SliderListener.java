package gui;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener {
	
	private GameFrame gameFrame;
	
	public SliderListener(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}
	
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        //if (!source.getValueIsAdjusting()) {
            int cardsize = (int)source.getValue();
            gameFrame.setCardsize(cardsize);
           // System.out.println("cardsize = "+cardsize);  only debug
            
       // }
    }

}
