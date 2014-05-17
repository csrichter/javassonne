package game;
import java.awt.Color;

public class Segment {
ConnectedSegment connectedSegment;
	
public Segment(char type) {
	switch (type) {
	case 'C':
		connectedSegment = new City();
		break;
	case 'G':
		connectedSegment = new Field();
		break;
	case 'S':
		connectedSegment = new Street();
		break;
	case 'M':
		connectedSegment = new Monastery();
		break;
	case ' ':
		connectedSegment = new Nothing();
		break;
	default:
		break;
	}
	connectedSegment.addSegment(this);

}
public char getType() {
	return connectedSegment.getType();
}	
public Color getColor() {
	return connectedSegment.getColor();
}
public ConnectedSegment getConnectedSegment() {
	return connectedSegment;
}
public void connect(ConnectedSegment newconnectedSegment)
{
	if(connectedSegment == null) //first connection
	{
		this.connectedSegment = newconnectedSegment;
		this.connectedSegment.addSegment(this);
	}
	else //reconnect
	{
		this.connectedSegment.removeSegment(this);
		this.connectedSegment = newconnectedSegment;
		this.connectedSegment.addSegment(this);
	}
	
	//System.out.println("connected");
}

}