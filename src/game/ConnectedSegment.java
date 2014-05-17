package game;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;

public abstract class ConnectedSegment {
	//private char type;
	private Color color;
	private HashSet<PlacedFollower> followerSet = new HashSet<PlacedFollower>(); 
	private HashSet<Segment> segmentSet = new HashSet<Segment>(); 

public ConnectedSegment() {
	this.color = colorGen();
	//this.type = type;
}
public ConnectedSegment(HashSet<PlacedFollower> followerSet, HashSet<Segment> segmentSet) {
	this.color = colorGen();
	this.followerSet = followerSet;
	this.segmentSet = segmentSet;
	//this.type = type;
}
public char getType() {
	if(this.getClass() == City.class)
		return 'C';
	if(this.getClass() == Monastery.class)
		return 'M';
	if(this.getClass() == Street.class)
		return 'S';
	if(this.getClass() == Field.class)
		return 'G';
	if(this.getClass() == Nothing.class)
		return ' ';
	return ' ';
}	
public Color getColor() {
	Color tempColor = this.color;

	for (PlacedFollower pf : followerSet) {
		Color c = pf.getFollower().getPlayer().getColor();
		tempColor = new Color(c.getRed(),c.getGreen(),c.getBlue(),127); //setzt alpha-transparenz

	}
	return tempColor;
}




static Color colorGen(){//generate random color
	//create new random-object
	Random rand = new Random();
	//get 3 random integers
	int r = rand.nextInt(256);
	int g = rand.nextInt(256);
	int b = rand.nextInt(256);
	
	return new Color(r,g,b,127);
}
public HashSet<PlacedFollower> getFollowerSet() {
	return followerSet;
}
public void addFollower(PlacedFollower followerToAdd){
	followerSet.add(followerToAdd);
}
public void removeFollower(PlacedFollower pf){
	followerSet.remove(pf);
}
public void addSegment(Segment s){
	segmentSet.add(s);	
}
public void removeSegment(Segment s){
	segmentSet.remove(s);	
}
public HashSet<Segment> getSegmentSet() {
	return segmentSet;
}

public void combine(ConnectedSegment cs){
	 /*HashSet<PlacedFollower> combinedFollowerSet = new HashSet<PlacedFollower>();
	 HashSet<Segment> combinedSegmentSet = new HashSet<Segment>();
	 
	 combinedFollowerSet.addAll(cs.getFollowerSet());
	 combinedSegmentSet.addAll(cs.getSegmentSet());
	 combinedFollowerSet.addAll(this.getFollowerSet());
	 combinedSegmentSet.addAll(this.getSegmentSet()); */
	if(cs.hashCode() != this.hashCode())
	{
		this.followerSet.addAll(cs.getFollowerSet());
		HashSet<Segment> otherSegmentSet = new HashSet<Segment>();
		otherSegmentSet.addAll(cs.getSegmentSet());

		for (Segment s : otherSegmentSet) {
			s.connect(this);
		}
		System.out.println("merged "+cs.hashCode()+" into "+this.hashCode());
	}
	else
		System.out.println("same");
}

}
