import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;

public class StartWorld implements IWorld {

    @Override
    public IWorld update() {
        return this;
    }
    
    public void draw(PApplet p, String img, int x, int y, int w, int h) {
    	
    	PImage image = p.loadImage(img);
    	p.imageMode(p.CENTER);
    	p.image(image, x, y, 100, 70);
    	
    }

    @Override
    public PApplet draw(PApplet p) {
    	
        p.background(0,125,225);
        // draw hero
        draw(p, "super-hero.png", 200, 110, 280, 160);
        // draw fire
        draw(p, "fire-ball.png", 40, 40, 60, 40);
        draw(p, "fire-ball.png", 220, 350, 60, 40);
        draw(p, "fire-ball.png", 330, 150, 60, 40);
        // draw clouds
        draw(p, "cloud.png", 85, 100, 100, 70);
        draw(p, "cloud.png", 60, 115, 100, 70);
        draw(p, "cloud.png", 270, 235, 100, 70);
        // draw text
        p.textSize(20);
        p.text("Super Dash", 160, 200);
        p.text("Press SPACEBAR To Start", 100, 350);
        
        return p;
        
    }

    @Override
    public IWorld keyPressed(KeyEvent kev) {
    	
        if (kev.getKey() == ' ') {
            return  new SuperDashWorld(new Player(new Posn(50, 200)), new MTLoO(), 0);
        } else {
            return this;
        }
        
    }

} 
