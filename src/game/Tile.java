package game;

import images.ImageHelper;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import data.DataHelper;


public class Tile {
	//Attributes
	private BufferedImage image;
	private String name;
	private int number;
	private PlacedFollower[][] followerArray = new PlacedFollower[5][5];//0-4
	//private Follower follower;
	private int followerCount =0;
	private char[][] structure;	
	private Segment[][] segmentArray;
	
	public Segment[][] getSegmentArray() {
		return segmentArray;
	}
	public void setSegmentArray(Segment[][] segmentArray) {
		this.segmentArray = segmentArray;
	}
	
	public Tile(int number){
		this.number = number;
		if(this.number <10)
		{
			name = "0"+Integer.toString(this.number);
		}
		else 
		{
			name = Integer.toString(this.number);
		}
		structure = DataHelper.loadCard(number);
		image = ImageHelper.loadImage(this.number);
		//followerPos = null;
		//follower = null;
	}
	
	public String getName() {
		return name;
	}
	public char[][] getStructure(){
		return structure;
	}
	public void printStructure(){
		/*String ret = "";
		for (int i = 0;i<5;i++ )
		{
			for(int j= 0;j<5;j++)
			{
				ret += structure[j][i];
			}
			ret += "\n";
		}
		System.out.println(ret);*/
		
		
		//print segments
		/*String ret2 = "";
		for (int i = 0;i<5;i++ )
		{
			for(int j= 0;j<5;j++)
			{
				if (segmentArray[j][i] != null)
					ret2 += segmentArray[j][i].hashCode()+ "\t";
				else
					ret2 += "null     \t";
			}
			ret2 += "\n";
		}
		System.out.println(ret2);*/
		
		//print corresponding connected segments
		String ret3 = "";
		for (int i = 0;i<5;i++ )
		{
			for(int j= 0;j<5;j++)
			{
				if (segmentArray[j][i] != null)
					ret3 += segmentArray[j][i].getConnectedSegment().hashCode()+ "\t";
				else
					ret3 += "null     \t";
			}
			ret3 += "\n";
		}
		System.out.println(ret3);
		
	}
	public String toString(){
		return name;
	}
	public BufferedImage getImage()
	{
		return image;
	}
	public void turn(){
		image = ImageHelper.turnImage(image);
		rotateFollowerArray();
	}
	public void turn(int count){
		for(int i=0; i<count;i++)
		{
			image = ImageHelper.turnImage(image);
			rotateFollowerArray();
		}
	}	
	
	public static LinkedList<Tile> getAllTilesForStack(){
		
		LinkedList<Tile> cardlist = new LinkedList<Tile>();
		int count;
		for(int i=1;i <= DataHelper.NUMBER_OF_CARDS;i++){
			count = DataHelper.getCardCount(i);
			//count mal Tile der cardlist hinzuf�gen
			for(int j=0;j < count;j++){
				cardlist.add(new Tile(i));
			}
		}
		return cardlist;
	}
	public void placeFollower(Follower f,Point cardPos, Point followerPos){
		PlacedFollower pf = new PlacedFollower(f,cardPos ,followerPos);
		followerArray[followerPos.x][followerPos.y] = pf;
		segmentArray[followerPos.x][followerPos.y].getConnectedSegment().addFollower(pf);
		followerCount++;
		
	}
	public Follower removeFollower(Point followerPos){
		PlacedFollower pf = followerArray[followerPos.x][followerPos.y];
		followerArray[followerPos.x][followerPos.y] = null;
		segmentArray[followerPos.x][followerPos.y].getConnectedSegment().removeFollower(pf);
		followerCount--;
		return pf.getFollower();
	}
	public int getFollowerCount(){
		return followerCount;
	}
	public Follower getFollower(Point followerPos){
		if(followerArray[followerPos.x][followerPos.y] ==null)
			return null;
		else
			return followerArray[followerPos.x][followerPos.y].getFollower();
	}
//	public Follower[][] getfollowerArray(){
//		return followerArray;
//	}
	public void rotateFollowerArray(){


		
		PlacedFollower[][] newArray = new PlacedFollower[5][5];

        //invert values 90 degrees clockwise by starting from button of
        //array to top and from left to right
       /*  int ii = 0;
        int jj = 0;
        for(int i=0; i<followerArray[0].length; i++){
            for(int j=followerArray.length-1; j>=0; j--){
                newArray[ii][jj] = followerArray[i][j];

                jj++;
            }
            ii++;
            jj=0;
        }*/
		char[][] newStructure = new char[5][5];
		
		int ii=0;
		int jj=4;
		for (int i = 0;i<5;i++ )
		{
			for(int j= 0;j<5;j++)
			{
				newArray[ii][jj] = followerArray[i][j];
				newStructure[ii][jj] = structure[i][j];
				ii++;
			}
			ii=0;
			jj--;
			
		}
		
		structure = newStructure;
        followerArray = newArray;
    }
	public static final int UP=0;
	public static final int RIGHT=1;
	public static final int DOWN=2;
	public static final int LEFT=3;
	
	public boolean fitsToNeighbour(Tile neighbour,int direction){
		boolean retVal = true;
		char[][] neighbourStructure = neighbour.getStructure();
		switch(direction){
		case UP:  for(int i=1;i<4;i++){
			if(neighbourStructure[i][4]!= structure[i][0])
				retVal= false;
		}
		break;
		case RIGHT:  for(int j=1;j<4;j++){
			if(neighbourStructure[0][j]!= structure[4][j])
				retVal= false;
		}
		break;
		case DOWN:  for(int i=1;i<4;i++){
			if(neighbourStructure[i][0]!= structure[i][4])
				retVal= false;
		};
		break;
		case LEFT:  for(int j=1;j<4;j++){
			if(neighbourStructure[4][j]!= structure[0][j])
				retVal= false;
		}
		break;
		}
			
		return retVal;
	}
	
}
