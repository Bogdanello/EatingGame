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
        enemies = new ArrayList<Enemy>();
        Enemy enemy1 = new Enemy(n/2,n/2);
        Enemy enemy2 = new Enemy(n/3,n/3);
        enemies.add(enemy1);
        enemies.add(enemy2);
        
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

/*
        // delete some random walls
        for (int i = 0; i < n; i++) {
            int x = 1 + StdRandom.uniform(n-1);
            int y = 1 + StdRandom.uniform(n-1);
            north[x][y] = south[x][y+1] = false;
        }

        // add some random walls
        for (int i = 0; i < 10; i++) {
            int x = n/2 + StdRandom.uniform(n/2);
            int y = n/2 + StdRandom.uniform(n/2);
            east[x][y] = west[x+1][y] = true;
        }
*/
     
    }

    // solve the maze using depth-first search
    private void solve(int x, int y) {
        if (x == 0 || y == 0 || x == n+1 || y == n+1) return;
        if (done || visited[x][y]) return;
        visited[x][y] = true;

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        //StdDraw.pause(30);

        // reached middle
        if (x == n/2 && y == n/2) done = true;

        if (!north[x][y]) solve(x, y + 1);
        if (!east[x][y])  solve(x + 1, y);
        if (!south[x][y]) solve(x, y - 1);
        if (!west[x][y])  solve(x - 1, y);

        if (done) return;

        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        //StdDraw.pause(30);
    }

    // solve the maze starting from the start state
    public void solve() {
        for (int x = 1; x <= n; x++)
            for (int y = 1; y <= n; y++)
                visited[x][y] = false;
        done = false;
        solve(1, 1);
    }

    // draw the maze
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(n/2.0 + 0.5, n/2.0 + 0.5, 0.375);
        StdDraw.filledCircle(1.5, 1.5, 0.375);
        StdDraw.filledCircle(2.5, 1.5, 0.375);

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
    		//drawEntity(enemy);
    		for(Enemy enemy : enemies)
    		{
    			moveEnemy(enemy);
        		drawEntity(enemy);
    		}
    	}
    }
    
=======
    public static void main(String[] args) throws InterruptedException {
		NewGame ng = new NewGame();
		while (ng.getMazeSize() == null) {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
			}
		}
		int n = Integer.parseInt(ng.getMazeSize());
		//System.out.println(ng.getLevel());
		//System.out.println(ng.getBotsNo());
		// start game btn, nb of bots, maze size, dificulty lvl buttons/dropdown

		Maze maze = new Maze(n);
		maze.setPlayerPosition(1, 1);

		// StdDraw.enableDoubleBuffering();
		maze.draw();
		maze.startGame();
		// maze.solve();
	}
>>>>>>> 08fa1f11aa85b83f4e27cbdfbef3bb0b652c1f86

}
