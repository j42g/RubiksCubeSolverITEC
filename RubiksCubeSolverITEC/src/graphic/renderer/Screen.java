package graphic.renderer;

import javafx.*;

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
			case W:
				camera.translateZProperty().set(camera.getTranslateZ() + 100);
				break;
			case S:
				camera.translateZProperty().set(camera.getTranslateZ() - 100);
				break;
			}
		});

		primaryStage.setTitle("Genuine Coder");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
