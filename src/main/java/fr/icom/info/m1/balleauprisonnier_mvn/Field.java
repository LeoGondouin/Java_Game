package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.ArrayList;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import fr.icom.info.m1.balleauprisonnier_mvn.ProjectileFactory;
import fr.icom.info.m1.balleauprisonnier_mvn.slowProj;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import java.util.logging.*;
/**
 * Classe gerant le terrain de jeu.
 * 
 */
public class Field extends Canvas {
	
	/** Joueurs */
	private static Field instance;
	Player [] joueurs = new Player[2];
	Bot [] bots = new Bot[4];
	slowProj slowProj;
	fastProj fastProj;
	/** Couleurs possibles */
	String[] colorMap = new String[] {"blue", "green", "orange", "purple", "yellow"};
	/** Tableau traçant les evenements */
    ArrayList<String> input = new ArrayList<String>();
    

    final GraphicsContext gc;
    final int width;
    final int height;
    /**
     * Canvas dans lequel on va dessiner le jeu.
     * 
     * @param scene Scene principale du jeu a laquelle on va ajouter notre Canvas
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
	private Field(Scene scene, int w, int h) 
	{
		super(w, h); 
		width = w;
		height = h;
		/** permet de capturer le focus et donc les evenements clavier et souris */
		this.setFocusTraversable(true);
		ProjectileFactory pf = new ProjectileFactory();
        gc = this.getGraphicsContext2D();
        /** On initialise le terrain de jeu */
    	joueurs[0] = new Player(gc, colorMap[0], w/2, h-50, "bottom");
    	joueurs[0].display();

    	joueurs[1] = new Player(gc, colorMap[1], w/2, 20, "top");
    	joueurs[1].display();
    	
    	bots[0] = new Bot(gc, colorMap[1], w/3, 20, "top");
    	bots[0].display();
    	
    	bots[1] = new Bot(gc, colorMap[1], w/4, 20, "top");
    	bots[1].display();
    	
    	bots[2] = new Bot(gc, colorMap[1], w/3, h-50, "bottom");
    	bots[2].display();
    	
    	bots[3] = new Bot(gc, colorMap[1], w/4, h-50, "bottom");
    	bots[3].display();
    	
    	String[] arrType = new String[]{"fast","slow"};
    	
    	Random r = new Random();
    	int randN = r.nextInt(0,2);
    	System.out.print(r.nextInt(0,2));
    	this.slowProj=(slowProj)pf.createproj("slow",joueurs[randN].x,joueurs[randN].y+50,joueurs[0].angle);  
    	this.fastProj=(fastProj)pf.createproj("fast",joueurs[randN].x,joueurs[randN].y+50,joueurs[randN].angle);    
    	if(0==1) {
    		fastProj.x=joueurs[1].x;
    		fastProj.y=joueurs[1].y; 
    		fastProj.side=joueurs[1].side;
    		joueurs[1].ballType = "fast";
    		fastProj.adjustBall();
    		
    		slowProj.x=joueurs[0].x;
    		slowProj.y=joueurs[0].y; 
    		slowProj.side=joueurs[0].side;
    		joueurs[0].ballType = "slow";
    		slowProj.adjustBall();
    	
    	}
    	else {
    		fastProj.x=joueurs[0].x;
    		fastProj.y=joueurs[0].y;
    		fastProj.side=joueurs[0].side;
    		joueurs[0].ballType = "fast";
    		fastProj.adjustBall();
    		
    		slowProj.x=joueurs[1].x;
    		slowProj.y=joueurs[1].y; 
    		slowProj.side=joueurs[1].side;
    		joueurs[1].ballType = "slow";
    		slowProj.adjustBall();
    		/*
    		slowProj.x=joueurs[0].x;
    		slowProj.y=joueurs[0].y; 	
    		slowProj.img.setX(joueurs[0].x+30);
    		slowProj.img.setY(joueurs[0].y-10);*/
    		/*slowProj.x=joueurs[0].x;
    		slowProj.y=joueurs[0].y; */	
    		//slowProj.adjustBall(joueurs[0].side);
    	}
    	
	    /** 
	     * Event Listener du clavier 
	     * quand une touche est pressee on la rajoute a la liste d'input
	     *   
	     */
	    this.setOnKeyPressed(
	    		new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            // only add once... prevent duplicates
	    	            if ( !input.contains(code) ) {
	    	                input.add( code );
	    	            	System.out.print(e.getCode().toString());
	    	            }
	    	        }
	    	    });

	    /** 
	     * Event Listener du clavier 
	     * quand une touche est relachee on l'enleve de la liste d'input
	     *   
	     */
	    this.setOnKeyReleased(
	    	    new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            input.remove( code );
	    	        }
	    	    });
	    
	    /** 
	     * 
	     * Boucle principale du jeu
	     * 
	     * handle() est appelee a chaque rafraichissement de frame
	     * soit environ 60 fois par seconde.
	     * 
	     */
	    new AnimationTimer() 
	    {
	        public void handle(long currentNanoTime)
	        {	 
	            // On nettoie le canvas a chaque frame
	            gc.setFill( Color.LIGHTGRAY);
	            gc.fillRect(0, 0, width, height);
	        
	            // Deplacement et affichage des joueurs
	        	for (int i = 0; i < joueurs.length; i++) 
	    	    {
	        		if (i==0 && input.contains("LEFT"))
	        		{
	        			joueurs[i].moveLeft();
	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[2].moveLeft();
	        				bots[3].moveLeft();
		        		} 
	    	    	}
	        		if (i==0 && input.contains("RIGHT")) 
	        		{
	        			joueurs[i].moveRight();
	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[2].moveRight();
	        				bots[3].moveRight();
		        		} 
	        		}
	        		if (i==0 && input.contains("UP"))
	        		{
	        			joueurs[i].turnLeft();
	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[2].turnLeft();
	        				bots[3].turnLeft();
		        		} 
	        		} 
	        		if (i==0 && input.contains("DOWN")) 
	        		{
	        			joueurs[i].turnRight();
	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[2].turnRight();
	        				bots[3].turnRight();
		        		}        			
	        		}
	        		if (i==0 && input.contains("NUMPAD0")){
	        			joueurs[i].shoot();
	        			//Timer chrono = new Timer();
	        			if((fastProj.x>=0 && fastProj.x<=w)&&(fastProj.y>=0 && fastProj.y<=h)){
	        				//chrono.schedule(new TimerTask(){
	        					//@Override
	        					//public void run() {
	        						fastProj.throw_();	
	        					//}
	        				//},1000);

	        			}
	        			else {
	        				respawnBall("fast");
	        			}
	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[2].shoot();
	        				bots[3].shoot();
		        		}   
	        			//String randType = arrType[r.nextInt(0,2)];
	        			//System.out.print(randType);
	        			//slowProj ok = (slowProj)pf.createproj("slow",joueurs[i].x,joueurs[i].y+50,joueurs[i].angle);
	        			for(int y=0;y<10;y++) {
	        				//ok.throw_();
		        			//System.out.print(ok.y);
	        			}

					}
	        		if (i==0 && input.contains("ADD")) 
	        		{
	        			joueurs[i].FollowStrat();
					}
	        		if (i==0 && input.contains("SUBTRACT")) 
	        		{
	        			joueurs[i].BlendStrat();
					}
	        		if (i==0 && joueurs[i].strat.equals("blend")){
	        			
	        			bots[2].randomMoves();
	        			bots[3].randomMoves();
					}
	        		if (i==1 && input.contains("Q"))
	        		{
	        			joueurs[i].moveLeft();
	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[0].moveLeft();
	        				bots[1].moveLeft();
		        		}   
	        		} 
	        		if (i==1 && input.contains("D")) 
	        		{
	        			joueurs[i].moveRight();
	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[0].moveRight();
	        				bots[1].moveRight();
		        		}       			
	        		}
	        		if (i==1 && input.contains("Z"))
	        		{
	        			joueurs[i].turnLeft();
	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[0].turnLeft();
	        				bots[1].turnLeft();
		        		}    
	        		} 
	        		if (i==1 && input.contains("S")) 
	        		{
	        			joueurs[i].turnRight();
	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[0].turnRight();
	        				bots[1].turnRight();
		        		}              			
	        		}
	        		if (i==1 && input.contains("SPACE")){
	        			joueurs[i].shoot();
	        			if(joueurs[i].ballType!=null && joueurs[i].ballType.equals("slow")) {
		        			if((slowProj.x>=0 && slowProj.x<=w)&&(slowProj.y>=0 && slowProj.y<=h)){
		        				slowProj.throw_();
		        			}
		        			else {
		        				respawnBall("slow");
		        			}
	        			}
	        			else if(joueurs[i].ballType!=null && joueurs[i].ballType.equals("fast")) {
		        			if((fastProj.x>=0 && fastProj.x<=w)&&(fastProj.y>=0 && fastProj.y<=h)){
		        				fastProj.throw_();
		        			}
		        			else {
		        				respawnBall("fast");
		        			}
	        			}
	        			//System.out.print(arrType[r.nextInt(0,2)]);
	        			if(joueurs[i].strat.equals("follow")) {
	        				bots[0].shoot();
	        				bots[1].shoot();
		        		}   
					}
	        		if (i==1 && input.contains("E")){
	        			joueurs[i].FollowStrat();
					}
	        		if (i==1 && input.contains("A")){
	        			joueurs[i].BlendStrat();
					}
	        		if (i==1 && joueurs[i].strat.equals("blend")){
	        			for(int n=0;n<21;n++) {
	        				if(n==20) {
			        			bots[0].randomMoves();
			        			//System.out.print(bots[0].x+"\n");
			        			//System.out.print(bots[0].x);
			        			bots[1].randomMoves();
	        				}
	        			}
	        		}
	        		
	        		joueurs[i].display();	        		
	    	    }
        		
        		for(int j = 0;j<bots.length;j++) {
        			bots[j].display();
        		}
	    	}
	     }.start(); // On lance la boucle de rafraichissement 
	     
	}
    public static Field FieldSingleTonManager(Scene scene, int w, int h) {
        if (instance == null) {
            instance = new Field(scene, w, h);
        }
        else {
        	Logger logger = Logger.getLogger(Field.class.getSimpleName());
        	logger.warning("Votre objet n\'a pas ete cree car une instance existe deja !");
        }
        return instance;
    	
    }
    
    public void respawnBall(String type){
    	if(type=="slow") {
    		if(slowProj.side.equals("bottom")){
        		slowProj.x=joueurs[1].x;
        		slowProj.y=joueurs[1].y;
        		slowProj.side=joueurs[1].side;
    			joueurs[1].ballType = "";
        		slowProj.adjustBall();
    		}
    		else{
        		slowProj.x=joueurs[0].x;
        		slowProj.y=joueurs[0].y;
        		slowProj.side=joueurs[0].side;
    			joueurs[0].ballType = "";
        		slowProj.adjustBall();
    		}
    	}
    	else {
    		if(fastProj.side.equals("bottom")){
        		fastProj.x=joueurs[1].x;
        		fastProj.y=joueurs[1].y;
        		fastProj.side=joueurs[1].side;
        		joueurs[1].ballType = "";
        		fastProj.adjustBall();
    		}
    		else{
    			fastProj.x=joueurs[0].x;
    			fastProj.y=joueurs[0].y;
    			fastProj.side=joueurs[0].side;
    			joueurs[0].ballType = "";
    			fastProj.adjustBall();
    		}
    	}
    }

	public Player[] getJoueurs() {
		return joueurs;
	}
	public Bot[] getBots() {
		return bots;
	}
	public slowProj getslowProj() {
		return slowProj;
	}
	public fastProj getfastProj() {
		return fastProj;
	}
}
