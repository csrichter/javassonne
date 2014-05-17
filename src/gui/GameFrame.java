package gui;

import game.Game;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GameFrame extends JFrame{
	
	private int cardsize;
	private TableComponent tablecomponent;
	private StackComponent stackcomponent;
	Game game;
	private JPanel rechtspanel;
	private JScrollPane scrollPane;
	private JSlider cardsizeSlider;
	private JPanel stackpanel;
	private PlayerListComponent playerListComponent;
	private static final long serialVersionUID = 1L;
	private TileComponent activeTileComp;
	private JCheckBox debugCheckbox;
	private JCheckBox showSegmentsCheckbox;
	private JCheckBox autoRotateCheckbox;
	

	public GameFrame(final Game game,int cardsize/*default: 108*/) {
		super();
		this.game = game;
		this.cardsize = cardsize;
		activeTileComp = null;
		
		tablecomponent = new TableComponent(this,game,cardsize);
		setTitle("Carcassonne");
		setBounds(200,200,800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		/*JPanel tablecomponentPanel = new JPanel();
		tablecomponentPanel.setLayout(new GridBagLayout());
		GridBagConstraints co = new  GridBagConstraints();
		co.anchor = GridBagConstraints.CENTER;
		tablecomponentPanel.add(tablecomponent,co);*/
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(tablecomponent);

		setLayout(new BorderLayout());
		add(scrollPane,BorderLayout.CENTER);
		
		

		rechtspanel = new JPanel();
		rechtspanel.setLayout(new BoxLayout(rechtspanel, BoxLayout.PAGE_AXIS));
		
		add (rechtspanel,BorderLayout.LINE_END);
		
		rechtspanel.setBorder(new CompoundBorder(
				new EmptyBorder(new Insets(10, 10, 10, 10)) //outside
		, new LineBorder(Color.black))); //inside
		
		playerListComponent = new PlayerListComponent(game,this);
		rechtspanel.add(playerListComponent);
		rechtspanel.setVisible(true);

		
		cardsizeSlider = new JSlider(JSlider.HORIZONTAL, 20, 200, 108);
		rechtspanel.add(cardsizeSlider);
		cardsizeSlider.addChangeListener(new SliderListener(this));
		cardsizeSlider.setFocusable(false);
		
		
		
		stackpanel = new JPanel();
		rechtspanel.add(stackpanel);
		
		stackcomponent = new StackComponent(game.getStack());
		stackpanel.add(stackcomponent);
		stackcomponent.setBorder(new LineBorder(Color.BLACK));
		
		//scrollPane.removeMouseWheelListener(scrollPane.getMouseWheelListeners()[0]);
		scrollPane.setWheelScrollingEnabled(false);
		//System.out.println("removing listener");
		scrollPane.addMouseWheelListener(new MyMouseWheelListener());
		
		//scrollPane.getViewport().setLocation(1080, 1080);


		
		//scrollPane.getVerticalScrollBar().setUnitIncrement(18);
		//scrollPane.getHorizontalScrollBar().setUnitIncrement(18);
		tablecomponent.setLocation(-1960,-1960);
		
		debugCheckbox = new JCheckBox("DEBUG");
		debugCheckbox.addItemListener(new MyItemListener());
		debugCheckbox.setMnemonic(KeyEvent.VK_D);
		rechtspanel.add(debugCheckbox);
		debugCheckbox.setFocusable(false);
		
		showSegmentsCheckbox = new JCheckBox("showSegments");
		showSegmentsCheckbox.addItemListener(new MyItemListener());
		showSegmentsCheckbox.setMnemonic(KeyEvent.VK_D);
		rechtspanel.add(showSegmentsCheckbox);
		showSegmentsCheckbox.setFocusable(false);
		//debugCheckbox.get
		autoRotateCheckbox = new JCheckBox("autoRotate");
		autoRotateCheckbox.addItemListener(new MyItemListener());
		autoRotateCheckbox.setMnemonic(KeyEvent.VK_D);
		rechtspanel.add(autoRotateCheckbox);
		autoRotateCheckbox.setFocusable(false);
		
		addKeyListener(new MyKeyListener());
		setLocationRelativeTo(null);//display window on center of screen
		setVisible(true);
		//System.out.println("focusableWindowState: "+this.getFocusableWindowState());
		
		debugCheckbox.doClick(); //enable debug
		autoRotateCheckbox.doClick(); //enable autorotate
		
		
		//drag-scrolling
		/*ComponentDragScrollListener l = new ComponentDragScrollListener(tablecomponent);
		tablecomponent.addMouseMotionListener(l);
		tablecomponent.addMouseListener(l);
		tablecomponent.addHierarchyListener(l);*/
		/*ViewportDragScrollListener l = new ViewportDragScrollListener(tablecomponent);
		JViewport v = scrollPane.getViewport();
		v.addMouseMotionListener(l);
		v.addMouseListener(l);
		v.addHierarchyListener(l);*/
	}
	
	public TileComponent getActiveTileComp() {
		return activeTileComp;
	}

	public void setActiveTileComp(TileComponent tileComp) {
		this.activeTileComp = tileComp;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public TableComponent getTablecomponent() {
		return tablecomponent;
	}

	public void setCardsize(int cardsize)
	{	
		Point loc = tablecomponent.getLocation();
		int diff = (this.cardsize-cardsize)*game.getTable().getLengthX()/2;
		loc.translate(diff,diff);
		
		tablecomponent.setLocation(loc);
		this.cardsize = cardsize;
		tablecomponent.SetCardsize(cardsize);
	
		//System.out.println(loc);
		scrollPane.repaint();
		scrollPane.revalidate();
	}
	public int getCardsize()
	{
		return this.cardsize;
	}
	public JSlider GetcardsizeSlider()
	{
		return cardsizeSlider;
	}
	public StackComponent getStackcomponent() {
		return stackcomponent;
	}
	
	
	class MyMouseWheelListener implements MouseWheelListener
	{
		public MyMouseWheelListener()
		{
			//System.out.println("construct");
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent mevt) {
			//System.out.println(mevt);
			if (mevt.isAltDown()) 
            {
                // Horizontal scrolling
                Adjustable adj = scrollPane.getHorizontalScrollBar();
                int scroll = mevt.getUnitsToScroll() * adj.getBlockIncrement();
                adj.setValue(adj.getValue() + scroll);

            } 
            if (mevt.isShiftDown()){
                // Vertical scrolling
                Adjustable adj = scrollPane.getVerticalScrollBar();
                int scroll = mevt.getUnitsToScroll() * adj.getBlockIncrement();
                adj.setValue(adj.getValue() + scroll);
                //System.out.println("scroll vertical");
            }
            if (!mevt.isControlDown() && !mevt.isShiftDown() && !mevt.isAltDown()) {
            	 if (mevt.getWheelRotation() < 0) {
            		 stackcomponent.getStack().peekTile().turn();
            		 stackcomponent.repaint();
            		 getActiveTileComp().revalidateColor();
            	 }
            	 else
            	 {
            		 stackcomponent.getStack().peekTile().turn(3);
            		 stackcomponent.repaint();
            		 getActiveTileComp().revalidateColor();
            	 }
            	
            }
            if (mevt.isControlDown()) {
                if (mevt.getWheelRotation() < 0) {
		            	int zoom = cardsizeSlider.getValue()+10;
		            	if (zoom < cardsizeSlider.getMaximum()){
		            		cardsizeSlider.setValue(zoom);}
                	
	                	/*int zoom = GetCardsize()-mevt.getUnitsToScroll();
		            	if (zoom < 200)
		            	{
		            		SetCardsize(zoom);
		            	}*/
		            		//System.out.println("zoom"+mevt.getUnitsToScroll());
	                    // Zoom +


                } else {
                	
	                	int zoom = cardsizeSlider.getValue()-10;
		            	if (zoom > cardsizeSlider.getMinimum()){
		            		cardsizeSlider.setValue(zoom);}
	                	/*int zoom = GetCardsize()-mevt.getUnitsToScroll();
		            	if (zoom > 20)
		            		SetCardsize(zoom);*/
		            	//System.out.println("zoom"+mevt.getUnitsToScroll());
	                    // Zoom -

                }
            }
            //System.out.println("mouseWheelListener ende");
		}
		
	}
	
	 class MyKeyListener implements KeyListener {
			
			@Override
			public void keyTyped(KeyEvent e) {
				switch(e.getKeyChar()){
				case 'n':
					game.nextPlayer();
					rechtspanel.repaint();
					break;
				case 'a':
					playerListComponent.addPlayer();
					break;
				case 'u':
					System.out.println(tablecomponent.getLocation()+"."+
							tablecomponent.getWidth());
					break;
				case 'q':
					System.exit(0);
					break;
				}
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {	}
			
			@Override
			public void keyPressed(KeyEvent e) {}
	 }
	class MyItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			 Object source = e.getItemSelectable();
			 if(source == debugCheckbox)
			 {
				 if(e.getStateChange() == ItemEvent.DESELECTED)
				 {
					 game.debugMode = false;
				 }
				 else
				 {
					 game.debugMode = true;
				 }
			 }
			 if(source == showSegmentsCheckbox)
			 {
				 if(e.getStateChange() == ItemEvent.DESELECTED)
				 {
					 game.showSegments = false;
					 repaint();
				 }
				 else
				 {
					 game.showSegments = true;
					 repaint();
				 }
			 }			
			 if(source == autoRotateCheckbox)
			 {
				 if(e.getStateChange() == ItemEvent.DESELECTED)
				 {
					 game.autoRotate = false;
					 repaint();
				 }
				 else
				 {
					 game.autoRotate = true;
					 repaint();
				 }
			 }
		}
		
	}

}
