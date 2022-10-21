package graphic.renderer;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class Screen extends Application {
	
	private final int WIDTH = 800;
	private final int HEIGHT = 600;

	@Override
	public void start(Stage primaryStage) {
		Sphere sphere = new Sphere(50);

		Group group = new Group();
		group.getChildren().add(sphere);

		Camera camera = new PerspectiveCamera(true);
		Scene scene = new Scene(group, WIDTH, HEIGHT);
		scene.setFill(Color.SILVER);
		scene.setCamera(camera);

		camera.translateZProperty().set(-500);

		camera.setNearClip(1);
		camera.setFarClip(1000);

		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case U:
				// U
				System.out.println("U");
				break;
			case R:
				// R
				System.out.println("R");
				break;
			case F:
				// F
				System.out.println("F");
				break;
			case D:
				// D
				break;
			case L:
				// L
				break;
			case B:
				// B
				break;
			}
		});

		primaryStage.setTitle("Genuine Coder");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	public static void anfangen() {
		Screen a = new Screen();
		a.launch();
	}

}
