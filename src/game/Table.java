package game;

public class Table {
	private Tile[][] tableArray;
	private Tile lastPlacedTile;
	private int count = 0;
	
	public Table(int x,int y){
		tableArray = new Tile[x][y];
	}
	
	
	public int getLengthX(){
		return tableArray.length;
	}
	public int getLengthY(){
		return tableArray[0].length;
	}
	public Tile getTile(int x, int y){
		return tableArray[x][y];
	}
	public void placeTile(Tile tile,int x, int y){
		tableArray[x][y]= tile;
		lastPlacedTile = tile;
		genSegments(tile,x,y);
		
		/*char[][] structure = tile.getStructure();
		Segment[][] segmentarray = new Segment[5][5];
		
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++)
			{
				if (i==0 & j==0)
					segmentarray[0][0] = new Segment(structure[0][0]);
				else
				{
					if(i-1 >= 0 && segmentarray[i-1][j].getType() == structure[i][j])
					{
						//wenn stelle rechts davon gleich dann auf gleiches segment verweisen
						segmentarray[i][j] = segmentarray[i-1][j];
					}
					else if(j-1 >= 0 && segmentarray[i][j-1].getType() == structure[i][j])
					{
						//sonst, wenn stelle dr�ber davon gleich dann auf gleiches segment verweisen
						segmentarray[i][j] = segmentarray[i][j-1];
					}
					else
					{	//sonst neues segment erzeugen
						segmentarray[i][j] = new Segment(structure[i][j]);
					}
						
				}
			}
		}
		tile.setSegmentArray(segmentarray);*/
		
		
		
		
	}
	private void genSegments(Tile tile,int x,int y)
	{
		Segment[][] segmentarray = new Segment[5][5];
		char[][] structure = tile.getStructure();
		count = 0;
		//add existing segments
		if(tableArray[x][y-1] != null)
		{
			Segment[][] neighborSegmentArray = tableArray[x][y-1].getSegmentArray();
			if(neighborSegmentArray != null)
			{
				for(int i=1;i<4;i++){
					if(neighborSegmentArray[i][4] != null && neighborSegmentArray[i][4].getType() == structure[i][0])
					{
						//copy if no segment exists
						if(segmentarray[i][0] == null)
						{
							segmentarray[i][0] = neighborSegmentArray[i][4];
							recursiveWalk(i,0,structure,segmentarray);
						}
						else//connect if segment exists
						{
							//segmentarray[i][0].connect(neighborSegmentArray[i][4].getConnectedSegment());
							segmentarray[i][0].getConnectedSegment().combine(neighborSegmentArray[i][4].getConnectedSegment());
						}
						
						//segmentarray[i][0] = neighborSegmentArray[i][4];
						//recursiveWalk(i,0,structure,segmentarray);
					}
				}
			}
		}
		if(tableArray[x][y+1] != null)
		{
			Segment[][] neighborSegmentArray = tableArray[x][y+1].getSegmentArray();
			if(neighborSegmentArray != null)
			{
				for(int i=1;i<4;i++){
					if(neighborSegmentArray[i][0] != null && neighborSegmentArray[i][0].getType() == structure[i][4])
					{
						//copy if no segment exists
						if(segmentarray[i][4] == null)
						{
							segmentarray[i][4] = neighborSegmentArray[i][0];
							recursiveWalk(i,4,structure,segmentarray);
						}
						else//connect if segment exists
						{
							//segmentarray[i][4].connect(neighborSegmentArray[i][0].getConnectedSegment());
							segmentarray[i][4].getConnectedSegment().combine(neighborSegmentArray[i][0].getConnectedSegment());
						}
						//segmentarray[i][4].connect(neighborSegmentArray[i][0].getConnectedSegment());
						//segmentarray[i][4] = neighborSegmentArray[i][0];
						//recursiveWalk(i,4,structure,segmentarray);
					}
				}
			}
		}
		if(tableArray[x+1][y] != null)
		{
			Segment[][] neighborSegmentArray = tableArray[x+1][y].getSegmentArray();
			if(neighborSegmentArray != null)
			{
				for(int i=1;i<4;i++){
					if(neighborSegmentArray[0][i] != null && neighborSegmentArray[0][i].getType() == structure[4][i])
					{
						//System.out.println("check right");
						//copy if no segment exists
						if(segmentarray[4][i] == null)
						{
							segmentarray[4][i] = neighborSegmentArray[0][i];
							recursiveWalk(4,i,structure,segmentarray);
						}
						else//connect if segment exists
						{
							segmentarray[4][i].getConnectedSegment().combine(neighborSegmentArray[0][i].getConnectedSegment());
							//segmentarray[4][i].connect(neighborSegmentArray[0][i].getConnectedSegment());
							//recursiveWalk(4,i,structure,segmentarray);
						}
						
						
						//segmentarray[4][i] = neighborSegmentArray[0][i];
						//recursiveWalk(4,i,structure,segmentarray);
					}
				}
			}
		}
		if(tableArray[x-1][y] != null)
		{
			Segment[][] neighborSegmentArray = tableArray[x-1][y].getSegmentArray();
			if(neighborSegmentArray != null)
			{
				for(int i=1;i<4;i++){
					if(neighborSegmentArray[4][i] != null && neighborSegmentArray[4][i].getType() == structure[0][i])
					{
						//System.out.println("check left");
						//copy if no segment exists
						if(segmentarray[0][i] == null)
						{
							segmentarray[0][i] = neighborSegmentArray[4][i];
							recursiveWalk(0,i,structure,segmentarray);
						}
						else//connect if segment exists
						{
							segmentarray[0][i].getConnectedSegment().combine(neighborSegmentArray[4][i].getConnectedSegment());
							//segmentarray[0][i].connect(neighborSegmentArray[4][i].getConnectedSegment());
							//recursiveWalk(0,i,structure,segmentarray);
						}
						//segmentarray[0][i] = neighborSegmentArray[4][i];
						//recursiveWalk(0,i,structure,segmentarray);
					}
				}
			}
		}
		
		//generate new segments
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++)
			{
				if(segmentarray[i][j] == null)
				{
					segmentarray[i][j] = new Segment(structure[i][j]);
					recursiveWalk(i,j,structure,segmentarray);
					System.out.print(count);
					count = 0;
					
				}
				else
					System.out.print(".");
			}
		}
		System.out.println("");
		tile.setSegmentArray(segmentarray);
	}
	private void recursiveWalk(int x,int y, char[][] structure,Segment[][] segmentarray){
		
		if(x+1 < 5 && structure[x+1][y] == structure[x][y] && segmentarray[x+1][y] == null)
		{
			segmentarray[x+1][y] = segmentarray[x][y];
			recursiveWalk(x+1,y,structure,segmentarray);
		}
		if(y+1 < 5 && structure[x][y+1] == structure[x][y]&& segmentarray[x][y+1] == null)
		{
			segmentarray[x][y+1] = segmentarray[x][y];
			recursiveWalk(x,y+1,structure,segmentarray);
		}
		if(x-1 >= 0 && structure[x-1][y] == structure[x][y]&& segmentarray[x-1][y] == null)
		{
			segmentarray[x-1][y] = segmentarray[x][y];
			recursiveWalk(x-1,y,structure,segmentarray);
		}
		if(y-1 >= 0 && structure[x][y-1] == structure[x][y]&& segmentarray[x][y-1] == null)
		{
			segmentarray[x][y-1] = segmentarray[x][y];
			recursiveWalk(x,y-1,structure,segmentarray);
		}	
		count++;
		/*if (x+1 < 5 && segmentarray[x+1][y] == null)
		{
			segmentarray[x+1][y] = new Segment(structure[x+1][y]);
			recursiveWalk(x+1,y,structure,segmentarray);
		}
		if (y+1 < 5 && segmentarray[x][y+1] == null)
		{
			segmentarray[x][y+1] = new Segment(structure[x][y+1]);
			recursiveWalk(x,y+1,structure,segmentarray);
		}
		if (y-1 > 0 && segmentarray[x][y-1] == null)
		{
			segmentarray[x][y-1] = new Segment(structure[x][y-1]);
			recursiveWalk(x,y-1,structure,segmentarray);
		}
		if (x-1 > 0 && segmentarray[x-1][y] == null)
		{
			segmentarray[x-1][y] = new Segment(structure[x-1][y]);
			recursiveWalk(x-1,y,structure,segmentarray);
		}*/
		
	}
	public Tile getLastplacedTile(){
		return lastPlacedTile;
	}
	public void lockLastTile(){
		lastPlacedTile = null;
	}
	public String toString(){
		String tableString = "Cards on the Table:\n";
		for(int y=0;y<this.getLengthY();y++){	
			for(int x=0;x<this.getLengthX();x++){
				if(tableArray[x][y] != null){
					tableString= tableString + tableArray[x][y].toString()+"  ";
				}
				else
				{
					tableString= tableString + "___ ";
				}
			}
			tableString = tableString+"\n";
		}
		return tableString;
	}
	public Tile remove(int x,int y){
		Tile tempTile = tableArray[x][y];
		tableArray[x][y] = null;
		return tempTile;
	}
	public boolean fits(Tile tile,int x,int y){
		boolean retVal = true;
		if(getTile(x, y-1) != null && !tile.fitsToNeighbour(getTile(x, y-1), Tile.UP))
				retVal = false;
		if(getTile(x-1,y) != null && !tile.fitsToNeighbour(getTile(x-1, y), Tile.LEFT))
			retVal = false;
		if(getTile(x,y+1) != null && !tile.fitsToNeighbour(getTile(x, y+1), Tile.DOWN))
			retVal = false;
		if(getTile(x+1,y) != null && !tile.fitsToNeighbour(getTile(x+1, y), Tile.RIGHT))
			retVal = false;
		
		return retVal;
	}
}
