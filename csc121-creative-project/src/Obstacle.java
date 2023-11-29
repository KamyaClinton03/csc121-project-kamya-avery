import java.util.Objects;
import java.util.Random;  // RandomNumberGenerator

import processing.core.*;
import processing.event.KeyEvent;

import processing.core.PApplet;

public class Obstacle {
	
	private Posn loc;
	
	
	public Obstacle(Posn loc) {
        this.loc = loc;
    }
	
	/* constructs a randomly-placed obstacle */
	public Obstacle() {
		Random rgen = new Random();
		this.loc = new Posn( 400,  rgen.nextFloat(20, 380) );
	}

    /**
     * Renders a picture of the drop on the window
     */
    public PApplet draw(PApplet p) {
    	
    	PImage img = p.loadImage("fire-ball.png");
    	p.image(img, (int)this.loc.getX(), (int)this.loc.getY(), 40, 30);
        //p.fill(255);
        //p.circle((int)this.loc.getX(), (int)this.loc.getY(), 15);
        return p;
        
    }
    
    /**
     * Produces an updated world where the obstacle moves
     * across the screen a little bit, if it hasn't hit 
     * the left side of the screen yet.
     */
    
    public Obstacle update() {        
    	
    	if (this.loc.getX() > -30) {
            return new Obstacle(this.loc.translate(new Posn(-2f, 0)));
        } else {
            return this;
        }
    	
    }
    
    public Posn getLoc() {
		
		return this.loc;
		
	}
    
    public int getX(Posn loc) {

		return (int) loc.getX();

	}

	public int getY(Posn loc) {

		return (int) loc.getY();

	}
	
    public boolean anyCollide( Posn p ) {
    	return this.loc.distanceTo(p) < 25;
		
    	/*
		return p.getX() <= (this.getX(this.getLoc()) + 5) &&
			   p.getX() >= (this.getX(this.getLoc()) - 5) &&
			   p.getY() <= (this.getY(this.getLoc()) + 5) &&
			   p.getY() >= (this.getY(this.getLoc()) - 5);
			   */
		
	}

    @Override
    public int hashCode() {
        return Objects.hash(loc);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Obstacle other = (Obstacle) obj;
        return Objects.equals(loc, other.loc);
    }

    @Override
    public String toString() {
        return "Obstacle [loc=" + loc + "]";
    }
    

}
