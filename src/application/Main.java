package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	Pane game = new Pane();
	Player player1 = new Player("player1", game);
	Player player2 = new Player("player2", game);
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Fireball fireball = new Fireball(player2, game);
			player1.Movement(game);
			player2.Movement(game);
			
			game.setOnMousePressed(event -> {
				Fireball fireball = new Fireball(player2, game);
				fireball.CastFireball(player2, player1, game, event.getX(), event.getY());
			});
			
			if (player1.getHealth() <= 0) {
				Text win = new Text("Player 1 Wins");
				player1.playerView.setVisible(false);
				game.getChildren().add(win);
			}
			if (player2.getHealth() <= 0) {
				Text win = new Text("Player 2 Wins");
				player1.playerView.setVisible(false);
				game.getChildren().add(win);
			}

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
