import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * Represents an interactive application where a drop of
 * water falls down from the top of the window. If the 
 * user clicks the mouse, the drop is moved over to the
 * location of the click;
 */
public class SuperDashWorld  implements IWorld {
	
    // the position of the drop
    private Player p1;
    private ILoO obs;
    private double generateRate = 0.05;  // the probability of a new obstacle appearing on each update
    private int ticks;
    private int highScore;

    public SuperDashWorld(Player p1, ILoO obs, int ticks) {
		super();
		this.p1 = p1;
		this.obs = obs;
		this.ticks = ticks;
		readHighScore() ;
	}

	/** 
     * Updates the state of this world
	 * @throws FileNotFoundException 
     */
    public IWorld update() {
    	
    	if ( this.obs.anyCollided(p1.getLoc()) ) {
    		if ( score() > this.highScore ) {
    			this.highScore = score();
    			this.writeHighScore();
    		}
    		return new EndWorld(this ,obs);
    	} else if ( Math.random() < this.generateRate ) {
    		return new SuperDashWorld(p1.update(), new ConsLoO(new Obstacle(), this.obs), this.ticks + 1);
    	} else {
    		return new SuperDashWorld(p1.update(), this.obs.update().removeEscaped(), this.ticks + 1);
    	}
    	
    }
    
    
    /** computes the score based on the current ticks */
    public int score() {
    	return ticks / 30;
    }
    
    /** 
     *  Records the given score value for the given
     *  tick
     */
    public void recordScore(int highScore) {
        if (highScore < 0) {
            throw new IllegalArgumentException("invalid hour " + highScore);
        }
        this.highScore = highScore;
        //this.readings[highScore] = highScore;
    }
    
    /**
     * Returns true if there is a temperature 
     * recorded for the given hour
     *
    public boolean hasScore(int score) {
    	return this.readings[score] >= 0;
    } */
    
    /* writes out the current this.highScore value to the output file */
    public void writeHighScore()  {
    	try {
	    	// standard output    ==== "console/terminal" display on the user screen
	    	System.out.println("Processing High Score");
	    	
	    	// open an output file
	    	PrintWriter pw = new PrintWriter( new File("leaderboard.txt") );
	    	
	    	// write out a summary of highScore data to the output file
	    	pw.println( this.highScore );
	    	
	    	/*&
	    	for ( int score = 0 ; score < 24 ; score ++) {
	    		SuperDashWorld sdw = new SuperDashWorld( p1.update(), new ConsLoO(new Obstacle(), this.obs), this.ticks );;
				if ( sdw.hasScore(highScore) ) {
					pw.println( "" + highScore );
				}
			}
			*/
	
	    	pw.close();
    	} catch (FileNotFoundException exp) {
    		System.out.println("problem saving high score");
    	}
    }


    /* opens the high score file and reads its contents into this.highScore */
    public void readHighScore()  {

    	//SuperDashWorld sdw = new SuperDashWorld( p1.update(), new ConsLoO(new Obstacle(), this.obs), this.ticks );
    	
    	try {
	    	String fileName = "leaderboard.txt";
	
	    	// open the data file
	    	Scanner sc = new Scanner( new File(fileName) );
	    	
	    	this.highScore = sc.nextInt();
	    	
	    	/*
	
	    	// read in lines of the file and record in bdr
	    	while( sc.hasNextInt() ) {
	    		int score = sc.nextInt();
	    		sdw.recordScore(score);
	    	}
	    	*/
	    	
	    	sc.close();
    	} catch (FileNotFoundException exp) {
    		System.out.println("high score not loaded for some reason.");
    		return ; 
    	}
    }



    /**
     * Renders a picture of the drop on the window
     */
    public PApplet draw(PApplet p) {
    	p.imageMode(PApplet.CENTER);
    	PImage img = p.loadImage("super-hero.png");
    	p.background(0,125,225);
    	p.textSize(20);
    	p.text("Super Dash", 160, 25);
    	p.fill(255);
    	p.image(img, p1.getX(p1.getLoc()), p1.getY(p1.getLoc()), 50, 40);

    	this.obs.draw(p);

    	p.fill(0);
    	p.text( this.score(), 50, 300);
    	p.text( this.highScore, 50, 330);

    	return p;
    }

    public IWorld keyPressed(KeyEvent kev) {

    	if (kev.getKeyCode() == PApplet.UP && p1.getLoc().getY() > 0) {
    		
    		if ( p1.getSpeed() > 0 ) {
    			p1.setSpeed(-p1.getSpeed());
    			return new SuperDashWorld(this.p1.addToSpeed(-1), this.obs, this.ticks);
    		} else {
    			return new SuperDashWorld(this.p1.addToSpeed(-1), this.obs, this.ticks);
    		}
            
    	} else if (kev.getKeyCode() == PApplet.DOWN && p1.getLoc().getY() < 400) {  // means the player is going DOWN currently
    		
    		if ( p1.getSpeed() < 0 ) {
    			p1.setSpeed(-p1.getSpeed());
    			return new SuperDashWorld(this.p1.addToSpeed(1), this.obs, this.ticks);
    		} else {
    			return new SuperDashWorld(this.p1.addToSpeed(1), this.obs, this.ticks);
    		}
    		
        } else if (kev.getKey() == 'p') {
        	
        	return new PauseWorld(this);
        
        } else if (this.obs.anyCollided(p1.getLoc())) { 
        	
        	return new EndWorld(this, obs);
       
        /*	
        } else if (kev.getKey() == 'p' && this.obs.anyCollided(p1.getLoc())) {
        	
        	return new SuperDashWorld(new Player(new Posn(50, 200)), new MTLoO(), 0);
        	
        */
        } else {
        	
            return this;
            
        }
    	
    }

	@Override
	public int hashCode() {
		return Objects.hash(generateRate, obs, p1);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuperDashWorld other = (SuperDashWorld) obj;
		return Double.doubleToLongBits(generateRate) == Double.doubleToLongBits(other.generateRate)
				&& Objects.equals(obs, other.obs) && Objects.equals(p1, other.p1);
	}

	@Override
	public String toString() {
		return "SuperDashWorld [p1=" + p1 + ", obs=" + obs + ", generateRate=" + generateRate + "]";
	}
	
}