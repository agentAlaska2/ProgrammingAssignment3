package application;

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
	Pane game = new Pane();
	Player player1 = new Player("player1", game);
	Player player2 = new Player("player2", game);
	boolean isHost;

	public void createFireball(double x, double y, Networking n) {
		if (player1.getHealth() > 0 && player2.getHealth() > 0) {
			Fireball fireball = new Fireball(player1, game);
			fireball.CastFireball(player1, player2, game, x, y, n);
			n.sendFireball(x,y);
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

	@Override
	public void start(Stage primaryStage) {
		try {
			TextInputDialog getAddr = new TextInputDialog();
			getAddr.setHeaderText(null);
			getAddr.setTitle(null);
			getAddr.setContentText("Enter the IP address of the player's computer you wish to compete with using spaces: ");
			Optional<String> addr = getAddr.showAndWait();
			String name = addr.get();
			Networking net = new Networking(name);
			Alert host = new Alert(AlertType.CONFIRMATION);
			host.setHeaderText(null);
			host.setContentText("Do you want to host?");
			Optional<ButtonType> button = host.showAndWait();
			if(button.get() == ButtonType.OK) {
				isHost = true;
			}
			else {
				isHost = false;
			}
			player1.Movement(game, net, isHost);
			player2.Movement(game, net, false);
			try {
				double[] pos = net.recieveFireball();
				createFireball(pos[0], pos[1], net);
			} catch (Exception e) {

			}

			game.setOnMousePressed(event -> {
				createFireball(event.getX(), event.getY(), net);
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
