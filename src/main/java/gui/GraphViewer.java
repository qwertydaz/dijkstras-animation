package src.main.java.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class GraphViewer extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		Line line = new Line();

		line.setStartX(0);
		line.setStartY(0);
		line.setEndX(100);
		line.setEndY(200);

		Group root = new Group();
		root.getChildren().add(line);

		// Creating an image
		Image img = new Image("https://media.geeksforgeeks.org/wp-content/uploads/20210224040124/JSBinCollaborativeJavaScriptDebugging6.png");

		// Setting the image view
		ImageView imgView = new ImageView(img);

		// Setting the position of the image
		imgView.setX(100);
		imgView.setY(100);

		// Setting the fit height and width of the image view
		imgView.setFitHeight(200);
		imgView.setFitWidth(400);

		// Setting the preserve ratio of the image view
		imgView.setPreserveRatio(true);

		Glow glow = new Glow();

		// Setting the level property
		glow.setLevel(0.9);

		imgView.setEffect(glow);

		root.getChildren().add(imgView);

		Scene scene = new Scene(root, 800, 600);

		primaryStage.setTitle("Graph Viewer");
		primaryStage.setScene(scene);
		primaryStage.show();

		scene.setOnMouseClicked(mouseEvent ->
		{
			if (mouseEvent.getButton() == MouseButton.SECONDARY)
			{

			}
		});
	}
}
