package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	Pane game = new Pane();
	Player player1 = new Player("player1", game);
	Player player2 = new Player("player2", game);
	
	public void createFireball(double x, double y, Networking n) {
		Fireball fireball = new Fireball(player2, game);
		fireball.CastFireball(player2, player1, game, x, y, n);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			TextInputDialog getAddr = new TextInputDialog();
			getAddr.setHeaderText(null);
			getAddr.setTitle(null);
			getAddr.setContentText("Enter the IP address of the player's computer you wish to compete with: ");
			Optional<String> addr = getAddr.showAndWait();
			String name = addr.toString();
			Networking net = new Networking(name);
			//Fireball fireball = new Fireball(player2, game);
			player1.Movement(game, net);
			player2.Movement(game, net);
			try {
				double[] pos = net.recieveFireball();
				createFireball(pos[0], pos[1], net);
			} catch (Exception e) {
				
			}
			
			game.setOnMousePressed(event -> {
				createFireball(event.getX(), event.getY(), net);
//				Fireball fireball = new Fireball(player2, game);
//				fireball.CastFireball(player2, player1, game, event.getX(), event.getY(), net);
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
