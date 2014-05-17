package game;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

public class Player {
	private String name;
	private Color color;
	private int score;
	private LinkedList<Follower> followers = new LinkedList<Follower>();
	private boolean placedCard = false;
	private boolean placedFollower = false;
	
	public String toString(){
		return name+":"+score;
	}
	
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
		this.score = 0;
		//create 7 followers
		for(int i=0;i<7;i++)
			followers.add(new Follower(this));
		
	}	
	public Player(String name) {
		this.name = name;
		this.color = Player.colorGen();
		this.score = 0;
		//create 7 followers
		for(int i=0;i<7;i++)
			followers.add(new Follower(this));
	}

	public boolean hasPlacedCard() {
		return placedCard;
	}

	public boolean canPlaceFollower() {
		return !placedFollower;
	}
	public void hasFinished(){
		this.placedCard = false;
		this.placedFollower = false;
	}
	public void placeCard(){
		this.placedCard = true;
	}	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void increment(){
		score += 1;
	}
	public void increment(int i){
		score += i;
	}
	public void decrement(){
		score -= 1;
	}
	public void decrement(int i){
		score -= i;
	}	
	public int getRemainingFollowers()
	{
		return followers.size();
	}
	public Follower getFollower(){ //get and remove first follower
		this.placedFollower = true;
		return followers.removeFirst();
	}
	public void returnFollower(Follower f){
		followers.addFirst(f);
		this.placedFollower = false;
	}

	static Color colorGen(){//generate random color
		//create new random-object
		Random rand = new Random();
		//get 3 random integers
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		
		return new Color(r,g,b);
	}
}
