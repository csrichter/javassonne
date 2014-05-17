package game;

import java.awt.Point;

public class PlacedFollower {
private Follower follower;
private Point cardPos;
private Point posOnCard;
public Follower getFollower() {
	return follower;
}
public Point getCardPos() {
	return cardPos;
}
public Point getPosOnCard() {
	return posOnCard;
}
public PlacedFollower(Follower follower, Point cardPos, Point posOnCard) {
	this.follower = follower;
	this.cardPos = cardPos;
	this.posOnCard = posOnCard;
}
public void remove(){
	
}

}
