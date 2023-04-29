package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane game = new Pane();

			Player player1 = new Player("player1", game);
			Player player2 = new Player("player2", game);

			//Fireball fireball = new Fireball(player2, game);
			player1.Movement(game);
			player2.Movement(game);
			
			game.setOnMousePressed(event -> {
				Fireball fireball = new Fireball(player2, game);
				fireball.CastFireball(player2, game, event.getX(), event.getY());
			});

			game.setStyle("-fx-background-color: lightgray;");
			primaryStage.setResizable(false);

			Scene scene = new Scene(game, 500, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
