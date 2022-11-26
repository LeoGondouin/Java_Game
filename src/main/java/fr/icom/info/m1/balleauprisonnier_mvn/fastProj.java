package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.Random;

import fr.icom.info.m1.balleauprisonnier_mvn.Projectile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * 
 * Classe gerant un joueur
 *
 */
public class fastProj extends Projectile
{
	String side;
	public fastProj(double xpos, double ypos,double direction){
		super(new ImageView("assets/ball_small.png"), xpos, ypos, direction);
	}
	public void adjustBall() {
		if(this.side.equals("bottom")) {
			this.x+=30;
			this.y+=32;
			this.img.setX(this.x);
			this.img.setY(this.y);
		}
		else {
			this.x+=7;
			this.y+=33;
			this.img.setX(this.x);
			this.img.setY(this.y);			
		}
	}
	public void throw_() {
		if(this.side.equals("bottom")) {
			this.y-=8;
			this.img.setY(this.y);
			System.out.print(this.y);
		}
		else {
			this.y+=8;
			this.img.setY(this.y);
			System.out.print(this.y);			
		}
	}	
	
}
