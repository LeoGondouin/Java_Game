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
public class slowProj extends Projectile
{
	String side;
	public slowProj(double xpos, double ypos,double direction){
		super(new ImageView("assets/tennis_ball.png"), xpos, ypos, direction);
	}
	public void adjustBall() {
		if(this.side.equals("bottom")) {
			this.x+=35;
			this.y+=27;
			this.img.setX(this.x);
			this.img.setY(this.y);
		}
		else {
			//this.x;
			this.x-=5;
			this.y+=27;
			this.img.setX(this.x);
			this.img.setY(this.y);			
		}
	}
	public void throw_() {
		if(this.side.equals("bottom")) {
			this.y-=4;
			this.img.setY(this.y);
		}
		else {
			this.x+=(double)(Math.cos(angle*Math.PI/180) * 4);	
			this.y+=(double)(Math.cos(angle*Math.PI/180) * 4);	
			this.img.setY(this.x);
			this.img.setY(this.y);
		}
	}	
}
