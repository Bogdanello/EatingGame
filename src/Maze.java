import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private int n;                 // dimension of maze
    private boolean[][] north;     // is there a wall to north of cell i, j
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    private boolean[][] visited;
    private boolean done = false;
    private Player player;
    private List<Enemy> enemies;
    
    private enum Difficulty {Easy, Normal, Hard};
    private enum Moves {Up, Down, Left, Right};
    
    public Maze(int n) {
        this.n = n;
        StdDraw.setXscale(0, n+2);
        StdDraw.setYscale(0, n+2);
        init();
        generate();
        player = new Player(1,1);
        
        
    }

    private void init() {
        // initialize border cells as already visited
        visited = new boolean[n+2][n+2];
        for (int x = 0; x < n+2; x++) {
            visited[x][0] = true;
            visited[x][n+1] = true;
        }
        for (int y = 0; y < n+2; y++) {
            visited[0][y] = true;
            visited[n+1][y] = true;
        }


        // initialze all walls as present
        north = new boolean[n+2][n+2];
        east  = new boolean[n+2][n+2];
        south = new boolean[n+2][n+2];
        west  = new boolean[n+2][n+2];
        for (int x = 0; x < n+2; x++) {
            for (int y = 0; y < n+2; y++) {
                north[x][y] = true;
                east[x][y]  = true;
                south[x][y] = true;
                west[x][y]  = true;
            }
        }
    }

    
    // generate the maze
    private void generate(int x, int y) {
        visited[x][y] = true;

        // while there is an unvisited neighbor
        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {

            // pick random neighbor (could use Knuth's trick instead)
            while (true) {
                double r = StdRandom.uniform(4);
                if (r == 0 && !visited[x][y+1]) {
                    north[x][y] = false;
                    south[x][y+1] = false;
                    generate(x, y + 1);
                    break;
                }
                else if (r == 1 && !visited[x+1][y]) {
                    east[x][y] = false;
                    west[x+1][y] = false;
                    generate(x+1, y);
                    break;
                }
                else if (r == 2 && !visited[x][y-1]) {
                    south[x][y] = false;
                    north[x][y-1] = false;
                    generate(x, y-1);
                    break;
                }
                else if (r == 3 && !visited[x-1][y]) {
                    west[x][y] = false;
                    east[x-1][y] = false;
                    generate(x-1, y);
                    break;
                }
            }
        }
    }

    // generate the maze starting from lower left
    private void generate() {
        generate(1, 1);
     
    }


    // draw the maze
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
       // StdDraw.filledCircle(n/2.0 + 0.5, n/2.0 + 0.5, 0.375);
//        StdDraw.filledCircle(1.5, 1.5, 0.375);
//        StdDraw.filledCircle(2.5, 1.5, 0.375);
        
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                if (south[x][y]) StdDraw.line(x, y, x+1, y);
                if (north[x][y]) StdDraw.line(x, y+1, x+1, y+1);
                if (west[x][y])  StdDraw.line(x, y, x, y+1);
                if (east[x][y])  StdDraw.line(x+1, y, x+1, y+1);
            }
        }
        StdDraw.show();
        //StdDraw.pause(1000);
    }

    public void setPlayerPosition(int x, int y)
    {
    	player.setXCoordinate(x);
    	player.setYCoordinate(y);
    }
    
    private void movePlayer()
    {
    	char keyPressed = ' '; 
    	StdDraw.hasNextKeyTyped();
    	while(!StdDraw.hasNextKeyTyped());
		keyPressed = StdDraw.nextKeyTyped();
    	int x, y;
    	cleanPreviousPosition(player);
    	
    	switch(keyPressed)
    	{
    	case 'W':
    	case 'w':
    		if(!north[player.getYCoordinate()][player.getXCoordinate()])
    			player.moveUp();
    		break;
    	case 'S':
    	case 's':
    		if(!south[player.getYCoordinate()][player.getXCoordinate()])
    			player.moveDown();
    		break;
    	case 'A':
    	case 'a':
    		if(!west[player.getYCoordinate()][player.getXCoordinate()])
    			player.moveLeft();
    		break;
    	case 'D':
    	case 'd':
    		if(!east[player.getYCoordinate()][player.getXCoordinate()])
    			player.moveRight();
    		break;
    	default:
    		return;
    	}
    	
    }
    
    private void moveEnemy(Enemy enemy) 
    {
    	int move;
    	boolean validMove = false;
    	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cleanPreviousPosition(enemy);
		while(!validMove)
		{
			move = (int)(4*Math.random());
			switch(move)
			{
			case 0:
				if(!north[enemy.getYCoordinate()][enemy.getYCoordinate()])
				{
					enemy.moveUp();
					validMove = true;
				}
				break;
			case 1:
				if(!south[enemy.getYCoordinate()][enemy.getXCoordinate()])
				{
					enemy.moveDown();
					validMove = true;
				}
				break;
			case 2:
				if(!west[enemy.getYCoordinate()][enemy.getXCoordinate()])
				{
					enemy.moveLeft();
					validMove = true;
				}
				break;
			case 3:
				if(!east[enemy.getYCoordinate()][enemy.getXCoordinate()])
				{
					enemy.moveRight();
					validMove = true;
				}
				break;
			default:
	    		break;
			}
		}

    }
    
    private void cleanPreviousPosition(Entity entity)
    {
    	StdDraw.setPenColor(StdDraw.WHITE);
    	StdDraw.filledCircle(entity.getYCoordinate() + 0.5, entity.getXCoordinate() + 0.5, 0.4);
        StdDraw.show();
    }
  
    private void drawEntity(Entity entity)
    {
    	
    	StdDraw.setPenColor(entity.color);
        StdDraw.filledCircle(entity.getYCoordinate() + 0.5, entity.getXCoordinate() + 0.5, 0.3);
        StdDraw.show();
    }
    
    public void startGame()
    {
    	while(true)
    	{
    		movePlayer();
    		drawEntity(player);
    		for(Enemy enemy : enemies)
    		{
    			moveEnemy(enemy);
        		drawEntity(enemy);
    		}
    	}
    }
    
    public void generateBots(int nrBots, int mazeSize)
    {
    	enemies = new ArrayList<Enemy>();
    	Enemy enemy;
    	for(int i=0; i < nrBots; i++)
    	{
    		enemy = new Enemy((int)(Math.random()*mazeSize), (int)(Math.random()*mazeSize));
            enemies.add(enemy);
    	}
    }
    
    public void generateStars(int mazeSize)
    {
    	double x,y;
    	
    	x = (int)(Math.random()*mazeSize) + 0.5;
    	y = (int)(Math.random()*mazeSize) + 0.5;
    	
    	for(int i=0; i < mazeSize * 2; i++)
    	{
    	
    		x = (int)(Math.random()*mazeSize) + 0.5;
        	y = (int)(Math.random()*mazeSize) + 0.5;
        	
    		while(x < 1 || x > mazeSize)
    		{
    			x = (int)(Math.random()*mazeSize) + 0.5;
    		}
    		
    		while(y < 1 || y > mazeSize)
    		{
    			y = (int)(Math.random()*mazeSize) + 0.5;
    		}
    		
    		StdDraw.picture(x, y, "star.png",0.8,0.8);	
    	}
    	
    }
    
    public static void main(String[] args) throws InterruptedException {
		NewGame ng = new NewGame();
		while (ng.getMazeSize() == null) {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
			}
		}
		int n = Integer.parseInt(ng.getMazeSize());
		System.out.println(ng.getBotsNo());

		Maze maze = new Maze(n);
		maze.generateStars(n);
		maze.generateBots(Integer.parseInt(ng.getBotsNo()), n);
		maze.setPlayerPosition(1, 1);
		
		// StdDraw.enableDoubleBuffering();
		maze.draw();
		maze.startGame();
	}
}
