
public class Player {
	
	private int xCoordinate;
	private int yCoordinate;
	
	public Player()
	{
		
	}
	
	public Player(int x, int y)
	{
		this.xCoordinate = x;
		this.yCoordinate = y;
	}
	
	public void setXCoordinate(int x)
	{
		xCoordinate = x;
	}
	
	public void setYCoordinate(int y)
	{
		yCoordinate = y;
	}
	
	public int getXCoordinate()
	{
		return xCoordinate;
	}
	
	public int getYCoordinate()
	{
		return yCoordinate;
	}
	
	public void moveUp()
	{
		xCoordinate++;
	}
	
	public void moveDown()
	{
		xCoordinate--;
	}
	
	public void moveLeft()
	{
		yCoordinate--;
	}
	
	public void moveRight()
	{
		yCoordinate++;
	}
	
}
