package FinalGame;
import java.awt.Color;
import hsa2.GraphicsConsole;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import java.awt.Font;
import java.util.ArrayList;





public class SnakeGame { 
	public final static int WIDTH = 25;
	public final static int HEIGHT = 25;
	public final static int SIZE = 18;
	private boolean [][] rocks = new boolean[WIDTH][HEIGHT];
	GraphicsConsole gc = new GraphicsConsole(WIDTH*SIZE,HEIGHT*SIZE);
	GraphicsConsole gcIntro = new GraphicsConsole(600,600);
	//private int dots;
	public int[] snakeXlength = new int[WIDTH*SIZE];
	public int[] snakeYlength = new int[HEIGHT*SIZE];
	public static final int UP = 1;
	public static final int DOWN = -1;
	public static final int LEFT = 2;
	public static final int RIGHT = -2;
	public static final int dir = 0;
	//private LinkedList<Point> body = new LinkedList<Point>();
	private int length = 3; //initial length
	private int Direction = RIGHT;
	private int sleepTime;
	private int score = 0;
	//private Point oldTail;
	//private int moves = 0;
	//private boolean right = false;
	//private boolean left = false;
	//private boolean up = false;
	//private boolean down = false;
	//private Image apple;
	
   private boolean dead = false;
	
	
	private int foodx ;
	private int foody ;
	
	
    
	public static void main(String[] args) {
		new SnakeGame();
		

	}

	SnakeGame(){
		Intro();
		GamePanel();
		generateRocks();
		createSnake();
		
		while(true) {
			KeyTyped();
			
			checkEatFood();
			moveSnake();
			checkEatBody();
			if (dead) break;
			//all drawing
		  
			synchronized(gc){
				
				drawSnake(); //this must be the first draw method because it clears the screen
				drawFood();
				drawRocks();
			}
			
			gc.sleep(100);
		}
		
	}

	void Intro() {
		gcIntro.setTitle("Home Page");
		gcIntro.setAntiAlias(true);
		
		gcIntro.setFont(new Font("Times New Roman",Font.ITALIC,20));
		gcIntro.drawString("The Game is here.", 50,50);
		gcIntro.drawString("Click Mouse to start the game",50,150);
		gcIntro.drawString("The player controls a dot, square, or object on a bordered plane",50,100);
		gcIntro.drawString("Use arrow key to control the direction of snake",50,200);
		gcIntro.setFont(new Font("Arial",Font.BOLD,30));
		gcIntro.drawString("Good luck", 50, 250);
		
		
		gcIntro.enableMouse();
		gc.setVisible(false);
		while(true) {
			if (gcIntro.getMouseClick() > 0) break;			
			gcIntro.sleep(100);
		}
		//hide intro, show main screen
		gcIntro.setVisible(false);
		gc.setVisible(true);
		moveFood();
	}

	void GamePanel() {
		gc.setAntiAlias(true);
		gc.setLocationRelativeTo(null);	
		gc.setTitle("Snake Game");
		gc.setBackgroundColor(new Color(100,100,250,200));
		
		//foodx = 100;
		//foody = 100;
		gc.clear();
	}

	void generateRocks() {
		for(int x = 0; x<WIDTH;x++)
			rocks[x][0] = rocks[x][HEIGHT-1] = true;
		for(int y=0; y<HEIGHT; y++)
			rocks[0][y] = rocks[WIDTH-1][y] = true;
	}
	void drawRocks() {
		//Then draws rocks
		gc.setColor(Color.DARK_GRAY);
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				if(rocks[x][y] == true) {
					gc.fill3DRect(x*SIZE, y*SIZE, SIZE, SIZE, true);
				}
			}
		}
	}


	void createSnake() {
		for(int i = 0; i < length; i++) {
			snakeXlength[i] = 18 - i*SIZE;
			snakeYlength[i] = 18;
			
		}
		
	}



	void KeyTyped() {
		if(gc.getKeyCode() == KeyEvent.VK_UP ) {
			if (Direction != DOWN) 
				Direction = UP;			 
		}
		if(gc.getKeyCode() == KeyEvent.VK_DOWN ){
			if(Direction != UP)
			Direction = DOWN;
		
		}
		if(gc.getKeyCode() == KeyEvent.VK_RIGHT ){
			if(Direction != LEFT)
			Direction = RIGHT;
		}
		if(gc.getKeyCode() == KeyEvent.VK_LEFT ) {
			if(Direction != RIGHT)
			Direction = LEFT;
		}

	}
	void moveSnake() {
		for(int i = length-1; i > 0; i--) {
			snakeXlength[i] = snakeXlength[i-1];
			snakeYlength[i] = snakeYlength[i-1];
			
		}//to move the whole part

		if(Direction == RIGHT ) {//only move the head
			snakeXlength[0] = snakeXlength[0] + SIZE;
			if(snakeXlength[0] > WIDTH*SIZE - SIZE){
                //snakeXlength[0] = SIZE;
				gc.showDialog("Lose", "Game Over");	
				dead = true;
						
			}
		}
		if(Direction == LEFT ) {
			snakeXlength[0] = snakeXlength[0] - SIZE;
			if(snakeXlength[0] < SIZE){
				//snakeXlength[0] = WIDTH*SIZE - SIZE;
				gc.showDialog("Lose", "Game Over");	
				dead = true;
			}
		}
		if(Direction == UP ) {
            snakeYlength[0] = snakeYlength[0] - SIZE;
            if(snakeYlength[0] < SIZE ){
            	//snakeYlength[0] = HEIGHT*SIZE - SIZE;
            	gc.showDialog("Lose", "Game Over");	
				dead = true;
                
            }
		}
		if(Direction == DOWN ) {
            snakeYlength[0] = snakeYlength[0] + SIZE;
            if(snakeYlength[0] > HEIGHT*SIZE){
            	//snakeYlength[0] = SIZE;
            	gc.showDialog("Lose", "Game Over");	
				dead = true;
            	
            }
		}
		
	}

	void drawSnake(){
			gc.clear();
			
			for(int i = 0; i < length; i++) {
				
				if(i == 0) { 
					gc.setColor(Color.RED);
					gc.fillOval(snakeXlength[i], snakeYlength[i], SIZE, SIZE);
				}else {
					gc.setColor(Color.YELLOW);
					gc.fillOval(snakeXlength[i], snakeYlength[i], SIZE, SIZE);
				}
					
			}
			
			//for (int i=0; i<WIDTH; i++) {
				//gc.drawLine(0, i*SIZE, 800, i*SIZE);
				//gc.drawLine(i*SIZE, 0, i*SIZE,800);
			//}
	}	
     
	void drawFood(){
       
		gc.setColor(Color.GREEN);
		gc.fillRect(foodx, foody, SIZE, SIZE);
		
	}
	
	void moveFood(){
		
		foodx = ((int)(Math.random()*23)+1)*SIZE;
		foody = ((int)(Math.random()*23)+1)*SIZE;
		//foodx = 1*SIZE;
		//foody = 1*SIZE;
	}
	
	void checkEatFood(){
		//check if the snake eats food
		if((snakeXlength[0] == foodx) && (snakeYlength[0] == foody)){
			length++;
			score++;
			
			//gc.setCursor(2, 4);
			gc.drawString("Score" + score, 10, 10);
			gc.setColor(Color.WHITE);
			gc.clear();
			//for(int i = 0; i < length; i++){
				//snakeXlength[i] =  snakeXlength[0] ;
				//snakeYlength[i] = snakeYlength[0] ;			
			//}
			//create new piece of snake
			moveFood();
		}
		//moveFood();
	}
	void checkEatBody(){ // check if the snake eats its own body
		for(int i = length; i > 0; i--) {
			if((i > 4)&&(snakeXlength[0] == snakeXlength[i]) && (snakeYlength[0] == snakeYlength[i])) {
				gc.showDialog("Lose", "Game Over");	
				dead = true;
			}
		}
	}
	

}
 
