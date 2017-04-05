import java.awt.Color;

public class Entity {

	private int xCoordinate;
	private int yCoordinate;
	protected Color color;
	
	public Entity()
	{
		
	}
	
	public Entity(int x, int y)
	{
		this.xCoordinate = x;
		this.yCoordinate = y;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
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
