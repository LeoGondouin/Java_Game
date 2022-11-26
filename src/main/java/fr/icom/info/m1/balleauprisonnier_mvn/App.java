package fr.icom.info.m1.balleauprisonnier_mvn;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Classe principale de l'application 
 * s'appuie sur javafx pour le rendu
 */
public class App extends Application 
{
	
	/**
	 * En javafx start() lance l'application
	 *
	 * On cree le SceneGraph de l'application ici
	 * @see http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm
	 * 
	 */
	@Override
	public void start(Stage stage) throws Exception 
	{
		// Nom de la fenetre
        stage.setTitle("Balle au prisonnier");

        Group root = new Group();
        Scene scene = new Scene( root );

        // On cree le terrain de jeu et on l'ajoute a la racine de la scene
        Field gameField = Field.FieldSingleTonManager(scene, 600, 600);
        //Field gameField2 = Field.FieldSingleTonManager(scene, 500, 500 );
        //Field gameField3 = Field.FieldSingleTonManager(scene, 500, 500 );
        root.getChildren().add( gameField );
		root.getChildren().add(((Player)gameField.getJoueurs()[0]).sprite);
		root.getChildren().add(((Player)gameField.getJoueurs()[1]).sprite);

		root.getChildren().add(((Bot)gameField.getBots()[0]).sprite);
		root.getChildren().add(((Bot)gameField.getBots()[1]).sprite);
		
		root.getChildren().add(((Bot)gameField.getBots()[2]).sprite);
		root.getChildren().add(((Bot)gameField.getBots()[3]).sprite);
		root.getChildren().add(gameField.getslowProj().img);
		root.getChildren().add(gameField.getfastProj().img);
		//root.getChildren().add(gameField.getJoueurs()[2].sprite);
		//root.getChildren().add(gameField.getslowProj().sprite);	
        // On ajoute la scene a la fenetre et on affiche
        stage.setScene( scene );
        stage.show();
	}
	
    public static void main(String[] args) 
    {
        //System.out.println( "Hello World!" );
    	Application.launch(args);
    }
}
