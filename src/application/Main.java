package application;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	static Pane game = new Pane();
	static Player player1 = new Player("player1", game);
	static Player player2 = new Player("player2", game);

	public static void createFireball(Player p, double x, double y, Networking n) {
		if (player1.getHealth() > 0 && player2.getHealth() > 0 && p.playerNum.equals("player1")) {
			Fireball fireball = new Fireball(game);
			fireball.CastFireball(player1, player2, game, x, y, n);
			n.sendFireball(x, y);
		} else if (player1.getHealth() > 0 && player2.getHealth() > 0 && p.playerNum.equals("player2")) {
			Fireball fireball = new Fireball(game);
			fireball.CastFireball(player2, player1, game, x, y, n);
		}
		if (player1.getHealth() <= 0) {
			Text t = new Text(250, 250, "Player 2 wins!");
			player1.playerView.setVisible(false);
			game.getChildren().add(t);
		}
		if (player2.getHealth() <= 0) {
			Text t = new Text(250, 250, "Player 1 wins!");
			player2.playerView.setVisible(false);
			game.getChildren().add(t);
		}
	}

	public void moveAndFireball(Pane game, Networking net, Player player1, Player player2) throws IOException {
		player1.Movement(game, net, player2);
		if (true) {
			try {
				double[] pos = net.recieveFireball();
				System.out.println("run");
				createFireball(player2, pos[0], pos[1], net);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			TextInputDialog getAddr = new TextInputDialog();
			getAddr.setHeaderText(null);
			getAddr.setTitle(null);
			getAddr.setContentText(
					"Enter the IP address of the player's computer you wish to compete with using spaces: ");
			Optional<String> addr = getAddr.showAndWait();
			String name = addr.get();
			Networking net = new Networking(name);
			moveAndFireball(game, net, player1, player2);

			game.setOnMousePressed(event -> {
				createFireball(player1, event.getX(), event.getY(), net);
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
