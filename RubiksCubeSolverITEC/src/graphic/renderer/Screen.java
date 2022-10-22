package graphic.renderer;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class Screen extends Application {
	
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	private static PhongMaterial[] farben;
	

	@Override
	public void start(Stage primaryStage) {
		Box x = new Box(100, 100, 1);
		Box y = new Box(100, 100, 1);
		Box u = new Box(100, 100, 1);
		
		
		Box sphere = new Box(100, 100, 1);
		
		Transform a = new Rotate(1, new Point3D(0, 1, 0));
		sphere.getTransforms().add(a);
		
		sphere.setMaterial(farben[0]);
		
		
		
		

		Group group = new Group();
		group.getChildren().add(sphere);

		Camera camera = new PerspectiveCamera(true);
		Scene scene = new Scene(group, WIDTH, HEIGHT);
		scene.setFill(Color.BLACK);
		scene.setCamera(camera);

		camera.translateZProperty().set(-500);
		camera.translateYProperty().set(-300);
		camera.translateXProperty().set(-300);
		camera.getTransforms().add(new Rotate(45, Rotate.Y_AXIS));
		camera.getTransforms().add(new Rotate(45, new Point3D(1, 0, -1)));
		
		printCoords(camera);
		
		camera.setNearClip(1);
		camera.setFarClip(10000);

		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case U:
				// U
				sphere.setTranslateX(sphere.getTranslateX() + 10);
				break;
			case R:
				// R
				sphere.setTranslateY(sphere.getTranslateY() + 10);
				break;
			case F:
				// F
				sphere.setTranslateZ(sphere.getTranslateZ() + 10);
				break;
			case D:
				// D
				sphere.setTranslateX(sphere.getTranslateX() - 10);
				break;
			case L:
				// L
				sphere.setTranslateY(sphere.getTranslateY() - 10);
				break;
			case B:
				// B
				sphere.setTranslateZ(sphere.getTranslateZ() - 10);
				break;
			case P:
				printCoords(sphere);
				break;
			}
		});

		primaryStage.setTitle("RubiksCube");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	public static void anfangen() {
		Screen a = new Screen();
		// Farben definieren
		PhongMaterial U = new PhongMaterial();
		U.setDiffuseColor(Color.WHITE);
		
		PhongMaterial R = new PhongMaterial();
		R.setDiffuseColor(Color.RED);
		
		PhongMaterial F = new PhongMaterial();
		F.setDiffuseColor(Color.GREEN);
		
		PhongMaterial D = new PhongMaterial();
		D.setDiffuseColor(Color.YELLOW);
		
		PhongMaterial L = new PhongMaterial();
		L.setDiffuseColor(Color.ORANGE);
		
		PhongMaterial B = new PhongMaterial();
		B.setDiffuseColor(Color.BLUE);
		
		farben = new PhongMaterial[]{U, R, F, D, L, B};
		
		a.launch();
	}
	
	public void printCoords(Camera a) {
		System.out.println(a.getTranslateX() + ", " + a.getTranslateY() + ", " + a.getTranslateZ());
	}
	
	public void printCoords(Box a) {
		System.out.println(a.getTranslateX() + ", " + a.getTranslateY() + ", " + a.getTranslateZ());
	}

}
